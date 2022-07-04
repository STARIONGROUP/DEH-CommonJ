/*
 * ObjectBrowserBase.java
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
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.netbeans.swing.outline.Outline;
import org.netbeans.swing.outline.OutlineModel;

import ViewModels.Interfaces.IContextMenuViewModel;
import ViewModels.Interfaces.IObjectBrowserBaseViewModel;
import ViewModels.Interfaces.IViewModel;
import ViewModels.ObjectBrowser.RenderDataProvider.ObjectBrowserRenderDataProvider;
import Views.ContextMenu.ContextMenu;
import Views.Interfaces.IView;

/**
 * The {@linkplain ObjectBrowserBase} is the base view for {@linkplain ObjectBrowser}s
 * 
 * @param <TViewModel> defines the type of the view model that can be bound to the view
 * @param <TContextMenuViewModel> defines the type of the context menu view model of the can be bound to the view
 */
@Annotations.ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public abstract class ObjectBrowserBase<TViewModel extends IObjectBrowserBaseViewModel<?>, TContextMenuViewModel extends IContextMenuViewModel> extends JPanel implements IView<TViewModel>
{
   /**
    * This view attached {@linkplain IViewModel}
    */
    protected transient TViewModel dataContext;
    
    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    @Override
    public void SetDataContext(TViewModel viewModel)
    {
        this.dataContext = viewModel;       
        this.Bind();
    }

    /**
     * Gets the DataContext
     * 
     * @return An {@link #TViewModel}
     */
    @Override
    public TViewModel GetDataContext()
    {
        return this.dataContext;
    }

    /**
     * the {@linkplain Outline}
     */
    protected Outline objectBrowserTree;
    
    /**
     * Initializes a new {@linkplain ObjectBrowser}
     */
    protected ObjectBrowserBase()
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

        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.fill = GridBagConstraints.BOTH;
        gbcPanel.gridx = 0;
        gbcPanel.gridy = 0;
        add(panel, gbcPanel);
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]
        { 0 };
        gblPanel.rowHeights = new int[]
        { 0 };
        gblPanel.columnWeights = new double[]
        { 1.0 };
        gblPanel.rowWeights = new double[]
        { 1.0 };
        panel.setLayout(gblPanel);

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
        GridBagConstraints gbcScrollView = new GridBagConstraints();
        gbcScrollView.fill = GridBagConstraints.BOTH;
        gbcScrollView.gridx = 0;
        gbcScrollView.gridy = 0;
        JScrollPane scrollView = new JScrollPane(this.objectBrowserTree);
        panel.add(scrollView, gbcScrollView);
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
        
        this.objectBrowserTree.setSelectionMode(this.GetDataContext().GetSelectionMode());

        this.objectBrowserTree.setRowSelectionAllowed(this.GetDataContext().GetSelectionMode() != 1);
        
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
            @Annotations.ExludeFromCodeCoverageGeneratedReport
            public void mouseClicked(MouseEvent event)
            {
                OnSelectionChanged();
            }
        });
    }

    /**
     * Sets the columns preferred width
     */
    protected void SetColumnsPreferredWidth()
    {
        for (int columnIndex = 0; columnIndex < this.objectBrowserTree.getColumnCount(); columnIndex++) 
        {
            DefaultTableColumnModel columnModel = (DefaultTableColumnModel) this.objectBrowserTree.getColumnModel();
            TableColumn column = columnModel.getColumn(columnIndex);
            int width = 0;

            for (int rowIndex = 0; rowIndex < this.objectBrowserTree.getRowCount(); rowIndex++) 
            {
                TableCellRenderer renderer = this.objectBrowserTree.getCellRenderer(rowIndex, columnIndex);
                
                Component comp = renderer.getTableCellRendererComponent(this.objectBrowserTree, 
                        this.objectBrowserTree.getValueAt(rowIndex, columnIndex), false, false, rowIndex, columnIndex);
                
                int preferredWidth = comp.getPreferredSize() != null ? comp.getPreferredSize().width : 110;
                width = Math.max(width, preferredWidth < 25 ? 110 : preferredWidth);
            }
            
            column.setPreferredWidth(width + 2);
        }
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
            SwingUtilities.invokeLater(() -> 
            {
                this.objectBrowserTree.setModel(model);
                this.objectBrowserTree.tableChanged(new TableModelEvent(this.objectBrowserTree.getOutlineModel()));
                this.SetColumnsPreferredWidth();
            });
        }
    }
    
    /**
     * Gets the context menu instance of the current {@linkplain #objectBrowserTree} if it is of type {@linkplain ContextMenu}
     *
     * @return the {@linkplain ContextMenu}
     */
    @SuppressWarnings("unchecked")
    public ContextMenu<TContextMenuViewModel> GetContextMenu()
    {
        JPopupMenu contextMenu = this.objectBrowserTree.getComponentPopupMenu();
        
        if (contextMenu instanceof ContextMenu)
        {
            return (ContextMenu<TContextMenuViewModel>) this.objectBrowserTree.getComponentPopupMenu();
        }
        
        return null;
    }
    
    /**
     * Sets this {@linkplain Outline} contained component context menu
     * 
     * @param contextMenu the {@linkplain ContextMenu}
     */
    public void SetContextMenu(ContextMenu<? extends TContextMenuViewModel> contextMenu)
    {
        this.objectBrowserTree.setComponentPopupMenu(contextMenu);
        
        if(contextMenu != null)
        {            
            this.objectBrowserTree.getComponentPopupMenu().addPopupMenuListener(new PopupMenuListener() 
            {
                /**
                 * Occurs when the pop-up menu will become visible
                 * 
                 * @param e the {@linkplain PopupMenuEvent}
                 */
                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e)
                {
                    OnSelectionChanged();             
                }

                /**
                 * Occurs when the pop-up menu will become invisible. Unused.
                 * 
                 * @param e the {@linkplain PopupMenuEvent}
                 */
                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) 
                {
                    //Unused
                }

                /**
                 * Occurs when the pop-up menu will become invisible. Unused.
                 * 
                 * @param e the {@linkplain PopupMenuEvent}
                 */
                @Override
                public void popupMenuCanceled(PopupMenuEvent e) 
                {
                    //Unused
                }
            });
        }
    }

    /**
     * Handles the selection when the user changes it, intended to be virtual
     */
    protected abstract void OnSelectionChanged();
}
