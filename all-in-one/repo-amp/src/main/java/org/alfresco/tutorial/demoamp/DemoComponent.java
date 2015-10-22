/*
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.alfresco.tutorial.demoamp;

import org.alfresco.repo.module.AbstractModuleComponent;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A basic component that will be started for this module.
 * Uses the NodeLocatorService to easily find nodes and the
 * NodeService to display them
 *
 * @author Gabriele Columbro
 * @author Maurizio Pillitu
 */
public class DemoComponent extends AbstractModuleComponent {
    Log log = LogFactory.getLog(DemoComponent.class);

    /**
     * This is the main method that needs to be implemented in every module component.
     * It does the actual work.
     */
    @Override
    protected void executeInternal() throws Throwable {
        System.out.println("DemoComponent has been executed");
        log.debug("Test debug logging. Number of nodes in Company Home = " + childNodesCount(getCompanyHome()));
        log.info("This is only for information purposed. Better remove me from the log in Production");
    }

    /**
     * This is a demo service interaction with Alfresco Foundation API.
     * This sample method returns the number of child nodes of a certain type
     * under a certain node.
     *
     * @return
     */
    public int childNodesCount(NodeRef nodeRef) {
        return serviceRegistry.getNodeService().countChildAssocs(nodeRef, true);
    }

    /**
     * Returns the NodeRef of "Company Home"
     *
     * @return
     */
    public NodeRef getCompanyHome() {
        return serviceRegistry.getNodeLocatorService().getNode("companyhome", null, null);
    }
}
