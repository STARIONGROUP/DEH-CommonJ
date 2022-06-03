/*
 * IDstControllerBase.java
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
package DstController;

import Enumerations.MappingDirection;
import Reactive.ObservableCollection;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.commondata.ClassKind;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain IDstControllerBase} is the base interface definition for DstController implementation on dst adapter
 * 
 * @param <TDstElement> the type of {@linkplain Element} the implementing dst adapter works with
 */
public interface IDstControllerBase<TDstElement>
{
    /**
     * Adds or Removes all {@linkplain TDstElement} from/to the relevant selected things to transfer depending on the current {@linkplain MappingDirection}
     * 
     * @param classKind the {@linkplain ClassKind} of the {@linkplain Thing}s to add or remove depending on which impact view it has been called from
     * @param shouldRemove a value indicating whether the things are to be removed
     */
    void AddOrRemoveAllFromSelectedThingsToTransfer(ClassKind classKind, boolean shouldRemove);

    /**
     * Gets The {@linkplain ObservableCollection} of DST map result
     * 
     * @return an {@linkplain ObservableCollection} of {@linkplain MappedElementRowViewModel}
     */
    ObservableCollection<MappedElementRowViewModel<? extends Thing, ? extends TDstElement>> GetDstMapResult();

    /**
     * Gets The {@linkplain ObservableCollection} of Hub map result
     * 
     * @return an {@linkplain ObservableCollection} of {@linkplain MappedElementRowViewModel}
     */
    ObservableCollection<MappedElementRowViewModel<? extends Thing, ? extends TDstElement>> GetHubMapResult();
}
