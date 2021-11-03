/*
 * HubBrowserPanel.java
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
package Views;

import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Font;

import Utils.ImageLoader.ImageLoader;
import Views.ObjectBrowser.ObjectBrowser;
import cdp4common.commondata.ClassKind;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;
import java.awt.Color;

/**
 * The {@link HubBrowserPanel} is the main panel view for the Hub controls like session controls and tree views.
 * This view is meant to be integrated into another container view specific to DST * 
 */
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
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[] {40, 80, 187, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 2.0, 0.0};
        setLayout(gridBagLayout);
        
        JPanel SessionControlContainer = new JPanel();
        GridBagConstraints gbc_SessionControlContainer = new GridBagConstraints();
        gbc_SessionControlContainer.anchor = GridBagConstraints.NORTH;
        gbc_SessionControlContainer.insets = new Insets(0, 0, 5, 0);
        gbc_SessionControlContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_SessionControlContainer.gridx = 0;
        gbc_SessionControlContainer.gridy = 0;
        add(SessionControlContainer, gbc_SessionControlContainer);
        SessionControlContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        sessionControlPanel = new SessionControlPanel();
        SessionControlContainer.add(sessionControlPanel);
        
        JPanel HubBrowserHeaderContainer = new JPanel();
        GridBagConstraints gbc_HubBrowserHeaderContainer = new GridBagConstraints();
        gbc_HubBrowserHeaderContainer.fill = GridBagConstraints.BOTH;
        gbc_HubBrowserHeaderContainer.insets = new Insets(0, 0, 5, 0);
        gbc_HubBrowserHeaderContainer.gridx = 0;
        gbc_HubBrowserHeaderContainer.gridy = 1;
        add(HubBrowserHeaderContainer, gbc_HubBrowserHeaderContainer);
        GridBagLayout gbl_HubBrowserHeaderContainer = new GridBagLayout();
        gbl_HubBrowserHeaderContainer.columnWidths = new int[]{189, 0};
        gbl_HubBrowserHeaderContainer.rowHeights = new int[]{14, 0};
        gbl_HubBrowserHeaderContainer.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_HubBrowserHeaderContainer.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        HubBrowserHeaderContainer.setLayout(gbl_HubBrowserHeaderContainer);
        
        GridBagConstraints hubBrowserHeaderConstraint = new GridBagConstraints();
        hubBrowserHeaderConstraint.insets = new Insets(0, 10, 0, 10);
        hubBrowserHeaderConstraint.fill = GridBagConstraints.BOTH;
        hubBrowserHeaderConstraint.anchor = GridBagConstraints.NORTHWEST;
        hubBrowserHeaderConstraint.gridx = 0;
        hubBrowserHeaderConstraint.gridy = 0;
        this.hubBrowserHeader = new HubBrowserHeader();
        HubBrowserHeaderContainer.add(this.hubBrowserHeader, hubBrowserHeaderConstraint);
        
        JTabbedPane HubBrowserTreeViewsContainer = new JTabbedPane(JTabbedPane.TOP);
        GridBagConstraints gbc_HubBrowserTreeViewsContainer = new GridBagConstraints();
        gbc_HubBrowserTreeViewsContainer.insets = new Insets(0, 0, 5, 0);
        gbc_HubBrowserTreeViewsContainer.fill = GridBagConstraints.BOTH;
        gbc_HubBrowserTreeViewsContainer.gridx = 0;
        gbc_HubBrowserTreeViewsContainer.gridy = 2;
        this.add(HubBrowserTreeViewsContainer, gbc_HubBrowserTreeViewsContainer);
        
        this.elementDefinitionBrowser = new ObjectBrowser();
        elementDefinitionBrowser.setBackground(Color.WHITE);
        HubBrowserTreeViewsContainer.addTab("Element Definitions", ImageLoader.GetIcon(ClassKind.Iteration), this.elementDefinitionBrowser, null);
        
        this.requirementBrowser = new ObjectBrowser();
        HubBrowserTreeViewsContainer.addTab("Requirements", ImageLoader.GetIcon(ClassKind.RequirementsSpecification), this.requirementBrowser, null);
    }
}
