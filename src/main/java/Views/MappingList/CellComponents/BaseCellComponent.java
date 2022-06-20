/*
 * BaseCellComponent.java
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
package Views.MappingList.CellComponents;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Utils.ImageLoader.ImageLoader;
import ViewModels.MappingListView.Rows.MappingListViewContainerBaseRowViewModel;
import cdp4common.commondata.ClassKind;

/**
 * The {@linkplain BaseCellComponent} is the base view for the {@linkplain ElementCellComponent} and the {@linkplain RequirementCellComponent}
 */
@SuppressWarnings("serial")
@Annotations.ExludeFromCodeCoverageGeneratedReport
public abstract class BaseCellComponent extends JPanel
{
    /**
     * View components declarations
     */
    private JLabel rootLabel;
    protected JPanel valueContainer;
    
    /**
     * Initializes a new {@linkplain BaseCellComponent}
     */
    protected BaseCellComponent()
    {        
        this.Initialize();
    }
    
    /**
     * Initializes this view components
     */
    private void Initialize()
    {
        this.setBackground(Color.WHITE);
        this.setOpaque(true);
        this.setPreferredSize(new Dimension(300, 100));
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {30, 0};
        gridBagLayout.rowHeights = new int[] {30, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0};
        this.setLayout(gridBagLayout);
        
        this.rootLabel = new JLabel("");
        GridBagConstraints gbcRootLabel = new GridBagConstraints();
        gbcRootLabel.anchor = GridBagConstraints.WEST;
        gbcRootLabel.gridwidth = 2;
        gbcRootLabel.insets = new Insets(0, 0, 5, 0);
        gbcRootLabel.gridx = 0;
        gbcRootLabel.gridy = 0;
        this.add(this.rootLabel, gbcRootLabel);

        this.valueContainer = new JPanel();
        this.valueContainer.setBackground(Color.WHITE);
        this.valueContainer.setLayout(new GridLayout(0, 1, 0, 5));
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        GridBagConstraints gbcList = new GridBagConstraints();
        gbcList.anchor = GridBagConstraints.NORTHEAST;
        gbcList.insets = new Insets(0, 0, 5, 0);
        gbcList.fill = GridBagConstraints.BOTH;
        gbcList.gridx = 1;
        gbcList.gridy = 1;
                
        JPanel flow = new JPanel();
        flow.setBackground(Color.WHITE);
        FlowLayout flFlow = new FlowLayout();
        flFlow.setAlignment(FlowLayout.LEFT);
        flow.setLayout(flFlow);
        flow.add(this.valueContainer);
        scrollPane.add(flow);
        scrollPane.getViewport().setBackground(Color.WHITE);
        scrollPane.setViewportView(flow);
        
        this.add(scrollPane, gbcList);
    }
    
    /**
     * Updates the components values
     * 
     * @param rowViewModel the {@linkplain MappingListViewContainerBaseRowViewModel}
     */
    public abstract void Update(MappingListViewContainerBaseRowViewModel<?> rowViewModel);
    
    /**
     * Updates the name and icon
     * 
     * @param name the {@linkplain String} name
     * @param classKind the {@linkplain ClassKind}
     */
    protected void Update(String name, ClassKind classKind)
    {
        this.rootLabel.setText(name);
        this.rootLabel.setIcon(ImageLoader.GetIcon(classKind));
    }
}
