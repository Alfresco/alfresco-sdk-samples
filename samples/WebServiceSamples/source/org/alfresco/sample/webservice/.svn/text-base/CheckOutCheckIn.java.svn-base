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

import org.alfresco.webservice.authoring.AuthoringServiceSoapBindingStub;
import org.alfresco.webservice.authoring.CheckoutResult;
import org.alfresco.webservice.content.Content;
import org.alfresco.webservice.content.ContentServiceSoapBindingStub;
import org.alfresco.webservice.repository.RepositoryServiceSoapBindingStub;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLAddAspect;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.types.Version;
import org.alfresco.webservice.types.VersionHistory;
import org.alfresco.webservice.util.AuthenticationUtils;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.ContentUtils;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;

/**
 * Web service sample 5
 * <p>
 * This sample shows how to check documents out, check them back in and then view the 
 * version history.
 * 
 * @author Roy Wetherall
 */
public class CheckOutCheckIn extends SamplesBase
{
    private final static String INITIAL_CONTENT = "This is the content pror to checkout";
    private final static String UPDATED_CONTENT = "This is the updated content";
    
    /**
     * Main function
     */
    public static void main(String[] args)
        throws Exception
    {
        AuthenticationUtils.startSession(USERNAME, PASSWORD);
        try
        {          
            // Make sure smaple data has been created
            createSampleData();
            
            // Get the content and authoring service
            RepositoryServiceSoapBindingStub repositoryService = WebServiceFactory.getRepositoryService();
            ContentServiceSoapBindingStub contentService = WebServiceFactory.getContentService();
            AuthoringServiceSoapBindingStub authoringService = WebServiceFactory.getAuthoringService();
            
            // Get a reference to a newly created content
            Reference contentReference = ContentReadAndWrite.createNewContent(contentService, "SampleFiveFileOne.txt", INITIAL_CONTENT);
            
            // Add the versionable aspect to the newly created content.  This will allows the content to be versioned
            makeVersionable(repositoryService, contentReference);
            
            // Checkout the newly created content, placing the working document in the same folder
            Predicate itemsToCheckOut = new Predicate(new Reference[]{contentReference}, null, null);
            CheckoutResult checkOutResult = authoringService.checkout(itemsToCheckOut, null);
            
            // Get a reference to the working copy
            Reference workingCopyReference = checkOutResult.getWorkingCopies()[0];
            
            // Update the content of the working copy
            ContentFormat format = new ContentFormat(Constants.MIMETYPE_TEXT_PLAIN, "UTF-8");
            contentService.write(workingCopyReference, Constants.PROP_CONTENT, UPDATED_CONTENT.getBytes(), format);
            
            // Now check the working copy in with a description of the change made that will be recorded in the version history
            Predicate predicate = new Predicate(new Reference[]{workingCopyReference}, null, null);
            NamedValue[] comments = new NamedValue[]{Utils.createNamedValue("description", "The content has been updated")};
            authoringService.checkin(predicate, comments, false);
            
            // Output the updated content
            Store store = new Store(Constants.WORKSPACE_STORE, "SpacesStore");
            Content[] readResult = contentService.read(
                            new Predicate(new Reference[]{contentReference}, store, null), 
                            Constants.PROP_CONTENT);
            Content content = readResult[0];
            System.out.println("This is the checked-in content:");
            System.out.println(ContentUtils.getContentAsString(content));
            
            // Get the version history
            System.out.println("The version history:");
            VersionHistory versionHistory = authoringService.getVersionHistory(contentReference);
            for (Version version : versionHistory.getVersions())
            {
                // Output the version details
                outputVersion(version);
            }
        }
        finally
        {
            // End the session
            AuthenticationUtils.endSession();
        }
    }
    
    /**
     * Helper method to make apply the versionable aspect to a given reference
     * <p>
     * See sample 4 for more CML examples
     * 
     * @param respositoryService    the respository service
     * @param reference             the reference
     * @throws Exception
     */
    public static void makeVersionable(RepositoryServiceSoapBindingStub respositoryService, Reference reference)
        throws Exception
    {
        // Create the add aspect query object
        Predicate predicate = new Predicate(new Reference[]{reference}, null, null);
        CMLAddAspect addAspect = new CMLAddAspect(Constants.ASPECT_VERSIONABLE, null, predicate, null); 
        
        // Create the content management language query
        CML cml = new CML();
        cml.setAddAspect(new CMLAddAspect[]{addAspect});
        
        // Execute the query, which will add the versionable aspect to the node is question
        respositoryService.update(cml);
    }
    
    /**
     * Helper to output the version details
     * 
     * @param version   the version
     */
    private static void outputVersion(Version version)
    {
        String description = "none";
        for (NamedValue namedValue : version.getCommentaries())
        {
            if (namedValue.getName().equals("description") == true)
            {
                description = namedValue.getValue();
            }
        }
        System.out.println("Version label = " + version.getLabel() + "; Version description = " + description);
    }
}
