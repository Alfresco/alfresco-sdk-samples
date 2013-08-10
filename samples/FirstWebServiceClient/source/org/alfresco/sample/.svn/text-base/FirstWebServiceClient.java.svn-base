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
package org.alfresco.sample;

import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLAddAspect;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;


/**
 * Simple client example demonstrating the use of the Alfresco Web Service API.
 * 
 * Note: An existing Alfresco Server must be started in order for the client to
 *       connect to it.
 * 
 * The client creates a content node in the "Company Home" folder.  The content
 * may be viewed and operated on within the Alfresco Web Client.  
 *
 * This client demonstrates the "Alfresco Server" deployment option as described
 * in the Alfresco Respotiory Architecture docucment - 
 * http://wiki.alfresco.com/wiki/Alfresco_Repository_Architecture
 */
public class FirstWebServiceClient
{
    
    public static void main(String[] args) throws Exception
    {
        // Start the session
        AuthenticationUtils.startSession("admin", "admin");
        
        try
        {
        	// Create a reference to the parent where we want to create content
            Store storeRef = new Store(Constants.WORKSPACE_STORE, "SpacesStore");
            ParentReference companyHomeParent = new ParentReference(storeRef, null, "/app:company_home", Constants.ASSOC_CONTAINS, null);

            // Assign name
            String name = "Web Services sample (" + System.currentTimeMillis() + ")";
            companyHomeParent.setChildName("cm:" + name);
            
            // Construct CML statement to create content node
            // Note: Assign "1" as a local id, so we can refer to it in subsequent
            //       CML statements within the same CML block
            NamedValue[] contentProps = new NamedValue[1]; 
            contentProps[0] = Utils.createNamedValue(Constants.PROP_NAME, name); 
            CMLCreate create = new CMLCreate("1", companyHomeParent, null, null, null, Constants.TYPE_CONTENT, contentProps);
            
            // Construct CML statement to add titled aspect
            NamedValue[] titledProps = new NamedValue[2];
            titledProps[0] = Utils.createNamedValue(Constants.PROP_TITLE, name);
            titledProps[1] = Utils.createNamedValue(Constants.PROP_DESCRIPTION, name);
            CMLAddAspect addAspect = new CMLAddAspect(Constants.ASPECT_TITLED, titledProps, null, "1");
            
            // Construct CML Block
            CML cml = new CML();
            cml.setCreate(new CMLCreate[] {create});
            cml.setAddAspect(new CMLAddAspect[] {addAspect});

            // Issue CML statement via Repository Web Service and retrieve result
            // Note: Batching of multiple statements into a single web call
            UpdateResult[] result = WebServiceFactory.getRepositoryService().update(cml);     
            Reference content = result[0].getDestination();

            //
            // Write some content
            //
            
            ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
            String text = "The quick brown fox jumps over the lazy dog";
            ContentFormat contentFormat = new ContentFormat("text/plain", "UTF-8");
            Content contentRef = contentService.write(content, Constants.PROP_CONTENT, text.getBytes(), contentFormat);
            System.out.println("Content Length: " + contentRef.getLength());
        }
        catch(Throwable e)
        {
            System.out.println(e.toString());
        }
        finally
        {
            // End the session
            AuthenticationUtils.endSession();
            System.exit(0);
        }
    }
    	
}
