/*
 * ParameterValueBaseRowViewModel.java
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

import java.util.ArrayList;

import com.google.common.math.Quantiles.Scale;

import Reactive.ObservableCollection;
import Utils.ValueSetUtils;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.ObjectBrowser.Interfaces.IValueSetRowViewModel;
import ViewModels.ObjectBrowser.Rows.OwnedDefinedThingRowViewModel;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.ActualFiniteState;
import cdp4common.engineeringmodeldata.Option;
import cdp4common.engineeringmodeldata.ParameterBase;
import cdp4common.engineeringmodeldata.ParameterSwitchKind;
import cdp4common.engineeringmodeldata.ParameterValueSetBase;
import cdp4common.engineeringmodeldata.ValueSet;
import cdp4common.sitedirectorydata.CompoundParameterType;
import cdp4common.sitedirectorydata.ParameterType;
import cdp4common.sitedirectorydata.SampledFunctionParameterType;

/**
 * The {@linkplain ParameterValueBaseRowViewModel} is the base abstract row view model for {@linkplain ParameterValueRowViewModel} 
 * as well as {@linkplain OptionRowViewModel} and {@linkplain ActualFiniteStateRowViewModel}
 */
public abstract class ParameterValueBaseRowViewModel<TParameter extends ParameterBase> extends OwnedDefinedThingRowViewModel<TParameter> implements IValueSetRowViewModel, IHaveContainedRows<IValueSetRowViewModel>
{
    /**
     * The {@linkplain ParameterSwitchKind} value that the represented parameter value has
     */
    protected ParameterSwitchKind valueSwitch;
    
    /**
     * Gets the {@linkplain valueSwitch} value that the represented parameter value has
     * 
     * @return the {@linkplain ParameterSwitchKind}
     */
    public ParameterSwitchKind GetValueSwitch()
    {
        return this.valueSwitch;
    }
    
    /**
     * The {@linkplain Scale} name that the represented parameter value has
     */
    protected String scale;

    /**
     * Gets the {@linkplain Scale} name that the represented parameter value has
     * 
     * @return the scale name {@linkplain String}
     */
    public String GetScale()
    {
        return this.scale;
    }
    
    /**
     * The published or actual value that the represented parameter value has
     */
    protected String publishedValue;

    /**
     * Gets the published or actual value that the represented parameter value has
     * 
     * @return the published value {@linkplain String}
     */
    public String GetPublishedValue()
    {
        return this.publishedValue;
    }
    
    /**
     * The computed value that the represented parameter value has
     */
    protected String computedValue;

    /**
     * Gets the computed value that the represented parameter value has
     * 
     * @return the computed value {@linkplain String}
     */
    public String GetComputedValue()
    {
        return this.computedValue;
    }
    
    /**
     * The manual value that the represented parameter value has
     */
    protected String manualValue;

    /**
     * Gets the manual that the represented parameter value has
     * 
     * @return the manual value {@linkplain String}
     */
    public String GetManualValue()
    {
        return this.manualValue;
    }
    
    /**
     * The reference value that the represented parameter value has
     */
    protected String referenceValue;

    /**
     * Gets the reference that the represented parameter value has
     * 
     * @return the reference value {@linkplain String}
     */
    public String GetReferenceValue()
    {
        return this.referenceValue;
    }
    
    /**
     * The formula value that the represented parameter value has
     */
    protected String formula;
    
    /**
     * Gets the formula that the represented parameter value has
     * 
     * @return the formula value {@linkplain String}
     */
    public String GetFormula()
    {
        return this.formula;
    }
    
    /**
     * The {@linkplain ArrayList} of {@linkplain IValueSetRowViewModel}
     */
    protected ObservableCollection<IValueSetRowViewModel> containedRows = new ObservableCollection<>();

    /**
     * Gets the contained row the implementing view model has
     * 
     * @return An {@linkplain ArrayList} of {@linkplain IValueSetRowViewModel}
     */
    @Override
    public ObservableCollection<IValueSetRowViewModel> GetContainedRows()
    {
        return this.containedRows;
    }
    
