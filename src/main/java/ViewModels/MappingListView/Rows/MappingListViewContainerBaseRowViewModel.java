/*
 * MappingListViewContainerBaseRowViewModel.java
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
package ViewModels.MappingListView.Rows;

import Reactive.ObservableCollection;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import cdp4common.commondata.ClassKind;

/**
 * The {@linkplain MappingListViewContainerBaseRowViewModel} is the base {@linkplain MappingListViewBaseRowViewModel} that implements {@linkplain IHaveContainedRows}
 * 
 * @param <TElement> the type of element the implementing row view model represents
 */
public abstract class MappingListViewContainerBaseRowViewModel<TElement> extends MappingListViewBaseRowViewModel implements IHaveContainedRows<MappingListViewBaseRowViewModel>
{
    /**
     * The represented {@linkplain CapellaElement}
     */
    protected TElement element;
    
    /**
     * Backing field for {@linkplain #GetContainedRows()}
     */
    protected  ObservableCollection<MappingListViewBaseRowViewModel> containedRows = new ObservableCollection<>(MappingListViewBaseRowViewModel.class);

    /**
     * Gets the {@linkplain ObservableCollection} of {@linkplain MappingListViewBaseRowViewModel}
     * 
     * @return a {@linkplain ObservableCollection} of {@linkplain MappingListViewBaseRowViewModel}
     */
    @Override
    public ObservableCollection<MappingListViewBaseRowViewModel> GetContainedRows()
    {
        return this.containedRows;
    }
        
    /**
     * Initializes a new {@linkplain MappingListViewCapellaElementRowViewModel}
     * 
     * @param element the represented {@linkplain #TElement}
     * @param id the {@linkplain String} id of the element
     * @param name the {@linkplain String} name of the represented element
     * @param value the {@linkplain String} value of the represented element
     * @param classKind the {@linkplain ClassKind} of the represented element
     */
    protected MappingListViewContainerBaseRowViewModel(TElement element, String id, String name, String value, ClassKind classKind)
    {
        super(id, name, value, classKind);
        this.element = element;
        this.ComputeContainedRows();
    }
}
