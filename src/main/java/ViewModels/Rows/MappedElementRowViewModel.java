/*
 * MappedElementRowViewModel.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-MDSYSML
 *
 * The DEH-MDSYSML is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-MDSYSML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ViewModels.Rows;

import Enumerations.MappingDirection;
import ViewModels.Interfaces.IMappedElementRowViewModel;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain MappedElementRowViewModel} is the base abstract row view model that represents a mapping done between a {@linkplain Thing} and a DST Element
 * @param <TThing> the type of {@linkplain Thing}
 * @param <TDstElement> the type of DST element
 */
public abstract class MappedElementRowViewModel<TThing extends Thing, TDstElement> implements IMappedElementRowViewModel
{
    /**
     * The {@linkplain Class} of the {@linkplain TThing}
     */
    private Class<TThing> clazz;
    
    /**
     * Gets the Thing type argument class that identifies the implementation of the {@linkplain MappedElementRowViewModel}
     * 
     * @return a {@linkplain Class}
     */
    @Override
    public Class<? extends Thing> GetTThingClass()
    {
        return this.clazz;
    }
    
    /**
     * Backing field for {@linkplain GetDstElement}
     */
    private TDstElement dstElement;
    
    /**
     * Gets the {@linkplain TDstElement} of this represented mapping
     * 
     * @return a {@linkplain TDstElement}
     */
    public TDstElement GetDstElement()
    {
        return this.dstElement;
    }
    
    /**
     * Backing field for {@linkplain GetDstElement}
     */
    private TThing hubElement;
    
    /**
     * Gets the {@linkplain TThing} of this represented mapping
     * 
     * @return a {@linkplain TThing}
     */
    public TThing GetHubElement()
    {
        return this.hubElement;
    }
    
    /**
     * Backing field for {@linkplain GetIsValid}
     */
    private MappingDirection mappingDirection;
    
    /**
     * Gets the {@linkplain MappingDirection} to which direction this represented mapping applies to
     * 
     * @return a {@linkplain MappingDirection}
     */
    public MappingDirection GetMappingDirection()
    {
        return this.mappingDirection;
    }
    
    /**
     * Backing field for {@linkplain GetIsValid}
     */
    private boolean isValid;
    
    /**
     * Gets a value indicating whether the mapping represented by this view model instance is valid
     * 
     * @return a {@linkplain boolean}
     */
    public boolean GetIsValid()
    {
        return this.isValid;
    }

    /**
     * Sets the hub element
     * 
     * @param hubElement the {@linkplain TThing}
     */
    public void SetHubElement(TThing hubElement)
    {
        this.hubElement = hubElement;
    }
    
    /**
     * Initializes a new {@linkplain MappedElementRowViewModel}
     * 
     * @param thing the {@linkplain TThing} that is at one end of the mapping
     * @param clazz the {@linkplain Class} of the {@linkplain TThing}
     * @param dstElement the {@linkplain TDstElement} that is at the other end
     * @param mappingDirection the {@linkplain MappingDirection} to which this mapping applies to
     */
    protected MappedElementRowViewModel(TThing thing, Class<TThing> clazz, TDstElement dstElement, MappingDirection mappingDirection)
    {
        this.hubElement = thing;
        this.dstElement = dstElement;
        this.mappingDirection = mappingDirection;
        this.clazz = clazz;
    }
    
    /**
     * Initializes a new {@linkplain MappedElementRowViewModel}
     * 
     * @param clazz the {@linkplain Class} of the {@linkplain TThing}
     * @param dstElement the {@linkplain TDstElement} that is at the other end
     * @param mappingDirection the {@linkplain MappingDirection} to which this mapping applies to
     */
    protected MappedElementRowViewModel(Class<TThing> clazz, TDstElement dstElement, MappingDirection mappingDirection)
    {
        this.dstElement = dstElement;
        this.mappingDirection = mappingDirection;
        this.clazz = clazz;
    }
}
