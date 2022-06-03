/*
 * HubBrowserPanelViewModelTestFixture.java
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import HubController.IHubController;
import Services.NavigationService.INavigationService;
import ViewModels.Interfaces.IElementDefinitionBrowserViewModel;
import ViewModels.Interfaces.IHubBrowserContextMenuViewModel;
import ViewModels.Interfaces.IHubBrowserHeaderViewModel;
import ViewModels.Interfaces.IRequirementBrowserViewModel;
import ViewModels.Interfaces.ISessionControlPanelViewModel;

class HubBrowserPanelViewModelTestFixture
{
    private INavigationService navigationService;
    private IHubController hubController;
    private IHubBrowserHeaderViewModel hubBrowserHeaderViewModel;
    private IElementDefinitionBrowserViewModel elementDefinitionBrowser;
    private IRequirementBrowserViewModel requirementBrowser;
    private ISessionControlPanelViewModel sessionControlViewModel;
    private IHubBrowserContextMenuViewModel contextMenuViewModel;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
        this.navigationService = mock(INavigationService.class);
        this.hubController = mock(IHubController.class);
        this.hubBrowserHeaderViewModel = mock(IHubBrowserHeaderViewModel.class);
        this.elementDefinitionBrowser = mock(IElementDefinitionBrowserViewModel.class);
        this.requirementBrowser = mock(IRequirementBrowserViewModel.class);
        this.sessionControlViewModel = mock(ISessionControlPanelViewModel.class);
        this.contextMenuViewModel = mock(IHubBrowserContextMenuViewModel.class);
    }

    @Test
    void VerifyConnectButtonAction()
    {
        assertDoesNotThrow(() -> new HubBrowserPanelViewModel(this.navigationService, 
                this.hubController, this.hubBrowserHeaderViewModel, this.requirementBrowser, this.elementDefinitionBrowser, 
                this.sessionControlViewModel, this.contextMenuViewModel, this.contextMenuViewModel));
    }    
}
