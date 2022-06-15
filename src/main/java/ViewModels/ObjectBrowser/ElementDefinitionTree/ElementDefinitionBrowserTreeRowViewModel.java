/*
 * ElementDefinitionRowViewModel.java
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
package ViewModels.ObjectBrowser.ElementDefinitionTree;

import org.netbeans.swing.outline.RowModel;

import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters.ParameterValueBaseRowViewModel;
import ViewModels.ObjectBrowser.Rows.IterationRowViewModel;
import ViewModels.ObjectBrowser.Rows.OwnedDefinedThingRowViewModel;
import ViewModels.Rows.BaseTreeRowModel;

public class ElementDefinitionBrowserTreeRowViewModel extends BaseTreeRowModel implements RowModel
{    
    /**
     * Gets column count for this tree grid needed to generate all the specified columns and also to compute rows values 
     * 
     * @return the total number of column
     */
    @Override
    public int getColumnCount()
    {
        return 3;
    }

    /**
     * Gets the value for the provided {@linkplain column} and {@linkplain rowViewModel}
     * 
     * @param rowViewModel the row
     * @param column the column
     * @return an {@linkplain Object} holding the value
     */
    @Override
    public Object getValueFor(Object rowViewModel, int column)
    {
        if(rowViewModel instanceof IterationRowViewModel)
        {
            return "";
        }

        if(rowViewModel instanceof ParameterValueBaseRowViewModel<?>)
        {
            ParameterValueBaseRowViewModel<?> element = (ParameterValueBaseRowViewModel<?>) rowViewModel;
            
            switch (column)
            {
                case 0 : return element.GetOwnerShortName();
                case 1 : return element.GetPublishedValue();
                case 2 : return element.GetScale();               
                default : assert false;
            }
        }
        
        else if(rowViewModel instanceof OwnedDefinedThingRowViewModel<?>)
        {
            OwnedDefinedThingRowViewModel<?> element = (OwnedDefinedThingRowViewModel<?>) rowViewModel;
            
            switch (column)
            {
                case 0 : return element.GetOwnerShortName();
                case 1 : return "";
                case 2 : return "";
                
                default : assert false;
            }
        }
        
        return "-";
    }

    /**
     * Gets the column {@linkplain Class} for the specified {@linkplain column}
     * 
     * @param column the column index
     * @return the {@linkplain Class}/ type of value the column holds   
     */
    @Override
    public Class<?> getColumnClass(int column)
    {
        switch (column)
        {
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return String.class;
            default : assert false;
        }
        
        return null;
    }
    
    /**
     * Gets the column name based on its index
     * 
     * @param column the column index
     * @return a {@linkplain String} holding the column name
     */
    @Override
    public String getColumnName(int column)
    {
        switch (column)
        {
            case 0 : return "Owner";
            case 1 : return "Published Value";
            case 2 : return "Scale";
            default : assert false;
        }
        
        return null;
    }
}
