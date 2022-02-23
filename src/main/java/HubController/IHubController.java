/*
 * IHubController.java
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

package HubController;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableMap;

import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EngineeringModelSetup;
import cdp4common.sitedirectorydata.IterationSetup;
import cdp4common.sitedirectorydata.Participant;
import cdp4common.sitedirectorydata.Person;
import cdp4common.sitedirectorydata.SiteDirectory;
import cdp4common.types.ContainerList;
import cdp4dal.Session;
import cdp4dal.dal.Credentials;
import io.reactivex.Observable;

/**
 * The {@linkplain IHubController} is the interface definition for {@linkplain HubController}
 */
public interface IHubController
{
    /**
     * Loads an {@link Iteration} with the selected {@link DomainOfExpertise}
     * 
     * @param engineeringModelSetup
     * @param iterationSetup
     * @param domainOfExpertise
     * @return A value indicating whether the operation went well
     */
    boolean OpenIteration(EngineeringModelSetup engineeringModelSetup, IterationSetup iterationSetup, DomainOfExpertise domainOfExpertise);

    /**
     * Reloads the {@link Session}
     * 
     * @return A value indicating whether the {@link future} completed with success
     */
    Boolean Refresh();

    /**
     * Reloads the {@link Session}
     * 
     * @return A value indicating whether the {@link future} completed with success
     */
    Boolean Reload();

    /**
     * Reads an {@link Iteration} and set the active @link DomainOfExpertise for the Iteration
     * 
     * @return a {@link ImmutableMap} of <{@link Iteration}, {@link Pair} <{@link DomainOfExpertise}, {@link Participant} >>
     */
    ImmutableMap<Iteration, Pair<DomainOfExpertise, Participant>> GetIteration();

    /**
     * Reads an {@link Iteration} and set the active {@link DomainOfExpertise} for the {@link Iteration}
     * 
     * @param The {@link Iteration} to read
     * @param The {@link Domain} that reads the {@link Iteration}
     */
    void GetIteration(Iteration iteration, DomainOfExpertise domain);

    /**
     * Gets the active person
     * 
     * @return The active {@link Person}
     */
    Person GetActivePerson();

    /**
     * Retrieves the <{@link SiteDirectory} that is loaded in the <{@link Session}
     * 
     * @return the {@link SiteDirectory}
     */
    SiteDirectory GetSiteDirectory();

    /**
     * Gets the {@link EngineeringModelSetup}s contained in the site directory
     *  
     * @return A {@link ContainerList} of {@link EngineeringModelSetup}
     */
    ContainerList<EngineeringModelSetup> GetEngineeringModels();

    /**
     * Opens the {@linkplain Session}
     * 
     * @param credentials the {@link Credentials}
     * @return A {@link Boolean} indicating whether opening the session succeeded
     */
    Boolean Open(Credentials credentials);

    /**
     * Gets the open {@link Iteration}
     * 
     * @return an {@linkplain Iteration}
     */
    Iteration GetOpenIteration();

    /**
     * Gets the {@linkplain Observable} from {@linkplain isSessionOpen} boolean field
     * 
     * @return an {@linkplain Observable} wrapping a value indicating whether the {@linkplain Session} is open
     */
    Observable<Boolean> GetIsSessionOpenObservable();

    /**
     * Gets a value indicating whether the session is open
     * 
     * @return a {@linkplain Boolean}
     */
    Boolean GetIsSessionOpen();

    /**
     * Gets the current {@linkplain DomainOfExpertise}
     */
    DomainOfExpertise GetCurrentDomainOfExpertise();

    /**
     * Gets the data source URI as string of the current {@linkplain Session}
     * 
     * @return a string representation of the URI or a empty string
     */
    String GetDataSourceUri();

    /**
     * Closes the connection to the data-source
     */
    void Close();    
}
