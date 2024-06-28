/*
 * IHubBrowserContextMenuViewModel.java
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

import io.reactivex.Observable;

/**
 * The {@linkplain IHubBrowserContextMenuViewModel} is the interface definition for the {@linkplain HubBrowserContextMenuViewModel}
 */
public interface IHubBrowserContextMenuViewModel extends IContextMenuViewModel
{
    /**
     * Maps the top element towards the DST
     */
    void MapTopElement();
    
    /**
     * Maps the selection towards the DST
     */
    void MapSelection();

    /**
     * Gets an {@linkplain Observable} of {@linkplain Boolean} indicating whether the {@linkplain #MapTopElement()} can execute
     * 
     * @return an {@linkplain Observable} of {@linkplain Boolean}
     */
    Observable<Boolean> CanMapTopElement();
    
    /**
     * Gets an {@linkplain Observable} of {@linkplain Boolean} indicating whether the {@linkplain #MapSelection()} can execute
     * 
     * @return an {@linkplain Observable} of {@linkplain Boolean}
     */
    Observable<Boolean> CanMapSelection();

    /**
     * Sets the browser type with the specified {@linkplain Class} of {@linkplain IObjectBrowserViewModel}
     * 
     * @param browserType the {@linkplain Class} {@linkplain IObjectBrowserViewModel} that identifies the type of browser
     */
    void SetBrowserType(Class<? extends IObjectBrowserViewModel> browserType);
}
