/*
 * RequirementRowViewModel.java
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

import cdp4common.engineeringmodeldata.Requirement;

/**
 * The {@linkplain RequirementRowViewModel} is the row view model that represents a {@linkplain RequirementRowViewModel} 
 */
public class RequirementRowViewModel extends RequirementBaseTreeElementViewModel<Requirement>
{
    /**
     * Initializes a new {@linkplain RequirementRowViewModel}
     * 
     * @param requirement the represented {@linkplain Requirement}
     */
    public RequirementRowViewModel(Requirement requirement)
    {
        super(requirement);
        this.UpdateProperties();
    }    
}
