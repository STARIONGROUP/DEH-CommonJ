/*
 * SessionControlPanelViewModelTest.java
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
package ViewModels;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.awaitility.Awaitility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import HubController.IHubController;
import Services.NavigationService.INavigationService;
import Views.HubLogin;

class SessionControlPanelViewModelTest
{
    private IHubController hubController;
    private INavigationService navigationService;
    private SessionControlPanelViewModel viewModel;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
        this.hubController = mock(IHubController.class);    
        this.navigationService = mock(INavigationService.class);
        when(this.navigationService.ShowDialog(any(HubLogin.class))).thenReturn(true);
        when(this.hubController.GetIsSessionOpen()).thenReturn(false, true);
        when(this.hubController.Refresh()).thenReturn(true);
        when(this.hubController.Reload()).thenReturn(true);
        
        this.viewModel = new SessionControlPanelViewModel(this.hubController, this.navigationService);
    }
    
    @Test
    void VerifyIsConnected()
    {
    	assertFalse(this.viewModel.GetIsConnected());
    	assertTrue(this.viewModel.GetIsConnected());
    }

    @Test
    void VerifyConnectButtonActions()
    {
        assertDoesNotThrow(() -> this.viewModel.Connect());
        verify(this.navigationService, times(1)).ShowDialog(any(HubLogin.class));
        assertDoesNotThrow(() -> this.viewModel.Disconnect());
    }
    
    @Test
    void VerifyAutoRefresh() throws InterruptedException
    {
        ArrayList<Integer> observedTicks = new ArrayList<Integer>();
        this.viewModel.GetTimeObservable().subscribe(x -> observedTicks.add(x));
        this.viewModel.SetAutoRefresh(5);

        Awaitility.await().atMost(8, SECONDS).until(() ->
        {
        	return observedTicks.size() == 8;
        });
        verify(this.hubController, times(1)).Refresh();
    }
    
    @Test
    void VerifySessionRefresh() 
    {
    	assertDoesNotThrow(() -> this.viewModel.Refresh());
    	assertDoesNotThrow(() -> this.viewModel.Reload());
    	verify(this.hubController, times(1)).Refresh();
    	verify(this.hubController, times(1)).Reload();
    }
}
