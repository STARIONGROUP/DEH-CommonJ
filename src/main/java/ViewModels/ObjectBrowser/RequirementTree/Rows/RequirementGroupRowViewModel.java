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

import java.util.List;

import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import cdp4common.engineeringmodeldata.Requirement;
import cdp4common.engineeringmodeldata.RequirementsGroup;

/**
 * The {@linkplain RequirementGroupRowViewModel} is the row view model that represents {@linkplain RequirementsGroup}
 */
public class RequirementGroupRowViewModel extends RequirementContainerRowViewModel<RequirementsGroup>
{
    /**
     * The collection of {@linkplain Requirement} that possibly belong to the represented {@linkplain RequirementGroupRowViewModel}
     */
    private List<Requirement> requirements;

    /**
     * Initializes a new {@linkplain RequirementGroupRowViewModel}
     * 
     * @param requirementElement the represented {@linkplain RequirementsGroup}
     * @param requirements the collection of {@linkplain Requirement} that possibly belong to the represented {@linkplain RequirementGroupRowViewModel}
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    public RequirementGroupRowViewModel(RequirementsGroup requirementElement, List<Requirement> requirements, IRowViewModel parentViewModel)
    {
        super(requirementElement, parentViewModel);
        this.requirements = requirements;
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
     * Computes this view model {@linkplain containedRows}
     */
    @Override
    public void ComputeContainedRows()
    {
        super.ComputeContainedRows();
        
        this.requirements
            .stream()
            .filter(x -> x.getGroup() != null)
            .filter(x -> x.getGroup().getIid().equals(this.GetThing().getIid()))
            .filter(x -> !x.isDeprecated())
            .forEach(x -> this.containedRows.add(new RequirementRowViewModel(x, this)));

        this.GetThing().getGroup()
            .stream()
            .filter(x -> x.getContainerOfType(RequirementsGroup.class) == this.GetThing())
            .forEach(x -> this.containedRows.add(new RequirementGroupRowViewModel(x, requirements, this)));
    }
}
