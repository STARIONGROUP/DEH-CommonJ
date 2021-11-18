/*
 * BrowserTreeViewModel.java
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
package ViewModels.ObjectBrowser;

import javax.swing.tree.TreeModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import HubController.IHubController;
import ViewModels.ObjectBrowser.Rows.IterationRowViewModel;
import Views.ObjectBrowser.ObjectBrowser;
import cdp4common.engineeringmodeldata.Iteration;

/**
 * The {@linkplain BrowserTreeViewModel} is the base {@linkplain TreeModel} for the {@linkplain ObjectBrowser}
 */
public abstract class BrowserTreeViewModel<TIterationRowViewModel extends IterationRowViewModel<?>> extends BrowserTreeBaseViewModel
{
    /**
     * The current class logger
     */
    protected final Logger Logger = LogManager.getLogger();
    
    /**
     * The root node of one {@linkplain ElementDefinitionBrowserTree}
     */
    private TIterationRowViewModel iterationRowViewModel;

    /**
     * Initializes a new {@linkplain BrowserTreeViewModel}
     * 
     * @param iterationRowViewModel the {@linkplain Iteration} row view model
     * @param hubController the {@linkplain IHubController}
     */
    public BrowserTreeViewModel(TIterationRowViewModel iterationRowViewModel)
    {
        this.iterationRowViewModel = iterationRowViewModel;
    }

    /**
     * Gets the root element of the tree
     * 
     * @return an {@linkplain Object}, representing the root of the tree
     */
    @Override
    public Object getRoot()
    {
        return this.iterationRowViewModel;
    }
}
