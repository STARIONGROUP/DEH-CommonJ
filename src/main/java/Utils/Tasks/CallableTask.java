/*
 * CallableTask.java
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
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@linkplain CallableTask} represents a {@linkplain Callable} that is to be executed in another thread
 */
class CallableTask<TResult> extends Task<TResult>
{
    /**
     * The current class logger
     */
    private Logger logger = LogManager.getLogger();
    
    /**
     * The {@linkplain FutureTask} that is to be executed
     */
    protected FutureTask<TResult> function;

    /**
     * The {@linkplain ObservableTask} that this will return when using the static {@linkplain Task.Run}
     */
    ObservableTask<TResult> ObservableFunction;

    /**
     * The {@linkplain ExecutorService} that will execute the {@linkplain observer} and the {@linkplain function}
     */
    private ExecutorService executor;

    /**
     * the {@linkplain Runnable} observer that watches the execution of the wrapped function
     */
    protected Runnable observer; 

    /**
     * Cancels the on going task execute
     */
    public void Cancel()
    {
        this.ObservableFunction.Task.Status = TaskStatus.Cancelled;
        this.function.cancel(true);
    }

    /**
     * Executes the observer and the function concurrently in a new thread pool
     */
    protected void Execute()
    {
        try
        {
            this.observer = this.GetObserver();
            this.executor = Executors.newFixedThreadPool(2);
            this.executor.submit(this.observer);
            this.executor.submit(this.function);
        }
        catch (Exception exception)
        {
            this.HandleException(exception);
        }
        finally
        {
            this.executor.shutdown();
        }
    }

    /**
     * Gets the observer {@linkplain Runnable} that observe the wrapped function
     * 
     * @return a {@linkplain Runnable}
     */
    protected Runnable GetObserver()
    {
        return () -> 
        {
            this.ObservableFunction.Task.Status = TaskStatus.Running;
            
            try
            {
                this.ObservableFunction.Task.result = this.function.get();
                this.ObservableFunction.Task.Status = TaskStatus.Completed;
                this.ObservableFunction.OnNext();
            }
            catch (InterruptedException | CancellationException cancellationException)
            {
                this.ObservableFunction.Task.Status = TaskStatus.Cancelled;
                this.ObservableFunction.OnNext();
            }
            catch (ExecutionException exception) 
            {
                this.HandleException(exception);
            }
        };
    }
        

    /**
     * Handles {@linkplain Exception} when they occurs while executing the wrapped function 
     * 
     * @param exception the {@linkplain Exception}
     */
    protected void HandleException(Exception exception)
    {
        this.logger.catching(exception);
        this.ObservableFunction.Task.Exception = exception;
        this.ObservableFunction.Task.Status = TaskStatus.Faulted;
        this.ObservableFunction.OnNext();
    }
    
    /**
     * Initializes a new {@linkplain CallableTask}
     * 
     * @param function the {@linkplain Callable} to be called
     * @param clazz the {@linkplain Class} of the return type
     */
    CallableTask(Callable<TResult> function, Class<TResult> clazz)
    { 
        this.ObservableFunction = new ObservableTask<TResult>(this);
        this.function = new FutureTask<TResult>(function);
    }
}
