/*
 * Copyright (C) 2005-2012 Alfresco Software Limited.
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

import java.util.Date;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import org.alfresco.repo.content.ContentServicePolicies;
import org.alfresco.repo.node.NodeServicePolicies;
import org.alfresco.repo.policy.Behaviour.NotificationFrequency;
import org.alfresco.repo.policy.BehaviourFilter;
import org.alfresco.repo.policy.JavaBehaviour;
import org.alfresco.repo.policy.PolicyComponent;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.repo.security.authentication.AuthenticationUtil.RunAsWork;
import org.alfresco.repo.tenant.TenantService;
import org.alfresco.repo.transaction.AlfrescoTransactionSupport;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.repo.transaction.RetryingTransactionHelper.RetryingTransactionCallback;
import org.alfresco.repo.transaction.TransactionListener;
import org.alfresco.repo.transaction.TransactionListenerAdapter;
import org.alfresco.repo.transaction.TransactionalResourceHelper;
import org.alfresco.service.cmr.repository.InvalidNodeRefException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.NodeService;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.transaction.TransactionService;
import org.alfresco.util.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class contains the behaviour behind the 'my:contentHits' aspect.
 * <p>
 * Access to content readers and writers does not necessarily have to be done in
 * writable transactions.  This aspect behaviour demonstrates how the hit count
 * incrementing is done after the transaction.
 * 
 * @author Roy Wetherall
 * @author Derek Hulley
 */
