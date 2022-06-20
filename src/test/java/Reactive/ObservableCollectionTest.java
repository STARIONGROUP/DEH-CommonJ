/*
* ObservableCollectionTest.java
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
package Reactive;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObservableCollectionTest
{
	ObservableCollection<String> observableCollection;

	@BeforeEach
	void setUp()
	{
		this.observableCollection = new ObservableCollection<>();
	}

	@Test
	void VerifyProperties()
	{
		assertNotNull(this.observableCollection.Changed());
		assertNotNull(this.observableCollection.ItemRemoved());
		assertNotNull(this.observableCollection.ItemAdded());
		assertNotNull(this.observableCollection.ItemsAdded());
		assertNotNull(this.observableCollection.IsEmptyObservable());
		assertNotNull(this.observableCollection.iterator());
		assertNotNull(this.observableCollection.listIterator());
		assertNotNull(this.observableCollection.listIterator(0));
		assertDoesNotThrow(() -> this.observableCollection = new ObservableCollection<>(String.class));
	}

	@Test
	void VerifyCollectionOperations()
	{
		assertTrue(this.observableCollection.isEmpty());
		this.observableCollection.add("15");
		assertFalse(this.observableCollection.isEmpty());
		assertDoesNotThrow(() -> this.observableCollection.remove("45"));
		assertFalse(this.observableCollection.isEmpty());
		assertDoesNotThrow(() -> this.observableCollection.remove("15"));
		assertTrue(this.observableCollection.isEmpty());

		ArrayList<String> otherCollection = new ArrayList<>();

		for (int index = 0; index < 10; index++)
		{
			otherCollection.add(String.valueOf(index));
		}

		this.observableCollection.addAll(otherCollection);
		assertEquals(otherCollection.size(), this.observableCollection.size());

		assertEquals("9", this.observableCollection.remove(9));
		assertEquals(otherCollection.size() - 1, this.observableCollection.size());

		assertThrows(IndexOutOfBoundsException.class, () -> this.observableCollection.remove(9));
		assertEquals(otherCollection.size() - 1, this.observableCollection.size());

		this.observableCollection.set(0, "45");
		assertEquals(0, this.observableCollection.indexOf("45"));
		assertEquals(0, this.observableCollection.lastIndexOf("45"));

		this.observableCollection.add(2, "3");
		assertEquals(2, this.observableCollection.indexOf("3"));

		this.observableCollection.addAll(0, otherCollection);
		assertEquals(10, this.observableCollection.indexOf("45"));

		assertTrue(this.observableCollection.removeAll(otherCollection));
		assertTrue(this.observableCollection.retainAll(otherCollection));

		this.observableCollection.clear();
		assertFalse(this.observableCollection.containsAll(otherCollection));
		assertFalse(this.observableCollection.containsAll(new ArrayList<>()));

		assertTrue(this.observableCollection.isEmpty());

		this.observableCollection = new ObservableCollection<>(otherCollection, String.class);
		assertEquals("0", this.observableCollection.get(0));
		assertEquals("9", this.observableCollection.get(9));

		assertTrue(this.observableCollection.containsAll(otherCollection));

		assertTrue(this.observableCollection.removeIf(x -> x.equals("4")));
		assertFalse(this.observableCollection.contains("4"));
	}

	@Test
	void VerifyToArrayConversion()
	{
		ArrayList<String> otherCollection = new ArrayList<>();

		for (int index = 0; index < 10; index++)
		{
			otherCollection.add(String.valueOf(index));
		}

		assertEquals(10, this.observableCollection.toArray(otherCollection.toArray()).length);
		assertEquals(0, this.observableCollection.toArray().length);
	}

	@Test
	void VerifyGetSublist()
	{
		for (int index = 0; index < 10; index++)
		{
			this.observableCollection.add(String.valueOf(index));
		}

		List<String> subList = this.observableCollection.subList(4, 6);
		assertEquals("4", subList.get(0));
		assertEquals(2, subList.size());
	}
}
