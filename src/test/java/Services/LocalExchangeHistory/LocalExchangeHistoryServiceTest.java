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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import HubController.IHubController;
import Services.AdapterInfo.IAdapterInfoService;
import Services.LocalExchangeHistory.ExchangeHistoryEntryCollection;
import Services.LocalExchangeHistory.LocalExchangeHistoryService;
import ViewModels.ExchangeHistory.Rows.ExchangeHistoryEntryRowViewModel;
import cdp4common.Version;

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
    }
    
    @Test
    void VerifyReadAndWrite() 
    {
    	assertDoesNotThrow(() -> this.service.Read());
    	assertDoesNotThrow(() -> this.service.Write());
    }
}
