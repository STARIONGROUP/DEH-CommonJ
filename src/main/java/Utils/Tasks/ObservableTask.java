/*
 * ObservableTask.java
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
package Utils.Tasks;

import java.util.RandomAccess;

import Reactive.ObservableValue;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * The {@linkplain ObservableTask} is the observable wrapper used in {@linkplain Task}
 */
public class ObservableTask<TResult>
{    
    /**
     * The {@linkplain TValue} result of the {@linkplain Task}
     */
    protected Task<TResult> Task;
    
    /**
     * The {@linkplain PublisherSubject} that serves to raise the OnNext event
     */
    protected PublishSubject<Task<TResult>> subject = PublishSubject.create();

    /**
     * Initializes a new {@linkplain ObservableTask}
     *  
     * @param task the wrapped {@linkplain Task}
     */
    ObservableTask(Task<TResult> task)
    {
        this.Task = task;
    }
        
    /**
     * Gets the {@linkplain Observable} that can be subscribed to
     * 
     * @return an {@linkplain Observable}
     */
    public Observable<Task<TResult>> Observable()
    {
        return subject.hide();
    }   
    
    /**
     * Raises the onNext
     * 
     * @param Task the {@linkplain TValue} to assign
     */
    void OnNext() 
    {
        subject.onNext(this.Task);     
    }
    
    /**
     * Cancels this on going task execute
     */
    public void Cancel()
    {
        this.Task.Cancel();
    }

    /**
     * Runs this wrapped {@linkplain Task}
     */
    public void Run()
    {
        this.Task.Execute();
    }
}
