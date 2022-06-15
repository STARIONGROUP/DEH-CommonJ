/*
 * RequirementCellComponent.java
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
package Views.MappingList.CellComponents;

import javax.swing.JTextArea;

import ViewModels.MappingListView.Rows.MappingListViewContainerBaseRowViewModel;

/**
* The {@linkplain RequirementCellComponent} is used to render cells in the mapping list view that represents a requirement
*/
@SuppressWarnings("serial")
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class RequirementCellComponent extends BaseCellComponent
{    
   /**
    * Initializes a new {@linkplain ElementCellComponent}
    * 
    * @param rowViewModel the {@linkplain MappingListViewContainerBaseRowViewModel}
    */
   public RequirementCellComponent(MappingListViewContainerBaseRowViewModel<?> rowViewModel)
   {
       super();
       this.Update(rowViewModel);
   }

   /**
    * Updates the components values
    * 
    * @param rowViewModel the {@linkplain MappingListViewContainerBaseRowViewModel}
    */
   @Override
   public void Update(MappingListViewContainerBaseRowViewModel<?> rowViewModel)
   {
       this.Update(rowViewModel.GetName(), rowViewModel.GetClassKind());
       
       JTextArea textArea;
       
       if(this.valueContainer.getComponents().length == 1 && this.valueContainer.getComponents()[0] instanceof JTextArea)
       {
           textArea = (JTextArea) this.valueContainer.getComponents()[0];
       }
       else
       {
           textArea = new JTextArea(5, 50);
           textArea.setLineWrap(true);
           textArea.setEditable(false);
           this.valueContainer.add(textArea);
       }
       
       textArea.setText(rowViewModel.GetValue());
   }
}