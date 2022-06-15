/*
 * SessionControlPanel.java
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

import Views.Interfaces.IView;
import javax.swing.JButton;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;

import ViewModels.Interfaces.ISessionControlPanelViewModel;
import ViewModels.Interfaces.IViewModel;

import javax.swing.JProgressBar;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ItemEvent;

import javax.swing.ImageIcon;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatter;

/**
 * The {@linkplain SessionControlPanel} is the view that contains the common {@linkplain Session} control such as refresh reload
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class SessionControlPanel extends JPanel implements IView<ISessionControlPanelViewModel>
{
	/**
	 * The used font
	 */
    private static final String TAHOMA = "Tahoma";

	/**
     * The {@link ISessionControlPanelViewModel} as the data context of this view
     */
    protected transient ISessionControlPanelViewModel dataContext;
    
    /**
     * View components declaration
     */
    private JProgressBar autoRefreshProgressBar;
    private JSpinner autoRefreshTime;
    private JCheckBox autoRefreshCheckBox;
    private JButton refreshButton;
    private JButton reloadButton;
    private JButton connectButton;
        
    /**
     * Initializes a new {@linkplain SessionControlPanel}
     */
    public SessionControlPanel()
    {
        this.Initialize();
        this.SetControlsEnabled(false);
    }

    /**
     * Initializes this view components
     */
    private void Initialize()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] {1, 1, 1, 1};
        gridBagLayout.rowHeights = new int[] {1};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 1.0};
        gridBagLayout.rowWeights = new double[]{0.0};
        setLayout(gridBagLayout);
        
        this.connectButton = new JButton("Connect");
        this.connectButton.setToolTipText("Connect to a Hub data source");
        this.connectButton.setFont(new Font(TAHOMA, Font.PLAIN, 14));
        GridBagConstraints gbc_connectButton = new GridBagConstraints();
        gbc_connectButton.anchor = GridBagConstraints.EAST;
        gbc_connectButton.insets = new Insets(0, 0, 5, 5);
        gbc_connectButton.gridx = 0;
        gbc_connectButton.gridy = 0;
        add(this.connectButton, gbc_connectButton);
        
        this.reloadButton = new JButton("Reload");
        this.reloadButton.setToolTipText("Reload all the data from the Hub");
        this.reloadButton.setFont(new Font(TAHOMA, Font.PLAIN, 14));
        GridBagConstraints gbc_reloadButton = new GridBagConstraints();
        gbc_reloadButton.insets = new Insets(0, 0, 5, 5);
        gbc_reloadButton.gridx = 1;
        gbc_reloadButton.gridy = 0;
        add(this.reloadButton, gbc_reloadButton);
        
        this.refreshButton = new JButton("Refresh");
        this.refreshButton.setToolTipText("Refresh the connection to the Hub");
        this.refreshButton.setFont(new Font(TAHOMA, Font.PLAIN, 14));
        GridBagConstraints gbc_refreshButton = new GridBagConstraints();
        gbc_refreshButton.insets = new Insets(0, 0, 5, 5);
        gbc_refreshButton.gridx = 2;
        gbc_refreshButton.gridy = 0;
        add(this.refreshButton, gbc_refreshButton);
        
        JPanel panel = new JPanel();
        panel.setVisible(false);
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.WEST;
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.gridx = 3;
        gbc_panel.gridy = 0;
        add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{85, 30, 0};
        gbl_panel.rowHeights = new int[]{23, 0, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);
        
        this.autoRefreshCheckBox = new JCheckBox("auto refresh");
        this.autoRefreshCheckBox.setToolTipText("Check on to turn on the auto refresh feature");
        GridBagConstraints gbc_autoRefreshCheckBox = new GridBagConstraints();
        gbc_autoRefreshCheckBox.anchor = GridBagConstraints.NORTHWEST;
        gbc_autoRefreshCheckBox.insets = new Insets(0, 0, 5, 5);
        gbc_autoRefreshCheckBox.gridx = 0;
        gbc_autoRefreshCheckBox.gridy = 0;
        panel.add(this.autoRefreshCheckBox, gbc_autoRefreshCheckBox);
        
        this.autoRefreshTime = new JSpinner();
        autoRefreshTime.setEnabled(false);
        this.autoRefreshTime.setToolTipText("Define a timer in second, to trigger the auto refresh");
        this.autoRefreshTime.setModel(new SpinnerNumberModel(30, 0, 86400, 1));
        JComponent comp = this.autoRefreshTime.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        
        GridBagConstraints gbc_autoRefreshTime = new GridBagConstraints();
        gbc_autoRefreshTime.fill = GridBagConstraints.HORIZONTAL;
        gbc_autoRefreshTime.insets = new Insets(0, 0, 5, 0);
        gbc_autoRefreshTime.gridx = 1;
        gbc_autoRefreshTime.gridy = 0;
        panel.add(this.autoRefreshTime, gbc_autoRefreshTime);
        
        this.autoRefreshProgressBar = new JProgressBar();
        GridBagConstraints gbc_autoRefreshProgressBar = new GridBagConstraints();
        gbc_autoRefreshProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbc_autoRefreshProgressBar.gridwidth = 2;
        gbc_autoRefreshProgressBar.gridx = 0;
        gbc_autoRefreshProgressBar.gridy = 1;
        panel.add(this.autoRefreshProgressBar, gbc_autoRefreshProgressBar);
    }
    
    /**
     * Binds the <code>TViewModel viewModel</code> to the implementing view
     * 
     * @param <code>viewModel</code> the view model to bind
     */
    @Override
    public void Bind()
    {
        this.SetControlsEnabled(this.dataContext.GetIsConnected());
        
        this.connectButton.addActionListener(e ->
                {
                    if(Boolean.FALSE.equals(this.dataContext.GetIsConnected()))
                    {
                        Boolean connectionDialogResult= this.dataContext.Connect();
                        
                        this.SetControlsEnabled(connectionDialogResult != null && connectionDialogResult);
                    }
                    else
                    {
                        this.dataContext.Disconnect();
                        this.SetControlsEnabled(false);
                    }
                });
        
        this.refreshButton.addActionListener(e -> this.dataContext.Refresh());
        this.reloadButton.addActionListener(e -> this.dataContext.Reload());
        
        this.autoRefreshTime.addChangeListener(e -> 
        { 
            SwingUtilities.invokeLater(() -> this.autoRefreshCheckBox.setSelected(true));
            this.TriggerAutoRefresh();
        });

        this.dataContext.GetTimeObservable().subscribe(x -> SwingUtilities.invokeLater(() -> this.autoRefreshProgressBar.setValue(x)));
        
        this.autoRefreshCheckBox.addItemListener(e -> 
        {
            boolean isChecked = e.getStateChange() == ItemEvent.SELECTED;
            
            this.SetAutoRefreshControlEnabled(isChecked);

            if(isChecked)
            {
                this.TriggerAutoRefresh();
            }
            else
            {
                this.dataContext.CancelAutoRefresh();
            }
        });
    }


    /**
     * Enables or disables relevant controls depending the a value indicating whether the session to the hub is open
     * 
     * @param isConnected the value indicating whether the session to the hub is open
     */
    private void SetControlsEnabled(boolean isConnected)
    {
        SwingUtilities.invokeLater(() ->
        {
            this.autoRefreshCheckBox.setEnabled(isConnected);
            this.reloadButton.setEnabled(isConnected);
            this.refreshButton.setEnabled(isConnected);
            connectButton.setText(isConnected ? "Disconnect" : "Connect");
        });
    }


    /**
     * Update the visibility of the progress bar and the activation of the spinner
     * 
     * @param isChecked a value indicating whether to activate or set the visibility of the relevant controls.
     * this value reflects the state of the <code>autoRefreshCheckbox</code>
     */
    private void SetAutoRefreshControlEnabled(boolean isChecked)
    {
        SwingUtilities.invokeLater(() ->
        {
            this.autoRefreshTime.setEnabled(isChecked);
            this.autoRefreshProgressBar.setVisible(isChecked);
        });
    }


    /**
     * Triggers the auto refresh to be activated
     */
    private void TriggerAutoRefresh()
    {
        Integer timer = (Integer)this.autoRefreshTime.getValue();
        
        if(timer != null)
        {
            SwingUtilities.invokeLater(() -> 
            {
                this.autoRefreshProgressBar.setMaximum(timer);
                this.dataContext.SetAutoRefresh(timer);
            });
        }
    }    

    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    @Override
    public void SetDataContext(ISessionControlPanelViewModel viewModel)
    {
        this.dataContext = viewModel;   
        this.Bind();
    }

    /**
     * Gets the DataContext
     * 
     * @return the {@link ISessionControlPanelViewModel}
     */
    @Override
    public ISessionControlPanelViewModel GetDataContext()
    {
        return this.dataContext;
    }
}
