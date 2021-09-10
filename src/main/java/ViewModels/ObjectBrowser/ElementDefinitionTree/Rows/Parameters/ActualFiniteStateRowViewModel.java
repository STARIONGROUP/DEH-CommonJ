/*
 * ValueSetActualFiniteStateRowViewModel.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Geren�, Alex Vorobiev, Nathanael Smiechowski 
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
package ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters;

import java.util.ArrayList;

import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Interfaces.IValueSetRowViewModel;
import ViewModels.ObjectBrowser.Rows.OwnedDefinedThingRowViewModel;
import cdp4common.engineeringmodeldata.ActualFiniteState;
import cdp4common.engineeringmodeldata.ParameterBase;

/**
 * The {@linkplain ActualFiniteStateRowViewModel} is the row view model to be contained by a {@linkplain ParameterOrOverrideBaseRowViewModel} 
 * where the represented {@linkplain Thing} is State dependent
 */
public class ActualFiniteStateRowViewModel<TParameter extends ParameterBase> extends ParameterBaseRowViewModel<TParameter> implements IValueSetRowViewModel, IHaveContainedRows<IValueSetRowViewModel>
{
    /**
     * The {@linkplain ActualFiniteState} this view model represents
     */
    private ActualFiniteState state;
    
    /**
     * Gets the {@linkplain ActualFiniteState} this view model represents
     * 
     * @return the {@linkplain ActualFiniteState}
     */
    public ActualFiniteState GetActualFiniteState()
    {
        return this.state;
    }
        
    /**
     * Initializes a new {@linkplain ActualFiniteStateRowViewModel}
     * 
     * @param actualFiniteState the {@linkplain ActualFiniteState} represented by this view model
     */
    public ActualFiniteStateRowViewModel(TParameter parameter, ActualFiniteState actualFiniteState)
    {
        super(parameter);
        this.state = actualFiniteState;
        this.SetName(actualFiniteState.getName());
    }
}
