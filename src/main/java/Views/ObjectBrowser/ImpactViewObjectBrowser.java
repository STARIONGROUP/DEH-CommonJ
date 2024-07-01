/*
 * ImpactViewObjectBrowser.java
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
package Views.ObjectBrowser;

import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;

/**
 * The {@linkplain ImpactViewObjectBrowser} is the specialized {@linkplain ObjectBrowser}
 */
@SuppressWarnings("serial")
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class ImpactViewObjectBrowser extends ObjectBrowser
{
    /**
     * Processes the currently selected elements
     */
    @Override
    protected void ProcessSelectedElements()
    {
        super.ProcessSelectedElements();
        
        SwingUtilities.invokeLater(() -> 
            objectBrowserTree.tableChanged(new TableModelEvent(objectBrowserTree.getOutlineModel())));
    }

    /**
     * Processes the currently selected element
     */
    @Override
    protected void ProcessSelectedElement()
    {
        super.ProcessSelectedElement();
        
        SwingUtilities.invokeLater(() -> 
            objectBrowserTree.tableChanged(new TableModelEvent(objectBrowserTree.getOutlineModel())));
    }    
}
