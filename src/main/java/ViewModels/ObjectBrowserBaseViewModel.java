/*
 * ObjectBrowserBaseViewModel.java
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

import javax.swing.tree.TreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.swing.outline.OutlineModel;

import Reactive.ObservableValue;
import ViewModels.Interfaces.IObjectBrowserBaseViewModel;
import io.reactivex.Observable;

/**
 * The {@linkplain ObjectBrowserBaseViewModel} is the base abstract class for all browser view model that are based on an {@linkplain OutlineModel}
 */
public abstract class ObjectBrowserBaseViewModel implements IObjectBrowserBaseViewModel
{
    /**
     * The current class logger
     */
    protected final Logger Logger = LogManager.getLogger();
    
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
    protected ObservableValue<OutlineModel> BrowserTreeModel = new ObservableValue<OutlineModel>(OutlineModel.class);
    
    /***
     * Gets the {@linkplain OutlineModel} for the element definition tree view
     * 
     * @return An {@linkplain Observable} of {@linkplain OutlineModel}
     */
    @Override
    public Observable<OutlineModel> BrowserTreeModel()
    {
        return BrowserTreeModel.Observable();
    }
    
    /**
     * Convenient method to get the last emitted {@linkplain OutlineModel}
     * 
     * @return an {@linkplain OutlineModel}
     */
    @Override
    public OutlineModel GetBrowserTreeModel()
    {
        return BrowserTreeModel.Value();
    }
        
    /**
     * The {@linkplain ObservableValue} of {@linkplain Boolean} for the {@linkplain IsTheTreeVisible}
     */
    protected ObservableValue<Boolean> IsTheTreeVisible = new ObservableValue<Boolean>(false, Boolean.class);
    
    /**
     * Gets the a value indicating whether the tree should be visible
     * 
     * @return a {@linkplain Boolean}
     */
    @Override
    public Observable<Boolean> IsTheTreeVisible()
    {
        return IsTheTreeVisible.Observable();
    }
    
    /**
     * Updates this view model {@linkplain TreeModel}
     * 
     * @param isConnected a value indicating whether the session is open
     */
    protected abstract void UpdateBrowserTrees(Boolean isConnected);
}
