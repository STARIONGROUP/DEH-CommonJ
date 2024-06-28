/*
 * ExchangeHistoryDialog.java
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
package Views.ExchangeHistory;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Utils.ImageLoader.ImageLoader;
import ViewModels.ExchangeHistory.Interfaces.IExchangeHistoryDialogViewModel;
import ViewModels.Interfaces.IViewModel;
import Views.Dialogs.BaseDialog;
import Views.Interfaces.IDialog;

/**
 * The {@linkplain ExchangeHistoryDialog} is the view that displays all exchange history entries
 */
@SuppressWarnings("serial")
@Annotations.ExludeFromCodeCoverageGeneratedReport
public class ExchangeHistoryDialog extends BaseDialog<Boolean> implements IDialog<IExchangeHistoryDialogViewModel, Boolean>
{
    /**
     * This view attached {@linkplain IViewModel}
     */
    private transient IExchangeHistoryDialogViewModel dataContext;
    
    /**
     * View {@linkplain ExchangeHistoryObjectBrowser}
     */
    private ExchangeHistoryObjectBrowser objectBrowserTree;
    
    /**
     * Initializes a new {@linkplain ExchangeHistoryDialog}
     */
    public ExchangeHistoryDialog()
    {
        this.setType(Type.POPUP);
        this.setModal(true);
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
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.setContentPane(contentPane);
        GridBagLayout gblContentPane = new GridBagLayout();
        gblContentPane.columnWidths = new int[]{0, 0};
        gblContentPane.rowHeights = new int[]{0, 0};
        gblContentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblContentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        contentPane.setLayout(gblContentPane);
        
        this.objectBrowserTree = new ExchangeHistoryObjectBrowser();
        GridBagConstraints gbcObjectBrowser = new GridBagConstraints();
        gbcObjectBrowser.fill = GridBagConstraints.BOTH;
        gbcObjectBrowser.gridx = 0;
        gbcObjectBrowser.gridy = 0;
        contentPane.add(this.objectBrowserTree, gbcObjectBrowser);
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
}
