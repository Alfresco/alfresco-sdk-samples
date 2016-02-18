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

package org.alfresco.tutorial.rating.repoaction;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.model.ContentModel;
import org.alfresco.repo.action.ParameterDefinitionImpl;
import org.alfresco.repo.action.executer.ActionExecuterAbstractBase;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.action.Action;
import org.alfresco.service.cmr.action.ParameterDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.rating.Rating;
import org.alfresco.service.cmr.rating.RatingServiceException;
import org.alfresco.service.cmr.repository.NodeRef;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.Serializable;
import java.util.*;

/**
 * Alfresco Repository Action that can rate a node based on passed in
 * Five Star rating.
 *
 * @author martin.bergljung@alfresco.com
 */
public class RateNodeActionExecuter extends ActionExecuterAbstractBase {
    private static Log LOG = LogFactory.getLog(RateNodeActionExecuter.class);

    private static final String FIVE_STAR_SCHEME_NAME = "fiveStarRatingScheme";
    private static final String PARAM_FIVESTAR_RATING_NAME = "star_rating";

    /**
     * The Alfresco Service Registry that gives access to all public content services in Alfresco.
     */
    private ServiceRegistry serviceRegistry;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    protected void addParameterDefinitions(List<ParameterDefinition> paramList) {
        paramList.add(new ParameterDefinitionImpl(
                PARAM_FIVESTAR_RATING_NAME,
                DataTypeDefinition.TEXT,
                true,
                getParamDisplayLabel(PARAM_FIVESTAR_RATING_NAME)));
    }

    @Override
    protected void executeImpl(Action action, NodeRef actionedUponNodeRef) {
        if (serviceRegistry.getNodeService().exists(actionedUponNodeRef)) {
            Serializable nodeName = serviceRegistry.getNodeService().getProperty(
                    actionedUponNodeRef, ContentModel.PROP_NAME);

            // Get the rating property entered via Share Form
            String fiveStarRating = (String) action.getParameterValue(PARAM_FIVESTAR_RATING_NAME);
            Float rating = 0F;
            try {
                rating = Float.parseFloat(fiveStarRating);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
                throw new AlfrescoRuntimeException("Incorrect rating number: " + fiveStarRating +
                        " [err=" + nfe.getMessage() + "]");
            }

            // Create a five star rating based on passed in value and
            // the user that is currently authenticated
            try {
                serviceRegistry.getRatingService().applyRating(actionedUponNodeRef, rating, FIVE_STAR_SCHEME_NAME);
            } catch (RatingServiceException re) {
                LOG.error("Failed to apply rating [node=" + nodeName + "][rating=" + fiveStarRating + "]: " +
                        re.getMessage());
                return;
            }

            // Get and log rating
            Rating currentUserRating = serviceRegistry.getRatingService().getRatingByCurrentUser(
                    actionedUponNodeRef, FIVE_STAR_SCHEME_NAME);
            LOG.info("Successfully rated " + nodeName + " [by=" + currentUserRating.getAppliedBy() +
                    "][scheme=" + currentUserRating.getScheme() + "][score=" + currentUserRating.getScore() + "]");
        }
    }
}
