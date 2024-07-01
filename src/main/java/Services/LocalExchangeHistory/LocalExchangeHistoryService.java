/*
 * LocalExchangeHistoryService.java
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
package Services.LocalExchangeHistory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

import HubController.IHubController;
import Services.AdapterInfo.IAdapterInfoService;
import ViewModels.ExchangeHistory.Rows.ExchangeHistoryEntryRowViewModel;
import cdp4common.ChangeKind;
import cdp4common.commondata.NamedThing;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.ParameterBase;
import cdp4common.engineeringmodeldata.ParameterOrOverrideBase;
import cdp4common.engineeringmodeldata.ParameterOverride;
import cdp4common.engineeringmodeldata.ParameterOverrideValueSet;
import cdp4common.engineeringmodeldata.ParameterValueSet;
import cdp4common.engineeringmodeldata.ParameterValueSetBase;
import cdp4common.engineeringmodeldata.ValueSet;
import cdp4common.sitedirectorydata.SampledFunctionParameterType;

/**
 * The {@linkplain LocalExchangeHistoryService} handles operations on the local exchange history
 */
public class LocalExchangeHistoryService implements ILocalExchangeHistoryService
{
    /**
     * The current class logger
     */
    private Logger logger = LogManager.getLogger();
    
    /**
     * The {@linkplain IAdapterVersionNumberService}
     */
    private final IAdapterInfoService adapterInfoService;
    
    /**
     * The {@linkplain IHubController}
     */
    private final IHubController hubController;
    
    /**
     * The file name of the local exchange history json file
     */
    private static final String fileName = "ExchangeHistory.json";
        
    /**
     * The {@linkplain List} of {@linkplain ExchangeHistoryEntryViewModel}
     */
    private List<ExchangeHistoryEntryRowViewModel> pendingEntries = new ArrayList<>();
    
    /**
     * The {@linkplain File} that contains the serialized {@linkplain ExchangeHistoryEntryViewModel}
     */
    private File localExchangeHistoryServiceFile;

    /**
     * The {@linkplain Gson} converter
     */
    private final Gson jsonConverter = new Gson();

    /**
     * Initializes a new {@linkplain LocalExchangeHistoryService}
     * 
     * @param hubController the {@linkplain IHubController}
     * @param versionService the {@linkplain IAdapterVersionNumberService}
     */
    public LocalExchangeHistoryService(IHubController hubController, IAdapterInfoService versionService)
    {
        this.hubController = hubController;
        this.adapterInfoService = versionService;
        this.localExchangeHistoryServiceFile = this.GetFile();
    }
    
    /**
     * Appends to the history a entry that concerns a difference between two {@linkplain ValueSet}
     * 
     * @param valueToUpdate the {@linkplain ParameterValueSetBase} that holds the old value
     * @param newValue the {@linkplain ValueSet} that contains the new value
     */
    @Override
    public void Append(ParameterValueSetBase valueToUpdate, ValueSet newValue)
    {
        ParameterOrOverrideBase parameter;
        
        if(valueToUpdate instanceof ParameterValueSet)
        {
            parameter = valueToUpdate.getContainerOfType(Parameter.class);
        }
        else if(valueToUpdate instanceof ParameterOverrideValueSet)
        {
            parameter = valueToUpdate.getContainerOfType(ParameterOverride.class);
        }
        else
        {
            this.logger.error(String.format("Error while appending log entry regarding the value set %s the type is not handled by the local exchange history service", 
                    valueToUpdate.getUserFriendlyName()));
            
            return;
        }
        
        valueToUpdate.getContainerOfType(ParameterOrOverrideBase.class);
        
        String scale = parameter.getScale() == null ? "-" : parameter.getScale().getShortName();

        String optionString = String.format("%s", valueToUpdate.getActualOption() != null ? String.format(" Option: %s", valueToUpdate.getActualOption().getName()) : "");
        String stateString = String.format("%s", valueToUpdate.getActualState() != null ? String.format(" State: %s", valueToUpdate.getActualState().getName()) : "");
        String prefix = String.format("%s%s", optionString, stateString);

        String newValueRepresentation = this.GetValueSetValueRepresentation(parameter, newValue);
        String oldValueRepresentation = this.GetValueSetValueRepresentation(parameter, valueToUpdate);

        String newValueString = String.format("%s %s [%s]", prefix, newValueRepresentation, scale);
        String valueToUpdateString = String.format("%s %s [%s]", prefix, oldValueRepresentation, scale);
        
        this.Append(parameter.modelCode(null), String.format("Value: [%s] from Parameter [%s] has been updated to [%s]", valueToUpdateString, parameter.modelCode(null), newValueString));
    }
    
