/*
 * MappedElementListViewRendererDataProvider.java
 *
 * Copyright (c) 2020-2022 RHEA System S.A.
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
package Renderers;

import java.awt.Color;
import java.net.URL;

import javax.swing.Icon;

import org.netbeans.swing.outline.RenderDataProvider;

import Enumerations.MappingDirection;
import Utils.ImageLoader.ImageLoader;
import ViewModels.ObjectBrowser.RenderDataProvider.ObjectBrowserRenderDataProvider;
import ViewModels.Rows.MappedElementRowViewModel;

/**
 * The {@linkplain MappedElementListViewRendererDataProvider} is the renderer for the {@linkplain MappedElementListView}
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class MappedElementListViewRendererDataProvider extends ObjectBrowserRenderDataProvider implements RenderDataProvider
{
    /**
     * Gets the specified row view model node name
     * 
     * @param rowViewModel the row view model to get the name from
     * @return a {@linkplain String}
     */
    @Override
    public String getDisplayName(Object rowViewModel)
    {
        if(rowViewModel instanceof MappedElementRowViewModel)
        {
            URL target = ((MappedElementRowViewModel<?,?>)rowViewModel).GetMappingDirection() == MappingDirection.FromDstToHub
                    ? ImageLoader.class.getClassLoader().getResource("icon16.png")
                    : ImageLoader.class.getClassLoader().getResource("dst.png");
            
            return String.format("<html><body>&#x1F872; <img src='%s'/></body></html>", target);            
        }
        
        return "";
    }

    /**
     * Gets the background color to be used for rendering this node. Returns
     * null if the standard table background or selected color should be used.
     * 
     * @param rowViewModel the row view model
     * @return a {@linkplain Color}
     */
    @Override
    public Color getBackground(Object rowViewModel)
    {
        return null;
    }

    /**
     * Gets the background color to be used for rendering this node. Returns
     * null if the standard table background or selected color should be used.
     * 
     * @param rowViewModel the row view model
     * @return a {@linkplain Color}
     */
    @Override
    public Icon getIcon(Object rowViewModel)
    { 
        if(rowViewModel instanceof MappedElementRowViewModel)
        {
            if(((MappedElementRowViewModel<?,?>)rowViewModel).GetMappingDirection() == MappingDirection.FromHubToDst)
            {
                return ImageLoader.GetIcon("icon16.png");
            }
            
            return ImageLoader.GetDstIcon();
        }
        
        return null;
    }
}
