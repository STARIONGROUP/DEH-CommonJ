/*
 * MappedElementListViewRootViewModel.java
 *
 * Copyright (c) 2020-2022 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-Capella
 *
 * The DEH-Capella is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-Capella is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ViewModels.MappedElementListView.Rows;

import java.util.Collection;

import Reactive.ObservableCollection;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.commondata.DefinedThing;

/**
 * The {@linkplain MappedElementListViewRootViewModel} is the root row view model that contains a collection of {@linkplain MappedElementRowViewModel}
 * 
 * @param <TElement> the type of element the dst adapter works with
 */
public class MappedElementListViewRootViewModel<TElement> implements IHaveContainedRows<MappedElementRowViewModel<DefinedThing, TElement>>
{
    /**
     * The {@linkplain ObservableCollection} of {@linkplain IElementRowViewModel}
     */
    private ObservableCollection<MappedElementRowViewModel<DefinedThing, TElement>> containedRows = new ObservableCollection<>();
        
    /**
     * Gets the contained row the implementing view model has
     * 
     * @return An {@linkplain ObservableCollection} of {@linkplain IElementRowViewModel}
     */
    @Override
    public ObservableCollection<MappedElementRowViewModel<DefinedThing, TElement>> GetContainedRows()
    {
        return this.containedRows;
    }
    
    /**
     * Initializes a new {@linkplain MappedElementListViewRootViewModel}
     * 
     * @param initialCollection the initial {@linkplain Collection} of {@linkplain MappedElementRowViewModel}
     */
    public MappedElementListViewRootViewModel(Collection<MappedElementRowViewModel<DefinedThing, TElement>> initialCollection)
    {
        this.containedRows.addAll(initialCollection);
    }

    /**
     * Computes the contained rows of this view model
     */
    @Override
    public void ComputeContainedRows() 
    {
    	// Added comment to satisfy the code smell raised by the rule 1186.
    	// This method is empty because nothing has to be done there.
    }
}
