/*
 * IObjectBrowserViewModel.java
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
package ViewModels.Interfaces;

import org.netbeans.swing.outline.OutlineModel;

import Reactive.ObservableValue;
import ViewModels.ObjectBrowser.Interfaces.IThingRowViewModel;
import cdp4common.commondata.Thing;
import io.reactivex.Observable;

/**
 * The {@linkplain IObjectBrowserViewModel} is the interface definition for the {@linkplain ObjectBrowserViewModel} 
 * such as the {@linkplain ElementDefinitionBrowserViewModel} or the {@linkplain RequirementBrowserViewModel}
 */
public interface IObjectBrowserViewModel extends IViewModel
{
    /**
     * Gets the {@linkplain OutlineModel} for the element definition tree view
     * 
     * @return An {@linkplain Observable} of {@linkplain OutlineModel}
     */
    Observable<OutlineModel> BrowserTreeModel();

    /**
     * Gets the a value indicating whether the tree should be visible
     * 
     * @return a {@linkplain Boolean}
     */
    Observable<Boolean> IsTheTreeVisible();

    /**
     * Compute eligible rows where the represented {@linkplain Thing} can be transfered,
     * and return the filtered collection for feedback application on the tree
     * 
     * @param selectedRows the collection of selected view model {@linkplain IThingRowViewModel}
     */
    void OnSelectionChanged(IThingRowViewModel<?> selectedRows);

    /**
     * Gets the {@linkplain ObservableValue} of {@linkplain Boolean} indicating whether the tree should get a refresh
     * 
     * @return an {@linkplain ObservableValue} of {@linkplain Boolean}
     */
    Observable<Boolean> GetShouldRefreshTree();
}
