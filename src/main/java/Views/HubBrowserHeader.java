/*
 * HubBrowserHeader.java
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

import javax.swing.*;

import ViewModels.Interfaces.IHubBrowserHeaderViewModel;
import ViewModels.Interfaces.IViewModel;
import Views.Interfaces.IView;

import java.awt.*;

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
    private JComboBox<String> optionsComboBox;
    private JLabel labelDataSource;
    private JLabel labelIteration;
    private JLabel labelModel;
    private JLabel labelPerson;
    private JLabel labelOptions;
    private JLabel labelDomainOfExpertise;

    /**
     * Initializes a new {@linkplain HubBrowserHeader}
     */
    public HubBrowserHeader()
    {
        this.Initialize();
    }

    /**
     * Initializes this view components
     */
    private void Initialize()
    {
        this.setBounds(0, 0, 500, 250);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
        this.setLayout(gridBagLayout);

        this.labelModel = new JLabel("Model:");
        this.labelModel.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblModel = new GridBagConstraints();
        gbcLblModel.fill = GridBagConstraints.VERTICAL;
        gbcLblModel.anchor = GridBagConstraints.WEST;
        gbcLblModel.insets = new Insets(5, 0, 5, 5);
        gbcLblModel.gridx = 0;
        gbcLblModel.gridy = 0;
        this.add(this.labelModel, gbcLblModel);

        this.modelLabel = new JLabel("");
        GridBagConstraints gbcModelLabel = new GridBagConstraints();
        gbcModelLabel.fill = GridBagConstraints.BOTH;
        gbcModelLabel.insets = new Insets(0, 0, 5, 5);
        gbcModelLabel.gridx = 1;
        gbcModelLabel.gridy = 0;
        this.add(this.modelLabel, gbcModelLabel);

        this.labelDataSource = new JLabel("Data-Source:");
        labelDataSource.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblDataSource = new GridBagConstraints();
        gbcLblDataSource.fill = GridBagConstraints.VERTICAL;
        gbcLblDataSource.anchor = GridBagConstraints.WEST;
        gbcLblDataSource.insets = new Insets(0, 0, 5, 5);
        gbcLblDataSource.gridx = 2;
        gbcLblDataSource.gridy = 0;
        this.add(labelDataSource, gbcLblDataSource);

        this.dataSourceLabel = new JLabel("");
        GridBagConstraints gbcDataSourceLabel = new GridBagConstraints();
        gbcDataSourceLabel.fill = GridBagConstraints.BOTH;
        gbcDataSourceLabel.insets = new Insets(0, 0, 5, 0);
        gbcDataSourceLabel.gridx = 3;
        gbcDataSourceLabel.gridy = 0;
        this.add(this.dataSourceLabel, gbcDataSourceLabel);

        this.labelIteration = new JLabel("Iteration:");
        this.labelIteration.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblIteration = new GridBagConstraints();
        gbcLblIteration.anchor = GridBagConstraints.WEST;
        gbcLblIteration.insets = new Insets(0, 0, 5, 5);
        gbcLblIteration.fill = GridBagConstraints.VERTICAL;
        gbcLblIteration.gridx = 0;
        gbcLblIteration.gridy = 1;
        this.add(this.labelIteration, gbcLblIteration);

        this.iterationLabel = new JLabel("");
        GridBagConstraints gbcIterationLabel = new GridBagConstraints();
        gbcIterationLabel.fill = GridBagConstraints.BOTH;
        gbcIterationLabel.insets = new Insets(0, 0, 5, 5);
        gbcIterationLabel.gridx = 1;
        gbcIterationLabel.gridy = 1;
        this.add(this.iterationLabel, gbcIterationLabel);

        this.labelPerson = new JLabel("Person:");
        this.labelPerson.setHorizontalAlignment(SwingConstants.RIGHT);
        this.labelPerson.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblPerson = new GridBagConstraints();
        gbcLblPerson.fill = GridBagConstraints.VERTICAL;
        gbcLblPerson.anchor = GridBagConstraints.WEST;
        gbcLblPerson.insets = new Insets(0, 0, 5, 5);
        gbcLblPerson.gridx = 2;
        gbcLblPerson.gridy = 1;
        this.add(this.labelPerson, gbcLblPerson);

        this.personLabel = new JLabel("");
        GridBagConstraints gbcPersonLabel = new GridBagConstraints();
        gbcPersonLabel.fill = GridBagConstraints.BOTH;
        gbcPersonLabel.insets = new Insets(0, 0, 5, 0);
        gbcPersonLabel.gridx = 3;
        gbcPersonLabel.gridy = 1;
        this.add(this.personLabel, gbcPersonLabel);

        this.labelOptions = new JLabel("Option:");
        this.labelOptions.setHorizontalAlignment(SwingConstants.RIGHT);
        this.labelOptions.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gridBagConstraintsLabelOptions = new GridBagConstraints();
        gridBagConstraintsLabelOptions.fill = GridBagConstraints.VERTICAL;
        gridBagConstraintsLabelOptions.anchor = GridBagConstraints.WEST;
        gridBagConstraintsLabelOptions.insets = new Insets(0, 0, 5, 5);
        gridBagConstraintsLabelOptions.gridx = 0;
        gridBagConstraintsLabelOptions.gridy = 2;
        this.add(this.labelOptions, gridBagConstraintsLabelOptions);

        this.optionsComboBox = new JComboBox<>();
        this.optionsComboBox.setMinimumSize(new Dimension(150, 50));
        GridBagConstraints gridBagConstraintsOptions = new GridBagConstraints();
        gridBagConstraintsOptions.fill = GridBagConstraints.WEST;
        gridBagConstraintsLabelOptions.insets = new Insets(0, 0, 5, 10);
        gridBagConstraintsLabelOptions.anchor = GridBagConstraints.WEST;
        gridBagConstraintsOptions.gridx = 1;
        gridBagConstraintsOptions.gridy = 2;
        this.add(this.optionsComboBox, gridBagConstraintsOptions);

        this.labelDomainOfExpertise = new JLabel("Domain of Expertise:");
        this.labelDomainOfExpertise.setHorizontalAlignment(SwingConstants.RIGHT);
        this.labelDomainOfExpertise.setFont(new Font(FONTNAME, Font.BOLD, 11));
        GridBagConstraints gbcLblDomainOfExpertise = new GridBagConstraints();
        gbcLblDomainOfExpertise.fill = GridBagConstraints.VERTICAL;
        gbcLblDomainOfExpertise.anchor = GridBagConstraints.WEST;
        gbcLblDomainOfExpertise.insets = new Insets(0, 0, 0, 5);
        gbcLblDomainOfExpertise.gridx = 2;
        gbcLblDomainOfExpertise.gridy = 2;
        this.add(this.labelDomainOfExpertise, gbcLblDomainOfExpertise);

        this.domainOfExpertiseLabel = new JLabel("");
        GridBagConstraints gbcDomainOfExpertiseLabel = new GridBagConstraints();
        gbcDomainOfExpertiseLabel.fill = GridBagConstraints.BOTH;
        gbcDomainOfExpertiseLabel.gridx = 3;
        gbcDomainOfExpertiseLabel.gridy = 2;
        this.add(this.domainOfExpertiseLabel, gbcDomainOfExpertiseLabel);
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
        this.optionsComboBox.removeAllItems();
        this.optionsComboBox.setVisible(false);

        this.modelLabel.setText(this.dataContext.GetEngineeringModelName().Value());
        this.dataSourceLabel.setText(this.dataContext.GetDataSource().Value());
        this.iterationLabel.setText(this.dataContext.GetIterationNumber().Value());
        this.personLabel.setText(this.dataContext.GetPersonName().Value());
        this.domainOfExpertiseLabel.setText(this.dataContext.GetDomainOfExpertiseName().Value());
        this.dataContext.GetAvailableOptions().forEach(x -> this.optionsComboBox.addItem(x));
        this.dataContext.GetSelectedOption().subscribe(x -> this.optionsComboBox.setSelectedItem(x));
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
     */
    @Override
    public void Bind()
    {
        this.dataContext.GetEngineeringModelName().Observable().subscribe(x -> this.modelLabel.setText(x));
        this.dataContext.GetDataSource().Observable().subscribe(x -> this.dataSourceLabel.setText(x));
        this.dataContext.GetIterationNumber().Observable().subscribe(x -> this.iterationLabel.setText(x));
        this.dataContext.GetPersonName().Observable().subscribe(x -> this.personLabel.setText(x));
        this.dataContext.GetDomainOfExpertiseName().Observable().subscribe(x -> this.domainOfExpertiseLabel.setText(x));

        this.dataContext.GetAvailableOptions().IsEmptyObservable().subscribe(x ->
        {
            this.optionsComboBox.removeAllItems();
            this.optionsComboBox.setVisible(false);
        });

        this.dataContext.GetAvailableOptions().ItemsAdded().subscribe(x ->
        {
            x.forEach(o -> this.optionsComboBox.addItem(o));
            this.optionsComboBox.setVisible(true);
        });

        this.optionsComboBox.addActionListener(x -> this.dataContext.SetSelectedOption(this.optionsComboBox.getSelectedItem()));
    }
}
