/*
 * DefinedThingRowViewModel.java
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
package ViewModels.ObjectBrowser.Rows;

import ViewModels.ObjectBrowser.Interfaces.IRowViewModel;
import cdp4common.ModelCode;
import cdp4common.commondata.DefinedThing;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.OwnedThing;

/**
 * The {@linkplain OwnedDefinedThingRowViewModel} is a row class representing a {@linkplain DefinedThing} and a {@linkplain IOwnedThing}
 * 
 * @param TThing the type of {@linkplain Thing} this row view model represents
 */
public abstract class OwnedDefinedThingRowViewModel<TThing extends Thing> extends ThingRowViewModel<TThing>
{
    /**
     * The {@linkplain String} short name
     */
    private String shortName;
    
    /**
     * Gets the {@linkplain shortName} of the represented {@linkplain TThing} to display 
     * 
     * @return a {@linkplain String}
     */
    public String GetShortName()
    {
        return this.shortName;
    }    
    
    /**
     * Sets the {@linkplain shortName} of the represented {@linkplain TThing} to display 
     * 
     * @param shortName a {@linkplain String}
     */
    public void SetShortName(String shortName)
    {
        this.shortName = shortName;
    }
    
    /**
     * The {@linkplain String} owner short name
     */
    private String ownerShortName;
    
    /**
     * Gets the {@linkplain ownerShortName} of the represented {@linkplain TThing} to display 
     * 
     * @return a {@linkplain String}
     */
    public String GetOwnerShortName()
    {
        return this.ownerShortName;
    }    
    
    /**
     * Sets the {@linkplain ownerShortName} of the represented {@linkplain TThing} to display 
     * 
     * @param shortName a {@linkplain String}
     */
    public void SetOwnerShortName(String shortName)
    {
        this.ownerShortName = shortName;
    }
    
    /**
     * The {@linkplain String} model code
     */
    private String modelCode;
    
    /**
     * Gets the {@linkplain modelCode} of the represented {@linkplain TThing} to display 
     * 
     * @return a {@linkplain String}
     */
    public String GetModelCode()
    {
        return this.modelCode;
    }    
    
    /**
     * Sets the {@linkplain modelCode} of the represented {@linkplain TThing} to display 
     * 
     * @param modelCode a {@linkplain String}
     */
    public void SetModelCode(String modelCode)
    {
        this.modelCode = modelCode;
    }
    
    /**
     * Initializes a new {@linkplain DefinedThingRowsViewModel} 
     * 
     * @param thing the {@linkplain TThing}
     * @param parentViewModel the {@linkplain IRowViewModel} parent viewModel
     */
    protected OwnedDefinedThingRowViewModel(TThing thing, IRowViewModel parentViewModel)
    {
        super(thing, parentViewModel);
    }
    
    /**
     * Updates the implementing view model properties
     */
    @Override
    public void UpdateProperties()
    {
        if(this.GetThing() instanceof DefinedThing)
        {
            DefinedThing thing = (DefinedThing)this.GetThing();            
            this.SetName(thing.getName());
            this.shortName = thing.getShortName();
        }
        
        if(this.GetThing() instanceof OwnedThing)
        {
            OwnedThing thing = (OwnedThing)this.GetThing();
            this.ownerShortName = thing.getOwner().getShortName();
        }
        
        if(this.GetThing() instanceof ModelCode)
        {
            ModelCode thing = (ModelCode)this.GetThing();
            this.modelCode = thing.modelCode(null);
        }
    }
}
