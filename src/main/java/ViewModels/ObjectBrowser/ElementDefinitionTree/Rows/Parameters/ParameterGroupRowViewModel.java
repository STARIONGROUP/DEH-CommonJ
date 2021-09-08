/*
 * ParameterGroupRowViewModel.java
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
package ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters;

import java.util.ArrayList;

import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Rows.OwnedDefinedThingRowViewModel;
import cdp4common.engineeringmodeldata.ParameterGroup;

/**
 * The {@linkplain ParameterGroupRowViewModel} is the row view model that represents {@linkplain ParameterGroup}
 */
public class ParameterGroupRowViewModel extends OwnedDefinedThingRowViewModel<ParameterGroup> implements IHaveContainedRows<OwnedDefinedThingRowViewModel<?>>
{
    /**
     * The {@linkplain ArrayList} of {@linkplain OwnedDefinedThingRowViewModel<?>}
     */
    private ArrayList<OwnedDefinedThingRowViewModel<?>> containedRows = new ArrayList<OwnedDefinedThingRowViewModel<?>>();

    /**
     * Gets the contained row the implementing view model has
     * 
     * @return An {@linkplain ArrayList} of {@linkplain OwnedDefinedThingRowViewModel<?>}
     */
    @Override
    public ArrayList<OwnedDefinedThingRowViewModel<?>> GetContainedRows()
    {
        return this.containedRows;
    }
        
    /**
     * Initializes a new {@linkplain ParameterGroupRowViewModel}
     * 
     * @param parameterGroup the {@linkplain ParameterGroup} that this view model represents
     */
    public ParameterGroupRowViewModel(ParameterGroup parameterGroup)
    {
        super(parameterGroup);
        this.UpdateProperties();
    }
    
    /**
     * Updates the implementing view model properties
     */
    @Override
    public void UpdateProperties()
    {
        this.SetName(this.GetThing().getName());
        this.ComputeContainedRows();
    }
    
    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
        this.containedRows.clear();
        
        this.GetThing().getContainedParameter()
            .stream()
            .forEach(x -> this.containedRows.add(new ParameterRowViewModel(x)));
        
        this.GetThing().getContainedGroup(false)
            .stream()
            .forEach(x -> this.containedRows.add(new ParameterGroupRowViewModel(x)));
    }    
}
