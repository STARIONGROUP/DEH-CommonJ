/*
 * HubBrowserHeader.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
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

import javax.swing.JPanel;

import ViewModels.Interfaces.IHubBrowserHeaderViewModel;
import ViewModels.Interfaces.IViewModel;
import Views.Interfaces.IView;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.SwingConstants;

/**
 * The {@linkplain HubBrowserHeader} is the hub session information display
 */
@SuppressWarnings("serial")
public class HubBrowserHeader extends JPanel implements IView<IHubBrowserHeaderViewModel>
{
    /**
     * This view attached {@linkplain IViewModel}
     */
    private IHubBrowserHeaderViewModel dataContext;
    
    /**
     * UI Elements declarations
     */
    private JLabel modelLabel;
    private JLabel dataSourceLabel;
    private JLabel iterationLabel;
    private JLabel personLabel;
    private JLabel domainOfExpertiseLabel;
    
    /**
     * Initializes a new {@linkplain HubBrowserHeader}
     */
    public HubBrowserHeader()
    {
        this.setBounds(0, 0, 500, 250);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        
        JLabel lblNewLabel = new JLabel("Model:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 0;
        add(lblNewLabel, gbc_lblNewLabel);
        
        this.modelLabel = new JLabel("");
        GridBagConstraints gbc_modelLabel = new GridBagConstraints();
        gbc_modelLabel.fill = GridBagConstraints.BOTH;
        gbc_modelLabel.insets = new Insets(0, 0, 5, 5);
        gbc_modelLabel.gridx = 1;
        gbc_modelLabel.gridy = 0;
        add(this.modelLabel, gbc_modelLabel);
        
        JLabel lblNewLabel_2 = new JLabel("Data-Source:");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 2;
        gbc_lblNewLabel_2.gridy = 0;
        add(lblNewLabel_2, gbc_lblNewLabel_2);
        
        this.dataSourceLabel = new JLabel("");
        GridBagConstraints gbc_dataSourceLabel = new GridBagConstraints();
        gbc_dataSourceLabel.fill = GridBagConstraints.BOTH;
        gbc_dataSourceLabel.insets = new Insets(0, 0, 5, 0);
        gbc_dataSourceLabel.gridx = 3;
        gbc_dataSourceLabel.gridy = 0;
        add(this.dataSourceLabel, gbc_dataSourceLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Iteration:");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 1;
        add(lblNewLabel_1, gbc_lblNewLabel_1);
        
        this.iterationLabel = new JLabel("");
        GridBagConstraints gbc_iterationLabel = new GridBagConstraints();
        gbc_iterationLabel.fill = GridBagConstraints.BOTH;
        gbc_iterationLabel.insets = new Insets(0, 0, 5, 5);
        gbc_iterationLabel.gridx = 1;
        gbc_iterationLabel.gridy = 1;
        add(this.iterationLabel, gbc_iterationLabel);
        
        JLabel lblNewLabel_3 = new JLabel("Person:");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 2;
        gbc_lblNewLabel_3.gridy = 1;
        add(lblNewLabel_3, gbc_lblNewLabel_3);
        
        this.personLabel = new JLabel("");
        GridBagConstraints gbc_personLabel = new GridBagConstraints();
        gbc_personLabel.fill = GridBagConstraints.BOTH;
        gbc_personLabel.insets = new Insets(0, 0, 5, 0);
        gbc_personLabel.gridx = 3;
        gbc_personLabel.gridy = 1;
        add(this.personLabel, gbc_personLabel);
        
        JLabel lblNewLabel_8 = new JLabel("Domain of Expertise:");
        lblNewLabel_8.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
        GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
        gbc_lblNewLabel_8.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_8.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel_8.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_8.gridx = 2;
        gbc_lblNewLabel_8.gridy = 2;
        add(lblNewLabel_8, gbc_lblNewLabel_8);
        
        this.domainOfExpertiseLabel = new JLabel("");
        GridBagConstraints gbc_domainOfExpertiseLabel = new GridBagConstraints();
        gbc_domainOfExpertiseLabel.fill = GridBagConstraints.BOTH;
        gbc_domainOfExpertiseLabel.gridx = 3;
        gbc_domainOfExpertiseLabel.gridy = 2;
        add(this.domainOfExpertiseLabel, gbc_domainOfExpertiseLabel);
    }

    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    public void SetDataContext(IViewModel viewModel)
    {
        this.dataContext = (IHubBrowserHeaderViewModel) viewModel;
        this.Bind();
    }
    
    /**
    * Gets the DataContext
    * 
    * @return An {@link IViewModel}
    */
    @Override
    public IHubBrowserHeaderViewModel GetDataContext()
    {
        return this.dataContext;
    }
    
    /**
     * Binds the <code>TViewModel viewModel</code> to the implementing view
     * 
     * @param <code>viewModel</code> the view model to bind
     */
    @Override
    public void Bind()
    {
        this.dataContext.GetEngineeringModelName().subscribe(x -> this.modelLabel.setText(x));
        this.dataContext.GetDataSource().subscribe(x -> this.dataSourceLabel.setText(x));
        this.dataContext.GetIterationNumber().subscribe(x -> this.iterationLabel.setText(x));
        this.dataContext.GetPersonName().subscribe(x -> this.personLabel.setText(x));
        this.dataContext.GetDomainOfExpertiseName().subscribe(x -> this.domainOfExpertiseLabel.setText(x));
    }
}
