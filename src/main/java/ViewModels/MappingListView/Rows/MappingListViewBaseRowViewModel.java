/*
 * MappingListViewBaseRowViewModel.java
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
package ViewModels.MappingListView.Rows;

import ViewModels.ObjectBrowser.Rows.RowViewModel;
import cdp4common.commondata.ClassKind;

/**
 * The {@linkplain MappingListViewBaseRowViewModel} is the base row view model for {@linkplain MappingListViewRootRowViewModel}
 */
public class MappingListViewBaseRowViewModel extends RowViewModel
{
    /**
     * Field for {@linkplain #GetId()}
     */
    private String id;
    
    /**
     * Gets the id
     * 
     * @return a string
     */
    public String GetId()
    {
        return this.id;
    }
    
    /**
     * Field for {@linkplain #GetName()}
     */
    private String name;
    
    /**
     * Gets the name to display
     * 
     * @return a string
     */
    public String GetName()
    {
        return this.name;
    }
    
    /**
     * Field for {@linkplain #GetValue()}
     */
    private String value;
    
    /**
     * Gets the name to display
     * 
     * @return a string
     */
    public String GetValue()
    {
        return this.value;
    }

    /**
     * Field for {@linkplain #GetClassKind()}
     */
    private ClassKind classKind;
    
    /**
     * Gets the {@linkplain ClassKind} of the represented element
     * 
     * @return a {@linkplain ClassKind}
     */
    public ClassKind GetClassKind()
    {
        return this.classKind;
    }
    
    /**
     * Initializes a new {@linkplain MappingListViewBaseRowViewModel}
     * 
     * @param id the {@linkplain String} id of the represented element
     * @param name the {@linkplain String} name of the represented element
     * @param value the {@linkplain String} value of the represented element
     * @param classKind the {@linkplain ClassKind} of the represented element
     */
    public MappingListViewBaseRowViewModel(String id, String name, String value, ClassKind classKind)
    {
        this.id = id;
        this.name = name;
        this.value = value;
        this.classKind = classKind;
    }
}
