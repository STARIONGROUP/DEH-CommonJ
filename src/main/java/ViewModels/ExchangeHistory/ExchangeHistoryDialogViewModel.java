/*
 * ExchangeHistoryDialogViewModel.java
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

import javax.swing.tree.TreeModel;

import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.OutlineModel;

import HubController.IHubController;
import Services.LocalExchangeHistory.ILocalExchangeHistoryService;
import ViewModels.ObjectBrowserViewModel;
import ViewModels.ExchangeHistory.Interfaces.IExchangeHistoryDialogViewModel;

/**
 * The {@linkplain ExchangeHistoryDialogViewModel} is the dialog view model for the {@linkplain ExchangeHistoryDialog}
 */
public class ExchangeHistoryDialogViewModel extends ObjectBrowserViewModel implements IExchangeHistoryDialogViewModel
{
    /**
     * The {@linkplain ILocalExchangeHistoryService}
     */
    private ILocalExchangeHistoryService localExchangeHistoryService;

    /**
     * Initializes a new {@linkplain IExchangeHistoryDialogViewModel}
     * 
     * @param localExchangeHistoryService the {@linkplain ILocalExchangeHistoryService}
     */
    public ExchangeHistoryDialogViewModel(IHubController hubController, ILocalExchangeHistoryService localExchangeHistoryService)
    {
        super(hubController);
        this.localExchangeHistoryService = localExchangeHistoryService;
        this.BuildTree();
    }

    /**
     * Creates the {@linkplain OutlineModel} tree to display the exchange history
     */
    @Override
    public void BuildTree()
    {
        this.browserTreeModel.Value(DefaultOutlineModel.createOutlineModel(
                new LocalExchangeHistoryTreeViewModel(this.localExchangeHistoryService.Read()),
                new LocalExchangeHistoryTreeRowViewModel(), true));
                
        this.isTheTreeVisible.Value(true);
    }

    /**
     * Updates this view model {@linkplain TreeModel}
     * 
     * @param isConnected a value indicating whether the session is open
     */
    protected void UpdateBrowserTrees(Boolean isConnected) { }
}
