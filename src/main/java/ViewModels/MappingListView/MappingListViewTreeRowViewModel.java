/*
 * MappingListViewTreeRowViewModel.java
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
package ViewModels.MappingListView;

import org.netbeans.swing.outline.RowModel;

import Enumerations.MappingDirection;
import ViewModels.MappingListView.Rows.MappingRowViewModel;
import ViewModels.Rows.BaseTreeRowModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.engineeringmodeldata.ElementDefinition;

/**
 * The {@linkplain MappedElementListViewTreeRowViewModel} is the {@linkplain RowModel} implementation for the {@linkplain MappingListViewViewModel}
 * 
 * @param <TDstElement> the type of DST element that maps to an {@linkplain ElementDefinition}
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class MappingListViewTreeRowViewModel<TDstElement> extends BaseTreeRowModel implements RowModel
{
    /**
     * The {@linkplain Class} of {@linkplain #TDstElement}
     */
    private Class<TDstElement> dstElementType;
    
    /**
     * Initializes a new {@linkplain MappingListViewTreeRowViewModel}
     * 
     * @param dstElementType the {@linkplain Class} of {@linkplain #TDstElement}
     */
    public MappingListViewTreeRowViewModel(Class<TDstElement> dstElementType)
    {
        this.dstElementType = dstElementType;
    }
    
    /**
     * Gets a value indicating whether the specified (by the provided {@linkplain node} and {@linkplain column}) cell is editable
     * 
     * @param node the row view model
     * @param column the column index
     * @return a {@linkplain boolean}
     */
    @Override
    public boolean isCellEditable(Object node, int column)
    {
        if(column == 0 || column == 2)
        {
            return true;
        }
        
        return super.isCellEditable(node, column);
    }
    
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
        if(rowViewModel instanceof MappingRowViewModel)
        {
            MappingRowViewModel<?,?> row = (MappingRowViewModel<?,?>)rowViewModel;
            
            switch (column)
            {
                case 0 : return row.GetDstElement();
                case 1 : return row.GetMappingDirection() == MappingDirection.FromHubToDst ? "<html><body>&#x1F870;</body></html>" : "<html><body>&#x1F872;</body></html>";
                case 2 : return row.GetHubElement();
                default : return "";
            }
        }
        
        return "";
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
            case 0 : return this.dstElementType;
            case 1 : return String.class;
            case 2 : return DefinedThing.class;
            default : return null;
        }
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
            case 0 : return "DST";
            case 1 : return "";
            case 2 : return "HUB";
            default : return null;
        }
    }
}