    /**
     * Gets the correct representation of the <paramref name="valueSet"/>
     * 
     * @param parameter the {@linkplain ParameterBase} container
     * @param valueSet the {@linkplain ValueSet}
     * @return the {@linkplain String} value representation
     */
    private String GetValueSetValueRepresentation(ParameterBase parameter, ValueSet valueSet)
    {
        if (parameter.getParameterType() instanceof SampledFunctionParameterType)
        {
            int cols = parameter.getParameterType().getNumberOfValues();
            return String.format("[%sx%s]", valueSet.getManual().size() / cols, cols);
        }
        
        if(valueSet.getManual().size() == 0)
        {
            return "-";
        }

        return StreamSupport.stream(valueSet.getManual().spliterator(), false)
                .reduce("", (x, y) -> !"".equals(x) ? String.format("%s,%s", x, y) : y );
    }

    /**
     * Append to the history an entry that relates of a {@linkplain ChangeKind} on the provided {@linkplain thing}
     * 
     * @param thing the changed {@linkplain Thing}
     * @param changeKind the type of change applied to provided {@linkplain Thing}
     */
    @Override
    public void Append(Thing thing, ChangeKind changeKind)
    {
    	if(thing instanceof NamedThing)
        {
            this.Append((NamedThing)thing, changeKind);
        }
        else if(thing instanceof ParameterOrOverrideBase)
        {
            this.Append((ParameterOrOverrideBase)thing, changeKind);
        }
        else
        {
            this.Append(thing.getUserFriendlyName(), String.format("%s %s with Id: %s has been %sD", thing.getClassKind(), thing.getUserFriendlyName(), thing.getIid(), changeKind));
        }
    }

    /**
     * Append to the history an entry that relates of a {@linkplain ChangeKind} on the provided {@linkplain thing}
     * 
     * @param thing the changed {@linkplain Thing}
     * @param changeKind the type of change applied to provided {@linkplain Thing}
     */
    private void Append(NamedThing thing, ChangeKind changeKind)
    {
        this.Append(thing.getName(), String.format("%s %s has been %sD", ((Thing)thing).getClassKind(), thing.getName(), changeKind));
    }
    
    /**
     * Append to the history an entry that relates of a {@linkplain ChangeKind} on the provided {@linkplain ParameterOrOverrideBase}
     * 
     * @param parameter the changed {@linkplain ParameterOrOverrideBase}
     * @param changeKind the type of change applied to provided {@linkplain Thing}
     */
    @Override
    public void Append(ParameterOrOverrideBase parameter, ChangeKind changeKind)
    {
        this.Append(parameter.modelCode(null), String.format("%s [%s] from [%s] has been %sD", parameter.getClassKind(), parameter.getParameterType().getName(), parameter.modelCode(null), changeKind));
    }

    /**
     * Append to the history
     * 
     * @param message the {@linkplain String} message
     * @param nodeName the {@linkplain String} node name to display for the node column
     */
    @Override
    public void Append(String nodeName, String message)
    {
        ExchangeHistoryEntryRowViewModel entry = new ExchangeHistoryEntryRowViewModel();
        entry.SetMessage(message);
        entry.SetPerson(this.hubController.GetActivePerson().getName());
        entry.SetDomain(this.hubController.GetCurrentDomainOfExpertise().getShortName());
        entry.SetNodeName(nodeName);
        
        this.pendingEntries.add(entry);
    }
    
