/*
 * HubController.java
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
package HubController;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.google.common.collect.ImmutableMap;

import Reactive.ObservableValue;
import Services.NavigationService.INavigationService;
import Utils.Ref;
import Views.Dialogs.LogEntryDialog;

import static Utils.Operators.Operators.AreTheseEquals;

import cdp4common.commondata.DeprecatableThing;
import cdp4common.commondata.LogLevelKind;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.EngineeringModel;
import cdp4common.engineeringmodeldata.ExternalIdentifierMap;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.engineeringmodeldata.ModelLogEntry;
import cdp4common.sitedirectorydata.*;
import cdp4common.types.CacheKey;
import cdp4common.types.ContainerList;
import cdp4dal.Session;
import cdp4dal.SessionImpl;
import cdp4dal.dal.Credentials;
import cdp4dal.exceptions.DalWriteException;
import cdp4dal.exceptions.TransactionException;
import cdp4dal.operations.ThingTransaction;
import cdp4dal.operations.ThingTransactionImpl;
import cdp4dal.operations.TransactionContextResolver;
import cdp4servicesdal.CdpServicesDal;
import io.reactivex.Observable;
import javassist.NotFoundException;

/**
 * Definition of the {@link HubController} which is responsible to provides {@link Session} related functionalities
 */
public class HubController implements IHubController 
{
    /**
     * The current class logger
     */
    private Logger logger = LogManager.getLogger();

    /**
     * The {@linkplain INavigationService}
     */
    private final INavigationService navigationService;
  
    /**
     * Backing field for {@link IsSessionOpen}
     */ 
    private DomainOfExpertise currentDomainOfExpertise;
    
    /**
     * Gets the current {@linkplain DomainOfExpertise}
     */
    @Override
    public DomainOfExpertise GetCurrentDomainOfExpertise()
    {
        return this.currentDomainOfExpertise;
    }

    /**
     * Backing field for {@link IsSessionOpen}
     */
    private Boolean isSessionOpen = false;
    
    /**
     * Gets a value indicating whether the session is open
     * 
     * @return a {@linkplain Boolean}
     */
    @Override
    public Boolean GetIsSessionOpen()
    {
        return this.isSessionOpen;
    }
    
    /**
     * Backing field for {@linkplain IsSessionOpenObservable}
     */
    private ObservableValue<Boolean> isSessionOpenObservable = new ObservableValue<>(Boolean.class);
    
    /**
     * Gets the {@linkplain Observable} from {@linkplain isSessionOpen} boolean field
     * 
     * @return an {@linkplain Observable} wrapping a value indicating whether the {@linkplain Session} is open
     */
    @Override
    public Observable<Boolean> GetIsSessionOpenObservable()
    {
        return this.isSessionOpenObservable.Observable();
    }
        
    /**
     * Backing field for {@linkplain GetSessionEventObservable}
     */
    private ObservableValue<Boolean> sessionEvent = new ObservableValue<>(false, Boolean.class);
    

    /**
     * Gets the {@linkplain Observable} from {@linkplain isSessionOpen} boolean field
     * 
     * @return an {@linkplain Observable} wrapping a value indicating whether the session has been refreshed or reloaded
     */
    @Override
    public Observable<Boolean> GetSessionEventObservable()
    {
        return this.sessionEvent.Observable();
    }
    
    /**
     * Sets the {@linkplain isSessionOpen} and call OnNext on {@linkplain isSessionOpenObservable}
     * 
     * @param value the {@linkplain Boolean} new value
     */
    private void SetIsSessionOpen(Boolean value)
    {
        this.isSessionOpen = value;
        this.isSessionOpenObservable.Value(value);
    }
    
    /**
     * Sets the open {@link Iteration}
     */
    private Iteration openIteration;
    
    /**
     * Gets the open {@link Iteration}
     * 
     * @return an {@linkplain Iteration}
     */
    @Override
    public Iteration GetOpenIteration()
    {
        return this.openIteration;
    }
            
    /**
     * The {@link Session} that is used to communicate with the data-store
     */
    private Session session;
    
    /**
     * Initializes an new {@linkplain HubController}
     * 
     * @param navigationService the {@linkplain INavigationService}
     */
    public HubController(INavigationService navigationService)
    {
        this.navigationService = navigationService;
    }
    
    /**
     * Gets the open {@linkplain ReferenceDataLibraries}
     * 
     * @return a {@linkplain Collection} of {@linkplain ReferenceDataLibraries}
     */
    public Collection<ReferenceDataLibrary> OpenReferenceDataLibraries()
    {
        if(Boolean.TRUE.equals(this.isSessionOpen))
        {
            return this.session.getOpenReferenceDataLibraries();
        }
        
        return new ArrayList<>();
    }
    
