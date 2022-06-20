/*
 * MappingConfigurationDialogViewModel.java
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
package ViewModels.Dialogs;

import static Utils.Operators.Operators.AreTheseEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import DstController.IDstControllerBase;
import Enumerations.MappedElementRowStatus;
import HubController.IHubController;
import Reactive.ObservableCollection;
import Reactive.ObservableValue;
import Utils.Ref;
import ViewModels.Dialogs.Interfaces.IMappingConfigurationDialogViewModel;
import ViewModels.Interfaces.IElementDefinitionBrowserViewModel;
import ViewModels.Interfaces.IRequirementBrowserViewModel;
import ViewModels.MappedElementListView.Interfaces.IMappedElementListViewViewModel;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.Requirement;
import io.reactivex.Observable;

/**
 * The MappingConfigurationDialogViewModel is the base view model for the {@linkplain DstToHubMappingConfigurationDialogViewModel} 
 * and {@linkplain the HubToDstMappingConfigurationDialogViewModel}
 * 
 * @param <TSourceElement> the type of source element depending on the mapping direction
 * @param <TDstElement> the type of element the DST adapter works with
 * @param <TRowViewModel> the type of base row view model the DST object browser displays
 */
public abstract class MappingConfigurationDialogViewModel<TSourceElement, TDstElement, TRowViewModel extends IRowViewModel> 
                            implements IMappingConfigurationDialogViewModel<TSourceElement, TDstElement, TRowViewModel>
{
    /**
     * This view model logger
     */
    protected Logger logger = LogManager.getLogger();
    
    /**
     * The {@linkplain IDstController}
     */
    protected final IDstControllerBase<TDstElement> dstController;
    
    /**
     * The {@linkplain IHubController}
     */
    protected final IHubController hubController;
    
    /**
     * The {@linkplain Collection} of {@linkplain #TElement} that were originally selected 
     */
    protected Collection<TSourceElement> originalSelection = new ArrayList<>();
        
    /**
     * Backing field for {@linkplain #GetElementDefinitionBrowserViewModel}
     */
    protected IElementDefinitionBrowserViewModel elementDefinitionBrowserViewModel;
    
    /**
     * Gets the {@linkplain IElementDefinitionBrowserViewModel}
     * 
     * @return an {@linkplain IElementDefinitionBrowserViewModel}
     */
    @Override
    public IElementDefinitionBrowserViewModel GetElementDefinitionBrowserViewModel()
    {
        return this.elementDefinitionBrowserViewModel;
    }
    
    /**
     * Backing field for {@linkplain #GetRequirementBrowserViewModel}
     */
    protected IRequirementBrowserViewModel requirementBrowserViewModel;
    
    /**
     * Gets the {@linkplain IRequirementBrowserViewModel}
     * 
     * @return an {@linkplain IRequirementBrowserViewModel}
     */
    @Override
    public IRequirementBrowserViewModel GetRequirementBrowserViewModel()
    {
        return this.requirementBrowserViewModel;
    }

    /**
     * Backing field for {@linkplain #GetMappedElementListViewViewModel}
     */
    protected IMappedElementListViewViewModel<TDstElement> mappedElementListViewViewModel;

    /**
     * Gets the {@linkplain IMappedElementListViewViewModel}
     * 
     * @return an {@linkplain IMappedElementListViewViewModel}
     */
    public IMappedElementListViewViewModel<TDstElement> GetMappedElementListViewViewModel()
    {
        return this.mappedElementListViewViewModel;
    }
    
    /**
     * The {@linkplain ObservableCollection} of {@linkplain MappedElementRowViewModel} that represents all the mapped elements
     */
    protected ObservableCollection<MappedElementRowViewModel<DefinedThing, TDstElement>> mappedElements = new ObservableCollection<>();
    
    /**
     * Gets the collection of mapped element
     * 
     * @return {@linkplain ObservableCollection} of {@linkplain MappedElementRowViewModel}
     */
    @Override
    public ObservableCollection<MappedElementRowViewModel<DefinedThing, TDstElement>> GetMappedElementCollection()
    {
        return this.mappedElements;
    }

    /**
     * Backing field for {@linkplain GetSelectedMappedElement}
     */
    protected ObservableValue<MappedElementRowViewModel<DefinedThing, TDstElement>> selectedMappedElement = new ObservableValue<>(null);
    
    /**
     * The selected {@linkplain MappedElementRowViewModel}
     * 
     * @return a {@linkplain Observable} of {@linkplain MappedElementRowViewModel}
     */
    @Override
    public Observable<MappedElementRowViewModel<DefinedThing, TDstElement>> GetSelectedMappedElement()
    {
        return this.selectedMappedElement.Observable();
    }

    /**
     * Sets the selectedMappedElement
     * 
     * @param mappedElement the {@linkplain MappedElementRowViewModel} that is to be selected
     */
    @Override
    public void SetSelectedMappedElement(MappedElementRowViewModel<DefinedThing, TDstElement> mappedElement)
    {
        mappedElement.SetIsSelected(true);
        this.selectedMappedElement.Value(mappedElement);
    }
    
    /**
     * Backing field for {@linkplain GetShouldMapToNewHubElementCheckBoxBeEnabled}
     */
    protected ObservableValue<Boolean> shouldMapToNewHubElementCheckBoxBeEnabled = new ObservableValue<>(true, Boolean.class);

    /**
     * Gets an {@linkplain Observable} value indicating whether the mapToNewHubElementCheckBox should be enabled 
     * 
     * @return an {@linkplain Observable} of {@linkplain Boolean}
     */
    @Override
    public Observable<Boolean> GetShouldMapToNewElementCheckBoxBeEnabled()
    {
        return this.shouldMapToNewHubElementCheckBoxBeEnabled.Observable();
    }
    
    /**
     * Initializes a new {@linkplain MappingConfigurationDialogViewModel}
     * 
     * @param dstController the {@linkplain IDstControllerBase}
     * @param hubController the {@linkplain IHubController}
     * @param elementDefinitionBrowserViewModel the {@linkplain IElementDefinitionBrowserViewModel}
     * @param requirementBrowserViewModel the {@linkplain IRequirementBrowserViewModel}
     * @param dstObjectBrowserViewModel the {@linkplain ICapellaObjectBrowserViewModel}
     * @param mappedElementListViewViewModel the {@linkplain IMappedElementListViewViewModel}
     */
    protected MappingConfigurationDialogViewModel(IDstControllerBase<TDstElement> dstController, IHubController hubController, 
            IElementDefinitionBrowserViewModel elementDefinitionBrowserViewModel, IRequirementBrowserViewModel requirementBrowserViewModel,
            IMappedElementListViewViewModel<TDstElement> mappedElementListViewViewModel)
    {
        this.elementDefinitionBrowserViewModel = elementDefinitionBrowserViewModel;
        this.requirementBrowserViewModel = requirementBrowserViewModel; 
        this.dstController = dstController;
        this.hubController = hubController;
        this.mappedElementListViewViewModel = mappedElementListViewViewModel;
    }
        
    /**
     * Initializes the {@linkplain Observable}s of this view model
     */
    protected void InitializeObservables()
    {
        this.elementDefinitionBrowserViewModel.GetSelectedElement()
            .subscribe(x -> this.SetHubElement(x.GetThing()));

        this.requirementBrowserViewModel.GetSelectedElement()
            .subscribe(x -> this.SetHubElement(x.GetThing()));

        this.mappedElementListViewViewModel.GetSelectedElement()
            .subscribe(x -> this.SetSelectedMappedElement(x));
        
        this.selectedMappedElement.Observable().subscribe(
                x -> this.shouldMapToNewHubElementCheckBoxBeEnabled.Value(
                        x != null && x.GetRowStatus() != MappedElementRowStatus.ExistingMapping),
                x -> this.logger.catching(x));        
    }

    /**
     * Sets the Hub element on the selected element if the element is compatible
     * 
     * @param thing the {@linkplain Thing} to assign
     */
    private void SetHubElement(Thing thing)
    {
        if(this.selectedMappedElement.Value() == null || this.selectedMappedElement.Value().GetRowStatus() == MappedElementRowStatus.ExistingMapping)
        {
            return;
        }
        
        if(thing instanceof ElementDefinition
                && this.selectedMappedElement.Value().GetTThingClass().isAssignableFrom(ElementDefinition.class))
        {
            this.SetHubElement(thing, ElementDefinition.class);
        }
        else if(thing instanceof Requirement
                && this.selectedMappedElement.Value().GetTThingClass().isAssignableFrom(Requirement.class))
        {
            this.SetHubElement(thing, Requirement.class);
        }
        else
        {
            this.logger.warn("Thing is not compatible with the current selected mapped element!");
        }
    }
    
    /**
     * Sets the Hub element on the selected element
     * 
     * @param thing the {@linkplain Thing} to assign
     * @param clazz the class of the {@linkplain Thing}
     */
    @SuppressWarnings("unchecked")
    private <TThing extends DefinedThing> void SetHubElement(Thing thing, Class<TThing> clazz)
    {
        MappedElementRowViewModel<TThing, ? extends TDstElement> mappedElementRowViewModel = 
                (MappedElementRowViewModel<TThing, ? extends TDstElement>)this.selectedMappedElement.Value();
        
        mappedElementRowViewModel.SetHubElement((TThing)thing.clone(true));
        
        this.shouldMapToNewHubElementCheckBoxBeEnabled.Value(false);
        mappedElementRowViewModel.SetShouldCreateNewTargetElement(false);
        
        this.UpdateRowStatus(this.selectedMappedElement.Value(), clazz);
    }
    

    /**
     * Updates the {@linkplain MappedElementRowStatus} of the provided {@linkplain MappedElementRowViewModel}
     * 
     * @param mappedElementRowViewModel the {@linkplain MappedElementRowViewModel} of which to update the row status
     * @param clazz the {@linkplain java.lang.Class} of the {@linkplain Thing} represented in the {@linkplain MappedElementRowViewModel}
     */
    protected <TThing extends Thing> void UpdateRowStatus(MappedElementRowViewModel<? extends Thing, ? extends TDstElement> mappedElementRowViewModel, Class<TThing> clazz)
    {
        Ref<TThing> refThing = new Ref<>(clazz);
        mappedElementRowViewModel.SetRowStatus(MappedElementRowStatus.None);
        
        if(mappedElementRowViewModel.GetShouldCreateNewTargetElementValue())
        {
            mappedElementRowViewModel.SetRowStatus(MappedElementRowStatus.NewElement);
        }
        else if(mappedElementRowViewModel.GetHubElement() != null)
        {
            if(this.dstController.GetDstMapResult().stream()
                    .filter(x -> x.GetHubElement().getClass() == clazz)
                    .anyMatch(x -> AreTheseEquals(x.GetHubElement().getIid(), mappedElementRowViewModel.GetHubElement().getIid())))
            {
                mappedElementRowViewModel.SetRowStatus(MappedElementRowStatus.ExistingMapping);
            }
            else if(this.hubController.TryGetThingById(mappedElementRowViewModel.GetHubElement().getIid(), refThing))
            {
                mappedElementRowViewModel.SetRowStatus(MappedElementRowStatus.ExisitingElement);
            }
        }
    }
    
    /**
     * Resets the pre-mapped things to the default way 
     */
    @Override
    public void ResetPreMappedThings()
    {
        this.UpdateProperties();
    }
    
    /**
     * Updates this view model properties
     */
    protected abstract void UpdateProperties();
    
    /**
     * Updates this view model properties
     * 
     * @param mappedElementCollection the collection of existing mapped element
     */
    protected void UpdateProperties(ObservableCollection<MappedElementRowViewModel<DefinedThing, TDstElement>> mappedElementCollection)
    {
        this.mappedElements.clear();
        
        for (MappedElementRowViewModel<DefinedThing, TDstElement> mappedElementRowViewModel : mappedElementCollection)
        {
            this.mappedElements.add(mappedElementRowViewModel);
        }
        
        this.PreMap(this.originalSelection);
        this.mappedElementListViewViewModel.BuildTree(this.mappedElements);
    }

    /**
     * Sets the mappedElement picked to open this dialog and sets the DST tree
     * 
     * @param selectedElements the collection of {@linkplain EObject}
     */
    @Override
    public void SetMappedElement(Collection<TSourceElement> selectedElements)
    {
        this.originalSelection = selectedElements;
        this.UpdateProperties();
    }
    
    /**
     * Pre-map the selected elements
     * 
     * @param selectedElement the collection of {@linkplain #TSourceElement}
     */
    protected abstract void PreMap(Collection<TSourceElement> selectedElements);
        
    /**
     * Occurs when the user sets the target element of the current mapped element to be a
     * 
     * @param selected the new {@linkplain boolean} value
     */
    @Override
    public void WhenMapToNewElementCheckBoxChanged(boolean selected)
    {
        if(this.selectedMappedElement.Value() == null)
        {
            return;
        }

        if(selected != this.selectedMappedElement.Value().GetShouldCreateNewTargetElementValue())
        {
            this.selectedMappedElement.Value().SetShouldCreateNewTargetElement(selected);
        }
    }
}
