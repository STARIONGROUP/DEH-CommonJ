/*
* ValueSetUtilsTest.java
*
* Copyright (c) 2020-2022 RHEA System S.A.
*
* Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski, Antoine Théate
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
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public License
* along with this program; if not, write to the Free Software Foundation,
* Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
*/
package Utils;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import cdp4common.engineeringmodeldata.ActualFiniteState;
import cdp4common.engineeringmodeldata.ActualFiniteStateList;
import cdp4common.engineeringmodeldata.ElementDefinition;
import cdp4common.engineeringmodeldata.Option;
import cdp4common.engineeringmodeldata.Parameter;
import cdp4common.engineeringmodeldata.ParameterValueSet;
import cdp4common.exceptions.Cdp4ModelValidationException;
import cdp4common.exceptions.IncompleteModelException;
import cdp4common.sitedirectorydata.BooleanParameterType;
import cdp4common.sitedirectorydata.ParameterType;

class ValueSetUtilsTest
{
	Parameter parameter;
	Option option;
	ActualFiniteState state;
	ActualFiniteStateList stateList;
	
	@BeforeEach
	void setUp()
	{
		this.parameter = new Parameter();
		ParameterType parameterType  = new BooleanParameterType();
		this.parameter.setParameterType(parameterType);
		
		@SuppressWarnings("resource")
		ElementDefinition element = new ElementDefinition();
		element.getContainedParameter().add(parameter);
		
		this.option = new Option();
		this.state = new ActualFiniteState();
		this.stateList = new ActualFiniteStateList();
		this.stateList.getActualState().add(state);
		
		this.parameter.getValueSet().add(new ParameterValueSet());
	}

	@Test
	void VerifyListSize()
	{
		assertDoesNotThrow(() -> ValueSetUtils.QueryParameterBaseValueSet(parameter, null, null));
		this.parameter.getValueSet().clear();
		assertEquals(0,this.parameter.getValueSet().size());
		assertThrows(IncompleteModelException.class,() -> ValueSetUtils.QueryParameterBaseValueSet(parameter, null, null));
		
		this.parameter.getValueSet().add(new ParameterValueSet());
		this.parameter.getValueSet().add(new ParameterValueSet());

		assertThrows(Cdp4ModelValidationException.class,() -> ValueSetUtils.QueryParameterBaseValueSet(parameter, null, null));
	}

	@Test
	void VerifyOptionDependence() 
	{
		this.parameter.setOptionDependent(true);
		
		assertThrows(IllegalArgumentException.class,() -> ValueSetUtils.QueryParameterBaseValueSet(parameter, null, null));
		assertThrows(IllegalArgumentException.class,() -> ValueSetUtils.QueryParameterBaseValueSet(parameter, option, null));
		this.parameter.getValueSet().get(0).setActualOption(option);
		assertEquals(this.parameter.getValueSet().get(0), ValueSetUtils.QueryParameterBaseValueSet(parameter, option, null));
	}
	
	@Test
	void VerifyStateDependence() 
	{
		this.parameter.setStateDependence(this.stateList);
		
		assertThrows(IllegalArgumentException.class,() -> ValueSetUtils.QueryParameterBaseValueSet(parameter, null, null));
		assertThrows(IllegalArgumentException.class,() -> ValueSetUtils.QueryParameterBaseValueSet(parameter, null, this.state));
		this.parameter.getValueSet().get(0).setActualState(state);
		assertEquals(this.parameter.getValueSet().get(0), ValueSetUtils.QueryParameterBaseValueSet(parameter, null, this.state));
	}
}
