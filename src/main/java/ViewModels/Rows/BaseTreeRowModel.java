/*
 * BaseTreeRowModel.java
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
package ViewModels.Rows;

import org.netbeans.swing.outline.RowModel;

/**
 * The {@linkplain BaseTreeRowModel} provides default implementations for any read-only {@linkplain RowModel}
 */
public abstract class BaseTreeRowModel implements RowModel
{
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
}
