/*
 * RequirementBrowserViewModelTestFixture.java
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.netbeans.swing.outline.OutlineModel;

import HubController.IHubController;
import Reactive.ObservableValue;
import ViewModels.ObjectBrowser.RequirementBrowserViewModel;
import ViewModels.ObjectBrowser.RequirementTree.Rows.IterationRequirementRowViewModel;
import ViewModels.ObjectBrowser.RequirementTree.Rows.RequirementGroupRowViewModel;
import ViewModels.ObjectBrowser.RequirementTree.Rows.RequirementRowViewModel;
import ViewModels.ObjectBrowser.RequirementTree.Rows.RequirementSpecificationRowViewModel;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.engineeringmodeldata.Requirement;
import cdp4common.engineeringmodeldata.RequirementsGroup;
import cdp4common.engineeringmodeldata.RequirementsSpecification;
import cdp4common.sitedirectorydata.DomainOfExpertise;

public class RequirementBrowserViewModelTestFixture
{
    private RequirementBrowserViewModel viewModel;
    private IHubController hubController;
    private RequirementsSpecification requirementSpecification0;
    private Iteration iteration;
    private Requirement requirement0;
    private RequirementsGroup requirementGroup0;
    private RequirementsGroup requirementGroup1;
    private Requirement requirement1;
    private DomainOfExpertise owner;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    public void setUp() throws Exception
    {
        this.hubController = mock(IHubController.class);

        this.iteration = new Iteration();
        this.owner = new DomainOfExpertise();
        this.owner.setIid(UUID.randomUUID());
        this.requirementSpecification0 = new RequirementsSpecification();
        this.requirementSpecification0.setOwner(this.owner);
        this.requirementSpecification0.setIid(UUID.randomUUID());
        this.requirement0 = new Requirement();
        this.requirement0.setOwner(this.owner);
        this.requirement0.setIid(UUID.randomUUID());
        this.requirement1 = new Requirement();
        this.requirement1.setOwner(this.owner);
        this.requirement1.setIid(UUID.randomUUID());
        this.requirementGroup0 = new RequirementsGroup();
        this.requirementGroup0.setOwner(this.owner);
        this.requirementGroup0.setIid(UUID.randomUUID());
        this.requirementGroup1 = new RequirementsGroup();
        this.requirementGroup1.setOwner(this.owner);
        this.requirementGroup1.setIid(UUID.randomUUID());

        this.requirement1.setGroup(this.requirementGroup1);
        this.requirementSpecification0.getRequirement().add(this.requirement0);
        this.requirementSpecification0.getRequirement().add(this.requirement1);
        this.requirementSpecification0.getGroup().add(this.requirementGroup0);
        this.requirementGroup0.getGroup().add(this.requirementGroup1);
        this.iteration.getRequirementsSpecification().add(this.requirementSpecification0);

        final ObservableValue<Boolean> isSessionOpen = new ObservableValue<Boolean>(false, Boolean.class);
        when(this.hubController.GetIsSessionOpenObservable()).thenReturn(isSessionOpen.Observable());
        when(this.hubController.GetOpenIteration()).thenReturn(this.iteration);
        when(this.hubController.GetSessionEventObservable()).thenReturn(io.reactivex.Observable.fromArray(true));
        this.viewModel = new RequirementBrowserViewModel(this.hubController);
        isSessionOpen.Value(true);
    }

    @Test
    public void VerifyTreeGetsBuilt() throws Exception
    {
        ObservableValue<OutlineModel> model = this.viewModel.browserTreeModel;

        assertNotNull(model.Value());
        final Object root = model.Value().getRoot();
        assertEquals(IterationRequirementRowViewModel.class, root.getClass());
        final IterationRequirementRowViewModel iterationRowViewModel = (IterationRequirementRowViewModel) root;
        Assert.isNonEmpty(iterationRowViewModel.GetContainedRows());
        assertEquals(1, iterationRowViewModel.GetContainedRows().size());
        final RequirementSpecificationRowViewModel requirementSpecificationRowViewModel0 = iterationRowViewModel.GetContainedRows().get(0);
        assertTrue(requirementSpecificationRowViewModel0 instanceof RequirementSpecificationRowViewModel);
        assertEquals(2, requirementSpecificationRowViewModel0.GetContainedRows().size());
        assertTrue(requirementSpecificationRowViewModel0.GetContainedRows().get(1) instanceof RequirementGroupRowViewModel);

        RequirementGroupRowViewModel requirementGroupRowViewModel0 = (RequirementGroupRowViewModel) requirementSpecificationRowViewModel0.GetContainedRows().get(1);
        assertTrue(requirementGroupRowViewModel0 instanceof RequirementGroupRowViewModel);
        assertEquals(1, requirementGroupRowViewModel0.GetContainedRows().size());
        assertTrue(requirementGroupRowViewModel0.GetContainedRows().get(0) instanceof RequirementGroupRowViewModel);
        RequirementGroupRowViewModel requirementGroupRowViewModel1 = (RequirementGroupRowViewModel) requirementGroupRowViewModel0.GetContainedRows().get(0);
        assertTrue(requirementGroupRowViewModel1.GetContainedRows().get(0) instanceof RequirementRowViewModel);
    }
}
