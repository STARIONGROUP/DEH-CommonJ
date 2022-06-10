/*
 * MappingListViewRequirementRowViewModel.java
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
package ViewModels.MappingListView.Rows.HubRows;

import ViewModels.MappingListView.Rows.MappingListViewContainerBaseRowViewModel;
import cdp4common.commondata.ClassKind;
import cdp4common.engineeringmodeldata.Requirement;

/**
 * The {@linkplain MappingListViewRequirementRowViewModel} is the row view model that represents one {@linkplain Requirement} in a {@linkplain MappingListView}
 */
public class MappingListViewRequirementRowViewModel extends MappingListViewContainerBaseRowViewModel<Requirement>
{    
    /**
     * Initializes a new {@linkplain MappingListViewElementDefinitionRowViewModel}
     * 
     * @param requirement the represented {@linkplain Requirement}
     */
    public MappingListViewRequirementRowViewModel(Requirement requirement)
    {
        super(requirement, requirement.getIid().toString(), String.format("%s-%s", requirement.getShortName(), requirement.getName()), 
                requirement.getDefinition().get(0).getContent(), ClassKind.Requirement);
    }
    
    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows() { }
}
