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
package org.alfresco.tutorial.rating.evaluator;

import org.alfresco.web.evaluator.BaseEvaluator;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.extensions.surf.RequestContext;
import org.springframework.extensions.surf.ServletUtil;
import org.springframework.extensions.surf.exception.ConnectorServiceException;
import org.springframework.extensions.surf.support.ThreadLocalRequestContext;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.connector.Connector;
import org.springframework.extensions.webscripts.connector.Response;

/**
 * A rating evaluator that evaluates to true if the
 * node has been rated by current user with the set
 * rating scheme.
 *
 * @author martin.bergljung@alfresco.com
 */
public class IsRatedEvaluator extends BaseEvaluator {
    private static final String PROP_NODEREF = "nodeRef";
    private static final String PROP_NODE_CREATOR = "cm:creator";
    private static final String PROP_NODE_CREATOR_USERNAME = "userName";
    private static final String PROP_RATING_CALL_RESPONSE_LIST = "list";
    private static final String PROP_RATING_CALL_RESPONSE_ENTRIES = "entries";
    private static final String PROP_RATING_CALL_RESPONSE_ENTRY = "entry";
    private static final String PROP_RATING_CALL_RESPONSE_MY_RATING = "myRating";
    private static final String PROP_RATING_CALL_RESPONSE_SCHEME_ID = "id";

    /**
     * The rating scheme that this evaluator will check for, for example 'fiveStar'.
     */
    private String ratingSchemeShortName;

    public void setRatingSchemeShortName(String ratingSchemeShortName) {
        this.ratingSchemeShortName = ratingSchemeShortName;
    }

    /**
     * If this is content not created by current user, then check if current user has rated with five star rating scheme.
     * Using the Rating REST API
     * Call Example:
     * http://localhost:8080/alfresco/api/-default-/public/alfresco/versions/1/nodes/55e1cf8b-ac7a-4da5-9b3b-7ba8769923ab/ratings
     * Response:
     {
     list: {
         pagination: {
                count: 2,
                hasMoreItems: false,
                totalItems: 2,
                skipCount: 0,
                maxItems: 100
        },
        entries: [
             {
                 entry: {
                     myRating: 4,
                     ratedAt: "2016-02-16T14:27:40.353+0000",
                     id: "fiveStar",
                     aggregate: {
                         numberOfRatings: 1,
                         average: 4
                     }
                 }
             },
             {
                 entry: {
                     id: "likes",
                         aggregate: {
                         numberOfRatings: 0
                     }
                 }
             }
         ]
         }
     }
     *
     * @param jsonObject all the information about the node being evaluated
     * @return true if evaluation succeeded (i.e. has been rated with fiveStar), otherwise false
     */
    @Override
    public boolean evaluate(JSONObject jsonObject) {
        // Check if user is trying to rate content that he or she created
        JSONObject creator = (JSONObject)getProperty(jsonObject, PROP_NODE_CREATOR);
        String creatorUserName = (String)creator.get(PROP_NODE_CREATOR_USERNAME);
        if (getUserId().equals(creatorUserName)) {
            // You cannot rate your own content when using the fiveStar rating scheme
            return true;
        }

        // Get the ID for the node we are rating
        String nodeId = getNodeId(jsonObject.get(PROP_NODEREF));
        if (nodeId == null) {
            return false;
        }

        // Call Rating REST API to check if user has already rated node
        final RequestContext rc = ThreadLocalRequestContext.getRequestContext();
        final String userId = rc.getUserId();

        try {
            Connector conn = rc.getServiceRegistry().getConnectorService()
                    .getConnector("alfresco-api", userId, ServletUtil.getSession());

            Response response = conn.call(
                    "/-default-/public/alfresco/versions/1/nodes/" + nodeId + "/ratings");

            if (response.getStatus().getCode() == Status.STATUS_OK) {
                try {
                    // Use a different JSONObject class that can parse the JSON response String
                    org.json.JSONObject json = new org.json.JSONObject(response.getResponse());
                    org.json.JSONObject responseList = (org.json.JSONObject) json.get(PROP_RATING_CALL_RESPONSE_LIST);
                    org.json.JSONArray responseEntries = (org.json.JSONArray)
                            responseList.get(PROP_RATING_CALL_RESPONSE_ENTRIES);
                    if (responseEntries.length() > 0) {
                        for (int i = 0; i < responseEntries.length(); i++) {
                            org.json.JSONObject item = responseEntries.getJSONObject(i);
                            org.json.JSONObject entry = item.getJSONObject(PROP_RATING_CALL_RESPONSE_ENTRY);
                            if (entry.has(PROP_RATING_CALL_RESPONSE_MY_RATING)) {
                                String currentUserRating = entry.getString(PROP_RATING_CALL_RESPONSE_MY_RATING);
                                String ratingScheme = entry.getString(PROP_RATING_CALL_RESPONSE_SCHEME_ID);
                                if (ratingSchemeShortName.equals(ratingScheme) && currentUserRating != null) {
                                    // Current user has done a rating with the rating scheme that is associated with
                                    // this evaluator, so don't allow the user to rate again
                                    return true;
                                }
                            }
                        }
                        return false;
                    }
                } catch (JSONException je) {
                    je.printStackTrace();
                    return false;
                }
            } else {
                return false;
            }
        } catch (ConnectorServiceException cse) {
            cse.printStackTrace();
            return false;
        }

        return false;
    }

    private String getNodeId(Object nodeRefVal) {
        if (nodeRefVal == null) {
            return null;
        }
        String nodeRef = (String) nodeRefVal;
        int lastForwardSlash = nodeRef.lastIndexOf('/');
        if (lastForwardSlash == -1) {
            return null;
        }
        return nodeRef.substring(lastForwardSlash + 1);
    }

}