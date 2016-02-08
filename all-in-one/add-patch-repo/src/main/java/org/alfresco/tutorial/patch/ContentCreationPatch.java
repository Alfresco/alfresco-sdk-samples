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

package org.alfresco.tutorial.patch;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.admin.patch.AbstractPatch;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.ChildAssociationRef;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.extensions.surf.util.I18NUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A patch demonstrating creating content during bootstrapping of the repository.
 *
 * @author martin.bergljung@alfresco.com
 */
public class ContentCreationPatch extends AbstractPatch {
    private static Log logger = LogFactory.getLog(ContentCreationPatch.class);
    private static final String PATCH_ID = "org.alfresco.tutorial.patch.contentCreationPatch";
    private static final String MSG_SUCCESS = PATCH_ID + ".result";
    private static final String MSG_ERROR = PATCH_ID + ".error";

    /**
     * The Alfresco Service Registry that gives access to all public content services in Alfresco.
     */
    private ServiceRegistry serviceRegistry;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    protected String applyInternal() throws Exception {
        logger.info("Starting execution of patch: " + I18NUtil.getMessage(PATCH_ID));

        // Get the store reference for the Repository store that contains live content
        StoreRef store = StoreRef.STORE_REF_WORKSPACE_SPACESSTORE;

        // Get root node for store
        NodeRef rootRef = serviceRegistry.getNodeService().getRootNode(store);

        // Do the patch work
        createFolder(getCompanyHomeNodeRef(rootRef));

        logger.info("Finished execution of patch: " + I18NUtil.getMessage(PATCH_ID));

        return I18NUtil.getMessage(MSG_SUCCESS);
    }

    private NodeRef getCompanyHomeNodeRef(NodeRef rootNodeRef) {
        String companyHomeXPath = "/app:company_home";

        List<NodeRef> refs = searchService.selectNodes(rootNodeRef, companyHomeXPath, null,
                serviceRegistry.getNamespaceService(), false);
        if (refs.size() != 1) {
            throw new AlfrescoRuntimeException(I18NUtil.getMessage(MSG_ERROR,
                    "Company home could not be found, XPATH query " + companyHomeXPath +
                            " returned " + refs.size() + " nodes."));
        }

        return refs.get(0);
    }

    private void createFolder(NodeRef rootRef) {
        String folderName = "FolderCreatedByPatch";
        NodeRef parentFolderNodeRef = rootRef;

        // Create Node
        QName associationType = ContentModel.ASSOC_CONTAINS;
        QName associationQName = QName.createQName(NamespaceService.CONTENT_MODEL_1_0_URI,
                QName.createValidLocalName(folderName));
        QName nodeType = ContentModel.TYPE_FOLDER;
        Map<QName, Serializable> nodeProperties = new HashMap<QName, Serializable>();
        nodeProperties.put(ContentModel.PROP_NAME, folderName);
        ChildAssociationRef parentChildAssocRef = nodeService.createNode(
                parentFolderNodeRef, associationType, associationQName, nodeType, nodeProperties);

        NodeRef newFolderNodeRef = parentChildAssocRef.getChildRef();

        // Add an aspect to the node
        Map<QName, Serializable> aspectProperties = new HashMap<QName, Serializable>();
        aspectProperties.put(ContentModel.PROP_TITLE, "My Patch Folder");
        aspectProperties.put(ContentModel.PROP_DESCRIPTION, "This is a folder that has been created by a patch");
        nodeService.addAspect(newFolderNodeRef, ContentModel.ASPECT_TITLED, aspectProperties);
    }
}
