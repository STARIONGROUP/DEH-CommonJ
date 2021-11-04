/*
 * Task.java
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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import Reactive.ObservableValue;
import io.reactivex.Observable;

/**
 * The {@linkplain Task} class is the base abstract class for {@linkplain java.util.concurrent.Callable} and {@linkplain Runnable}
 * It also provides static utility class that helps running {@linkplain java.util.concurrent.Callable} and {@linkplain Runnable} asynchronously
 * 
 * @param <TResult> the type of result this returns
 */
public abstract class Task<TResult>
{

    /**
     * Initializes a {@linkplain Task} that is ready to run asynchronously a 
     * {@linkplain java.util.concurrent.Callable} and returns a {@linkplain ObservableTask} that yields T values
     * 
     * @param <T> The type the {@linkplain java.util.concurrent.Callable} returns
     * @param function the {@linkplain java.util.concurrent.Callable}
     * @param clazz the {@linkplain Class} of type T
     * @return an {@linkplain Observable} of T
     */
    public static <T> ObservableTask<T> Create(Callable<T> function, Class<T> clazz)
    {
        CallableTask<T> task = new CallableTask<T>(function, clazz);
        return task.ObservableFunction;
    }
    
    /**
     * Runs asynchronously a {@linkplain java.util.concurrent.Callable} and returns a {@linkplain ObservableTask} that yields T values
     * 
     * @param <T> The type the {@linkplain java.util.concurrent.Callable} returns
     * @param function the {@linkplain java.util.concurrent.Callable}
     * @param clazz the {@linkplain Class} of type T
     * @return an {@linkplain Observable} of T
     */
    public static <T> ObservableTask<T> Run(Callable<T> function, Class<T> clazz)
    {
        CallableTask<T> task = new CallableTask<T>(function, clazz);
        task.Execute();
        return task.ObservableFunction;
    }

    /**
     * Runs asynchronously a {@linkplain Runnable} and returns a {@linkplain ObservableTask} that yields {@linkplain TaskStatus} values
     * 
     * @param function the {@linkplain Runnable}
     * @return an {@linkplain Observable} of {@linkplain TaskStatus}
     */
    public static ObservableTask<TaskStatus> Run(Runnable function)
    {
        RunnableTask task = new RunnableTask(function);
        task.Execute();        
        return task.ObservableFunction;
    }
    
    /**
     * The result of the Task
     */
    protected TResult result;
    
    /**
     * Gets the Result of the {@linkplain Task}
     * 
     * @return a {@linkplain TResult}
     */
    public TResult GetResult()
    {
        return this.result;
    }
    
    /**
     * The {@linkplain TaskStatus}
     */
    protected TaskStatus Status;

    /**
     * Gets the {@linkplain TaskStatus}
     * 
     * @return a {@linkplain TaskStatus}
     */
    public TaskStatus GetStatus()
    {
        return this.Status;  
    }
    
    /**
     * The {@linkplain Exception}
     */
    protected Exception Exception;

    /**
     * Gets the {@linkplain Exception} if it occurs
     * 
     * @return a {@linkplain Exception}
     */
    public Exception GetException()
    {
        return this.Exception;  
    }
    
    /**
     * Cancels the on going task execute
     */
    abstract void Cancel();
    
    /**
     * Runs this {@linkplain Task}
     */
    abstract void Execute();
}
