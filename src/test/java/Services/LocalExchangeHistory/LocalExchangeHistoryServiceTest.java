/*
 * LocalExchangeHistoryServiceTest.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-CommonJ
 *
 * The DEH-CommonJ is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-CommonJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package Services.LocalExchangeHistory;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HubController.IHubController;
import Services.AdapterInfo.IAdapterInfoService;
import ViewModels.ExchangeHistory.Rows.ExchangeHistoryEntryRowViewModel;
import cdp4common.ChangeKind;
import cdp4common.Version;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.ElementUsage;
import cdp4common.engineeringmodeldata.Option;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.ParameterOverride;
import cdp4common.engineeringmodeldata.ParameterOverrideValueSet;
import cdp4common.engineeringmodeldata.ParameterSwitchKind;
import cdp4common.engineeringmodeldata.ParameterValueSet;
import cdp4common.sitedirectorydata.BooleanParameterType;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.MeasurementScale;
import cdp4common.sitedirectorydata.ParameterType;
import cdp4common.sitedirectorydata.ParticipantPermission;
import cdp4common.sitedirectorydata.Person;
import cdp4common.sitedirectorydata.RatioScale;
import cdp4common.sitedirectorydata.SimpleQuantityKind;
import cdp4common.types.ValueArray;

class LocalExchangeHistoryServiceTest
{
	private LocalExchangeHistoryService service;
	private IHubController hubController;
	private IAdapterInfoService adapterInfoService;
	
    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
    	this.hubController = mock(IHubController.class);
    	this.adapterInfoService = mock(IAdapterInfoService.class);
    	when(this.adapterInfoService.GetVersion()).thenReturn(new Version(1,5,0));
    	
    	Person person = new Person();
    	person.setShortName("shortname");
    	when(this.hubController.GetActivePerson()).thenReturn(person);
    	
    	DomainOfExpertise domain = new DomainOfExpertise();
    	domain.setShortName("aDomain");
    	domain.setName("A Domain");
    	when(this.hubController.GetCurrentDomainOfExpertise()).thenReturn(domain);
    	
    	this.service = new LocalExchangeHistoryService(this.hubController, this.adapterInfoService);
    }
    
    @Test
    void VerifySerializingExchangeHistoryEntryViewModel()
    {
        ExchangeHistoryEntryRowViewModel entry = new ExchangeHistoryEntryRowViewModel();
        Version adapterVersion = new Version(1,8,456);
        entry.SetAdapterVersion(adapterVersion);
        entry.SetTimeStamp(new Date());
        ExchangeHistoryEntryCollection history = new ExchangeHistoryEntryCollection();
        history.add(entry);
        
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS").create();
        
        String json = gson.toJson(history, ExchangeHistoryEntryCollection.class);
        ExchangeHistoryEntryRowViewModel newEntry = gson.fromJson(json, ExchangeHistoryEntryCollection.class).get(0);
        assertEquals(adapterVersion, newEntry.GetAdapterVersion());
        assertNotSame(adapterVersion, newEntry.GetAdapterVersion());
        newEntry.SetDomain(this.hubController.GetCurrentDomainOfExpertise().getName());
        assertNotNull(newEntry.GetDomain());
        newEntry.SetMessage("A new message");
        assertNotNull(newEntry.GetMessage());
        newEntry.SetTimeStamp(new Date());
        assertNotNull(newEntry.GetTimestamp());
        newEntry.SetPerson(this.hubController.GetActivePerson().getShortName());
        assertNotNull(newEntry.GetPerson());
    }
    
    @Test
    void VerifyReadAndWrite() 
    {
    	assertDoesNotThrow(() -> this.service.Read());
    	assertDoesNotThrow(() -> this.service.Write());
    	this.service.Append("A exchange history entry");
    	assertDoesNotThrow(() -> this.service.Write());
    }
    
    @Test
    void VerifyAppend() 
    {
    	ElementDefinition elementDefinition = new ElementDefinition();
    	elementDefinition.setName("ElementDefinition NAME");
    	assertDoesNotThrow(() -> this.service.Append(elementDefinition, ChangeKind.UPDATE));
    	
    	Parameter parameter = new Parameter();
    	ParameterType booleanParameterType = new BooleanParameterType();
    	booleanParameterType.setName("Boolean parameter type");
    	parameter.setParameterType(booleanParameterType);
    	parameter.setContainer(elementDefinition);
    	assertDoesNotThrow(() -> this.service.Append(parameter, ChangeKind.CREATE));
    	
    	ElementUsage usage = new ElementUsage();
    	usage.setName("Usage");
    	usage.setContainer(elementDefinition);
    	
    	ParameterOverride parameterOverride = new ParameterOverride();
    	parameterOverride.setParameter(parameter);
    	parameterOverride.setContainer(usage);
    	assertDoesNotThrow(() -> this.service.Append(parameterOverride, ChangeKind.CREATE));
    	
    	ParticipantPermission permission = new ParticipantPermission();
    	permission.setIid(UUID.randomUUID());
    	assertDoesNotThrow(() -> this.service.Append(permission, ChangeKind.DELETE));
    	
    	Parameter parameter2 = new Parameter();
    	ParameterType quantityKind = new SimpleQuantityKind();
    	MeasurementScale scale = new RatioScale();
    	scale.setShortName("m");
    	parameter2.setContainer(elementDefinition);
    	parameter2.setParameterType(quantityKind);
    	parameter2.setOptionDependent(true);
    	parameter2.setScale(scale);
    	Option option = new Option();
    	option.setName("default");
    	
    	ParameterValueSet booleanValueSetOld = new ParameterValueSet();
    	booleanValueSetOld.setComputed(new ValueArray<>(Arrays.asList(""), String.class));
    	booleanValueSetOld.setValueSwitch(ParameterSwitchKind.COMPUTED);
    	booleanValueSetOld.setContainer(parameter);

    	ParameterValueSet booleanValueSetNew = new ParameterValueSet();
    	booleanValueSetNew.setComputed(new ValueArray<>(Arrays.asList("true"), String.class));
    	booleanValueSetNew.setValueSwitch(ParameterSwitchKind.COMPUTED);
    	
    	assertDoesNotThrow(() -> this.service.Append(booleanValueSetOld, booleanValueSetNew));
    	
    	ParameterValueSet quantityValueSetOld = new ParameterValueSet();
    	quantityValueSetOld.setManual(new ValueArray<>(Arrays.asList(""), String.class));
    	quantityValueSetOld.setValueSwitch(ParameterSwitchKind.MANUAL);
    	quantityValueSetOld.setContainer(parameter2);
    	quantityValueSetOld.setActualOption(option);
    	
    	ParameterValueSet quantityValueSetNew = new ParameterValueSet();
    	quantityValueSetNew.setManual(new ValueArray<>(Arrays.asList("45"), String.class));
    	quantityValueSetNew.setValueSwitch(ParameterSwitchKind.MANUAL);
    	quantityValueSetNew.setActualOption(option);
    	
    	assertDoesNotThrow(() -> this.service.Append(quantityValueSetOld, quantityValueSetNew));
    	
    	ParameterOverrideValueSet parameterOverrideValueSetOld = new ParameterOverrideValueSet();
    	parameterOverrideValueSetOld.setManual(new ValueArray<>(Arrays.asList(""), String.class));
    	parameterOverrideValueSetOld.setValueSwitch(ParameterSwitchKind.MANUAL);
    	parameterOverride.getValueSet().add(parameterOverrideValueSetOld);
    	parameterOverrideValueSetOld.setContainer(parameterOverride);
    	parameterOverrideValueSetOld.setParameterValueSet(quantityValueSetNew);

    	ParameterOverrideValueSet parameterOverrideValueSetNew = new ParameterOverrideValueSet();
    	parameterOverrideValueSetNew.setManual(new ValueArray<>(Arrays.asList("45"), String.class));
    	parameterOverrideValueSetNew.setValueSwitch(ParameterSwitchKind.MANUAL);
    	parameterOverrideValueSetNew.setParameterValueSet(quantityValueSetNew);
    	
    	assertDoesNotThrow(() -> this.service.Append(parameterOverrideValueSetOld, parameterOverrideValueSetNew));
    }
}