    /**
     * Writes asynchronously the {@linkplain #pendingEntries} to the json file
     */
    @Override
    public void Write()
    {
        try
        {
            StopWatch stopwatch = StopWatch.createStarted();
            
            Date timestamp = new Date();
            
            this.pendingEntries.forEach(x -> x.SetTimeStamp(timestamp));
            this.pendingEntries.forEach(x -> x.SetAdapterVersion(this.adapterInfoService.GetVersion()));

            ExchangeHistoryEntryCollection localExchangeHistory = this.Read();

            localExchangeHistory.addAll(this.pendingEntries);            

            try (FileWriter writer = new FileWriter(this.localExchangeHistoryServiceFile.getAbsolutePath(), false))
            {
                writer.write(this.jsonConverter.toJson(localExchangeHistory));
            }
            
            this.ClearPending();
            stopwatch.stop();
            this.logger.info(String.format("Exchange history processed in %s ms", stopwatch.getTime()));
        }
        catch (Exception exception)
        {
            this.logger.error(exception);
        }
    }

    /**
     * Clear the {@linkplain #pendingEntries} collection
     */
    @Override
    public void ClearPending()
    {
        this.pendingEntries.clear();
    }

    /**
     * Gets the {@linkplain ExchangeHistoryEntryCollection} by reading the json {@linkplain #localExchangeHistoryServiceFile}
     * 
     * @return the {@linkplain ExchangeHistoryEntryCollection}
     */
    @Override
    public ExchangeHistoryEntryCollection Read()
    {
        if (!this.TryCreateDirectoryOrFile(this.localExchangeHistoryServiceFile))
        {
            throw new InvalidPathException(this.localExchangeHistoryServiceFile.getAbsolutePath(), "Cannot make use of the local exchange history service. Check the log for more detail"); 
        }

        try (FileInputStream reader = new FileInputStream(this.localExchangeHistoryServiceFile))
        {
            byte[] data = new byte[(int) this.localExchangeHistoryServiceFile.length()];

            int read = reader.read(data);
            
            this.logger.info(String.format("%s bytes read from the UserPreference file", read));

            String dataString = new String(data, StandardCharsets.UTF_8);
            
            ExchangeHistoryEntryCollection result = this.jsonConverter.fromJson(dataString, ExchangeHistoryEntryCollection.class);
            
            if (result == null)
            {
                return new ExchangeHistoryEntryCollection();
            }
            
            return result;
        }
        catch (IOException exception)
        {
            this.logger.error(MessageFormat.format("Could not local exchange history file {0} because {1}", 
                    this.localExchangeHistoryServiceFile.getAbsolutePath(), exception));
            
            return new ExchangeHistoryEntryCollection();
        }
    }
    
    /**
     * Gets the file instance
     * 
     * @return a {@linkplain File}
     */
    private File GetFile()
    {
        File file = new File(System.getProperty("user.home"));
        file = new File(file, "stariongroup");
        this.TryCreateDirectoryOrFile(file);
        file = new File(file, "DEHAdapterLocalExchangeHistory");
        this.TryCreateDirectoryOrFile(file);
        file = new File(file, String.format("%s-%s", this.adapterInfoService.GetAdapterName() ,fileName));
        this.TryCreateDirectoryOrFile(file);
        return file;
    }
    
    /**
     * Create the directory if it does not exist yet
     * 
     * @param fileOrDirectory the {@linkplain File} containing the path
     * @return a value indicating whether all checks on the {@linkplain file} are ok 
     */
    private boolean TryCreateDirectoryOrFile(File fileOrDirectory)
    {
        try 
        {                
            if(fileOrDirectory.getName().endsWith("json") && !fileOrDirectory.exists())
            {
                return fileOrDirectory.createNewFile();
            }
            
            if(!fileOrDirectory.exists() && fileOrDirectory.mkdir())
            {
                return fileOrDirectory.exists();
            }
            
            return fileOrDirectory.exists();
        }
        catch (IOException exception)
        {
            this.logger.error(MessageFormat.format("Could not create specified directory or file: {0}", fileOrDirectory.getAbsolutePath()));
            this.logger.catching(exception);
            return false;
        }
    }
}
