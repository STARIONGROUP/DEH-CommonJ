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
package ViewModels.ObjectBrowser.ElementDefinitionTree.Rows;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Reactive.ObservableCollection;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Rows.ThingRowViewModel;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.types.OrderedItemList;

/**
 * The {@linkplain IterationRowViewModel} is the row view model that represents an {@linkplain Iteration}
 */
public class IterationRowViewModel extends ThingRowViewModel<Iteration> implements IHaveContainedRows<ElementDefinitionRowViewModel>
{
    /**
     * The current class logger
     */
    private final Logger logger = LogManager.getLogger();
    
    /**
     * The {@linkplain ObservableCollection} of {@linkplain ElementDefinitionRowViewModel}
     */
//    private ObservableCollection<ElementDefinitionRowViewModel> containedRows = new ObservableCollection<ElementDefinitionRowViewModel>();
    private ArrayList<ElementDefinitionRowViewModel> containedRows = new ArrayList<ElementDefinitionRowViewModel>();
    
    /**
     * Gets the contained row the implementing view model has
     * 
     * @return An {@linkplain ObservableCollection} of {@linkplain TRowViewModel}
     */
    @Override
//    public ObservableCollection<ElementDefinitionRowViewModel> GetContainedRows()
    public ArrayList<ElementDefinitionRowViewModel> GetContainedRows()
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
        this.UpdateProperties();
    }

    /**
     * Updates the implementing view model properties
     */
    @Override
    public void UpdateProperties()
    {
        this.SetName("ElementDefinitions");
        this.ComputeContainedRows();
    }
    
    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
        this.containedRows.clear();
        this.GetThing().getElement()
            .stream()
            .forEach(x -> this.containedRows.add(new ElementDefinitionRowViewModel(x)));
    }
}
