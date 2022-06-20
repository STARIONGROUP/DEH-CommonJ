/*
* MappingConfigurationDialogViewModelTest.java
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
package ViewModels.Dialogs;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DstController.IDstControllerBase;
import Enumerations.MappedElementRowStatus;
import Enumerations.MappingDirection;
import HubController.IHubController;
import Reactive.ObservableCollection;
import Reactive.ObservableValue;
import Services.MappingEngineService.TestRules.Sphere;
import Utils.Ref;
import ViewModels.Dialogs.Rows.MappedElementDefinitionSphereRowViewModel;
import ViewModels.Dialogs.Rows.MappedRequirementSphereRowViewModel;
import ViewModels.Interfaces.IElementDefinitionBrowserViewModel;
import ViewModels.Interfaces.IObjectBrowserBaseViewModel;
import ViewModels.Interfaces.IRequirementBrowserViewModel;
import ViewModels.MappedElementListView.Interfaces.IMappedElementListViewViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.ElementDefinitionRowViewModel;
import ViewModels.ObjectBrowser.RequirementTree.Rows.RequirementRowViewModel;
import ViewModels.ObjectBrowser.Rows.ThingRowViewModel;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.engineeringmodeldata.Requirement;
import cdp4common.engineeringmodeldata.RequirementsSpecification;
import cdp4common.sitedirectorydata.DomainOfExpertise;

class MappingConfigurationDialogViewModelTest
{
	private MappingConfigurationDialogViewModel<Thing, Sphere, ElementDefinitionRowViewModel> viewModel;
	private IDstControllerBase<Sphere> dstController;
	private IHubController hubController;
	private IElementDefinitionBrowserViewModel elementDefinitionBrowser;
	private IRequirementBrowserViewModel requirementBrowser;
	private IMappedElementListViewViewModel<Sphere> mappedElementListView;
	private Iteration iteration;
	private DomainOfExpertise domain;
	private ElementDefinition elementDefinition;
	private Requirement requirement;
	private RequirementsSpecification requirementsSpecification;
	private ObservableValue<ThingRowViewModel<Thing>> elementDefinitionObservable;
	private ObservableValue<ThingRowViewModel<Thing>> requirementObservable;
	private ObservableValue<MappedElementRowViewModel<DefinedThing, Sphere>> mappedValueObservable;
	private ObservableCollection<MappedElementRowViewModel<DefinedThing, Sphere>> dstMapResult;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp()
	{
		this.dstController = mock(IDstControllerBase.class);
		this.dstMapResult = new ObservableCollection<>();
		when(this.dstController.GetDstMapResult()).thenReturn(this.dstMapResult);

		this.iteration = new Iteration();
		this.domain = new DomainOfExpertise();
		this.domain.setName("THERMAL");
		this.domain.setShortName("THE");

		this.hubController = mock(IHubController.class);
		ObservableValue<Boolean> isSessionOpen = new ObservableValue<>(true, Boolean.class);
		when(this.hubController.GetIsSessionOpenObservable()).thenReturn(isSessionOpen.Observable());
		when(this.hubController.GetOpenIteration()).thenReturn(this.iteration);
		when(this.hubController.GetSessionEventObservable()).thenReturn(io.reactivex.Observable.fromArray(true));

		this.elementDefinition = new ElementDefinition();
		this.elementDefinition.setIid(UUID.randomUUID());
		this.elementDefinition.setName("Battery");
		this.elementDefinition.setShortName("battery");
		this.elementDefinition.setOwner(this.domain);

		this.requirement = new Requirement();
		this.requirement.setName("Mass limit");
		this.requirement.setIid(UUID.randomUUID());
		this.requirement.setOwner(this.domain);

		this.requirementsSpecification = new RequirementsSpecification();
		this.requirementsSpecification.setIid(UUID.randomUUID());
		this.requirementsSpecification.setName("Mission Requirements");
		this.requirementsSpecification.setOwner(this.domain);

		this.requirementsSpecification.getRequirement().add(this.requirement);

		this.iteration.getRequirementsSpecification().add(this.requirementsSpecification);
		this.iteration.getElement().add(this.elementDefinition);

		this.elementDefinitionBrowser = mock(IElementDefinitionBrowserViewModel.class);
		this.requirementBrowser = mock(IRequirementBrowserViewModel.class);
		this.mappedElementListView = mock(IMappedElementListViewViewModel.class);

		this.elementDefinitionObservable = new ObservableValue<>();
		this.requirementObservable = new ObservableValue<>();
		this.mappedValueObservable = new ObservableValue<>();

		when(this.elementDefinitionBrowser.GetSelectedElement())
				.thenReturn(this.elementDefinitionObservable.Observable());
		when(this.requirementBrowser.GetSelectedElement()).thenReturn(this.requirementObservable.Observable());
		when(this.mappedElementListView.GetSelectedElement()).thenReturn(this.mappedValueObservable.Observable());

		this.viewModel = new MappingConfigurationDialogViewModel<Thing, Sphere, ElementDefinitionRowViewModel>(
				this.dstController, this.hubController, this.elementDefinitionBrowser, this.requirementBrowser,
				this.mappedElementListView)
		{

			@Override
			public IObjectBrowserBaseViewModel<ElementDefinitionRowViewModel> GetDstObjectBrowserViewModel()
			{
				return null;
			}

			@Override
			protected void UpdateProperties()
			{

			}

			@Override
			protected void PreMap(Collection<Thing> selectedElements)
			{

			}
		};
	}

	@Test
	void VerifyProperties()
	{
		assertDoesNotThrow(() -> this.viewModel.InitializeObservables());
		assertNull(this.viewModel.GetDstObjectBrowserViewModel());
		assertEquals(this.elementDefinitionBrowser, this.viewModel.GetElementDefinitionBrowserViewModel());
		assertEquals(this.requirementBrowser, this.viewModel.GetRequirementBrowserViewModel());
		assertEquals(this.mappedElementListView, this.viewModel.GetMappedElementListViewViewModel());
		assertEquals(0, this.viewModel.GetMappedElementCollection().size());
		assertNotNull(this.viewModel.GetSelectedMappedElement());
		assertNotNull(this.viewModel.GetShouldMapToNewElementCheckBoxBeEnabled());
	}

	@SuppressWarnings("unchecked")
	@Test
	void VerifyUpdateProperties()
	{
		assertDoesNotThrow(() -> this.viewModel.ResetPreMappedThings());
		ObservableCollection<MappedElementRowViewModel<DefinedThing, Sphere>> mappedElementCollection = new ObservableCollection<>();

		ElementDefinition elementDefinition = new ElementDefinition();
		elementDefinition.setName("Element Definition");

		Sphere sphere = new Sphere();
		sphere.SetId(UUID.randomUUID());
		sphere.SetName("Sphere");

		MappedElementRowViewModel<? extends DefinedThing, Sphere> mappedElement = new MappedElementDefinitionSphereRowViewModel(
				elementDefinition, ElementDefinition.class, sphere, MappingDirection.FromDstToHub);
		mappedElementCollection.add((MappedElementRowViewModel<DefinedThing, Sphere>) mappedElement);

		assertDoesNotThrow(() -> this.viewModel.UpdateProperties(mappedElementCollection));
		assertEquals(1, this.viewModel.GetMappedElementCollection().size());
		ArrayList<Thing> selectedElements = new ArrayList<>();
		selectedElements.addAll(this.iteration.getElement());
		assertDoesNotThrow(() -> this.viewModel.SetMappedElement(selectedElements));
	}

	@SuppressWarnings("unchecked")
	@Test
	void VerifyWhenMapToNewElementCheckBox()
	{
		ObservableCollection<MappedElementRowViewModel<DefinedThing, Sphere>> mappedElementCollection = new ObservableCollection<>();

		Sphere sphere = new Sphere();
		sphere.SetId(UUID.randomUUID());
		sphere.SetName("Sphere");

		MappedElementRowViewModel<? extends DefinedThing, Sphere> mappedElement = new MappedElementDefinitionSphereRowViewModel(
				this.elementDefinition, ElementDefinition.class, sphere, MappingDirection.FromDstToHub);
		mappedElementCollection.add((MappedElementRowViewModel<DefinedThing, Sphere>) mappedElement);

		this.viewModel.UpdateProperties(mappedElementCollection);

		assertFalse(mappedElement.GetShouldCreateNewTargetElementValue());
		this.viewModel.WhenMapToNewElementCheckBoxChanged(true);

		assertFalse(mappedElement.GetShouldCreateNewTargetElementValue());
		this.viewModel.SetSelectedMappedElement((MappedElementRowViewModel<DefinedThing, Sphere>) mappedElement);

		this.viewModel.WhenMapToNewElementCheckBoxChanged(true);
		assertTrue(mappedElement.GetShouldCreateNewTargetElementValue());

		this.viewModel.WhenMapToNewElementCheckBoxChanged(true);
		assertTrue(mappedElement.GetShouldCreateNewTargetElementValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	void VerifyObservables()
	{
		this.viewModel.InitializeObservables();
		ThingRowViewModel<? extends Thing> elementDefinitionRow = new ElementDefinitionRowViewModel(
				this.elementDefinition, null);
		this.elementDefinitionObservable.Value((ThingRowViewModel<Thing>) elementDefinitionRow);
		assertNull(this.viewModel.selectedMappedElement.Value());

		ObservableCollection<MappedElementRowViewModel<DefinedThing, Sphere>> mappedElementCollection = new ObservableCollection<>();

		ElementDefinition newElementDefinition = new ElementDefinition();
		newElementDefinition.setName("Element Definition");

		Requirement newRequirement = new Requirement();
		newRequirement.setName("A new requirement");

		Sphere sphere = new Sphere();
		sphere.SetId(UUID.randomUUID());
		sphere.SetName("Sphere");

		MappedElementRowViewModel<? extends DefinedThing, Sphere> mappedElement = new MappedElementDefinitionSphereRowViewModel(
				newElementDefinition, ElementDefinition.class, sphere, MappingDirection.FromDstToHub);

		MappedElementRowViewModel<? extends DefinedThing, Sphere> mappedRequirement = new MappedRequirementSphereRowViewModel(
				newRequirement, Requirement.class, sphere, MappingDirection.FromDstToHub);

		mappedElementCollection.add((MappedElementRowViewModel<DefinedThing, Sphere>) mappedElement);
		mappedElementCollection.add((MappedElementRowViewModel<DefinedThing, Sphere>) mappedRequirement);

		this.viewModel.UpdateProperties(mappedElementCollection);
		mappedElement.SetRowStatus(MappedElementRowStatus.NewElement);
		mappedRequirement.SetRowStatus(MappedElementRowStatus.NewElement);
		assertFalse(mappedElement.GetIsSelected());

		this.mappedValueObservable.Value((MappedElementRowViewModel<DefinedThing, Sphere>) mappedElement);
		assertTrue(mappedElement.GetIsSelected());

		mappedElement.SetRowStatus(MappedElementRowStatus.ExistingMapping);
		this.mappedValueObservable.Value((MappedElementRowViewModel<DefinedThing, Sphere>) mappedElement);

		this.elementDefinitionObservable.Value((ThingRowViewModel<Thing>) elementDefinitionRow);

		assertEquals(newElementDefinition, mappedElement.GetHubElement());

		mappedElement.SetRowStatus(MappedElementRowStatus.NewElement);
		mappedElement.SetShouldCreateNewTargetElement(true);

		when(this.hubController.TryGetThingById(eq(this.elementDefinition.getIid()), any(Ref.class))).thenReturn(false);
		this.elementDefinitionObservable.Value((ThingRowViewModel<Thing>) elementDefinitionRow);
		assertEquals(this.elementDefinition.getIid(), mappedElement.GetHubElement().getIid());

		when(this.hubController.TryGetThingById(eq(this.elementDefinition.getIid()), any(Ref.class))).thenReturn(true);
		this.elementDefinitionObservable.Value((ThingRowViewModel<Thing>) elementDefinitionRow);
		assertEquals(this.elementDefinition.getIid(), mappedElement.GetHubElement().getIid());

		this.dstMapResult.add((MappedElementRowViewModel<DefinedThing, Sphere>) mappedElement);

		this.elementDefinitionObservable.Value((ThingRowViewModel<Thing>) elementDefinitionRow);
		assertEquals(this.elementDefinition.getIid(), mappedElement.GetHubElement().getIid());

		mappedElement.SetRowStatus(MappedElementRowStatus.NewElement);

		ThingRowViewModel<? extends Thing> requirementRow = new RequirementRowViewModel(this.requirement, null);
		this.requirementObservable.Value((ThingRowViewModel<Thing>) requirementRow);

		this.mappedValueObservable.Value((MappedElementRowViewModel<DefinedThing, Sphere>) mappedRequirement);
		this.elementDefinitionObservable.Value((ThingRowViewModel<Thing>) elementDefinitionRow);
		this.requirementObservable.Value((ThingRowViewModel<Thing>) requirementRow);
		assertEquals(this.requirement.getIid(), mappedRequirement.GetHubElement().getIid());
	}

	@Test
	void VerifyMappedRowProperties()
	{
		Sphere sphere = new Sphere();
		sphere.SetId(UUID.randomUUID());
		sphere.SetName("Sphere");

		MappedElementRowViewModel<? extends DefinedThing, Sphere> mappedElement = new MappedElementDefinitionSphereRowViewModel(
				this.elementDefinition, ElementDefinition.class, sphere, MappingDirection.FromDstToHub);

		MappedElementRowViewModel<? extends DefinedThing, Sphere> mappedRequirement = new MappedRequirementSphereRowViewModel(
				this.requirement, Requirement.class, sphere, MappingDirection.FromHubToDst);
		
		assertTrue(mappedElement.GetIsValid());
		assertTrue(mappedRequirement.GetIsValid());
		assertNotNull(mappedRequirement.GetIsSelectedObservable());
		assertNotNull(mappedRequirement.GetShouldCreateNewTargetElement());
		assertEquals(0, mappedElement.GetRelationships().size());
		assertEquals(MappingDirection.FromDstToHub, mappedElement.GetMappingDirection());
		assertNotNull(mappedElement.GetDstElementRepresentation());
		assertNotNull(mappedRequirement.GetDstElementRepresentation());
		assertNotNull(mappedElement.GetHubElementRepresentation());
		assertNotNull(mappedRequirement.GetHubElementRepresentation());
		
		assertNotNull(mappedRequirement.GetHubElementRepresentation(Requirement.class));
		mappedRequirement.SetDstElement(null);
		assertFalse(mappedRequirement.GetIsValid());
		
		mappedRequirement.SetShouldCreateNewTargetElement(true);
		assertTrue(mappedRequirement.GetIsValid());

		mappedRequirement.SetDstElement(sphere);
		mappedRequirement.SetHubElement(null);
		
		assertNotNull(mappedRequirement.GetHubElementRepresentation(Requirement.class));
		assertFalse(mappedRequirement.GetIsValid());
		
		assertNotNull(mappedElement.GetHubElementRepresentation(ElementDefinition.class));
		mappedElement.SetDstElement(null);
		assertFalse(mappedElement.GetIsValid());
		
		mappedElement.SetDstElement(sphere);
		mappedElement.SetHubElement(null);
		assertFalse(mappedElement.GetIsValid());
		
		assertNotNull(mappedElement.GetHubElementRepresentation(ElementDefinition.class));
		
		mappedElement.SetShouldCreateNewTargetElement(true);
		assertTrue(mappedElement.GetIsValid());
		
		MappedElementDefinitionSphereRowViewModel newRow = new MappedElementDefinitionSphereRowViewModel(ElementDefinition.class, sphere, MappingDirection.FromDstToHub);
		assertNull(newRow.GetHubElement());
	}
}
