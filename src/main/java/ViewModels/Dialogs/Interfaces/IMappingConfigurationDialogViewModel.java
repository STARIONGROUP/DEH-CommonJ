/*
 * IMappingConfigurationDialogViewModel.java
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
package ViewModels.Dialogs.Interfaces;

import java.util.Collection;

import Reactive.ObservableCollection;
import ViewModels.Interfaces.IElementDefinitionBrowserViewModel;
import ViewModels.Interfaces.IObjectBrowserBaseViewModel;
import ViewModels.Interfaces.IRequirementBrowserViewModel;
import ViewModels.Interfaces.IViewModel;
import ViewModels.MappedElementListView.Interfaces.IMappedElementListViewViewModel;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.commondata.DefinedThing;
import cdp4common.commondata.Thing;
import io.reactivex.Observable;

/**
 * The IMappingConfigurationDialogViewModel is the base interface for the {@linkplain IDstToHubMappingConfigurationDialogViewModel} 
 * and the {@linkplain IHubToDstMappingConfigurationDialogViewModel}
 * 
 * @param <TSourceElement> the type of source element depending on the mapping direction
 * @param <TDstElement> the type of element the DST adapter works with
 * @param <TRowViewModel> the type of base row view mode DST adapter uses for the DST object browser
 */
public interface IMappingConfigurationDialogViewModel<TSourceElement, TDstElement, TRowViewModel extends IRowViewModel> extends IViewModel
{
    /**
     * Sets the mappedElement picked to open this dialog and sets the DST tree
     * 
     * @param selectedElement the collection of {@linkplain #TElement}
     */
    void SetMappedElement(Collection<TSourceElement> selectedElement);
    
    /**
     * Occurs when the user sets the target element of the current mapped element to be a
     * 
     * @param selected the new {@linkplain boolean} value
     */
    void WhenMapToNewElementCheckBoxChanged(boolean selected);

    /**
     * Resets the pre-mapped things to the default way 
     */
    void ResetPreMappedThings();

    /**
     * Gets an {@linkplain Observable} value indicating whether the mapToNewHubElementCheckBox should be enabled 
     * 
     * @return an {@linkplain Observable} of {@linkplain Boolean}
     */
    Observable<Boolean> GetShouldMapToNewElementCheckBoxBeEnabled();

    /**
     * Gets the DST {@linkplain IObjectBrowserBaseViewModel}
     * 
     * @return an {@linkplain IObjectBrowserBaseViewModel}
     */
    IObjectBrowserBaseViewModel<TRowViewModel> GetDstObjectBrowserViewModel();

    /**
     * Gets the {@linkplain IRequirementBrowserViewModel}
     * 
     * @return an {@linkplain IRequirementBrowserViewModel}
     */
    IRequirementBrowserViewModel GetRequirementBrowserViewModel();

    /**
     * Gets the {@linkplain IElementDefinitionBrowserViewModel}
     * 
     * @return an {@linkplain IElementDefinitionBrowserViewModel}
     */
    IElementDefinitionBrowserViewModel GetElementDefinitionBrowserViewModel();
    
    /**
     * Gets the {@linkplain IMappedElementListViewViewModel}
     * 
     * @return an {@linkplain IMappedElementListViewViewModel}
     */
    IMappedElementListViewViewModel<TDstElement> GetMappedElementListViewViewModel();

    /**
     * Sets the selectedMappedElement
     * 
     * @param mappedElement the {@linkplain MappedElementRowViewModel} that is to be selected
     */
    void SetSelectedMappedElement(MappedElementRowViewModel<DefinedThing, TDstElement> mappedElement);

    /**
     * The selected {@linkplain MappedElementRowViewModel}
     * 
     * @return a {@linkplain Observable} of {@linkplain MappedElementRowViewModel}
     */
    Observable<MappedElementRowViewModel<DefinedThing, TDstElement>> GetSelectedMappedElement();

    /**
     * Gets the collection of mapped element
     * 
     * @return {@linkplain ObservableCollection} of {@linkplain MappedElementRowViewModel}
     */
    ObservableCollection<MappedElementRowViewModel<DefinedThing, TDstElement>> GetMappedElementCollection();
}
