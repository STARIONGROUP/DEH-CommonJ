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
import javax.swing.event.TableModelEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.RowModel;

import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters.ParameterValueBaseRowViewModel;
import ViewModels.ObjectBrowser.Rows.IterationRowViewModel;
import ViewModels.ObjectBrowser.Rows.OwnedDefinedThingRowViewModel;
import cdp4common.commondata.ClassKind;

public class ElementDefinitionBrowserTreeRowViewModel implements RowModel
{
//    public Outline tree;
//
//    /** listener on node properties changes, recreates displayed data */
//    private PropertyChangeListener pcl = new PropertyChangeListener() {
//        @Override
//        public void propertyChange(PropertyChangeEvent evt)
//        {
//            if (!SwingUtilities.isEventDispatchThread()) {
//                SwingUtilities.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        propertyChange(evt);
//                    }
//                });
//                return;
//            }
//            
//            tree.tableChanged(new TableModelEvent(tree.getModel(), TableModelEvent.HEADER_ROW, row,
//                            TableModelEvent.ALL_COLUMNS, TableModelEvent.UPDATE));
//            
//        }
//    };
//    
    /**
     * The current class logger
     */
    private final Logger logger = LogManager.getLogger();
    
    /**
     * Gets column count for this tree grid needed to generate all the specified columns and also to compute rows values 
     * 
     * @return the total number of column
     */
    @Override
    public int getColumnCount()
    {
        return 11;
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
                case 0 : return "";
                case 1 : return element.GetOwnerShortName();
                case 2 : return element.GetPublishedValue();
                case 3 : return element.GetScale();
                case 4 : return element.GetValueSwitch();
                case 5 : return element.GetComputedValue();
                case 6 : return element.GetManualValue();
                case 7 : return element.GetReferenceValue();
                case 8 : return element.GetFormula();
                case 9 : return element.GetModelCode();
                case 10 : return element.GetThing().getClassKind();
                
                default : assert false;
            }
        }
        
        else if(rowViewModel instanceof OwnedDefinedThingRowViewModel<?>)
        {
            OwnedDefinedThingRowViewModel<?> element = (OwnedDefinedThingRowViewModel<?>) rowViewModel;
            
            switch (column)
            {
                case 0 : return "";
                case 1 : return element.GetOwnerShortName();
                case 2 : return "";
                case 3 : return "";
                case 4 : return "";
                case 5 : return "";
                case 6 : return "";
                case 7 : return "";
                case 8 : return "";
                case 9 : return element.GetModelCode();
                case 10 : return element.GetThing().getClassKind();
                
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
            case 3 : return String.class;
            case 4 : return String.class;
            case 5 : return String.class;
            case 6 : return String.class;
            case 7 : return String.class;
            case 8 : return String.class;
            case 9 : return String.class;
            case 10 : return ClassKind.class;
            default : assert false;
        }
        
        return null;
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
        return false;
    }


    /**
     * Sets the value provided by {@linkplain value} to the node view model, typically it should call a setter on the row view model
     * 
     * @param node the row view model
     * @param column the column index
     */
    @Override
    public void setValueFor(Object node, int column, Object value) { }

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
            case 0 : return "Option";
            case 1 : return "Owner";
            case 2 : return "Published Value";
            case 3 : return "Scale";
            case 4 : return "Switch";
            case 5 : return "Computed";
            case 6 : return "Manual";
            case 7 : return "Reference";
            case 8 : return "Formula";
            case 9 : return "Model Code";
            case 10 : return "Row Type";
            default : assert false;
        }
        
        return null;
    }
}
