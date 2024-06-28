/*
 * ElementDefinitionBrowserViewModel.java
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
package ViewModels.ObjectBrowser;

import javax.swing.ListSelectionModel;
import javax.swing.tree.TreeModel;

import org.netbeans.swing.outline.DefaultOutlineModel;

import HubController.IHubController;
import ViewModels.ObjectBrowserViewModel;
import ViewModels.Interfaces.IElementDefinitionBrowserViewModel;
import ViewModels.Interfaces.IHubBrowserContextMenuViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.ElementDefinitionBrowserTreeRowViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.ElementDefinitionBrowserTreeViewModel;
import Views.ObjectBrowser.ObjectBrowser;

/**
 * The {@linkplain ElementDefinitionBrowserViewModel} is the main browser view model for the element definition tree displayed in a {@linkplain ObjectBrowser}
 */
public final class ElementDefinitionBrowserViewModel extends ObjectBrowserViewModel implements IElementDefinitionBrowserViewModel
{
    /**
     * Backing field for {@linkplain #GetContextMenuViewModel()}
     */
    private IHubBrowserContextMenuViewModel contextMenuViewModel;

    /**
     * Gets the the associated {@linkplain IHubBrowserContextMenuViewModel}
     * 
     * @return the {@linkplain IHubBrowserContextMenuViewModel}
     */
    public IHubBrowserContextMenuViewModel GetContextMenuViewModel()
    {
        return this.contextMenuViewModel;
    }
    
    /**
     * Initializes a new {@linkplain ElementDefinitionBrowserViewModel}
     * 
     * @param hubController the {@linkplain IHubController}
     */
    public ElementDefinitionBrowserViewModel(IHubController hubController)
    {   
        super(hubController);
        this.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION;
    }
    
    /**
     * Updates this view model {@linkplain TreeModel}
     * 
     * @param isConnected a value indicating whether the session is open
     */
    @Override
    protected void UpdateBrowserTrees(Boolean isConnected)
    {
        if(Boolean.TRUE.equals(isConnected))
        {
            this.browserTreeModel.Value(DefaultOutlineModel.createOutlineModel(
                    new ElementDefinitionBrowserTreeViewModel(this.hubController.GetOpenIteration()), 
                    new ElementDefinitionBrowserTreeRowViewModel(), true));
        }

        this.isTheTreeVisible.Value(isConnected);
    }
}
