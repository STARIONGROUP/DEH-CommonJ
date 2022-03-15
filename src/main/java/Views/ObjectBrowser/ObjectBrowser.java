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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.OutlineModel;

import ViewModels.Interfaces.IObjectBrowserBaseViewModel;
import ViewModels.Interfaces.IObjectBrowserViewModel;
import ViewModels.Interfaces.IViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.ElementDefinitionBrowserTreeViewModel;
import ViewModels.ObjectBrowser.RenderDataProvider.ObjectBrowserRenderDataProvider;
import ViewModels.ObjectBrowser.Rows.ThingRowViewModel;
import Views.ContextMenu.ContextMenu;
import Views.Interfaces.IView;
import cdp4common.commondata.Thing;

/**
 * The {@linkplain ObjectBrowser} is the base browser for the
 * {@linkplain ElementDefinitionBrowserTreeViewModel} or the
 * {@linkplain RequirementSepcificationBrowserViewModel}
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public class ObjectBrowser extends JPanel implements IView<IObjectBrowserBaseViewModel>
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
    protected Outline objectBrowserTree;

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
        gridBagLayout.columnWidths = new int[]
        { 187, 0 };
        gridBagLayout.rowHeights = new int[]
        { 77, 0 };
        gridBagLayout.columnWeights = new double[]
        { 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[]
        { 1.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        this.setBackground(Color.WHITE);

        JPanel panel = new JPanel();

        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]
        { 0 };
        gbl_panel.rowHeights = new int[]
        { 0 };
        gbl_panel.columnWeights = new double[]
        { 1.0 };
        gbl_panel.rowWeights = new double[]
        { 1.0 };
        panel.setLayout(gbl_panel);

        this.objectBrowserTree = new Outline();
        this.objectBrowserTree.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.objectBrowserTree.setRowSelectionAllowed(false);
        this.objectBrowserTree.setShowHorizontalLines(false);
        this.objectBrowserTree.setShowVerticalLines(false);
        this.objectBrowserTree.setBackground(Color.WHITE);
        this.objectBrowserTree.setOpaque(false);
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
        if(this.GetDataContext().GetBrowserTreeModel() != null)
        {
            this.SetOutlineModel(this.GetDataContext().GetBrowserTreeModel());
            this.SetTreeVisibility(true);
        }
        
        this.objectBrowserTree.setSelectionMode(this.GetDataContext().GetCanSelectMultipleElements()
                ? ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
                        : ListSelectionModel.SINGLE_SELECTION);
        

        this.objectBrowserTree.setRowSelectionAllowed(this.GetDataContext().GetCanSelectMultipleElements());
        
        this.GetDataContext().BrowserTreeModel().subscribe(this::SetOutlineModel);
        this.GetDataContext().IsTheTreeVisible().subscribe(this::SetTreeVisibility);

        this.GetDataContext().GetShouldRefreshTree().filter(x -> x).subscribe(x -> SwingUtilities.invokeLater(
                () -> objectBrowserTree.tableChanged(new TableModelEvent(this.objectBrowserTree.getOutlineModel()))));

        this.objectBrowserTree.addMouseListener(new MouseAdapter()
        {
            /**
             * Invoked when the mouse button has been clicked (pressed and released) on a component.
             * 
             * @param event the {@linkplain MouseEvent}
             */
            @Override
            public void mouseClicked(MouseEvent event)
            {
                OnSelectionChanged();
            }
        });
    }

    /**
     * Sets the visibility of this tree
     * 
     * @param isVisible the value indicating whether the tree should be made visible
     */
    private void SetTreeVisibility(Boolean isVisible)
    {
        SwingUtilities.invokeLater(() -> this.objectBrowserTree.setVisible(isVisible));
    }

    /**
     * Sets the {@linkplain OutlineModel} of this tree
     * 
     * @param model the {@linkplain OutlineModel} to set
     */
    private void SetOutlineModel(OutlineModel model)
    {
        if (model != null)
        {
            SwingUtilities.invokeLater(() -> {
                this.objectBrowserTree.setModel(model);
                this.objectBrowserTree.tableChanged(new TableModelEvent(this.objectBrowserTree.getOutlineModel()));
            });
        }
    }

    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    @Override
    public void SetDataContext(IViewModel viewModel)
    {
        this.dataContext = (IObjectBrowserViewModel) viewModel;       
        this.Bind();
    }

    /**
     * Gets the DataContext
     * 
     * @return An {@link IViewModel}
     */
    @Override
    public IObjectBrowserBaseViewModel GetDataContext()
    {
        return this.dataContext;
    }

    /**
     * Gets the context menu instance of the current {@linkplain #objectBrowserTree} if it is of type {@linkplain ContextMenu}
     * 
     * @return the {@linkplain ContextMenu}
     */
    public ContextMenu<?> GetContextMenu()
    {
        JPopupMenu contextMenu = this.objectBrowserTree.getComponentPopupMenu();
        
        if (contextMenu instanceof ContextMenu)
        {
            return (ContextMenu<?>) this.objectBrowserTree.getComponentPopupMenu();
        }
        
        return null;        
    }
    
    /**
     * Sets this {@linkplain Outline} contained component context menu
     * 
     * @param contextMenu the {@linkplain ContextMenu}
     */
    public void SetContextMenu(ContextMenu<?> contextMenu)
    {
        this.objectBrowserTree.setComponentPopupMenu(contextMenu);
    }

    /**
     * Handles the selection when the user changes it, intended to be virtual
     */
    protected void OnSelectionChanged()
    {
        this.ProcessSelectedElement();
        this.ProcessSelectedElements();
    }

    /**
     * Processes the currently selected elements
     */
    @SuppressWarnings("unchecked")
    private void ProcessSelectedElements()
    {
        int[] selectedRowIndexes = objectBrowserTree.getSelectedRows();
        
        ArrayList<ThingRowViewModel<? extends Thing>> selectedThings = new ArrayList<>();
        
        for (int rowIndex : selectedRowIndexes)
        {
            selectedThings.add((ThingRowViewModel<? extends Thing>) objectBrowserTree.getValueAt(rowIndex, 0));
        }

        dataContext.OnSelectionChanged(selectedThings);
    }
    
    /**
     * Processes the currently selected element
     */
    private void ProcessSelectedElement()
    {
        int selectedRowIndex = objectBrowserTree.getSelectedRow();
        
        @SuppressWarnings("unchecked")
        Pair<Integer, ThingRowViewModel<? extends Thing>> row = Pair.of(selectedRowIndex,
                (ThingRowViewModel<? extends Thing>) objectBrowserTree.getValueAt(selectedRowIndex, 0));

        dataContext.OnSelectionChanged(row.getRight());

        SwingUtilities.invokeLater(() -> {
            objectBrowserTree
                    .tableChanged(new TableModelEvent(objectBrowserTree.getOutlineModel(), row.getLeft()));
        });
    }
}
