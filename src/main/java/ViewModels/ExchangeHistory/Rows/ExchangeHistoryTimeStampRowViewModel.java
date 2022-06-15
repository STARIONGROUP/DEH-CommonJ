/*
 * ExchangeHistoryTimeStampRowViewModel.java
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
package ViewModels.ExchangeHistory.Rows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import Reactive.ObservableCollection;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Rows.RowViewModel;

/**
 * The {@linkplain ExchangeHistoryTimeStampRowViewModel} is the row view model that represent a group of time tagged {@linkplain ExchangeHistoryEntryRowViewModel}
 */
public class ExchangeHistoryTimeStampRowViewModel extends RowViewModel implements IHaveContainedRows<ExchangeHistoryEntryRowViewModel>
{
    /**
     * Backing field for {@linkplain #GetName()}
     */
    private String name;
    
    /**
     * Gets the name of this row view model
     * 
     * @return a {@linkplain String}
     */
    public String GetName()
    {
        return this.name;
    }
    
    /**
     * Backing field for {@linkplain #GetContainedRows()}
     */
    private ObservableCollection<ExchangeHistoryEntryRowViewModel> containedRows = new ObservableCollection<>();

    /**
     * Gets the contained row the implementing view model has
     * 
     * @return An {@linkplain ObservableCollection} of {@linkplain ExchangeHistoryEntryRowViewModel}
     */
    @Override
    public ObservableCollection<ExchangeHistoryEntryRowViewModel> GetContainedRows()
    {
        return this.containedRows;
    }
    
    /**
     * @param list
     */
    public ExchangeHistoryTimeStampRowViewModel(Date date, List<ExchangeHistoryEntryRowViewModel> entryRowViewModels)
    {
        this.name = String.format("Time of Transfer: %s", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        this.GetContainedRows().addAll(entryRowViewModels);
    }

    /**
     * Computes this row view model contained rows, unused
     */
    @Override
    public void ComputeContainedRows() 
    {
    	// Added comment to satisfy the code smell raised by the rule 1186.
    	// This method is empty because nothing has to be done there.
    }
}
