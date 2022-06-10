/*
 * MappingListViewDefinedThingRootRowTreeViewModel.java
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
package ViewModels.MappingListView.Rows.HubRows;

import javax.swing.tree.TreeModel;

import HubController.IHubController;
import ViewModels.MappingListView.Rows.MappingListViewContainerBaseRowViewModel;
import ViewModels.ObjectBrowser.BrowserTreeViewModel;
import ViewModels.ObjectBrowser.RequirementTree.Rows.IterationRequirementRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.engineeringmodeldata.Requirement;

/**
 * The {@linkplain MappingListViewDefinedThingRootRowTreeViewModel} is the {@linkplain TreeModel} for an {@linkplain ElementDefinition} in the {@linkplain MappingListView}
 */
public class MappingListViewDefinedThingRootRowTreeViewModel extends BrowserTreeViewModel<MappingListViewContainerBaseRowViewModel<? extends DefinedThing>>
{
    /**
     * Initializes a new {@linkplain MappingListViewDefinedThingRootRowTreeViewModel}
     * 
     * @param thing the {@linkplain DefinedThing}
     */
    public MappingListViewDefinedThingRootRowTreeViewModel(DefinedThing thing)
    {
        super(thing instanceof ElementDefinition
                ? new MappingListViewElementDefinitionRowViewModel((ElementDefinition)thing) 
                : new MappingListViewRequirementRowViewModel((Requirement)thing));
    }
}
