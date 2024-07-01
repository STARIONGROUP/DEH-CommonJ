/*
 * ValueSetOptionRowViewModel.java
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
package ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters;

import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.ObjectBrowser.Interfaces.IValueSetRowViewModel;
import cdp4common.engineeringmodeldata.Option;
import cdp4common.engineeringmodeldata.ParameterBase;

/**
 * The {@linkplain OptionRowViewModel} is the row view model to be contained by {@linkplain ParameterOrOverrideBaseRowViewModel} 
 * where the represented {@linkplain Thing} is option dependent
 */
public class OptionRowViewModel<TParameter extends ParameterBase> extends ParameterBaseRowViewModel<TParameter> implements IValueSetRowViewModel, IHaveContainedRows<IValueSetRowViewModel>
{
    /**
     * The {@linkplain Option} this view model represents
     */
    private Option option;
    
    /**
     * Gets the {@linkplain Option} this view model represents
     * 
     * @return the {@linkplain Option}
     */
    public Option GetOption()
    {
        return this.option;
    }
    
    /**
     * Initializes a new {@linkplain OptionRowViewModel}
     * 
     * @param option the {@linkplain Option} represented by this row view model
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    protected OptionRowViewModel(TParameter parameter, Option option, IRowViewModel parentViewModel)
    {
        super(parameter, parentViewModel);
        this.option = option;
        this.SetName(this.option.getName());
    }
}
