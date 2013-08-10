/*
 * Copyright (C) 2005-2010 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.sample.webservice;

import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.WebServiceFactory;

/**
 * Web service sample 1.
 * <p>
 * Connect to the reposity and get a list of all the stores available in the repository.
 * 
 * @author Roy Wetherall
 */
public class GetStores extends SamplesBase
{
    /**
     * Connect to the respository and print out the names of the available 
     * 
     * @param args
     */
    public static void main(String[] args) 
        throws Exception
    {
        // Start the session
        AuthenticationUtils.startSession(USERNAME, PASSWORD);
        
        try
        {   
            // Get the respoitory service
            RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
            
            // Get array of stores available in the repository
            Store[] stores = repositoryService.getStores();
            if (stores == null)
            {
                // NOTE: empty array are returned as a null object, this is a issue with the generated web service code.
                System.out.println("There are no stores avilable in the repository.");
            }
            else
            {
                // Output the names of all the stores available in the repository
                System.out.println("The following stores are available in the repository:");
                for (Store store : stores)
                {
                    System.out.println(store.getScheme() + "://" + store.getAddress());
                }
            }
        }
        finally
        {
            // End the session
            AuthenticationUtils.endSession();
        }
    }       
}
