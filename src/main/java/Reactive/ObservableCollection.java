/*
 * ObservableCollection.java
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

    @Override
    public int size()
    {
        return this.value.size();
    }

    @Override
    public boolean isEmpty()
    {
        return this.value.size() == 0;
    }

    @Override
    public boolean contains(Object object)
    {
        return this.value.contains(object);
    }

    @Override
    public Iterator<TValue> iterator()
    {
        return this.value.iterator();
    }

    @Override
    public Object[] toArray()
    {
        return this.value.toArray();
    }

    @Override
    public <T> T[] toArray(T[] array)
    {
        return this.value.toArray(array);
    }

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

    @Override
    public boolean retainAll(Collection<?> collection)
    {
        return this.value.retainAll(collection);
    }

    @Override
    public void clear()
    {
        this.value.clear();
        this.FiresIsEmpty();
    }
    
    public ObservableCollection()
    {
        this.value = new ArrayList<TValue>();
    }

    @Override
    public boolean addAll(int index, Collection<? extends TValue> c)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public TValue get(int index)
    {
        return this.value.get(index);
    }

    @Override
    public TValue set(int index, TValue element)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void add(int index, TValue element)
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public TValue remove(int index)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int indexOf(Object o)
    {
        return this.value.indexOf(0);
    }

    @Override
    public int lastIndexOf(Object o)
    {
        return this.value.lastIndexOf(o);    }

    @Override
    public ListIterator<TValue> listIterator()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator<TValue> listIterator(int index)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<TValue> subList(int fromIndex, int toIndex)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
