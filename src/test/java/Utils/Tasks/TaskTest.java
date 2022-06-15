/*
 * TaskTest.java
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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Utils.Ref;
import io.reactivex.disposables.Disposable;

class TaskTest
{
    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
    }
    
    @Test
    void VerifyRunCallable() throws InterruptedException
    {
        final Ref<Boolean> refCompleted = new Ref<Boolean>(Boolean.class, false);
        
        Task.Run(() ->
        {
            try
            {
                Thread.sleep(1 * 1000);
                return true;
            } 
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
                return false;
            }            
        }, Boolean.class)
        .Observable()
        .subscribe(x -> 
        {
            refCompleted.Set(x.GetResult());   
        });
        
        assertFalse(refCompleted.Get());
        Thread.sleep(2 * 1000);
        assertTrue(refCompleted.Get());
    }

    @Test
    void VerifyRunRunnable() throws InterruptedException
    {
        final Ref<Boolean> refCompleted = new Ref<Boolean>(Boolean.class, false);
        
        Task.Run(() -> 
        {
            try
            {
                Thread.sleep(1 * 1000);
                return;
            } 
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }            
        })
        .Observable()
        .subscribe(x -> 
        {
            refCompleted.Set(x.GetResult() == TaskStatus.Completed);   
        });
        
        assertFalse(refCompleted.Get());
        Thread.sleep(2 * 1000);
        assertTrue(refCompleted.Get());
    }
    
    @Test
    void VerifyFaultedRunnable() throws InterruptedException
    {
        final Ref<TaskStatus> refCompleted = new Ref<TaskStatus>(TaskStatus.class, TaskStatus.Iddle);
        
        Task.Run(() -> 
        {
            try
            {
                Thread.sleep(1 * 1000);
                throw new NullPointerException();
                
            } 
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
            }
        })
        .Observable()
        .subscribe(x -> 
        {
            refCompleted.Set(x.GetResult());   
        });
        
        assertEquals(TaskStatus.Iddle, refCompleted.Get());
        Thread.sleep(2 * 1000);
        assertEquals(TaskStatus.Faulted, refCompleted.Get());
    }
    
    @Test
    void VerifyCancel() throws InterruptedException
    {
        final Ref<Boolean> refCompleted = new Ref<Boolean>(Boolean.class, false);
        
        ObservableTask<Boolean> task = Task.Create(() -> 
        {
            try
            {
                System.out.println("called");
                Thread.sleep(50 * 1000);
                return true;
            } 
            catch (InterruptedException exception)
            {
                exception.printStackTrace();
                return false;
            } 
        }, Boolean.class);
        
        task.Observable()
        .subscribe(x -> 
        {
            refCompleted.Set(x.GetResult());   
        });
        
        task.Run();
        Thread.sleep(1000);
        task.Cancel();

        Thread.sleep(1000);
        assertEquals(null, refCompleted.Get());
        assertEquals(TaskStatus.Cancelled, task.Task.GetStatus());
        assertEquals(null, refCompleted.Get());
    }
}

