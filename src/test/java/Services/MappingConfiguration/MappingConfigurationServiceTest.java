/*
* MappingConfigurationService.java
*
* Copyright (c) 2020-2022 RHEA System S.A.
*
* Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski, Antoine Théate
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
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program; if not, write to the Free Software Foundation,
* Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/
package Services.MappingConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Enumerations.MappingDirection;
import HubController.IHubController;
import Services.MappingEngineService.TestRules.Sphere;
import Utils.Ref;
import ViewModels.Interfaces.IMappedElementRowViewModel;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.ExternalIdentifierMap;
import cdp4common.engineeringmodeldata.IdCorrespondence;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4dal.exceptions.TransactionException;
import cdp4dal.operations.ThingTransaction;

class MappingConfigurationServiceTest
{
	MappingConfigurationService<Sphere, ExternalIdentifier> service;
	IHubController hubController;

	@BeforeEach
	void setUp() throws Exception
	{
		this.hubController = mock(IHubController.class);

		this.service = new MappingConfigurationService<Sphere, ExternalIdentifier>(this.hubController, ExternalIdentifier.class)
		{
			@Override
			public ExternalIdentifierMap CreateExternalIdentifierMap(String newName, String modelName,
					boolean addTheTemporyMapping)
			{
				return this.CreateExternalIdentifierMap(newName, modelName, "TestTool", addTheTemporyMapping);
			}

			@Override
			public Collection<IMappedElementRowViewModel> LoadMapping(Collection<Sphere> elements)
			{
				return new ArrayList<>();
			}
		};
	}

	@Test
	void VerifyProperties()
	{
		assertTrue(this.service.IsTheCurrentIdentifierMapTemporary());
		assertNotNull(this.service.GetExternalIdentifierMap());
		String mapName = "map";
		this.service.SetExternalIdentifierMap(this.service.CreateExternalIdentifierMap(mapName, mapName, false));
		assertFalse(this.service.IsTheCurrentIdentifierMapTemporary());
		assertEquals(mapName, this.service.GetExternalIdentifierMap().getName());
	}

	@SuppressWarnings("unchecked")
	@Test
	void VerifyRefreshExternalIdentifierMap()
	{
		when(this.hubController.TryGetThingById(any(UUID.class), any(Ref.class))).thenReturn(false);
		assertDoesNotThrow(() -> this.service.RefreshExternalIdentifierMap());

		String mapName = "map";
		this.service.SetExternalIdentifierMap(this.service.CreateExternalIdentifierMap(mapName, mapName, true));
		assertDoesNotThrow(() -> this.service.RefreshExternalIdentifierMap());
		assertNull(this.service.GetExternalIdentifierMap().getOriginal().getOriginal());

		when(this.hubController.TryGetThingById(any(UUID.class), any(Ref.class))).thenAnswer(x -> {
			Ref<ExternalIdentifierMap> refMap = x.getArgument(1, Ref.class);
			refMap.Set(this.service.GetExternalIdentifierMap());
			return true;
		});

		assertDoesNotThrow(() -> this.service.RefreshExternalIdentifierMap());
		assertNotNull(this.service.GetExternalIdentifierMap().getOriginal().getOriginal());
	}

	@Test
	void VerifyPersistExternalIdentifierMap() throws TransactionException
	{
		ThingTransaction transaction = mock(ThingTransaction.class);
		Iteration iteration = new Iteration();

		assertDoesNotThrow(() -> this.service.PersistExternalIdentifierMap(transaction, iteration));

		this.service.GetExternalIdentifierMap().setIid(UUID.randomUUID());
		this.service.GetExternalIdentifierMap().getCorrespondence().add(new IdCorrespondence());
		assertDoesNotThrow(() -> this.service.PersistExternalIdentifierMap(transaction, iteration));
		verify(transaction, times(2)).createOrUpdate(any(Thing.class));

		this.service.SetExternalIdentifierMap(this.service.CreateExternalIdentifierMap("map", "map", false));
		assertDoesNotThrow(() -> this.service.PersistExternalIdentifierMap(transaction, iteration));

		this.service.GetExternalIdentifierMap().setRevisionNumber(2);
		assertDoesNotThrow(() -> this.service.PersistExternalIdentifierMap(transaction, iteration));

		verify(transaction, times(4)).createOrUpdate(any(Thing.class));
		assertEquals(2, iteration.getExternalIdentifierMap().size());
	}

	@Test
	void VerifyAddToExternalIdentifierMap()
	{
		this.service.AddToExternalIdentifierMap(UUID.randomUUID(), UUID.randomUUID().toString(),
				MappingDirection.FromDstToHub);
		assertEquals(1, this.service.GetExternalIdentifierMap().getCorrespondence().size());

		UUID internalId = UUID.randomUUID();
		String externalId = UUID.randomUUID().toString();
		ExternalIdentifier externalIdentifier = new ExternalIdentifier();
		externalIdentifier.Identifier = externalId;

		this.service.AddToExternalIdentifierMap(internalId, externalIdentifier,
				(Predicate<ImmutableTriple<UUID, ExternalIdentifier, UUID>>) null);
		assertEquals(2, this.service.GetExternalIdentifierMap().getCorrespondence().size());

		externalIdentifier.MappingDirection = MappingDirection.FromHubToDst;
		this.service.AddToExternalIdentifierMap(internalId, externalIdentifier,
				(Predicate<ImmutableTriple<UUID, ExternalIdentifier, UUID>>) null);
		assertEquals(3, this.service.GetExternalIdentifierMap().getCorrespondence().size());

		this.service.SetExternalIdentifierMap(this.service.CreateExternalIdentifierMap("map", externalId, true));
		this.service.AddToExternalIdentifierMap(internalId, externalIdentifier,
				(Predicate<ImmutableTriple<UUID, ExternalIdentifier, UUID>>) null);

		assertEquals(3, this.service.GetExternalIdentifierMap().getCorrespondence().size());
	}
}
