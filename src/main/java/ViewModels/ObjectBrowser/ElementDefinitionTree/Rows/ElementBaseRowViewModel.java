/*
 * ElementBaseRowViewModel.java
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
package ViewModels.ObjectBrowser.ElementDefinitionTree.Rows;

import java.util.ArrayList;

import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Rows.OwnedDefinedThingRowViewModel;
import cdp4common.engineeringmodeldata.ElementBase;

/**
 * The {@linkplain ElementBaseRowViewModel} is the base row view model for {@linkplain ElementDefinitionRowViewModel} and {@linkplain ElementUsageRowViewModel}
 */
public abstract class ElementBaseRowViewModel<TElement extends ElementBase> extends OwnedDefinedThingRowViewModel<TElement> implements IHaveContainedRows<OwnedDefinedThingRowViewModel<?>>
{
    /**
     * The {@linkplain ArrayList} of {@linkplain OwnedDefinedThingRowViewModel}
     */
    protected ArrayList<OwnedDefinedThingRowViewModel<?>> containedRows = new ArrayList<OwnedDefinedThingRowViewModel<?>>();

    /**
     * Gets the contained row the implementing view model has
     * 
     * @return An {@linkplain ArrayList} of {@linkplain OwnedDefinedThingRowViewModel}
     */
    @Override
    public ArrayList<OwnedDefinedThingRowViewModel<?>> GetContainedRows()
    {
        return this.containedRows;
    }
    
    /**
     * Initializes a new {@linkplain ElementBaseRowViewModel}
     * 
     * @param elementBase the represented {@linkplain ElementBase}
     */
    protected ElementBaseRowViewModel(TElement elementBase)
    {
        super(elementBase);
    }
}
