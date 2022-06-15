/*
 * MappingListView.java
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
package Views.MappingList;

import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import Annotations.ExludeFromCodeCoverageGeneratedReport;
import ViewModels.Interfaces.IHubBrowserContextMenuViewModel;
import ViewModels.Interfaces.IViewModel;
import ViewModels.MappingListView.MappingListViewRendererDataProvider;
import ViewModels.MappingListView.Interfaces.IMappingListViewViewModel;
import ViewModels.MappingListView.Renderers.MappingListViewDefinedThingCellEditor;
import ViewModels.MappingListView.Renderers.MappingListViewDefinedThingCellRenderer;
import Views.ObjectBrowser.ObjectBrowser;
import Views.ObjectBrowser.ObjectBrowserBase;
import cdp4common.commondata.DefinedThing;

/**
 * The {@linkplain MappingListView} is the {@linkplain ObjectBrowser} for the MappingListViewPanel
 */
@ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public abstract class MappingListView extends ObjectBrowserBase<IMappingListViewViewModel, IHubBrowserContextMenuViewModel>
{    
    /**
     * Initializes a new {@linkplain MagicDrawObjectBrowser}
     */
    protected MappingListView()
    {
        super();
        objectBrowserTree.setColumnHidingAllowed(false);
        this.objectBrowserTree.setRenderDataProvider(new MappingListViewRendererDataProvider());
        this.objectBrowserTree.setDefaultRenderer(DefinedThing.class, new MappingListViewDefinedThingCellRenderer());
        this.objectBrowserTree.setDefaultEditor(DefinedThing.class, new MappingListViewDefinedThingCellEditor());
        this.objectBrowserTree.setCellSelectionEnabled(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        this.objectBrowserTree.setDefaultRenderer(String.class, centerRenderer);
        
        this.objectBrowserTree.setRootVisible(false);
        
        this.objectBrowserTree.setToolTipText("The collection of currently mapped elements");
        
        this.objectBrowserTree.setSelectionBackground(new Color(104, 143, 184));
        this.objectBrowserTree.setRowHeight(100);
    }

    /**
     * Sets the columns preferred width
     */
    @Override
    protected void SetColumnsPreferredWidth()
    {
        int totalWidth = (this.getWidth() /2) - 150;
        
        for (int columnIndex = 0; columnIndex < this.objectBrowserTree.getColumnModel().getColumnCount(); columnIndex++)
        {
            this.objectBrowserTree.getColumnModel().getColumn(columnIndex).setPreferredWidth(columnIndex % 2 == 0 ? 75 : totalWidth);
        }
    }
    
    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    @Override
    public void SetDataContext(IMappingListViewViewModel viewModel)
    {
        this.dataContext = viewModel;
        this.Bind();
    }

    /**
     * Handles the selection when the user changes it
     */
    @Override
    protected void OnSelectionChanged() { }
}
