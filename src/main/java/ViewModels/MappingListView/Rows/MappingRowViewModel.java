/*
 * MappingRowViewModel.java
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
package ViewModels.MappingListView.Rows;

import org.apache.commons.lang3.tuple.Triple;

import Enumerations.MappingDirection;
import ViewModels.ObjectBrowser.Interfaces.IRowBaseViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.Requirement;

/**
 * The {@linkplain MappingRowViewModel} represents a mapping row
 * 
 * @param <TDstElement> the type of DST element that maps to an {@linkplain ElementDefinition} or a {@linkplain Requirement}
 * @param <THubElement> the type of Hub element that maps to the {@linkplain #TDstElement}
 */
public class MappingRowViewModel<TDstElement, TThing extends DefinedThing> implements IRowBaseViewModel
{
    /**
     * Private field for {@linkplain #GetMappingDirection()}
     */
    private MappingDirection mappingDirection;

    /**
     * Gets the {@linkplain MappingDirection} that applies to the represented mapping
     * 
     * @return a {@linkplain MappingDirection}
     */
    public MappingDirection GetMappingDirection()
    {
        return this.mappingDirection;
    }
    
    /**
     * Private field for {@linkplain #GetHubElement()}
     */
    private TThing hubElement;

    /**
     * Gets the {@linkplain ElementDefinition}
     * 
     * @return a {@linkplain ElementDefinition}
     */
    public TThing GetHubElement()
    {
        return this.hubElement;
    }
    
    /**
     * Private field for {@linkplain #GetDstElement()}
     */
    private TDstElement dstElement;

    /**
     * Gets the {@linkplain #TDstElement}
     * 
     * @return a {@linkplain #TDstElement}
     */
    public TDstElement GetDstElement()
    {
        return this.dstElement;
    }
    
    /**
     * Initializes a new {@linkplain MappingRowViewModel}
     * 
     * @param hubElement the {@linkplain TThing} hub element
     * @param dstElement the {@linkplain TDstElement} dst element
     * @param mappingDirection the {@linkplain MappingDirection}
     */
    public MappingRowViewModel(TThing hubElement, TDstElement dstElement, MappingDirection mappingDirection)
    {
        this.hubElement = hubElement;
        this.dstElement = dstElement;
        this.mappingDirection = mappingDirection;
    }   

    /**
     * Initializes a new {@linkplain MappingRowViewModel}
     * 
     * @param mappedElement a {@linkplain Triple} of the {@linkplain #TDstElement} the {@linkplain MappingDirection} and the {@linkplain Thing}
     */
    @SuppressWarnings("unchecked")
    public MappingRowViewModel(Triple<? extends TDstElement, MappingDirection, ? extends Thing> mappedElement)
    {
        this.hubElement = (TThing) mappedElement.getRight();
        this.dstElement = mappedElement.getLeft();
        this.mappingDirection = mappedElement.getMiddle();
    }
}
