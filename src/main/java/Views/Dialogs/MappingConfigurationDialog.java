/*
 * MappingConfigurationDialog.java
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
package Views.Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Annotations.ExludeFromCodeCoverageGeneratedReport;
import Enumerations.MappingDirection;
import Utils.ImageLoader.ImageLoader;
import ViewModels.Dialogs.Interfaces.IMappingConfigurationDialogViewModel;
import ViewModels.Interfaces.IContextMenuViewModel;
import ViewModels.Interfaces.IObjectBrowserBaseViewModel;
import ViewModels.Interfaces.IViewModel;
import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import ViewModels.Rows.MappedElementRowViewModel;
import Views.MappedElementListView;
import Views.Interfaces.IDialog;
import Views.ObjectBrowser.ObjectBrowser;
import Views.ObjectBrowser.ObjectBrowserBase;
import cdp4common.commondata.ClassKind;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.RequirementsSpecification;

/**
 * The MappingConfigurationDialog is the base view for all mapping configuration dialog
 * 
 * @param <TViewModel> the type of {@linkplain IMappingConfigurationDialogViewModel}
 * @param <TSourceElement> the type of source element depending on the mapping direction
 * @param <TDstElement> the type of element the DST adapter works with
 * @param <TRowViewModel> the type of base row view mode DST adapter uses for the DST object browser
 */
