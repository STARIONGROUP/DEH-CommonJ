/*
 * RowViewModel.java
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
package ViewModels.ObjectBrowser.Rows;

import Reactive.ObservableValue;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;

/**
 * The {@linkplain RowViewModel} the base abstract class for all row view models
 */
public abstract class RowViewModel implements IRowViewModel
{
    /**
     * The parent row view model of the current row
     */
    protected IRowViewModel parent;
    
    /**
     * Gets the parent row view model of the current row
     * 
     * @return an {@linkplain IRowViewModel}
     */
    public IRowViewModel GetParent()
    {
        return this.parent;
    }

    /**
     * The {@linkplain ObservableValue} of {@linkplain Boolean} indicating whether this row is selected
     */
    protected ObservableValue<Boolean> isSelected = new ObservableValue<>(false, Boolean.class);

    /**
     * Gets the {@linkplain boolean} current value from the {@linkplain ObservableValue} isSelected
     * 
     * @return a {@linkplain Boolean}
     */
    public boolean GetIsSelected()
    {
        return this.isSelected.Value();
    }
    
    /**
     * Sets a value indicating whether this row is selected
     * 
     * @param isSelected the new {@linkplain boolean} value
     */
    public void SetIsSelected(boolean isSelected)
    {
        this.isSelected.Value(isSelected);
    }

    /**
     * Switches between the two possible values for the {@linkplain isSelected}
     * 
     * @return the new {@linkplain boolean} value
     */
    @Override
    public boolean SwitchIsSelectedValue()
    {
        this.isSelected.Value(!this.isSelected.Value());
        return this.isSelected.Value();
    }
    
    /**
     * The value indicating whether this row should be highlighted in the tree
     */
    protected boolean isHighlighted;
    
    /**
     * Sets a value whether this row is highlighted
     * 
     * @param isHighlighted the {@linkplain boolean} value
     */
    @Override
    public void SetIsHighlighted(boolean isHighlighted)
    {
        this.isHighlighted = isHighlighted;
    }
    
    /**
     * Gets a value indicating whether this row should be highlighted in the tree
     * 
     * @return a {@linkplain boolean}
     */
    public boolean GetIsHighlighted()
    {
        return this.isHighlighted;
    }
    
    /**
     * A Value indicating whether the current row is expanded
     */
    private boolean isExpanded;
    
    /**
     * Sets a value indicating whether the current row is expanded
     * 
     * @return a {@linkplain boolean}
     */
    public void SetIsExpanded(boolean isExpanded)
    {
        this.isExpanded = isExpanded;
    }
    
    /**
     * Gets a value indicating whether the current row is expanded
     * 
     * @return a {@linkplain boolean}
     */
    public boolean GetIsExpanded()
    {
        return this.isExpanded;
    }    
}
