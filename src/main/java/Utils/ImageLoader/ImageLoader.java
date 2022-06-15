/*
 * ImageLoader.java
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
package Utils.ImageLoader;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cdp4common.commondata.ClassKind;

/**
 * The {@link ImageLoader} provides helper methods for dealing with image resource
 */
public final class ImageLoader
{
    /**
     * The {@link Logger} log4J this class
     */
    private static final Logger Logger = LogManager.getLogger(); 
        
    /**
     * The {@linkplain Thing} folder name where the images are
     */
    public static final String ThingFolder = "Thing/";
    
    /**
     * The name of the dst icon that should be contained in the client adapter solution
     */
    public static final String DstIconFileName = "dst.png";
    
    /**
     * Initializes a new {@linkplain ImageLoader}
     * this constructor is explicitly specifying the static character of this {@linkplain Class} 
     * since static class don't exist out of the box in java. UNUSED
     */
    private ImageLoader() { }
    
    /**
     * The {@link GetIcon} gets the icon that represents the dst
     * 
     * @return a {@link ImageIcon}
     */
    public static ImageIcon GetDstIcon()
    {
        return GetIcon(DstIconFileName);
    }
        
    /**
     * The {@link GetIcon} gets the icon with the provided {@link fileName} that is in Utils.ImageLoader.Images
     * and the specified sub folder
     * 
     * @param subfolder the sub folder where the requested image resides
     * @param fileName the file name of the requested image
     * @return a {@link ImageIcon}
     */
    public static ImageIcon GetIcon(String subfolder, String fileName)
    {
        return GetIcon(subfolder + fileName);
    }

    /**
     * The {@link GetIcon} gets the icon with the provided fileName that is in Utils.ImageLoader.Images
     * 
     * @param imagePath the file name of the requested image
     * @return a {@link ImageIcon}
     */
    public static ImageIcon GetIcon(String imagePath)
    {
        URL ressource = ImageLoader.class.getClassLoader().getResource(imagePath);
        
        if (ressource != null) 
        {
            return new ImageIcon(ressource, "");
        }
        else 
        {
            Logger.error(String.format("Image not found with file name: %s", imagePath));
            return null;
        }
    }
        
    /**
     * The {@link GetIcon} gets the main icon in 32x32
     * 
     * @return a {@link ImageIcon}
     */
    public static ImageIcon GetIcon()
    {
        return GetIcon("icon32.png");
    }

    /**
     * Gets an {@linkplain Icon} that represents the provided {@linkplain ClassKind} 
     * 
     * @param classKind the {@linkplain ClassKind}
     * @return an {@linkplain Icon}
     */
    public static Icon GetIcon(ClassKind classKind)
    {
        String iconFileName;
        
        String parameterIcon = "parameter.png";
		switch(classKind)
        {
            case ElementBase:
                iconFileName = "elementdefinition.png";
                break;
            case ElementDefinition:
                iconFileName = "elementdefinition.png";
                break;
            case ElementUsage:
                iconFileName = "elementusage.png";
                break;
            case Parameter:
                iconFileName = parameterIcon;
                break;
            case ParameterBase:
                iconFileName = parameterIcon;
                break;
            case ParameterGroup:
                iconFileName = "parametergroup.png";
                break;
            case ParameterOrOverrideBase:
                iconFileName = parameterIcon;
                break;
            case ParameterOverride:
                iconFileName = "parameteroverride.png";
                break;
            case ParameterSubscription:
                iconFileName = "parametersubscription.png";
                break;
            case Iteration:
                iconFileName = "iteration.png";
                break;
            case Option:
                iconFileName = "option.png";
                break;
            case ActualFiniteState:
                iconFileName = "actualfinitestate.png";
                break;
            case Requirement:
                iconFileName = "requirement.png";
                break;
            case RequirementsGroup:
                iconFileName = "parametergroup.png";
                break;
            default:
                iconFileName = "iteration.png";
                break;
        }
        
        return GetIcon(ThingFolder, iconFileName);
    }
}
