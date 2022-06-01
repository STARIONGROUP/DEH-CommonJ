/*
 * IHubController.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Geren�, Alex Vorobiev, Nathanael Smiechowski 
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

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CompletionException;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableMap;

import Utils.Ref;
import Views.Dialogs.LogEntryDialog;
import cdp4common.commondata.LogEntry;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.ExternalIdentifierMap;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EngineeringModelSetup;
import cdp4common.sitedirectorydata.IterationSetup;
import cdp4common.sitedirectorydata.Participant;
import cdp4common.sitedirectorydata.Person;
import cdp4common.sitedirectorydata.ReferenceDataLibrary;
import cdp4common.sitedirectorydata.SiteDirectory;
import cdp4common.types.ContainerList;
import cdp4dal.Session;
import cdp4dal.dal.Credentials;
import cdp4dal.exceptions.DalWriteException;
import cdp4dal.exceptions.TransactionException;
import cdp4dal.operations.ThingTransaction;
import io.reactivex.Observable;
import javassist.NotFoundException;

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
    boolean Refresh();

    /**
     * Reloads the {@link Session}
     * 
     * @return A value indicating whether the {@link future} completed with success
     */
    boolean Reload();

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
     * @throws NotFoundException 
     */
    void GetIteration(Iteration iteration, DomainOfExpertise domain) throws NotFoundException;

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

    /**
     * Gets the thing by predicate on {@linkplain Thing} from the chain of rdls
     * 
     * @param predicate the predicate on {@linkplain Thing}
     * @return An assert whether the thing has been found
     */
    <TThing extends Object> boolean TryGetThingFromChainOfRdlBy(Predicate<TThing> predicate, Ref<TThing> thing);

    /**
     * Gets the DEHP {@linkplain ReferenceDataLibraries} or the open model one
     * 
     * @return the {@linkplain ReferenceDataLibraries}
     */
    ReferenceDataLibrary GetDehpOrModelReferenceDataLibrary();

    /**
     * Creates or updates the things in the specified {@linkplain ThingTransaction}
     * 
     * @param transaction the {@linkplain ThingTransaction}
     * @return a value indicating whether the transaction has been committed with success
     * @throws DalWriteException, CompletionException
     */
    void Write(ThingTransaction transaction) throws DalWriteException, CompletionException;

    /**
     * Refresh the specified library local cache by reading it
     * 
     * @param library the {@linkplain ReferenceDataLibrary} to refresh
     */
    void RefreshReferenceDataLibrary(ReferenceDataLibrary library);

    /**
     * Gets the {@linkplain Thing} by it's Iid from the cache
     * 
     * @param <TThing> the type of {@linkplain Thing} to retrieve
     * @param iid the Iid of the {@linkplain Thing} to retrieve from the cache
     * @param refThing the {@linkplain Ref} of {@linkplain TThing} as ref parameter
     */
    <TThing extends Thing> boolean TryGetThingById(UUID iid, Ref<TThing> refThing);

    /**
     * Initializes a new {@linkplain ThingTransaction} based on the current open {@linkplain Iteration}
     * 
     * @return a {@linkplain Pair} of {@linkplain Iteration} cloned and its {@linkplain ThingTransaction}
     * @throws TransactionException
     */
    Pair<Iteration, ThingTransaction> GetIterationTransaction() throws TransactionException;

    /**
     * Gets the {@linkplain Observable} from {@linkplain isSessionOpen} boolean field
     * 
     * @return an {@linkplain Observable} wrapping a value indicating whether the session has been refreshed or reloaded
     */
    Observable<Boolean> GetSessionEventObservable();

    /**
     * Gets the collection of available {@linkplain ExternalIdentifierMap} for the provided DST tool name
     * 
     * @param toolName the {@linkplain String} DST tool name
     * @return a {@linkplain Collection} of {@linkplain ExternalIdentifierMap}
     */
    Collection<ExternalIdentifierMap> GetAvailableExternalIdentifierMap(String toolName);

    /**
     * Adds a new <see cref="ModelLogEntry"/> record to the <see cref="EngineeringModel.LogEntry"/> 
     * list of the current <see cref="OpenIteration"/> and registers the change to a <see cref="ThingTransaction"/>
     * @throws TransactionException 
     */
    void RegisterLogEntry(String content, ThingTransaction transaction) throws TransactionException;

    /**
     * Tries to create a {@linkplain LogEntry} base on the input from the {@linkplain LogEntryDialog}
     * 
     * @param transaction the {@linkplain ThingTransaction}
     * @return a value indicating whether the whole transaction should be cancelled based on the dialog result
     * @throws TransactionException 
     */
    boolean TrySupplyAndCreateLogEntry(ThingTransaction transaction) throws TransactionException;    
}
