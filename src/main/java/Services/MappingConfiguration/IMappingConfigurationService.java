/*
 * IMappingConfigurationService.java
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

import java.util.UUID;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.ImmutableTriple;

import Enumerations.MappingDirection;
import cdp4common.engineeringmodeldata.ExternalIdentifierMap;
import cdp4common.engineeringmodeldata.IdCorrespondence;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4dal.exceptions.TransactionException;
import cdp4dal.operations.ThingTransaction;

/**
 * The {@linkplain IMappingConfigurationService} is the interface definition for the {@linkplain MappingConfigurationServiceTest}
 * 
 * @param <TExternalIdentifier> the type of {@linkplain ExternalIdentifier} the dst adapter uses
 */
public interface IMappingConfigurationService<TExternalIdentifier extends ExternalIdentifier>
{
    /**
     * Adds one correspondence to the {@linkplain ExternalIdentifierMap} 
     * 
     * @param internalId The thing that the ExternalIdentifier corresponds to
     * @param externalIdentifier The external thing that the internal id corresponds to
     * @param extraFilter A {@linkplain Predicate} that allows to filter on other properties while this method checks for existing mapping
     */
    void AddToExternalIdentifierMap(UUID internalId, TExternalIdentifier externalIdentifier, Predicate<ImmutableTriple<UUID, TExternalIdentifier, UUID>> extraFilter);

    /**
     * Adds one correspondence to the {@linkplain ExternalIdentifierMap}
     * 
     * @param internalId the {@linkplain UUID} that identifies the thing to correspond to
     * @param externalId the {@linkplain Object} that identifies the object to correspond to
     * @param mappingDirection the {@linkplain MappingDirection} the mapping belongs to
     */
    void AddToExternalIdentifierMap(UUID internalId, Object externalId, MappingDirection mappingDirection);

    /**
     * Updates the configured mapping, registering the {@linkplain ExternalIdentifierMap} and its {@linkplain IdCorrespondence}s
     * 
     * @param transaction the {@linkplain ThingTransaction}
     * @param iterationClone the {@linkplain Iteration} clone
     * @throws TransactionException 
     */
    void PersistExternalIdentifierMap(ThingTransaction transaction, Iteration iterationClone) throws TransactionException;

    /**
     * Refreshes the {@linkplain ExternalIdentifierMap}, usually done after a session write
     */
    void RefreshExternalIdentifierMap();

    /**
     * Sets the {@linkplain ExternalIdentifierMap} and parses the {@linkplain IdCorrespondence}s
     * 
     * @param externalIdentifierMap the {@linkplain ExternalIdentifierMap} to assign
     */
    void SetExternalIdentifierMap(ExternalIdentifierMap externalIdentifierMap);

    /**
     * Gets the {@linkplain ExternalIdentifierMap} the {@linkplain MappingConfigurationServiceTest} works with
     * 
     * @return a {@linkplain ExternalIdentifierMap}
     */
    ExternalIdentifierMap GetExternalIdentifierMap();

    /**
     * Creates a new {@linkplain ExternalIdentifierMap} and sets the current as the new one
     * 
     * @param newName the {@linkplain String} name of the new configuration
     * @param addTheTemporyMapping a value indicating whether the current temporary {@linkplain ExternalIdentifierMap} 
     * contained correspondence should be transfered the new one
     * 
     * @return the new configuration {@linkplain ExternalIdentifierMap}
     */
    ExternalIdentifierMap CreateExternalIdentifierMap(String newName, String modelName, boolean addTheTemporyMapping);

    /**
     * Gets a value indicating whether the current {@linkplain ExternalIdentifierMap} is a default one
     * 
     * @return a {@linkplain boolean}
     */
    boolean IsTheCurrentIdentifierMapTemporary();
}
