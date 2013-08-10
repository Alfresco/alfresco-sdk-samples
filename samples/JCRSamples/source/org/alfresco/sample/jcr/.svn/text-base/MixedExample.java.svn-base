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
package org.alfresco.sample.jcr;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.Repository;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import org.alfresco.jcr.api.JCRNodeRef;
import org.alfresco.model.ContentModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * Example that demonstrate use of JCR and Alfresco API calls.
 * 
 * @author David Caruana
 */
public class MixedExample
{

    public static void main(String[] args)
        throws Exception
    {
        // Setup Spring and Transaction Service
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:alfresco/application-context.xml");
        ServiceRegistry registry = (ServiceRegistry)context.getBean(ServiceRegistry.SERVICE_REGISTRY);
        NodeService nodeService = (NodeService)registry.getNodeService();
        
        // Retrieve Repository
        Repository repository = (Repository)context.getBean("JCR.Repository");

        // Login to workspace
        // Note: Default workspace is the one used by Alfresco Web Client which contains all the Spaces
        //       and their documents
        Session session = repository.login(new SimpleCredentials("admin", "admin".toCharArray()));

        try
        {
            // Retrieve Company Home
            Node root = session.getRootNode();
            Node companyHome = root.getNode("app:company_home");

            // Read Company Home Name
            Property name = companyHome.getProperty("cm:name");
            System.out.println("Name = " + name.getString());
            
            // Update Node via Alfresco Node Service API
            NodeRef companyHomeRef = JCRNodeRef.getNodeRef(companyHome);
            nodeService.setProperty(companyHomeRef, ContentModel.PROP_NAME, "Updated Company Home Name");
            
            // Re-read via JCR
            System.out.println("Updated name = " + name.getString());
        }
        finally
        {
            session.logout();
            System.exit(0);
        }
    }
    
}
