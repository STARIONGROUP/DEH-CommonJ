/*
 * HubBrowserContextMenu.java
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
package Views.ContextMenu;

import javax.swing.JMenuItem;

import Utils.ImageLoader.ImageLoader;
import ViewModels.Interfaces.IHubBrowserContextMenuViewModel;
import ViewModels.Interfaces.IImpactViewContextMenuViewModel;
import ViewModels.Interfaces.IObjectBrowserViewModel;
import ViewModels.Interfaces.IViewModel;

/**
 * The {@linkplain HubBrowserContextMenu} is the main context menu view for HUB Browsers
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class HubBrowserContextMenu extends ContextMenu<IHubBrowserContextMenuViewModel>
{
    /**
     * The {@linkplain IImpactViewContextMenuViewModel} data context of this view
     */
    private IHubBrowserContextMenuViewModel dataContext;
    
    /**
     * the {@linkplain JMenuItem} that allows to map the top element
     */
    private JMenuItem mapTopElement;

    /**
     * the {@linkplain JMenuItem} that allows to map the selection
     */
    private JMenuItem mapSelection;

    /**
     * The {@linkplain Class} of {@linkplain IObjectBrowserViewModel} that implement this {@linkplain ContextMenu}
     */
    private Class<? extends IObjectBrowserViewModel> browserType;

    /**
     * Initializes a new {@linkplain ImpactViewContextMenu}
     * 
     * @param browserType the {@linkplain Class} of {@linkplain IObjectBrowserViewModel} that implement this {@linkplain ContextMenu}
     */
    public HubBrowserContextMenu(Class<? extends IObjectBrowserViewModel> browserType)
    {
        this.browserType = browserType;
        this.mapTopElement = new JMenuItem("Map the Top Element");
        this.mapTopElement.setIcon(ImageLoader.GetIcon("icon16.png"));
        this.mapTopElement.setEnabled(false);
        this.add(this.mapTopElement);
        this.mapSelection = new JMenuItem("Map the Current Selection");
        this.mapSelection.setIcon(ImageLoader.GetIcon("icon16.png"));
        this.mapSelection.setEnabled(false);
        this.add(this.mapSelection);
    }
    
    /**
     * Binds the <code>TViewModel viewModel</code> to the implementing view
     * 
     * @param <code>viewModel</code> the view model to bind
     */
    public void Bind()
    {
        this.dataContext.SetBrowserType(this.browserType);
        
        this.mapTopElement.addActionListener(x -> this.dataContext.MapTopElement());        
        this.mapSelection.addActionListener(x -> this.dataContext.MapSelection());
        
        this.dataContext.CanMapTopElement().subscribe(x -> this.mapTopElement.setEnabled(x));
        this.dataContext.CanMapSelection().subscribe(x -> this.mapSelection.setEnabled(x));
    }
    
    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    public void SetDataContext(IViewModel viewModel)
    {
        this.dataContext = (IHubBrowserContextMenuViewModel)viewModel;
        this.Bind();
    }
    
    /**
     * Gets the DataContext
     * 
     * @return the {@link IViewModel}
     */
    public IHubBrowserContextMenuViewModel GetDataContext()
    {
        return this.dataContext;
    }
}
