/*
 * IHubController.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
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

/**
 * The {@linkplain IHubController} is the interface definition for {@linkplain HubController}
 */
public interface IHubController
{
    /**
     * @param credentials
     * @return A {@link Boolean} indicating whether opening the session succeeded
     */
    Boolean Open(Credentials credentials);

    /**
     * Reloads the {@link Session}
     * @return A value indicating whether the {@link future} completed with success
     */
    Boolean Refresh();

    /**
     * Reloads the {@link Session}
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
     * @param The {@link Iteration} to read
     * @param The {@link Domain} that reads the {@link Iteration}
     */
    void GetIteration(Iteration iteration, DomainOfExpertise domain);

    /**
     * Retrieves the <{@link SiteDirectory} that is loaded in the <{@link Session}
     * @return {@link SiteDirectory}
     */
    SiteDirectory GetSiteDirectory();
    
    /**
     * Gets the active person
     * @return {@link Person}
     */
    Person GetActivePerson();

    /**
     *  Gets the {@link EngineeringModelSetup}s contained in the site directory
     * @return A {@link ContainerList} of {@link EngineeringModelSetup}
     */
    ContainerList<EngineeringModelSetup> GetEngineeringModels();

    /**
     * A value indicating whether the session is open
     */
    Boolean IsSessionOpen();

    /**
     * A value indicating whether the session is open
     */
    DomainOfExpertise GetCurrentDomainOfExpertise();

    /**     * 
     * Loads an {@link Iteration} with the selected {@link DomainOfExpertise}
     * 
     * @param engineeringModelSetup
     * @param iterationSetup
     * @param domainOfExpertise
     * @return A value indicating whether the operation went well
     */
    boolean OpenIteration(EngineeringModelSetup engineeringModelSetup, IterationSetup iterationSetup, DomainOfExpertise domainOfExpertise);
}
