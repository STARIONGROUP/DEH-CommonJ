/*
 * MappingListViewDefinedThingCellEditor.java
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
package ViewModels.MappingListView.Renderers;

import ViewModels.MappingListView.Rows.HubRows.MappingListViewElementDefinitionRowViewModel;
import ViewModels.MappingListView.Rows.HubRows.MappingListViewRequirementRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.Requirement;

/**
 * The MappingListViewDefinedThingCellEditor is the {@linkplain DefaultTableCellEditor} for the 
 * {@linkplain MappedElementListView} where the represented element is an {@linkplain DefinedThing}
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class MappingListViewDefinedThingCellEditor extends MappingListViewElementBaseCellEditor<DefinedThing>
{
    /**
     * Initializes a new {@linkplain MappingListViewDefinedThingCellRenderer}
     * 
     * @param rootRowViewModelType the type of root row view model
     */
    public MappingListViewDefinedThingCellEditor()
    {
        super(x -> x instanceof ElementDefinition
                ? new MappingListViewElementDefinitionRowViewModel((ElementDefinition)x) 
                : new MappingListViewRequirementRowViewModel((Requirement)x));
    }    
}
