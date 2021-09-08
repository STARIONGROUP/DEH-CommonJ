/*
 * ObservableCollection.java
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
package Reactive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * The {@linkplain ObservableCollection} class provides an easier way to observe changes on properties
 * It provides a wrapper for the value as well as a observable that can be subscribed to
 * 
 * @param TValue the type of the collection this holds
 */
public class ObservableCollection<TValue> extends ObservableValue<ArrayList<TValue>> implements List<TValue>, RandomAccess, Cloneable
{
    /**
     * The {@linkplain PublisherSubject} that serves to raise the OnNext event when an item is added to the collection
     */
    protected PublishSubject<TValue> itemAdded = PublishSubject.create();
    
    /**
     * The {@linkplain PublisherSubject} that serves to raise the OnNext event when an item is removed from the collection
     */
    protected PublishSubject<TValue> itemRemoved = PublishSubject.create();

    /**
     * The {@linkplain PublisherSubject} that serves to raise the OnNext event when the collection becomes empty
     */
    protected PublishSubject<Boolean> isEmpty = PublishSubject.create();

    /**
     * Field holding a value indicating whether the collection is empty or not
     */
    private boolean isEmptyValue;
    
    /**
     * Adds a new {@linkplain TValue} to the {@linkplain ObservableCollection}
     * 
     * @param value the {@linkplain TValue}
     * @return An value indication whether the addition went through
     */
    @Override
    public boolean add(TValue value)
    {
        boolean result = this.value.add(value);
        this.itemAdded.onNext(value);
        this.FiresIsEmpty();
        return result;
    }

