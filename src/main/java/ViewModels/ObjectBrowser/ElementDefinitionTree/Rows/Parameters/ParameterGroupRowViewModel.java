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
import cdp4common.engineeringmodeldata.ElementUsage;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.ParameterGroup;
import cdp4common.engineeringmodeldata.ParameterOverride;

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
     * The optional {@linkplain ElementUsage} container, in case the represented group row is a child of an Element Usage row
     */
    private ElementUsage elementUsageContainer;

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
     * @param elmentUsage a optional {@linkplain ElementUsage} in case the represented group row is a child of an Element Usage row
     */
    public ParameterGroupRowViewModel(ParameterGroup parameterGroup, ElementUsage elementUsage)
    {
        super(parameterGroup);
        this.elementUsageContainer = elementUsage;
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
        
        if(this.elementUsageContainer != null)
        {
            for (Parameter parameter : this.GetThing().getContainedParameter())
            {
                ParameterOverride parameterOverride = this.elementUsageContainer.getParameterOverride()
                        .stream()
                        .filter(x -> x.getParameter().getIid() == parameter.getIid())
                        .findFirst()
                        .orElse(null);
                
                if(parameterOverride != null)
                {
                    this.containedRows.add(new ParameterOverrideRowViewModel(parameterOverride));
                }
                else
                {
                    this.containedRows.add(new ParameterRowViewModel(parameter));
                }
            }
        }
        else
        {
            this.GetThing().getContainedParameter()
                .stream()
                .forEach(x -> this.containedRows.add(new ParameterRowViewModel(x)));
        }
        this.GetThing().getContainedGroup(false)
            .stream()
            .forEach(x -> this.containedRows.add(new ParameterGroupRowViewModel(x, this.elementUsageContainer)));
    }    
}
