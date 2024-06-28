/*
* ObservableValueTest.java
*
* Copyright (c) 2020-2024 Starion Group S.A.
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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObservableValueTest
{
	ObservableValue<String> observable;

	@BeforeEach
	void setUp() throws Exception
	{
		this.observable = new ObservableValue<>();
	}

	@Test
	void VerifySetValue()
	{
		assertDoesNotThrow(() -> this.observable.Value(null));
		assertDoesNotThrow(() -> this.observable.Value("A string value"));
		assertNotNull(this.observable.Value());
		assertDoesNotThrow(() -> this.observable.Value(null));
		assertNull(this.observable.Value());
	}
}
