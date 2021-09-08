/*
 * ObjectBrowserViewModel.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Geren�, Alex Vorobiev, Nathanael Smiechowski 
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.OutlineModel;

import HubController.IHubController;
import Reactive.ObservableValue;
import ViewModels.Interfaces.IElementDefinitionBrowserViewModel;
import ViewModels.Interfaces.IElementDefinitionRowViewModel;
import ViewModels.Interfaces.IObjectBrowserViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.ElementDefinitionBrowserTreeRowViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.ElementDefinitionBrowserTreeViewModel;
import cdp4dal.CDPMessageBus;
import cdp4dal.events.SessionEvent;
import cdp4dal.events.SessionStatus;
import io.reactivex.Observable;

/**
 * The {@linkplain ObjectBrowserViewModel} is the main view model for the {@linkplain ObjectBrowser} providing the collections to display in the {@linkplain ObjectBrowser}
 */
public class ObjectBrowserViewModel implements IObjectBrowserViewModel
{
    /**
     * The current class logger
     */
    private final Logger logger = LogManager.getLogger();
    
    /**
     * The {@link IHubController}
     */
    private IHubController hubController;
    
    /**
     * The {@linkplain ObservableValue} of {@linkplain OutlineModel} for the element definition tree
     */
    private ObservableValue<OutlineModel> elementDefinitionTree = new ObservableValue<OutlineModel>();
    
    /***
     * Gets the {@linkplain OutlineModel} for the tree view
     * 
     * @return An {@linkplain Observable} of {@linkplain OutlineModel}
     */
    @Override
    public Observable<OutlineModel> ElementDefinitionTree()
    {
        return elementDefinitionTree.Observable();
    }
    
    /** 
     * Initializes a new {@link HubLoginViewModel}
     * 
     * @param hubController the {@linkplain IHubController}
     */
    public ObjectBrowserViewModel(IHubController hubController)
    {
        this.hubController = hubController;
        this.hubController.GetIsSessionOpenObservable().subscribe(x -> this.UpdateElementDefinitionTree(x));
    }

    /**
     * Updates the {@linkplain ElementDefinitionTree}
     * 
     * @param isConnected a value indicating whether the session is open
     */
    private void UpdateElementDefinitionTree(Boolean isConnected)
    {
        if(isConnected)
        {
            elementDefinitionTree.Value(DefaultOutlineModel.createOutlineModel(
                    new ElementDefinitionBrowserTreeViewModel(this.hubController.GetOpenIteration()), 
                    new ElementDefinitionBrowserTreeRowViewModel(), true));
        }
    }    
}
