/*
 * HubLoginViewModel.java
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

import static Utils.Operators.Operators.AreTheseEquals;

import java.awt.event.ActionEvent;
import java.net.URI;
import java.text.MessageFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JComboBox;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import HubController.IHubController;
import Services.UserPreferenceService.IUserPreferenceService;
import Services.UserPreferenceService.SavedServerConnection;
import ViewModels.Interfaces.IHubLoginViewModel;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EngineeringModelSetup;
import cdp4common.sitedirectorydata.IterationSetup;
import cdp4common.sitedirectorydata.Participant;
import cdp4dal.dal.Credentials;

/**
 * The HubLoginViewModel is main kind of view model for {@link HubLogin}
 */
public class HubLoginViewModel implements IHubLoginViewModel
{
    /**
     * The current class logger
     */
    private final Logger logger = LogManager.getLogger();
    
    /**
     * The {@link IHubController}
     */
    private IHubController hubController;
    
    /**
     * The {@linkplain IUserPreferenceService}
     */
    private IUserPreferenceService userPreferenceService;
    
    /**
     * Backing field for {@link GetIsLoginSuccessful}
     */
    private boolean isLoginSuccessful;
    
    /**
     * The default {@link DomainOfExpertise} 
     */
    private DomainOfExpertise defaultDomain;
    
    /**
     * The {@link GetEngineeringModelSetups}
     */
    private Supplier<Stream<DomainOfExpertise>> domains;
    
    /**
     * The {@link GetEngineeringModelSetups}
     */
    private Supplier<Stream<IterationSetup>> iterationSetups;
    
    /**
     * The {@link GetEngineeringModelSetups}
     */
    private Supplier<Stream<EngineeringModelSetup>> engineeringModelSetups;

    /**
     * Gets an assert indicating whether the authentication on the server went OK
     */
    @Override
    public boolean GetIsLoginSuccessful()
    {
        return this.isLoginSuccessful;    
    }
    
    /**
     * Backing field for {@link GetAddresses}
     */
    private List<String> addresses = new ArrayList<>();
    
    /**
     * Get the collection of server addresses
     * 
     * @return a Collection of string
     */
    public List<String> GetAddresses()
    {
        return this.addresses;
    }
    
    /** 
     * Initializes a new {@link HubLoginViewModel}
     * 
     * @param hubController the {@linkplain IHubController}
     * @param userPreferenceService the {@linkplain IUserPreferenceService}
     */
    public HubLoginViewModel(IHubController hubController, IUserPreferenceService userPreferenceService)
    {
        this.hubController = hubController;
        this.userPreferenceService = userPreferenceService;
        
        this.addresses.addAll(this.userPreferenceService.GetUserPreference().savedServerConections.stream().map(x -> x.uri).collect(Collectors.toList()));        
    }
    
    /**
     * Occurs when the address ComboBox is edited
     * 
     * @param addressComboBox the {@link JComboBox}
     * @param e the {@link ActionEvent}
     */
    public void WhenAddressComboxIsEdited(JComboBox<String> addressComboBox, ActionEvent e)
    {
        int index = addressComboBox.getSelectedIndex();
        String selectedItem = (String)addressComboBox.getSelectedItem();
        if(index >= 0)
        {
            addressComboBox.setSelectedIndex(index);
        }
        else if("comboBoxEdited".equals(e.getActionCommand())) 
        {
            int indexInSource = this.addresses.indexOf(selectedItem);
            if (indexInSource > -1)
            {
                addressComboBox.setSelectedIndex(indexInSource);   
            }
            else
            {
                addressComboBox.addItem(selectedItem);
                addressComboBox.setSelectedItem(selectedItem);  
                this.addresses.add(selectedItem);
            }
        }               
    }
    
    /**
     * Saves the current Uri in the user preference
     * 
     * @param uri the string uri to save
     */
    @Override
    public void DoSaveTheCurrentSelectedUri(String uri)
    {
        this.userPreferenceService.GetUserPreference().savedServerConections.add(new SavedServerConnection(uri));
        this.userPreferenceService.Save();
    }
    
    /**
     * Opens the {@link Session}
     * 
     * @param address The server address
     * @param password The user password
     * @param login The user login
     */
    @Override
    public void Login(String login, String password, URI address)
    {
        this.isLoginSuccessful = this.hubController.Open(new Credentials(login, password, address, null));
    }
    
    /**
     * Gets the available {@link EngineeringModelSetup}s
     * 
     * @return a {@link Stream} of {@link String}
     */
    @Override
    public Stream<String> GetEngineeringModels()
    {
        this.engineeringModelSetups = () -> this.hubController.GetEngineeringModels().stream().sorted((model0, model1) 
        		-> model0.getShortName().compareToIgnoreCase(model1.getShortName()));
        
        return this.engineeringModelSetups.get().map(x -> x.getShortName());
    }

