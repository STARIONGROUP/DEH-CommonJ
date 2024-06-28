/*
 * ElementDefinitionRowViewModel.java
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
package ViewModels.ObjectBrowser.ElementDefinitionTree.Rows;

import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters.ParameterGroupRowViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters.ParameterRowViewModel;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import cdp4common.engineeringmodeldata.ElementDefinition;

/**
 * The {@linkplain ElementDefinitionRowViewModel} is the main row view model for {@linkplain ElementDefinition}
 */
public class ElementDefinitionRowViewModel extends ElementBaseRowViewModel<ElementDefinition>
{
    /**
     * Initializes a new {@linkplain ElementDefinitionRowViewModel}
     * 
     * @param elementDefinition the represented {@linkplain ElementDefinition}
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    public ElementDefinitionRowViewModel(ElementDefinition elementDefinition, IRowViewModel parentViewModel)
    {
        super(elementDefinition, parentViewModel);
        this.UpdateProperties();
    }
    
    /**
     * Updates this view model properties
     */
    @Override
    public void UpdateProperties()
    {
        super.UpdateProperties();
        this.ComputeContainedRows();
    }

    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
        this.containedRows.clear();
        
        this.GetThing().getParameterGroup()
            .stream()
            .filter(x -> x.getContainingGroup() == null)
            .forEach(x -> this.containedRows.add(new ParameterGroupRowViewModel(x, null, this)));
        
        this.GetThing().getParameter()
            .stream()
            .filter(x -> x.getGroup() == null)
            .forEach(x -> this.containedRows.add(new ParameterRowViewModel(x, this)));
        
        this.GetThing().getContainedElement()
            .stream()
            .forEach(x -> this.containedRows.add(new ElementUsageRowViewModel(x, this)));
    }
}
