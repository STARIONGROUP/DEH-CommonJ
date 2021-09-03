/*
 * HubLoginViewModelTestFixture.java
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
package ViewModels;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.event.ActionEvent;
import java.net.URI;
import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.swing.JComboBox;

import static org.mockito.ArgumentMatchers.*;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import HubController.IHubController;
import Services.UserPreferenceService.IUserPreferenceService;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EngineeringModelSetup;
import cdp4common.sitedirectorydata.IterationSetup;
import cdp4common.sitedirectorydata.Participant;
import cdp4common.sitedirectorydata.Person;
import cdp4common.types.ContainerList;
import cdp4dal.dal.Credentials;

class HubLoginViewModelTestFixture
{

    private IHubController hubController;
    private HubLoginViewModel viewModel;
    private DomainOfExpertise domainOfExpertise;
    private IterationSetup iterationSetup;
    private IUserPreferenceService userPreferenceService;

        @BeforeEach
    void setUp() throws Exception
    {
        this.hubController = mock(IHubController.class);
        
        when(this.hubController.Open(any(Credentials.class))).thenReturn(true);
                
        Person activePerson = new Person();
        this.domainOfExpertise = new DomainOfExpertise();
        this.domainOfExpertise.setName("domain");
        Participant participant = new Participant();
        
        activePerson.setDefaultDomain(this.domainOfExpertise);
        participant.setPerson(activePerson);
        participant.setDomain(new ArrayList<DomainOfExpertise>(Arrays.asList(this.domainOfExpertise)));

        this.iterationSetup = new IterationSetup();
        this.iterationSetup.setIterationNumber(0);
        this.iterationSetup.setFrozenOn(OffsetDateTime.MIN);
        this.iterationSetup.setDescription("description");
        
        ContainerList<IterationSetup> iterations = new ContainerList<IterationSetup>(null);
        iterations.add(iterationSetup);
        
        ContainerList<EngineeringModelSetup> engineeringModelSetups = new ContainerList<EngineeringModelSetup>(null);
        EngineeringModelSetup engineeringModelSetup = new EngineeringModelSetup();
        engineeringModelSetup.setIterationSetup(iterations);
        engineeringModelSetup.setShortName("model");
        ContainerList<Participant> participants = new ContainerList<Participant>(null);
        participants.add(participant);
        engineeringModelSetup.setParticipant(participants);
        engineeringModelSetups.add(engineeringModelSetup);
        EngineeringModelSetup engineeringModelSetup2 = new EngineeringModelSetup();
        engineeringModelSetup2.setShortName("model2");
        engineeringModelSetups.add(engineeringModelSetup2);
        when(this.hubController.GetEngineeringModels()).thenReturn(engineeringModelSetups);
        
        this.userPreferenceService = mock(IUserPreferenceService.class);
        this.viewModel = new HubLoginViewModel(this.hubController, this.userPreferenceService);
        when(this.hubController.GetActivePerson()).thenReturn(activePerson);
    }
    
    @Test
    void VerifyWhenAddressComboxIsEdited()
    {
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.addItem("selected");
        comboBox.setSelectedIndex(0);
        comboBox.setSelectedItem("selected");

        ActionEvent actionEvent = mock(ActionEvent.class);

        assertDoesNotThrow(() -> this.viewModel.WhenAddressComboxIsEdited(comboBox, actionEvent));        
        when(actionEvent.getActionCommand()).thenReturn("comboBoxEdited");
        comboBox.setSelectedIndex(-1);
        comboBox.setSelectedItem("selected1");        
        assertDoesNotThrow(() -> this.viewModel.WhenAddressComboxIsEdited(comboBox, actionEvent));
        comboBox.setSelectedItem(null);
        assertDoesNotThrow(() -> this.viewModel.WhenAddressComboxIsEdited(comboBox, actionEvent));
    }

    @Test
    void VerifyLogin()
    {
        assertFalse(this.viewModel.GetIsLoginSuccessful());
        assertThrows(NullPointerException.class, () -> this.viewModel.Login(null, null, null));
        assertFalse(this.viewModel.GetIsLoginSuccessful());
        assertDoesNotThrow(() -> this.viewModel.Login("admin", "pass", new URI("http://tes.t")));
        assertTrue(this.viewModel.GetIsLoginSuccessful());
    }
    
    @Test
    void VerifyGetEngineerinModels()
    {
        assertEquals(2, this.viewModel.GetEngineeringModels().count());
    }

    @Test
    void VerifyGetIterations()
    {
        assertDoesNotThrow(() -> this.viewModel.GetEngineeringModels());
        assertThrows(NoSuchElementException.class, () -> this.viewModel.GetIterations(""));
        Supplier<Stream<String>> iterations = () -> this.viewModel.GetIterations("model");
        assertEquals(1, iterations.get().count());
        assertEquals(MessageFormat.format("Iteration 0 {0} description",
                OffsetDateTime.MIN.format(DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss"))), iterations.get().findFirst().get());
    }
    
    @Test
    void VerifyGetDomainOfExpertise()
    {
        assertDoesNotThrow(() -> this.viewModel.GetEngineeringModels());
        assertThrows(NoSuchElementException.class, () -> this.viewModel.GetIterations(""));
        Pair<Stream<String>, String> result = this.viewModel.GetDomainOfExpertise("model");
        assertNotNull(result);
        assertEquals(this.domainOfExpertise.getName(), result.getRight());
        assertEquals(this.domainOfExpertise.getName(), result.getLeft().findFirst().get());
    }
    
    @Test
    void VerifyOpenIteration()
    {
        assertDoesNotThrow(() -> this.viewModel.GetEngineeringModels());
        assertDoesNotThrow(() -> this.viewModel.GetIterations("model"));
        assertDoesNotThrow(() -> this.viewModel.GetDomainOfExpertise("model"));
        
        assertFalse(() -> 
            this.viewModel.OpenIteration("model", 
                MessageFormat.format("Iteration 0 {0} description", 
                        OffsetDateTime.MAX.format(DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss"))),
                "domain"));
        when(this.hubController.OpenIteration(
                any(EngineeringModelSetup.class), any(IterationSetup.class), any(DomainOfExpertise.class)))
            .thenReturn(true);

        assertFalse(() -> 
            this.viewModel.OpenIteration("model", 
                MessageFormat.format("Iteration p {1} description",
                        OffsetDateTime.MAX.format(DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss"))),
                "domain"));
        
        assertDoesNotThrow(() -> this.viewModel.GetEngineeringModels());
        assertTrue(() -> 
            this.viewModel.OpenIteration("model", 
                    MessageFormat.format("Iteration 0 {0} description", 
                            OffsetDateTime.MAX.format(DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss"))),
                            "domain"));
        
    }
}
