/*
 * ObjectBrowser.java
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
package Views.ObjectBrowser;

import java.util.ArrayList;

import javax.swing.JTable;

import org.apache.commons.lang3.tuple.Pair;

import ViewModels.Interfaces.IContextMenuViewModel;
import ViewModels.Interfaces.IObjectBrowserViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.ElementDefinitionBrowserTreeViewModel;
import ViewModels.ObjectBrowser.Rows.ThingRowViewModel;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain ObjectBrowser} is the base browser for the
 * {@linkplain ElementDefinitionBrowserTreeViewModel} or the
 * {@linkplain RequirementSepcificationBrowserViewModel}
 * 
 * @param <TRowViewModel> the type of row view model for the selection mechanisms
 */
@SuppressWarnings("serial")
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class ObjectBrowser extends ObjectBrowserBase<IObjectBrowserViewModel, IContextMenuViewModel>
{
    /**
     * Initializes a new {@linkplain ObjectBrowser}
     */
    public ObjectBrowser()
    {
        super();
        this.objectBrowserTree.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    /**
     * Handles the selection when the user changes it, intended to be virtual
     */
    @Override
    protected void OnSelectionChanged()
    {
        this.ProcessSelectedElement();
        this.ProcessSelectedElements();
    }

    /**
     * Processes the currently selected elements
     */
    @SuppressWarnings("unchecked")
    protected void ProcessSelectedElements()
    {
        int[] selectedRowIndexes = objectBrowserTree.getSelectedRows();
        
        ArrayList<ThingRowViewModel<Thing>> selectedThings = new ArrayList<>();
        
        for (int rowIndex : selectedRowIndexes)
        {
            selectedThings.add((ThingRowViewModel<Thing>) objectBrowserTree.getValueAt(rowIndex, 0));
        }

        dataContext.OnSelectionChanged(selectedThings);
    }
    
    /**
     * Processes the currently selected element
     */
    protected void ProcessSelectedElement()
    {
        int selectedRowIndex = objectBrowserTree.getSelectedRow();
        
        @SuppressWarnings("unchecked")
        Pair<Integer, ThingRowViewModel<Thing>> row = Pair.of(selectedRowIndex,
                (ThingRowViewModel<Thing>) objectBrowserTree.getValueAt(selectedRowIndex, 0));

        dataContext.OnSelectionChanged(row.getRight());
    }
}
