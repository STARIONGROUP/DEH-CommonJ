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
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Enumerations.MappingDirection;
import Views.ContextMenu.ImpactViewContextMenu;
import Views.ObjectBrowser.ObjectBrowser;
import cdp4common.commondata.ClassKind;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.RequirementsSpecification;
import io.reactivex.Observable;
import Utils.ImageLoader.ImageLoader;
import ViewModels.Interfaces.IImpactViewContextMenuViewModel;

import Utils.Tasks.*;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.ImageIcon;

/**
 * The {@linkplain ImpactViewPanel} is the view that displays the impact views
 */
@SuppressWarnings("serial")
public class ImpactViewPanel extends JPanel
{
    /**
     * The current class {@linkplain Logger}
     */
    private Logger logger = LogManager.getLogger();
    
    /**
     * The arrows format-able base string
     */
    private final String ArrowBaseString = "<html><body>&#x1F87%s;</body></html>";
    
    /**
     * The base text for the transfer button
     */
    private final String TransferButtonBaseText = "Transfer %s";
        
    /**
     * Backing field for {@link ConnectButton}
     */
    private JButton switchMappingDirectionButton;
        
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
     * The {@linkplain ContextMenu} that is to be used on the {@linkplain ElementDefinition} impact tree
     */
    private ImpactViewContextMenu elementDefinitionContextMenu;

    /**
     * The {@linkplain ContextMenu} that is to be used on the {@linkplain RequirementsSpecification} impact tree
     */
    private ImpactViewContextMenu requirementsSpecificationContextMenu;
    
    /**
     * View components declaration
     */
    private JLabel arrowLeft;
    private JLabel arrowRight;
    private JTabbedPane impactViewsTabbedPane;
    private JPanel panel;
    private JComboBox<String> mappingConfigurationComboBox;
    private JLabel lblNewLabel_2;
    private JButton saveMappingConfigurationButton;
    private JPanel panel_1;
    private JProgressBar progressBar;
    private JButton cancelButton;
    private JButton transferButton;
    private JLabel lblNewLabel_3;
    private JLabel lblNewLabel_4;
        
    /**
     * Initializes a new {@link ImpactViewPanel}
     */
    public ImpactViewPanel()
    {
        this.elementDefinitionContextMenu = new ImpactViewContextMenu(ClassKind.ElementDefinition);
        this.requirementsSpecificationContextMenu = new ImpactViewContextMenu(ClassKind.RequirementsSpecification);
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
        GridBagConstraints gbc_transferDirection = new GridBagConstraints();
        gbc_transferDirection.anchor = GridBagConstraints.NORTH;
        gbc_transferDirection.insets = new Insets(10, 10, 5, 10);
        gbc_transferDirection.fill = GridBagConstraints.HORIZONTAL;
        gbc_transferDirection.gridx = 0;
        gbc_transferDirection.gridy = 0;
        add(transferDirection, gbc_transferDirection);        
        GridBagLayout gbl_transferDirection = new GridBagLayout();
        gbl_transferDirection.columnWidths = new int[] {0, 1, 1, 1, 0};
        gbl_transferDirection.rowHeights = new int[] {0};
        gbl_transferDirection.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0};
        gbl_transferDirection.rowWeights = new double[]{0.0};
        transferDirection.setLayout(gbl_transferDirection);
        
        lblNewLabel_3 = new JLabel("");
        lblNewLabel_3.setIcon(new ImageIcon(ImpactViewPanel.class.getResource("/Utils/ImageLoader/Images/sysml.png")));
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 0;
        transferDirection.add(lblNewLabel_3, gbc_lblNewLabel_3);
                
        arrowLeft = new JLabel("<html><body>&#x1F872;</body></html>");
        arrowLeft.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 0;
        transferDirection.add(arrowLeft, gbc_lblNewLabel);

