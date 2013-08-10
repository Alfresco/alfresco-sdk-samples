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

import org.alfresco.webservice.repository.UpdateResult;
import org.alfresco.webservice.types.CML;
import org.alfresco.webservice.types.CMLCreate;
import org.alfresco.webservice.types.ContentFormat;
import org.alfresco.webservice.types.NamedValue;
import org.alfresco.webservice.types.ParentReference;
import org.alfresco.webservice.types.Predicate;
import org.alfresco.webservice.types.Reference;
import org.alfresco.webservice.types.Store;
import org.alfresco.webservice.util.Constants;
import org.alfresco.webservice.util.Utils;
import org.alfresco.webservice.util.WebServiceFactory;

/**
 * @author Roy Wetherall
 */
public class SamplesBase
{
    /** Admin user name and password used to connect to the repository */
    protected static final String USERNAME = "admin";
    protected static final String PASSWORD = "admin";
    
    /** The store used throughout the samples */
    protected static final Store STORE = new Store(Constants.WORKSPACE_STORE, "SpacesStore");
    
    protected static final Reference SAMPLE_FOLDER = new Reference(STORE, null, "/app:company_home/cm:sample_folder"); 
    
    protected static void createSampleData() throws Exception
    {
        try
        {
            // Check to see if the sample folder has already been created or not
            WebServiceFactory.getRepositoryService().get(new Predicate(new Reference[]{SAMPLE_FOLDER}, STORE, null));
        }
        catch (Exception exception)
        {
            // Create parent reference to company home
            ParentReference parentReference = new ParentReference(
                    STORE,
                    null, 
                    "/app:company_home",
                    Constants.ASSOC_CONTAINS, 
                    Constants.createQNameString(Constants.NAMESPACE_CONTENT_MODEL, "sample_folder"));

            // Create folder
            NamedValue[] properties = new NamedValue[]{Utils.createNamedValue(Constants.PROP_NAME, "Web Service Sample Folder")};
            CMLCreate create = new CMLCreate("1", parentReference, null, null, null, Constants.TYPE_FOLDER, properties);
            CML cml = new CML();
            cml.setCreate(new CMLCreate[]{create});
            UpdateResult[] results = WebServiceFactory.getRepositoryService().update(cml);                
            
            // Create parent reference to sample folder
            Reference sampleFolder = results[0].getDestination();
            ParentReference parentReference2 = new ParentReference(
                    STORE,
                    sampleFolder.getUuid(),
                    null,
                    Constants.ASSOC_CONTAINS, 
                    Constants.createQNameString(Constants.NAMESPACE_CONTENT_MODEL, "sample_content"));
            
            // Create content
            NamedValue[] properties2 = new NamedValue[]{Utils.createNamedValue(Constants.PROP_NAME, "SampleContent.txt")};
            CMLCreate create2 = new CMLCreate("1", parentReference2, null, null, null, Constants.TYPE_CONTENT, properties2);
            CML cml2 = new CML();
            cml2.setCreate(new CMLCreate[]{create2});
            UpdateResult[] results2 = WebServiceFactory.getRepositoryService().update(cml2);  
            
            // Set content
            ContentFormat format = new ContentFormat(Constants.MIMETYPE_TEXT_PLAIN, "UTF-8");
            byte[] content = "This is some test content provided by the Alfresco development team!".getBytes();
            WebServiceFactory.getContentService().write(results2[0].getDestination(), Constants.PROP_CONTENT, content, format);
            
        }
    }
}