public class ContentHitsAspect implements ContentServicePolicies.OnContentReadPolicy,
                                          ContentServicePolicies.OnContentUpdatePolicy,
                                          NodeServicePolicies.OnAddAspectPolicy
{
    /** A key that keeps track of nodes that need read count increments */
    private static final String KEY_CONTENT_HITS_READS = ContentHitsAspect.class.getName() + ".reads";
    /** A key that keeps track of nodes that need write count increments */
    private static final String KEY_CONTENT_HITS_WRITES = ContentHitsAspect.class.getName() + ".writes";
    
    /* Aspect names */
    public static final QName ASPECT_CONTENT_HITS = QName.createQName("extension.contenthits", "contentHits");
    
    /* Property names */
    public static final QName PROP_COUNT_STARTED_DATE = QName.createQName("extension.contenthits", "countStartedDate");
    public static final QName PROP_UPDATE_COUNT = QName.createQName("extension.contenthits", "updateCount");
    public static final QName PROP_READ_COUNT = QName.createQName("extension.contenthits", "readCount");
    
    private static Log logger = LogFactory.getLog(ContentHitsAspect.class);
    
    private PolicyComponent policyComponent;
    private BehaviourFilter policyFilter;
    private NodeService nodeService;
    private TenantService tenantService;
    private TransactionService transactionService;
    private ThreadPoolExecutor threadExecuter;
    private TransactionListener transactionListener;

    /**
     * Default constructor for bean construction
     */
    public ContentHitsAspect()
    {
        this.transactionListener = new ContentHitsTransactionListener();
    }
    
    /**
     * Sets the policy component
     * 
     * @param policyComponent   the policy component
     */
    public void setPolicyComponent(PolicyComponent policyComponent)
    {
        this.policyComponent = policyComponent;
    }

    /**
     * Set the component to filter out behaviour
     * 
     * @param policyFilter      the policy behaviour filter
     */
    public void setPolicyFilter(BehaviourFilter policyFilter)
    {
        this.policyFilter = policyFilter;
    }

    /** 
     * Sets the node service 
     * 
     * @param nodeService   the node service
     */
    public void setNodeService(NodeService nodeService)
    {
        this.nodeService = nodeService;
    }
    
    /**
     * Sets the tenant service.
     * 
     * @param tenantService
     *            the tenant service
     */
    public void setTenantService(TenantService tenantService)
    {
        this.tenantService = tenantService;
    }

    /**
     * Set the service that allows new transactions
     * 
     * @param transactionService    the transaction service 
     */
    public void setTransactionService(TransactionService transactionService)
    {
        this.transactionService = transactionService;
    }

    /**
     * Set the thread pool that will handle the post-commit write transactions.
     * 
     * @param threadExecuter        an <i>executor</i>
     */
    public void setThreadExecuter(ThreadPoolExecutor threadExecuter)
    {
        this.threadExecuter = threadExecuter;
    }

    /**
     * Spring initilaise method used to register the policy behaviours
     */
    public void init()
    {
        // Register the policy behaviours
        this.policyComponent.bindClassBehaviour(
                                 QName.createQName(NamespaceService.ALFRESCO_URI, "onAddAspect"),
                                 ASPECT_CONTENT_HITS,
                                 new JavaBehaviour(this, "onAddAspect", NotificationFrequency.FIRST_EVENT));
        this.policyComponent.bindClassBehaviour(
                                 ContentServicePolicies.ON_CONTENT_READ,
                                 ASPECT_CONTENT_HITS,
                                 new JavaBehaviour(this, "onContentRead", NotificationFrequency.TRANSACTION_COMMIT));
        this.policyComponent.bindClassBehaviour(
                                 ContentServicePolicies.ON_CONTENT_UPDATE,
                                 ASPECT_CONTENT_HITS,
                                 new JavaBehaviour(this, "onContentUpdate", NotificationFrequency.TRANSACTION_COMMIT));
    }

    /**
     * onAddAspect policy behaviour.
     * <p>
     * Sets the count started date to the date/time at which the contentHits aspect was
     * first applied.
     * 
     * @param nodeRef           the node reference
     * @param aspectTypeQName   the qname of the aspect being applied
     */
    public void onAddAspect(NodeRef nodeRef, QName aspectTypeQName)
    {
        // Set the count started date
        this.nodeService.setProperty(nodeRef, PROP_COUNT_STARTED_DATE, new Date());
    }
    
    /**
     * onContentRead policy behaviour.
     * <p>
     * This adds the node to the list of nodes that require read count incremets after the transaction completes.
     * 
     * Increments the aspect's read count property by one.
     */
    public void onContentRead(NodeRef nodeRef)
    {
        // Bind the listener to the transaction
        AlfrescoTransactionSupport.bindListener(transactionListener);
        // Get the set of nodes read
        Set<Pair<NodeRef, String>> readNodeRefPairs = TransactionalResourceHelper.getSet(KEY_CONTENT_HITS_READS);
        readNodeRefPairs.add(new Pair<NodeRef, String>(nodeRef, tenantService.getCurrentUserDomain()));
    }

    /**
     * onContentUpdate policy behaviour.
     * <p>
     * This adds the node to the list of nodes that require write count incremets after the transaction completes.
     */
    public void onContentUpdate(NodeRef nodeRef, boolean newContent)
    {
        // Bind the listener to the transaction
        AlfrescoTransactionSupport.bindListener(transactionListener);
        // Get the set of nodes written
        Set<Pair<NodeRef, String>> writeNodeRefPairs = TransactionalResourceHelper.getSet(KEY_CONTENT_HITS_WRITES);
        writeNodeRefPairs.add(new Pair<NodeRef, String>(nodeRef, tenantService.getCurrentUserDomain()));
    }
    
    private class ContentHitsTransactionListener extends TransactionListenerAdapter
    {
        @Override
        public void afterCommit()
        {
            // Get all the nodes that need their read counts incremented
            Set<Pair<NodeRef, String>> readNodeRefPairs = TransactionalResourceHelper.getSet(KEY_CONTENT_HITS_READS);
            for (Pair<NodeRef, String> nodeRefPair : readNodeRefPairs)
            {
                Runnable runnable = new ContentHitsReadCountIncrementer(nodeRefPair.getFirst(), nodeRefPair.getSecond());
                threadExecuter.execute(runnable);
            }
            Set<Pair<NodeRef, String>> writeNodeRefPairs = TransactionalResourceHelper.getSet(KEY_CONTENT_HITS_WRITES);
            for (Pair<NodeRef, String> nodeRefPair : writeNodeRefPairs)
            {
                Runnable runnable = new ContentHitsWriteCountIncrementer(nodeRefPair.getFirst(), nodeRefPair.getSecond());
                threadExecuter.execute(runnable);
            }
        }
    }
    
    /**
     * The worker that will increment a node's content read count.
     * 
     * @author Derek Hulley
     */
    private class ContentHitsReadCountIncrementer implements Runnable
    {
        private final NodeRef nodeRef;
        private final String currentUserDomain;
        private ContentHitsReadCountIncrementer(NodeRef nodeRef, String currentUserDomain)
        {
            this.nodeRef = nodeRef;
            this.currentUserDomain = currentUserDomain;
        }
        /**
         * Increments the read count on the node
         */
        public void run()
        {
            final RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
            final RetryingTransactionCallback<Integer> callback = new RetryingTransactionCallback<Integer>()
            {
                public Integer execute() throws Throwable
                {
                    try
                    {
                        // Ensure that the policy doesn't refire for this node on this thread
                        // This won't prevent background processes from refiring, though
                        policyFilter.disableBehaviour(nodeRef, ASPECT_CONTENT_HITS);
                        
                        // Increment the read count property value
                        Integer currentValue = (Integer) nodeService.getProperty(nodeRef, PROP_READ_COUNT);
                        int newValue = currentValue.intValue() + 1;
                        nodeService.setProperty(nodeRef, PROP_READ_COUNT, newValue);
                        return (Integer) newValue;
                    }
                    finally
                    {
                        policyFilter.enableBehaviour(ASPECT_CONTENT_HITS);
                    }
                }
            };
            try
            {
                Integer newCount = AuthenticationUtil.runAs(new RunAsWork<Integer>()
                {
                    @Override
                    public Integer doWork() throws Exception
                    {
                        return txnHelper.doInTransaction(callback, false, true);
                    }
                }, tenantService.getDomainUser(AuthenticationUtil.getSystemUserName(), this.currentUserDomain));
                 
                // Done
                if (logger.isDebugEnabled())
                {
                    logger.debug(
                            "Incremented content read count on node: \n" +
                            "   Node:      " + nodeRef + "\n" +
                            "   New Count: " + newCount);
                }
            }
            catch (InvalidNodeRefException e)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("Unable to increment content read count on missing node: " + nodeRef);
                }
            }
            catch (Throwable e)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug(e);
                }
                logger.error("Failed to increment content read count on node: " + nodeRef);
                // We are the last call on the thread
            }
        }
    }
    
    /**
     * The worker that will increment a node's content write count.
     * 
     * @author Derek Hulley
     */
    private class ContentHitsWriteCountIncrementer implements Runnable
    {
        private final NodeRef nodeRef;
        private final String currentUserDomain;
        private ContentHitsWriteCountIncrementer(NodeRef nodeRef, String currentUserDomain)
        {
            this.nodeRef = nodeRef;
            this.currentUserDomain = currentUserDomain;
        }
        /**
         * Increments the write count on the node
         */
        public void run()
        {
            final RetryingTransactionHelper txnHelper = transactionService.getRetryingTransactionHelper();
            final RetryingTransactionCallback<Integer> callback = new RetryingTransactionCallback<Integer>()
            {
                public Integer execute() throws Throwable
                {
                    try
                    {
                        // Ensure that the policy doesn't refire for this node on this thread
                        // This won't prevent background processes from refiring, though
                        policyFilter.disableBehaviour(nodeRef, ASPECT_CONTENT_HITS);
                        
                        // Increment the read count property value
                        Integer currentValue = (Integer) nodeService.getProperty(nodeRef, PROP_UPDATE_COUNT);
                        int newValue = currentValue.intValue() + 1;
                        nodeService.setProperty(nodeRef, PROP_UPDATE_COUNT, newValue);
                        return (Integer) newValue;
                    }
                    finally
                    {
                        policyFilter.enableBehaviour(ASPECT_CONTENT_HITS);
                    }
                }
            };
            try
            {
                Integer newCount = AuthenticationUtil.runAs(new RunAsWork<Integer>()
                {
                    @Override
                    public Integer doWork() throws Exception
                    {
                        return txnHelper.doInTransaction(callback, false, true);
                    }
                }, tenantService.getDomainUser(AuthenticationUtil.getSystemUserName(), this.currentUserDomain));
                // Done
                if (logger.isDebugEnabled())
                {
                    logger.debug(
                            "Incremented content write count on node: \n" +
                            "   Node:      " + nodeRef + "\n" +
                            "   New Count: " + newCount);
                }
            }
            catch (InvalidNodeRefException e)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug("Unable to increment content write count on missing node: " + nodeRef);
                }
            }
            catch (Throwable e)
            {
                if (logger.isDebugEnabled())
                {
                    logger.debug(e);
                }
                logger.error("Failed to increment content write count on node: " + nodeRef);
                // We are the last call on the thread
            }
        }
    }
}