@ExludeFromCodeCoverageGeneratedReport
@SuppressWarnings("serial")
public abstract class MappingConfigurationDialog
    <TViewModel extends IMappingConfigurationDialogViewModel<TSourceElement, TDstElement, TRowViewModel>, TSourceElement, TDstElement, TRowViewModel 
    extends IRowViewModel> extends BaseDialog<Boolean> implements IDialog<TViewModel, Boolean>
{
    /**
     * This view attached {@linkplain #IViewModel}
     */
    private transient TViewModel dataContext;
    
    /**
     * The {@linkplain ObjectBrowser} view for {@linkplain ElementDefinition}
     */
    private ObjectBrowser elementDefinitionBrowser;

    /**
     * The {@linkplain ObjectBrowser} view for {@linkplain RequirementsSpecification}
     */
    private ObjectBrowser requirementBrowser;    

    /**
     * The {@linkplain MappedElementListView} view for the {@linkplain MappedElementRowViewModel}
     */
    protected MappedElementListView<TDstElement> mappedElementListView;

    /**
     * View components declarations
     */
    private final JPanel contentPanel = new JPanel();
    private JButton okButton;
    private JButton cancelButton;
    private JSplitPane browserSplitPane;
    private ObjectBrowserBase<IObjectBrowserBaseViewModel<TRowViewModel>, ? extends IContextMenuViewModel> dstObjectBrowser;
    private boolean hasBeenPaintedOnce;
    private JSplitPane mainSplitPane;
    private JCheckBox mapToNewElementCheckBox;
    private JButton resetButton;
    
    /**
     * Initializes a new {@linkplain CapellaDstToHubMappingConfigurationDialog}
     * 
     * @param mappingDirection the {@linkplain MappingDirection} the concrete view is working on
     * @param dstObjectBrowser the dst adapter specific {@linkplain ObjectBrowserBase}
     * @param mappedElementListView the specific {@linkplain MappedElementListView} instance
     */
    @SuppressWarnings("unchecked")
    protected MappingConfigurationDialog(MappingDirection mappingDirection, ObjectBrowserBase<?, ? extends IContextMenuViewModel> dstObjectBrowser, MappedElementListView<TDstElement> mappedElementListView)
    {
        this.mappedElementListView = mappedElementListView;
        this.dstObjectBrowser = (ObjectBrowserBase<IObjectBrowserBaseViewModel<TRowViewModel>, ? extends IContextMenuViewModel>)dstObjectBrowser;
        this.Initialize(mappingDirection);
    }

    /**
     * Initializes a new {@linkplain CapellaDstToHubMappingConfigurationDialog}
     * 
     * @param mappingDirection the {@linkplain MappingDirection} the concrete view is working on
     * @param dstObjectBrowser the dst adapter specific {@linkplain ObjectBrowserBase}
     */
    protected MappingConfigurationDialog(MappingDirection mappingDirection, ObjectBrowserBase<?, ? extends IContextMenuViewModel> dstObjectBrowser)
    {
        this(mappingDirection, dstObjectBrowser, new MappedElementListView<>());
    }
    
    /**
     * Initializes this view visual components and properties
     * 
     * @param mappingDirection the {@linkplain MappingDirection} the concrete view is working on  
     */
    @ExludeFromCodeCoverageGeneratedReport
    private void Initialize(MappingDirection mappingDirection)
    {
        String titleSuffix = mappingDirection == MappingDirection.FromDstToHub ? "DST to HUB" : "HUB to DST";
        this.setTitle(String.format("Mapping Configuration dialog from %s", titleSuffix));
        this.setType(Type.POPUP);
        this.setModal(true);
        this.setBounds(100, 100, 549, 504);
        this.setMinimumSize(new Dimension(800, 600));
        this.setIconImage(ImageLoader.GetIcon("icon16.png").getImage());
        this.getContentPane().setLayout(new BorderLayout());
        this.contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(this.contentPanel, BorderLayout.CENTER);
        GridBagLayout gblContentPanel = new GridBagLayout();
        gblContentPanel.columnWidths = new int[] {};
        gblContentPanel.rowHeights = new int[] {0};
        gblContentPanel.columnWeights = new double[]{1.0};
        gblContentPanel.rowWeights = new double[]{1.0};
        this.contentPanel.setLayout(gblContentPanel);
        
        this.mainSplitPane = new JSplitPane();
        this.mainSplitPane.setDividerLocation(0.5);
        this.mainSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        
        this.browserSplitPane = new JSplitPane();
        this.browserSplitPane.setContinuousLayout(true);
        this.browserSplitPane.setDividerLocation(0.5);
        this.mainSplitPane.setLeftComponent(this.browserSplitPane);
        
        JPanel hubBrowserPanel = new JPanel();
        
        if(mappingDirection == MappingDirection.FromDstToHub)
        {
            this.browserSplitPane.setLeftComponent(this.dstObjectBrowser);
            this.browserSplitPane.setRightComponent(hubBrowserPanel);
        }
        else
        {
            this.browserSplitPane.setRightComponent(this.dstObjectBrowser);
            this.browserSplitPane.setLeftComponent(hubBrowserPanel);            
        }
                
        GridBagLayout gblPanel = new GridBagLayout();
        gblPanel.columnWidths = new int[]{0, 0};
        gblPanel.rowHeights = new int[]{0, 0, 0};
        gblPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        hubBrowserPanel.setLayout(gblPanel);
                
        JTabbedPane hubBrowserTreeViewsContainer = new JTabbedPane(SwingConstants.TOP);
        GridBagConstraints gbcHubBrowserTreeViewsContainer = new GridBagConstraints();
        gbcHubBrowserTreeViewsContainer.insets = new Insets(0, 0, 5, 0);
        gbcHubBrowserTreeViewsContainer.fill = GridBagConstraints.BOTH;
        gbcHubBrowserTreeViewsContainer.gridx = 0;
        gbcHubBrowserTreeViewsContainer.gridy = 0;
        
        this.elementDefinitionBrowser = new ObjectBrowser();
        this.elementDefinitionBrowser.setBackground(Color.WHITE);
        hubBrowserTreeViewsContainer.addTab("Element Definitions", ImageLoader.GetIcon(ClassKind.Iteration), this.elementDefinitionBrowser, null);
        
        this.requirementBrowser = new ObjectBrowser();
        hubBrowserTreeViewsContainer.addTab("Requirements", ImageLoader.GetIcon(ClassKind.RequirementsSpecification), this.requirementBrowser, null);
        
        hubBrowserPanel.add(hubBrowserTreeViewsContainer, gbcHubBrowserTreeViewsContainer);
        
        this.mapToNewElementCheckBox = new JCheckBox("Map the current selected row to a new Hub element");
        this.mapToNewElementCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        
        GridBagConstraints gbcMapToNewHubElementCheckBox = new GridBagConstraints();
        gbcMapToNewHubElementCheckBox.anchor = GridBagConstraints.WEST;
        gbcMapToNewHubElementCheckBox.gridx = 0;
        gbcMapToNewHubElementCheckBox.gridy = 1;
        
        hubBrowserPanel.add(this.mapToNewElementCheckBox, gbcMapToNewHubElementCheckBox);
        
        JPanel mappedElementsPanel = new JPanel();

        GridBagLayout mappedElementsPanelLayout = new GridBagLayout();
        gblPanel.columnWidths = new int[]{0, 0};
        gblPanel.rowHeights = new int[]{0, 0, 0};
        gblPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gblPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        mappedElementsPanel.setLayout(mappedElementsPanelLayout);
        
        this.mappedElementListView.setBackground(Color.WHITE);
        
        mappedElementsPanel.add(this.mappedElementListView);
        
        this.mainSplitPane.setRightComponent(mappedElementsPanel);
        GridBagConstraints gbcScrollPane = new GridBagConstraints();
        gbcScrollPane.insets = new Insets(0, 0, 5, 0);
        gbcScrollPane.fill = GridBagConstraints.BOTH;
        gbcScrollPane.gridx = 0;
        gbcScrollPane.gridy = 0;
        mappedElementsPanel.setLayout(new BoxLayout(mappedElementsPanel, BoxLayout.Y_AXIS));
                
        GridBagConstraints gbcSplitPane = new GridBagConstraints();
        gbcSplitPane.fill = GridBagConstraints.BOTH;
        gbcSplitPane.gridx = 0;
        gbcSplitPane.gridy = 0;
        this.contentPanel.add(mainSplitPane, gbcSplitPane);
        
        JPanel buttonPane = new JPanel();
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        GridBagLayout gblButtonPane = new GridBagLayout();
        gblButtonPane.columnWidths = new int[] {1, 588, 1, 1, 1, 1};
        gblButtonPane.rowHeights = new int[]{23, 0};
        gblButtonPane.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
        gblButtonPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        buttonPane.setLayout(gblButtonPane);
        
        resetButton = new JButton("Reset");
        resetButton.setToolTipText("Reset  to the default mapping.");
        GridBagConstraints gbcResetButton = new GridBagConstraints();
        gbcResetButton.insets = new Insets(0, 5, 10, 5);
        gbcResetButton.gridx = 0;
        gbcResetButton.gridy = 0;
        buttonPane.add(resetButton, gbcResetButton);
        
        this.okButton = new JButton("Next");
        this.okButton.setToolTipText("Map the current elements");
        this.okButton.setActionCommand("OK");
        GridBagConstraints gbcOkButton = new GridBagConstraints();
        gbcOkButton.insets = new Insets(0, 5, 10, 5);
        gbcOkButton.gridx = 2;
        gbcOkButton.gridy = 0;
        buttonPane.add(this.okButton, gbcOkButton);
        
        this.cancelButton = new JButton("Cancel");
        this.cancelButton.setToolTipText("Close this dialog and abort the mapping");
        this.cancelButton.setActionCommand("Cancel");
        GridBagConstraints gbcCancelButton = new GridBagConstraints();
        gbcCancelButton.insets = new Insets(0, 5, 10, 5);
        gbcCancelButton.gridx = 3;
        gbcCancelButton.gridy = 0;
        buttonPane.add(this.cancelButton, gbcCancelButton);
        
        this.addComponentListener(new @ExludeFromCodeCoverageGeneratedReport ComponentAdapter() 
        {
            /**
             * Invoked when the component's size changes
             * 
             * @param componentEvent the {@linkplain ComponentEvent}
             */
            @Override
            @ExludeFromCodeCoverageGeneratedReport
            public void componentResized(ComponentEvent componentEvent) 
            {
                super.componentResized(componentEvent);
                ((MappingConfigurationDialog<?,?,?,?>)componentEvent.getComponent()).UpdateSplitPanelsDividerLocation();
            }
        });
    }
    
    /**
     * Binds the {@linkplain #TViewModel} viewModel to the implementing view
     * 
     * @param viewModel the view model to bind
     */
    public void Bind()
    {
        this.elementDefinitionBrowser.SetDataContext(this.dataContext.GetElementDefinitionBrowserViewModel());
        this.requirementBrowser.SetDataContext(this.dataContext.GetRequirementBrowserViewModel());
        this.dstObjectBrowser.SetDataContext(this.dataContext.GetDstObjectBrowserViewModel());
        this.mappedElementListView.SetDataContext(this.dataContext.GetMappedElementListViewViewModel());
        
        this.dataContext.GetSelectedMappedElement().subscribe(x -> 
        {
            if(x != null)
            {
                this.UpdateMapToNewHubElementCheckBoxState(x.GetShouldCreateNewTargetElementValue());
            }
        });
        
        this.mapToNewElementCheckBox.addActionListener(x -> this.dataContext.WhenMapToNewElementCheckBoxChanged(this.mapToNewElementCheckBox.isSelected()));
        
        this.dataContext.GetShouldMapToNewElementCheckBoxBeEnabled()
            .subscribe(x -> this.mapToNewElementCheckBox.setEnabled(x));
        
        this.okButton.addActionListener(x -> this.CloseDialog(true));        
        this.cancelButton.addActionListener(x -> this.CloseDialog(false));
        this.resetButton.addActionListener(x -> this.dataContext.ResetPreMappedThings());
    }

    /**
     * Updates the visual state of the {@linkplain mapToNewHubElementCheckBox} according to the selected mapped element
     * 
     * @param shouldCreateNewTargetElement the new value
     */
    private void UpdateMapToNewHubElementCheckBoxState(boolean shouldCreateNewTargetElement)
    {
        SwingUtilities.invokeLater(() -> this.mapToNewElementCheckBox.setSelected(shouldCreateNewTargetElement));        
    }

    /**
     * Sets the DataContext
     * 
     * @param viewModel the {@link IViewModel} to assign
     */
    public void SetDataContext(TViewModel viewModel)
    {
        this.dataContext = viewModel;
        this.Bind();
    }
    
    /**
    * Gets the DataContext
    * 
    * @return an {@link IViewModel}
    */
    @Override
    public TViewModel GetDataContext()
    {
        return this.dataContext;
    }

    /**
     * Paints the container. This forwards the paint to any lightweight components that are children of this container. 
     * If this method is re-implemented, super.paint(g) should be called so that lightweight components 
     * are properly rendered. If a child component is entirely clipped by the current clipping setting in g, 
     * paint() will not be forwarded to that child.
     * 
     * @param graphics the specified Graphics window
     */
    @Override
    public void paint(Graphics graphics) 
    {
        super.paint(graphics);

        if (!this.hasBeenPaintedOnce) 
        {
            this.hasBeenPaintedOnce = true;
            this.UpdateSplitPanelsDividerLocation();
        }
    }

    /**
     * Updates this view split panels divider location
     */
    private void UpdateSplitPanelsDividerLocation()
    {
        this.mainSplitPane.setDividerLocation(0.5);
        this.browserSplitPane.setDividerLocation(0.5);
    }
}