    /**
     * Removes the {@linkplain Object} from the {@linkplain ObservableCollection}
     * Please use the {@linkplain Remove}
     * 
     * @param value the {@linkplain Object}
     * @return An value indication whether the removal went through
     */
    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object value)
    {
        return this.Remove((TValue) value);
    }
    
    /**
     * Removes the {@linkplain TValue} from the {@linkplain ObservableCollection}
     * 
     * @param value the {@linkplain TValue}
     * @return An value indication whether the removal went through
     */
    public boolean Remove(TValue value)
    {
        boolean result = this.value.remove(value);
        
        if(result)
        {
            this.itemRemoved.onNext(value);
            this.FiresIsEmpty();
        }
        
        return result;
    }
    
    /**
     * Fires the OnNext on the {@linkplain isEmpty}
     */
    private void FiresIsEmpty()
    {
        boolean newValue = this.value.isEmpty();
        if (this.isEmptyValue != newValue)
        {
            this.isEmptyValue = newValue;
            this.isEmpty.onNext(newValue);
        }
    }
    
    /**
     * Gets the {@linkplain Observable} that fires when ever the collection gets one new item
     * 
     * @return An {@linkplain Observable} of {@linkplain TValue}
     */
    public Observable<TValue> ItemAdded()
    {
        return itemAdded.hide();
    }
    
    /**
     * Gets the {@linkplain Observable} that fires when ever the collection loses one item
     * 
     * @return An {@linkplain Observable} of {@linkplain TValue}
     */
    public Observable<TValue> ItemRemoved()
    {
        return itemRemoved.hide();
    }
    
    /**
     * Gets the {@linkplain Observable} that fires when ever the collection emptiness state changes 
     * 
     * @return An {@linkplain Observable} of {@linkplain Boolean}
     */
    public Observable<Boolean> IsEmpty()
    {
        return isEmpty.hide();
    }

    /**
     * Gets the number of element of this collection
     * 
     * @return the number of element as {@linkplain int}
     */
    @Override
    public int size()
    {
        return this.value.size();
    }

    /**
     * Gets a value indicating whether this collection has not element
     * 
     * @return an assert
     */
    @Override
    public boolean isEmpty()
    {
        return this.value.size() == 0;
    }

    /**
     * Asserts that this collection contains the provided {@linkplain Object}
     * 
     * @param object the {@linkplain Object} that could be present in this collection
     * @return an assert
     */
    @Override
    public boolean contains(Object object)
    {
        return this.value.contains(object);
    }

    /**
     * Gets an {@linkplain Iterator} from this collection
     * 
     * @return a {@linkplain Iterator} of {@linkplain TValue}
     */
    @Override
    public Iterator<TValue> iterator()
    {
        return this.value.iterator();
    }

    /**
     * Converts this collection to an array
     * 
     * @return an array of {@linkplain Object}
     */
    @Override
    public Object[] toArray()
    {
        return this.value.toArray();
    }

    /**
     * Converts this collection to an array of the type provided by {@linkplain T}
     * 
     * @param TObject the runtime type of the array to contain the collection
     * @param array an initial array used to provided the type and an array of start, 
     * if it is not big enough an other one of the same type will be return
     * @return an array of {@linkplain T}
     */
    @Override
    public <T> T[] toArray(T[] array)
    {
        return this.value.toArray(array);
    }

    /**
     * Verify that the items in the provided {@linkplain Collection} are all contained also in ths collection
     * 
     *  @param collection the {@linkplain Collection} of item to check existence
     *  @return an assert
     */
    @Override
    public boolean containsAll(Collection<?> collection)
    {
        boolean result = !(collection.isEmpty() || this.value.isEmpty());
        
        for (Object object : collection)
        {
            result &= this.contains(object);
        }
        
        return result;
    }

    /**
     * Adds all the items in the provided {@linkplain collection} to this collection.
     * It fire the {@linkplain itemAdded} {@linkplain Observable}
     * 
     * @param collection the {@linkplain Collection} of item to add
     * @return an assert indicating whether all items were added successfully
     */
    @Override
    public boolean addAll(Collection<? extends TValue> collection)
    {
        boolean result = true;
        
        for (TValue value : collection)
        {
            result &= this.add(value);
        }
        
        return result;
    }

    /**
     * Removes all the items in the provided {@linkplain collection} to this collection.
     * It fire the {@linkplain itemRemoved} {@linkplain Observable}
     * 
     * @param collection the {@linkplain Collection} of item to remove
     * @return an assert indicating whether all items were removed successfully
     */
    @Override
    public boolean removeAll(Collection<?> collection)
    {
        boolean result = true;
        
        for (Object value : collection)
        {
            result &= this.remove(value);
        }
        
        return result;
    }

    /**
     * Retains only the elements in this list that are contained in the specified collection. 
     * In other words, removes from this list all of its elements that are not contained in the specified collection.
     * 
     * @param collection the {@linkplain Collection} of item to retain
     * @return a {@linkplain boolean} holding true if this list changed as a result of the call
     */
    @Override
    public boolean retainAll(Collection<?> collection)
    {
        return this.value.retainAll(collection);
    }

    /**
     * Clear this collection. It fires the {@linkplain isEmpty}     * 
     */
    @Override
    public void clear()
    {
        this.value.clear();
        this.FiresIsEmpty();
    }
    
    /**
     * Initializes a new {@linkplain ObservableCollection} with an empty {@linkplain Collection}
     */
    public ObservableCollection()
    {
        this.value = new ArrayList<TValue>();
    }

    /**
     * Inserts all of the elements in the specified collection into this. list at the specified position.
     * Does not fire the {@linkplain itemAdded} {@linkplain Observable}
     * 
     * @param collection the {@linkplain Collection} of item to add
     * @param index the {@linkplain index} where to add the new items
     * @return an assert indicating whether all items were added successfully
     */
    @Override
    public boolean addAll(int index, Collection<? extends TValue> collection)
    {
        return this.value.addAll(index, collection);
    }

    /**
     * Gets the item located at the specified index
     * 
     * @param index the index where the item to return is located
     * @return the value extracted from this collection
     */
    @Override
    public TValue get(int index)
    {
        return this.value.get(index);
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * 
     * @param index the index where the old item is located
     * @return the old value removed from this collection
     */
    @Override
    public TValue set(int index, TValue element)
    {
        return this.value.set(index, element);
    }

    /**
     * Inserts the specified element at the specified position in this collection
     * 
     * @param index the index at which the specified element is to be inserted
     * @param element the element to be added
     */
    @Override
    public void add(int index, TValue element)
    {
        this.value.add(index, element);
    }

    /**
     * Remove the element at the specified position in this collection
     * 
     * @param index the index at which the specified element is to be removed
     * @return the value removed
     */
    @Override
    public TValue remove(int index)
    {
        return this.value.remove(index);
    }

    /**
     * Gets the index of the provided element. Return -1 if not found
     * 
     * @param the {@linkplain Object} element to find the index of
     * @return the 0 based index
     */
    @Override
    public int indexOf(Object element)
    {
        return this.value.indexOf(element);
    }

    /**
     * Returns the index of the last occurrence of the specified element in this collection
     * 
     * @param element the {@linkplain Object} element to find the index of
     * @return the 0 based index
     */
    @Override
    public int lastIndexOf(Object element)
    {
        return this.value.lastIndexOf(element);    
    }

    /**
     * Returns a list iterator over the elements in this list
     * 
     * @return a list iterator over the elements in this list
     */
    @Override
    public ListIterator<TValue> listIterator()
    {
        return this.value.listIterator();
    }

    /**
     * Returns a list iterator over the elements in this list starting at the specified position
     * 
     * @return a list iterator over the elements in this list
     */
    @Override
    public ListIterator<TValue> listIterator(int index)
    {
        return this.value.listIterator(index);
    }

    /**
     * Gets a subsequence of item from this collection
     * 
     *  @param fromIndex the lower bound index
     *  @param toIndex the upper bound index
     */
    @Override
    public List<TValue> subList(int fromIndex, int toIndex)
    {
        return this.value.subList(fromIndex, toIndex);
    }
}
