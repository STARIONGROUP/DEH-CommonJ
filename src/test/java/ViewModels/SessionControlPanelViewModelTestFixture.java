/*
 * SessionControlPanelViewModelTestFixture.java
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Iterator;

import static org.mockito.ArgumentMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import HubController.IHubController;
import Services.NavigationService.INavigationService;
import Views.HubLogin;

class SessionControlPanelViewModelTestFixture
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
        this.viewModel = new SessionControlPanelViewModel(this.hubController, this.navigationService);
    }

    @Test
    void VerifyConnectButtonAction()
    {
        assertDoesNotThrow(() -> this.viewModel.Connect());
        verify(this.navigationService, times(1)).ShowDialog(any(HubLogin.class));
    }
    
    @Test
    void Verify() throws InterruptedException
    {
        ArrayList<Integer> observedTicks = new ArrayList<Integer>();
        this.viewModel.GetTimeObservable().subscribe(x -> observedTicks.add(x));
        this.viewModel.SetAutoRefresh(6);

        assertEquals(1, observedTicks.size());
        Thread.sleep(7000);

        assertEquals(8, observedTicks.size());

        verify(this.hubController, times(1)).Refresh();
    }
}
