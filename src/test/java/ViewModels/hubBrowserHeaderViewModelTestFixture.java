/*
 * hubBrowserHeaderViewModelTestFixture.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-CommonJ
 *
 * The DEH-MDSYSML is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-MDSYSML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ViewModels;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import HubController.IHubController;
import Reactive.ObservableValue;
import cdp4common.engineeringmodeldata.EngineeringModel;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EngineeringModelSetup;
import cdp4common.sitedirectorydata.IterationSetup;
import cdp4common.sitedirectorydata.Person;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

class hubBrowserHeaderViewModelTestFixture
{
    private IHubController hubController;
    private HubBrowserHeaderViewModel viewModel;
    private Iteration iteration;
    private ObservableValue<Boolean> isSessionOpen;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
        IterationSetup iterationSetup = new IterationSetup();
        iterationSetup.setIterationNumber(42);
        EngineeringModelSetup engineeringModelSetup = new EngineeringModelSetup();
        engineeringModelSetup.setName("model");
        EngineeringModel engineeringModel = new EngineeringModel();
        engineeringModel.setEngineeringModelSetup(engineeringModelSetup);
        
        this.iteration = new Iteration();
        this.iteration.setIterationSetup(iterationSetup);
        
        engineeringModel.getIteration().add(iteration);
        
        this.hubController = mock(IHubController.class);
                
        Person person = new Person();
        
        DomainOfExpertise domain = new DomainOfExpertise();
        domain.setName("domain");
        
        when(this.hubController.GetOpenIteration()).thenReturn(this.iteration);        
        when(this.hubController.GetDataSourceUri()).thenReturn("http://localhost:5000");        
        when(this.hubController.GetActivePerson()).thenReturn(person);
        when(this.hubController.GetCurrentDomainOfExpertise()).thenReturn(domain);
        
        this.isSessionOpen = new ObservableValue<Boolean>(false);
        when(this.hubController.GetIsSessionOpenObservable()).thenReturn(this.isSessionOpen.Observable());
        
        this.viewModel = new HubBrowserHeaderViewModel(this.hubController);
    }

    @Test
    void VerifyProperties() throws InterruptedException, ExecutionException
    {
        this.isSessionOpen.Value(false);
        
        ArrayList<String> modelResults = new ArrayList<String>();
        ArrayList<String> iterationResults = new ArrayList<String>();
        ArrayList<String> personResult = new ArrayList<String>();
        ArrayList<String> domainResult = new ArrayList<String>();
        ArrayList<String> dataSourceResult = new ArrayList<String>();
        
        this.viewModel.GetEngineeringModelName().subscribe(x -> modelResults.add(x));
        this.viewModel.GetIterationNumber().subscribe(x -> iterationResults.add(x));
        this.viewModel.GetPersonName().subscribe(x -> personResult.add(x));
        this.viewModel.GetDomainOfExpertiseName().subscribe(x -> domainResult.add(x));
        this.viewModel.GetDataSource().subscribe(x -> dataSourceResult.add(x));

        this.isSessionOpen.Value(false);
        this.isSessionOpen.Value(true);
        
        assertEquals(2, modelResults.size());
        assertEquals(2, iterationResults.size());
        assertEquals(2, personResult.size());
        assertEquals(2, domainResult.size());
        assertEquals(2, dataSourceResult.size());
     
        assertEquals("model", modelResults.stream().skip(modelResults.size() - 1).findFirst().get());
        assertEquals("42", iterationResults.stream().skip(iterationResults.size() - 1).findFirst().get());
        assertEquals("null null", personResult.stream().skip(iterationResults.size() - 1).findFirst().get());
        assertEquals("domain", domainResult.stream().skip(domainResult.size() - 1).findFirst().get());
        assertEquals("http://localhost:5000", dataSourceResult.stream().skip(dataSourceResult.size() - 1).findFirst().get());    
        }
}
