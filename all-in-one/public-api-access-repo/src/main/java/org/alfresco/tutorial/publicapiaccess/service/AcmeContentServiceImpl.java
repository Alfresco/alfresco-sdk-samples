/**
 * Copyright (C) 2015 Alfresco Software Limited.
 * <p/>
 * This file is part of the Alfresco SDK Samples project.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.alfresco.tutorial.publicapiaccess.service;

import org.alfresco.model.ContentModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.repo.nodelocator.CompanyHomeNodeLocator;
import org.alfresco.repo.transaction.RetryingTransactionHelper;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.alfresco.tutorial.publicapiaccess.model.AcmeContract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.alfresco.tutorial.publicapiaccess.model.AcmeContentModel.*;

/**
 * An implementation of the ACME content service that uses the Alfresco Public API to fetch content and metadata.
 * This implementation uses the best practice approach with the RetryingTransactionHelper.
 *
 * @author martin.bergljung@alfresco.com
 * @version 1.0
 */
public class AcmeContentServiceImpl implements AcmeContentService {
    private static Logger logger = LoggerFactory.getLogger(AcmeContentServiceImpl.class);

    /**
     * The Alfresco Service Registry that gives access to all public content services in Alfresco.
     */
    private ServiceRegistry serviceRegistry;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * Create a contract file under the /Company Home folder.
     * This will be done in a read-write transaction, retry until successful or 20 trials.
     * Joins an ongoing transaction if one exists.
     */
    public NodeRef createContractFile(final String filename, final String contractTxt, final AcmeContract contract) {
        NodeRef nodeRefForContract = serviceRegistry.getRetryingTransactionHelper().doInTransaction(
                new RetryingTransactionHelper.RetryingTransactionCallback<NodeRef>() {
                    public NodeRef execute() throws Throwable {
                        NodeRef parentFolderNodeRef =
                                serviceRegistry.getNodeLocatorService().getNode(CompanyHomeNodeLocator.NAME, null, null);

                        // Create Node metadata
                        QName associationType = ContentModel.ASSOC_CONTAINS;
                        QName associationQName = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI,
                                QName.createValidLocalName(filename));
                        Map<QName, Serializable> nodeProperties = new HashMap<QName, Serializable>();
                        nodeProperties.put(ContentModel.PROP_NAME, filename);
                        nodeProperties.put(DocumentType.Prop.DOCUMENT_ID, contract.getDocumentId());
                        nodeProperties.put(SecurityClassifiedAspect.Prop.SECURITY_CLASSIFICATION,
                                contract.getSecurityClassificationLevel());
                        nodeProperties.put(ContractType.Prop.CONTRACT_NAME, contract.getContractName());
                        nodeProperties.put(ContractType.Prop.CONTRACT_ID, contract.getContractId());
                        ChildAssociationRef parentChildAssocRef = serviceRegistry.getNodeService().createNode(
                                parentFolderNodeRef, associationType, associationQName, ContractType.QNAME, nodeProperties);

                        NodeRef newFileNodeRef = parentChildAssocRef.getChildRef();

                        // Set content for node
                        boolean updateContentPropertyAutomatically = true;
                        ContentWriter writer = serviceRegistry.getContentService().getWriter(
                                newFileNodeRef, ContentModel.PROP_CONTENT, updateContentPropertyAutomatically);
                        writer.setMimetype(MimetypeMap.MIMETYPE_TEXT_PLAIN);
                        writer.setEncoding("UTF-8");
                        writer.putContent(contractTxt);

                        return newFileNodeRef;
                    }
                });

        return nodeRefForContract;
    }

    /**
     * Apply the acme:webPublished aspect to the content item with passed in node reference.
     * This will be done in a read-write transaction, retry until successful or 20 trials.
     * Joins an ongoing transaction if one exists.
     *
     * @param nodeRef the Alfresco Repo node reference to apply the aspect to
     */
    public void applyWebPublishedAspect(final NodeRef nodeRef) {
        Boolean result = serviceRegistry.getRetryingTransactionHelper().doInTransaction(
                new RetryingTransactionHelper.RetryingTransactionCallback<Boolean>() {
                    public Boolean execute() throws Throwable {
                        Map<QName, Serializable> aspectProperties = new HashMap<QName, Serializable>();
                        aspectProperties.put(WebPublishedAspect.Prop.PUBLISHED_DATE, new Date());
                        serviceRegistry.getNodeService().addAspect(nodeRef, WebPublishedAspect.QNAME, aspectProperties);

                        return true;
                    }
                });
    }

    /**
     * Get the node reference for the /Company Home top folder in Alfresco.
     * Use the standard node locator service.
     *
     * @return Alfresco node referenece for the /Company Home folder
     */
    private NodeRef getCompanyHomeNodeRef() {
        return serviceRegistry.getNodeLocatorService().getNode(CompanyHomeNodeLocator.NAME, null, null);
    }
}
