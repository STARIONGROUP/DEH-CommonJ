/*
 * ImpactViewPanelViewModel.java
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import HubController.IHubController;

/**
 * The {@linkplain ImpactViewPanelViewModel} is the main view model for the impact view panel
 */
public abstract class ImpactViewPanelViewModel
{
    /**
     * The current instance class {@linkplain Logger}
     */
    protected Logger Logger = LogManager.getLogger();
    
    /**
     * The {@linkplain IHubController}
     */
    protected IHubController HubController;

    /**
     * Initializes a new {@linkplain ImpactViewPanelViewModel}
     * 
     * @param hubController the {@linkplain IHubController}
     */
    public ImpactViewPanelViewModel(IHubController hubController)
    {
        this.HubController = hubController;
    }
}
