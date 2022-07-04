/*
 * ParameterOrOverrideRowViewModel.java
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
package ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters;

import java.util.Collection;
import java.util.Collections;

import Reactive.ObservableCollection;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.ObjectBrowser.Interfaces.IValueSetRowViewModel;
import cdp4common.engineeringmodeldata.ActualFiniteState;
import cdp4common.engineeringmodeldata.ActualFiniteStateList;
import cdp4common.engineeringmodeldata.Option;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.ParameterBase;
import cdp4common.engineeringmodeldata.ParameterOrOverrideBase;

/**
 * The {@linkplain ParameterBaseRowViewModel} is the base abstract row view model for {@linkplain ParameterOrOverrideBase}
 * 
 * @param TParameter the type of {@linkplain ParameterOrOverride} this row view model represents
 */
public abstract class ParameterBaseRowViewModel<TParameter extends ParameterBase> extends ParameterValueBaseRowViewModel<TParameter> implements IHaveContainedRows<IValueSetRowViewModel>
{
    /**
     * The {@linkplain ActualFiniteStateList} the represented {@linkplain ParameterBase} depends on
     */
    private Collection<ActualFiniteState> stateDependencies;
        
    /**
     * Initializes a new {@linkplain ParameterBaseRowViewModel}
     * 
     * @param elementDefinition the represented {@linkplain ParameterBase}
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    protected ParameterBaseRowViewModel(TParameter parameterBase, IRowViewModel parentViewModel)
    {
        super(parameterBase, parentViewModel);
    }

    /**
     * Updates this view model properties
     */
    @Override
    public void UpdateProperties()
    {
        super.UpdateProperties();
        this.SetName(this.GetThing().getParameterType().getName());
        
        this.stateDependencies = this.GetThing().getStateDependence() != null
            ? this.GetThing().getStateDependence().getActualState() 
            : Collections.emptyList();
        
        this.ComputeContainedRows();
    }
    
    /**
     * Computes the contained rows
     */
    public void ComputeContainedRowsWithStateAndOptionRows(OptionRowViewModel<TParameter> optionRow, ActualFiniteStateRowViewModel<TParameter> stateRow)
    {
        if(optionRow == null && this.GetThing().isOptionDependent())
        {
            this.ComputeOptionDependencies();
        }
        else if(stateRow == null && !this.stateDependencies.isEmpty())
        {
            this.ComputeStateDependencies(optionRow);
        }
        else
        {
            ActualFiniteState state = null;
            Option option = null;
            
            if(optionRow != null)
            {
                option = optionRow.GetOption();
            }
            
            if(stateRow != null)
            {
                state = stateRow.GetActualFiniteState();
            }
            
            if(optionRow != null && stateRow == null)
            {
                optionRow.UpdateProperties(option, state);
            }
            else if(stateRow != null)
            {
                stateRow.UpdateProperties(option, state);
            }
            else
            {
                this.UpdateProperties(option, state);   
            }
        }
    }
    
    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
         this.ComputeContainedRowsWithStateAndOptionRows(null, null);
    }

    /**
     * Computes the {@linkplain OptionRowViewModel} the represented Parameter depends on
     */
    private void ComputeOptionDependencies()
    {
        this.GetThing().getValueSets()
            .stream()
            .map(x -> x.getActualOption())
            .distinct()
            .forEach(x -> 
            {
                OptionRowViewModel<TParameter> optionRow = new OptionRowViewModel<>(this.GetThing(), x, this);                
                this.ComputeContainedRowsWithStateAndOptionRows(optionRow, null);
                this.containedRows.add(optionRow);
            });
    }

    /**
     * Computes the {@linkplain ActualFiniteStateRowViewModel} the represented Parameter depends on
     * 
     * @param optionRow an optional {@linkplain OptionRowViewModel}
     */
    private void ComputeStateDependencies(OptionRowViewModel<TParameter> optionRow)
    {
        ObservableCollection<IValueSetRowViewModel> containedRows = optionRow == null ? this.containedRows : optionRow.GetContainedRows();
        
        for (ActualFiniteState actualFiniteState : this.stateDependencies)
        {
            ActualFiniteStateRowViewModel<TParameter> stateRow = new ActualFiniteStateRowViewModel<>(this.GetThing(), actualFiniteState, this);
            this.ComputeContainedRowsWithStateAndOptionRows(optionRow, stateRow);
            containedRows.add(stateRow);
        }
    }
}
