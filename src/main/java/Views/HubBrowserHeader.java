/*
 * HubBrowserHeader.java
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
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class HubBrowserHeader extends JPanel implements IView<IHubBrowserHeaderViewModel>
{
	/**
	 * The name of the font
	 */
    private static final String FONTNAME = "Tahoma";

	/**
     * This view attached {@linkplain IViewModel}
     */
    private transient IHubBrowserHeaderViewModel dataContext;
    
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
        
        JLabel lblModel = new JLabel("Model:");
        lblModel.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblModel = new GridBagConstraints();
        gbcLblModel.fill = GridBagConstraints.VERTICAL;
        gbcLblModel.anchor = GridBagConstraints.WEST;
        gbcLblModel.insets = new Insets(5, 0, 5, 5);
        gbcLblModel.gridx = 0;
        gbcLblModel.gridy = 0;
        add(lblModel, gbcLblModel);
        
        this.modelLabel = new JLabel("");
        GridBagConstraints gbcModelLabel = new GridBagConstraints();
        gbcModelLabel.fill = GridBagConstraints.BOTH;
        gbcModelLabel.insets = new Insets(0, 0, 5, 5);
        gbcModelLabel.gridx = 1;
        gbcModelLabel.gridy = 0;
        add(this.modelLabel, gbcModelLabel);
        
        JLabel lblDataSource = new JLabel("Data-Source:");
        lblDataSource.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblDataSource = new GridBagConstraints();
        gbcLblDataSource.fill = GridBagConstraints.VERTICAL;
        gbcLblDataSource.anchor = GridBagConstraints.WEST;
        gbcLblDataSource.insets = new Insets(0, 0, 5, 5);
        gbcLblDataSource.gridx = 2;
        gbcLblDataSource.gridy = 0;
        add(lblDataSource, gbcLblDataSource);
        
        this.dataSourceLabel = new JLabel("");
        GridBagConstraints gbcDataSourceLabel = new GridBagConstraints();
        gbcDataSourceLabel.fill = GridBagConstraints.BOTH;
        gbcDataSourceLabel.insets = new Insets(0, 0, 5, 0);
        gbcDataSourceLabel.gridx = 3;
        gbcDataSourceLabel.gridy = 0;
        add(this.dataSourceLabel, gbcDataSourceLabel);
        
        JLabel lblIteration = new JLabel("Iteration:");
        lblIteration.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblIteration = new GridBagConstraints();
        gbcLblIteration.anchor = GridBagConstraints.WEST;
        gbcLblIteration.insets = new Insets(0, 0, 5, 5);
        gbcLblIteration.fill = GridBagConstraints.VERTICAL;
        gbcLblIteration.gridx = 0;
        gbcLblIteration.gridy = 1;
        add(lblIteration, gbcLblIteration);
        
        this.iterationLabel = new JLabel("");
        GridBagConstraints gbcIterationLabel = new GridBagConstraints();
        gbcIterationLabel.fill = GridBagConstraints.BOTH;
        gbcIterationLabel.insets = new Insets(0, 0, 5, 5);
        gbcIterationLabel.gridx = 1;
        gbcIterationLabel.gridy = 1;
        add(this.iterationLabel, gbcIterationLabel);
        
        JLabel lblPerson = new JLabel("Person:");
        lblPerson.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPerson.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblPerson = new GridBagConstraints();
        gbcLblPerson.fill = GridBagConstraints.VERTICAL;
        gbcLblPerson.anchor = GridBagConstraints.WEST;
        gbcLblPerson.insets = new Insets(0, 0, 5, 5);
        gbcLblPerson.gridx = 2;
        gbcLblPerson.gridy = 1;
        add(lblPerson, gbcLblPerson);
        
        this.personLabel = new JLabel("");
        GridBagConstraints gbcPersonLabel = new GridBagConstraints();
        gbcPersonLabel.fill = GridBagConstraints.BOTH;
        gbcPersonLabel.insets = new Insets(0, 0, 5, 0);
        gbcPersonLabel.gridx = 3;
        gbcPersonLabel.gridy = 1;
        add(this.personLabel, gbcPersonLabel);
        
        JLabel lblDomainOfExpertise = new JLabel("Domain of Expertise:");
        lblDomainOfExpertise.setHorizontalAlignment(SwingConstants.RIGHT);
        lblDomainOfExpertise.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblDomainOfExpertise = new GridBagConstraints();
        gbcLblDomainOfExpertise.fill = GridBagConstraints.VERTICAL;
        gbcLblDomainOfExpertise.anchor = GridBagConstraints.WEST;
        gbcLblDomainOfExpertise.insets = new Insets(0, 0, 0, 5);
        gbcLblDomainOfExpertise.gridx = 2;
        gbcLblDomainOfExpertise.gridy = 2;
        add(lblDomainOfExpertise, gbcLblDomainOfExpertise);
        
        this.domainOfExpertiseLabel = new JLabel("");
        GridBagConstraints gbcDomainOfExpertiseLabel = new GridBagConstraints();
        gbcDomainOfExpertiseLabel.fill = GridBagConstraints.BOTH;
        gbcDomainOfExpertiseLabel.gridx = 3;
        gbcDomainOfExpertiseLabel.gridy = 2;
        add(this.domainOfExpertiseLabel, gbcDomainOfExpertiseLabel);
    }

    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    public void SetDataContext(IHubBrowserHeaderViewModel viewModel)
    {
        this.dataContext = viewModel;
        this.Bind();
        this.UpdateProperties();
    }
    
    /**
     * Update this view properties
     */
    private void UpdateProperties()
    {
        this.modelLabel.setText(this.dataContext.GetEngineeringModelName().Value());
        this.dataSourceLabel.setText(this.dataContext.GetDataSource().Value());
        this.iterationLabel.setText(this.dataContext.GetIterationNumber().Value());
        this.personLabel.setText(this.dataContext.GetPersonName().Value());
        this.domainOfExpertiseLabel.setText(this.dataContext.GetDomainOfExpertiseName().Value());
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
        this.dataContext.GetEngineeringModelName().Observable().subscribe(x -> this.modelLabel.setText(x));
        this.dataContext.GetDataSource().Observable().subscribe(x -> this.dataSourceLabel.setText(x));
        this.dataContext.GetIterationNumber().Observable().subscribe(x -> this.iterationLabel.setText(x));
        this.dataContext.GetPersonName().Observable().subscribe(x -> this.personLabel.setText(x));
        this.dataContext.GetDomainOfExpertiseName().Observable().subscribe(x -> this.domainOfExpertiseLabel.setText(x));
    }
}
