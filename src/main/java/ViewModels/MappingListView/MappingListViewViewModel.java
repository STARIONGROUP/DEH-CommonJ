/*
 * MappingListViewViewModel.java
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
package ViewModels.MappingListView;

import DstController.IDstControllerBase;
import HubController.IHubController;
import ViewModels.ObjectBrowserBaseViewModel;
import ViewModels.MappingListView.Interfaces.IMappingListViewViewModel;
import ViewModels.MappingListView.Renderers.MappingListViewCellRendererService;
import ViewModels.MappingListView.Rows.MappingRowViewModel;
import io.reactivex.Observable;

/**
 * The {@linkplain MappingListViewViewModel} is the tree view model for the {@linkplain MappingListView}
 */
public abstract class MappingListViewViewModel<TDstController extends IDstControllerBase<?>> 
    extends ObjectBrowserBaseViewModel<MappingRowViewModel<?,?>> implements IMappingListViewViewModel
{
    /**
     * The {@linkplain #TDstController}
     */
    protected final TDstController dstController;
    
    /**
     * The {@linkplain IHubController}
     */
    protected final IHubController hubController;
    
    /**
     * Initializes a new {@linkplain MappingListViewViewModel}
     * 
     * @param dstController the {@linkplain #TDstController}
     * @param hubController the {@linkplain IHubController}
     */
    protected MappingListViewViewModel(TDstController dstController, IHubController hubController)
    {
        this.dstController = dstController;
        this.hubController = hubController;
        
        this.InitializeObservable();
    }
    
    /**
     * Initializes this view model {@linkplain Observable}s
     */
    protected void InitializeObservable()
    {        
        this.dstController.GetDstMapResult().ItemsAdded()
            .subscribe(x -> this.UpdateBrowserTrees(this.GetShouldDisplayTree()));
        
        this.dstController.GetHubMapResult().ItemsAdded()
            .subscribe(x -> this.UpdateBrowserTrees(this.GetShouldDisplayTree()));

        this.dstController.GetDstMapResult().IsEmptyObservable()
            .subscribe(x -> this.UpdateBrowserTrees(this.GetShouldDisplayTree()));
        
        this.dstController.GetHubMapResult().IsEmptyObservable()
            .subscribe(x -> this.UpdateBrowserTrees(this.GetShouldDisplayTree()));
    }

    /**
     * Gets a value indicating whether the tree should displayed
     * 
     * @return a {@linkplain Boolean}
     */
    private Boolean GetShouldDisplayTree()
    {
        boolean result = this.hubController.GetIsSessionOpen() && 
                (!this.dstController.GetDstMapResult().isEmpty() || !this.dstController.GetHubMapResult().isEmpty());
        
        if(!result)
        {
            MappingListViewCellRendererService.Current.Clear();
        }
        
        return result;
    }
    
    /**
     * Gets the {@linkplain Observable} of {@linkplain #TRowViewModel} that yields the selected element
     * 
     * @return an {@linkplain Observable} of {@linkplain #TRowViewModel}
     */
    @Override
    public Observable<MappingRowViewModel<?, ?>> GetSelectedElement()
    {
        return null;
    }

    @Override
    public void OnSelectionChanged(MappingRowViewModel<?,?> selectedRow) { }
}
