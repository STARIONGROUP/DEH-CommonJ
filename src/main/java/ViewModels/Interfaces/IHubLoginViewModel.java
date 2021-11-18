/*
 * IHubLoginViewModel.java
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
package ViewModels.Interfaces;

import java.awt.event.ActionEvent;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JComboBox;

import org.apache.commons.lang3.tuple.Pair;

import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EngineeringModelSetup;
import cdp4common.sitedirectorydata.IterationSetup;

/**
 * The <code>IHubLoginViewModel</code> if the interface definition for the <code>HubLoginViewModel</code>
 */
public interface IHubLoginViewModel extends IViewModel
{
    /**
     * Gets the addresses
     * 
     * @return a collection of string
     */
    List<String> GetAddresses();
    
    /**
     * Occurs when the addressComboxIsEdited
     * 
     * @param addressComboBox
     * @param actionEvent
     */
    void WhenAddressComboxIsEdited(JComboBox<String> addressComboBox, ActionEvent actionEvent);
    
    /**
     * Opens the {@link Session}
     * 
     * @param address The server address
     * @param password The user password
     * @param login The user login
     */
    void Login(String login, String password, URI address);

    /**
     * Gets the available {@link EngineeringModelSetup}s
     * 
     * @return a {@link Stream} of {@link String}
     */
    Stream<String> GetEngineeringModels();

    /**
     * Gets the available {@link IterationSetup}s
     * 
     * @param selectedItem The selected {@link EngineeringModelSetup}
     * @return a {@link Stream} of {@link String}
     */
    Stream<String> GetIterations(String selectedEngineeringModelSetupName);

    /**
     * Gets all available {@link DomainOfExpertise} and the default one
     * 
     * @param selectedEngineeringModelSetupName The selected {@link EngineeringModelSetup} Name
     * @return a {@link Pair}<{@link Stream}<{@link String}>, {@link String}>
     */
    Pair<Stream<String>, String> GetDomainOfExpertise(String selectedEngineeringModelSetupName);

    /**
     * Gets a value indicating whether the authentication to the server has been approved
     */
    boolean GetIsLoginSuccessful();

    /**
     * Sets the {@link EngineeringModel} loads an {@link Iteration} with the selected {@link DomainOfExpertise}
     * 
     * @param engineeringModelSetupName The selected engineeringModelSetup ShortName
     * @param iterationSetupDisplayString The selected IterationSetup string as displayed in the comboBox
     * @param domainOfExpertiseName The DomainOfExpertise Name
     * @return A value indicating whether the operation went well
     */
    boolean OpenIteration(String engineeringModelSetupName, String iterationSetupDisplayString, String domainOfExpertiseName);

    /**
     * Saves the current Uri in the user preference
     * 
     * @param uri the string uri to save
     */
    void DoSaveTheCurrentSelectedUri(String uri);
}
