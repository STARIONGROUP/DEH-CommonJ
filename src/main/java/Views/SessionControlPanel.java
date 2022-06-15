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

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.text.DefaultFormatter;

import ViewModels.Interfaces.ISessionControlPanelViewModel;
import ViewModels.Interfaces.IViewModel;
import Views.Interfaces.IView;
import cdp4dal.Session;

/**
 * The {@linkplain SessionControlPanel} is the view that contains the common {@linkplain Session} control such as refresh reload
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class SessionControlPanel extends JPanel implements IView<ISessionControlPanelViewModel>
{
	/**
	 * The name of the font
	 */
    private static final String FONTNAME = "Tahoma";

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
        this.connectButton.setFont(new Font(FONTNAME, Font.PLAIN, 14));
        GridBagConstraints gbcConnectButton = new GridBagConstraints();
        gbcConnectButton.anchor = GridBagConstraints.EAST;
        gbcConnectButton.insets = new Insets(0, 0, 5, 5);
        gbcConnectButton.gridx = 0;
        gbcConnectButton.gridy = 0;
        add(this.connectButton, gbcConnectButton);
        
        this.reloadButton = new JButton("Reload");
        this.reloadButton.setToolTipText("Reload all the data from the Hub");
        this.reloadButton.setFont(new Font(FONTNAME, Font.PLAIN, 14));
        GridBagConstraints gbcReloadButton = new GridBagConstraints();
        gbcReloadButton.insets = new Insets(0, 0, 5, 5);
        gbcReloadButton.gridx = 1;
        gbcReloadButton.gridy = 0;
        add(this.reloadButton, gbcReloadButton);
        
        this.refreshButton = new JButton("Refresh");
        this.refreshButton.setToolTipText("Refresh the connection to the Hub");
        this.refreshButton.setFont(new Font(FONTNAME, Font.PLAIN, 14));
        GridBagConstraints gbcRefreshButton = new GridBagConstraints();
        gbcRefreshButton.insets = new Insets(0, 0, 5, 5);
        gbcRefreshButton.gridx = 2;
        gbcRefreshButton.gridy = 0;
        add(this.refreshButton, gbcRefreshButton);
        
        JPanel panel = new JPanel();
        panel.setVisible(false);
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.anchor = GridBagConstraints.WEST;
        gbcPanel.insets = new Insets(0, 0, 5, 0);
        gbcPanel.gridx = 3;
        gbcPanel.gridy = 0;
        add(panel, gbcPanel);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{85, 30, 0};
        gblPanel.rowHeights = new int[]{23, 0, 0};
        gblPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gblPanel);
        
        this.autoRefreshCheckBox = new JCheckBox("auto refresh");
        this.autoRefreshCheckBox.setToolTipText("Check on to turn on the auto refresh feature");
        GridBagConstraints gbcAutoRefreshCheckBox = new GridBagConstraints();
        gbcAutoRefreshCheckBox.anchor = GridBagConstraints.NORTHWEST;
        gbcAutoRefreshCheckBox.insets = new Insets(0, 0, 5, 5);
        gbcAutoRefreshCheckBox.gridx = 0;
        gbcAutoRefreshCheckBox.gridy = 0;
        panel.add(this.autoRefreshCheckBox, gbcAutoRefreshCheckBox);
        
        this.autoRefreshTime = new JSpinner();
        autoRefreshTime.setEnabled(false);
        this.autoRefreshTime.setToolTipText("Define a timer in second, to trigger the auto refresh");
        this.autoRefreshTime.setModel(new SpinnerNumberModel(30, 0, 86400, 1));
        JComponent comp = this.autoRefreshTime.getEditor();
        JFormattedTextField field = (JFormattedTextField) comp.getComponent(0);
        DefaultFormatter formatter = (DefaultFormatter) field.getFormatter();
        formatter.setCommitsOnValidEdit(true);
        
        GridBagConstraints gbcAutoRefreshTime = new GridBagConstraints();
        gbcAutoRefreshTime.fill = GridBagConstraints.HORIZONTAL;
        gbcAutoRefreshTime.insets = new Insets(0, 0, 5, 0);
        gbcAutoRefreshTime.gridx = 1;
        gbcAutoRefreshTime.gridy = 0;
        panel.add(this.autoRefreshTime, gbcAutoRefreshTime);
        
        this.autoRefreshProgressBar = new JProgressBar();
        GridBagConstraints gbcAutoRefreshProgressBar = new GridBagConstraints();
        gbcAutoRefreshProgressBar.fill = GridBagConstraints.HORIZONTAL;
        gbcAutoRefreshProgressBar.gridwidth = 2;
        gbcAutoRefreshProgressBar.gridx = 0;
        gbcAutoRefreshProgressBar.gridy = 1;
        panel.add(this.autoRefreshProgressBar, gbcAutoRefreshProgressBar);
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
