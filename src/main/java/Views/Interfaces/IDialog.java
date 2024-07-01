/*
 * IDialog.java
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
package Views.Interfaces;

import ViewModels.Interfaces.IViewModel;

/**
 * The <code>IView</code> is the base interface that defines a view as bind-able to a view model
 * 
 * @param <code>TViewModel</code> defines the type of the view model that can be bound to the view
 */
public interface IDialog<TViewModel extends IViewModel, TResult> extends IView<TViewModel>
{
    /**
     * Shows the dialog and return the result
     * 
     * @return a {@linkplain #TResult}
     */
    TResult ShowDialog();
    
    /**
     * Closes the dialog and sets the dialogResult
     * 
     * @param result the {@linkplain #TResult} to set
     */
    void CloseDialog(TResult result);

     /**
      * Gets the {@linkplain dialogResult}
      * 
      * @return a {@linkplain #TResult}
      */
    TResult GetDialogResult();
}