        this.switchMappingDirectionButton = new JButton("Switch Transfer Direction");
        this.switchMappingDirectionButton.setToolTipText("Switch Transfer Direction");
        this.switchMappingDirectionButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        GridBagConstraints gbc_switchMappingDirectionButton = new GridBagConstraints();
        gbc_switchMappingDirectionButton.insets = new Insets(0, 0, 0, 5);
        gbc_switchMappingDirectionButton.anchor = GridBagConstraints.NORTH;
        gbc_switchMappingDirectionButton.gridx = 2;
        gbc_switchMappingDirectionButton.gridy = 0;
        transferDirection.add(this.switchMappingDirectionButton, gbc_switchMappingDirectionButton);
        
        arrowRight = new JLabel("<html><body>&#x1F872;</body></html>");
        arrowRight.setFont(new Font("Tahoma", Font.PLAIN, 20));
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_1.gridx = 3;
        gbc_lblNewLabel_1.gridy = 0;
        transferDirection.add(arrowRight, gbc_lblNewLabel_1);
        
        lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setIcon(new ImageIcon(ImpactViewPanel.class.getResource("/Utils/ImageLoader/Images/icon16.png")));
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.gridx = 4;
        gbc_lblNewLabel_4.gridy = 0;
        transferDirection.add(lblNewLabel_4, gbc_lblNewLabel_4);
        
        panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.NORTH;
        gbc_panel.insets = new Insets(5, 10, 5, 10);
        gbc_panel.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 1;
        add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{0, 0, 0};
        gbl_panel.rowHeights = new int[]{0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        lblNewLabel_2 = new JLabel("Current Saved Mapping Configuration");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.gridx = 0;
        gbc_lblNewLabel_2.gridy = 0;
        panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
        
        this.mappingConfigurationComboBox = new JComboBox<String>();
        this.mappingConfigurationComboBox.setEditable(true);
        
        this.mappingConfigurationComboBox.setToolTipText("<html><ul>Select an existing configuration from this list or write a name to create a new configuration.</ul>\n"
                + "<ul>Then hit the save button to create/load the Mapping Configuration</ul></html>");
        
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 1;
        gbc_comboBox.gridy = 0;
        panel.add(mappingConfigurationComboBox, gbc_comboBox);
        
        this.saveMappingConfigurationButton = new JButton("\uD83D\uDCBE");
        this.saveMappingConfigurationButton.setToolTipText("Create and/or Load this new mapping configuration");
        GridBagConstraints gbc_SaveUriButton = new GridBagConstraints();
        gbc_SaveUriButton.fill = GridBagConstraints.BOTH;
        gbc_SaveUriButton.insets = new Insets(10, 10, 10, 10);
        gbc_SaveUriButton.gridx = 2;
        gbc_SaveUriButton.gridy = 0;
        panel.add(this.saveMappingConfigurationButton, gbc_SaveUriButton);
        
        JTabbedPane HubBrowserTreeViewsContainer = new JTabbedPane(JTabbedPane.TOP);
                
        this.elementDefinitionBrowser = new ObjectBrowser();
        this.elementDefinitionBrowser.SetContextMenu(this.elementDefinitionContextMenu);
        
        HubBrowserTreeViewsContainer.addTab("Element Definitions", ImageLoader.GetIcon(ClassKind.Iteration), this.elementDefinitionBrowser, null);
        
        this.requirementBrowser = new ObjectBrowser();
        this.requirementBrowser.SetContextMenu(this.requirementsSpecificationContextMenu);
        HubBrowserTreeViewsContainer.addTab("Requirements", ImageLoader.GetIcon(ClassKind.RequirementsSpecification), this.requirementBrowser, null);
        
        this.impactViewsTabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        GridBagConstraints gbc_impactViewsTabbedPane = new GridBagConstraints();
        gbc_impactViewsTabbedPane.insets = new Insets(0, 0, 5, 0);
        gbc_impactViewsTabbedPane.fill = GridBagConstraints.BOTH;
        gbc_impactViewsTabbedPane.gridx = 0;
        gbc_impactViewsTabbedPane.gridy = 2;
        this.add(this.impactViewsTabbedPane, gbc_impactViewsTabbedPane);
        
        this.impactViewsTabbedPane.addTab("Hub Impact", ImageLoader.GetIcon(ClassKind.RequirementsSpecification), HubBrowserTreeViewsContainer, null);
        this.impactViewsTabbedPane.addTab("SysML Project Impact", ImageLoader.GetIcon(ClassKind.RequirementsSpecification), new JTabbedPane(JTabbedPane.TOP), null);
        
        panel_1 = new JPanel();
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.insets = new Insets(5, 10, 10, 10);
        gbc_panel_1.anchor = GridBagConstraints.SOUTH;
        gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 3;
        add(panel_1, gbc_panel_1);
        GridBagLayout gbl_panel_1 = new GridBagLayout();
        gbl_panel_1.columnWidths = new int[]{0, 0, 0};
        gbl_panel_1.rowHeights = new int[]{0, 0, 0};
        gbl_panel_1.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel_1.setLayout(gbl_panel_1);
        
        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        progressBar.setForeground(Color.decode("#00D8FF"));

        GridBagConstraints gbc_progressBar = new GridBagConstraints();
        gbc_progressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_progressBar.insets = new Insets(0, 0, 5, 0);
        gbc_progressBar.gridwidth = 2;
        gbc_progressBar.gridx = 0;
        gbc_progressBar.gridy = 0;
        panel_1.add(progressBar, gbc_progressBar);
        
        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);
        GridBagConstraints gbc_cancelButton = new GridBagConstraints();
        gbc_cancelButton.insets = new Insets(0, 0, 0, 5);
        gbc_cancelButton.gridx = 0;
        gbc_cancelButton.gridy = 1;
        panel_1.add(cancelButton, gbc_cancelButton);
        
