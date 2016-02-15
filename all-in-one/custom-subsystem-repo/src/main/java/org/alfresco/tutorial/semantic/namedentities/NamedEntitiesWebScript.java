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
package org.alfresco.tutorial.semantic.namedentities;

import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.cmr.repository.StoreRef;
import org.apache.commons.lang3.StringUtils;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.WebScriptRequest;
import org.springframework.extensions.webscripts.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * A Web Script that is used to call the Named Entities Service.
 *
 * @author martin.bergljung@alfresco.com
 * @version 1.0
 */
public class NamedEntitiesWebScript extends DeclarativeWebScript {
    private static final String NODEID_PARAM_NAME = "nodeId";

    /**
     * Service registry for access to Alfresco public API
     */
    private ServiceRegistry serviceRegistry;

    /**
     * Named entities service to call to extract entities for text
     */
    private NamedEntitiesService namedEntitiesService;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void setNamedEntitiesService(NamedEntitiesService namedEntitiesService) {
        this.namedEntitiesService = namedEntitiesService;
    }

    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest webScriptRequest, Status status) {
        Map<String, Object> model = new HashMap<String, Object>();

        String nodeId = webScriptRequest.getParameter(NODEID_PARAM_NAME);

        if (StringUtils.isBlank(nodeId)) {
            status.setCode(400, "Failed entity extraction: required node ID data has not been provided");
            status.setRedirect(true);
        } else {
            NodeRef nodeRef = new NodeRef(StoreRef.STORE_REF_WORKSPACE_SPACESSTORE + "/" + nodeId);
            if (!serviceRegistry.getNodeService().exists(nodeRef)) {
                status.setCode(404, "Failed entity extraction: no node found for node reference:" + nodeRef);
                status.setRedirect(true);
            } else {
                namedEntitiesService.extractNamedEntities(nodeRef);

                model.put("message", "Successfully completed named entities extraction");
                model.put("nodeId", nodeId);
            }
        }

        return model;
    }
}