    /**
     * Gets the DEHP {@linkplain ReferenceDataLibraries} or the open model one
     * 
     * @return the {@linkplain ReferenceDataLibrary}
     */
    @Override
    public ReferenceDataLibrary GetDehpOrModelReferenceDataLibrary()
    {
        Optional<ReferenceDataLibrary> optionalLibrary = this.OpenReferenceDataLibraries().stream()
                .filter(x -> x.getShortName().toUpperCase().contains("DEHP")).findFirst();
        
        if(optionalLibrary.isPresent())
        {
            return optionalLibrary.get();
        }
        
        return this.GetOpenIteration().getContainerOfType(EngineeringModel.class)
                .getEngineeringModelSetup().getRequiredRdl().stream().findFirst().orElse(null);
    }
    
    /**
     * Opens the {@linkplain Session}
     * 
     * @param credentials the {@link Credentials}
     * @return A {@link Boolean} indicating whether opening the session succeeded
     */
    @Override
    public Boolean Open(Credentials credentials)
    {
        this.session = new SessionImpl(new CdpServicesDal(), credentials); 

        this.session.open().join();
                
        return this.session.getAssembler().retrieveSiteDirectory() != null;
    }    

    /**
     * Gets the {@link EngineeringModelSetup}s contained in the site directory
     *  
     * @return A {@link ContainerList} of {@link EngineeringModelSetup}
     */
    @Override
    public ContainerList<EngineeringModelSetup> GetEngineeringModels()
    {
        SiteDirectory siteDirectory = this.GetSiteDirectory();
        
        if (siteDirectory != null)
        {
            return siteDirectory.getModel();
        }
        
        return new ContainerList<>(siteDirectory);
    }

    /**
     * Retrieves the <{@link SiteDirectory} that is loaded in the <{@link Session}
     * 
     * @return the {@link SiteDirectory}
     */
    @Override
    public SiteDirectory GetSiteDirectory()
    {
        if (this.session != null)
        {
            return this.session.retrieveSiteDirectory();            
        }
        
        return null;
    }
    
    /**
     * Gets the data source URI as string of the current {@linkplain Session}
     * 
     * @return a string representation of the URI or a empty string
     */
    @Override
    public String GetDataSourceUri()
    {
        if (this.session != null)
        {
            return this.session.getDataSourceUri();
        }
        
        return "";
    }
    
    /**
     * Gets the active person
     * 
     * @return The active {@link Person}
     */
    @Override
    public Person GetActivePerson()
    {
        if (this.session != null)
        {
            return this.session.getActivePerson();            
        }
        
        return null;
    }    
    
    /**
     * Reads an {@link Iteration} and set the active {@link DomainOfExpertise} for the {@link Iteration}
     * 
     * @param The {@link Iteration} to read
     * @param The {@link Domain} that reads the {@link Iteration}
     * @throws NotFoundException 
     */
    @Override
    public void GetIteration(Iteration iteration, DomainOfExpertise domain) throws NotFoundException
    {
        this.session.read(iteration, domain).join();
        ImmutableMap<Iteration, Pair<DomainOfExpertise, Participant>> iterationDomainAndParticipant = this.GetIteration();
        
        this.openIteration = iterationDomainAndParticipant.keySet().stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("The Iteration %s was not found"));
                
        this.currentDomainOfExpertise = iterationDomainAndParticipant.entrySet().stream()
                .map(x -> x.getValue().getLeft())
                .findFirst()
                .orElseThrow(() -> new NotFoundException("The Iteration %s was not found"));
        
