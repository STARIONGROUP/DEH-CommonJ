/*
 * HubControllerTestFixture.java
 *
 * Copyright (c) 2015-2019 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-CommonJ
 *
 * The DEH-MDSYSML is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-MDSYSML is distributed in the hope that it will be useful,
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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

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

class HubControllerTestFixture
{

    private HubController controller;
    private Session session;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
        this.controller = new HubController();
        this.session = mock(Session.class);
    }

    @Test
    void VerifyGetEngineeringModels()
    {
        assertNull(this.controller.GetEngineeringModels());
        
        this.controller.SetSession(this.session);
        SiteDirectory siteDirectory = new SiteDirectory();
        ContainerList<EngineeringModelSetup> engineeringModelSetups = new ContainerList<EngineeringModelSetup>(siteDirectory);
        engineeringModelSetups.add(new EngineeringModelSetup());
        siteDirectory.setModel(engineeringModelSetups);
        when(this.session.retrieveSiteDirectory()).thenReturn(siteDirectory);
        assertSame(engineeringModelSetups, this.controller.GetEngineeringModels());
    }
    
    @Test
    void VerifyGetActivePerson()
    {
        assertNull(this.controller.GetActivePerson());
        this.controller.SetSession(this.session);
        Person person = new Person();
        when(this.session.getActivePerson()).thenReturn(person);
        assertSame(person, this.controller.GetActivePerson());
    }
    
    @Test
    void VerifyGetIteration()
    {
        this.controller.SetSession(this.session);
        when(this.session.read(any(Iteration.class), any(DomainOfExpertise.class))).thenReturn(CompletableFuture.completedFuture(null));
        
        ImmutableMap<Iteration, Pair<DomainOfExpertise, Participant>> map = ImmutableMap.<Iteration, Pair<DomainOfExpertise, Participant>>builder()
                .put(new Iteration(), new ImmutablePair<DomainOfExpertise, Participant>(new DomainOfExpertise(), new Participant()))
                .build();
        
        when(this.session.getOpenIterations()).thenReturn(map);
        assertDoesNotThrow(() -> this.controller.GetIteration(new Iteration(), new DomainOfExpertise()));
        assertTrue(this.controller.IsSessionOpen());
    }
    
    @Test
    void VerifyReloadOrRefresh()
    {
        this.controller.SetSession(this.session);
        assertThrows(NullPointerException.class, () -> this.controller.Reload());
        assertThrows(NullPointerException.class, () -> this.controller.Refresh());

        when(this.session.reload()).thenReturn(CompletableFuture.completedFuture(null));
        when(this.session.refresh()).thenReturn(CompletableFuture.completedFuture(null));

        assertTrue(this.controller.Reload());
    }
    
    @Test
    void VerifyOpenIteration() throws URISyntaxException
    {
        this.controller.SetSession(this.session);
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
}
