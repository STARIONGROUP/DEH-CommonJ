/*
 * RunnableTask.java
 *
 * Copyright (c) 2020-2024 Starion Group S.A.
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

import java.util.concurrent.Executors;

/**
 *The {@linkplain RunnableTask} is a specialization of {@linkplain CallableTask} of {@linkplain TaskStatus}
 */
class RunnableTask extends CallableTask<TaskStatus>
{
   /**
    * Initializes a new {@linkplain RunnableTask}.
    * The constructor visibility is limited to package
    * 
    * @param function the {@linkplain Runnable}
    */
    RunnableTask(Runnable function)
    {
        super(Executors.callable(function, TaskStatus.Completed), TaskStatus.class);
    }

    /**
     * Handles the exception thrown while executing the {@linkplain Runnable}.
     * As a difference with the base {@linkplain HandleException} is that the {@linkplain Runnable} one calls 
     * a <code>OnNext()</code> on the {@linkplain Observable}
     * 
     * @param exception the {@linkplain Exception}
     */
    @Override
    protected void HandleException(Exception exception)
    {
        this.observableFunction.task.result = TaskStatus.Faulted;
        super.HandleException(exception);
    }
}
