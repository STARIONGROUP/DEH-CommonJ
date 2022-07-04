/*
 * MappedElementListViewBaseCellRenderer.java
 *
 * Copyright (c) 2020-2022 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski, Antoine Théate
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
package Renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import com.google.common.collect.HashBasedTable;

import ViewModels.Rows.MappedElementRowViewModel;

/**
 * The {@linkplain MappedElementListViewBaseRenderer} for {@linkplain MappedElementListViewCellRenderer}
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class MappedElementListViewBaseCellRendererProvider
{
    /**
     * Static final instance of this provider
     */
    public static final MappedElementListViewBaseCellRendererProvider Current = new MappedElementListViewBaseCellRendererProvider();
    
    /**
     * Local cache of components use to render cells
     */
    private HashBasedTable<Integer, Integer, JComponent> componentCache = HashBasedTable.create();
    
    /**
     * Returns the component used for drawing the cell. This method is
     * used to configure the renderer appropriately before drawing.
     * 
     * @param table the {@linkplain JTable} instance
     * @param cellValue the value
     * @param isSelected a value indicating whether the current row is selected
     * @param hasFocus a value indicating whether the current row has focus
     * @param row the row number
     * @param columnIndex the column number
     * @return a {@linkplain Component}
     */
    public Component GetComponent(JTable table, Object cellValue, boolean isSelected, int rowIndex, int columnIndex)
    {
        JLabel component = (JLabel) componentCache.get(rowIndex, columnIndex);
                
        if(component == null)
        {
            component = new JLabel();
            component.setOpaque(true);
            component.setBackground(Color.WHITE);
            component.setHorizontalAlignment(SwingConstants.CENTER);
            componentCache.put(rowIndex, columnIndex, component);
        }
                
        this.UpdateVisualState(component, isSelected, table.getModel().getValueAt(rowIndex, 0));
        
        component.setText(cellValue.toString());
        
        return component;
    }

    /**
     * Updates the provided {@linkplain MappedElementRowViewModel} row selection status
     *  
     * @param container the {@linkplain Component} that needs to be updated
     * @param isSelected a value indicating how the container background will be updated
     * @param row the {@linkplain Object} row view model
     */
    protected void UpdateVisualState(Component container, boolean isSelected, Object row)
    {
        if(row instanceof MappedElementRowViewModel)
        {
            this.UpdateRowStatusFor(container, (MappedElementRowViewModel<?, ?>)row);   
        }
        
        this.UpdateRowBackground(container, isSelected);
    }

    /**
     * Updates the the provided {@linkplain MappedElementRowViewModel} row selection status
     * 
     * @param container the {@linkplain Component} that needs to be updated
     * @param isSelected a value indicating how the container background will be updated
     */
    private void UpdateRowBackground(Component container, boolean isSelected)
    {
        if(isSelected)
        {
            container.setBackground(new Color(104, 143, 184));
        }
        else
        {
            container.setBackground(Color.WHITE);
        }
    }
    
    /**
     * Updates the provided {@linkplain MappedElementRowViewModel} row status
     * 
     * @param container the {@linkplain Component} that needs to be updated
     * @param rowViewModel the {@linkplain MappedElementRowViewModel} row view model
     */
    private void UpdateRowStatusFor(Component container, MappedElementRowViewModel<?, ?> rowViewModel)
    {
        if(rowViewModel.GetRowStatus() != null)
        {
            switch(rowViewModel.GetRowStatus())
            {
                case ExisitingElement:
                    container.setForeground(Color.decode("#17418f"));
                    container.setForeground(Color.decode("#17418f"));
                    break;
                case ExistingMapping:
                    container.setForeground(Color.decode("#a8601d"));
                    container.setForeground(Color.decode("#a8601d"));
                    break;
                case NewElement:
                    container.setForeground(Color.decode("#226b1e"));
                    container.setForeground(Color.decode("#226b1e"));
                    break;
                default:
                    container.setForeground(Color.BLACK);
                    container.setForeground(Color.BLACK);
                    break;
            }
        }
    }
    
    /**
     * Clears the cache of Components managed by this provider
     */
    public void ClearCache()
    {
        this.componentCache.clear();
    }
}
