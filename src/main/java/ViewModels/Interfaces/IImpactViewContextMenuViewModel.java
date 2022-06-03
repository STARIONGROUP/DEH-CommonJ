/*
 * IImpactViewContextMenuViewModel.java
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
package ViewModels.Interfaces;

import ViewModels.ImpactViewContextMenuViewModel;
import cdp4common.commondata.ClassKind;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain IImpactViewContextMenuViewModel} is the interface definition for the {@linkplain ImpactViewContextMenuViewModel}
 */
public interface IImpactViewContextMenuViewModel extends IContextMenuViewModel
{
    /**
     * Deselects all things that can be transfered in the target view model
     * 
     * @param classKind the {@linkplain ClassKind} of the {@linkplain Thing}s to add depending on which impact view it has been called from
     */
    void DeselectAllOfType(ClassKind classKind);

    /**
     * Selects all things that can be transfered in the target view model
     * 
     * @param classKind the {@linkplain ClassKind} of the {@linkplain Thing}s to remove depending on which impact view it has been called from
     */
    void SelectAllOfType(ClassKind classKind);
}
