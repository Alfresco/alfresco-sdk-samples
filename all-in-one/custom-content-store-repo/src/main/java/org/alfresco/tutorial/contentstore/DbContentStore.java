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

package org.alfresco.tutorial.contentstore;

import org.alfresco.repo.content.AbstractContentStore;
import org.alfresco.repo.content.ContentStore;
import org.alfresco.repo.content.filestore.FileContentStore;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.service.cmr.repository.ContentWriter;
import org.alfresco.util.GUID;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;

/**
 * Stores files as Database BLOBs.
 *
 * @author martin.bergljung@alfresco.com
 */
public class DbContentStore extends AbstractContentStore {
    private static final Log LOG = LogFactory.getLog(DbContentStore.class);

    /**
     * Store protocol that is used as prefix in contentUrls
     */
    public static final String DB_STORE_PROTOCOL = "db";

    /**
     * Database Adapter
     */
    private DatabaseAdapter databaseAdapter;

    public void setDatabaseAdapter(DatabaseAdapter databaseAdapter) {
        this.databaseAdapter = databaseAdapter;
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.content.ContentStore#getReader(java.lang.String)
     */
    @Override
    public ContentReader getReader(final String contentUrl)
            throws ContentIOException {
        try {
            return new DbContentReader(contentUrl, databaseAdapter);
        } catch (Exception globalExcp) {
            throw new ContentIOException(
                    "DbContentStore Failed to get reader for URL: " + contentUrl, globalExcp);
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.content.AbstractContentStore#getWriterInternal(org.alfresco.service.cmr.repository.ContentReader, java.lang.String)
	 */
    @Override
    protected ContentWriter getWriterInternal(final ContentReader existingContentReader,
                                              final String newContentUrl) throws ContentIOException {
        try {
            String contentUrl = null;

            // Was a URL provided?
            if (newContentUrl == null || newContentUrl.equals("")) {
                contentUrl = createNewUrl();
            } else {
                contentUrl = newContentUrl;
            }

            return new DbContentWriter(contentUrl, existingContentReader, databaseAdapter);
        } catch (Exception globalExcp) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed to get writer. " + globalExcp);
            }

            throw new ContentIOException("Failed to get writer.");
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.content.AbstractContentStore#delete(java.lang.String)
	 */
    @Override
    public boolean delete(final String url) throws ContentIOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Deleting file content from database [url=" + url +"]");
        }

        try {
            // Delete file content from Database, delete BLOB keyed on URL
            databaseAdapter.deleteObject(url);
        } catch (SQLException se) {
            throw new ContentIOException("Failed to delete content BLOB for [url=" + url +"]" +
                    ", " + se.getMessage());
        }

        return true;
    }

    @Override
    public boolean isWriteSupported() {
        return true;
    }

    @Override
    public boolean isContentUrlSupported(String contentUrl) {
        if (contentUrl == null) {
            throw new IllegalArgumentException("The contentUrl may not be null");
        }

        int index = contentUrl.indexOf(DB_STORE_PROTOCOL + ContentStore.PROTOCOL_DELIMITER);
        if (index < 0) {
            // temp: backwards compat for trashathon only !
            index = contentUrl.indexOf(FileContentStore.STORE_PROTOCOL + ContentStore.PROTOCOL_DELIMITER);
            if (index < 0) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Checking contentUrl=" + contentUrl + ", it is NOT supported by this content store");
                }

                return false;
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Checking contentUrl=" + contentUrl + ", it IS supported by this content store");
        }

        return true;
    }

    /**
     * Creates a new content URL.  This must be supported by all
     * stores that are compatible with Alfresco.
     *
     * @return Returns a new and unique content URL, will be used as DB PK
     */
    public static String createNewUrl() {
        // Create the URL, it is just a flat root storage,
        // no hierarchy based on date and time etc...
        StringBuilder sb = new StringBuilder(20);

        sb.append(DB_STORE_PROTOCOL)
                .append(ContentStore.PROTOCOL_DELIMITER)
                .append(GUID.generate()).append(".bin");

        String newContentUrl = sb.toString();

        return newContentUrl;
    }

}
