/*
 * MappingEngineServiceTestFixture.java
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
package Service.MappingEngineService;

import static org.junit.jupiter.api.Assertions.*;
import static org.picocontainer.Characteristics.CACHE;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.picocontainer.Characteristics;

import App.AppContainer;
import Reactive.ObservableCollection;
import Service.MappingEngineService.TestRules.Box;
import Service.MappingEngineService.TestRules.DumbRule;
import Service.MappingEngineService.TestRules.Sphere;
import Service.MappingEngineService.TestRules.SphereCollectionTestRule;
import Service.MappingEngineService.TestRules.SphereTestRule;
import Service.MappingEngineService.TestRules.SphereTypedCollection;
import Services.MappingEngineService.IMappingEngineService;
import Services.MappingEngineService.MappingEngineService;

@TestInstance(Lifecycle.PER_CLASS)
class MappingEngineServiceTestFixture
{
    private MappingEngineService engine;

    @BeforeAll
    void setUp() throws Exception
    {
        AppContainer.Container.addComponent(DumbRule.class.getSimpleName(), DumbRule.class);
        AppContainer.Container.addConfig(MappingEngineService.AssemblyParameterName, SphereTestRule.class.getPackage());
        AppContainer.Container.as(CACHE, Characteristics.USE_NAMES).addComponent(IMappingEngineService.class, MappingEngineService.class);
        
        this.engine = (MappingEngineService)AppContainer.Container.getComponent(IMappingEngineService.class);
    }
    
    @AfterAll
    void TearDown()
    {
        try
        {
            AppContainer.Container.stop();
        }
        catch(IllegalStateException exception)
        {
            System.out.println("Container was already stopped");
        }
    }

    @Test
    void VerifyResolvingEngine()
    {
        Type arrayListType = ((ParameterizedType)new SphereCollectionTestRule().getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        assertNotNull(this.engine);
        assertEquals(4, this.engine.Rules.size());
        ArrayList<String> keys = new ArrayList<String>(this.engine.Rules.keySet());
        
        assertTrue(keys.indexOf(arrayListType.toString()) > -1);
        assertTrue(keys.indexOf(Sphere.class.toString().replace("class ", "")) > -1);
        assertTrue(keys.indexOf(DumbRule.class.toString().replace("class ", "")) > -1);
    }
    
    @Test
    void VerifyMap()
    {
        assertDoesNotThrow(() -> this.engine.Map(8));
        Object mapResultFromInt = this.engine.Map(8);
        assertNull(mapResultFromInt);
        
        Sphere sphereToMap = new Sphere();
        assertDoesNotThrow(() -> this.engine.Map(sphereToMap));
        Object mapResultFromSphere = this.engine.Map(sphereToMap);
        assertNotNull(mapResultFromSphere);
        assertEquals(Box.class, mapResultFromSphere.getClass());
        Box mappedBox = (Box)mapResultFromSphere;
        assertEquals(sphereToMap.GetId().getLeastSignificantBits(), mappedBox.GetLength());
        assertEquals((int)sphereToMap.GetName().charAt(0), mappedBox.GetHeight());

        ObservableCollection<Sphere> sphereListToMap = new ObservableCollection<Sphere>(Arrays.asList(new Sphere(),new Sphere()), Sphere.class);
                
        assertDoesNotThrow(() -> this.engine.Map(sphereListToMap));
        Object mapResultFromSphereList = this.engine.Map(sphereListToMap);
        assertNotNull(mapResultFromSphereList);
        assertTrue(mapResultFromSphereList instanceof ArrayList<?>);
        

        Collection<Sphere> sphereCollectionToMap = new ArrayList<Sphere>();
        assertDoesNotThrow(() -> this.engine.Map((Collection<Sphere>)sphereCollectionToMap));
        Object mapResultFromSphereCollection = this.engine.Map((Collection<Sphere>)sphereCollectionToMap);
        assertNull(mapResultFromSphereCollection);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    void VerifyMapFromTypedCollectionThing()
    {
        SphereTypedCollection collection = new SphereTypedCollection();
        collection.add(new Sphere());
        collection.add(new Sphere());
        collection.add(new Sphere());
        Object mapResultFromSphereList = this.engine.Map(collection);
        assertNotNull(mapResultFromSphereList);
        assertTrue(mapResultFromSphereList instanceof ArrayList<?>);
        ArrayList<Box> result = (ArrayList<Box>)mapResultFromSphereList;
        assertFalse(result.isEmpty());
        assertTrue(result.stream().allMatch(x -> x.GetHeight() == 42 && x.GetLength() == 42));
    }
}
