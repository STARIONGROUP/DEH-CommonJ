/*
 * ObservableValue.java
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

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * The {@linkplain ObservableValue} class provides an easier way to observe changes on properties
 * It provides a wrapper for the value as well as a observable that can be subscribed to
 * 
 * @param TValue the type of the value this holds
 */
public class ObservableValue<TValue>
{
    /**
     * Holds the current generic {@linkplain Type} of this {@linkplain ObservableValue}
     */
    protected Class<?> type;
    
    /**
     * Gets the enclosed type of this {@linkplain ObservableValue}
     * 
     * @return the {@linkplain Type}
     */
    public Class<?> GetType()
    {
        return this.type;
    }


    /**
     * Sets the enclosed type of this {@linkplain ObservableValue}
     * 
     * @param the {@linkplain Type}
     */
    protected void SetType(Class<?> type)
    {
        this.type = type;       
    }
        
    /**
     * The {@linkplain TValue} value
     */
    protected TValue value;
    
    /**
     * The {@linkplain PublisherSubject} that serves to raise the OnNext event
     */
    protected PublishSubject<TValue> subject = PublishSubject.create();

    /**
     * Gets the {@linkplain TValue}
     * 
     * @return the value
     */
    public TValue Value() 
    {
        return value;
    }

    /**
     * Sets the value
     * 
     * @param value the {@linkplain TValue} to assign
     */
    public void Value(TValue value) 
    {
        try
        {
            if(value == null)
            {
                subject.onNext(this.value);
            }
            
            this.value = value;
            subject.onNext(value);
            
        } 
        catch (Exception exception)
        {
            PublishSubject<TValue> newSubject = GetNewSubject();
            this.subject.onErrorResumeNext(newSubject);
            this.subject.onError(exception);
            this.subject = newSubject;
        }        
    }

    /**
     * Gets the {@linkplain Observable} that can be subscribed to
     * 
     * @return an {@linkplain Observable}
     */
    public Observable<TValue> Observable() 
    {
        return subject.hide();
    }
    
    /**
     * Initializes a new {@linkplain ObservableValue} with a initial value for the {@linkplain value}
     * 
     * @param initialValue the initial value for the {@linkplain value}
     * @param type the enclosed type of this {@linkplain ObservableValue}
     */
    public ObservableValue(TValue initialValue, Class<TValue> type)
    {
        this(type);
        this.Value(initialValue);
    }

    /**
     * Initializes a {@linkplain ObservableValue}
     * 
     * @param type the enclosed type of this {@linkplain ObservableValue}
     */
    public ObservableValue(Class<TValue> type)
    {
        this.type = type;
    }
    
    /**
     * Initializes a default {@linkplain ObservableValue}
     */
    protected ObservableValue()
    {
    }

    /**
     * Gets a new {@linkplain PublishSubject}
     * 
     * @return a {@linkplain PublishSubject}
     */
    private PublishSubject<TValue> GetNewSubject()
    {
        return PublishSubject.create();
    }
}
