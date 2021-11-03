/*
 * ParameterSubscriptionRowViewModel.java
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

import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import cdp4common.engineeringmodeldata.ParameterSubscription;

/**
 * The {@linkplain ParameterSubscriptionRowViewModel} is the row view model that represent a {@linkplain ParameterSubscription} in the {@linkplain ElementDefinition} tree
 */
public class ParameterSubscriptionRowViewModel extends ParameterBaseRowViewModel<ParameterSubscription>
{
    /**
     * Initializes a new {@linkplain ParameterRowViewModel}
     * 
     * @param parameterSubscription the {@linkplain ParameterSubscription} that this view model represents
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    public ParameterSubscriptionRowViewModel(ParameterSubscription parameterSubscription, IRowViewModel parentViewModel)
    {
        super(parameterSubscription, parentViewModel);
        this.UpdateProperties();
    }
}
