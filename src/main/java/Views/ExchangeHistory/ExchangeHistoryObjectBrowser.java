/*
 * ExchangeHistoryObjectBrowser.java
 *
 * Copyright (c) 2020-2024 Starion Group S.A.
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
package Views.ExchangeHistory;

import java.awt.Color;

import ViewModels.ExchangeHistory.ExchangeHistoryRenderDataProvider;
import ViewModels.ExchangeHistory.Interfaces.IExchangeHistoryDialogViewModel;
import ViewModels.Interfaces.IContextMenuViewModel;
import Views.ObjectBrowser.ObjectBrowser;
import Views.ObjectBrowser.ObjectBrowserBase;

/**
 * The {@linkplain ExchangeHistoryObjectBrowser} is the {@linkplain ObjectBrowser} for the {@linkplain ExchangeHistoryDialog}
 */
@SuppressWarnings("serial")
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class ExchangeHistoryObjectBrowser extends ObjectBrowserBase<IExchangeHistoryDialogViewModel, IContextMenuViewModel>
{
    /**
     * Initializes a new {@linkplain MagicDrawObjectBrowser}
     */
    public ExchangeHistoryObjectBrowser()
    {
        super();
        this.objectBrowserTree.setRenderDataProvider(new ExchangeHistoryRenderDataProvider());
        this.objectBrowserTree.setRootVisible(false);
        this.objectBrowserTree.setSelectionBackground(new Color(104, 143, 184));
    }
    
    /**
     * Handles the selection when the user changes it, intended to be virtual
     */
    @Override
    protected void OnSelectionChanged() 
    {
    	// Added comment to satisfy the code smell raised by the rule 1186.
    	// This method is empty because nothing has to be done there.
    }
}
