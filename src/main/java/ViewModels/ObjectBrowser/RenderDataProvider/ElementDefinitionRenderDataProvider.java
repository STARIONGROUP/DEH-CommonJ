/*
 * ElementDefinitionRenderDataProvider.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Geren�, Alex Vorobiev, Nathanael Smiechowski 
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
import ViewModels.ObjectBrowser.Rows.ThingRowViewModel;
import cdp4common.commondata.ClassKind;

public class ElementDefinitionRenderDataProvider implements RenderDataProvider
{
    /**
     * The current class logger
     */
    private final Logger logger = LogManager.getLogger();
    
    @Override
    public String getDisplayName(Object rowViewModel)
    {
        if(rowViewModel instanceof ThingRowViewModel<?>)
        {
            ThingRowViewModel<?> row = (ThingRowViewModel<?>)rowViewModel;
            return row.GetName();
        }
        
        return "undefined";
    }

    @Override
    public boolean isHtmlDisplayName(Object o)
    {
        return false;
    }

    @Override
    public Color getBackground(Object o)
    {
        return Color.WHITE;
    }

    @Override
    public Color getForeground(Object o)
    {
        return null;
    }

    @Override
    public String getTooltipText(Object o)
    {
        return null;
    }

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
