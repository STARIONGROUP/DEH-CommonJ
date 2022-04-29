/*
 * HubBrowserPanelViewModel.java
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

import HubController.IHubController;
import Services.NavigationService.INavigationService;
import ViewModels.Interfaces.IElementDefinitionBrowserViewModel;
import ViewModels.Interfaces.IHubBrowserContextMenuViewModel;
import ViewModels.Interfaces.IHubBrowserHeaderViewModel;
import ViewModels.Interfaces.IHubBrowserPanelViewModel;
import ViewModels.Interfaces.IObjectBrowserViewModel;
import ViewModels.Interfaces.IRequirementBrowserViewModel;
import ViewModels.Interfaces.ISessionControlPanelViewModel;

/**
 * The {@link HubBrowserPanelViewModel} is the main viewModel for the Hub browsers views
 */
public class HubBrowserPanelViewModel implements IHubBrowserPanelViewModel
{    
    /**
     * Backing field for {@linkplain #GetElementDefinitionBrowserContextMenuViewModel()}
     */
    private IHubBrowserContextMenuViewModel elementDefinitionBrowserContextMenuViewModel;
    
    /**
     * The {@linkplain IHubBrowserContextMenuViewModel} for the element definition browser
     * 
     * @return a {@linkplain IHubBrowserContextMenuViewModel}
     */
    @Override
    public IHubBrowserContextMenuViewModel GetElementDefinitionBrowserContextMenuViewModel()
    {
        return this.elementDefinitionBrowserContextMenuViewModel;
    }
    
    private IHubBrowserContextMenuViewModel requirementBrowserContextMenuViewModel;

    /**
     * The {@linkplain IHubBrowserContextMenuViewModel} for the requirement browser
     * 
     * @return a {@linkplain IHubBrowserContextMenuViewModel}
     */
    @Override
    public IHubBrowserContextMenuViewModel GetRequirementBrowserContextMenuViewModel()
    {
        return this.requirementBrowserContextMenuViewModel;
    }
    
    /**
     * the {@linkplain IHubBrowserHeaderViewModel}
     */
    private IHubBrowserHeaderViewModel hubBrowserHeaderViewModel;
    
    /**
     * Gets the {@linkplain IHubBrowserHeaderViewModel}
     * 
     * @return the {@linkplain IHubBrowserHeaderViewModel}
     */
    @Override
    public IHubBrowserHeaderViewModel GetHubBrowserHeaderViewModel()
    {
        return this.hubBrowserHeaderViewModel;
    }
    
    /**
     * the {@linkplain IHubBrowserHeaderViewModel} for the element definition tree
     */
    private IObjectBrowserViewModel elementDefinitionBrowserViewModel;
    
    /**
     * Gets the {@linkplain IObjectBrowserViewModel} for the element definition tree
     * 
     * @return the {@linkplain IObjectBrowserViewModel}
     */
    @Override
    public IObjectBrowserViewModel GetElementDefinitionBrowserViewModel()
    {
        return this.elementDefinitionBrowserViewModel;
    }

    /**
     * The {@linkplain IHubBrowserHeaderViewModel} for the requirement tree
     */
    private IObjectBrowserViewModel requirementBrowserViewModel;

    /**
     * Gets the {@linkplain IObjectBrowserViewModel} for the requirement tree
     * 
     * @return the {@linkplain IObjectBrowserViewModel}
     */
    @Override
    public IObjectBrowserViewModel GetRequirementBrowserViewModel()
    {
        return this.requirementBrowserViewModel;
    }

    /**
     * The {@linkplain ISessionControlPanelViewModel} for the session controls panel
     */
    private ISessionControlPanelViewModel sessionControlViewModel;
    
    /**
     * Gets the {@linkplain ISessionControlPanelViewModel} for the session controls panel
     * 
     * @return the {@linkplain ISessionControlPanelViewModel}
     */
    @Override
    public ISessionControlPanelViewModel GetSessionControlViewModel()
    {
        return this.sessionControlViewModel;
    }
    
    /**
     * Initializes a new {@link HubBrowserPanelViewModel}
     * @param navigationService the {@linkplain INavigationService}
     * @param hubController the {@linkplain IHubController}
     * @param hubBrowserHeaderViewModel the {@linkplain IHubBrowserHeaderViewModel}
     * @param requirementBrowserViewModel the {@linkplain IRequirementBrowserViewModel}
     * @param elementDefinitionBrowserViewModel the {@linkplain IElementDefinitionBrowserViewModel}
     * @param sessionControlViewModel the {@linkplain ISessionControlPanelViewModel}
     * @param requirementBrowserContextMenuViewModel the {@linkplain IHubBrowserContextMenuViewModel}
     * @param elementDefinitionBrowserContextMenuViewModel the {@linkplain IHubBrowserContextMenuViewModel}
     */
    public HubBrowserPanelViewModel(INavigationService navigationService, IHubController hubController, 
            IHubBrowserHeaderViewModel hubBrowserHeaderViewModel, IRequirementBrowserViewModel requirementBrowserViewModel,
            IElementDefinitionBrowserViewModel elementDefinitionBrowserViewModel, ISessionControlPanelViewModel sessionControlViewModel,
            IHubBrowserContextMenuViewModel requirementBrowserContextMenuViewModel, IHubBrowserContextMenuViewModel elementDefinitionBrowserContextMenuViewModel)
    {
        this.hubBrowserHeaderViewModel = hubBrowserHeaderViewModel;
        this.elementDefinitionBrowserViewModel = elementDefinitionBrowserViewModel;
        this.requirementBrowserViewModel = requirementBrowserViewModel;
        this.sessionControlViewModel = sessionControlViewModel;
        this.requirementBrowserContextMenuViewModel = requirementBrowserContextMenuViewModel;
        this.elementDefinitionBrowserContextMenuViewModel = elementDefinitionBrowserContextMenuViewModel;
    }
}
