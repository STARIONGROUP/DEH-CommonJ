/*
* AdapterInfoServiceTest.java
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
package Services.AdapterInfo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cdp4common.Version;

class AdapterInfoServiceTest
{
	@Test
	void VerifyAdapterInfoProperty()
	{
		Version version = new Version(1, 5, 0);
		String adapterName = "TestAdapter";
		AdapterInfoService service = new AdapterInfoService(version, adapterName);
		assertEquals(1, service.GetVersion().getMajor());
		assertEquals(5, service.GetVersion().getMinor());
		assertEquals(adapterName, service.GetAdapterName());
	}
}
