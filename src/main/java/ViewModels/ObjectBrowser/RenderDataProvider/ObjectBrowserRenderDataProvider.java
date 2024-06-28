/*
 * ElementDefinitionRenderDataProvider.java
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
package ViewModels.ObjectBrowser.RenderDataProvider;

import java.awt.Color;

import javax.swing.Icon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.swing.outline.RenderDataProvider;

import Utils.ImageLoader.ImageLoader;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters.ActualFiniteStateRowViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters.OptionRowViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters.ParameterValueRowViewModel;
import ViewModels.ObjectBrowser.RequirementTree.Rows.RequirementBaseTreeElementViewModel;
import ViewModels.ObjectBrowser.Rows.ThingRowViewModel;
import cdp4common.commondata.ClassKind;

/**
 * The {@linkplain ObjectBrowserRenderDataProvider} is the {@linkplain RenderDataProvider} for the element definition tree
 * It is used mainly for styling the tree
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class ObjectBrowserRenderDataProvider implements RenderDataProvider
{
    /**
     * The current class logger
     */
    private final Logger logger = LogManager.getLogger();
    
    /**
     * Gets the specified row view model node name
     * 
     * @param rowViewModel the row view model to get the name from
     * @return a {@linkplain String}
     */
    @Override
    public String getDisplayName(Object rowViewModel)
    {
        if(rowViewModel instanceof ThingRowViewModel<?>)
        {
            if(rowViewModel instanceof RequirementBaseTreeElementViewModel)
            {
                RequirementBaseTreeElementViewModel<?> row = (RequirementBaseTreeElementViewModel<?>)rowViewModel;
                return row.GetShortName();
            }
            
            ThingRowViewModel<?> row = (ThingRowViewModel<?>)rowViewModel;
            return row.GetName();
        }
        
        return "undefined";
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
        return false;
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
        if (rowViewModel instanceof ThingRowViewModel<?>)
        {
            if(((ThingRowViewModel<?>)rowViewModel).GetIsSelected())
            {
                return new Color(104, 143, 184);
            }
            
            if(((ThingRowViewModel<?>)rowViewModel).GetIsHighlighted())
            {
                return Color.YELLOW;
            }            
        }
        
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
     * Gets the background color to be used for rendering this node. Returns
     * null if the standard table background or selected color should be used.
     * 
     * @param rowViewModel the row view model
     * @return a {@linkplain Color}
     */
    @Override
    public Icon getIcon(Object rowViewModel)
    {
        if(rowViewModel instanceof ThingRowViewModel<?>)
        {
            try
            {
                if(rowViewModel instanceof OptionRowViewModel)
                {
                    if(((OptionRowViewModel<?>)rowViewModel).GetContainedRows().isEmpty())
                    {
                        return ImageLoader.GetIcon(ImageLoader.ThingFolder, "optionparameter.png");
                    }
                    
                    return ImageLoader.GetIcon(ClassKind.Option);
                }
                else if(rowViewModel instanceof ActualFiniteStateRowViewModel)
                {
                    if(((ActualFiniteStateRowViewModel<?>)rowViewModel).GetContainedRows().isEmpty())
                    {
                        return ImageLoader.GetIcon(ImageLoader.ThingFolder, "stateparameter.png");
                    }
                    
                    return ImageLoader.GetIcon(ClassKind.ActualFiniteState);
                }
                else if(rowViewModel instanceof ParameterValueRowViewModel)
                {
                    return ImageLoader.GetIcon(ImageLoader.ThingFolder, "value.png");
                }
                
                return ImageLoader.GetIcon(((ThingRowViewModel<?>)rowViewModel).GetThing().getClassKind());
            } 
            catch (Exception e)
            {
                this.logger.error(String.format("getIcon \n\r ((ThingRowViewModel<?>)rowViewModel) != null?  %s "
                        + "\n\r ((ThingRowViewModel<?>)rowViewModel).GetThing() != null ?  %s \n\r e: %s", 
                        ((ThingRowViewModel<?>)rowViewModel) != null, ((ThingRowViewModel<?>)rowViewModel).GetThing() != null,
                        e));    
            }
        }
        
        return ImageLoader.GetIcon();
    }
}
