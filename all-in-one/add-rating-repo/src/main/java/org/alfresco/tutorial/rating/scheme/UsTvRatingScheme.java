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

package org.alfresco.tutorial.rating.scheme;

import org.alfresco.rest.api.impl.node.ratings.AbstractRatingScheme;
import org.alfresco.rest.api.model.DocumentRatingSummary;
import org.alfresco.rest.framework.core.exceptions.InvalidArgumentException;
import org.alfresco.service.cmr.rating.RatingServiceException;
import org.alfresco.service.cmr.repository.NodeRef;

/**
 * The REST APIs representation of the 'US TV' rating scheme.
 *
 * @author martin.bergljung@alfresco.com
 */
public class UsTvRatingScheme extends AbstractRatingScheme {

    public UsTvRatingScheme() {
        super("usTv", "usTvRatingScheme");
    }

    public Float getRatingServiceRating(Object rating) {
        Float ratingToApply = null;

        if (rating instanceof Integer) {
            ratingToApply = ((Integer) rating).floatValue();
        } else {
            throw new InvalidArgumentException("Rating should be non-null and an integer for 'usTv' rating scheme.");
        }

        validateRating(ratingToApply);

        return ratingToApply;
    }

    public Object getApiRating(Float rating) {
        Object apiRating = Integer.valueOf(rating.intValue());
        return apiRating;
    }

    public DocumentRatingSummary getDocumentRatingSummary(NodeRef nodeRef) {
        return new UsTvRatingSummary(ratingService.getRatingsCount(nodeRef, ratingSchemeName),
                ratingService.getTotalRating(nodeRef, ratingSchemeName),
                ratingService.getAverageRating(nodeRef, ratingSchemeName));
    }

    @Override
    public void applyRating(NodeRef nodeRef, Object rating) {
        try {
            Float ratingServiceRating = getRatingServiceRating(rating);
            ratingService.applyRating(nodeRef, ratingServiceRating, getRatingServiceName());
        } catch (RatingServiceException e) {
            throw new InvalidArgumentException(e.getMessage());
        }
    }

    @Override
    public void removeRating(NodeRef nodeRef) {
        try {
            ratingService.removeRatingByCurrentUser(nodeRef, getRatingServiceName());
        } catch (RatingServiceException e) {
            throw new InvalidArgumentException(e.getMessage());
        }
    }
}
