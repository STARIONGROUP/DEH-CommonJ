/*
 * LogEntryDialog.java
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
package Views.Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.tuple.Pair;

import Utils.ImageLoader.ImageLoader;
import ViewModels.Dialogs.Interfaces.ILogEntryDialogViewModel;
import ViewModels.Interfaces.IViewModel;
import Views.Interfaces.IDialog;
import cdp4common.commondata.LogEntry;

/**
 * The {@linkplain LogEntryDialog} is the dialog window that let the user
 * provide a {@linkplain LogEntry} upon transfer to the hub
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class LogEntryDialog extends BaseDialog<Pair<String, Boolean>> implements IDialog<ILogEntryDialogViewModel, Pair<String, Boolean>>
{
    /**
     * This view attached {@linkplain #IViewModel}
     */
    private transient ILogEntryDialogViewModel dataContext;
    
    /**
     * View element declarations
     */
    private final JPanel contentPanel = new JPanel();
    private JButton cancelButton;
    private JButton okButton;
    private JTextArea justificationText;

    /**
     * Initializes a new {@linkplain LogEntryDialog}
     */
    public LogEntryDialog()
    {
        this.Initialize();
    }
    
    /**
     * Initializes this view components
     */
    private void Initialize()
    {
        this.setTitle("Add Log Entry");
        this.setModal(true);
        this.setIconImage(ImageLoader.GetIcon().getImage());
        this.setBounds(100, 100, 450, 300);
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[] {1, 0};
        gblContentPanel.rowHeights = new int[] {30};
        gblContentPanel.columnWeights = new double[]{1.0, 1.0};
        gblContentPanel.rowWeights = new double[]{1.0};
        contentPanel.setLayout(gblContentPanel);
        
        JLabel lblJustification = new JLabel("Justification:");
        lblJustification.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbcLblJustification = new GridBagConstraints();
        gbcLblJustification.anchor = GridBagConstraints.NORTH;
        gbcLblJustification.insets = new Insets(0, 0, 0, 5);
        gbcLblJustification.gridx = 0;
        gbcLblJustification.gridy = 0;
        contentPanel.add(lblJustification, gbcLblJustification);
        
        justificationText = new JTextArea();
        justificationText.setBackground(new Color(255, 255, 255));
        justificationText.setWrapStyleWord(true);
        justificationText.setLineWrap(true);
        justificationText.setColumns(18);
        justificationText.setRows(3);
        justificationText.setDropMode(DropMode.USE_SELECTION);
        justificationText.setToolTipText("Enter a justification text. If left blank: no LogEntry will be created");
        GridBagConstraints gbcJustificationText = new GridBagConstraints();
        gbcJustificationText.fill = GridBagConstraints.BOTH;
        gbcJustificationText.gridx = 1;
        gbcJustificationText.gridy = 0;
        contentPanel.add(justificationText, gbcJustificationText);
        
        JScrollPane scrollPane = new JScrollPane(justificationText);
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.gridx = 1;
        gbcScrollPane.gridy = 0;
        contentPanel.add(scrollPane, gbcScrollPane);
        
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        this.cancelButton = new JButton("Cancel");
        cancelButton.setToolTipText("Cancel the pending transactions");
        this.cancelButton.setActionCommand("Cancel");
        buttonPane.add(this.cancelButton);
        
        this.okButton = new JButton("OK");
        okButton.setToolTipText("Save a LogEntry with the specified justification text");
        this.okButton.setActionCommand("OK");
        buttonPane.add(this.okButton);
        getRootPane().setDefaultButton(this.okButton);
    }
    
    /**
     * Binds the <code>TViewModel viewModel</code> to the implementing view
     * 
     * @param <code>viewModel</code> the view model to bind
     */
    public void Bind()
    {
        this.okButton.addActionListener(x -> 
        {
            this.dataContext.SetLogEntryContent(this.justificationText.getText());
            this.CloseDialog(Pair.of(this.justificationText.getText(), true));
        });        
        this.cancelButton.addActionListener(x -> this.CloseDialog(Pair.of("", false)));
    }
    
    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    public void SetDataContext(ILogEntryDialogViewModel viewModel)
    {
        this.dataContext = viewModel;
        this.Bind();
    }
    
    /**
    * Gets the DataContext
    * 
    * @return an {@link #IViewModel}
    */
    @Override
    public ILogEntryDialogViewModel GetDataContext()
    {
        return this.dataContext;
    }
}
