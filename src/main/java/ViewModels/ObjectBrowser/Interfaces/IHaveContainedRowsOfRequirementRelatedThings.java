/*
 * IHaveContainedRowsOfRequirementRelatedThings.java
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
package ViewModels.ObjectBrowser.Interfaces;

import Reactive.ObservableCollection;
import ViewModels.ObjectBrowser.RequirementTree.Rows.RequirementBaseTreeElementViewModel;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain IHaveContainedRowsOfRequirementRelatedThings} is the specialized interface for {@linkplain RequirementBaseTreeElementViewModel} from {@linkplain IHaveContainedRows}
 */
public interface IHaveContainedRowsOfRequirementRelatedThings<TRowViewModel extends RequirementBaseTreeElementViewModel<?> & IRowBaseViewModel> extends IHaveContainedRows<TRowViewModel>
{
    /**
     * Gets all the contained rows of the provided type
     * 
     * @param <TRequirementBaseRowViewModel> the type of rows to get e.g. {@linkplain RequirementRowViewModel} or {@linkplain RequirementGroupRowViewModel}
     * @param clazz the {@linkplain Class} of the {@linkplain TRequirementBaseRowViewModel}
     * @return an {@linkplain ObservableCollection} of {@linkplain TRequirementBaseRowViewModel}
     */
    <TRequirementBaseRowViewModel extends RequirementBaseTreeElementViewModel<?>> ObservableCollection<TRequirementBaseRowViewModel> 
        GetAllContainedRowsOfType(Class<TRequirementBaseRowViewModel> clazz);        
}
