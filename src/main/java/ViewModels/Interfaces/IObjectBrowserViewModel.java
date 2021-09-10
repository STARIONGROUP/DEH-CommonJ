/*
 * IObjectBrowserViewModel.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Geren�, Alex Vorobiev, Nathanael Smiechowski 
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

import org.netbeans.swing.outline.OutlineModel;

import io.reactivex.Observable;

/**
 * The {@linkplain IObjectBrowserViewModel} is the interface definition for the {@linkplain ObjectBrowserViewModel}. It is registered in the container.
 */
public interface IObjectBrowserViewModel extends IViewModel
{
    /**
     * Gets the {@linkplain OutlineModel} for the treeview
     * 
     * @return An {@linkplain Observable} of {@linkplain OutlineModel}
     */
    Observable<OutlineModel> ElementDefinitionTree();
}
