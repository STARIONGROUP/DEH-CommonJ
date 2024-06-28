/*
 * ImpactViewContextMenuViewModel.java
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
package ViewModels;

import DstController.IDstControllerBase;
import ViewModels.Interfaces.IImpactViewContextMenuViewModel;
import Views.ContextMenu.ImpactViewContextMenu;
import cdp4common.commondata.ClassKind;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain ImpactViewContextMenuViewModel} is the main view model for the {@linkplain ImpactViewContextMenu}
 */
public class ImpactViewContextMenuViewModel implements IImpactViewContextMenuViewModel
{
    /**
     * The {@linkplain IDstControllerBase}
     */
    private IDstControllerBase<?> dstController;
        
    /**
     * Initializes a new {@linkplain ImpactViewContextMenuViewModel}
     */
    public ImpactViewContextMenuViewModel(IDstControllerBase<?> dstController)
    {
        this.dstController = dstController;
    }
    
    /**
     * Selects all things that can be transfered in the target view model
     * 
     * @param classKind the {@linkplain ClassKind} of the {@linkplain Thing}s to add or remove depending on which impact view it has been called from
     */
    @Override
    public void SelectAllOfType(ClassKind classKind)
    {
        this.dstController.AddOrRemoveAllFromSelectedThingsToTransfer(classKind, false);
    }
    
    /**
     * Deselects all things that can be transfered in the target view model
     * 
     * @param classKind the {@linkplain ClassKind} of the {@linkplain Thing}s to add or remove depending on which impact view it has been called from
     */
    @Override
    public void DeselectAllOfType(ClassKind classKind)
    {
        this.dstController.AddOrRemoveAllFromSelectedThingsToTransfer(classKind, true);
    }
}
