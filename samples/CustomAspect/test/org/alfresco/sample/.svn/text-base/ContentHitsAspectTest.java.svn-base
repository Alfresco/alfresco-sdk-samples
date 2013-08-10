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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.UserTransaction;

import junit.framework.TestCase;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.security.authentication.AuthenticationComponent;
import org.alfresco.service.cmr.repository.ContentService;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.alfresco.util.ApplicationContextHelper;
import org.springframework.context.ApplicationContext;

/**
 * Content hits apsect sample unit test.
 * 
 * @author Roy Wetherall
 */
public class ContentHitsAspectTest extends TestCase
{
    private static ApplicationContext applicationContext = ApplicationContextHelper.getApplicationContext();
    protected NodeService nodeService;
    protected ContentService contentService;
    protected TransactionService transactionService;
    protected AuthenticationComponent authenticationComponent;

    
    @Override
    public void setUp()
    {
        nodeService = (NodeService)applicationContext.getBean("nodeService");
        contentService = (ContentService)applicationContext.getBean("contentService");
        authenticationComponent = (AuthenticationComponent)applicationContext.getBean("authenticationComponent");
        transactionService = (TransactionService)applicationContext.getBean("transactionComponent");
    
        // Authenticate as the system user
        authenticationComponent.setSystemUserAsCurrentUser();
    }

    @Override
    public void tearDown()
    {
        authenticationComponent.clearCurrentSecurityContext();
    }
    
    
    /**
     * Test the contentHits aspect behaviour
     */
    public void testContentHitsApsectBehaviour() throws Throwable
    {
        NodeRef nodeRef = null;
        
        UserTransaction userTransaction1 = transactionService.getUserTransaction();
        try
        {
            userTransaction1.begin();
        
            // Create the store and get the root node
            StoreRef storeRef = this.nodeService.createStore(StoreRef.PROTOCOL_WORKSPACE, "Test_" + System.currentTimeMillis());
            NodeRef rootNodeRef = this.nodeService.getRootNode(storeRef);

            // Create the content node
            Map<QName, Serializable> properties = new HashMap<QName, Serializable>(1);
            properties.put(ContentModel.PROP_NAME, "contentHits.txt");
            nodeRef = nodeService.createNode(rootNodeRef, ContentModel.ASSOC_CHILDREN, QName.createQName("{contentHitsAspectTest}countedContent"),
                    ContentModel.TYPE_CONTENT, properties).getChildRef();
            
            // Apply the content hits aspect
            nodeService.addAspect(nodeRef, ContentHitsAspect.ASPECT_CONTENT_HITS, null);
            
            // Check the count hit values
            checkHitCountValues(nodeService, nodeRef, 0, 0);
            
            // Add some content to the node
            ContentWriter contentWriter = contentService.getWriter(nodeRef, ContentModel.PROP_CONTENT, true);
            contentWriter.setEncoding("UTF-8");
            contentWriter.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
            contentWriter.putContent("Putting some initial content onto the node.");
            
            // Read the content a couple of times
            contentService.getReader(nodeRef, ContentModel.PROP_CONTENT);
            contentService.getReader(nodeRef, ContentModel.PROP_CONTENT);
            
            // Update the content again
            ContentWriter contentWriter2 = contentService.getWriter(nodeRef, ContentModel.PROP_CONTENT, true);
            contentWriter2.putContent("Updating the existing content.");

            // Check the content hit values
            checkHitCountValues(nodeService, nodeRef, 0, 0);

            userTransaction1.commit();
        }
        catch(Throwable e)
        {
            try { userTransaction1.rollback(); } catch (IllegalStateException ee) {}
            throw e;
        }        

        UserTransaction userTransaction2 = transactionService.getUserTransaction();
        try
        {
            userTransaction2.begin();
            checkHitCountValues(nodeService, nodeRef, 1, 1);
            userTransaction2.rollback();
        }
        catch(Exception e)
        {
            try { userTransaction2.rollback(); } catch (IllegalStateException ee) {}
            throw e;
        }        
    }
    
    /**
     * Helper method to check that contentHits apsect currently holds the expected values
     * 
     * @param nodeService               the node service
     * @param nodeRef                   the node reference
     * @param expectedUpdateCount       the expected update count value
     * @param expectedReadCount         the expected read count value
     */
    private synchronized void checkHitCountValues(
            NodeService nodeService,
            NodeRef nodeRef,
            int expectedUpdateCount,
            int expectedReadCount)
    {
        int currentReadCount = 0;
        int currentUpdateCount = 0;
        // Loop for 5 seconds, testing for the value
        for (int i = 0; i < 5; i++)
        {
            try { this.wait(1000L); } catch (InterruptedException e) {}
            
            // Get the read count value
            currentReadCount = ((Integer)nodeService.getProperty(nodeRef, ContentHitsAspect.PROP_READ_COUNT)).intValue();
            // Get the update count value
            currentUpdateCount = ((Integer)nodeService.getProperty(nodeRef, ContentHitsAspect.PROP_UPDATE_COUNT)).intValue();
            
            if (expectedUpdateCount == currentUpdateCount && expectedReadCount == currentReadCount)
            {
                // Got it
                return;
            }
        }
        fail("Content Hits incorrect: \n" +
                "   Expected read count:  " + expectedReadCount + "\n" +
                "   Actual read count:    " + currentReadCount + "\n" +
                "   Expected write count: " + expectedUpdateCount + "\n" +
                "   Actual write count:   " + currentUpdateCount);
    }
}
