/*
 * Sess	ionControlPanelViewModel.java
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
package ViewModels;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import HubController.IHubController;
import Reactive.ObservableValue;
import Services.NavigationService.INavigationService;
import Utils.Ref;
import Utils.Tasks.ObservableTask;
import Utils.Tasks.Task;
import Utils.Tasks.TaskStatus;
import ViewModels.Interfaces.ISessionControlPanelViewModel;
import Views.HubLogin;
import io.reactivex.Observable;

/**
 * The {@linkplain SessionControlPanelViewModel} is the main view model for the {@linkplain SessionControlPanel} that appears in the {@linkplain HubBrowserPanel}
 */
public final class SessionControlPanelViewModel implements ISessionControlPanelViewModel
{
    /**
     * The {@linkplain Logger} of the current class
     */
    private Logger logger = LogManager.getLogger();
    
    /**
     * The {@linkplain IHubController}
     */
    private IHubController hubController;

    /**
     * The {@linkplain INavigationService}
     */
    private INavigationService navigationService;
    
    /**
     * The {@linkplain ObservableValue} of type {@linkplain Integer} that yields the current tick of the ongoing timer
     */
    private ObservableValue<Integer> timer = new ObservableValue<>(0, Integer.class);

    /**
     * The auto refresh {@linkplain ObservableTask} collection with their associated cancellation handle
     */
    private List<Pair<Ref<Boolean>, ObservableTask<TaskStatus>>> tasks = new ArrayList<>();
    
    /**
     * Gets the timer tick {@linkplain Observable}
     * 
     * @return an {@linkplain Observable} of {@linkplain Integer}
     */
    @Override
    public Observable<Integer> GetTimeObservable()
    {
        return this.timer.Observable();
    }
    
    /**
     * Initializes a new {@linkplain SessionControlPanelViewModel}
     * 
     * @param hubController the {@linkplain IHubController}
     * @param navigationService the {@linkplain INavigationService}
     */
    public SessionControlPanelViewModel(IHubController hubController, INavigationService navigationService)
    {
        this.hubController = hubController;
        this.navigationService = navigationService;        
    }

    /**
     * Action to be taken when the Connect button is clicked
     * 
     * @return a {@linkplain Boolean} as the dialog result
     */
    @Override
    public Boolean Connect()
    {
        HubLogin view = new HubLogin();
        return this.navigationService.ShowDialog(view);
    }
    
    /**
     * Closes the {@linkplain Session}
     */
    @Override
    public void Disconnect()
    {
        this.CancelAutoRefresh();
        this.hubController.Close();
    }
    
    /**
     * Gets a value indicating whether the session is open or not
     * 
     * @return a {@linkplain Boolean}
     */
    @Override
    public Boolean GetIsConnected()
    {
        return this.hubController.GetIsSessionOpen();    
    }

    /**
     * Calls upon the {@linkplain IHubController} to refresh the {@linkplain Session}
     */
    @Override
    public void Refresh()
    {
        this.hubController.Refresh();
    }

    /**
     * Calls upon the {@linkplain IHubController} to refresh the {@linkplain Session}
     */
    @Override
    public void Reload()
    {
        this.hubController.Reload();
    }

    /**
     * Cancels the auto refresh
     */
    @Override
    public void CancelAutoRefresh()
    {
        for (Pair<Ref<Boolean>, ObservableTask<TaskStatus>> pair : tasks)
        {
            pair.getLeft().Set(true);
        }
    }

    /**
     * Sets the auto refresh on based on the provided timer value
     * 
     * @param timer the {@linkplain Integer} value to base the timer on
     */
    @Override
    public void SetAutoRefresh(Integer timer)
    {
        this.CancelAutoRefresh();
        
        Ref<Boolean> isCancelled = new Ref<>(Boolean.class, false);
        
        ObservableTask<TaskStatus> task = Task.Run(() -> 
        {
            this.timer.Value(timer);
            
            while(Boolean.FALSE.equals(isCancelled.Get()))
            {
                try
                {
                    Thread.sleep(1000);
                    this.timer.Value(this.timer.Value() - 1);
                    
                    if(this.timer.Value() == 0)
                    {
                        this.hubController.Refresh();
                        this.timer.Value(timer);
                    }
                } 
                catch (InterruptedException exception)
                {
                    this.logger.catching(exception);
                    isCancelled.Set(true);
                    this.timer.Value(timer);
                }
            }            
        });

        Pair<Ref<Boolean>, ObservableTask<TaskStatus>> taskWithCancellation = Pair.of(isCancelled, task);
        
        task.Observable().subscribe(x -> 
        {
            this.logger.debug(String.format("Auto refresh task is done with status: %s", x.GetStatus()));
            this.tasks.remove(taskWithCancellation);
        });
        
        this.tasks.add(taskWithCancellation);
    }
}
