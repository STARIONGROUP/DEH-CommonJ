/*
 * NavigationServiceTestFixture.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Gerene, Alex Vorobiev, Nathanael Smiechowski 
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
package Service.NavigationService;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import App.AppContainer;
import ViewModels.Interfaces.IViewModel;
import Views.Interfaces.IDialog;
import Views.Interfaces.IView;

class NavigationServiceTestFixture
{
    private NavigationService navigationService;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
        this.navigationService = new NavigationService();
    }

    @Test
    void VerifyShowDialog()
    {
        assertThrows(org.picocontainer.PicoCompositionException.class, () -> this.navigationService.ShowDialog(GenerateDialog()));
        AppContainer.Container.addComponent(IViewModel.class.getSimpleName(), new IViewModel() {});                
        assertTrue(() -> this.navigationService.ShowDialog(GenerateDialog()));
    }
    
    @SuppressWarnings("serial")
    @Test
    void VerifyShow()
    {
        assertDoesNotThrow(() -> this.navigationService.Show(new FakeWindow() {}, null));
        
        AppContainer.Container.removeComponent(IViewModel.class.getSimpleName());
        assertThrows(org.picocontainer.PicoCompositionException.class, () -> this.navigationService.Show(new FakeWindow() {}));
        
        AppContainer.Container.addComponent(IViewModel.class.getSimpleName(), new IViewModel() {}); 
        assertDoesNotThrow(() -> this.navigationService.Show(new FakeWindow() {}));
    }

    private IDialog<IViewModel, Boolean> GenerateDialog()
    {
        return new IDialog<IViewModel, Boolean>() {

            @Override
            public void Bind() { }

            @Override
            public void SetDataContext(IViewModel viewModel) { }

            @Override
            public IViewModel GetDataContext() 
            {
                return null; 
            }

            @Override
            public Boolean ShowDialog()
            {
                return true;
            }

            @Override
            public void CloseDialog(Boolean result) { }

            @Override
            public Boolean GetDialogResult()
            {
                return null;
            }
        };
    }
    
    @SuppressWarnings("serial")
    private abstract class FakeWindow extends JFrame implements IView<IViewModel>
    {
        @Override
        public void Bind() { }

        @Override
        public void SetDataContext(IViewModel viewModel) { }

        @Override
        public IViewModel GetDataContext()
        {
            return null;
        }        
    }
}
