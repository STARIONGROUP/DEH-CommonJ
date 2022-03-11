/*
 * _.java
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
package Utils;

/**
 * The {@linkplain Ref} is a value wrapper where the generic type can be any thing, as suggested by many java developers, 
 * to overcome the lack of {@linkplain ref} and {@linkplain out}
 * 
 * @param <TType> the type of the contained reference
 */
public final class Ref<TType>
{
    /**
     * The reference this holds
     */
    private TType reference;
    
    /**
     * The class type of this reference 
     */
    private Class<TType> clazz;
    
    /**
     * Gets the enclosed reference
     * 
     * @return a {@linkplain TType}
     */
    public TType Get()
    {
        return this.reference;
    }
    
    /**
     * Sets the encapsulated value
     * 
     * @param value the {@linkplain TType} value
     */
    public void Set(TType value)
    {
        this.reference = value;
    }
        
    /**
     * Gets the type enclosed reference
     * 
     * @return the enclosed reference type
     */
    public Class<TType> GetType()
    {
        return this.clazz;
    }
    
    /**
     * Initializes a new Type wrapper {@linkplain Ref}
     * Acts like passing by reference
     * 
     * @param reference the reference this will hold
     * @param clazz the type of the held reference
     */
    public Ref(Class<TType> clazz, TType reference) 
    {
        this.reference = reference;
        this.clazz = clazz;
    }
    
    /**
     * Initializes a new Type wrapper {@linkplain Ref}
     * Acts like the out keyword
     * 
     * @param clazz the type of the held reference
     */
    public Ref(Class<TType> clazz)
    {
        this.clazz = clazz;
    }
    
    /**
     * Gets a value indicating whether the contained reference is != null
     * 
     * @return a {@linkplain boolean}
     */
    public boolean HasValue()
    {
        return this.reference != null;
    }
}
