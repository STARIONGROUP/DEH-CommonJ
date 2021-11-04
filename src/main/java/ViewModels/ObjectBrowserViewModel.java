/*
 * ObjectBrowserViewModel.java
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
package ViewModels;

import java.util.List;

import javax.swing.tree.TreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.swing.outline.OutlineModel;

import HubController.IHubController;
import Reactive.ObservableValue;
import ViewModels.Interfaces.IObjectBrowserViewModel;
import ViewModels.ObjectBrowser.Interfaces.IThingRowViewModel;
import Views.ObjectBrowser.ObjectBrowser;
import cdp4common.commondata.Thing;
import io.reactivex.Observable;

/**
 * The {@linkplain ObjectBrowserViewModel} is the main view model for the {@linkplain ObjectBrowser} providing the collections to display in the {@linkplain ObjectBrowser}
 */
public abstract class ObjectBrowserViewModel implements IObjectBrowserViewModel
{
    /**
     * The current class logger
     */
    protected final Logger logger = LogManager.getLogger();
    
    /**
     * The {@link IHubController}
     */
    protected IHubController hubController;
    
    /**
     * An {@linkplain ObservableValue} of {@linkplain Boolean} indicating whether the tree should get a refresh
     */
    protected ObservableValue<Boolean> shouldRefreshTree = new ObservableValue<Boolean>(false, Boolean.class);
    
    /**
     * Gets the {@linkplain ObservableValue} of {@linkplain Boolean} indicating whether the tree should get a refresh
     * 
     * @return an {@linkplain ObservableValue} of {@linkplain Boolean}
     */
    @Override
    public Observable<Boolean> GetShouldRefreshTree()
    {
        return this.shouldRefreshTree.Observable();
    }
    
    /**
     * The {@linkplain ObservableValue} of {@linkplain OutlineModel} for the element definition tree
     */
    protected ObservableValue<OutlineModel> browserTreeModel = new ObservableValue<OutlineModel>(OutlineModel.class);
    
    /***
     * Gets the {@linkplain OutlineModel} for the element definition tree view
     * 
     * @return An {@linkplain Observable} of {@linkplain OutlineModel}
     */
    @Override
    public Observable<OutlineModel> BrowserTreeModel()
    {
        return browserTreeModel.Observable();
    }
        
    /**
     * The {@linkplain ObservableValue} of {@linkplain Boolean} for the {@linkplain IsTheTreeVisible}
     */
    protected ObservableValue<Boolean> isTheTreeVisible = new ObservableValue<Boolean>(false, Boolean.class);
    
    /**
     * Gets the a value indicating whether the tree should be visible
     * 
     * @return a {@linkplain Boolean}
     */
    @Override
    public Observable<Boolean> IsTheTreeVisible()
    {
        return isTheTreeVisible.Observable();
    }
    
    /**
     * Initializes a new {@link HubLoginViewModel}
     * 
     * @param hubController the {@linkplain IHubController}
     */
    public ObjectBrowserViewModel(IHubController hubController)
    {
        this.hubController = hubController;
        
        this.hubController.GetIsSessionOpenObservable()
            .subscribe(x -> this.UpdateBrowserTrees(x), x -> this.logger.catching(x));
        
        this.hubController.GetSessionEventObservable()
            .filter(x -> x)
            .subscribe(
                x -> this.UpdateBrowserTrees(x), 
                x -> this.logger.error(String.format("An error occured while listening for session event: %s", x)));
    }

    /**
     * Updates this view model {@linkplain TreeModel}
     * 
     * @param isConnected a value indicating whether the session is open
     */
    protected abstract void UpdateBrowserTrees(Boolean isConnected);

    /**
     * Compute eligible rows where the represented {@linkplain Thing} can be transfered,
     * and return the filtered collection for feedback application on the tree
     * 
     * @param selectedRows the collection of selected view model {@linkplain IThingRowViewModel}
     * @return a {@linkplain List} of {@linkplain IThingRowViewModel}
     */
    @Override
    public void OnSelectionChanged(IThingRowViewModel<?> selectedRow) { }
}
