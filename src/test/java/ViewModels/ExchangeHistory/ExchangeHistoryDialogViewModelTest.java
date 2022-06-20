/*
* ExchangeHistoryDialogViewModelTest.java
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
package ViewModels.ExchangeHistory;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Services.LocalExchangeHistory.ExchangeHistoryEntryCollection;
import Services.LocalExchangeHistory.ILocalExchangeHistoryService;
import ViewModels.ExchangeHistory.Rows.ExchangeHistoryEntryRowViewModel;

class ExchangeHistoryDialogViewModelTest
{
	ExchangeHistoryDialogViewModel viewModel;
	ILocalExchangeHistoryService service;
	ExchangeHistoryEntryCollection entries;
	
	@BeforeEach
	void setUp()
	{
		this.service = mock(ILocalExchangeHistoryService.class);
		this.entries = new ExchangeHistoryEntryCollection();
		when(this.service.Read()).thenReturn(this.entries);
		this.viewModel = new ExchangeHistoryDialogViewModel(this.service);
	}

	@Test
	void VerifyProperties()
	{
		assertNotNull(this.viewModel.GetSelectedElement());
		assertDoesNotThrow(() -> this.viewModel.OnSelectionChanged(null));
		assertDoesNotThrow(() -> this.viewModel.BuildTree());
		ExchangeHistoryEntryRowViewModel newEntry = new ExchangeHistoryEntryRowViewModel();
		newEntry.SetTimeStamp(new Date());
		ExchangeHistoryEntryRowViewModel newEntry2 = new ExchangeHistoryEntryRowViewModel();
		newEntry2.SetTimeStamp(new Timestamp(1000));
		this.entries.add(newEntry);
		this.entries.add(newEntry2);
		assertDoesNotThrow(() -> this.viewModel.UpdateBrowserTrees(true));
	}
}
