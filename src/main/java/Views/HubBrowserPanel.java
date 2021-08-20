/*
 * HubBrowserPanel.java
 *
 * Copyright (c) 2015-2019 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-CommonJ
 *
 * The DEH-MDSYSML is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-MDSYSML is distributed in the hope that it will be useful,
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

import javax.swing.JTree;
import javax.swing.JLabel;

/**
 * The {@link HubBrowserPanel} is the main panel view for the Hub controls like session controls and tree views.
 * This view is meant to be integrated into another container view specific to DST * 
 */
@SuppressWarnings("serial")
public class HubBrowserPanel extends JPanel 
{
    /**
     * Backing field for {@link ConnectButton}
     */
    private JButton connectButton;
    
    /**
     * Gets the connect button
     */
    public JButton ConnectButton()
    {
        return this.connectButton;
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
        gridBagLayout.rowHeights = new int[] {40, 80, 187};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 2.0};
        setLayout(gridBagLayout);
        
        JPanel SessionControlContainer = new JPanel();
        GridBagConstraints gbc_SessionControlContainer = new GridBagConstraints();
        gbc_SessionControlContainer.anchor = GridBagConstraints.NORTH;
        gbc_SessionControlContainer.insets = new Insets(0, 0, 5, 0);
        gbc_SessionControlContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_SessionControlContainer.gridx = 0;
        gbc_SessionControlContainer.gridy = 0;
        SessionControlContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        add(SessionControlContainer, gbc_SessionControlContainer);        

        this.connectButton = new JButton("Connect");
        this.connectButton.setToolTipText("Connect to a Hub data source");
        this.connectButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        SessionControlContainer.add(this.connectButton);
        
        JPanel HubBrowserHeaderContainer = new JPanel();
        FlowLayout flowLayout = (FlowLayout) HubBrowserHeaderContainer.getLayout();
        GridBagConstraints gbc_HubBrowserHeaderContainer = new GridBagConstraints();
        gbc_HubBrowserHeaderContainer.insets = new Insets(0, 0, 5, 0);
        gbc_HubBrowserHeaderContainer.gridx = 0;
        gbc_HubBrowserHeaderContainer.gridy = 1;
        add(HubBrowserHeaderContainer, gbc_HubBrowserHeaderContainer);
        
        JLabel lblNewLabel = new JLabel("Header info");
        HubBrowserHeaderContainer.add(lblNewLabel);
        
        JTabbedPane HubBrowserTreeViewsContainer = new JTabbedPane(JTabbedPane.TOP);
        GridBagConstraints gbc_HubBrowserTreeViewsContainer = new GridBagConstraints();
        gbc_HubBrowserTreeViewsContainer.insets = new Insets(0, 0, 5, 0);
        gbc_HubBrowserTreeViewsContainer.fill = GridBagConstraints.BOTH;
        gbc_HubBrowserTreeViewsContainer.gridx = 0;
        gbc_HubBrowserTreeViewsContainer.gridy = 2;
        add(HubBrowserTreeViewsContainer, gbc_HubBrowserTreeViewsContainer);
        
        JTree tree = new JTree();
        HubBrowserTreeViewsContainer.addTab("Element Definitions tree", null, tree, null);
    }
}
