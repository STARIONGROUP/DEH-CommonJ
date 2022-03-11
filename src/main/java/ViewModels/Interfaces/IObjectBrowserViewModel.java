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

import java.util.Collection;

import ViewModels.ObjectBrowser.Rows.ThingRowViewModel;
import cdp4common.commondata.Thing;
import io.reactivex.Observable;

/**
 * The {@linkplain IObjectBrowserViewModel} is the interface definition for the {@linkplain ObjectBrowserViewModel} 
 * such as the {@linkplain ElementDefinitionBrowserViewModel} or the {@linkplain RequirementBrowserViewModel}
 */
public interface IObjectBrowserViewModel extends IObjectBrowserBaseViewModel
{
    /**
     * Compute eligible rows where the represented {@linkplain Thing} can be transfered,
     * and return the filtered collection for feedback application on the tree
     * 
     * @param selectedRow the collection of selected view model {@linkplain ThingRowViewModel}
     */
    void OnSelectionChanged(ThingRowViewModel<? extends Thing> selectedRow);

    /**
     * Gets the {@linkplain Observable} of {@linkplain IElementRowViewModel} that yields the selected element
     * 
     * @return an {@linkplain Observable} of {@linkplain ClassRowViewModel}
     */
    Observable<ThingRowViewModel<? extends Thing>> GetSelectedElement();

    /**
     * Compute eligible rows where the represented {@linkplain Thing} can be transfered,
     * and return the filtered collection for feedback application on the tree
     * 
     * @param selectedRows the collection of selected view model {@linkplain ThingRowViewModel}
     */
    void OnSelectionChanged(Collection<ThingRowViewModel<? extends Thing>> selectedRows);

    /**
     * Gets the current selection of elements 
     * 
     * @return a {@linkplain Collection} of {@linkplain ThingRowViewModel}
     */
    Collection<ThingRowViewModel<? extends Thing>> GetSelectedElements();
}
