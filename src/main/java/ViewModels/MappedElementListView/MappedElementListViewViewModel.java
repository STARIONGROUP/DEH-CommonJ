/*
 * MappedElementListViewViewModel.java
 *
 * Copyright (c) 2020-2024 Starion Group S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-Capella
 *
 * The DEH-Capella is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-Capella is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package ViewModels.MappedElementListView;

import java.util.Collection;

import javax.swing.ListSelectionModel;
import javax.swing.tree.TreeModel;

import org.netbeans.swing.outline.DefaultOutlineModel;
import org.netbeans.swing.outline.OutlineModel;
import Reactive.ObservableValue;
import ViewModels.ObjectBrowserBaseViewModel;
import ViewModels.MappedElementListView.Interfaces.IMappedElementListViewViewModel;
import ViewModels.Rows.MappedElementRowViewModel;
import cdp4common.commondata.DefinedThing;
import io.reactivex.Observable;

/**
 * The {@linkplain MappedElementListViewViewModel} is the {@linkplain ObjectBrowserBaseViewModel}
 * 
 * @param <TElement> the type of element the dst adapter works with
 */
public class MappedElementListViewViewModel<TElement> extends ObjectBrowserBaseViewModel<MappedElementRowViewModel<DefinedThing, TElement>> implements IMappedElementListViewViewModel<TElement>
{    
    /**
     * Backing field for {@linkplain GetSelectedElement}
     */
    private ObservableValue<MappedElementRowViewModel<DefinedThing, TElement>> selectedElement = new ObservableValue<>();
    
    /**
     * Gets the {@linkplain Observable} of {@linkplain ElementRowViewModel} that yields the selected element
     * 
     * @return an {@linkplain Observable} of {@linkplain ElementRowViewModel}
     */
    @Override
    public Observable<MappedElementRowViewModel<DefinedThing, TElement>> GetSelectedElement()
    {
        return this.selectedElement.Observable();
    }
   
    /**
     * Occurs when the selection changes
     * 
     * @param selectedRow the selected {@linkplain ClassRowViewModel}
     */
    @Override
    public void OnSelectionChanged(MappedElementRowViewModel<DefinedThing, TElement> selectedRow)
    {
        this.selectedElement.Value(selectedRow);            
    }
    
    /**
     * Initializes a new {@linkplain MappedElementListViewViewModel}
     */
    public MappedElementListViewViewModel() 
    {
        this.selectionMode = ListSelectionModel.SINGLE_SELECTION; 
    }    
    
    /**
     * Creates the {@linkplain OutlineModel} tree from the provided {@linkplain Collection} of {@linkplain EObject}
     * 
     * @param elements the {@linkplain Collection} of {@linkplain EObject}
     */
    @Override
    public void BuildTree(Collection<MappedElementRowViewModel<DefinedThing, TElement>> elements)
    {
        this.browserTreeModel.Value(DefaultOutlineModel.createOutlineModel(
                new MappedElementListViewTreeViewModel<TElement>(elements),
                new MappedElementListViewTreeRowViewModel(), true));
                
        this.isTheTreeVisible.Value(true);
    }

    /**
     * Updates this view model {@linkplain TreeModel}
     * 
     * @param isConnected a value indicating whether the session is open
     */
    protected void UpdateBrowserTrees(Boolean isConnected) 
    {
    	// Added comment to satisfy the code smell raised by the rule 1186.
    	// This method is empty because nothing has to be done there.
    }
}
