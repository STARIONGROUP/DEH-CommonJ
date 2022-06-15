/*
 * MappingListViewTreeViewModel.java
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
package ViewModels.MappingListView;

import java.util.Collection;

import javax.swing.tree.TreeModel;

import org.apache.commons.lang3.tuple.Triple;

import Enumerations.MappingDirection;
import ViewModels.MappedElementListView.MappedElementListViewTreeViewModel;
import ViewModels.MappingListView.Rows.MappingRootRowViewModel;
import ViewModels.ObjectBrowser.BrowserTreeBaseViewModel;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain MappedElementListViewTreeViewModel} is the {@linkplain TreeModel} for the Mapping List browser
 * 
 * @param <TDstElement> the type of element the DST adapter works with
 */
public class MappingListViewTreeViewModel<TDstElement> extends BrowserTreeBaseViewModel
{
    /**
     * Initializes a new {@linkplain MappedElementListViewTreeViewModel}
     * 
     * @param mappedElement a {@linkplain Collection} of {@linkplain Triple} of the {@linkplain #TDstElement} the {@linkplain MappingDirection} and the {@linkplain Thing}
     */
    public MappingListViewTreeViewModel(Collection<Triple<? extends TDstElement, MappingDirection, ? extends Thing>> mappedElements)
    {
        this.root = new MappingRootRowViewModel<TDstElement>(mappedElements);
    }
}
