/*
* MappingListViewViewModelTest.java
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
package ViewModels.MappingListView;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DstController.IDstControllerBase;
import Enumerations.MappingDirection;
import HubController.IHubController;
import Reactive.ObservableCollection;
import Services.MappingEngineService.TestRules.Sphere;
import ViewModels.Dialogs.Rows.MappedElementDefinitionSphereRowViewModel;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.engineeringmodeldata.ElementDefinition;

class MappingListViewViewModelTest
{
	MappingListViewViewModel<IDstControllerBase<Sphere>> viewModel;
	IDstControllerBase<Sphere> dstController;
	IHubController hubController;
	ObservableCollection<MappedElementRowViewModel<DefinedThing, Sphere>> dstMapResult;
	ObservableCollection<MappedElementRowViewModel<DefinedThing, Sphere>> hubMapResult;

	@SuppressWarnings("unchecked")
	@BeforeEach
	void setUp()
	{
		this.dstMapResult = new ObservableCollection<>();
		this.hubMapResult = new ObservableCollection<>();
		this.hubController = mock(IHubController.class);
		this.dstController = mock(IDstControllerBase.class);
		when(this.dstController.GetDstMapResult()).thenReturn(this.dstMapResult);
		when(this.dstController.GetHubMapResult()).thenReturn(this.hubMapResult);
		when(this.hubController.GetIsSessionOpen()).thenReturn(false, true, false, true);

		this.viewModel = new MappingListViewViewModel<>(this.dstController, this.hubController)
		{
			@Override
			protected void UpdateBrowserTrees(Boolean isConnected)
			{
			}
		};
	}

	@Test
	void VerifyProperties()
	{
		assertNull(this.viewModel.GetSelectedElement());
		assertDoesNotThrow(() -> this.viewModel.OnSelectionChanged(null));
	}

	@SuppressWarnings("unchecked")
	@Test
	void VerifyObservables()
	{
		MappedElementRowViewModel<? extends DefinedThing, Sphere> mappedElement = new MappedElementDefinitionSphereRowViewModel(
				ElementDefinition.class, new Sphere(), MappingDirection.FromDstToHub);

		ArrayList<MappedElementRowViewModel<DefinedThing, Sphere>> mappedElements = new ArrayList<>();
		mappedElements.add((MappedElementRowViewModel<DefinedThing, Sphere>) mappedElement);

		this.hubMapResult.addAll(mappedElements);
		this.dstMapResult.addAll(mappedElements);
		this.hubMapResult.clear();
		this.dstMapResult.addAll(mappedElements);
		this.hubMapResult.addAll(mappedElements);
		this.dstMapResult.clear();
		this.hubMapResult.clear();

		verify(this.hubController, times(8)).GetIsSessionOpen();
	}
}
