/*
 * BrowserTreeBaseViewModel.java
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
package ViewModels.ObjectBrowser;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import ViewModels.ObjectBrowser.Interfaces.IHaveContainedRows;

/**
 * The {@linkplain BrowserTreeBaseViewModel} is the base class for all tree model
 */
public abstract class BrowserTreeBaseViewModel implements TreeModel
{
    /**
     * Gets the children of the specified {@linkplain rowViewModel} at the specified {@linkplain index}
     * 
     * @param rowViewModel the row view model to get the children from
     * @param index the index of the children to get
     * @return an {@linkplain Object} representing the children, returns null if the row has no child
     */
    @Override
    public Object getChild(Object rowViewModel, int index)
    {        
        if (rowViewModel instanceof IHaveContainedRows<?>) 
        {            
            return ((IHaveContainedRows<?>)rowViewModel).GetContainedRows().get(index);
        }
        
        return null;
    }

    /**
     * Gets the number of children the provided {@linkplain rowViewModel} has.
     * This method is used to get all the child with {@linkplain getChild()}
     * 
     * @param rowViewModel the row view model to get child count from
     * @return the number of children of the row view model
     */
    @Override
    public int getChildCount(Object rowViewModel)
    {
        if (rowViewModel instanceof IHaveContainedRows<?>)
        {
            return ((IHaveContainedRows<?>)rowViewModel).GetContainedRows().size();
        }
        
        return 0;
    }

    /**
     * Gets a value indicating whether the {@linkplain rowViewModel} is a leaf. 
     * e.g. it will return true if the row does not have any children or cannot have any children.
     * This method is used by the tree model to retrieve the children of each row
     * 
     * @param rowViewModel the row view model to verify
     * @return a {@linkplain boolean}
     */
    @Override
    public boolean isLeaf(Object rowViewModel)
    {
        return rowViewModel instanceof IHaveContainedRows<?> && ((IHaveContainedRows<?>)rowViewModel).GetContainedRows().isEmpty();
    }

    /**
     * Messaged when the user has altered the value for the item identified by path to newValue.
     * If newValue signifies a truly new value, the model should post a treeNodesChanged event.
     * 
     * @param path the path to the node that the user has altered
     * @param newValue the new value from the TreeCellEditor
     */
    @Override
    public void valueForPathChanged(TreePath path, Object newValue) { }

    /**
     * Gets the index of child in parent. If either parent or child is null, returns -1.
     * If either parent or child don't belong to this tree model, returns -1.
     * 
     * @param parent the node in the tree
     * @param child the node child node
     * @return the index of the child
     */
    @Override
    public int getIndexOfChild(Object parent, Object child)
    {
        if(parent == null || child == null)
        {
            return 0;
        }
        
        return ((IHaveContainedRows<?>)parent).GetContainedRows().indexOf(child);
    }

    /**
     * Adds a listener for the TreeModel Event posted after the tree changes.
     * 
     * @param listner the listener to add
     */
    @Override
    public void addTreeModelListener(TreeModelListener listener) { }

    /**
     * Removes a listener for the TreeModel Event posted after the tree changes.
     * 
     * @param listner the listener to remove
     */
    @Override
    public void removeTreeModelListener(TreeModelListener listener) { }
}