        this.SetIsSessionOpen(this.openIteration != null);
    }

    /**
     * Reads an {@link Iteration} and set the active @link DomainOfExpertise for the Iteration
     * 
     * @return a {@link ImmutableMap} of <{@link Iteration}, {@link Pair} <{@link DomainOfExpertise}, {@link Participant} >>
     */
    @Override
    public ImmutableMap<Iteration, Pair<DomainOfExpertise, Participant>> GetIteration()
    {
        return this.session.getOpenIterations();
    }

    /**
     * Gets the collection of available {@linkplain ExternalIdentifierMap} for the provided DST tool name
     * 
     * @param toolName the {@linkplain String} DST tool name
     * @return a {@linkplain Collection} of {@linkplain ExternalIdentifierMap}
     */
    @Override
    public Collection<ExternalIdentifierMap> GetAvailableExternalIdentifierMap(String toolName)
    {
        return this.openIteration.getExternalIdentifierMap()
              .stream()
              .filter(x -> AreTheseEquals(x.getExternalToolName(), toolName))
              .collect(Collectors.toList());
    }
    
    /**
     * Closes the connection to the data-source
     */
    @Override
    public void Close()
    {
        if (Boolean.FALSE.equals(this.isSessionOpen))
        {
            this.logger.info("At first a connection should be opened.");
            return;
        }

        try
        {
            this.session.close().join();
            this.session = null;
            this.SetIsSessionOpen(false);
            this.currentDomainOfExpertise = null;
            this.openIteration = null;
        }
        catch (Exception exception)
        {
            this.logger.error(MessageFormat.format("During close operation an error has occured: {0}", exception));
        }
    }
    
    /**
     * Reloads the {@link Session}
     * 
     * @return A value indicating whether the {@link future} completed with success
     */
    @Override
    public boolean Reload()
    {
        boolean result = this.RefreshOrReload(this.session.reload());
        this.sessionEvent.Value(result);

        return result;
    }

    /**
     * Reloads the {@link Session}
     * 
     * @return A value indicating whether the {@link future} completed with success
     */
    @Override
    public boolean Refresh()
    {
        boolean result = this.RefreshOrReload(this.session.refresh());
        this.sessionEvent.Value(result);
        
        
        return result;
    }
    
    /**
     * Refresh the specified library local cache by reading it
     * 
     * @param library the {@linkplain ReferenceDataLibrary} to refresh
     */
    @Override
    public void RefreshReferenceDataLibrary(ReferenceDataLibrary library)
    {
        this.RefreshOrReload(this.session.read(library));
    }
    
    /**
     * Calls the {@link future} wrapped in try block and return a {@link Boolean}
     * 
     * @param future a {@link CompletableFuture}
     * @return A value indicating whether the {@link future} completed with success
     */
    private boolean RefreshOrReload(CompletableFuture<Void> future)
    {
        try
        {
            future.get();
            return true;
        } 
        catch (InterruptedException interruptedException)
        {
            this.logger.catching(interruptedException);
            Thread.currentThread().interrupt();
        } 
        catch (ExecutionException executionException)
        {
            this.logger.catching(executionException);
        }
        
        return false;
    }

    /**
     * Loads an {@link Iteration} with the selected {@link DomainOfExpertise}
     * 
     * @param engineeringModelSetup
     * @param iterationSetup
     * @param domainOfExpertise
     * @return A value indicating whether the operation went well
     */
    @Override
    public boolean OpenIteration(EngineeringModelSetup engineeringModelSetup, IterationSetup iterationSetup, DomainOfExpertise domainOfExpertise)
    {
        try
        {
            try(EngineeringModel model = new EngineeringModel(engineeringModelSetup.getEngineeringModelIid(), 
                    this.session.getAssembler().getCache(), this.session.getCredentials().getUri()))
            {
                model.setEngineeringModelSetup(engineeringModelSetup);

                Iteration iteration = new Iteration(iterationSetup.getIterationIid(),
                    this.session.getAssembler().getCache(), this.session.getCredentials().getUri());

                model.getIteration().add(iteration);
    
                this.GetIteration(iteration, domainOfExpertise);
            }
            
            return true;
        }
        catch (Exception exception)
        {
            this.logger.catching(exception);
            return false;
        }
    }

    /**
     * Gets the {@linkplain Thing} by it's Iid from the cache
     * 
     * @param <TThing> the type of {@linkplain Thing} to retrieve
     * @param iid the Iid of the {@linkplain Thing} to retrieve from the cache
     * @param refThing the {@linkplain Ref} of {@linkplain TThing} as ref parameter
     */
    @Override
    @SuppressWarnings("unchecked")
    public <TThing extends Thing> boolean TryGetThingById(UUID iid, Ref<TThing> refThing)
    {
        @Nullable Thing thing = this.session.getAssembler().getCache().getIfPresent(new CacheKey(iid, this.openIteration.getIid()));
        
        if (thing != null && thing.getClass().isAssignableFrom(refThing.GetType()))
        {
            refThing.Set((TThing)thing);
        }

        return refThing.HasValue();
    }
    
    /**
     * Gets the thing by predicate on {@linkplain Thing} from the chain of rdls
     * 
     * @param predicate the predicate on {@linkplain Thing}
     * @return An assert whether the thing has been found
     */
    @Override
    @SuppressWarnings("unchecked")
    public <TThing extends Object> boolean TryGetThingFromChainOfRdlBy(Predicate<TThing> predicate, Ref<TThing> thing)
    {
        Function<? super ReferenceDataLibrary, ? extends Stream<?>> collectionSelector = null;
        
        if(Category.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryCategoriesFromChainOfRdls().stream();
        }
        else if(Rule.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryRulesFromChainOfRdls().stream();
        }
        else if(Constant.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryConstantsFromChainOfRdls().stream();
        }
        else if(FileType.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryFileTypesFromChainOfRdls().stream();
        }
        else if(Glossary.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryGlossariesFromChainOfRdls().stream();
        }
        else if(MeasurementScale.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryMeasurementScalesFromChainOfRdls().stream();
        }
        else if(MeasurementUnit.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryMeasurementUnitsFromChainOfRdls().stream();
        }
        else if(ReferenceSource.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryReferenceSourcesFromChainOfRdls().stream();
        }
        else if(UnitPrefix.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryUnitPrefixesFromChainOfRdls().stream();
        }
        else if(ParameterType.class.isAssignableFrom(thing.GetType()))
        {
            collectionSelector = x -> x.queryParameterTypesFromChainOfRdls().stream();
        }
        
        if (collectionSelector == null)
        {
            return false;
        }

        Optional<Object> optionalThing = this.OpenReferenceDataLibraries().stream()
                .flatMap(collectionSelector)
                .filter((Predicate<? super Object>) predicate)
                .filter(x -> !((DeprecatableThing)x).isDeprecated())
                .findFirst();
        
        if (optionalThing.isPresent() && thing.GetType().isAssignableFrom(optionalThing.get().getClass()))
        {
            thing.Set((TThing)optionalThing.get());
        }

        return thing.HasValue();
    }

    /**
     * Creates or updates the things in the specified {@linkplain ThingTransaction}
     * 
     * @param transaction the {@linkplain ThingTransaction}
     * @throws Exception 
     */
    @Override
    public void Write(ThingTransaction transaction) throws DalWriteException, CompletionException
    {
        this.session.write(transaction.finalizeTransaction()).join();
    }    

    /**
     * Tries to create a {@linkplain LogEntry} base on the input from the {@linkplain LogEntryDialog}
     * 
     * @param transaction the {@linkplain ThingTransaction}
     * @return a value indicating whether the whole transaction should be cancelled based on the dialog result
     * @throws TransactionException 
     */
    @Override
    public boolean TrySupplyAndCreateLogEntry(ThingTransaction transaction) throws TransactionException
    {
        Pair<String, Boolean> dialogResult = this.navigationService.ShowDialog(new LogEntryDialog());
        
        if(!Boolean.TRUE.equals(dialogResult.getRight()))
        {
            return false;
        }
        
        this.RegisterLogEntry(dialogResult.getLeft(), transaction);
        return true;
    }
    
    /**
     * Adds a new {@linkplain ModelLogEntry} record to the {@linkplain EngineeringModel}
     *  and registers the change to the provided {@linkplain ThingTransaction}
     * 
     * @param content the {@linkplain String} content
     * @param the {@linkplain ThingTransaction}
     * @throws TransactionException 
     */
    @Override
    public void RegisterLogEntry(String content, ThingTransaction transaction) throws TransactionException
    {
        if (StringUtils.isBlank(content))
        {
            return;
        }

        ModelLogEntry logEntry = new ModelLogEntry();
        logEntry.setIid(UUID.randomUUID());
        logEntry.setContent(content);
        logEntry.setAuthor(this.GetActivePerson());
        logEntry.setLevel(LogLevelKind.USER);
        logEntry.setLanguageCode("en-GB");

        EngineeringModel clonedEngineeringModel = this.GetOpenIteration().getContainerOfType(EngineeringModel.class).clone(false);
        clonedEngineeringModel.getLogEntry().add(logEntry);

        transaction.createOrUpdate(logEntry);
        transaction.createOrUpdate(clonedEngineeringModel);
    }
    
    /**
     * Initializes a new {@linkplain ThingTransaction} based on the current open {@linkplain Iteration}
     * 
     * @return a {@linkplain Pair} of {@linkplain Iteration} cloned and its {@linkplain ThingTransaction}
     * @throws TransactionException
     */
    @Override
    public Pair<Iteration, ThingTransaction> GetIterationTransaction() throws TransactionException
    {
        Iteration iterationClone = this.GetOpenIteration().clone(false);
        return Pair.of(iterationClone, new ThingTransactionImpl(TransactionContextResolver.resolveContext(iterationClone), iterationClone));
    }
}
