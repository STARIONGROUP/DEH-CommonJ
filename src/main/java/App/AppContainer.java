/*
 * AppController.java
 *
 * Copyright (c) 2015-2019 RHEA System S.A.
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
package App;

import org.picocontainer.DefaultPicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.behaviors.Caching;
import org.picocontainer.behaviors.ImplementationHiding;
import HubController.HubController;
import HubController.IHubController;
import Service.NavigationService.INavigationService;
import Service.NavigationService.NavigationService;
import ViewModels.HubLoginViewModel;
import ViewModels.Interfaces.IHubLoginViewModel;
import ViewModels.Interfaces.IViewModel;

import static org.picocontainer.Characteristics.CACHE;
import static org.picocontainer.Characteristics.NO_CACHE;

import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class AppContainer 
{
	public final static MutablePicoContainer Container;
	
	static
	{
	    Container = new DefaultPicoContainer(new Caching());
        Container.as(NO_CACHE).addComponent(IHubLoginViewModel.class.getSimpleName(), HubLoginViewModel.class);
        Container.as(NO_CACHE).addComponent(IHubLoginViewModel.class, HubLoginViewModel.class);
		Container.as(CACHE).addComponent(IHubController.class, HubController.class);
		Container.as(NO_CACHE).addComponent(INavigationService.class, NavigationService.class);
	}
}
