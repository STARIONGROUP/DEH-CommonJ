/*
 * MappedElementListViewCellRenderer.java
 *
 * Copyright (c) 2020-2024 Starion Group S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-Capella
 *
 * The DEH-Capella is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-Capella is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package Renderers;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import Views.MappedElementListView;

/**
 * The MappedElementListViewCellRenderer is the {@linkplain TableCellRenderer} for the {@linkplain MappedElementListView}
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class MappedElementListViewCellRenderer  implements TableCellRenderer
{
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
    @Override
    public Component getTableCellRendererComponent(JTable table, Object cellValue, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex)
    {
        return MappedElementListViewBaseCellRendererProvider.Current.GetComponent(table, cellValue, isSelected, rowIndex, columnIndex);
    }
}
