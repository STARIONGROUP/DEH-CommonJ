/*
 * HubController.java
 *
 * Copyright (c) 2020-2021 RHEA System S.A.
 *
 * Author: Sam Gerené, Alex Vorobiev, Nathanael Smiechowski 
 *
 * This file is part of DEH-CommonJ
 *
 * The DEH-MDSYSML is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * The DEH-MDSYSML is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package HubController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableMap;

import cdp4common.engineeringmodeldata.EngineeringModel;
import cdp4common.engineeringmodeldata.Iteration;
import cdp4common.sitedirectorydata.DomainOfExpertise;
import cdp4common.sitedirectorydata.EngineeringModelSetup;
import cdp4common.sitedirectorydata.IterationSetup;
import cdp4common.sitedirectorydata.Participant;
import cdp4common.sitedirectorydata.Person;
import cdp4common.sitedirectorydata.SiteDirectory;
import cdp4common.types.ContainerList;
import cdp4dal.Session;
import cdp4dal.SessionImpl;
import cdp4dal.dal.Credentials;
import cdp4servicesdal.CdpServicesDal;

/**
 * Definition of the {@link HubController} which is responsible to provides {@link Session} related functionalities
 */
public final class HubController implements IHubController 
{
    /**
     * The current class logger
     */
    private Logger logger = LogManager.getLogger();
    /**
     * Backing field for {@link IsSessionOpen}
     */ 
    private DomainOfExpertise currentDomainOfExpertise;
    
    /**
     * A value indicating whether the session is open
     */
    @Override
    public DomainOfExpertise GetCurrentDomainOfExpertise()
    {
        return this.currentDomainOfExpertise;
    }

    /**
     * Backing field for {@link IsSessionOpen}
     */ 
    private Boolean isSessionOpen;
    
    /**
     * A value indicating whether the session is open
     */
    @Override
    public Boolean IsSessionOpen()
    {
        return this.isSessionOpen;
    }
           
    /**
     * Gets or sets the open {@link Iteration}
     */
    public Iteration openIteration;
            
    /**
     * The {@link Session} that is used to communicate with the data-store
     */
    private Session session;

    /**
     * Sets the session
     * 
     * @param Session the Session to assign
     */
    public void SetSession(Session session)
    {
        this.session = session;
    }
    
    /**
     * Opens the {@linkplain Session}
     * 
     * @param credentials the {@link Credentials}
     * @return A {@link Boolean} indicating whether opening the session succeeded
     */
    @Override
    public Boolean Open(Credentials credentials)
    {
        this.session = new SessionImpl(new CdpServicesDal(), credentials); 

        this.session.open().join();
                
        return this.session.getAssembler().retrieveSiteDirectory() != null;
    }    

    /**
     *  Gets the {@link EngineeringModelSetup}s contained in the site directory
     *  
     * @return A {@link ContainerList} of {@link EngineeringModelSetup}
     */
    @Override
    public ContainerList<EngineeringModelSetup> GetEngineeringModels()
    {
        SiteDirectory siteDirectory = this.GetSiteDirectory();
        
        if (siteDirectory != null)
        {
            return siteDirectory.getModel();
        }
        
        return null;
    }

    /**
     * Retrieves the <{@link SiteDirectory} that is loaded in the <{@link Session}
     * 
     * @return the {@link SiteDirectory}
     */
    @Override
    public SiteDirectory GetSiteDirectory()
    {
        if (this.session != null)
        {
            return this.session.retrieveSiteDirectory();            
        }
        
        return null;
    }
    
    /**
     * Gets the active person
     * 
     * @return The active {@link Person}
     */
    @Override
    public Person GetActivePerson()
    {
        if (this.session != null)
        {
            return this.session.getActivePerson();            
        }
        
        return null;
    }    
    
    /**
     * Reads an {@link Iteration} and set the active {@link DomainOfExpertise} for the {@link Iteration}
     * @param The {@link Iteration} to read
     * @param The {@link Domain} that reads the {@link Iteration}
     */
    @Override
    public void GetIteration(Iteration iteration, DomainOfExpertise domain)
    {
        this.session.read(iteration, domain).join();
        ImmutableMap<Iteration, Pair<DomainOfExpertise, Participant>> iterationDomainAndParticipant = this.GetIteration();
        this.openIteration = iterationDomainAndParticipant.keySet().stream().findFirst().get();
        this.currentDomainOfExpertise = iterationDomainAndParticipant.entrySet().stream().findFirst().get().getValue().getKey();
        
        this.isSessionOpen = this.openIteration != null;
    }

    /**
     * Reads an {@link Iteration} and set the active @link DomainOfExpertise for the Iteration
     * 
     * @return a {@link ImmutableMap} of <{@link Iteration}, {@link Pair} <{@link DomainOfExpertise}, {@link Participant} >>
     */
    @Override
    public ImmutableMap<Iteration, Pair<DomainOfExpertise, Participant>> GetIteration()
    {
        return this.session.getOpenIterations();
    }

    
    /**
     * Reloads the {@link Session}
     * 
     * @return A value indicating whether the {@link future} completed with success
     */
    @Override
    public Boolean Reload()
    {
        return this.RefreshOrReload(this.session.reload());
    }

    /**
     * Reloads the {@link Session}
     * 
     * @return A value indicating whether the {@link future} completed with success
     */
    @Override
    public Boolean Refresh()
    {
        return this.RefreshOrReload(this.session.refresh());
    }
    
    /**
     * Calls the {@link future} wrapped in try block and return a {@link Boolean}
     * 
     * @param future a {@link CompletableFuture}
     * @return A value indicating whether the {@link future} completed with success
     */
    private Boolean RefreshOrReload(CompletableFuture<Void> future)
    {
        try
        {
            future.get();
            return true;
        } 
        catch (InterruptedException interruptedException)
        {
            this.logger.catching(interruptedException);
        } 
        catch (ExecutionException executionException)
        {
            this.logger.catching(executionException);
        }
        
        return false;
    }

    /**
     * Loads an {@link Iteration} with the selected {@link DomainOfExpertise}
     * 
     * @param engineeringModelSetup
     * @param iterationSetup
     * @param domainOfExpertise
     * @return A value indicating whether the operation went well
     */
    @Override
    public boolean OpenIteration(EngineeringModelSetup engineeringModelSetup, IterationSetup iterationSetup, DomainOfExpertise domainOfExpertise)
    {
        try
        {
            EngineeringModel model = new EngineeringModel(engineeringModelSetup.getEngineeringModelIid(), 
                    this.session.getAssembler().getCache(), this.session.getCredentials().getUri());
            
            model.setEngineeringModelSetup(engineeringModelSetup);

            Iteration iteration = new Iteration(iterationSetup.getIterationIid(),
                    this.session.getAssembler().getCache(), this.session.getCredentials().getUri());

            model.getIteration().add(iteration);
            
            this.GetIteration(iteration, domainOfExpertise);
            return true;
        }
        catch (Exception exception)
        {
            this.logger.catching(exception);
            return false;
        }        
    }
}
