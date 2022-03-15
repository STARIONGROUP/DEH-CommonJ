/*
 * MappingRule.java
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
package Services.MappingEngineService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cdp4common.commondata.ShortNamedThing;

/**
 * The {@linkplain MappingRule} class is the abstract base class for all mapping rules implemented in dst adapters. 
 * It allows the concrete mapping rules to be processed by the {@linkplain MappingEngine}
 * 
 * @param TInput the input type the rule will process
 * @param TOutput the output type the rule will return
 */
public abstract class MappingRule<TInput extends Object, TOutput> implements IMappingRule<TInput, TOutput>
{
    /**
     * The current class logger
     */
    protected Logger Logger = LogManager.getLogger();
        
    /**
     * To overcome the inability to use TInput and reflection in the {@linkplain MappingEngine},
     * this casting method returns the input typed as it should be
     * 
     * @param input the input object to transform
     * @return a {@linkplain TInput} object
     */
    @SuppressWarnings("unchecked")
    protected TInput CastInput(Object input)
    {
        return (TInput) input;
    }
    
    /**
     * Compares the two specified things to determine if they have the same shortName. 
     * Please note that the provided elementName will be used after being processed by {@linkplain GetShortName}
     * 
     * @param shortNamedThing the 10-25 {@linkplain ShortNamedThing}
     * @param elementShortName the dst element name as {@linkplain String}
     * @return a value indicating whether the two element have the same short name
     */
    protected boolean AreShortNamesEquals(ShortNamedThing shortNamedThing, String elementShortName)
    {
        return shortNamedThing.getShortName().compareToIgnoreCase(elementShortName) == 0;
    }
}
