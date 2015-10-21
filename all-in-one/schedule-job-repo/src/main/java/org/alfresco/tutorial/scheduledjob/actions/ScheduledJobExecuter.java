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

package org.alfresco.tutorial.scheduledjob.actions;

import org.alfresco.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class implements the work that should be done when the scheduled job is triggered.
 *
 * @author martin.bergljung@alfresco.com
 */
public class ScheduledJobExecuter {
    private static final Logger LOG = LoggerFactory.getLogger(ScheduledJobExecuter.class);

    /**
     * Public API access
     */
    private ServiceRegistry serviceRegistry;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    /**
     * Executer implementation
     */
    public void execute() {
        LOG.info("Running the scheduled job");

        // Work/Job implementation goes here...
    }

}
