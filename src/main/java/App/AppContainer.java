/*
 * AppController.java
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
package App;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import HubController.HubController;
import HubController.IHubController;
import Services.UserPreferenceService.*;
import Services.NavigationService.*;
import ViewModels.*;
import ViewModels.Interfaces.*;
import ViewModels.ObjectBrowser.ElementDefinitionBrowserViewModel;
import ViewModels.ObjectBrowser.RequirementBrowserViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.ElementDefinitionBrowserTreeRowViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.ElementDefinitionBrowserTreeViewModel;

import static org.picocontainer.Characteristics.CACHE;
import static org.picocontainer.Characteristics.NO_CACHE;

/**
 * The {@linkplain AppContainer} provides the IOC container for DI for this Application
 */
public final class AppContainer 
{
    /**
     * Gets the {@link PicoContainer} container
     */
	public final static MutablePicoContainer Container;
	
	/**
	 * Private set for {@linkplain Container}
	 * 
	 * Registers all the dependencies such as services, view models 
	 */
	static
	{
        Container = new DefaultPicoContainer(new Caching());
        Container.as(CACHE).addComponent(IHubController.class, HubController.class);
        Container.as(NO_CACHE).addComponent(INavigationService.class, NavigationService.class);
        Container.as(NO_CACHE).addComponent(IUserPreferenceService.class, UserPreferenceService.class);
        RegisterViewModels();
	}

    /**
     * Registers the view models that need to be resolved.
     * @implNote
     * Dialogs that need to be set visible through the {@linkplain INavigationService} 
     * needs their view model to be registered in the container with the key to be a string reflecting the interface class name
     */
    private static void RegisterViewModels()
    {
        Container.as(NO_CACHE).addComponent(IHubLoginViewModel.class.getSimpleName(), HubLoginViewModel.class);
        Container.as(NO_CACHE).addComponent(IHubLoginViewModel.class, HubLoginViewModel.class);
        Container.as(NO_CACHE).addComponent(IHubBrowserHeaderViewModel.class, HubBrowserHeaderViewModel.class);
        Container.as(NO_CACHE).addComponent(IElementDefinitionBrowserViewModel.class, ElementDefinitionBrowserViewModel.class);        
        Container.as(NO_CACHE).addComponent(IRequirementBrowserViewModel.class, RequirementBrowserViewModel.class);        
    }
}
