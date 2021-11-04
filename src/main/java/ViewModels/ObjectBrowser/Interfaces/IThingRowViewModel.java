/*
 * IThingRowViewModel.java
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
package ViewModels.ObjectBrowser.Interfaces;

import cdp4common.commondata.Thing;

/**
 * The {@linkplain IThingRowViewModel} is the base interface for {@linkplain ThingRowViewModel} that defines that the implementation must provide a GetThing() method
 * 
 * @param TThing the type of thing the {@linkplain ThingRowViewModel} represents
 */
public interface IThingRowViewModel<TThing extends Thing>
{
    /**
     * Gets the {@linkplain Thing} that this view model represents
     * 
     * @return a {@linkplain TThing}
     */
    TThing GetThing();

    /**
     * Sets a value whether this row is highlighted
     * 
     * @param isHighlighted the {@linkplain boolean} value
     */
    void SetIsHighlighted(boolean isHighlighted);

    /**
     * Gets a value indicating whether this row should be highlighted as "selected for transfer"
     * 
     * @return a {@linkplain boolean}
     */
    boolean GetIsSelected();

    /**
     * Sets a value whether this row is selected
     * 
     * @param isSelected the {@linkplain boolean} value
     */
    void SetIsSelected(boolean isSelected);

    /**
     * Switches between the two possible values for the {@linkplain isSelected}
     * 
     * @return the new {@linkplain boolean} value
     */
    boolean SwitchIsSelectedValue();
}
