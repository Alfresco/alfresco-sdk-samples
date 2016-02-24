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

import org.alfresco.rest.api.model.DocumentRatingSummary;

/**
 * Class representing the summary of all ratings with the US TV rating scheme.
 *
 * @author martin.bergljung@alfresco.com
 */
public class UsTvRatingSummary implements DocumentRatingSummary {
    private Integer numberOfRatings;
    private Float average;

    public UsTvRatingSummary(Integer numberOfRatings, Float ratingTotal, Float average) {
        super();
        this.numberOfRatings = numberOfRatings;
        this.average = (average == -1 ? null : average);
    }

    public Integer getNumberOfRatings() {
        return numberOfRatings;
    }

    public Float getAverage() {
        return average;
    }

    @Override
    public String toString() {
        return "UsTvRatingSummary [numberOfRatings=" + numberOfRatings + ", average=" + average + "]";
    }
}
