/*
 * MappingConfigurationService.java
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
package Services.MappingConfiguration;

import static Utils.Operators.Operators.AreTheseEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import Enumerations.MappingDirection;
import HubController.IHubController;
import Utils.Ref;
import ViewModels.Interfaces.IMappedElementRowViewModel;
import cdp4common.engineeringmodeldata.ExternalIdentifierMap;
import cdp4common.engineeringmodeldata.IdCorrespondence;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4dal.exceptions.TransactionException;
import cdp4dal.operations.ThingTransaction;

/**
 * The {@linkplain MappingConfigurationServiceTest} is the base abstract class for concrete implementations in adapters 
 * that take care of handling all operation related to saving and loading configured mapping.
 * 
 * @param <TDstElement> the type of element the dst adapter works with
 * @param <TExternalIdentifier> the type of external identifer json class
 */
public abstract class MappingConfigurationService<TDstElement, TExternalIdentifier extends ExternalIdentifier> implements IMappingConfigurationService<TExternalIdentifier>
{
    /**
     * The {@linkplain Class} of {@linkplain #TExternalIdentifier}
     */
    private Class<TExternalIdentifier> externalIdentifierClass;
    
    /**
     * The current class {@linkplain Logger}
     */
    protected Logger logger = LogManager.getLogger();
    
    /**
     * The {@linkplain IHubController}
     */
    protected IHubController hubController;

    /**
     * The collection of id correspondence as {@linkplain ImmutableTriple} of {@code Pair<UUID correspondenceId, ExternalIdentifier externalIdentifier, UUID internalId>}
     */
    protected ArrayList<ImmutableTriple<UUID, TExternalIdentifier, UUID>> correspondences = new ArrayList<>();

    /**
     * Backing field for {@linkplain GetExternalIdentifierMap}
     */
    private ExternalIdentifierMap externalIdentifierMap = new ExternalIdentifierMap();
        
    /**
     * Gets the {@linkplain ExternalIdentifierMap} the {@linkplain MappingConfigurationServiceTest} works with
     * 
     * @return a {@linkplain ExternalIdentifierMap}
     */
    @Override
    public ExternalIdentifierMap GetExternalIdentifierMap()
    {
        return this.externalIdentifierMap;
    }
    
    /**
     * Sets the {@linkplain ExternalIdentifierMap} and parses the {@linkplain IdCorrespondence}s
     * 
     * @param externalIdentifierMap the {@linkplain ExternalIdentifierMap} to assign
     */
    @Override
    public void SetExternalIdentifierMap(ExternalIdentifierMap externalIdentifierMap)
    {
        this.externalIdentifierMap = externalIdentifierMap.clone(true);
        this.ParseIdCorrespondence();
    }

    /**
     * Gets a value indicating whether the current {@linkplain ExternalIdentifierMap} is a default one
     * 
     * @return a {@linkplain boolean}
     */
    @Override
    public boolean IsTheCurrentIdentifierMapTemporary()
    {
        return this.externalIdentifierMap.getIid() == null;
    }
    
    /**
     * Initializes a new {@linkplain MappingConfigurationServiceTest}
     * 
     * @param hubController the {@linkplain IHubController}
     * @param externalIdentifierClass the {@linkplain Class} of {@linkplain #TExternalIdentifier}
     */
    protected MappingConfigurationService(IHubController hubController, Class<TExternalIdentifier> externalIdentifierClass)
    {
        this.hubController = hubController;
        this.externalIdentifierClass = externalIdentifierClass;
    }
    
    /**
     * Refreshes the {@linkplain ExternalIdentifierMap}, usually done after a session write
     */
    @Override
    public void RefreshExternalIdentifierMap()
    {
        if(this.IsTheCurrentIdentifierMapTemporary())
        {
            return;
        }
        
        Ref<ExternalIdentifierMap> refExternalIdentifierMap = new Ref<>(ExternalIdentifierMap.class);        
        
        if(this.hubController.TryGetThingById(this.externalIdentifierMap.getIid(), refExternalIdentifierMap))
        {
            this.SetExternalIdentifierMap(refExternalIdentifierMap.Get().clone(true));
            return;
        }
        
        this.logger.error(String.format("The %s was unable to reload the ExternalIdentifierMap from the cache.", this.getClass().getSimpleName()));
    }
        
