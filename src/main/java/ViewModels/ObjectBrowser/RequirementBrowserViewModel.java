/*
 * RequirementBrowserViewModel.java
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
package ViewModels.ObjectBrowser;

import org.netbeans.swing.outline.DefaultOutlineModel;

import HubController.IHubController;
import ViewModels.ObjectBrowserViewModel;
import ViewModels.Interfaces.IHubBrowserContextMenuViewModel;
import ViewModels.Interfaces.IRequirementBrowserViewModel;
import ViewModels.ObjectBrowser.RequirementTree.RequirementBrowserTreeRowViewModel;
import ViewModels.ObjectBrowser.RequirementTree.RequirementBrowserTreeViewModel;

/**
 * The {@linkplain RequirementBrowserViewModel} is the view model for the {@linkplain RequirementsSpecification} browser
 */
public final class RequirementBrowserViewModel extends ObjectBrowserViewModel implements IRequirementBrowserViewModel
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
     * Initializes a new {@linkplain RequirementBrowserViewModel}
     * 
     * @param hubController the {@linkplain IHubController}
     */
    public RequirementBrowserViewModel(IHubController hubController)
    {
        super(hubController);
        this.canSelectMultipleElements = true;
    }
    
    /**
     * Updates this view model {@linkplain TreeModel}
     * 
     * @param isConnected a value indicating whether the session is open
     */
    @Override
    protected void UpdateBrowserTrees(Boolean isConnected)
    {
        if(isConnected.booleanValue())
        {
            this.browserTreeModel.Value(DefaultOutlineModel.createOutlineModel(
                    new RequirementBrowserTreeViewModel(this.hubController.GetOpenIteration()), 
                    new RequirementBrowserTreeRowViewModel(), true));
        }

        this.isTheTreeVisible.Value(isConnected);
    }
}
