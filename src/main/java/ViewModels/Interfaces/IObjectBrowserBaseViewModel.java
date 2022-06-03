/*
 * IObjectBrowserBaseViewModel.java
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

import javax.swing.ListSelectionModel;

import org.netbeans.swing.outline.OutlineModel;

import ViewModels.ObjectBrowserBaseViewModel;
import ViewModels.ObjectBrowser.Interfaces.IRowBaseViewModel;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import io.reactivex.Observable;

/**
 * The {@linkplain IObjectBrowserBaseViewModel} is the interface definition for the {@linkplain ObjectBrowserBaseViewModel}
 * 
 * @param <TRowViewModel> the type of row view model for the selection mechanisms
 */
public interface IObjectBrowserBaseViewModel<TRowViewModel extends IRowBaseViewModel> extends IViewModel
{
    /**
     * Gets the a value indicating whether the tree should be visible
     * 
     * @return an {@linkplain Observable} of {@linkplain Boolean}
     */
    Observable<Boolean> IsTheTreeVisible();

    /***
     * Gets the {@linkplain OutlineModel} for the element definition tree view
     * 
     * @return An {@linkplain Observable} of {@linkplain OutlineModel}
     */
    Observable<OutlineModel> BrowserTreeModel();

    /**
     * Gets the {@linkplain Observable} of {@linkplain Boolean} indicating whether the tree should get a refresh
     * 
     * @return an {@linkplain Observable} of {@linkplain Boolean}
     */
    Observable<Boolean> GetShouldRefreshTree();

    /**
     * Convenient method to get the last emitted {@linkplain OutlineModel}
     * 
     * @return an {@linkplain OutlineModel}
     */
    OutlineModel GetBrowserTreeModel();

    /**
     * Gets the int value from {@linkplain ListSelectionModel} constant applicable to the bound {@linkplain ObjectBrowser}.
     * Where -1 == no selection allowed
     * 
     * @return a int
     */
    int GetSelectionMode();
    
    /**
     * Gets the {@linkplain Observable} of {@linkplain #TRowViewModel} that yields the selected element
     * 
     * @return an {@linkplain Observable} of {@linkplain #TRowViewModel}
     */
    Observable<TRowViewModel> GetSelectedElement();
    
    /**
     * Called whenever the selection changes on the bound {@linkplain ObjectBrowser}
     * 
     * @param selectedRow the selected row view model {@linkplain TRowViewModel}
     */
    void OnSelectionChanged(TRowViewModel selectedRow);
}