    /**
     * Updates the configured mapping, registering the {@linkplain ExternalIdentifierMap} and its {@linkplain IdCorrespondence}s
     * 
     * @param transaction the {@linkplain ThingTransaction}
     * @param iterationClone the {@linkplain Iteration} clone
     * @throws TransactionException 
     */
    @Override
    public void PersistExternalIdentifierMap(ThingTransaction transaction, Iteration iterationClone) throws TransactionException
    {
        if(this.IsTheCurrentIdentifierMapTemporary())
        {
            this.logger.error(String.format("The current mapping (%s correspondences) will not be saved, "
                    + "as it is tempary until the user creates one or loads an existing one.",
                    this.externalIdentifierMap.getCorrespondence().size()));
            
            return;
        }
        
        if (this.externalIdentifierMap.getOriginal() == null || this.externalIdentifierMap.getRevisionNumber() < 1)
        {
            iterationClone.getExternalIdentifierMap().add(this.externalIdentifierMap);
        }

        for (IdCorrespondence correspondence : this.externalIdentifierMap.getCorrespondence())
        {
            transaction.createOrUpdate(correspondence);    
        }

        transaction.createOrUpdate(this.externalIdentifierMap);
    }
    
    /**
     * Creates a new {@linkplain ExternalIdentifierMap} and sets the current as the new one
     *
     * @param newName the {@linkplain String} name of the new configuration
     * @param addTheTemporyMapping a value indicating whether the current temporary {@linkplain ExternalIdentifierMap} 
     * contained correspondence should be transfered the new one
     * @return the new configuration {@linkplain ExternalIdentifierMap}
     */
    protected ExternalIdentifierMap CreateExternalIdentifierMap(String newName, String modelName, String toolName, boolean addTheTemporyMapping)
    {        
        ExternalIdentifierMap identifierMap = new ExternalIdentifierMap();
        
        identifierMap.setIid(UUID.randomUUID());
        identifierMap.setName(newName);
        identifierMap.setExternalModelName(StringUtils.isBlank(modelName) ? newName : modelName);
        identifierMap.setExternalToolName(toolName);
        identifierMap.setOwner(this.hubController.GetCurrentDomainOfExpertise());
        
        if(addTheTemporyMapping)
        {
            identifierMap.getCorrespondence().addAll(this.externalIdentifierMap.getCorrespondence());
        }
        
        return identifierMap;
    }
    
    /**
     * Parses the {@linkplain ExternalIdentifierMap} Correspondences and adds these to the {@linkplain Correspondences} collection
     */
    private void ParseIdCorrespondence()
    {
        this.correspondences.clear();

        StopWatch timer = StopWatch.createStarted();

        this.correspondences.addAll(this.externalIdentifierMap
                .getCorrespondence()
                .stream()
                .map(x -> ImmutableTriple.of(x.getIid(), new Gson().fromJson(x.getExternalId(), this.externalIdentifierClass), x.getInternalThing()))
                .collect(Collectors.toList()));

        timer.stop();
        
        this.logger.info(String.format("%s ExternalIdentifiers deserialized in %s ms", this.correspondences.size(), timer.getTime(TimeUnit.MILLISECONDS)));
    }

    /**
     * Loads the mapping configuration and generates the map result respectively
     * 
     * @param elements a {@linkplain Collection} of {@code TDstElement}
     * @return a {@linkplain Collection} of {@linkplain IMappedElementRowViewModel}
     */
    public abstract Collection<IMappedElementRowViewModel> LoadMapping(Collection<TDstElement> elements);

    /**
     * Adds one correspondence to the {@linkplain ExternalIdentifierMap}
     * 
     * @param internalId the {@linkplain UUID} that identifies the thing to correspond to
     * @param externalId the {@linkplain Object} that identifies the object to correspond to
     * @param mappingDirection the {@linkplain MappingDirection} the mapping belongs to
     */
    @Override
    public void AddToExternalIdentifierMap(UUID internalId, Object externalId, MappingDirection mappingDirection)
    {
        this.AddToExternalIdentifierMap(internalId, this.InitializeExternalIdentifier(externalId, mappingDirection), x -> x != null);
    }
    

    /**
     * Adds one correspondence to the {@linkplain ExternalIdentifierMap}
     * 
     * @param internalId the {@linkplain UUID} that identifies the thing to correspond to
     * @param externalId the {@linkplain Object} that identifies the object to correspond to
     * @param mappingDirection the {@linkplain MappingDirection} the mapping belongs to
     * @param filter a {@linkplain Predicate} to find any existing mapping
     */
    @Override
    public void AddToExternalIdentifierMap(UUID internalId, Object externalId, MappingDirection mappingDirection, Predicate<ImmutableTriple<UUID, TExternalIdentifier, UUID>> filter)
    {
        this.AddToExternalIdentifierMap(internalId, this.InitializeExternalIdentifier(externalId, mappingDirection), filter, x -> x != null);
    }

