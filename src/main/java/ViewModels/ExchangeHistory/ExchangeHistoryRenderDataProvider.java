/*
 * ExchangeHistoryRenderDataProvider.java
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
package ViewModels.ExchangeHistory;

import java.awt.Color;

import javax.swing.Icon;

import org.netbeans.swing.outline.RenderDataProvider;

import Utils.ImageLoader.ImageLoader;
import ViewModels.ExchangeHistory.Rows.ExchangeHistoryTimeStampRowViewModel;

/**
 * The {@linkplain ExchangeHistoryRenderDataProvider} is the {@linkplain RenderDataProvider} for {@linkplain ExchangeHistoryDialog} object browser
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class ExchangeHistoryRenderDataProvider implements RenderDataProvider
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
        if(rowViewModel instanceof ExchangeHistoryTimeStampRowViewModel)
        {
            return String.format("<html><b>%s</b></html>", ((ExchangeHistoryTimeStampRowViewModel)rowViewModel).GetName());
        }
        
        return "";
    }

    /**
     * Gets an value indicating to the tree whether the display name for this object should use HTMLrendering
     * 
     * @param rowViewModel the row view model
     * @return a {@linkplain boolean}
     */
    @Override
    public boolean isHtmlDisplayName(Object rowViewModel)
    {
        return true;
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
        return Color.WHITE;
    }

    /**
     * Gets the foreground color to be used for rendering this node. Returns
     * null if the standard table foreground or selected color should be used.
     * 
     * @param rowViewModel the row view model
     * @return a {@linkplain Color}
     */
    @Override
    public Color getForeground(Object rowViewModel)
    {
        return null;
    }

    /**
     * Gets a description for this object suitable for use in a tool tip. 
     * 
     * @param rowViewModel the row view model
     * @return a {@linkplain String}
     */
    @Override
    public String getTooltipText(Object rowViewModel)
    {
        return null;
    }

    /**
     * Gets the icon to be used for rendering this node. Returns
     * null if the row corresponds to a {@linkplain ExchangeHistoryTimeStampRowViewModel}
     * 
     * @param rowViewModel the row view model
     * @return a {@linkplain Icon}
     */
    @Override
    public Icon getIcon(Object rowViewModel)
    {
        if(rowViewModel instanceof ExchangeHistoryTimeStampRowViewModel)
        {
            return ImageLoader.GetIcon();
        }
        
        return null;
    }
}