    /**
     * Initializes a new {@linkplain ParameterValueBaseRowViewModel}
     * 
     * @param thing the represented {@linkplain Thing} either an value set option row, an actual finite state row or a value from a value set
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    protected ParameterValueBaseRowViewModel(TParameter thing, IRowViewModel parentViewModel)
    {
        super(thing, parentViewModel);
    }
    

    /**
     * Computes the properties and contained rows for the represented {@linkplain ParameterBase}
     * 
     * @param option the optional {@linkplain Option} that the current valueSet depends
     * @param state the optional {@linkplain ActualFiniteState} that the current valueSet depends
     */
    protected void UpdateProperties(Option option, ActualFiniteState state)
    {
        ValueSet valueSet = ValueSetUtils.QueryParameterBaseValueSet(this.GetThing(), option, state);
        
        ParameterType parameterType = this.GetThing().getParameterType();
        
        int numberOfValue = parameterType.getNumberOfValues();
        
        if(parameterType instanceof SampledFunctionParameterType)
        {
            SetSampledFunctionParameterProperties(valueSet, numberOfValue);
        }        
        else if(numberOfValue == 1)
        {
            this.UpdateProperties(valueSet, 0);
        }
        else
        {
            for (int index = 0; index < numberOfValue; index ++)
            {
                ParameterValueRowViewModel<TParameter> valueRow = new ParameterValueRowViewModel<>(this.GetThing(), valueSet, index, this);
                
                if(parameterType instanceof CompoundParameterType)
                {
                    valueRow.SetName(((CompoundParameterType)parameterType).getComponent().get(index).getShortName());
                }
                else
                {
                    valueRow.SetName(this.GetThing().getParameterType().getName());
                }

                this.containedRows.add(valueRow);
            }
        }
    }

    /**
     * Update this row view model properties based on a valueSet
     * 
     * @param valueSet the {@linkplain ValueSet} to represent
     * @param index the {@linkplain int} index to get the represented value from the value set
     */
    protected void UpdateProperties(ValueSet valueSet, int index)
    {        
        this.valueSwitch = valueSet.getValueSwitch();
        
        if(this.GetThing().getScale() != null)
        {
            this.scale = this.GetThing().getScale().getShortName();                
        }
        else
        {
            this.scale = "-";
        }
        
        this.publishedValue = valueSet.getActualValue().get(index);
        this.referenceValue = valueSet.getReference().get(index);
        this.computedValue = valueSet.getComputed().get(index);
        this.manualValue = valueSet.getManual().get(index);
        this.formula = valueSet.getFormula().get(index);
    }

    /**
     * Sets the properties in case that the represented {@linkplain ParameterBase} has a {@linkplain ParameterType} of type {@linkplain SampledFunctionParameterType}
     * 
     * @param valueSet the current {@linkplain ValueSet}
     * @param numberOfValue the number of value this {@linkplain ParameterBase} should have
     */
    private void SetSampledFunctionParameterProperties(ValueSet valueSet, int numberOfValue)
    {
        this.valueSwitch = valueSet.getValueSwitch();
        String regex = "[%sx%s]";
		this.computedValue = String.format(regex, valueSet.getComputed().size() / numberOfValue, numberOfValue);
        this.manualValue = String.format(regex, valueSet.getManual().size() / numberOfValue, numberOfValue);
        this.referenceValue = String.format(regex, valueSet.getReference().size() / numberOfValue, numberOfValue);
        this.formula = String.format(regex, valueSet.getFormula().size() / numberOfValue, numberOfValue);
        
        if(valueSet instanceof ParameterValueSetBase)
        {
            this.publishedValue = String.format(regex, ((ParameterValueSetBase)valueSet).getPublished().size() / numberOfValue, numberOfValue);
        }
        else
        {
            this.publishedValue = String.format(regex, valueSet.getComputed().size() / numberOfValue, numberOfValue);                
        }
    }
}
