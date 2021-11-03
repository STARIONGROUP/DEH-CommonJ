/*
 * RequirementBaseTreeElement.java
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
package ViewModels.ObjectBrowser.RequirementTree.Rows;

import java.util.ArrayList;

import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRowsOfRequirementRelatedThings;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.ObjectBrowser.Rows.OwnedDefinedThingRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.commondata.Definition;
import cdp4common.commondata.Thing;
import cdp4common.types.ContainerList;
import java.util.Locale;
import java.util.stream.Collectors;

import Reactive.ObservableCollection;

/**
 * The {@linkplain RequirementBaseTreeElementViewModel} is the base row view model for all row view model that are contained in the Requirement tree
 */
public abstract class RequirementBaseTreeElementViewModel<TThing extends Thing> extends OwnedDefinedThingRowViewModel<TThing> implements IHaveContainedRowsOfRequirementRelatedThings<RequirementBaseTreeElementViewModel<?>>
{
    /**
     * The definition the represented {@linkplain TThing}
     */
    private String definition;
    
    /**
     * Gets the {@linkplain definition}, in case the represented thing is not a {@linkplain DefinedThing} it may be null
     * @return
     */
    public String GetDefinition()
    {
        return this.definition;
    }
    
    /**
     * The {@linkplain ArrayList} of {@linkplain OwnedDefinedThingRowViewModel}
     */
    protected ObservableCollection<RequirementBaseTreeElementViewModel<?>> containedRows = new ObservableCollection<RequirementBaseTreeElementViewModel<?>>();

    /**
     * Gets the contained row the implementing view model has
     * 
     * @return An {@linkplain ArrayList} of {@linkplain OwnedDefinedThingRowViewModel}
     */
    @Override
    public ObservableCollection<RequirementBaseTreeElementViewModel<?>> GetContainedRows()
    {
        return this.containedRows;
    }
    
    /**
     * Gets all the contained rows of the provided type
     * 
     * @param <TRequirementBaseRowViewModel> the type of rows to get e.g. {@linkplain RequirementRowViewModel} or {@linkplain RequirementGroupRowViewModel}
     * @param clazz the {@linkplain Class} of the {@linkplain TRequirementBaseRowViewModel}
     * @return an {@linkplain ObservableCollection} of {@linkplain TRequirementBaseRowViewModel}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <TRequirementBaseRowViewModel extends RequirementBaseTreeElementViewModel<?>> ObservableCollection<TRequirementBaseRowViewModel> GetAllContainedRowsOfType(Class<TRequirementBaseRowViewModel> clazz)
    {
        ObservableCollection<TRequirementBaseRowViewModel> result = new ObservableCollection<TRequirementBaseRowViewModel>();
        
        result.addAll(this.containedRows.stream()
                            .filter(x -> clazz.isAssignableFrom(x.getClass()))
                            .map(x -> (TRequirementBaseRowViewModel)x).collect(Collectors.toList()));
        
        if(this.containedRows.stream().anyMatch(x -> x.containedRows.size() > 0))
        {
            result.addAll(
                    this.containedRows.stream()
                        .flatMap(x -> x.GetAllContainedRowsOfType(clazz).stream())
                        .collect(Collectors.toList()));
        }

        return result;
    }
    
    /**
     * Initializes a new {@linkplain RequirementBaseTreeElementViewModel}
     * 
     * @param thing the {@linkplain TThing} that is represented
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    protected RequirementBaseTreeElementViewModel(TThing thing, IRowViewModel parentViewModel)
    {
        super(thing, parentViewModel);
    }
    
    /**
     * Updates this view model properties
     */
    @Override
    public void UpdateProperties()
    {
        super.UpdateProperties();
        
        if(this.GetThing() instanceof DefinedThing)
        {
            ContainerList<Definition> definitions = ((DefinedThing)this.GetThing()).getDefinition();

            String currentCultureCode = String.format("%s-%s", Locale.getDefault().getCountry(), Locale.getDefault().getLanguage());
                    
            Definition definition = definitions
                    .stream()
                    .filter(x -> x.getLanguageCode().equalsIgnoreCase(currentCultureCode))
                    .findAny()
                    .orElse(null);
            
            if(definition != null)
            {
                this.definition = definition.getContent();
            }
            else
            {
                definitions.stream()
                        .findFirst()
                        .ifPresent(x -> this.definition = x.getContent());
            }
        }
    }
    
    /**
     * Computes this row view model contained rows
     */
    @Override
    public void ComputeContainedRows()
    {
        this.containedRows.clear();
    }
}
