/*
 * MappingRootRowViewModel.java
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
package ViewModels.MappingListView.Rows;

import java.util.Collection;

import org.apache.commons.lang3.tuple.Triple;

import Enumerations.MappingDirection;
import Reactive.ObservableCollection;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Rows.RowViewModel;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.Requirement;

/**
 * The {@linkplain MappingRootRowViewModel} is the root row view model for the {@linkplain MappingListView}
 * 
 * @param <TDstElement> the base type of DST element that maps to an {@linkplain ElementDefinition} or a {@linkplain Requirement}
 */
public class MappingRootRowViewModel<TDstElement> extends RowViewModel implements IHaveContainedRows<MappingRowViewModel<TDstElement, ? extends DefinedThing>>
{
    /**
     * The original collection of {@linkplain MappedElementRowViewModel}
     */
    private Collection<Triple<? extends TDstElement, MappingDirection, ? extends Thing>> originalCollection;
    
    /**
     * Backing field for {@linkplain #GetContainedRows()}
     */
    protected  ObservableCollection<MappingRowViewModel<TDstElement, ? extends DefinedThing>> containedRows = new ObservableCollection<>();
    
    /**
     * Gets the {@linkplain ObservableCollection} of {@linkplain MappingListViewBaseRowViewModel}
     * 
     * @return a {@linkplain ObservableCollection} of {@linkplain MappingListViewBaseRowViewModel}
     */
    @Override
    public ObservableCollection<MappingRowViewModel<TDstElement, ? extends DefinedThing>> GetContainedRows()
    {
        return this.containedRows;
    }

    /**
     * Initializes a new {@linkplain MappingRootRowViewModel}
     * 
     * @param mappedElements the {@linkplain Collection} of {@linkplain Triple} of the {@linkplain #TDstElement} the {@linkplain MappingDirection} and the {@linkplain Thing}
     */
    public MappingRootRowViewModel(Collection<Triple<? extends TDstElement, MappingDirection, ? extends Thing>> mappedElements)
    {
        this.originalCollection = mappedElements;
        this.ComputeContainedRows();
    }
    
    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
        for (Triple<? extends TDstElement, MappingDirection, ? extends Thing> mappingRowViewModel : this.originalCollection)
        {
            this.containedRows.add(new MappingRowViewModel<>(mappingRowViewModel));
        }
    }
}
