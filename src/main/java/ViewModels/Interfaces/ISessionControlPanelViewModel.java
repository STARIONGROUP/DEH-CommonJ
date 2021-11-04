/*
 * ISessionControlPanelViewModel.java
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
package ViewModels.Interfaces;

import HubController.IHubController;
import io.reactivex.Observable;

/**
 * The {@linkplain ISessionControlPanelViewModel} is the interface definition for the {@linkplain SessionControlPanelViewModel}
 */
public interface ISessionControlPanelViewModel extends IViewModel
{

    /**
     * Action to be taken when the Connect button is clicked
     * 
     * @return a {@linkplain Boolean} as the dialog result
     */
    Boolean Connect();
    
    /**
     * Gets a value indicating whether the session is open or not
     * 
     * @return a {@linkplain Boolean}
     */
    Boolean GetIsConnected();

    /**
     * Closes the {@linkplain Session}
     */
    void Disconnect();

    /**
     * Sets the auto refresh on based on the provided timer value
     * 
     * @param timer the {@linkplain Integer} value to base the timer on
     */
    void SetAutoRefresh(Integer timer);

    /**
     * Cancels the auto refresh
     */
    void CancelAutoRefresh();

    /**
     * Calls upon the {@linkplain IHubController} to refresh the {@linkplain Session}
     */
    void Reload();

    /**
     * Calls upon the {@linkplain IHubController} to refresh the {@linkplain Session}
     */
    void Refresh();

    /**
     * Gets the timer tick {@linkplain Observable}
     * 
     * @return an {@linkplain Observable} of {@linkplain Integer}
     */
    Observable<Integer> GetTimeObservable();
}
