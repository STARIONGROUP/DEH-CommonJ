/*
 * HubControllerTest.java
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
package HubController;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import Services.NavigationService.INavigationService;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EngineeringModelSetup;
import cdp4common.sitedirectorydata.IterationSetup;
import cdp4common.sitedirectorydata.Participant;
import cdp4common.sitedirectorydata.Person;
import cdp4common.sitedirectorydata.SiteDirectory;
import cdp4common.types.ContainerList;
import cdp4dal.Assembler;
import cdp4dal.Session;
import cdp4dal.dal.Credentials;

class HubControllerTest
{
    private HubController controller;
    private Session session;
    private INavigationService navigationService;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
        this.navigationService = mock(INavigationService.class);
        this.controller = new HubController(this.navigationService);
        this.session = mock(Session.class);
    }

    @Test
    void VerifyGetEngineeringModels() throws Exception
    {
        assertNull(this.controller.GetEngineeringModels());
        
        this.SetSession();
        SiteDirectory siteDirectory = new SiteDirectory();
        ContainerList<EngineeringModelSetup> engineeringModelSetups = new ContainerList<EngineeringModelSetup>(siteDirectory);
        engineeringModelSetups.add(new EngineeringModelSetup());
        siteDirectory.setModel(engineeringModelSetups);
        when(this.session.retrieveSiteDirectory()).thenReturn(siteDirectory);
        assertSame(engineeringModelSetups, this.controller.GetEngineeringModels());
    }
    
    @Test
    void VerifyGetActivePerson() throws Exception
    {
        assertNull(this.controller.GetActivePerson());
        this.SetSession();
        Person person = new Person();
        when(this.session.getActivePerson()).thenReturn(person);
        assertSame(person, this.controller.GetActivePerson());
    }
    
    @Test
    void VerifyGetIteration() throws Exception
    {
        this.SetSession();
        when(this.session.read(any(Iteration.class), any(DomainOfExpertise.class))).thenReturn(CompletableFuture.completedFuture(null));
        
        ImmutableMap<Iteration, Pair<DomainOfExpertise, Participant>> map = ImmutableMap.<Iteration, Pair<DomainOfExpertise, Participant>>builder()
                .put(new Iteration(), new ImmutablePair<DomainOfExpertise, Participant>(new DomainOfExpertise(), new Participant()))
                .build();
        
        when(this.session.getOpenIterations()).thenReturn(map);
        assertDoesNotThrow(() -> this.controller.GetIteration(new Iteration(), new DomainOfExpertise()));
        assertTrue(this.controller.GetIsSessionOpen());
    }
    
    @Test
    void VerifyReloadOrRefresh() throws Exception
    {
        this.SetSession();
        assertThrows(NullPointerException.class, () -> this.controller.Reload());
        assertThrows(NullPointerException.class, () -> this.controller.Refresh());

        when(this.session.reload()).thenReturn(CompletableFuture.completedFuture(null));
        when(this.session.refresh()).thenReturn(CompletableFuture.completedFuture(null));

        assertTrue(this.controller.Reload());
        assertTrue(this.controller.Refresh());
    }
    
    @Test
    void VerifyOpenIteration() throws Exception
    {
        this.SetSession();
        Assembler assembler = new Assembler(new URI("http://tes.t"));
        when(this.session.getAssembler()).thenReturn(assembler);
        when(this.session.getCredentials()).thenReturn(new Credentials("a", "p", new URI("http://tes.t"), null));
        assertDoesNotThrow(() -> this.controller.OpenIteration(new EngineeringModelSetup(), new IterationSetup(), new DomainOfExpertise()));
    }
    
    @Test
    void VerifyGetCurrentDomainOfexpertise()
    {
        assertNull(this.controller.GetCurrentDomainOfExpertise());
    }
    
    @Test
    void VerifyGetDataSourceUri() throws Exception
    {
        assertEquals("", this.controller.GetDataSourceUri());
        this.SetSession();
        when(this.session.getDataSourceUri()).thenReturn("t");
        assertEquals("t", this.controller.GetDataSourceUri());
    }
    
    @Test
    void VerifyClose() throws Exception
    {
        this.controller.Close();
        this.SetSession();
        Method setIsSessionOpenMethod = HubController.class.getDeclaredMethod("SetIsSessionOpen", Boolean.class);
        setIsSessionOpenMethod.setAccessible(true);
        setIsSessionOpenMethod.invoke(this.controller, true);
        assertTrue(this.controller.GetIsSessionOpen());
        assertDoesNotThrow(() -> this.controller.Close());
        assertTrue(this.controller.GetIsSessionOpen());        
        when(this.session.close()).thenReturn(CompletableFuture.completedFuture(null)); 
        assertDoesNotThrow(() -> this.controller.Close());       
    }
    
    private void SetSession() throws Exception
    {
        try
        {
            Field sessionField = HubController.class.getDeclaredField("session");
            sessionField.setAccessible(true);
            sessionField.set(this.controller, this.session);     
        } 
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exception)
        {
            exception.printStackTrace();
            throw exception;
        }
    }
}