        transferButton = new JButton("Transfer");
        GridBagConstraints gbc_transferButton = new GridBagConstraints();
        gbc_transferButton.fill = GridBagConstraints.HORIZONTAL;
        gbc_transferButton.gridx = 1;
        gbc_transferButton.gridy = 1;
        panel_1.add(transferButton, gbc_transferButton);
    }
    
    /**
     * Attach a {@linkplain ActionListener} for the {@linkplain switchMappingDirectionButton}
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
     * Set the collection source of the {@linkplain ComboBox} that lists all available {@linkplain ExternalIdentifierMap}
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
     * Attach a {@linkplain ActionListener} for the {@linkplain switchMappingDirectionButton}
     * 
     * @param handler the {@linkplain Function} of {@linkplain String} representing the current mapping configuration.
     * The {@linkplain Function} returns a value indicating whether the loaded configuration is new.
     */
    public void AttachOnSaveLoadMappingConfiguration(Function<String, Boolean> handler)
    {
        this.saveMappingConfigurationButton.addActionListener(e ->
                {
                    try
                    {
                        String newConfigurationName = this.mappingConfigurationComboBox.getSelectedItem().toString();
                        
                        if(handler.apply(newConfigurationName))
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
     * Attach a {@linkplain ActionListener} for the {@linkplain switchMappingDirectionButton}
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
                
                ActionListener cancelButtonHandler = x ->
                {
                    task.Cancel();
                };

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
        if(newDirection == MappingDirection.FromHubToDst)
        {
            this.arrowLeft.setText(String.format(ArrowBaseString, 0));
            this.arrowRight.setText(String.format(ArrowBaseString, 0));
            this.impactViewsTabbedPane.setSelectedIndex(1);
        }
        else if(newDirection == MappingDirection.FromDstToHub)
        {
            this.arrowLeft.setText(String.format(ArrowBaseString, 2));
            this.arrowRight.setText(String.format(ArrowBaseString, 2));
            this.impactViewsTabbedPane.setSelectedIndex(0);
        }
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
        this.progressBar.setVisible(isTransferInProgress);;
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
