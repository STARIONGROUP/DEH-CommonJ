/*
 * MappingListViewElementBaseCellRenderer.java
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

import java.awt.Component;
import java.util.function.Function;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import ViewModels.MappingListView.Rows.MappingListViewContainerBaseRowViewModel;
import Views.MappingList.MappingListView;
import Views.ObjectBrowser.ObjectBrowserBase;

/**
 * The {@linkplain MappingListViewDefinedThingCellRenderer} is the base cell renderer for elements of the {@linkplain MappingListView}
 * 
 * @param <TElement> the base type of root row view model
 */
@SuppressWarnings("serial")
@Annotations.ExludeFromCodeCoverageGeneratedReport
public abstract class MappingListViewElementBaseCellRenderer<TElement> extends DefaultTableCellRenderer
{
    /**
     * The type of root row view model
     */
    protected transient Function<TElement, MappingListViewContainerBaseRowViewModel<? extends TElement>> viewModelFactory;
    
    /**
     * Initializes a new {@linkplain MappingListViewDefinedThingCellRenderer}
     * 
     * @param rootRowViewModelType the {@linkplain Class} of {@linkplain #TElementRootRowViewModel}
     */
    protected MappingListViewElementBaseCellRenderer(Function<TElement, MappingListViewContainerBaseRowViewModel<? extends TElement>> viewModelFactory)
    {
        this.viewModelFactory = viewModelFactory;
    }

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
     * @return a {@linkplain ObjectBrowserBase}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Component getTableCellRendererComponent(JTable table, Object cellValue, boolean isSelected, boolean hasFocus, int rowIndex, int columnIndex)
    {
        MappingListViewContainerBaseRowViewModel<? extends TElement> viewModel = this.viewModelFactory.apply((TElement)cellValue);        
        return MappingListViewCellRendererService.Current.Get(viewModel);
    }
}
