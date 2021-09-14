/*
 * RequirementGroupRowViewModel.java
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
package ViewModels.ObjectBrowser.RequirementTree.Rows;

import cdp4common.engineeringmodeldata.RequirementsContainer;
/**
 * The {@linkplain RequirementsContainerRowViewModel} is the abstract view model that represents a {@linkplain RequirementsContainer}, 
 * It has 2 specializations {@linkplain RequirementGroupRowViewModel}, {@linkplain RequirementSpecificationRowViewModel}
 */
public class RequirementContainerRowViewModel<TRequirementElement extends RequirementsContainer> extends RequirementBaseTreeElementViewModel<TRequirementElement>
{ 
    /**
     * Initializes a new {@linkplain RequirementContainerRowViewModel} 
     * 
     * @param requirementElement
     */
    protected RequirementContainerRowViewModel(TRequirementElement requirementElement)
    {
        super(requirementElement);
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
    }
}
