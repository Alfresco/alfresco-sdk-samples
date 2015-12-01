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

import org.alfresco.repo.content.AbstractContentReader;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.SQLException;
import java.util.Date;

/**
 * Database Content Reader
 *
 * @author martin.bergljung@alfresco.com
 */
public class DbContentReader extends AbstractContentReader {
    private static final Log LOG = LogFactory.getLog(DbContentReader.class);

    /**
     * File availability.
     */
    private boolean isFileAvailable;

    /**
     * The content URL for file stored in Database.
     * e.g. db://78c98aa1-7ded-48e5-a7ee-838dfec49e04.bin
     */
    private String url;

    /**
     * Database Adapter
     */
    private DatabaseAdapter databaseAdapter;

    /**
     * Construct the DB Content Reader with the URL that points to the file
     * that should be read and what database connection to use.
     *
     * @param url          the file URL, basically the key in the database table,
     *                     for example: db://78c98aa1-7ded-48e5-a7ee-838dfec49e04.bin
     * @param databaseAdapter the database adapter with methods to store and retrieve BLOBs with file content
     */
    public DbContentReader(final String url, final DatabaseAdapter databaseAdapter) {
        super(url);

        this.databaseAdapter = databaseAdapter;
        this.url = url;
        isFileAvailable = isFileAvailable();
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.content.AbstractContentReader#createReader()
	 */
    @Override
    protected ContentReader createReader() throws ContentIOException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Creating a new content reader [url=" + url + "]");
        }

        return new DbContentReader(url, databaseAdapter);
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.content.AbstractContentReader#getDirectReadableChannel()
     */
    @Override
    protected ReadableByteChannel getDirectReadableChannel() throws ContentIOException {
        // Confirm the requested object exists
        if (!exists()) {
            throw new ContentIOException("File content does not exist [url=" + url + "]");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Obtaining input stream and creating readable byte channel [url=" + url + "]");
        }

        try {
            // Get file content from Database as BLOB keyed on URL
            final InputStream is = databaseAdapter.getObject(url);
            ReadableByteChannel channel = Channels.newChannel(is);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Successfully created new readable byte channel [url=" + url + "]");
            }

            return channel;
        } catch (SQLException se) {
            throw new ContentIOException("Failed to read content BLOB for [url=" + url + "], " + se.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.repository.ContentReader#exists()
	 */
    @Override
    public boolean exists() {
        return isFileAvailable;
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.repository.ContentReader#getLastModified()
	 */
    @Override
    public long getLastModified() {
        if (!exists()) {
            return 0L;
        }

        // TODO - Store last modified date in Database row or something
        return new Date().getTime();
    }

    /*
     * Get the size so spoof file can be created if random access is not supported (i.e. no FileChannel).
     *
     * (non-Javadoc)
	 * @see org.alfresco.service.cmr.repository.ContentAccessor#getSize()
	 */
    @Override
    public long getSize() {
        // Confirm the requested object exists
        if (!exists()) {
            return 0L;
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Obtaining file size [url=" + url + "]");
        }

        try {
            return databaseAdapter.getObjectSize(url);
        } catch (SQLException e) {
            throw new ContentIOException("Failed to read file size [url=" + url + "], " + e.getMessage());
        }
    }

    /**
     * Checks if there is an entry in the BLOB table for the file (PK = url).
     *
     * @return true if row exists, otherwise false
     */
    private boolean isFileAvailable() {
        try {
            return databaseAdapter.isObjectAvailable(url);
        } catch (SQLException e) {
            throw new ContentIOException("Failed to check if file row exists [url=" + url + "], " + e.getMessage());
        }
    }
}