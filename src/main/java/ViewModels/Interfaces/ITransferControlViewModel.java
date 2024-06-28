/*
 * ITransferControlViewModel.java
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

import java.util.concurrent.Callable;

import io.reactivex.Observable;

/**
 * The {@linkplain ITransferControlViewModel} is the interface definition for dst adapter implementation of transfer control view model
 */
public interface ITransferControlViewModel extends IViewModel
{
    /**
     * Gets the number of selected things to transfer
     * 
     * @return an {@linkplain Observable} of {@linkplain Integer}
     */
    Observable<Integer> GetNumberOfSelectedThing();

    /**
     * Gets a {@linkplain Callable} of {@linkplain Boolean} to call when the transfer button is pressed
     * 
     * @return a {@linkplain Callable} of {@linkplain Boolean}
     */
    Callable<Boolean> GetOnTransferCallable();
}
