/*
 * IMappedElementRowViewModel.java
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
package ViewModels.Interfaces;

import Enumerations.MappingDirection;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain IMappedElementRowViewModel} is the main interface for all {@linkplain MappedElementRowViewModel}
 */
public interface IMappedElementRowViewModel
{
    /**
     * Gets the Thing type argument class that identifies the implementation of the {@linkplain MappedElementRowViewModel}
     * 
     * @return a {@linkplain Class}
     */
    Class<? extends Thing> GetTThingClass();

    /**
     * Gets the {@linkplain MappingDirection} to which direction this represented mapping applies to
     * 
     * @return a {@linkplain MappingDirection}
     */
    MappingDirection GetMappingDirection();
}
