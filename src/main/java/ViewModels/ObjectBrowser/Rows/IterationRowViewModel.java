/*
 * IterationRowViewModel.java
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
package ViewModels.ObjectBrowser.Rows;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Reactive.ObservableCollection;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.ElementDefinitionRowViewModel;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.types.OrderedItemList;

/**
 * The {@linkplain IterationRowViewModel} is the base abstract row view model that represents an {@linkplain Iteration}
 */
public abstract class IterationRowViewModel<TContainedRows extends IRowViewModel> extends ThingRowViewModel<Iteration> implements IHaveContainedRows<TContainedRows>
{    
    /**
     * The {@linkplain ObservableCollection} of {@linkplain ElementDefinitionRowViewModel}
     */
    protected ArrayList<TContainedRows> containedRows = new ArrayList<TContainedRows>();
    
    /**
     * Gets the contained row the implementing view model has
     * 
     * @return An {@linkplain ObservableCollection} of {@linkplain TRowViewModel}
     */
    @Override
    public ArrayList<TContainedRows> GetContainedRows()
    {
        return this.containedRows;
    }
    
    /**
     * Initializes a new {@linkplain IterationRowViewModel}
     * 
     * @param thing the {@link Iteration} 
     */
    public IterationRowViewModel(Iteration thing)
    {
        super(thing);
    }

    /**
     * Updates the implementing view model properties
     */
    @Override
    public void UpdateProperties()
    {
        this.ComputeContainedRows();
    }
    
    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
        this.containedRows.clear();
    }
}
