/*
 * MappingListViewElementDefinitionRowViewModel.java
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

import ViewModels.MappingListView.Rows.MappingListViewBaseRowViewModel;
import ViewModels.MappingListView.Rows.MappingListViewContainerBaseRowViewModel;
import cdp4common.commondata.ClassKind;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.ParameterValueSet;

/**
 * The {@linkplain MappingListViewElementDefinitionRowViewModel} is the row view model that represents one {@linkplain ElementDefinition} in a {@linkplain MappingListView}
 */
public class MappingListViewElementDefinitionRowViewModel extends MappingListViewContainerBaseRowViewModel<ElementDefinition>
{    
    /**
     * Initializes a new {@linkplain MappingListViewElementDefinitionRowViewModel}
     * 
     * @param elementDefinition the represented {@linkplain ElementDefinition}
     */
    public MappingListViewElementDefinitionRowViewModel(ElementDefinition elementDefinition)
    {
        super(elementDefinition, elementDefinition.getIid().toString(), elementDefinition.getName(), null, ClassKind.ElementDefinition);
    }
    
    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
        for (Parameter parameter : this.element.getParameter())
        {
            for (ParameterValueSet parameterValueSet : parameter.getValueSet())
            {
                this.containedRows.add(
                    new MappingListViewBaseRowViewModel(parameter.getIid().toString(), this.GetParameterName(parameter, parameterValueSet),
                            this.GetParameterValue(parameter, parameterValueSet), ClassKind.Parameter));
            }
        }
    }

    /**
     * Computes the parameter value to display based on the provided {@linkplain Parameter} and {@linkplain ParameterValueSet}
     * 
     * @param parameter the {@linkplain Parameter}
     * @param parameterValueSet the {@linkplain ParameterValueSet}
     * @return a {@linkplain String}
     */
    private String GetParameterValue(Parameter parameter, ParameterValueSet parameterValueSet)
    {
        String value = 
                parameterValueSet.getActualValue().size() == 1 
                ? parameterValueSet.getActualValue().get(0)
                : String.format("[%sx%s]", parameterValueSet.getActualValue().size() / parameter.getParameterType().getNumberOfValues(), parameter.getParameterType().getNumberOfValues());
                
        String scale = null;
        
        if(parameter.getScale() != null)
        {
            scale = String.format("[%s]", parameter.getScale().getShortName());
        }
        
        return String.format("%s %s", value, scale != null ? scale : "");        
    }

    /**
     * Computes the parameter value to display based on the provided {@linkplain Parameter} and {@linkplain ParameterValueSet}
     * 
     * @param parameter the {@linkplain Parameter}
     * @param parameterValueSet the {@linkplain ParameterValueSet}
     * @return a {@linkplain String}
     */
    private String GetParameterName(Parameter parameter, ParameterValueSet parameterValueSet)
    {
        return String.format("%s %s %s", parameter.getParameterType().getName(),
                parameterValueSet.getActualOption() != null ? parameterValueSet.getActualOption().getName() : "",
                parameterValueSet.getActualState() != null ? parameterValueSet.getActualState().getName() : "");
    }
}
