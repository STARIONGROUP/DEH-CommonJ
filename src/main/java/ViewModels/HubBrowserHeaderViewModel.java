/*
 * HubBrowserHeaderViewModel.java
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
package ViewModels;

import HubController.IHubController;
import Reactive.ObservableValue;
import ViewModels.Interfaces.IHubBrowserHeaderViewModel;
import cdp4common.engineeringmodeldata.EngineeringModel;
import io.reactivex.Observable;

/**
 * The {@linkplain HubBrowserHeaderViewModel} is the main view model for {@linkplain HubBrowserHeader}
 */
public final class HubBrowserHeaderViewModel implements IHubBrowserHeaderViewModel
{
    /**
     * The {@linkplain IHubController}
     */
    private IHubController hubController;
    
    /**
     * Backing field for {@linkplain GetEngineeringModelName}
     */
    private ObservableValue<String> engineeringModelName = new ObservableValue<String>();
    
    /**
     * Gets the {@linkplain Observable} from {@linkplain engineeringModelName} {@linkplain ObservableValue}
     * 
     * @return a {@linkplain Observable}
     */
    @Override
    public Observable<String> GetEngineeringModelName()
    {
        return this.engineeringModelName.Observable();
    }
    
    /**
     * Backing field for {@linkplain GetDataSource}
     */
    private ObservableValue<String> dataSource = new ObservableValue<String>();
    
    /**
     * Gets the {@linkplain Observable} from {@linkplain dataSource} {@linkplain ObservableValue}
     * 
     * @return a {@linkplain Observable}
     */
    @Override
    public Observable<String> GetDataSource()
    {
        return this.dataSource.Observable();
    }
    
    /**
     * Backing field for {@linkplain GetIterationNumber}
     */
    private ObservableValue<String> iterationNumber = new ObservableValue<String>();
    
    /**
     * Gets the {@linkplain Observable} from {@linkplain iterationNumber} {@linkplain ObservableValue}
     * 
     * @return a {@linkplain Observable}
     */
    @Override
    public Observable<String> GetIterationNumber()
    {
        return this.iterationNumber.Observable();
    }
    
    /**
     * Backing field for {@linkplain GetPersonName}
     */
    private ObservableValue<String> personName = new ObservableValue<String>();
    
    /**
     * Gets the {@linkplain Observable} from {@linkplain personName} {@linkplain ObservableValue}
     * 
     * @return a {@linkplain Observable}
     */
    @Override
    public Observable<String> GetPersonName()
    {
        return this.personName.Observable();
    }
    
    /**
     * Backing field for {@linkplain GetDomainOfExpertiseName}
     */
    private ObservableValue<String> domainOfExpertiseName = new ObservableValue<String>();
    
    /**
     * Gets the {@linkplain Observable} from {@linkplain domainOfExpertiseName} {@linkplain ObservableValue}
     * 
     * @return a {@linkplain Observable}
     */
    @Override
    public Observable<String> GetDomainOfExpertiseName()
    {
        return this.domainOfExpertiseName.Observable();
    }

    /**
     * Initializes a new {@linkplain HubBrowserHeaderViewModel}
     * 
     * @param hubController The {@linkplain IHubController}
     */
    public HubBrowserHeaderViewModel(IHubController hubController)
    {
        this.hubController = hubController;
        this.InitializeObservable();
    }
    
    /**
     * Initializes this view model observable
     */
    private void InitializeObservable()
    {
        this.hubController.GetIsSessionOpenObservable()
            .subscribe(isSessionIsOpen -> 
            {
                if (isSessionIsOpen)
                {
                    this.engineeringModelName.Value(this.hubController.GetOpenIteration().getContainerOfType(EngineeringModel.class).getEngineeringModelSetup().getName());
                    this.dataSource.Value(this.hubController.GetDataSourceUri());
                    this.iterationNumber.Value(String.valueOf(this.hubController.GetOpenIteration().getIterationSetup().getIterationNumber()));
                    this.personName.Value(this.hubController.GetActivePerson().getName());
                    this.domainOfExpertiseName.Value(this.hubController.GetCurrentDomainOfExpertise().getName());
                }
                else
                {
                    this.engineeringModelName.Value("");
                    this.dataSource.Value("");
                    this.iterationNumber.Value("");
                    this.personName.Value("");
                    this.domainOfExpertiseName.Value("");
                }
            });
    }
}
