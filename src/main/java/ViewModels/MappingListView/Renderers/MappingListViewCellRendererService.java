/*
 * MappingListViewCellRendererService.java
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
package ViewModels.MappingListView.Renderers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import ViewModels.MappingListView.Rows.MappingListViewContainerBaseRowViewModel;
import Views.MappingList.CellComponents.BaseCellComponent;
import Views.MappingList.CellComponents.ElementCellComponent;
import Views.MappingList.CellComponents.RequirementCellComponent;

/**
 * The {@linkplain MappingListViewCellRendererService} is there to provide the cell's renderer components to display in the mapping list view
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class MappingListViewCellRendererService
{
    /**
     * Provided the singleton instance of the {@linkplain MappingListViewCellRendererService}
     */
    public static final MappingListViewCellRendererService Current;
    
    /**
     * Initializes the {@linkplain #Current}
     */
    static
    {
        Current = new MappingListViewCellRendererService();
    }
    
    /**
     * Backing field for {@linkplain #GetCellsComponent}
     */
    private Map<String, BaseCellComponent> cellsComponent = new HashMap<>();
    
    /**
     * Gets the collection of cell's renderer of the thing it represents
     */
    public Collection<BaseCellComponent> GetCellsComponent()
    {
        return Collections.unmodifiableCollection(this.cellsComponent.values());
    }
    
    /**
     * Initializes a new {@linkplain MappingListViewCellRendererService}. Unused, hides the public constructor
     */
    private MappingListViewCellRendererService() { }
    
    /**
     * Gets a BaseCellComponent to render the represented element by the provided {@linkplain MappingListViewContainerBaseRowViewModel}
     * 
     * @param rowViewModel the {@linkplain MappingListViewContainerBaseRowViewModel}
     * @return a {@linkplain JPanel}
     */
    public BaseCellComponent Get(MappingListViewContainerBaseRowViewModel<?> rowViewModel)
    {
        if(this.cellsComponent.containsKey(rowViewModel.GetId()))
        {
            BaseCellComponent panel = this.cellsComponent.get(rowViewModel.GetId());
            panel.Update(rowViewModel);
            return panel;
        }
        
        BaseCellComponent newComponent = this.Initialize(rowViewModel);
        this.cellsComponent.putIfAbsent(rowViewModel.GetId(), newComponent);
        return newComponent;
    }
    
    /**
     * Gets a JPanel to render the represented element by the provided {@linkplain MappingListViewContainerBaseRowViewModel}
     * 
     * @param rowViewModel the {@linkplain MappingListViewContainerBaseRowViewModel}
     * @return a {@linkplain JPanel}
     */
    private BaseCellComponent Initialize(MappingListViewContainerBaseRowViewModel<?> rowViewModel)
    {
        if(rowViewModel.GetClassKind() == cdp4common.commondata.ClassKind.Requirement)
        { 
            return new RequirementCellComponent(rowViewModel);
        }
        
        return new ElementCellComponent(rowViewModel);
    }
    
    /**
     * Clears the collection of components
     */
    public void Clear()
    {
        this.cellsComponent.clear();
    }
}
