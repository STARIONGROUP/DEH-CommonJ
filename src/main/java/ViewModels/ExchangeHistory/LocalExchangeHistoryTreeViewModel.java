/*
 * LocalExchangeHistoryTreeViewModel.java
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
package ViewModels.ExchangeHistory;

import Services.LocalExchangeHistory.ExchangeHistoryEntryCollection;
import ViewModels.ExchangeHistory.Rows.ExchangeHistoryRootRowViewModel;
import ViewModels.ObjectBrowser.BrowserTreeBaseViewModel;

/**
 * The {@linkplain LocalExchangeHistoryTreeViewModel} is the {@linkplain BrowserTreeBaseViewModel} for the {@linkplain ExchangeHistoryDialog}
 */
public class LocalExchangeHistoryTreeViewModel extends BrowserTreeBaseViewModel
{
    /**
     * The root element of the tree
     */
    private Object root;

    /**
     * Gets the root element of the tree
     * 
     * @return an {@linkplain Object}
     */
    @Override
    public Object getRoot()
    {
        return this.root;
    }
    
    /**
     * Initializes a new {@linkplain LocalExchangeHistoryTreeViewModel}
     * 
     * @param mappedElements the {@linkplain RootRowViewModel}
     */
    public LocalExchangeHistoryTreeViewModel(ExchangeHistoryEntryCollection entries)
    {
        this.root = new ExchangeHistoryRootRowViewModel(entries);
    }
}
