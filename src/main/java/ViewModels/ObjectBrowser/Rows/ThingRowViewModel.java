/*
 * ThingRowViewModel.java
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
package ViewModels.ObjectBrowser.Rows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.ObjectBrowser.Interfaces.IThingRowViewModel;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.Iteration;

/**
 * The {@linkplain ThingRowViewModel} is a base row view model for all {@linkplain Thing}
 * 
 * @param TThing the type of {@linkplain Thing}
 */
public abstract class ThingRowViewModel<TThing extends Thing> implements IRowViewModel, IThingRowViewModel<TThing>
{
    /**
     * The current class logger
     */
    protected final Logger logger = LogManager.getLogger();
    
    /**
     * The parent row view model of the current row
     */
    private IRowViewModel parent;
    
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
     * The {@linkplain Thing}
     */
    private TThing thing;
    
    /**
     * Gets the {@linkplain Thing} that this view model represents
     * 
     * @return a {@linkplain TThing}
     */
    public TThing GetThing()
    {
        return this.thing;
    }
    
    /**
     * The {@linkplain String} name
     */
    private String name;

    /**
     * The value indicating whether this row should be highlighted as "selected for transfer"
     */
    private boolean isSelected;
    
    /**
     * Switches between the two possible values for the {@linkplain isSelected}
     * 
     * @return the new {@linkplain boolean} value
     */
    @Override
    public boolean SwitchIsSelectedValue()
    {
        return this.isSelected = !this.isSelected;
    }
    
    /**
     * Sets a value whether this row is selected
     * 
     * @param isSelected the {@linkplain boolean} value
     */
    @Override
    public void SetIsSelected(boolean isSelected)
    {
        this.isSelected = isSelected;
    }
    
    /**
     * Gets a value indicating whether this row should be highlighted as "selected for transfer"
     * 
     * @return a {@linkplain boolean}
     */
    @Override
    public boolean GetIsSelected()
    {
        return this.isSelected;
    }
    
    /**
     * The value indicating whether this row should be highlighted in the tree
     */
    private boolean isHighlighted;
    
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
    
    /**
     * Gets the {@linkplain name} of the represented {@linkplain TThing} to display 
     * 
     * @return a {@linkplain String}
     */
    public String GetName()
    {
        return this.name;
    }    
    
    /**
     * Sets the {@linkplain name} of the represented {@linkplain TThing} to display 
     * 
     * @param name a {@linkplain String}
     */
    public void SetName(String name)
    {
        this.name = name;
    }
    
    /**
     * Initializes a new {@linkplain ThingRowViewModel}
     * 
     * @param thing the {@linkplain TThing}
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    protected ThingRowViewModel(TThing thing, IRowViewModel parentViewModel)
    {
        this.thing = thing;
        this.parent = parentViewModel;
        
        try
        {
            this.isHighlighted = !(this.GetThing() instanceof Iteration) && this.GetThing().getOriginal() != null || this.GetThing().getRevisionNumber() == 0;
        }
        catch(Exception exception)
        {
            this.logger.error(String.format("Failed to set is highlighted for row %s", thing));
            this.logger.catching(exception);
        }
    }
    
    /**
     * Updates the reference to the represented thing and highlights the row and recompute the properties
     * 
     * @param thing the new {@linkplain Thing}
     */
    public void UpdateThing(TThing thing)
    {
        this.logger.debug(String.format("UpdateThing for %s", this.GetName()));
        this.thing = thing;
        this.isHighlighted = true;
        this.UpdateProperties();
    }
    
    /**
     * Updates the implementing view model properties
     */
    protected abstract void UpdateProperties();
}
