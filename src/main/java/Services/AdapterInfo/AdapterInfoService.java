/*
 * AdapterInfoService.java
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
package Services.AdapterInfo;

import cdp4common.Version;

public class AdapterInfoService implements IAdapterInfoService
{
    /**
     * Backing field for {@linkplain #GetVersion()}
     */
    private Version version;
    
    /**
     * Gets the {@linkplain Version} that is provided by this service
     * 
     * @return the {@linkplain Version}
     */
    @Override
    public Version GetVersion()
    {
        return this.version;
    }
    
    /**
     * Backing field for {@linkplain #GetAdapterName()}
     */
    private String adapterName;

    /**
     * Gets the {@linkplain String} adapter name that is provided by this service
     * 
     * @return the adapter name
     */
    @Override
    public String GetAdapterName()
    {
        return this.adapterName;
    }

    /**
     * Initializes a new {@linkplain AdapterInfoService}
     * 
     * @param version the provided {@linkplain Version}
     * @param adapterName the provided {@linkplain }
     */
    public AdapterInfoService(Version version, String adapterName)
    {
        this.adapterName = adapterName;
        this.version = version;
    }
}
