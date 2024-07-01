/*
 * ThingRowViewModel.java
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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.ObjectBrowser.Interfaces.IThingRowViewModel;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain ThingRowViewModel} is a base row view model for all {@linkplain Thing}
 * 
 * @param TThing the type of {@linkplain Thing}
 */
public abstract class ThingRowViewModel<TThing extends Thing> extends RowViewModel implements IRowViewModel, IThingRowViewModel<TThing>
{
    /**
     * The current class logger
     */
    protected final Logger logger = LogManager.getLogger();
    
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
