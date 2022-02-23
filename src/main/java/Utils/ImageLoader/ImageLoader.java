/*
 * ImageLoader.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Gerene, Alex Vorobiev, Nathanael Smiechowski 
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

import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
     * The {@link RootFolder} where the images are
     */
    private final static String RootFolder = "Images/";
        
    /**
     * The {@link GetIcon} gets the icon with the provided {@link fileName} that is in Utils.ImageLoader.Images
     * 
     * @param {@link fileName} the file name of the requested image
     * @return a {@link ImageIcon}
     */
    public static ImageIcon GetIcon(String fileName)
    {
        URL imageUrl = ImageLoader.class.getResource(RootFolder+fileName);
        
        if (imageUrl != null) 
        {
            return new ImageIcon(imageUrl, "");
        }
        else 
        {
            Logger.error(String.format("Image not found with file name: %s", fileName));
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
}
