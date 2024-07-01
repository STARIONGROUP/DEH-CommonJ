/*
 * IHubBrowserPanelViewModel.java
 *
 * Copyright (c) 2020-2024 Starion Group S.A.
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
package ViewModels.Interfaces;

import ViewModels.HubBrowserPanelViewModel;

/**
 * The {@linkplain IHubBrowserPanelViewModel} is the interface definition for {@link HubBrowserPanelViewModel}
 */
public interface IHubBrowserPanelViewModel extends IViewModel
{
    /**
     * Gets the {@linkplain IHubBrowserHeaderViewModel}
     * 
     * @return the {@linkplain IHubBrowserHeaderViewModel}
     */
    IHubBrowserHeaderViewModel GetHubBrowserHeaderViewModel();
    
    /**
     * Gets the {@linkplain IObjectBrowserViewModel} for the element definitions tree
     * 
     * @return the {@linkplain IObjectBrowserViewModel}
     */
    IObjectBrowserViewModel GetElementDefinitionBrowserViewModel();

    /**
     * Gets the {@linkplain IObjectBrowserViewModel} for the requirements tree
     * 
     * @return the {@linkplain IObjectBrowserViewModel}
     */
    IObjectBrowserViewModel GetRequirementBrowserViewModel();

    /**
     * Gets the {@linkplain ISessionControlPanelViewModel} for the session controls panel
     * 
     * @return the {@linkplain ISessionControlPanelViewModel}
     */
    ISessionControlPanelViewModel GetSessionControlViewModel();
    
    /**
     * The {@linkplain IHubBrowserContextMenuViewModel} for the requirement browser
     * 
     * @return a {@linkplain IHubBrowserContextMenuViewModel}
     */
    IHubBrowserContextMenuViewModel GetRequirementBrowserContextMenuViewModel();

    /**
     * The {@linkplain IHubBrowserContextMenuViewModel} for the element definition browser
     * 
     * @return a {@linkplain IHubBrowserContextMenuViewModel}
     */
    IHubBrowserContextMenuViewModel GetElementDefinitionBrowserContextMenuViewModel();
}
