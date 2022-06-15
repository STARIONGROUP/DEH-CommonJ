/*
 * MappingEngineService.java
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

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import App.AppContainer;
import Reactive.ObservableCollection;

import java.util.stream.Collectors;

/**
 * The {@linkplain MappingEngineService} provides a standardized way to process mapping rules implemented in dst adapters
 */
public final class MappingEngineService implements IMappingEngineService
{
    /**
     * Gets the constructor parameter name, used by the IoC
     */
    public static final String AssemblyParameterName = "assembly";

    /**
     * The current class logger
     */
    private final Logger logger = LogManager.getLogger();
    
    /**
     * Gets a Dictionary that contains all the available {@linkplain IMappingRule} based on the provided assembly
     * where the Key is the Input type as string of the Value of a corresponding {@linkplain IMappingRule}
     */
    Map<String, MappingRule<?,?>> rules = new HashMap<>();
    
    /**
     * Initializes a new {@linkplain MappingEngine}
     * 
     * @param assembly the {@linkplain Package}
     */
    public MappingEngineService(Package assembly)
    {
        this.PopulateRules(assembly.getName());
    }
    
    /**
     * Maps the provided {@linkplain input} to another type if a rule is found
     * 
     * @param input the input to be mapped
     * @return the output of the rule
     */
    @Override
    public Object Map(Object input)
    {
        if(this.rules.isEmpty())
        {
            return null;
        }

        MappingRule<?, ?> foundRule = this.rules.getOrDefault(this.GetKeyFromObject(input), null);

        if(foundRule != null)
        {
            return foundRule.Transform(input);
        }
        
        return null;
    }

    /**
     * Gets the key as it is used in the {@linkplain Rules} as key
     * 
     * @param object the {@linkplain Object} input
     * @return a {@linkplain String}
     */
    private String GetKeyFromObject(Object object)
    {
        final String classSuffixString = "class ";
        
        if(object instanceof ObservableCollection)
        {
            return String.format("%s<%s>", object.getClass(), 
                    ((ObservableCollection<?>)object).GetType().getName()).replace(classSuffixString, "");
        }
        
        else if(object instanceof Class<?>)
        {
            Class<?> objectClass = (Class<?>)object;
            
            if(objectClass.getGenericSuperclass() instanceof ParameterizedType)
            {
                return String.format("%s", ((ParameterizedType) objectClass.getGenericSuperclass())
                        .getActualTypeArguments()[0]).replace(classSuffixString, "");
            }
        }
        
        return (object.getClass().toString()).replace(classSuffixString, "");
    }
    
    /**
     * Populates the rules that have been found  
     * 
     * @param ruleAssembly the assembly name where the rules are
     */
    private void PopulateRules(String ruleAssembly)
    {
        for (Class<?> classRule : this.GetAvailableMappingRules(ruleAssembly))
        {
            MappingRule<?, ?> ruleInstance = this.InitializeRule(classRule);
            
            if(ruleInstance != null)
            {
               this.rules.putIfAbsent(this.GetKeyFromObject(classRule), ruleInstance);
            }            
        }
    }

    /**
     * Initializes the rule whether it is present in the IoC or it need to be initialized by constructor calling.
     * In case the rule is registered in the container, in order for the container to resolve it register it with the fully qualified class name
     * 
     * @param classRule the class rule to initialize
     * @return an instance of the {@linkplain classRule}
     */
    private MappingRule<?,?> InitializeRule(Class<?> classRule)
    {
        try
        {  
            if (classRule.getSuperclass().getTypeParameters().length == 0)
            {
                return null;
            }
            
            MappingRule<?,?> mappingRule = (MappingRule<?,?>)AppContainer.Container.getComponent(classRule.getName());
                        
            if (mappingRule != null)
            {
                return mappingRule;
            }
            
            return (MappingRule<?, ?>) classRule.newInstance();
        }
        catch (Exception exception)
        {
            this.logger.error(String.format("Error, could not initialize the mapping rule %s, because the following exception occured %s", classRule.getSimpleName(), exception));
            this.logger.catching(exception);
            return null;
        }
    }
    
    /**
     * Gets the available {@linkplain Mapping rules}
     * 
     * @param packageName the package name where mapping rules are implemented
     * @return a {@linkplain Set} of class
     */
    @SuppressWarnings({"rawtypes"})
    private Set<Class<? extends IMappingRule>> GetAvailableMappingRules(String packageName) 
    {
        Reflections reflections = new Reflections(packageName, new SubTypesScanner(false));
        
        return reflections.getSubTypesOf(MappingRule.class)
                .stream()
                .filter(x -> !java.lang.reflect.Modifier.isAbstract(x.getModifiers()))
                .collect(Collectors.toSet());
    }
}
