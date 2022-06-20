/*
 * HubControllerTest.java
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import Services.NavigationService.INavigationService;
import Utils.Ref;
import Views.Dialogs.LogEntryDialog;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.EngineeringModel;
import cdp4common.engineeringmodeldata.ExternalIdentifierMap;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.sitedirectorydata.Category;
import cdp4common.sitedirectorydata.Constant;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EngineeringModelSetup;
import cdp4common.sitedirectorydata.FileType;
import cdp4common.sitedirectorydata.Glossary;
import cdp4common.sitedirectorydata.IterationSetup;
import cdp4common.sitedirectorydata.MeasurementScale;
import cdp4common.sitedirectorydata.MeasurementUnit;
import cdp4common.sitedirectorydata.ModelReferenceDataLibrary;
import cdp4common.sitedirectorydata.ParameterType;
import cdp4common.sitedirectorydata.Participant;
import cdp4common.sitedirectorydata.Person;
import cdp4common.sitedirectorydata.ReferenceDataLibrary;
import cdp4common.sitedirectorydata.ReferenceSource;
import cdp4common.sitedirectorydata.Rule;
import cdp4common.sitedirectorydata.SiteDirectory;
import cdp4common.sitedirectorydata.UnitPrefix;
import cdp4common.types.ContainerList;
import cdp4dal.Assembler;
import cdp4dal.Session;
import cdp4dal.dal.Credentials;
import cdp4dal.operations.OperationContainer;
import cdp4dal.operations.ThingTransaction;

class HubControllerTest
{
	private HubController controller;
	private Session session;
	private INavigationService navigationService;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception
	{
		this.navigationService = mock(INavigationService.class);
		this.controller = new HubController(this.navigationService);
		this.session = mock(Session.class);
	}

	@Test
	void VerifyProperties()
	{
		assertNotNull(this.controller.GetIsSessionOpenObservable());
		assertNotNull(this.controller.GetSessionEventObservable());
		assertNull(this.controller.GetOpenIteration());
	}

	@Test
	void VerifyGetEngineeringModels() throws Exception
	{
		assertEquals(0, this.controller.GetEngineeringModels().size());

		this.SetSession();
		SiteDirectory siteDirectory = new SiteDirectory();
		ContainerList<EngineeringModelSetup> engineeringModelSetups = new ContainerList<>(
				siteDirectory);
		engineeringModelSetups.add(new EngineeringModelSetup());
		siteDirectory.setModel(engineeringModelSetups);
		when(this.session.retrieveSiteDirectory()).thenReturn(siteDirectory);
		assertSame(engineeringModelSetups, this.controller.GetEngineeringModels());
	}

	@Test
	void VerifyGetActivePerson() throws Exception
	{
		assertNull(this.controller.GetActivePerson());
		this.SetSession();
		Person person = new Person();
		when(this.session.getActivePerson()).thenReturn(person);
		assertSame(person, this.controller.GetActivePerson());
	}

	@Test
	void VerifyGetIteration() throws Exception
	{
		this.SetIteration();

		assertTrue(this.controller.GetIsSessionOpen());
	}

	@Test
	void VerifyReloadOrRefresh() throws Exception
	{
		this.SetSession();
		assertThrows(NullPointerException.class, () -> this.controller.Reload());
		assertThrows(NullPointerException.class, () -> this.controller.Refresh());

		when(this.session.reload()).thenReturn(CompletableFuture.completedFuture(null));
		when(this.session.refresh()).thenReturn(CompletableFuture.completedFuture(null));

		assertTrue(this.controller.Reload());
		assertTrue(this.controller.Refresh());
	}

	@Test
	void VerifyOpenIteration() throws Exception
	{
		this.SetIteration();
		assertTrue(this.controller.OpenIteration(new EngineeringModelSetup(), new IterationSetup(),
				new DomainOfExpertise()));
	}

	@Test
	void VerifyGetCurrentDomainOfexpertise()
	{
		assertNull(this.controller.GetCurrentDomainOfExpertise());
	}

	@Test
	void VerifyGetDataSourceUri() throws Exception
	{
		assertEquals("", this.controller.GetDataSourceUri());
		this.SetSession();
		when(this.session.getDataSourceUri()).thenReturn("t");
		assertEquals("t", this.controller.GetDataSourceUri());
	}

	@Test
	void VerifyClose() throws Exception
	{
		this.controller.Close();
		this.SetSession();
		Method setIsSessionOpenMethod = HubController.class.getDeclaredMethod("SetIsSessionOpen", Boolean.class);
		setIsSessionOpenMethod.setAccessible(true);
		setIsSessionOpenMethod.invoke(this.controller, true);
		assertTrue(this.controller.GetIsSessionOpen());
		assertDoesNotThrow(() -> this.controller.Close());
		assertTrue(this.controller.GetIsSessionOpen());
		when(this.session.close()).thenReturn(CompletableFuture.completedFuture(null));
		assertDoesNotThrow(() -> this.controller.Close());
	}

	@SuppressWarnings("resource")
	@Test
	void VerifyOpenReferenceDataLibraries() throws Exception
	{
		assertEquals(0, this.controller.OpenReferenceDataLibraries().size());
		this.SetSession();
		when(this.session.read(any(Iteration.class), any(DomainOfExpertise.class)))
				.thenReturn(CompletableFuture.completedFuture(null));

		ModelReferenceDataLibrary notDehpRDL = new ModelReferenceDataLibrary();
		notDehpRDL.setIid(UUID.randomUUID());
		notDehpRDL.setShortName("Common");

		IterationSetup iterationSetup = new IterationSetup();
		EngineeringModelSetup engineeringSetup = new EngineeringModelSetup();
		engineeringSetup.getIterationSetup().add(iterationSetup);
		engineeringSetup.getRequiredRdl().add(notDehpRDL);

		Iteration iteration = new Iteration();
		iteration.setIterationSetup(iterationSetup);

		EngineeringModel engineeringModel = new EngineeringModel();
		engineeringModel.setEngineeringModelSetup(engineeringSetup);
		engineeringModel.getIteration().add(iteration);

		ImmutableMap<Iteration, Pair<DomainOfExpertise, Participant>> map = ImmutableMap
				.<Iteration, Pair<DomainOfExpertise, Participant>>builder()
				.put(iteration,
						new ImmutablePair<>(new DomainOfExpertise(), new Participant()))
				.build();

		when(this.session.getOpenIterations()).thenReturn(map);

		assertDoesNotThrow(() -> this.controller.GetIteration(iteration, new DomainOfExpertise()));
		assertTrue(this.controller.GetIsSessionOpen());

		ArrayList<ReferenceDataLibrary> dataLibraries = new ArrayList<>();
		when(this.session.getOpenReferenceDataLibraries()).thenReturn(dataLibraries);
		assertEquals(0, this.controller.OpenReferenceDataLibraries().size());

		dataLibraries.add(notDehpRDL);
		assertEquals(1, this.controller.OpenReferenceDataLibraries().size());
		assertEquals(notDehpRDL.getIid(), this.controller.GetDehpOrModelReferenceDataLibrary().getIid());

		ModelReferenceDataLibrary dehpRDL = new ModelReferenceDataLibrary();
		dehpRDL.setIid(UUID.randomUUID());
		dehpRDL.setShortName("DEHP");
		dataLibraries.add(dehpRDL);
		assertEquals(2, this.controller.OpenReferenceDataLibraries().size());

		assertEquals(dehpRDL.getIid(), this.controller.GetDehpOrModelReferenceDataLibrary().getIid());
	}

	@Test
	void VerifyGetAvailableExternalIdentifierMap() throws Exception
	{
		this.SetIteration();

		ExternalIdentifierMap mdMap = new ExternalIdentifierMap();
		mdMap.setExternalToolName("MD");
		mdMap.setIid(UUID.randomUUID());

		ExternalIdentifierMap capellaMap = new ExternalIdentifierMap();
		capellaMap.setExternalToolName("Capella");
		capellaMap.setIid(UUID.randomUUID());

		this.controller.GetOpenIteration().getExternalIdentifierMap().add(mdMap);
		this.controller.GetOpenIteration().getExternalIdentifierMap().add(capellaMap);

		assertEquals(0, this.controller.GetAvailableExternalIdentifierMap("MatLab").size());
		assertEquals(1, this.controller.GetAvailableExternalIdentifierMap("MD").size());
		assertEquals(1, this.controller.GetAvailableExternalIdentifierMap("Capella").size());
		assertEquals(1, this.controller.GetAvailableExternalIdentifierMap("MD").size());
	}

	@Test
	void VerifyTryGetThing() throws Exception
	{
		this.SetIteration();

		ModelReferenceDataLibrary mRdl = new ModelReferenceDataLibrary();
		mRdl.setIid(UUID.randomUUID());
		mRdl.setShortName("Common");

		ArrayList<ReferenceDataLibrary> dataLibraries = new ArrayList<>();
		dataLibraries.add(mRdl);
		when(this.session.getOpenReferenceDataLibraries()).thenReturn(dataLibraries);

		assertEquals(1, this.controller.OpenReferenceDataLibraries().size());

		Category category = new Category(UUID.randomUUID(), null, null);
		mRdl.getDefinedCategory().add(category);

		assertFalse(this.controller.TryGetThingById(UUID.randomUUID(), new Ref<>(Category.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == UUID.randomUUID(),
				new Ref<>(Category.class)));
		assertTrue(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(Category.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(Rule.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(Constant.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(FileType.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(Glossary.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(MeasurementScale.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(MeasurementUnit.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(ReferenceSource.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(UnitPrefix.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(ParameterType.class)));
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(Parameter.class)));

		category.setDeprecated(true);
		assertFalse(this.controller.TryGetThingFromChainOfRdlBy(x -> x.getIid() == category.getIid(),
				new Ref<>(Category.class)));
	}

	@Test
	void VerifyRegisterLogEntry() throws Exception
	{
		Pair<String, Boolean> result = new MutablePair<>("", false);

		this.SetIteration();

		IterationSetup iterationSetup = new IterationSetup();
		EngineeringModelSetup engineeringSetup = new EngineeringModelSetup();
		engineeringSetup.getIterationSetup().add(iterationSetup);
		engineeringSetup.getRequiredRdl().add(new ModelReferenceDataLibrary());

		this.controller.GetOpenIteration().setIterationSetup(iterationSetup);

		@SuppressWarnings("resource")
		EngineeringModel engineeringModel = new EngineeringModel();
		engineeringModel.setEngineeringModelSetup(engineeringSetup);
		engineeringModel.getIteration().add(this.controller.GetOpenIteration());

		when(this.navigationService.ShowDialog(any(LogEntryDialog.class))).thenReturn(result);

		ThingTransaction transaction = mock(ThingTransaction.class);
		assertFalse(this.controller.TrySupplyAndCreateLogEntry(transaction));
		result.setValue(true);
		assertTrue(this.controller.TrySupplyAndCreateLogEntry(transaction));
		this.controller.RegisterLogEntry("A content", transaction);
		verify(transaction, times(2)).createOrUpdate(any(Thing.class));

		when(transaction.finalizeTransaction()).thenReturn(mock(OperationContainer.class));
		when(this.session.write(any(OperationContainer.class))).thenReturn(CompletableFuture.completedFuture(null));

		assertDoesNotThrow(() -> this.controller.Write(transaction));
	}

	@Annotations.ExludeFromCodeCoverageGeneratedReport
	private void SetIteration() throws Exception
	{
		this.SetSession();

		Assembler assembler = new Assembler(new URI("http://tes.t"));
		when(this.session.getAssembler()).thenReturn(assembler);
		when(this.session.getCredentials()).thenReturn(new Credentials("a", "p", new URI("http://tes.t"), null));
		when(this.session.read(any(Iteration.class), any(DomainOfExpertise.class)))
				.thenReturn(CompletableFuture.completedFuture(null));

		ImmutableMap<Iteration, Pair<DomainOfExpertise, Participant>> map = ImmutableMap
				.<Iteration, Pair<DomainOfExpertise, Participant>>builder()
				.put(new Iteration(),
						new ImmutablePair<>(new DomainOfExpertise(), new Participant()))
				.build();

		when(this.session.getOpenIterations()).thenReturn(map);
		this.controller.GetIteration(new Iteration(), new DomainOfExpertise());
	}

	@Annotations.ExludeFromCodeCoverageGeneratedReport
	private void SetSession() throws Exception
	{
		try
		{
			Field sessionField = HubController.class.getDeclaredField("session");
			sessionField.setAccessible(true);
			sessionField.set(this.controller, this.session);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exception)
		{
			exception.printStackTrace();
			throw exception;
		}
	}

}
