/*
 * UserPreferenceBaseService.java
 *
 * Copyright (c) 2020-2024 Starion Group S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski, Antoine Théate
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
package Services.UserPreferenceService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.InvalidPathException;
import java.text.MessageFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;

/**
 * The {@linkplain UserPreferenceBaseService} is the base user preference service for any user preference service
 * 
 * @param <TUserPreference> the type of user preference
 */
public abstract class UserPreferenceBaseService<TUserPreference>
{
    /**
     * The current class logger
     */
    private final Logger logger = LogManager.getLogger();
    
    /**
     * The name of folder where user preference is storage.
     */
    public static final String UserPreferenceDirectoryName = "UserPreferences";

    /**
     * The setting file extension
     */
    public static final String SettingFileExtension = ".settings.json";
    
    /**
     * Gets the {@linkplain Class} of {@linkplain #TUserPreference}
     */
    protected abstract Class<TUserPreference> GetUserPreferenceType();

    /**
     * Gets the user preference file name
     */
    protected abstract String GetFileName();
    
    /**
     * Backing field for {@linkplain GetUserPreference()}
     */
    private TUserPreference userPreference;
    
    /**
     * Gets the {@linkplain IUserPreference} the {@linkplain UserPreferenceService} currently write and read from/to
     * 
     * @return a {@linkplain IUserPreference}
     */    
    public TUserPreference GetUserPreference()
    {
        if(this.userPreference == null)
        {
            this.Read();
        }
        
        return this.userPreference;
    }
    
    /**
     * The {@linkplain File} that holds the user preferences
     */
    private File userPreferenceFile = this.GetSettingFile();
    
    /**
     * Reads the {@linkplain IUserPreference} from the file
     * 
     * @return the {@linkplain TUserPreference}
     */    
    public TUserPreference Read()
    {
        if (!this.TryCreateDirectoryOrFile(this.userPreferenceFile))
        {
            throw new InvalidPathException(this.userPreferenceFile.getAbsolutePath(), "Cannot make use of the user preference service. Check the log for more detail"); 
        }

        try (FileInputStream reader = new FileInputStream(this.userPreferenceFile))
        {
            byte[] data = new byte[(int) this.userPreferenceFile.length()];
            int read = reader.read(data);
            
            this.logger.info(String.format("%s bytes read from the UserPreference file ", read));
            
            String dataString = new String(data, StandardCharsets.UTF_8);
            
            this.userPreference = new Gson().fromJson(dataString, GetUserPreferenceType());
            
            if (this.userPreference == null)
            {
                this.userPreference = GetUserPreferenceType().newInstance();
            }
            
            return this.userPreference;
        }
        catch (IOException | InstantiationException | IllegalAccessException exception)
        {
            this.logger.error(MessageFormat.format("Could not read the user setting file {0} because {1}", 
                    this.userPreferenceFile.getAbsolutePath(),
                    exception));
            
            return null;
        }
    }
    
    /**
     * Saves the {@linkplain userPreference} to disk
     */
    public void Save()
    {
        if (!this.TryCreateDirectoryOrFile(this.userPreferenceFile))
        {
            throw new InvalidPathException(this.userPreferenceFile.getAbsolutePath(), "Cannot make use of the user preference service. Check the log for more detail"); 
        }
        
        try (FileWriter writer = new FileWriter(this.userPreferenceFile.getAbsolutePath(), false))
        {
            writer.write(new Gson().toJson(this.userPreference)); 
        } 
        catch (IOException exception)
        {
            this.logger.error(MessageFormat.format("Could not write the user setting file {0} because {1}", 
                    this.userPreferenceFile.getAbsolutePath(),
                    exception));
        }
    }
        
    /**
     * Gets the setting file
     * 
     * @return a {@linkplain File}
     */
    private File GetSettingFile()
    {
        File file = new File(System.getProperty("user.home"));
        file = new File(file, ".stariongroup");
        this.TryCreateDirectoryOrFile(file);
        file = new File(file, "DEHAdapterSettingFile");
        this.TryCreateDirectoryOrFile(file);
        file = new File(file, UserPreferenceDirectoryName);
        this.TryCreateDirectoryOrFile(file);
        file = new File(file, this.GetFileName() + UserPreference.class.getSimpleName() + SettingFileExtension);
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
            if(fileOrDirectory.getName().endsWith(SettingFileExtension) && !fileOrDirectory.exists())
            {
                return fileOrDirectory.createNewFile();
            }
            
            if(!fileOrDirectory.exists() && fileOrDirectory.mkdir())
            {
                return fileOrDirectory.createNewFile();
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
