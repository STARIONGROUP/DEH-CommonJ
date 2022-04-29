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
import Reactive.ObservableValue;
import Services.LocalExchangeHistory.ILocalExchangeHistoryService;
import ViewModels.ObjectBrowserBaseViewModel;
import ViewModels.ObjectBrowserViewModel;
import ViewModels.ExchangeHistory.Interfaces.IExchangeHistoryDialogViewModel;
import ViewModels.ExchangeHistory.Rows.ExchangeHistoryEntryRowViewModel;
import ViewModels.ObjectBrowser.Rows.ThingRowViewModel;
import Views.ExchangeHistory.ExchangeHistoryDialog;
import cdp4common.commondata.Thing;
import io.reactivex.Observable;

/**
 * The {@linkplain ExchangeHistoryDialogViewModel} is the dialog view model for the {@linkplain ExchangeHistoryDialog}
 */
public class ExchangeHistoryDialogViewModel extends ObjectBrowserBaseViewModel<ExchangeHistoryEntryRowViewModel> implements IExchangeHistoryDialogViewModel
{
    /**
     * The {@linkplain ILocalExchangeHistoryService}
     */
    private ILocalExchangeHistoryService localExchangeHistoryService;
    
    /**
     * Backing field for {@linkplain #GetSelectedElement()}
     */
    private ObservableValue<ExchangeHistoryEntryRowViewModel> selectedElement = new ObservableValue<>();
    
    /**
     * Initializes a new {@linkplain IExchangeHistoryDialogViewModel}
     * 
     * @param localExchangeHistoryService the {@linkplain ILocalExchangeHistoryService}
     */
    public ExchangeHistoryDialogViewModel(ILocalExchangeHistoryService localExchangeHistoryService)
    {
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
    
    /**
     * Gets the {@linkplain Observable} of {@linkplain #TRowViewModel} that yields the selected element
     * 
     * @return an {@linkplain Observable} of {@linkplain #TRowViewModel}
     */
    @Override
    public Observable<ExchangeHistoryEntryRowViewModel> GetSelectedElement()
    {
        return this.selectedElement.Observable();
    }
    
    /**
     * Called whenever the selection changes on the bound {@linkplain ObjectBrowser}
     * 
     * @param selectedRow the selected row view model {@linkplain TRowViewModel}
     */
    @Override
    public void OnSelectionChanged(ExchangeHistoryEntryRowViewModel selectedRow)
    {
        this.selectedElement.Value(selectedRow);;
    }
}
