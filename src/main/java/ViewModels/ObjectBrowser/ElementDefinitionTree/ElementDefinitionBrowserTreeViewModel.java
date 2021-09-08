/*
 * ElementDefinitionBrowserViewModel.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Geren�, Alex Vorobiev, Nathanael Smiechowski 
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
package ViewModels.ObjectBrowser.ElementDefinitionTree;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import HubController.IHubController;
import ViewModels.Interfaces.IElementDefinitionBrowserViewModel;
import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;
import ViewModels.ObjectBrowser.Rows.*;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.IterationRowViewModel;
import cdp4common.engineeringmodeldata.Iteration;

public class ElementDefinitionBrowserTreeViewModel implements TreeModel, IElementDefinitionBrowserViewModel
{
    /**
     * The current class logger
     */
    private final Logger logger = LogManager.getLogger();
    
    /**
     * The root node of one {@linkplain ElementDefinitionBrowserTree}
     */
    private IterationRowViewModel iterationRowViewModel;
    
    /**
     * Initializes a new {@linkplain ElementDefinitionBrowserTreeViewModel}
     * 
     * @param iteration the {@linkplain Iteration}
     * @param hubController the {@linkplain IHubController}
     */
    public ElementDefinitionBrowserTreeViewModel(Iteration iteration, IHubController hubController)
    {
        this.iterationRowViewModel = new IterationRowViewModel(iteration);
        hubController.GetIsSessionOpenObservable().subscribe(x -> 
            {
                if(x == false)
                {
                    this.iterationRowViewModel = null;
                }
            });        
    }

    @Override
    public Object getRoot()
    {
        return this.iterationRowViewModel;
    }

    @Override
    public Object getChild(Object rowViewModel, int index)
    {        
        if (rowViewModel instanceof IHaveContainedRows<?>) 
        {            
            return ((IHaveContainedRows<?>)rowViewModel).GetContainedRows().get(index);
        }
        
        return null;
    }

    @Override
    public int getChildCount(Object rowViewModel)
    {                
        if (rowViewModel instanceof IHaveContainedRows<?>)
        {
            return ((IHaveContainedRows<?>)rowViewModel).GetContainedRows().size();
        }
        
        return 0;
    }

    @Override
    public boolean isLeaf(Object rowViewModel)
    {
        return rowViewModel instanceof IHaveContainedRows<?> && ((IHaveContainedRows<?>)rowViewModel).GetContainedRows().isEmpty();
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) { }

    @Override
    public int getIndexOfChild(Object parent, Object child)
    {
        if(parent == null || child == null)
        {
            return 0;
        }
        
        return ((IHaveContainedRows<?>)parent).GetContainedRows().indexOf(child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l)
    {
        // TODO Auto-generated method stub        
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l)
    {
        // TODO Auto-generated method stub
    }
}
