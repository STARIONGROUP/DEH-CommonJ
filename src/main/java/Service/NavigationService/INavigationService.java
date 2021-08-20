/*
 * INavigationService.java
 *
 * Copyright (c) 2015-2019 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-CommonJ
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
package Service.NavigationService;

import ViewModels.Interfaces.IViewModel;
import Views.Interfaces.IDialog;
import Views.Interfaces.IView;

/**
 * The {@linkplain INavigationService} is the main interface definition for the {@link NavigationService}
 */
public interface INavigationService
{
    /**
     * Shows a window and associate its view model to it
     * 
     * @param window the {@linkplain IView} window instance
     * @param viewModel the {@linkplain IViewModel} class to associate with the view
     */
    void Show(Class<IView<? extends IViewModel>> window, Class<IViewModel> viewModel);

    /**
     * Shows a window and associate its view model to it
     * 
     * @param window the {@linkplain IView} window instance
     * @param viewModel the {@linkplain IViewModel} to associate with the view
     */
    void Show(IView<? extends IViewModel> window, IViewModel viewModel);

    /**
     * Shows a window and associate its view model to it
     * 
     * @param window the {@linkplain IView} window instance
     */
    void Show(IView<? extends IViewModel> window);

    /**
     * Shows a dialog and returns the dialog result and associate its view model to it
     * 
     * @param <TResult> the kind of result to return from the dialog
     * @param window the {@linkplain IDialog} dialog window instance
     * @return a {@link TResult} instance
     */
    <TResult> TResult ShowDialog(IDialog<? extends IViewModel, TResult> window);
}
