/*
 * IterationElementDefinitionRowViewModel.java
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

import ViewModels.ObjectBrowser.Rows.IterationRowViewModel;
import cdp4common.engineeringmodeldata.Iteration;

/**
 * The {@linkplain IterationElementDefinitionRowViewModel} is the row view model for the element definition tree that represents an {@linkplain Iteration}
 */
public final class IterationElementDefinitionRowViewModel extends IterationRowViewModel<ElementDefinitionRowViewModel>
{
    /**
     * Initializes a new {@linkplain IterationElementDefinitionRowViewModel}
     * 
     * @param iteration the represented {@linkplain Iteration}
     */
    public IterationElementDefinitionRowViewModel(Iteration iteration)
    {
        super(iteration);
        this.UpdateProperties();
    }

    /**
     * Updates the implementing view model properties
     */
    @Override
    public void UpdateProperties()
    {
        super.UpdateProperties();
        this.SetName("ElementDefinitions");
    }

    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
        super.ComputeContainedRows();    
        
        this.GetThing().getElement()
            .stream()
            .forEach(x -> this.containedRows.add(new ElementDefinitionRowViewModel(x, this)));
    }    
}
