/*
 * IMapCommandService.java
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
package Services.Mapping;


import Enumerations.MappingDirection;
import io.reactivex.Observable;

/**
 * The {@linkplain IMapCommandService} is the interface definition for {@linkplain MapCommandService}
 */
public interface IMapCommandService
{
    /**
     * Maps the selection from the {@linkplain ISelectionService} to the specified {@linkplain MappingDirection}
     * 
     * @param mappingDirection the {@linkplain MappingDirection}
     */
    void MapSelection(MappingDirection mappingDirection);

    /**
     * Gets an {@linkplain Observable} of {@linkplain Boolean} indicating whether the map action can be executed
     * 
     * @return an {@linkplain Observable} of {@linkplain Boolean}
     */
    Observable<Boolean> CanExecuteObservable();

    /**
     * Initializes this {@linkplain MapCommandService} {@linkplain CanExecuteObservable} 
     * for later use by the context menu map command
     */
    void Initialize();

    /**
     * Gets a value indicating whether the map action can be executed
     * 
     * @return a {@linkplain Boolean} value
     */
    boolean CanExecute();

    /**
     * Maps the specified top element to the specified {@linkplain MappingDirection}
     * 
     * @param <TElement> the type of the top element
     * @param topElement the {@linkplain #TElement} top element
     * @param mappingDirection the {@linkplain MappingDirection}
     */
    <TElement> void MapTopElement(TElement topElement, MappingDirection mappingDirection);
}
