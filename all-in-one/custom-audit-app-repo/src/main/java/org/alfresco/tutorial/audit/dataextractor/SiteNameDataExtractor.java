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

package org.alfresco.tutorial.audit.dataextractor;

import org.alfresco.repo.audit.extractor.AbstractDataExtractor;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * A custom data extractor that extracts the Share site identifier/short name.
 *
 * @author martin.bergljung@alfresco.com
 */
public class SiteNameDataExtractor extends AbstractDataExtractor {

    /**
     * @return true if this extractor can do anything with the data
     */
    @Override
    public boolean isSupported(Serializable data) {
        return (data != null && data instanceof String);
    }

    /**
     * Extract the site name / id
     *
     * @param in a string containing the site id
     * @return the site id
     * @throws Throwable
     */
    @Override
    public Serializable extractData(Serializable in) throws Throwable {
        String path = (String) in;

        String siteName = "";
        if (path.contains("st:sites")) {
            siteName = StringUtils.substringBetween(path, "/st:sites/", "/");

            if (logger.isDebugEnabled()) {
                logger.debug("Extracted site name: " + siteName);
            }
        }

        // If content is not in a site, or if it is surf config for user dashboard
        if (StringUtils.isBlank(siteName) || StringUtils.equals(siteName, "cm:surf-config")) {
            // The default site name for content not associated with sites.
            siteName = "<no-site>";
        }

        return siteName;
    }
}
