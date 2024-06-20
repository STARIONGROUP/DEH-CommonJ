/*
 * ImpactViewPanel.java
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

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.swing.*;

import Utils.Ref;
import cdp4common.engineeringmodeldata.ExternalIdentifierMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Enumerations.MappingDirection;
import Views.ContextMenu.ContextMenu;
import Views.ContextMenu.ImpactViewContextMenu;
import Views.ObjectBrowser.ImpactViewObjectBrowser;
import Views.ObjectBrowser.ObjectBrowser;
import Views.ObjectBrowser.ObjectBrowserBase;
import cdp4common.commondata.ClassKind;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.RequirementsSpecification;
import io.reactivex.Observable;
import Utils.ImageLoader.ImageLoader;
import ViewModels.Interfaces.IImpactViewContextMenuViewModel;

import Utils.Tasks.*;

/**
 * The {@linkplain ImpactViewPanel} is the view that displays the impact views
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class ImpactViewPanel extends JPanel
{
	/**
	 * The name of the font
	 */
    private static final String FONTNAME = "Tahoma";

	/**
     * The current class {@linkplain Logger}
     */
    private final transient Logger logger = LogManager.getLogger();

    /**
     * The base text for the transfer button
     */
    private static final String TransferButtonBaseText = "Transfer %s";
        
    /**
     * Backing field for {@link #switchMappingDirectionButton}
     */
    private JButton switchMappingDirectionButton;
        
    /**
     * The element definitions {@linkplain ObjectBrowser}
     */
    private ObjectBrowser elementDefinitionBrowser;

    /**
     * The loaded {@linkplain ImageIcon} arrow
     */
    private ImageIcon arrowImageIcon = ImageLoader.GetIcon("arrow32.png");

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
     * The {@linkplain ContextMenu} that is to be used on the {@linkplain ElementDefinition} impact tree
     */
    private ImpactViewContextMenu elementDefinitionContextMenu;

    /**
     * The {@linkplain ContextMenu} that is to be used on the {@linkplain RequirementsSpecification} impact tree
     */
    private ImpactViewContextMenu requirementsSpecificationContextMenu;

    /**
     *  Timer for animation
     */
    private Timer animationTimer;

    /**
     * Animation duration in milliseconds
     */
    private static final int ANIMATION_DURATION = 250;

    /**
     * Current rotation angle
     */
    private int currentRotation = 0;

    /**
     * View components declaration
     */
    private JLabel arrowLeft;
    private JLabel arrowRight;
    private JTabbedPane impactViewsTabbedPane;
    private JComboBox<String> mappingConfigurationComboBox;
    private JButton saveMappingConfigurationButton;
    private JProgressBar progressBar;
    private JButton cancelButton;
    private JButton transferButton;
    private JPanel dstImpactViewPanel;
        
    /**
     * Initializes a new {@link ImpactViewPanel}
     */
    public ImpactViewPanel()
    {
        this.elementDefinitionContextMenu = new ImpactViewContextMenu(ClassKind.ElementDefinition);
        this.requirementsSpecificationContextMenu = new ImpactViewContextMenu(ClassKind.Requirement);
        this.Initialize();
        this.SetTransferIsInProgress(false);
        this.UpdateTransferButtonNumberOfItems(0);
        this.SetLoadMappingControlsIsEnable(false);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void Initialize()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[] {40, 0, 187, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
        setLayout(gridBagLayout);
        
        JPanel transferDirection = new JPanel();
        GridBagConstraints gbcTransferDirection = new GridBagConstraints();
        gbcTransferDirection.anchor = GridBagConstraints.NORTH;
        gbcTransferDirection.insets = new Insets(10, 10, 5, 10);
        gbcTransferDirection.fill = GridBagConstraints.HORIZONTAL;
        gbcTransferDirection.gridx = 0;
        gbcTransferDirection.gridy = 0;
        this.add(transferDirection, gbcTransferDirection);
        GridBagLayout gblTransferDirection = new GridBagLayout();
        gblTransferDirection.columnWidths = new int[] {0, 1, 1, 1, 0};
        gblTransferDirection.rowHeights = new int[] {0};
        gblTransferDirection.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
        gblTransferDirection.rowWeights = new double[]{0.0};
        transferDirection.setLayout(gblTransferDirection);
        
        JLabel lblNewLabel = new JLabel(ImageLoader.GetIcon("dst32.png"));
        GridBagConstraints gbcLblNewLabel = new GridBagConstraints();
        gbcLblNewLabel.insets = new Insets(0, 0, 0, 5);
        gbcLblNewLabel.gridx = 0;
        gbcLblNewLabel.gridy = 0;
        transferDirection.add(lblNewLabel, gbcLblNewLabel);

        this.arrowLeft = new JLabel(this.arrowImageIcon);
        GridBagConstraints gbcArrowLeft = new GridBagConstraints();
        gbcArrowLeft.insets = new Insets(0, 0, 0, 5);
        gbcArrowLeft.gridx = 1;
        gbcArrowLeft.gridy = 0;
        transferDirection.add(this.arrowLeft, gbcArrowLeft);

        this.switchMappingDirectionButton = new JButton("Switch Transfer Direction");
        this.switchMappingDirectionButton.setToolTipText("Switch Transfer Direction");
        this.switchMappingDirectionButton.setFont(new Font(FONTNAME, Font.PLAIN, 14));
        GridBagConstraints gbcSwitchMappingDirectionButton = new GridBagConstraints();
        gbcSwitchMappingDirectionButton.insets = new Insets(0, 0, 0, 5);
        gbcSwitchMappingDirectionButton.anchor = GridBagConstraints.NORTH;
        gbcSwitchMappingDirectionButton.gridx = 2;
        gbcSwitchMappingDirectionButton.gridy = 0;
        transferDirection.add(this.switchMappingDirectionButton, gbcSwitchMappingDirectionButton);
        
        this.arrowRight = new JLabel(this.arrowImageIcon);
        GridBagConstraints gbcArrowRight = new GridBagConstraints();
        gbcArrowRight.insets = new Insets(0, 0, 0, 5);
        gbcArrowRight.gridx = 3;
        gbcArrowRight.gridy = 0;
        transferDirection.add(this.arrowRight, gbcArrowRight);
        
        JLabel lblNewLabel4 = new JLabel(ImageLoader.GetIcon("icon32.png"));
        GridBagConstraints gbcMblNewLabel4 = new GridBagConstraints();
        gbcMblNewLabel4.gridx = 4;
        gbcMblNewLabel4.gridy = 0;
        transferDirection.add(lblNewLabel4, gbcMblNewLabel4);
        
        JPanel panel = new JPanel();
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.anchor = GridBagConstraints.NORTH;
        gbcPanel.insets = new Insets(5, 10, 5, 10);
        gbcPanel.fill = GridBagConstraints.HORIZONTAL;
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 1;
        add(panel, gbcPanel);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{0, 0, 0};
        gblPanel.rowHeights = new int[]{0, 0};
        gblPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panel.setLayout(gblPanel);
        
        JLabel lblNewLabel2 = new JLabel("Current Saved Mapping Configuration");
        GridBagConstraints gbcLblNewLabel2 = new GridBagConstraints();
        gbcLblNewLabel2.insets = new Insets(0, 0, 0, 5);
        gbcLblNewLabel2.anchor = GridBagConstraints.EAST;
        gbcLblNewLabel2.gridx = 0;
        gbcLblNewLabel2.gridy = 0;
        panel.add(lblNewLabel2, gbcLblNewLabel2);
        
        this.mappingConfigurationComboBox = new JComboBox<>();
        this.mappingConfigurationComboBox.setEditable(true);
        
        this.mappingConfigurationComboBox.setToolTipText("<html><ul>Select an existing configuration from this list or write a name to create a new configuration.</ul>\n"
                + "<ul>Then hit the save button to create/load the Mapping Configuration</ul></html>");
        
        GridBagConstraints gbcComboBox = new GridBagConstraints();
        gbcComboBox.fill = GridBagConstraints.HORIZONTAL;
        gbcComboBox.gridx = 1;
        gbcComboBox.gridy = 0;
        panel.add(mappingConfigurationComboBox, gbcComboBox);
        
        this.saveMappingConfigurationButton = new JButton("\uD83D\uDCBE");
        this.saveMappingConfigurationButton.setToolTipText("Create and/or Load this new mapping configuration");
        GridBagConstraints gbcSaveUriButton = new GridBagConstraints();
        gbcSaveUriButton.fill = GridBagConstraints.BOTH;
        gbcSaveUriButton.insets = new Insets(10, 10, 10, 10);
        gbcSaveUriButton.gridx = 2;
        gbcSaveUriButton.gridy = 0;
        panel.add(this.saveMappingConfigurationButton, gbcSaveUriButton);
        
        JTabbedPane hubBrowserTreeViewsContainer = new JTabbedPane(SwingConstants.TOP);
                
        this.elementDefinitionBrowser = new ImpactViewObjectBrowser();
        this.elementDefinitionBrowser.SetContextMenu(this.elementDefinitionContextMenu);
        
        hubBrowserTreeViewsContainer.addTab("Element Definitions", ImageLoader.GetIcon(ClassKind.Iteration), this.elementDefinitionBrowser, null);
        
        this.requirementBrowser = new ImpactViewObjectBrowser();
        this.requirementBrowser.SetContextMenu(this.requirementsSpecificationContextMenu);
        hubBrowserTreeViewsContainer.addTab("Requirements", ImageLoader.GetIcon(ClassKind.RequirementsSpecification), this.requirementBrowser, null);
        
        this.impactViewsTabbedPane = new JTabbedPane(SwingConstants.BOTTOM);
        GridBagConstraints gbcImpactViewsTabbedPane = new GridBagConstraints();
        gbcImpactViewsTabbedPane.insets = new Insets(0, 0, 5, 0);
        gbcImpactViewsTabbedPane.fill = GridBagConstraints.BOTH;
        gbcImpactViewsTabbedPane.gridx = 0;
        gbcImpactViewsTabbedPane.gridy = 2;
        this.add(this.impactViewsTabbedPane, gbcImpactViewsTabbedPane);
        
        this.impactViewsTabbedPane.addTab("Hub Impact", ImageLoader.GetIcon(ClassKind.RequirementsSpecification), hubBrowserTreeViewsContainer, null);
        
        JPanel dstImpactContainer = new JPanel();
        impactViewsTabbedPane.addTab("Dst Impact", null, dstImpactContainer, null);
        GridBagLayout gblDstImpactContainer = new GridBagLayout();
        gblDstImpactContainer.columnWidths = new int[]{0, 0};
        gblDstImpactContainer.rowHeights = new int[]{0, 0};
        gblDstImpactContainer.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblDstImpactContainer.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        dstImpactContainer.setLayout(gblDstImpactContainer);
        
        dstImpactViewPanel = new JPanel();
        GridBagConstraints gbcDstImpactViewPanel = new GridBagConstraints();
        gbcDstImpactViewPanel.fill = GridBagConstraints.BOTH;
        gbcDstImpactViewPanel.gridx = 0;
        gbcDstImpactViewPanel.gridy = 0;
        
        dstImpactContainer.add(dstImpactViewPanel, gbcDstImpactViewPanel);
        GridBagLayout gblDstImpactViewPanel = new GridBagLayout();
        gblDstImpactViewPanel.columnWidths = new int[]{0};
        gblDstImpactViewPanel.rowHeights = new int[]{0};
        gblDstImpactViewPanel.columnWeights = new double[]{Double.MIN_VALUE};
        gblDstImpactViewPanel.rowWeights = new double[]{Double.MIN_VALUE};
        dstImpactViewPanel.setLayout(gblDstImpactViewPanel);
        
        JPanel panel1 = new JPanel();
        GridBagConstraints gbcPanel1 = new GridBagConstraints();
        gbcPanel1.insets = new Insets(5, 10, 10, 10);
        gbcPanel1.anchor = GridBagConstraints.SOUTH;
        gbcPanel1.fill = GridBagConstraints.HORIZONTAL;
        gbcPanel1.gridx = 0;
        gbcPanel1.gridy = 3;
        add(panel1, gbcPanel1);
        GridBagLayout gblPanel1 = new GridBagLayout();
        gblPanel1.columnWidths = new int[]{0, 0, 0};
        gblPanel1.rowHeights = new int[]{0, 0, 0};
        gblPanel1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gblPanel1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel1.setLayout(gblPanel1);
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setForeground(Color.decode("#00D8FF"));

        GridBagConstraints gbcProgressBar = new GridBagConstraints();
        gbcProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbcProgressBar.insets = new Insets(0, 0, 5, 0);
        gbcProgressBar.gridwidth = 2;
        gbcProgressBar.gridx = 0;
        gbcProgressBar.gridy = 0;
        panel1.add(progressBar, gbcProgressBar);
        
        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        GridBagConstraints gbcCancelButton = new GridBagConstraints();
        gbcCancelButton.insets = new Insets(0, 0, 0, 5);
        gbcCancelButton.gridx = 0;
        gbcCancelButton.gridy = 1;
        panel1.add(cancelButton, gbcCancelButton);
        
        transferButton = new JButton("Transfer");
        GridBagConstraints gbcTransferButton = new GridBagConstraints();
        gbcTransferButton.fill = GridBagConstraints.HORIZONTAL;
        gbcTransferButton.gridx = 1;
        gbcTransferButton.gridy = 1;
        panel1.add(transferButton, gbcTransferButton);
    }

    /**
     * Sets the impact-view view for the Dst model impact
     * 
     * @param dstObjectBrowser The {@linkplain ObjectBrowserBase} view to set
     */
    public void SetDstImpactViewView(ObjectBrowserBase<?,?> dstObjectBrowser)
    {
        SwingUtilities.invokeLater(() ->
        {
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.BOTH;
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.gridx = 0;
            constraints.gridy = 0;
            this.dstImpactViewPanel.add(dstObjectBrowser, constraints);
        });
    }
    
    /**
     * Attach a {@linkplain ActionListener} for the {@linkplain #switchMappingDirectionButton}
     * 
     * @param handler the {@linkplain Callable} of {@linkplain MappingDirection}
     */
    public void AttachOnChangeDirection(Callable<MappingDirection> handler)
    {
        this.switchMappingDirectionButton.addActionListener(e ->
        {
            try
            {
                this.UpdateVisualDirection(handler.call());
            } 
            catch (Exception exception)
            {
                this.logger.catching(exception);
            }
        });
        
    }
    
    /**
     * Set the collection source of the {@linkplain JComboBox} that lists all available {@linkplain ExternalIdentifierMap}
     * 
     * @param configurations the {@linkplain List} of configurations name
     */
    public void SetSavedMappingconfigurationCollection(List<String> configurations)
    {
        this.mappingConfigurationComboBox.removeAllItems();
        
        for (String string : configurations)
        {
            this.mappingConfigurationComboBox.addItem(string);
        }        
    }
        
    /**
     * Attach a {@linkplain ActionListener} for the {@linkplain #switchMappingDirectionButton}
     * 
     * @param handler the {@linkplain Function} of {@linkplain String} representing the current mapping configuration.
     * The {@linkplain Function} returns a value indicating whether the loaded configuration is new.
     */
    public void AttachOnSaveLoadMappingConfiguration(Predicate<String> handler)
    {
        this.saveMappingConfigurationButton.addActionListener(e ->
                {
                    try
                    {
                        String newConfigurationName = this.mappingConfigurationComboBox.getSelectedItem().toString();
                        
                        if(handler.test(newConfigurationName))
                        {
                            this.mappingConfigurationComboBox.addItem(newConfigurationName);
                            this.mappingConfigurationComboBox.setSelectedItem(newConfigurationName);
                        }
                    } 
                    catch (Exception exception)
                    {
                        this.logger.catching(exception);
                    }
                });
        
    }
    
    /**
     * Attach a {@linkplain ActionListener} for the {@linkplain #switchMappingDirectionButton}
     * 
     * @param handler the {@linkplain Callable} of {@linkplain Boolean}
     */
    public void AttachOnTransfer(Callable<Boolean> handler)
    {
        this.transferButton.addActionListener(e ->
        {
            try
            {
                ObservableTask<Boolean> task = Task.Create(handler, Boolean.class);
                
                ActionListener cancelButtonHandler = x -> task.Cancel();

                this.SetTransferIsInProgress(true);
                this.cancelButton.addActionListener(cancelButtonHandler);
                
                task.Run();
                
                task.Observable().subscribe(x -> 
                {
                    this.SetTransferIsInProgress(false);
                    this.cancelButton.removeActionListener(cancelButtonHandler);
                });
            } 
            catch (Exception exception)
            {
                this.logger.catching(exception);
                this.SetTransferIsInProgress(false);
            }
        });        
    }

    /**
     * Updates the arrows and the active tab for the impact views
     * 
     * @param newDirection the new {@linkplain MappingDirection}
     */
    private void UpdateVisualDirection(MappingDirection newDirection)
    {
//        if(newDirection == MappingDirection.FromHubToDst)
//        {
//            this.arrowLeft.setText(String.format(ArrowBaseString, 0));
//            this.arrowRight.setText(String.format(ArrowBaseString, 0));
//            this.impactViewsTabbedPane.setSelectedIndex(1);
//        }
//        else if(newDirection == MappingDirection.FromDstToHub)
//        {
//            this.arrowLeft.setText(String.format(ArrowBaseString, 2));
//            this.arrowRight.setText(String.format(ArrowBaseString, 2));
//            this.impactViewsTabbedPane.setSelectedIndex(0);
//        }
        int targetRotation; // Target rotation angle based on the new direction

        if (newDirection == MappingDirection.FromHubToDst) {
            targetRotation = 180; // Rotate left arrow by 180 degrees
            this.impactViewsTabbedPane.setSelectedIndex(1); // Activate Dst Impact tab
        } else if (newDirection == MappingDirection.FromDstToHub) {
            targetRotation = 0; // Rotate left arrow back to 0 degrees
            this.impactViewsTabbedPane.setSelectedIndex(0); // Activate Hub Impact tab
        } else {
            return; // No need to animate if direction is unchanged
        }

        // Initialize animation timer
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop(); // Stop any existing animation
        }

        // Calculate rotation step based on animation duration
        final int steps = ANIMATION_DURATION / 20; // Dividing by 20 for smoother animation
        final int stepRotation = (targetRotation - currentRotation) / steps;

        Ref<Integer> stepCount = new Ref<>(Integer.class, 0);

        // Create animation timer
        animationTimer = new Timer(20, e ->
        {
            currentRotation += stepRotation; // Update current rotation
            stepCount.Set(stepCount.Get() + 1);

            // Rotate arrows
            arrowLeft.setIcon(ImageLoader.GetRotatedIcon(this.arrowImageIcon, currentRotation));
            arrowRight.setIcon(ImageLoader.GetRotatedIcon(this.arrowImageIcon, currentRotation));

            if (stepCount.Get() >= steps)
            {
                animationTimer.stop();
            }
        });

        // Start animation timer
        animationTimer.start();
    }
    
    /**
     * Sets the relevant control of this view as enabled or disabled depending on the provided value
     * 
     * @param shouldEnable a value indicating whether it should enable or disable the relevant control
     */
    public void SetEnabled(boolean shouldEnable)
    {
        this.mappingConfigurationComboBox.setEnabled(shouldEnable);
        this.transferButton.setEnabled(shouldEnable);
        this.cancelButton.setEnabled(shouldEnable);
        this.saveMappingConfigurationButton.setEnabled(shouldEnable);
        this.switchMappingDirectionButton.setEnabled(shouldEnable);
    }
    
    /**
     * Updates the visibility of the progress bar and enables/disables the cancel button
     * 
     * @param isTransferInProgress a value indicating whether any transfer is in progress
     */
    public void SetTransferIsInProgress(boolean isTransferInProgress)
    {
        this.progressBar.setVisible(isTransferInProgress);
        this.cancelButton.setEnabled(isTransferInProgress);
    }
    
    /**
     * Updates the transfer button text with the number of selected for transfer button and sets whether the transfer can be enabled
     * 
     * @param number the number of items
     */
    public void UpdateTransferButtonNumberOfItems(int number)
    {
        this.transferButton.setText(String.format(TransferButtonBaseText, number > 0 ? String.format("%s thing(s)", number) : ""));
        this.transferButton.setEnabled(number > 0);
    }

    /**
     * Binds the transfer button text to update upon the provided observable that yields a number of selected things
     * 
     * @param numberOfSelectedThingObservable the {@linkplain Observable} of {@linkplain Integer}
     */
    public void BindNumberOfSelectedThingToTransfer(Observable<Integer> numberOfSelectedThingObservable)
    {
        numberOfSelectedThingObservable.subscribe(x -> this.UpdateTransferButtonNumberOfItems(x));
    }
    
    /**
     * Sets this {@linkplain JPopupMenu} data context
     * 
     * @param viewModel the {@linkplain IImpactViewContextMenuViewModel}
     */
    public void SetContextMenuDataContext(IImpactViewContextMenuViewModel viewModel)
    {
        this.elementDefinitionContextMenu.SetDataContext(viewModel);
        this.requirementsSpecificationContextMenu.SetDataContext(viewModel);
    }

    /**
     * Sets the mapping related controls enabled according to the specified to value
     * 
     * @param shouldEnabled
     */
    public void SetLoadMappingControlsIsEnable(Boolean shouldEnabled)
    {
        this.mappingConfigurationComboBox.setEnabled(shouldEnabled);
        this.saveMappingConfigurationButton.setEnabled(shouldEnabled);
    }    
}
