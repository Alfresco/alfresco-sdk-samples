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

import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentStreamListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

/**
 * Store the content file in the database as a BLOB when stream closes.
 *
 * @author martin.bergljung@alfresco.com
 */
public class DbContentStreamListener implements ContentStreamListener {
    private static final Log LOG = LogFactory.getLog(DbContentStreamListener.class);

    /**
     * Database Adapter
     */
    private DatabaseAdapter databaseAdapter;

    /**
     * The writer.
     */
    private final DbContentWriter writer;

    /**
     * Initialize the new DB content stream listener, it is attached to a specific writer.
     *
     * @param writer the DB content writer for the file
     */
    public DbContentStreamListener(final DbContentWriter writer) {
        this.writer = writer;
        this.databaseAdapter = writer.getDatabaseAdapter();
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.repository.ContentStreamListener#contentStreamClosed()
	 */
    @Override
    public void contentStreamClosed() throws ContentIOException {
        final String url = writer.getUrl();
        final File file = writer.getTempFile();
        final long size = file.length();
        writer.setSize(size);

        if (LOG.isDebugEnabled()) {
            LOG.debug("Retrieving temp file stream [url=" + url + "][size=" + size + "]");
        }

        try {
            // Store file content in Database as BLOB keyed on URL
            FileInputStream fis = new FileInputStream(file);
            databaseAdapter.storeObject(url, fis, size);
        } catch (FileNotFoundException fnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed to store file [url=" + url + "] in database as BLOB, file not found: "
                        + fnfe.getMessage());
            }
        } catch (SQLException se) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Failed to store file [url=" + url + "] in database as BLOB: " + se.getMessage());
            }
        }
    }
}
