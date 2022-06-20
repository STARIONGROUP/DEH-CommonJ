/*
* MappedElementListViewViewModelTest.java
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
package ViewModels.MappedElementListView;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import javax.swing.ListSelectionModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Enumerations.MappingDirection;
import Services.MappingEngineService.TestRules.Sphere;
import ViewModels.Dialogs.Rows.MappedElementDefinitionSphereRowViewModel;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.engineeringmodeldata.ElementDefinition;

class MappedElementListViewViewModelTest
{
	MappedElementListViewViewModel<Sphere> viewModel;
	ArrayList<MappedElementRowViewModel<DefinedThing, Sphere>> mappedElements;
	
	@BeforeEach
	void setUp()
	{
		this.mappedElements = new ArrayList<>();
		this.viewModel = new MappedElementListViewViewModel<>();
	}

	@Test
	void VerifyProperties()
	{
		assertNotNull(this.viewModel.GetSelectedElement());
		assertNotNull(this.viewModel.GetShouldRefreshTree());
		assertNotNull(this.viewModel.BrowserTreeModel());
		assertEquals(ListSelectionModel.SINGLE_SELECTION, this.viewModel.GetSelectionMode());
	}

	@SuppressWarnings("unchecked")
	@Test
	void VerifyBuildTree() 
	{
		assertDoesNotThrow(() -> this.viewModel.BuildTree(mappedElements));
		MappedElementRowViewModel<? extends DefinedThing, Sphere> mappedElement = new MappedElementDefinitionSphereRowViewModel(new ElementDefinition(), ElementDefinition.class, new Sphere(), MappingDirection.FromDstToHub);
		mappedElements.add((MappedElementRowViewModel<DefinedThing, Sphere>)mappedElement);
		assertDoesNotThrow(() -> this.viewModel.BuildTree(mappedElements));
		assertDoesNotThrow(() -> this.viewModel.OnSelectionChanged((MappedElementRowViewModel<DefinedThing, Sphere>)mappedElement));
	}
}
