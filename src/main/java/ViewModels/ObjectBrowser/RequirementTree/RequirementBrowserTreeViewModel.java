/*
 * RequirementBrowserTreeViewModel.java
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
package ViewModels.ObjectBrowser.RequirementTree;

import javax.swing.tree.TreeModel;

import HubController.IHubController;
import ViewModels.ObjectBrowser.BrowserTreeViewModel;
import ViewModels.ObjectBrowser.RequirementTree.Rows.IterationRequirementRowViewModel;
import cdp4common.engineeringmodeldata.Iteration;

/**
 * The {@linkplain RequirementBrowserTreeViewModel} is the {@linkplain TreeModel} for the requirement specification browser
 */
public class RequirementBrowserTreeViewModel extends BrowserTreeViewModel<IterationRequirementRowViewModel>
{
    /**
     * Initializes a new {@linkplain RequirementBrowserTreeViewModel}
     * 
     * @param iteration the {@linkplain Iteration}
     * @param hubController the {@linkplain IHubController}
     */
    public RequirementBrowserTreeViewModel(Iteration iteration)
    {
        super(new IterationRequirementRowViewModel(iteration));
    }
}
