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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A dummy implementation of a Cloud Service integration to extract named entities from text.
 *
 * @author martin.bergljung@alfresco.com
 */
public class DummyNamedEntitiesServiceImpl implements NamedEntitiesService {
    private static final Log LOG = LogFactory.getLog(DummyNamedEntitiesServiceImpl.class);

    /**
     * Properties used to connect to the remote named entities semantic cloud service.
     * Not used in this dummy implementation.
     */
    private String remoteServiceHostname;
    private String remoteServiceAipKey;
    private String remoteServicePort;

    /**
     * Service registry for access to Alfresco public API
     */
    private ServiceRegistry serviceRegistry;

    public void setRemoteServiceHostname(String remoteServiceHostname) {
        this.remoteServiceHostname = remoteServiceHostname;
    }

    public void setRemoteServiceAipKey(String remoteServiceAipKey) {
        this.remoteServiceAipKey = remoteServiceAipKey;
    }

    public void setRemoteServicePort(String remoteServicePort) {
        this.remoteServicePort = remoteServicePort;
    }

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    public void extractNamedEntities(NodeRef nodeRef) {
        if (serviceRegistry.getNodeService().exists(nodeRef)) {
            LOG.info("Extracting named entities for node [nodeRef=" + nodeRef + "]");

            // Dummy implementation that does nothing
            // In a real implementation we would:
            // 1) extract the text for the node via for example NodeService and ContentService
            // 2) call the remote service with the text to get the named entities
            // 3) add a custom NamedEntities aspect to the node with the extracted entities, such as person, company, country etc.
        } else {
            LOG.error("Node does not exist [nodeRef=" + nodeRef + "]");
        }
    }

}
