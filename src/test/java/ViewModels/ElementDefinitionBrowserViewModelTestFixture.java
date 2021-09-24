/*
 * ObjectBrowserViewModel.java
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
package ViewModels;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.swing.outline.OutlineModel;

import HubController.IHubController;
import Reactive.ObservableValue;
import ViewModels.ObjectBrowser.ElementDefinitionBrowserViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.ElementDefinitionRowViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.ElementUsageRowViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.IterationElementDefinitionRowViewModel;
import ViewModels.ObjectBrowser.ElementDefinitionTree.Rows.Parameters.ParameterGroupRowViewModel;
import ViewModels.ObjectBrowser.Rows.IterationRowViewModel;
import cdp4common.engineeringmodeldata.ActualFiniteState;
import cdp4common.engineeringmodeldata.ActualFiniteStateList;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.ElementUsage;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.engineeringmodeldata.Option;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.ParameterGroup;
import cdp4common.engineeringmodeldata.ParameterOverride;
import cdp4common.engineeringmodeldata.ParameterOverrideValueSet;
import cdp4common.engineeringmodeldata.ParameterSwitchKind;
import cdp4common.engineeringmodeldata.ParameterValueSet;
import cdp4common.sitedirectorydata.ArrayParameterType;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.ParameterTypeComponent;
import cdp4common.sitedirectorydata.TextParameterType;
import cdp4common.types.OrderedItemList;
import cdp4common.types.ValueArray;

class ElementDefinitionBrowserViewModelTestFixture
{
    private Iteration iteration;
    private IHubController hubController;
    private ObjectBrowserViewModel viewModel;
    private ElementDefinition element0;
    private Parameter parameter0;
    private DomainOfExpertise owner;
    private Option option0;
    private Parameter parameter1;
    private Option option1;
    private Parameter parameter2;
    private ActualFiniteState state0;
    private ActualFiniteStateList actualFiniteStateList;
    private ActualFiniteState state1;
    private ElementUsage elementUsage;
    private ElementDefinition element1;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception
    {        
        this.SetUpModel();
        this.hubController = mock(IHubController.class);
        ObservableValue<Boolean> isSessionOpen = new ObservableValue<Boolean>(false, Boolean.class);
        when(this.hubController.GetIsSessionOpenObservable()).thenReturn(isSessionOpen.Observable());
        when(this.hubController.GetOpenIteration()).thenReturn(this.iteration);
        this.viewModel = new ElementDefinitionBrowserViewModel(this.hubController);
        isSessionOpen.Value(true);
    }

    private void SetUpModel()
    {
        this.owner = new DomainOfExpertise();
        this.owner.setName("Owner");
        this.owner.setShortName("Owner");
        
        this.option0 = new Option();
        this.option0.setName("option0");
        
        this.option1 = new Option();
        this.option1.setName("option1");
        
        this.actualFiniteStateList = new ActualFiniteStateList();
        this.state0 = new ActualFiniteState();
        this.state1 = new ActualFiniteState();

        this.actualFiniteStateList.getActualState().add(this.state0);
        this.actualFiniteStateList.getActualState().add(this.state1);
        
        ArrayParameterType arrayType = new ArrayParameterType();
        TextParameterType textType = new TextParameterType();
        
        OrderedItemList<Integer> dimension = new OrderedItemList<Integer>(arrayType, Integer.class);
        dimension.add(2);
        dimension.add(2);
        arrayType.setDimension(dimension);
        ParameterTypeComponent parameterTypeComponent = new ParameterTypeComponent();
        parameterTypeComponent.setParameterType(textType);
        arrayType.getComponent().add(parameterTypeComponent);
        arrayType.setShortName("arrayType");
        arrayType.setName("arrayType");
        
        this.parameter0 = new Parameter();
        this.parameter0.setOwner(this.owner);
        this.parameter0.setParameterType(arrayType);
        ParameterValueSet valueSet0Parameter0 = new ParameterValueSet();
        valueSet0Parameter0.setComputed(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        valueSet0Parameter0.setManual(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        valueSet0Parameter0.setReference(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        valueSet0Parameter0.setFormula(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        valueSet0Parameter0.setPublished(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        valueSet0Parameter0.setValueSwitch(ParameterSwitchKind.MANUAL);
        this.parameter0.getValueSet().add(valueSet0Parameter0);
        
        this.parameter1 = new Parameter();
        this.parameter1.setOwner(this.owner);
        this.parameter1.setParameterType(textType);
        this.parameter1.setOptionDependent(true);
        
        ParameterValueSet valueSet0Parameter1 = new ParameterValueSet();
        valueSet0Parameter1.setActualOption(this.option0);
        valueSet0Parameter1.setComputed(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter1.setManual(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter1.setReference(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter1.setFormula(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter1.setPublished(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter1.setValueSwitch(ParameterSwitchKind.COMPUTED);
        this.parameter1.getValueSet().add(valueSet0Parameter1);

        ParameterValueSet valueSet1Parameter1 = new ParameterValueSet();
        valueSet1Parameter1.setActualOption(this.option1);
        valueSet1Parameter1.setComputed(new ValueArray<String>(Arrays.asList("4"), String.class));
        valueSet1Parameter1.setManual(new ValueArray<String>(Arrays.asList("4"), String.class));
        valueSet1Parameter1.setReference(new ValueArray<String>(Arrays.asList("5"), String.class));
        valueSet1Parameter1.setFormula(new ValueArray<String>(Arrays.asList("78"), String.class));
        valueSet1Parameter1.setPublished(new ValueArray<String>(Arrays.asList("5"), String.class));
        valueSet1Parameter1.setValueSwitch(ParameterSwitchKind.COMPUTED);
        this.parameter1.getValueSet().add(valueSet1Parameter1);
        
        this.parameter2 = new Parameter();
        this.parameter2.setOwner(this.owner);
        this.parameter2.setParameterType(textType);
        this.parameter2.setOptionDependent(true);
        this.parameter2.setStateDependence(this.actualFiniteStateList);
        
        ParameterValueSet valueSet0Parameter2 = new ParameterValueSet();
        valueSet0Parameter2.setActualOption(this.option0);
        valueSet0Parameter2.setActualState(this.state0);
        valueSet0Parameter2.setComputed(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter2.setManual(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter2.setReference(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter2.setFormula(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter2.setPublished(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet0Parameter2.setValueSwitch(ParameterSwitchKind.COMPUTED);
        this.parameter2.getValueSet().add(valueSet0Parameter2);
        
        ParameterValueSet valueSet1Parameter2 = new ParameterValueSet();
        valueSet1Parameter2.setActualOption(this.option1);
        valueSet1Parameter2.setActualState(this.state0);
        valueSet1Parameter2.setComputed(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet1Parameter2.setManual(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet1Parameter2.setReference(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet1Parameter2.setFormula(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet1Parameter2.setPublished(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet1Parameter2.setValueSwitch(ParameterSwitchKind.COMPUTED);
        this.parameter2.getValueSet().add(valueSet1Parameter2);
        
        ParameterValueSet valueSet2Parameter2 = new ParameterValueSet();
        valueSet2Parameter2.setActualOption(this.option0);
        valueSet2Parameter2.setActualState(this.state1);
        valueSet2Parameter2.setComputed(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet2Parameter2.setManual(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet2Parameter2.setReference(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet2Parameter2.setFormula(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet2Parameter2.setPublished(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet2Parameter2.setValueSwitch(ParameterSwitchKind.COMPUTED);
        this.parameter2.getValueSet().add(valueSet2Parameter2);
        
        ParameterValueSet valueSet3Parameter2 = new ParameterValueSet();
        valueSet3Parameter2.setActualOption(this.option1);
        valueSet3Parameter2.setActualState(this.state1);
        valueSet3Parameter2.setComputed(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet3Parameter2.setManual(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet3Parameter2.setReference(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet3Parameter2.setFormula(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet3Parameter2.setPublished(new ValueArray<String>(Arrays.asList("2"), String.class));
        valueSet3Parameter2.setValueSwitch(ParameterSwitchKind.COMPUTED);
        this.parameter2.getValueSet().add(valueSet3Parameter2);
        
        ParameterGroup parameterGroup = new ParameterGroup();
        parameterGroup.setName("group");
        this.parameter0.setGroup(parameterGroup);
        this.iteration = new Iteration();
        this.element0 = new ElementDefinition();
        this.element0.setOwner(this.owner);
        this.element0.getParameter().add(this.parameter0);
        this.element0.getParameter().add(this.parameter1);
        this.element0.getParameter().add(this.parameter2);
        this.element0.getParameterGroup().add(parameterGroup);

        this.element1 = new ElementDefinition();
        this.element1.setOwner(this.owner);
        Parameter parameter0Cloned = this.parameter0.clone(true);
        this.element1.getParameter().add(parameter0Cloned);
        
        this.elementUsage = new ElementUsage();
        this.elementUsage.setElementDefinition(element0);
        this.elementUsage.setOwner(this.owner);
        ParameterOverride parameterOverride = new ParameterOverride();
        parameterOverride.setOwner(this.owner);
        parameterOverride.setParameter(this.parameter0);

        ParameterOverrideValueSet overrideValueSet = new ParameterOverrideValueSet();
        overrideValueSet.setComputed(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        overrideValueSet.setManual(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        overrideValueSet.setReference(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        overrideValueSet.setFormula(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        overrideValueSet.setPublished(new ValueArray<String>(Arrays.asList("2", "8", "56", "87"), String.class));
        overrideValueSet.setValueSwitch(ParameterSwitchKind.MANUAL);
        parameterOverride.getValueSet().add(overrideValueSet);
        
        this.elementUsage.getParameterOverride().add(parameterOverride);
        this.element1.getContainedElement().add(this.elementUsage);
        this.iteration.getElement().add(this.element0);
        this.iteration.getElement().add(this.element1);
    }

    @SuppressWarnings("unchecked")
    @Test
    void VerifyTreeBuilds() throws Exception
    {
        ObservableValue<OutlineModel> model;
        
        try
        {
            Field elementDefinitionTreeField = ObjectBrowserViewModel.class.getDeclaredField("browserTreeModel");
            elementDefinitionTreeField.setAccessible(true);
            model = (ObservableValue<OutlineModel>) elementDefinitionTreeField.get(this.viewModel);
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException exception)
        {
            exception.printStackTrace();
            throw exception;
        }
        
        assertNotNull(model.Value());
        Object root = model.Value().getRoot();
        assertEquals(IterationElementDefinitionRowViewModel.class, root.getClass());
        IterationElementDefinitionRowViewModel iterationRowViewModel = (IterationElementDefinitionRowViewModel)root;
        Assert.isNonEmpty(iterationRowViewModel.GetContainedRows());
        assertEquals(2, iterationRowViewModel.GetContainedRows().size());
        ElementDefinitionRowViewModel elementDefinitionRowViewModel0 = iterationRowViewModel.GetContainedRows().get(0);
        assertTrue(elementDefinitionRowViewModel0 instanceof ElementDefinitionRowViewModel);
        assertEquals(3, elementDefinitionRowViewModel0.GetContainedRows().size());
        assertTrue(elementDefinitionRowViewModel0.GetContainedRows().get(0) instanceof ParameterGroupRowViewModel);

        ElementDefinitionRowViewModel elementDefinitionRowViewModel1 = iterationRowViewModel.GetContainedRows().get(1);
        assertTrue(elementDefinitionRowViewModel1 instanceof ElementDefinitionRowViewModel);
        assertEquals(1, elementDefinitionRowViewModel1.GetContainedRows().size());
        assertTrue(elementDefinitionRowViewModel1.GetContainedRows().get(0) instanceof ElementUsageRowViewModel);
    }
}
