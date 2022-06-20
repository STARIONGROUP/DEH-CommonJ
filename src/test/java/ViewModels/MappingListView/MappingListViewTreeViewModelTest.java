/*
* MappingListViewTreeViewModelTest.java
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Enumerations.MappingDirection;
import Services.MappingEngineService.TestRules.Sphere;
import ViewModels.MappingListView.Rows.MappingRootRowViewModel;
import ViewModels.MappingListView.Rows.MappingRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.ElementDefinition;

class MappingListViewTreeViewModelTest
{
	MappingListViewTreeViewModel<Sphere> viewModel;
	ArrayList<Triple<? extends Sphere, MappingDirection, ? extends Thing>> mappedElements;

	@BeforeEach
	void setUp()
	{
		this.mappedElements = new ArrayList<>();
		Sphere sphere = new Sphere();
		ElementDefinition definition = new ElementDefinition();
		this.mappedElements.add(new ImmutableTriple<>(sphere,
				MappingDirection.FromDstToHub, definition));
		this.mappedElements.add(new ImmutableTriple<>(sphere,
				MappingDirection.FromDstToHub, definition));
		this.viewModel = new MappingListViewTreeViewModel<>(this.mappedElements);
	}

	@SuppressWarnings("unchecked")
	@Test
	void VerifyContainedRow()
	{
		MappingRootRowViewModel<Sphere> root = (MappingRootRowViewModel<Sphere>) this.viewModel.getRoot();
		assertEquals(2, root.GetContainedRows().size());

		for (int index = 0; index < root.GetContainedRows().size(); index++)
		{
			MappingRowViewModel<Sphere, ? extends DefinedThing> containedRow = root.GetContainedRows().get(index);
			assertNotNull(containedRow.GetDstElement());
			assertEquals(ElementDefinition.class, containedRow.GetHubElement().getClass());
			assertEquals(index == 0 ? MappingDirection.FromDstToHub : MappingDirection.FromDstToHub,
					containedRow.GetMappingDirection());
		}

		assertDoesNotThrow(() -> root.GetContainedRows().add(new MappingRowViewModel<>(
				new ElementDefinition(), new Sphere(), MappingDirection.FromHubToDst)));
		assertEquals(3, root.GetContainedRows().size());
	}
}
