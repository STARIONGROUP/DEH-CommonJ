/*
 * IHubBrowserHeaderViewModel.java
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

import Reactive.ObservableCollection;
import Reactive.ObservableValue;
import ViewModels.HubBrowserHeaderViewModel;
import io.reactivex.Observable;

import java.util.Collection;

/**
 * The {@linkplain IHubBrowserHeaderViewModel} is the main interface definition for {@linkplain HubBrowserHeaderViewModel}
 */
public interface IHubBrowserHeaderViewModel extends IViewModel
{
    /**
     * Gets the {@linkplain ObservableValue} from domainOfExpertiseName {@linkplain ObservableValue}
     * 
     * @return a {@linkplain Observable}
     */
    ObservableValue<String> GetDomainOfExpertiseName();

    /**
     * Gets the {@linkplain ObservableValue} from personName {@linkplain ObservableValue}
     * 
     * @return a {@linkplain ObservableValue}
     */
    ObservableValue<String> GetPersonName();

    /**
     * Gets the {@linkplain ObservableValue} from iterationNumber {@linkplain ObservableValue}
     * 
     * @return a {@linkplain ObservableValue}
     */
    ObservableValue<String> GetIterationNumber();

    /**
     * Gets an {@linkplain Observable} yielding the selected option
     *
     * @return An {@linkplain Observable} of string
     */
    Observable<String> GetSelectedOption();

    /**
     * Gets the {@linkplain ObservableValue} holding the selected option
     *
     * @param optionName The option name string as {@linkplain Object}
     */
    void SetSelectedOption(Object optionName);

    /**
     * Gets the {@linkplain ObservableValue} from dataSource {@linkplain ObservableValue}
     * 
     * @return a {@linkplain ObservableValue}
     */
    ObservableValue<String> GetDataSource();

    /**
     * Gets the {@linkplain ObservableValue} from engineeringModelName {@linkplain ObservableValue}
     * 
     * @return a {@linkplain ObservableValue}
     */
    ObservableValue<String> GetEngineeringModelName();

    /**
     * The {@linkplain ObservableCollection} of available options
     *
     * @return A {@linkplain Collection} of string
     */
    ObservableCollection<String> GetAvailableOptions();
}
