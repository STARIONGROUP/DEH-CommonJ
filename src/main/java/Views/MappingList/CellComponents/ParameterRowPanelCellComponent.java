/*
 * ParameterRowPanelCellComponent.java
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
package Views.MappingList.CellComponents;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

import Utils.ImageLoader.ImageLoader;
import cdp4common.commondata.ClassKind;

import java.awt.Color;

/**
 * The {@linkplain ParameterRowPanelCellComponent} panel displays the name and value of one parameter in the MappingListView
 */
@SuppressWarnings("serial")
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class ParameterRowPanelCellComponent extends JPanel
{
    /**
     * View components declaration
     */
    private JLabel parameterName;
    private JLabel value;

    /**
     * Initializes a new {@linkplain ParameterRowPanelCellComponent}
     *      * 
     * @param name the {@linkplain String} name of the represented element
     * @param value the {@linkplain String} value of the represented element
     */
    public ParameterRowPanelCellComponent(String name, String value)
    {
        this.Initialize();
        this.parameterName.setText(name);
        this.parameterName.setIcon(ImageLoader.GetIcon(ClassKind.Parameter));
        this.value.setText(value);
    }

    /**
     * Initialize the contents of this panel
     */
    private void Initialize()
    {
        this.setBackground(Color.WHITE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {0, 0};
        gridBagLayout.rowHeights = new int[] {0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0};
        gridBagLayout.rowWeights = new double[]{1.0};
        this.setLayout(gridBagLayout);
        
        this.parameterName = new JLabel("");
        this.parameterName.setHorizontalAlignment(SwingConstants.RIGHT);
        GridBagConstraints gbcParameterName = new GridBagConstraints();
        gbcParameterName.anchor = GridBagConstraints.WEST;
        gbcParameterName.fill = GridBagConstraints.VERTICAL;
        gbcParameterName.insets = new Insets(0, 0, 0, 5);
        gbcParameterName.gridx = 0;
        gbcParameterName.gridy = 0;
        this.add(this.parameterName, gbcParameterName);
        
        this.value = new JLabel("");
        GridBagConstraints gbcValue = new GridBagConstraints();
        gbcValue.fill = GridBagConstraints.BOTH;
        gbcValue.gridx = 1;
        gbcValue.gridy = 0;
        this.add(this.value, gbcValue);
    }
}
