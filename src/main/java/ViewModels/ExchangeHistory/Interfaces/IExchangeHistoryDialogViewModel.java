/*
 * IExchangeHistoryDialogViewModel.java
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
package ViewModels.ExchangeHistory.Interfaces;

import ViewModels.ExchangeHistory.Rows.ExchangeHistoryEntryRowViewModel;
import ViewModels.Interfaces.IObjectBrowserBaseViewModel;

/**
 * The {@linkplain IExchangeHistoryDialogViewModel} is the interface definition for {@linkplain ExchangeHistoryDialogViewModel}
 */
public interface IExchangeHistoryDialogViewModel extends IObjectBrowserBaseViewModel<ExchangeHistoryEntryRowViewModel>
{
    /**
     * Creates the {@linkplain OutlineModel} tree to display the exchange history
     */
    void BuildTree();
}