    /**
     * Initializes a new {@linkplain #TExternalIdentifier} based on the provided external id and the {@linkplain MappingDirection}
     * 
     * @param externalId the {@linkplain Object} external identifier
     * @param mappingDirection the {@linkplain MappingDirection}
     */
    private TExternalIdentifier InitializeExternalIdentifier(Object externalId, MappingDirection mappingDirection)
    {
        try
        {
            TExternalIdentifier externalIdentifier = this.externalIdentifierClass.newInstance(); 
            externalIdentifier.Identifier = externalId;
            externalIdentifier.MappingDirection = mappingDirection;
            return externalIdentifier;
        }
        catch (InstantiationException | IllegalAccessException exception)
        {
            this.logger.catching(exception);
            return null;
        }
    }
    
    
    /**
     * Adds one correspondence to the {@linkplain ExternalIdentifierMap} 
     * 
     * @param internalId The thing that the ExternalIdentifier corresponds to
     * @param externalIdentifier The external thing that the internal id corresponds to
     * @param extraFilter A {@linkplain Predicate} that allows to filter on other properties while this method checks for existing mapping
     */
    @Override
    public void AddToExternalIdentifierMap(UUID internalId, TExternalIdentifier externalIdentifier, Predicate<ImmutableTriple<UUID, TExternalIdentifier, UUID>> extraFilter)
    {        
        Predicate<ImmutableTriple<UUID, TExternalIdentifier, UUID>> filter = x -> AreTheseEquals(x.getRight(), internalId)
                && AreTheseEquals(externalIdentifier.Identifier, x.getMiddle().Identifier)
                && externalIdentifier.MappingDirection == x.getMiddle().MappingDirection;

        this.AddToExternalIdentifierMap(internalId, externalIdentifier, filter, extraFilter);
    }

    /**
     * Adds one correspondence to the {@linkplain ExternalIdentifierMap}
     * 
     * @param internalId the internal thing {@linkplain UUID}
     * @param externalIdentifier the {@linkplain #TExternalIdentifier}
     * @param filter a {@linkplain Predicate} to find any existing mapping
     * @param extraFilter a extra filter {@linkplain Predicate}
     */
    @Override
    public void AddToExternalIdentifierMap(UUID internalId, TExternalIdentifier externalIdentifier,
            Predicate<ImmutableTriple<UUID, TExternalIdentifier, UUID>> filter,
            Predicate<ImmutableTriple<UUID, TExternalIdentifier, UUID>> extraFilter)
    {
        if(extraFilter == null)
        {
            extraFilter = x -> x != null;
        }
        
        Optional<UUID> correspondenceIid = this.correspondences.stream()
            .filter(filter)
            .filter(extraFilter)
            .map(x -> x.left)
            .findFirst();
        
        if(correspondenceIid.isPresent())
        {
            this.TryUpdateExistingCorrespondence(internalId, externalIdentifier, correspondenceIid.get());
        }
        
        IdCorrespondence idCorrespondence = new IdCorrespondence();
        idCorrespondence.setIid(UUID.randomUUID());
        idCorrespondence.setExternalId(new Gson().toJson(externalIdentifier));
        idCorrespondence.setInternalThing(internalId);
                
        this.externalIdentifierMap.getCorrespondence().add(idCorrespondence);
    }

    /**
     * Adds one correspondence to the {@linkplain ExternalIdentifierMap} 
     * 
     * @param internalId The thing that the ExternalIdentifier corresponds to
     * @param externalIdentifier The external thing that the internal id corresponds to
     * @return a value indicating whether the existing correspondence was updated
     */
    public boolean TryUpdateExistingCorrespondence(UUID internalId, TExternalIdentifier externalIdentifier, UUID correspondenceIid)
    {
        Optional<IdCorrespondence> existingCorrespondence = this.externalIdentifierMap
                .getCorrespondence()
                .stream()
                .filter(e -> AreTheseEquals(correspondenceIid, e.getIid()))
                .findFirst();
        
        if (existingCorrespondence.isPresent())
        {
            existingCorrespondence.get().setInternalThing(internalId);
            existingCorrespondence.get().setExternalId(new Gson().toJson(externalIdentifier));
            return true;
        }
        
        return false;
    }
}
