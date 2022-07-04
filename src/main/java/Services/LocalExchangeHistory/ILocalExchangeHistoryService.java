/*
 * ILocalExchangeHistoryService.java
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
package Services.LocalExchangeHistory;

import cdp4common.ChangeKind;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.ParameterOrOverrideBase;
import cdp4common.engineeringmodeldata.ParameterValueSetBase;
import cdp4common.engineeringmodeldata.ValueSet;

/**
 * The {@linkplain ILocalExchangeHistoryService} is the interface definition for {@linkplain LocalExchangeHistoryService}
 */
public interface ILocalExchangeHistoryService
{
    /**
     * Gets the {@linkplain ExchangeHistoryEntryCollection} by reading the json {@linkplain #localExchangeHistoryServiceFile}
     * 
     * @return the {@linkplain ExchangeHistoryEntryCollection}
     */
    ExchangeHistoryEntryCollection Read();

    /**
     * Clear the {@linkplain #pendingEntries} collection
     */
    void ClearPending();

    /**
     * Writes asynchronously the {@linkplain #pendingEntries} to the json file
     */
    void Write();

    /**
     * Append to the history an entry that relates of a {@linkplain ChangeKind} on the provided {@linkplain ParameterOrOverrideBase}
     * 
     * @param parameter the changed {@linkplain ParameterOrOverrideBase}
     * @param changeKind the type of change applied to provided {@linkplain Thing}
     */
    void Append(ParameterOrOverrideBase parameter, ChangeKind changeKind);

    /**
     * Append to the history an entry that relates of a {@linkplain ChangeKind} on the provided {@linkplain thing}
     * 
     * @param thing the changed {@linkplain Thing}
     * @param changeKind the type of change applied to provided {@linkplain Thing}
     */
    void Append(Thing thing, ChangeKind changeKind);

    /**
     * Appends to the history a entry that concerns a difference between two {@linkplain ValueSet}
     * 
     * @param valueToUpdate the {@linkplain ParameterValueSetBase} that holds the old value
     * @param newValue the {@linkplain ValueSet} that contains the new value
     */
    void Append(ParameterValueSetBase valueToUpdate, ValueSet newValue);

    /**
     * Append to the history
     * 
     * @param message the {@linkplain String} message
     * @param nodeName the {@linkplain String} node name to display for the node column
     */
    void Append(String nodeName, String message);
}
