/*
 * HubBrowserPanel.java
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
package Views;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import Utils.ImageLoader.ImageLoader;
import ViewModels.Interfaces.IElementDefinitionBrowserViewModel;
import ViewModels.Interfaces.IRequirementBrowserViewModel;
import Views.ContextMenu.HubBrowserContextMenu;
import Views.ObjectBrowser.ObjectBrowser;
import cdp4common.commondata.ClassKind;

/**
 * The {@link HubBrowserPanel} is the main panel view for the Hub controls like session controls and tree views.
 * This view is meant to be integrated into another container view specific to DST * 
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class HubBrowserPanel extends JPanel 
{
    /**
     * The {@linkplain HubBrowserHeader}
     */
    private HubBrowserHeader hubBrowserHeader;
    
    /**
     * Gets the {@linkplain HubBrowserHeader}
     * 
     * @return the {@linkplain HubBrowserHeader}
     */
    public HubBrowserHeader getHubBrowserHeader()
    {
        return this.hubBrowserHeader;
    }    
    
    /**
     * The element definitions {@linkplain ObjectBrowser}
     */
    private ObjectBrowser elementDefinitionBrowser;
    
    /**
     * Gets the element definitions {@linkplain ObjectBrowser} 
     * 
     * @return the {@linkplain ObjectBrowser}
     */
    public ObjectBrowser GetElementDefinitionBrowser()
    {
        return this.elementDefinitionBrowser;
    }
    
    /**
     * The requirement specifications {@linkplain ObjectBrowser}
     */
    private ObjectBrowser requirementBrowser;
    
    /**
     * Gets the requirement specifications {@linkplain ObjectBrowser} 
     * 
     * @return the {@linkplain ObjectBrowser}
     */
    public ObjectBrowser GetRequirementBrowser()
    {
        return this.requirementBrowser;
    }
    
    /**
     * The {@linkplain SessionControlPanel} view
     */
    private SessionControlPanel sessionControlPanel;
        
    /**
     * Gets the {@linkplain SessionControlPanel} view
     * 
     * @return a {@linkplain SessionControlPanel}
     */
    public SessionControlPanel GetSessionControlPanel()
    {
        return this.sessionControlPanel;
    }
    
    /**
     * Initializes a new {@link HubBrowserPanel}
     */
    public HubBrowserPanel()
    {        
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        GridBagLayout gridLayout = new GridBagLayout();
        gridLayout.columnWidths = new int[]{0, 0};
        gridLayout.rowHeights = new int[] {40, 80, 187, 0};
        gridLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridLayout.rowWeights = new double[]{0.0, 0.0, 2.0, 0.0};
        setLayout(gridLayout);
        
        JPanel sessionControlContainer = new JPanel();
        GridBagConstraints gbcSessionControlContainer = new GridBagConstraints();
        gbcSessionControlContainer.anchor = GridBagConstraints.NORTH;
        gbcSessionControlContainer.insets = new Insets(0, 0, 5, 0);
        gbcSessionControlContainer.fill = GridBagConstraints.HORIZONTAL;
        gbcSessionControlContainer.gridx = 0;
        gbcSessionControlContainer.gridy = 0;
        add(sessionControlContainer, gbcSessionControlContainer);
        sessionControlContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        sessionControlPanel = new SessionControlPanel();
        sessionControlContainer.add(sessionControlPanel);
        
        JPanel hubBrowserHeaderContainer = new JPanel();
        GridBagConstraints gbcHubBrowserHeaderContainer = new GridBagConstraints();
        gbcHubBrowserHeaderContainer.fill = GridBagConstraints.BOTH;
        gbcHubBrowserHeaderContainer.insets = new Insets(0, 0, 5, 0);
        gbcHubBrowserHeaderContainer.gridx = 0;
        gbcHubBrowserHeaderContainer.gridy = 1;
        add(hubBrowserHeaderContainer, gbcHubBrowserHeaderContainer);
        GridBagLayout gblHubBrowserHeaderContainer = new GridBagLayout();
        gblHubBrowserHeaderContainer.columnWidths = new int[]{189, 0};
        gblHubBrowserHeaderContainer.rowHeights = new int[]{14, 0};
        gblHubBrowserHeaderContainer.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblHubBrowserHeaderContainer.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        hubBrowserHeaderContainer.setLayout(gblHubBrowserHeaderContainer);
        
        GridBagConstraints hubBrowserHeaderConstraint = new GridBagConstraints();
        hubBrowserHeaderConstraint.insets = new Insets(0, 10, 0, 10);
        hubBrowserHeaderConstraint.fill = GridBagConstraints.BOTH;
        hubBrowserHeaderConstraint.anchor = GridBagConstraints.NORTHWEST;
        hubBrowserHeaderConstraint.gridx = 0;
        hubBrowserHeaderConstraint.gridy = 0;
        this.hubBrowserHeader = new HubBrowserHeader();
        hubBrowserHeaderContainer.add(this.hubBrowserHeader, hubBrowserHeaderConstraint);
        
        JTabbedPane hubBrowserTreeViewsContainer = new JTabbedPane(SwingConstants.TOP);
        GridBagConstraints gbcHubBrowserTreeViewsContainer = new GridBagConstraints();
        gbcHubBrowserTreeViewsContainer.insets = new Insets(0, 0, 5, 0);
        gbcHubBrowserTreeViewsContainer.fill = GridBagConstraints.BOTH;
        gbcHubBrowserTreeViewsContainer.gridx = 0;
        gbcHubBrowserTreeViewsContainer.gridy = 2;
        this.add(hubBrowserTreeViewsContainer, gbcHubBrowserTreeViewsContainer);
        
        this.elementDefinitionBrowser = new ObjectBrowser();
        this.elementDefinitionBrowser.setBackground(Color.WHITE);
        this.elementDefinitionBrowser.SetContextMenu(new HubBrowserContextMenu(IElementDefinitionBrowserViewModel.class));
        hubBrowserTreeViewsContainer.addTab("Element Definitions", ImageLoader.GetIcon(ClassKind.Iteration), this.elementDefinitionBrowser, null);
        
        this.requirementBrowser = new ObjectBrowser();
        this.requirementBrowser.SetContextMenu(new HubBrowserContextMenu(IRequirementBrowserViewModel.class));
        hubBrowserTreeViewsContainer.addTab("Requirements", ImageLoader.GetIcon(ClassKind.RequirementsSpecification), this.requirementBrowser, null);
    }
}
