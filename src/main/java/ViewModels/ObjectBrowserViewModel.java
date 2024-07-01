/*
 * ObjectBrowserViewModel.java
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
package ViewModels;

import java.util.ArrayList;
import java.util.Collection;

import HubController.IHubController;
import Reactive.ObservableValue;
import ViewModels.Interfaces.IObjectBrowserViewModel;
import ViewModels.ObjectBrowser.Rows.ThingRowViewModel;
import Views.ObjectBrowser.ObjectBrowser;
import cdp4common.commondata.Thing;
import io.reactivex.Observable;

/**
 * The {@linkplain ObjectBrowserViewModel} is the main view model for the {@linkplain ObjectBrowser} providing the collections to display in the {@linkplain ObjectBrowser}
 */
public abstract class ObjectBrowserViewModel extends ObjectBrowserBaseViewModel<ThingRowViewModel<Thing>> implements IObjectBrowserViewModel
{
    /**
     * Backing field for {@linkplain #GetSelectedElements()}
     */
    private Collection<ThingRowViewModel<Thing>> selectedElements = new ArrayList<>();
    
    /**
     * Gets the current selection of elements 
     * 
     * @return a {@linkplain Collection} of {@linkplain ThingRowViewModel}
     */
    @Override
    public Collection<ThingRowViewModel<Thing>> GetSelectedElements()
    {
        return this.selectedElements;
    }
    
    /**
     * Backing field for {@linkplain #GetSelectedElement()}
     */
    private ObservableValue<ThingRowViewModel<Thing>> selectedElement = new ObservableValue<>();
    
    /**
     * Gets the {@linkplain Observable} of {@linkplain IElementRowViewModel} that yields the selected element
     * 
     * @return an {@linkplain Observable} of {@linkplain ClassRowViewModel}
     */
    @Override
    public Observable<ThingRowViewModel<Thing>> GetSelectedElement()
    {
        return this.selectedElement.Observable();
    }
    
    /**
     * The {@link IHubController}
     */
    protected IHubController hubController;
        
    /**
     * Initializes a new {@link HubLoginViewModel}
     * 
     * @param hubController the {@linkplain IHubController}
     */
    protected ObjectBrowserViewModel(IHubController hubController)
    {
        this.hubController = hubController;
        
        this.hubController.GetIsSessionOpenObservable()
            .subscribe(x -> this.UpdateBrowserTrees(x), x -> this.logger.catching(x));
        
        if(Boolean.TRUE.equals(this.hubController.GetIsSessionOpen())) 
        {
            this.UpdateBrowserTrees(true);
        }
        
        this.hubController.GetSessionEventObservable()
            .filter(x -> x)
            .subscribe(
                x -> this.UpdateBrowserTrees(x), 
                x -> this.logger.error(String.format("An error occured while listening for session event: %s", x)));
    }

    /**
     * Compute eligible row where the represented {@linkplain Thing} can be transfered,
     * and return the filtered collection for feedback application on the tree
     * 
     * @param selectedRows the collection of selected view model {@linkplain ThingRowViewModel}
     */
    @Override
    public void OnSelectionChanged(ThingRowViewModel<Thing> selectedRow) 
    {
        if(selectedRow != null)
        {
            this.selectedElement.Value(selectedRow);
        }
    }
    
    /**
     * Compute eligible rows where the represented {@linkplain Thing}s can be transfered,
     * and return the filtered collection for feedback application on the tree
     * 
     * @param selectedRows the collection of selected view model {@linkplain ThingRowViewModel}
     */
    public void OnSelectionChanged(Collection<ThingRowViewModel<Thing>> selectedRows)
    {
        this.selectedElements.clear();
        this.selectedElements.addAll(selectedRows);
    }
}
