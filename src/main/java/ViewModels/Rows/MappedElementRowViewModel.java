/*
 * MappedElementRowViewModel.java
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
package ViewModels.Rows;

import java.util.ArrayList;
import java.util.List;

import Enumerations.MappedElementRowStatus;
import Enumerations.MappingDirection;
import Reactive.ObservableValue;
import ViewModels.Interfaces.IMappedElementRowViewModel;
import cdp4common.commondata.NamedThing;
import cdp4common.commondata.Thing;
import cdp4common.engineeringmodeldata.Relationship;
import io.reactivex.Observable;

/**
 * The {@linkplain MappedElementRowViewModel} is the base abstract row view model that represents a mapping done between a {@linkplain Thing} and a DST Element
 * @param <TThing> the type of {@linkplain Thing}
 * @param <TDstElement> the type of DST element
 */
public abstract class MappedElementRowViewModel<TThing extends Thing & NamedThing, TDstElement> implements IMappedElementRowViewModel
{
    /**
     * The {@linkplain Class} of the {@linkplain TThing}
     */
    private Class<TThing> clazz;
    
    /**
     * Gets the Thing type argument class that identifies the implementation of the {@linkplain MappedElementRowViewModel}
     * 
     * @return a {@linkplain Class}
     */
    @Override
    public Class<? extends Thing> GetTThingClass()
    {
        return this.clazz;
    }
    
    /**
     * Backing field for {@linkplain #GetDstElement}
     */
    private TDstElement dstElement;
    
    /**
     * Gets the {@linkplain #TDstElement} of this represented mapping
     * 
     * @return a {@linkplain #TDstElement}
     */
    public TDstElement GetDstElement()
    {
        return this.dstElement;
    }

    /**
     * Sets the DST element
     * 
     * @param dstElement the {@linkplain #TDstElement}
     */
    public void SetDstElement(TDstElement dstElement)
    {
        this.dstElement = dstElement;
    }
    
    /**
     * Backing field for {@linkplain #GetDstElement}
     */
    private TThing hubElement;
    
    /**
     * Gets the {@linkplain #TThing} of this represented mapping
     * 
     * @return a {@linkplain #TThing}
     */
    public TThing GetHubElement()
    {
        return this.hubElement;
    }
    
    /**
     * Sets the hub element
     * 
     * @param hubElement the {@linkplain #TThing}
     */
    public void SetHubElement(TThing hubElement)
    {
        this.hubElement = hubElement;
    }
        
    /**
     * Backing field for {@linkplain GetIsValid}
     */
    private MappingDirection mappingDirection;
    
    /**
     * Gets the {@linkplain MappingDirection} to which direction this represented mapping applies to
     * 
     * @return a {@linkplain MappingDirection}
     */
    public MappingDirection GetMappingDirection()
    {
        return this.mappingDirection;
    }
    
    /**
     * Backing field for {@linkplain GetShouldCreateNewTargetElement} and {@linkplain SetShouldCreateNewTargetElement}
     */
    protected ObservableValue<Boolean> ShouldCreateNewTargetElement = new ObservableValue<>(false, Boolean.class);
    
    /**
     * Gets a value indicating whether this row represents a mapping done to a new element
     * 
     * @return a {@linkplain boolean}
     */
    public Observable<Boolean> GetShouldCreateNewTargetElement()
    {
        return this.ShouldCreateNewTargetElement.Observable();
    }
    
    /**
     * Sets a value indicating whether this row represents a mapping done to a new element
     * 
     * @param shouldCreateNewTargetElement the {@linkplain boolean} value
     */
    public void SetShouldCreateNewTargetElement(boolean shouldCreateNewTargetElement)
    {
        this.ShouldCreateNewTargetElement.Value(shouldCreateNewTargetElement);
    }
    
    /**
     * The {@linkplain ObservableValue} of {@linkplain Boolean} indicating whether this row is selected
     */
    private ObservableValue<Boolean> isSelected = new ObservableValue<>(false, Boolean.class);
    
    /**
     * Gets the {@linkplain Observable} of {@linkplain Boolean} from the {@linkplain ObservableValue} isSelected
     * 
     * @return an {@linkplain Observable} of {@linkplain Boolean}
     */
    public Observable<Boolean> GetIsSelected()
    {
        return this.isSelected.Observable();
    }
    
    /**
     * Sets a value indicating whether this row is selected
     * 
     * @param isSelected the new {@linkplain boolean} value
     */
    public void SetIsSelected(boolean isSelected)
    {
        this.isSelected.Value(isSelected);
    }
    
    /**
     * Backing field for {@linkplain GetRelationships()}
     */
    private ArrayList<Relationship> relationships = new ArrayList<>();
    
    /**
     * Gets the {@linkplain Relationship}s linked to the referenced {@linkplain GetHubElement()} and its containing {@linkplain ElementUsage}s
     * 
     * @return a collection of {@linkplain Relationship}
     */
    public List<Relationship> GetRelationships()
    {
        return this.relationships;
    }
    
