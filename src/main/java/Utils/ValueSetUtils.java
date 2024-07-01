/*
 * IValueSetUtils.java
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
package Utils;

import java.util.List;
import java.util.stream.Collectors;

import cdp4common.engineeringmodeldata.ActualFiniteState;
import cdp4common.engineeringmodeldata.Option;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.ParameterBase;
import cdp4common.engineeringmodeldata.ParameterValueSet;
import cdp4common.engineeringmodeldata.ValueSet;
import cdp4common.exceptions.Cdp4ModelValidationException;
import cdp4common.exceptions.IncompleteModelException;

/**
 * The {@linkplain ValueSetUtils} provide an easy way to query {@linkplain ValueSet} from {@linkplain ParameterBase}
 */
public final class ValueSetUtils
{
    /**
     * Initializes a new {@linkplain ValueSetUtils}
     * this constructor is explicitly specifying the static character of this {@linkplain Class} 
     * since static class don't exist out of the box in java. UNUSED
     */
    private ValueSetUtils() { }
	
    /**
     * Searches for a {@linkplain ValueSet}
     * for the given {@linkplain Option} and {@linkplain ActualFiniteState} in the value sets of the given {@linkplain ParameterBase}
     * 
     * @param parameter the base parameter to query the value set from
     * @param option the {@linkplain Option} the queried {@linkplain ValueSet} depends on
     * @param state the {@linkplain ActualFiniteState}  the queried {@linkplain ValueSet} depends on
     * @return the corresponding {@linkplain ValueSet}
     */
    public static ValueSet QueryParameterBaseValueSet(ParameterBase parameter, Option option, ActualFiniteState state)
    {
        String baseErrorMessage = String.format("%s [%s]", parameter.getClass().getName(), parameter.getParameterType().getName());
        List<ValueSet> list = parameter.getValueSets();
        
        if (list.isEmpty())
        {
            throw new IncompleteModelException(String.format("%s doesn't contain any values.", baseErrorMessage));
        }
        
        if (parameter.isOptionDependent())
        {
            if (option == null)
            {
                throw new IllegalArgumentException(String.format("%s is option dependent. The option cannot be null.", baseErrorMessage));
            }
            
            list = list.stream().filter(x -> x.getActualOption() == option).collect(Collectors.toList());
            
            if (list.isEmpty())
            {
                throw new IllegalArgumentException(String.format("%s doesn't have values for Option %s.", baseErrorMessage, option.getName()));
            }
        }
        if (parameter.getStateDependence() != null)
        {
            if (state == null)
            {
                throw new IllegalArgumentException(String.format("%s is state dependent. The actualState property cannot be null.", baseErrorMessage));
            }
            
            list = list.stream().filter(x -> x.getActualState() == state).collect(Collectors.toList());
            
            if (list.isEmpty())
            {
                throw new IllegalArgumentException(String.format("%s doesn't have values for ActualFiniteState %s.", baseErrorMessage, state.getName()));                
            }            
        }
            
        if(list.size() <= 1 || parameter.getOriginal() != null)
        {
            return list.get(0);
        }
        
        throw new Cdp4ModelValidationException(String.format("Multiple ValueSets found for %s", baseErrorMessage));
    }
    
    /**
     * Computes the parameter value to display based on the provided {@linkplain Parameter} and {@linkplain Option} and {@linkplain }
     * 
     * @param parameter the {@linkplain Parameter}
     * @param option the {@linkplain Option} the queried {@linkplain ValueSet} depends on
     * @param state the {@linkplain ActualFiniteState}  the queried {@linkplain ValueSet} depends on
     * @return a {@linkplain String}
     */
    public static String GetParameterValue(Parameter parameter, Option option, ActualFiniteState actualFiniteState)
    {
        return ValueSetUtils.GetParameterValue(parameter, ValueSetUtils.QueryParameterBaseValueSet(parameter, option, actualFiniteState));
    }

    /**
     * Computes the parameter value to display based on the provided {@linkplain Parameter} and {@linkplain ParameterValueSet}
     * 
     * @param parameter the {@linkplain Parameter}
     * @param parameterValueSet the {@linkplain ValueSet}
     * @return a {@linkplain String}
     */
    public static String GetParameterValue(Parameter parameter, ValueSet parameterValueSet)
    {
        String value = 
                parameterValueSet.getActualValue().size() == 1 
                ? parameterValueSet.getActualValue().get(0)
                : String.format("[%sx%s]", parameterValueSet.getActualValue().size() / parameter.getParameterType().getNumberOfValues(), parameter.getParameterType().getNumberOfValues());
                
        String scale = null;
        
        if(parameter.getScale() != null)
        {
            scale = String.format("[%s]", parameter.getScale().getShortName());
        }
        
        return String.format("%s %s", value, scale != null ? scale : "");
    }
}
