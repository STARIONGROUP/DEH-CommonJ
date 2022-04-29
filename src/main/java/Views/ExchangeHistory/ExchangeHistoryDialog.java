/*
 * ExchangeHistoryDialog.java
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
package Views.ExchangeHistory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utils.ImageLoader.ImageLoader;
import ViewModels.ExchangeHistory.ExchangeHistoryRenderDataProvider;
import ViewModels.ExchangeHistory.Interfaces.IExchangeHistoryDialogViewModel;
import ViewModels.Interfaces.IHubLoginViewModel;
import ViewModels.Interfaces.IViewModel;

import java.awt.Toolkit;
import java.awt.GridBagLayout;

import Views.Interfaces.IDialog;
import Views.Interfaces.IView;
import Views.ObjectBrowser.ObjectBrowser;
import java.awt.GridBagConstraints;

/**
 * The {@linkplain ExchangeHistoryDialog} is the view that displays all exchange history entries
 */
@SuppressWarnings("serial")
public class ExchangeHistoryDialog extends JFrame implements IDialog<IExchangeHistoryDialogViewModel, Boolean>
{
    /**
     * Backing field for {@linkplain #GetDialogResult()}
     */
    private Boolean dialogResult;

    /**
     * This view attached {@linkplain IViewModel}
     */
    private IExchangeHistoryDialogViewModel dataContext;
    
    /**
     * View component declarations
     */
    private JPanel contentPane;
    private ExchangeHistoryObjectBrowser objectBrowserTree;
    
    /**
     * Initializes a new {@linkplain ExchangeHistoryDialog}
     */
    public ExchangeHistoryDialog()
    {
        this.Initialize();
    }
    
    /**
     * Initializes this view components
     */
    private void Initialize()
    {
        this.setTitle("Local Exchange History");
        this.setIconImage(ImageLoader.GetIcon().getImage());
        this.setBounds(100, 100, 450, 300);
        this.contentPane = new JPanel();
        this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);
        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0};
        gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);
        
        this.objectBrowserTree = new ExchangeHistoryObjectBrowser();
        GridBagConstraints gbc_objectBrowser = new GridBagConstraints();
        gbc_objectBrowser.fill = GridBagConstraints.BOTH;
        gbc_objectBrowser.gridx = 0;
        gbc_objectBrowser.gridy = 0;
        contentPane.add(this.objectBrowserTree, gbc_objectBrowser);
    }
    
    /**
     * Binds the <code>TViewModel viewModel</code> to the implementing view
     * 
     * @param <code>viewModel</code> the view model to bind
     */
    public void Bind()
    {
        this.objectBrowserTree.SetDataContext(this.GetDataContext());
    }
    
    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IExchangeHistoryDialogViewModel} to assign
     */
    public void SetDataContext(IExchangeHistoryDialogViewModel viewModel)
    {
        this.dataContext = viewModel;
        this.Bind();
    }
    
    /**
    * Gets the DataContext
    * 
    * @return the {@link IExchangeHistoryDialogViewModel}
    */
    @Override
    public IExchangeHistoryDialogViewModel GetDataContext()
    {
        return this.dataContext;
    }

    /**
     * Shows the dialog and return the result
     * 
     * @return a {@linkplain TResult}
     */
    @Override
    public Boolean ShowDialog()
    {
        this.setVisible(true);
        return this.dialogResult;
    }
    
    /**
     * Closes the dialog and sets the {@link dialogResult}
     * 
     * @param result the {@linkplain TResult} to set
     */
    @Override
    public void CloseDialog(Boolean result)
    {
        this.dialogResult = result;
        setVisible(false);
        dispose();
    }
    
    /**
     * Gets the {@linkplain dialogResult}
     * 
     * @return a {@linkplain Boolean}
     */
    public Boolean GetDialogResult()
    {
        return this.dialogResult;
    }
}