    /**
     * Backing field for {@linkplain GetRowStatus}
     */
    private MappedElementRowStatus rowStatus = MappedElementRowStatus.ExistingMapping;
    
    /**
     * Sets the {@linkplain MappedElementRowStatus} value of this row
     * 
     * @param rowStatus the new {@linkplain MappedElementRowStatus}
     */
    public void SetRowStatus(MappedElementRowStatus rowStatus)
    {
        this.rowStatus = rowStatus;
    }
    
    /**
     * Gets the {@linkplain MappedElementRowStatus} value of this row
     * 
     * @return a {@linkplain MappedElementRowStatus}
     */
    public MappedElementRowStatus GetRowStatus()
    {
        return this.rowStatus;
    }
    
    /**
     * Initializes a new {@linkplain MappedElementRowViewModel}
     * 
     * @param thing the {@linkplain TThing} that is at one end of the mapping
     * @param clazz the {@linkplain Class} of the {@linkplain TThing}
     * @param dstElement the {@linkplain TDstElement} that is at the other end
     * @param mappingDirection the {@linkplain MappingDirection} to which this mapping applies to
     */
    protected MappedElementRowViewModel(TThing thing, Class<TThing> clazz, TDstElement dstElement, MappingDirection mappingDirection)
    {
        this.hubElement = thing;
        this.dstElement = dstElement;
        this.mappingDirection = mappingDirection;
        this.clazz = clazz;
    }
    
    /**
     * Initializes a new {@linkplain MappedElementRowViewModel}
     * 
     * @param clazz the {@linkplain Class} of the {@linkplain TThing}
     * @param dstElement the {@linkplain TDstElement} that is at the other end
     * @param mappingDirection the {@linkplain MappingDirection} to which this mapping applies to
     */
    protected MappedElementRowViewModel(Class<TThing> clazz, TDstElement dstElement, MappingDirection mappingDirection)
    {
        this.dstElement = dstElement;
        this.mappingDirection = mappingDirection;
        this.clazz = clazz;
    }    

    /**
     * Gets a value indicating whether the mapping represented by this view model instance is valid
     * 
     * @return a {@linkplain boolean}
     */
    public boolean GetIsValid()
    {
        if(this.mappingDirection == MappingDirection.FromDstToHub)
        {
            return this.dstElement != null && (this.hubElement != null || this.ShouldCreateNewTargetElement.Value());
        }
        
        return this.hubElement != null && (this.dstElement != null || this.ShouldCreateNewTargetElement.Value());
    }
    

    /**
     * Convenient method to get the last emitted value from {@linkplain ShouldCreateNewTargetElement}
     * 
     * @return a {@linkplain boolean}
     */
    public boolean GetShouldCreateNewTargetElementValue()
    {
        return this.ShouldCreateNewTargetElement.Value();
    }
    
    /**
     * Gets the string representation of the represented DST element
     * 
     * @return a {@linkplain String}
     */
    public abstract String GetDstElementRepresentation();
    
    /**
     * Gets the string representation of the represented DST element
     * 
     * @return a {@linkplain String}
     */
    public abstract String GetHubElementRepresentation();
    
    
    /**
     * Gets the string representation of the represented HUB element
     * 
     * @param thingType the {@linkplain Class} of the Hub element {@linkplain Thing}
     * @return a {@linkplain String}
     */
    public String GetHubElementRepresentation(Class<? extends Thing> thingType)
    {
        return this.GetElementRepresentation(this.GetHubElement() == null ? "-" : this.GetHubElement().getName(),
                thingType.getSimpleName(), MappingDirection.FromDstToHub);
    }
    
    /**
     * Gets the element representation {@linkplain String} based on the provided element name and type 
     * 
     * @param elementName the {@linkplain string} element name
     * @param typeName the {@linkplain String} type name
     */
    protected String GetElementRepresentation(String elementName, String typeName, MappingDirection mappingDirection)
    {    
        String representation = elementName;
                
        if(!"-".equals(elementName))
        {
            representation = this.GetMappingDirection() == mappingDirection        
                    ? String.format("%s [%s]", elementName, typeName)
                    : String.format("[%s] %s", typeName, elementName);
        }
        else if(this.GetShouldCreateNewTargetElementValue() && "-".equals(elementName))
        {
            representation = String.format("new %s", typeName);
        }

        if(this.GetMappingDirection() == mappingDirection)
        {
           return this.GetElementRepresentation(representation);
        }        

        return representation;
    }
    
    /**
     * Gets the element representation based on {@linkplain #ShouldCreateNewTargetElement}
     * 
     * @param representation the existing {@linkplain String} representation to format
     */
    private String GetElementRepresentation(String representation)
    {
        if(this.ShouldCreateNewTargetElement.Value().booleanValue())
        {
            return String.format("%s *", representation);   
        }
        
        return String.format("%s   ", representation);
    }
}
