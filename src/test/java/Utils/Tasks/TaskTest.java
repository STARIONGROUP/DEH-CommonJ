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

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Utils.Ref;

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
        	await().during(1, TimeUnit.SECONDS).until(() -> true);	
        	return true;
        }, Boolean.class)
        .Observable()
        .subscribe(x -> 
        {
            refCompleted.Set(x.GetResult());   
        });
        
        assertFalse(refCompleted.Get());
        await().atMost(2, TimeUnit.SECONDS).until(() -> refCompleted.Get());
        assertTrue(refCompleted.Get());
    }

    @Test
    void VerifyRunRunnable() throws InterruptedException
    {
        final Ref<Boolean> refCompleted = new Ref<Boolean>(Boolean.class, false);
        
        Task.Run(() -> 
        {
        	await().during(1, TimeUnit.SECONDS).until(() -> true);	
            return;
        })
        .Observable()
        .subscribe(x -> 
        {
            refCompleted.Set(x.GetResult() == TaskStatus.Completed);   
        });
        
        assertFalse(refCompleted.Get());
        await().atMost(2, TimeUnit.SECONDS).until(() -> refCompleted.Get());
        assertTrue(refCompleted.Get());
    }
    
    @Test
    void VerifyFaultedRunnable() throws InterruptedException
    {
        final Ref<TaskStatus> refCompleted = new Ref<TaskStatus>(TaskStatus.class, TaskStatus.Iddle);
        
        Task.Run(() -> 
        {
        	await().during(1, TimeUnit.SECONDS).until(() -> true);	
            throw new NullPointerException();
        })
        .Observable()
        .subscribe(x -> 
        {
            refCompleted.Set(x.GetResult());   
        });
        
        assertEquals(TaskStatus.Iddle, refCompleted.Get());
        await().atMost(2, TimeUnit.SECONDS).until(() -> refCompleted.Get() == TaskStatus.Faulted);
        assertEquals(TaskStatus.Faulted, refCompleted.Get());
    }
    
    @Test
    void VerifyCancel() throws InterruptedException
    {
        final Ref<Boolean> refCompleted = new Ref<Boolean>(Boolean.class, false);
        
        ObservableTask<Boolean> task = Task.Create(() -> 
        {
            System.out.println("called");
            await().during(50, TimeUnit.SECONDS).until(() -> true);
            return true;
        }, Boolean.class);
        
        task.Observable()
        .subscribe(x -> 
        {
            refCompleted.Set(x.GetResult());   
        });
        
        task.Run();
        await().during(1, TimeUnit.SECONDS).until(() -> true);
        task.Cancel();

        await().atMost(2, TimeUnit.SECONDS).until(() -> refCompleted.Get() == null);
        assertEquals(null, refCompleted.Get());
        assertEquals(TaskStatus.Cancelled, task.task.GetStatus());
        assertEquals(null, refCompleted.Get());
    }
}