    /**
     * Gets the available {@link IterationSetup}s
     * 
     * @param selectedItem The selected {@link EngineeringModelSetup}
     * @return a {@link Stream} of {@link String}
     */
    @Override
    public Stream<String> GetIterations(String selectedEngineeringModelSetupName)
    {
        EngineeringModelSetup engineeringModelSetup = this.engineeringModelSetups.get()
                .filter(x -> x.getShortName().equals(selectedEngineeringModelSetupName))
                .findFirst()
                .get();
        
        this.iterationSetups = () -> engineeringModelSetup
            .getIterationSetup()
            .stream()
            .sorted(Comparator.comparingInt(IterationSetup :: getIterationNumber));
        
        return this.iterationSetups.get().map(x -> 
            MessageFormat.format("Iteration {0} {1} {2}", 
                    x.getIterationNumber(),
                    this.GetFormatedIterationFrozenOnDateTime(x.getFrozenOn()),
                    x.getDescription()));
    }

    /**
     * Gets a formated string built with the {@linkplain frozenOn} {@linkplain OffsetDateTime}
     * 
     * @param frozenOn the {@linkplain OffsetDateTime} to format
     * @return a {@linkplain String} containing the {@linkplain iterationSetup} formated or an empty String
     */
    private String GetFormatedIterationFrozenOnDateTime(OffsetDateTime frozenOn)
    {
        if (frozenOn != null)
        {
            return frozenOn.format(DateTimeFormatter.ofPattern("dd-MMM-yy HH:mm:ss"));
        }
        
        return "active";
    }

    /**
     * Gets all available {@link DomainOfExpertise} and the default one
     * 
     * @param selectedEngineeringModelSetupName The selected {@link EngineeringModelSetup} Name
     * @return a {@link Pair}<{@link Stream}<{@link String}>, {@link String}>
     */
    @Override
    public Pair<Stream<String>, String> GetDomainOfExpertise(String selectedEngineeringModelSetupName)
    {
        Pair<Stream<String>, String> defaultResult = Pair.of(Stream.empty(), null);
        
        EngineeringModelSetup engineeringModelSetup = this.engineeringModelSetups.get()
                .filter(x -> AreTheseEquals(x.getShortName(), selectedEngineeringModelSetupName, true))
                .findFirst()
                .orElse(null);
        
        if(engineeringModelSetup == null)
        {
            return defaultResult;
        }
        
        Participant participant = engineeringModelSetup
                .getParticipant()
                .stream()
                .filter(x -> x.getPerson() == this.hubController.GetActivePerson())
                .findFirst()
                .orElse(null);

        if(participant == null)
        {
            return defaultResult;
        }
        
        this.defaultDomain = participant.getPerson().getDefaultDomain();
        
        this.domains = () -> participant.getDomain().stream()
                .sorted((x, y) -> x.getName().compareToIgnoreCase(y.getName()));
        
        return Pair.of(this.domains.get().map(x -> x.getName()),  this.defaultDomain != null ? this.defaultDomain.getName() : "");
    }

    /**
     * Sets the {@link EngineeringModel} loads an {@link Iteration} with the selected {@link DomainOfExpertise}
     * 
     * @param engineeringModelSetupName The selected engineeringModelSetup ShortName
     * @param iterationSetupDisplayString The selected IterationSetup string as displayed in the comboBox
     * @param domainOfExpertiseName The DomainOfExpertise Name
     * @return A value indicating whether the operation went well
     */
    @Override
    public boolean OpenIteration(String engineeringModelSetupName, String iterationSetupDisplayString, String domainOfExpertiseName)
    {
        EngineeringModelSetup engineeringModelSetup = this.engineeringModelSetups.get()
                .filter(x -> x.getShortName().equals(domainOfExpertiseName))
                .findFirst()
                .orElse(null);
        
        Integer selectedIterationNumber = this.ParseIterationSetupDisplayString(iterationSetupDisplayString);

        if(selectedIterationNumber == null)
        {
            logger.error(MessageFormat.format("Could not parse {0} into Integer", iterationSetupDisplayString));
            return false;
        }
                        
        IterationSetup iterationSetup = this.iterationSetups.get().filter(x -> x.getIterationNumber() == selectedIterationNumber).findFirst().orElse(null);
        
        DomainOfExpertise domainOfExpertise = this.domains.get().filter(x -> x.getName().equals(domainOfExpertiseName)).findFirst().get();
        
        return this.hubController.OpenIteration(engineeringModelSetup, iterationSetup, domainOfExpertise);
    }

    /**
     * @param iterationSetupDisplayString
     * 
     * @return a {@linkplain Integer} that represents the Iteration number
     */
    private Integer ParseIterationSetupDisplayString(String iterationSetupDisplayString)
    {
        Supplier<Stream<Character>> caracters = () -> iterationSetupDisplayString.split(" ")[1].chars().mapToObj(x -> (char)x);
        
        if(caracters.get().allMatch(x -> Character.isDigit(x)))
        {
            StringBuilder builder = new StringBuilder();
            caracters.get().forEach(ch->builder.append(ch));
            try
            {
                return Integer.parseInt(builder.toString());  
            } 
            catch (Exception exception)
            {
                this.logger.catching(exception);
            }          
        }
        
        return null;
    }
}
