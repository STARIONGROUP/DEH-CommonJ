/*
 * ExchangeHistoryEntryRowViewModel.java
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
package ViewModels.ExchangeHistory.Rows;

import java.util.Date;

import ViewModels.ObjectBrowser.Interfaces.IRowBaseViewModel;
import cdp4common.Version;

/**
 * The {@linkplain ExchangeHistoryEntryRowViewModel} represents one timed stamped entry in the local exchange history 
 */
public class ExchangeHistoryEntryRowViewModel implements IRowBaseViewModel
{
    /**
     * Backing field for {@linkplain #GetNodeName()} and {@linkplain #SetNodeName(nodeName)}
     */
    private String nodeName;

    /**
     * Gets the adapter {@linkplain String}
     *
     * @return the adapter {@linkplain String} value
     */
    public String GetNodeName()
    {
        return this.nodeName;
    }

    /**
     * Sets the adapter {@linkplain String}
     *
     * @param version the new {@linkplain String}
     */
    public void SetNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }
    
    /**
     * Backing field for {@linkplain #GetAdapterVersion()} and {@linkplain #SetAdapterVersion(Version)}
     */
    private Version adapterVersion;

    /**
     * Gets the adapter {@linkplain Version}
     *
     * @return the adapter {@linkplain Version} value
     */
    public Version GetAdapterVersion()
    {
        return adapterVersion;
    }

    /**
     * Sets the adapter {@linkplain Version}
     *
     * @param version the new {@linkplain Version}
     */
    public void SetAdapterVersion(Version version)
    {
        this.adapterVersion = version;
    }
    
    /**
     * Backing field for {@linkplain #GetTimestamp()} and {@linkplain SetTimeStamp()}
     */
    private Date timestamp;
    
    /**
     * Gets the timeStamp
     *
     * @return the timeStamp value
     */
    public Date GetTimestamp()
    {
        return timestamp;
    }

    /**
     * Sets the timeStamp
     *
     * @param timeStamp the timeStamp new value
     */
    public void SetTimeStamp(Date timeStamp)
    {
        this.timestamp = timeStamp;
    }
    
    /**
     * Backing field for {@linkplain #GetPerson()} and {@linkplain SetPerson()}
     */
    private String person;
    
    /**
     * Gets the person
     *
     * @return the person value
     */
    public String GetPerson()
    {
        return person;
    }

    /**
     * Sets the person
     *
     * @param person the person new value
     */
    public void SetPerson(String person)
    {
        this.person = person;
    }
    
    /**
     * Backing field for {@linkplain #GetDomain()} and {@linkplain SetDomain()}
     */
    private String domain;
    
    /**
     * Gets the domain
     *
     * @return the domain value
     */
    public String GetDomain()
    {
        return domain;
    }

    /**
     * Sets the domain
     *
     * @param domain the domain new value
     */
    public void SetDomain(String domain)
    {
        this.domain = domain;
    }
    
    /**
     * Backing field for {@linkplain #GetMessage()} and {@linkplain SetMessage()}
     */
    private String message;

    /**
     * Gets the message
     *
     * @return the message value
     */
    public String GetMessage()
    {
        return message;
    }

    /**
     * Sets the message
     *
     * @param message the message new value
     */
    public void SetMessage(String message)
    {
        this.message = message;
    }
}
