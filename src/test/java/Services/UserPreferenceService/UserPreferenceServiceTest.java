/*
 * UserPreferenceServiceTest.java
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
package Services.UserPreferenceService;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPreferenceServiceTest
{
    private UserPreferenceService service;
    private File file;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {
        this.service = new UserPreferenceService();
        this.SetTestFile();
    }

    @AfterEach
    void TearDown()
    {
        this.file.delete();
    }
    
    @Test
    void VerifySettingFile() throws Exception
    {
        File file;
        
        try
        {
            Field sessionField = UserPreferenceService.class.getDeclaredField("userPreferenceFile");
            sessionField.setAccessible(true);
            file = (File) sessionField.get(this.service);
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exception)
        {
            exception.printStackTrace();
            throw exception;
        }
        
        assertTrue(file.exists());
        assertFalse(file.isDirectory());
        
    }
    
    @Test
    void VerifyRead() throws Exception
    {        
        UserPreference userPreference = this.service.Read();
        assertTrue(userPreference.savedServerConections.isEmpty());
    }
    
    @Test
    void VerifyWrite() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, IOException
    {
        UserPreference userPreference = this.service.Read();
        userPreference.savedServerConections.add(new SavedServerConnection("http://tes.t"));
        userPreference.savedServerConections.add(new SavedServerConnection("http://veri.f"));
        assertDoesNotThrow(() -> this.service.Save());
        this.service = new UserPreferenceService();
        this.SetTestFile();
        
        this.service.Read();
        UserPreference userPreferenceNew = this.service.GetUserPreference();
        assertNotSame(userPreference, userPreferenceNew);
        assertDoesNotThrow(() -> this.service.Read());
        assertEquals(2, userPreference.savedServerConections.size());
    }
    
    void SetTestFile() throws NoSuchFieldException, SecurityException, IOException, IllegalArgumentException, IllegalAccessException
    {
        this.file = new File("target", "Test.js");
        file.createNewFile();
        Field field = UserPreferenceService.class.getDeclaredField("userPreferenceFile");
        field.setAccessible(true);

        field.set(this.service, file);
    }
}
