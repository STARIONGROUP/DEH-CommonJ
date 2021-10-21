/*
 * ParameterValueRowViewModel.java
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

import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.ObjectBrowser.Interfaces.IValueSetRowViewModel;
import cdp4common.engineeringmodeldata.ParameterBase;
import cdp4common.engineeringmodeldata.ValueSet;

/**
 * The {@linkplain ParameterValueRowViewModel} is the view model representing one value from a {@linkplain IValueSet}
 */
public class ParameterValueRowViewModel<TParameter extends ParameterBase> extends ParameterValueBaseRowViewModel<TParameter> implements IValueSetRowViewModel, IHaveContainedRows<IValueSetRowViewModel>
{    
    /**
     * The {@linkplain int} model code
     */
    private int valueSetIndex;
    
    /**
     * Gets the {@linkplain valueSetIndex} of the represented {@linkplain TThing} to display 
     * 
     * @return an {@linkplain int}
     */
    public int GetValueSetIndex()
    {
        return this.valueSetIndex;
    }

    /**
     * The {@linkplain ValueSet}
     */
    private ValueSet valueSet;
    
    /**
     * Gets the {@linkplain valueSet} of the represented {@linkplain TThing} to display 
     * 
     * @return a {@linkplain ValueSet}
     */
    public ValueSet GetValueSet()
    {
        return this.valueSet;
    }
    
    /**
     * Initializes a new {@linkplain ParameterValueRowViewModel}
     * 
     * @param parameter the {@linkplain ParameterBase} represented by this row view model
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    public ParameterValueRowViewModel(TParameter parameter, ValueSet valueSet, int valueSetIndex, IRowViewModel parentViewModel)
    {
        super(parameter, parentViewModel);
        this.valueSetIndex = valueSetIndex;
        this.valueSet = valueSet;
        this.UpdateProperties();
    }
    
    /**
     * Updates this view model properties
     */
    @Override
    public void UpdateProperties()
    {
        super.UpdateProperties();
        this.UpdateProperties(this.valueSet, this.valueSetIndex);
    }
    
    /**
     * Computes the contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
    }
}
