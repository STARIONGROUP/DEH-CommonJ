/*
 * ObjectBrowser.java
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
package Views.ObjectBrowser;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import ViewModels.Interfaces.IObjectBrowserViewModel;
import ViewModels.Interfaces.IViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.ElementDefinitionBrowserTreeViewModel;
import ViewModels.ObjectBrowser.Interfaces.IThingRowViewModel;
import ViewModels.ObjectBrowser.RenderDataProvider.ObjectBrowserRenderDataProvider;
import Views.Interfaces.IView;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.swing.outline.Outline;

import java.awt.GridBagLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

/**
 * The {@linkplain ObjectBrowser} is the base browser for the {@linkplain ElementDefinitionBrowserTreeViewModel} or the {@linkplain RequirementSepcificationBrowserViewModel}
 */
@SuppressWarnings("serial")
public class ObjectBrowser extends JPanel implements IView<IObjectBrowserViewModel>
{
    /**
     * The current class log4J {@linkplain Logger}
     */
    private final Logger logger = LogManager.getLogger();

    /**
     * This view attached {@linkplain IViewModel}
     */
    private IObjectBrowserViewModel dataContext;
    
    /**
     * View element declaration
     */
    private Outline objectBrowserTree;
    
    /**
     * Initializes a new {@linkplain ObjectBrowser}
     */
    public ObjectBrowser()
    {        
        this.InitializeComponents();
    }

    /**
     * Initializes all the view component this view has 
     */
    private void InitializeComponents()
    {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{187, 0};
        gridBagLayout.rowHeights = new int[]{77, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);
        this.setBackground(Color.WHITE);
        
        this.objectBrowserTree = new Outline();
        objectBrowserTree.setShowHorizontalLines(false);
        objectBrowserTree.setShowVerticalLines(false);
        objectBrowserTree.setBackground(Color.white);
        JPanel panel = new JPanel();
                
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] {0};
        gbl_panel.rowHeights = new int[] {0};
        gbl_panel.columnWeights = new double[]{1.0};
        gbl_panel.rowWeights = new double[]{1.0};
        panel.setLayout(gbl_panel);
        this.objectBrowserTree = new Outline();
        this.objectBrowserTree.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.objectBrowserTree.setRowSelectionAllowed(true);
        this.objectBrowserTree.setSelectionBackground(new Color(104, 143, 184));
        this.objectBrowserTree.setBackground(Color.WHITE);
        this.objectBrowserTree.setOpaque(true);
        this.objectBrowserTree.setGridColor(Color.WHITE);
        this.objectBrowserTree.setRenderDataProvider(new ObjectBrowserRenderDataProvider()); 
        this.objectBrowserTree.setRootVisible(true);
                
        GridBagConstraints gbc_scrollView = new GridBagConstraints();
        gbc_scrollView.fill = GridBagConstraints.BOTH;
        gbc_scrollView.gridx = 0;
        gbc_scrollView.gridy = 0;
        JScrollPane scrollView = new JScrollPane(this.objectBrowserTree);
        panel.add(scrollView, gbc_scrollView);        
    }
    
    /**
     * Binds the <code>TViewModel viewModel</code> to the implementing view
     * 
     * @param <code>viewModel</code> the view model to bind
     */
    @Override
    public void Bind()
    {
        this.dataContext.BrowserTreeModel().subscribe(x -> 
        {   
            SwingUtilities.invokeLater(() -> 
            {
                this.objectBrowserTree.setModel(x);
            });
        });
        
        this.dataContext.IsTheTreeVisible().subscribe(x -> this.objectBrowserTree.setVisible(x));
        
        this.objectBrowserTree.getSelectionModel().addListSelectionListener(e ->
        {
            int row = objectBrowserTree.getSelectedRow();
            
            IThingRowViewModel<?> rowViewModel = (IThingRowViewModel<?>) objectBrowserTree.getValueAt(row, 0);
            
            if (!e.getValueIsAdjusting()) 
            {
                dataContext.OnSelectionChanged(rowViewModel);
            }
            
        });
    }
    
    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    @Override
    public void SetDataContext(IViewModel viewModel)
    {
        this.dataContext = (IObjectBrowserViewModel)viewModel;
        this.Bind();
    }
    
    /**
     * Gets the DataContext
     * 
     * @return An {@link IViewModel}
     */
    @Override
    public IObjectBrowserViewModel GetDataContext()
    {
        return this.dataContext;
    }
}
