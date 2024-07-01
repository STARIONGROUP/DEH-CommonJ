/*
 * ExchangeHistoryRootRowViewModel.java
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
package ViewModels.ExchangeHistory.Rows;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Reactive.ObservableCollection;
import Services.LocalExchangeHistory.ExchangeHistoryEntryCollection;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Rows.RowViewModel;

/**
 * The {@linkplain ExchangeHistoryRootRowViewModel} is the main container for {@linkplain ExchangeHistoryTimeStampRowViewModel}
 */
public class ExchangeHistoryRootRowViewModel extends RowViewModel implements IHaveContainedRows<ExchangeHistoryTimeStampRowViewModel>
{
    /**
     * The orginal {@linkplain Collection} of {@linkplain ExchangeHistoryEntryRowViewModel}
     */
    private List<ExchangeHistoryEntryRowViewModel> allEntries;
    
    /**
     * Backing field for {@linkplain #GetContainedRows()}
     */
    private ObservableCollection<ExchangeHistoryTimeStampRowViewModel> containedRows = new ObservableCollection<>();

    /**
     * Gets the contained row the implementing view model has
     * 
     * @return An {@linkplain ObservableCollection} of {@linkplain ExchangeHistoryTimeStampRowViewModel}
     */
    @Override
    public ObservableCollection<ExchangeHistoryTimeStampRowViewModel> GetContainedRows()
    {
        return this.containedRows;
    }
    
    /**
     * Initializes a new {@linkplain ExchangeHistoryRootRowViewModel}
     * 
     * @param entries the {@linkplain ExchangeHistoryEntryCollection}
     */
    public ExchangeHistoryRootRowViewModel(ExchangeHistoryEntryCollection entries)
    {
        this.allEntries = entries != null ? entries : new ObservableCollection<>();
        this.ComputeContainedRows();
    }

    /**
     * Computes this row view model contained rows, unused
     */
    @Override
    public void ComputeContainedRows() 
    {
        Map<Date, List<ExchangeHistoryEntryRowViewModel>> groupedEntries = this.allEntries.stream()
                .collect(Collectors.groupingBy(ExchangeHistoryEntryRowViewModel::GetTimestamp));
        
        List<Date> orderedTimeStamp = new ArrayList<>(groupedEntries.keySet());
        Collections.sort(orderedTimeStamp);
        
        for (Date date : orderedTimeStamp)
        {
            this.GetContainedRows().add(new ExchangeHistoryTimeStampRowViewModel(date, groupedEntries.get(date)));
        }
    }
}
