/*
 * ImpactViewContextMenu.java
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
import ViewModels.Interfaces.IImpactViewContextMenuViewModel;
import ViewModels.Interfaces.IViewModel;
import cdp4common.commondata.ClassKind;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain ImpactViewContextMenu} is the main context menu view for {@linkplain ImpactViewPanel}
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class ImpactViewContextMenu extends ContextMenu<IImpactViewContextMenuViewModel>
{
    /**
     * The {@linkplain IImpactViewContextMenuViewModel} data context of this view
     */
    private IImpactViewContextMenuViewModel dataContext;
    
    /**
     * the {@linkplain JMenuItem} that allows to select all select-able things
     */
    private JMenuItem selectAll;

    /**
     * the {@linkplain JMenuItem} that allows to deselect all select-able things
     */
    private JMenuItem deselectAll;

    /**
     * the {@linkplain ClassKind} of {@linkplain Thing}>
     */
    private ClassKind classKind;
    
    /**
     * Initializes a new {@linkplain ImpactViewContextMenu}
     */
    public ImpactViewContextMenu() 
    {
        this.selectAll = new JMenuItem("Select all");
        this.selectAll.setIcon(ImageLoader.GetIcon("icon16.png"));
        this.add(this.selectAll);
        this.deselectAll = new JMenuItem("Deselect all");
        this.deselectAll.setIcon(ImageLoader.GetIcon("icon16.png"));
        this.add(this.deselectAll);
    }
    
    /**
     * Initializes a new {@linkplain ImpactViewContextMenu}
     * 
     * @param clazz the {@linkplain Class} of {@linkplain Thing}>
     */
    public ImpactViewContextMenu(ClassKind classKind) 
    {
        this();
        this.classKind = classKind;
    }
    
    /**
     * Binds the <code>TViewModel viewModel</code> to the implementing view
     * 
     * @param <code>viewModel</code> the view model to bind
     */
    public void Bind()
    {
        this.selectAll.addActionListener(x -> this.dataContext.SelectAllOfType(classKind));
        this.deselectAll.addActionListener(x -> this.dataContext.DeselectAllOfType(classKind));
    }
    
    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    public void SetDataContext(IImpactViewContextMenuViewModel viewModel)
    {
        this.dataContext = viewModel;
        this.Bind();
    }
    
    /**
     * Gets the DataContext
     * 
     * @return An {@link IViewModel}
     */
    public IImpactViewContextMenuViewModel GetDataContext()
    {
        return this.dataContext;
    }
}
