/*
 * NavigationService.java
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
package Services.NavigationService;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import App.AppContainer;
import ViewModels.Interfaces.IViewModel;
import Views.Interfaces.IDialog;
import Views.Interfaces.IView;

/**
 * The {@link NavigationService} provides handy methods to handle dialogs and window as resolving their {@link IViewModel}
 */
public final class NavigationService implements INavigationService
{
    /**
     * The {@link Log4J} logger
     */
    private final Logger logger = LogManager.getLogger(); 
    
    /**
     * Shows a dialog and returns the dialog result and associate its view model to it
     * 
     * @param <TResult> the kind of result to return from the dialog
     * @param window the {@linkplain IDialog} dialog window instance
     * @return a {@link TResult} instance
     */
    @Override
    public <TResult> TResult ShowDialog(IDialog<? extends IViewModel, TResult> window)
    {
        this.BuildView(window);
        return window.ShowDialog();
    }

    /**
     * Shows a dialog and returns the dialog result and associate the specified view model to it
     * 
     * @param <TViewModel> the kind of view model that view accepts
     * @param <TResult> the kind of result to return from the dialog
     * @param window the {@linkplain IDialog} dialog window instance
     * @param viewModel {@linkplain IViewModel} to associate the view with
     * @return a {@link TResult} instance
     */
    @Override
    public <TViewModel extends IViewModel, TResult> TResult ShowDialog(IDialog<TViewModel, TResult> window, TViewModel viewModel)
    {
        window.SetDataContext(viewModel);
        return window.ShowDialog();
    }
    
    /**
     * Shows a window and associate its view model to it
     * 
     * @param window the {@linkplain IView} window instance
     */
    @Override
    public void Show(IView<? extends IViewModel> window)
    {
        this.BuildView(window);
        ((JFrame) window).setVisible(true);
    }

    /**
     * Shows a window and associate its view model to it
     * 
     * @param window the {@linkplain IView} window instance
     * @param viewModel the {@linkplain IViewModel} to associate with the view
     */
    @Override
    public void Show(IView<? extends IViewModel> window, IViewModel viewModel)
    {
        window.SetDataContext(viewModel);        
        ((JFrame) window).setVisible(true);
    }

    /**
     * Builds a view by resolving it's viewModel base on Naming conventions
     * 
     * @param window the {@linkplain IView} window instance
     */
    private void BuildView(IView<? extends IViewModel> window)
    {
        String viewModelName = String.format("I%sViewModel", window.getClass().getSimpleName());
        IViewModel viewModel = (IViewModel) AppContainer.Container.getComponent(viewModelName);
                
        if(viewModel == null)
        {
            String errorMessage = String.format("failed to get view model from name/key %s", viewModelName);
            logger.error(errorMessage, viewModelName);
            throw new org.picocontainer.PicoCompositionException(errorMessage);
        }
        
        window.SetDataContext(viewModel);
    }
}
