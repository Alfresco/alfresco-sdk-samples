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

package org.alfresco.tutorial.scheduledjob.jobs;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.schedule.AbstractScheduledLockedJob;
import org.alfresco.tutorial.scheduledjob.actions.ScheduledJobExecuter;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

/**
 * Scheduled Job implementation.
 * <p/>
 * Extends the AbstractScheduledLockedJob class that has job lock service functionality to lock job so
 * it can run safely in a cluster.
 * <p/>
 * Important: implement StatefulJob so the job is not triggered concurrently by the scheduler
 *
 * @author martin.bergljung@alfresco.com
 */
public class ScheduledJob extends AbstractScheduledLockedJob implements StatefulJob {
    @Override
    public void executeJob(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobData = context.getJobDetail().getJobDataMap();

        // Extract the Job executer to use
        Object executerObj = jobData.get("jobExecuter");
        if (executerObj == null || !(executerObj instanceof ScheduledJobExecuter)) {
            throw new AlfrescoRuntimeException(
                    "ScheduledJob data must contain valid 'Executer' reference");
        }

        final ScheduledJobExecuter jobExecuter = (ScheduledJobExecuter) executerObj;

        // Execute the scheduled job as a specific user
        AuthenticationUtil.runAs(new AuthenticationUtil.RunAsWork<Object>() {
            public Object doWork() throws Exception {
                jobExecuter.execute();
                return null;
            }
        }, AuthenticationUtil.getSystemUserName());
    }
}