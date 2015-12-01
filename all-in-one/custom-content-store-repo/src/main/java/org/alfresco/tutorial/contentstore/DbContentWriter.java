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

import org.alfresco.repo.content.AbstractContentWriter;
import org.alfresco.service.cmr.repository.ContentIOException;
import org.alfresco.service.cmr.repository.ContentReader;
import org.alfresco.util.GUID;
import org.alfresco.util.TempFileProvider;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;

/**
 * Database Content Writer for a file
 *
 * @author martin.bergljung@alfresco.com
 */
public class DbContentWriter extends AbstractContentWriter {
    private static final Log LOG = LogFactory.getLog(DbContentWriter.class);

    /**
     * The content URL for file stored in Database.
     * e.g. db://78c98aa1-7ded-48e5-a7ee-838dfec49e04.bin, this is the PK in the database
     */
    private String url;

    /**
     * The temp file uuid, for example: d789b2ea-a87e-4b68-a2df-fcedc607c8b1
     */
    private String uuid;

    /**
     * The temp file stored in a location such as:
     * <p/>
     * /tmp/Alfresco/2015/12/1/9/46/d789b2ea-a87e-4b68-a2df-fcedc607c8b1.bin
     */
    private File tempFile;

    /**
     * The temp file size.
     */
    private long size;

    /**
     * Database Adapter
     */
    private DatabaseAdapter databaseAdapter;

    /**
     * Listens to when the content stream is closed for the file this writer is managing
     */
    private DbContentStreamListener listener;

    /**
     * Initialize new database content writer
     *
     * @param url                   the node url, basically the PK in the database table
     * @param existingContentReader the existing content reader (if this file already exist and new version should be stored
     * @param databaseAdapter the database adapter with methods to store and retrieve BLOBs with file content
     */
    public DbContentWriter(final String url, final ContentReader existingContentReader,
                           final DatabaseAdapter databaseAdapter) {
        super(url, existingContentReader);

        this.url = url;
        this.databaseAdapter = databaseAdapter;
        this.uuid = GUID.generate();

        // Add content stream listener, so we can write file to DB when stream closes
        this.listener = new DbContentStreamListener(this);
        addListener(this.listener);
    }

    /* (non-Javadoc)
     * @see org.alfresco.repo.content.AbstractContentWriter#createReader()
	 */
    @Override
    protected ContentReader createReader() throws ContentIOException {
        return new DbContentReader(getContentUrl(), databaseAdapter);
    }

    /**
     * Uploading a new file kicks off this method, which creates a temp file ready for
     * the bytes from the uploaded file to be written to it.
     * <p/>
     * (non-Javadoc)
     *
     * @see org.alfresco.repo.content.AbstractContentWriter#getDirectWritableChannel()
     */
    @Override
    protected WritableByteChannel getDirectWritableChannel()
            throws ContentIOException {
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Creating temp file [uuid=" + uuid + "]");
            }

            tempFile = TempFileProvider.createTempFile(uuid, ".bin");
            final OutputStream outStream = new FileOutputStream(tempFile);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Returning write channel to temp file [uuid=" + uuid + "]");
            }

            return Channels.newChannel(outStream);
        } catch (Exception excp) {
            throw new ContentIOException("Failed to open channel. " + this, excp);
        }
    }

    /**
     * Gets the file URL, for example: db://78c98aa1-7ded-48e5-a7ee-838dfec49e04.bin
     *
     * @return the file url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the file URL, for example db://78c98aa1-7ded-48e5-a7ee-838dfec49e04.bin
     *
     * @param url the new file url
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Gets the temp file UUID.
     *
     * @return the uuid
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * Sets the temp file UUID.
     *
     * @param uuid the new uuid
     */
    public void setUuid(final String uuid) {
        this.uuid = uuid;
    }


    /**
     * Gets the temp file stored in a location such as:
     * <p/>
     * /tmp/Alfresco/2015/12/1/9/46/d789b2ea-a87e-4b68-a2df-fcedc607c8b1.bin
     *
     * @return the temp file
     */
    public File getTempFile() {
        return tempFile;
    }

    /**
     * Sets the size.
     *
     * @param size the new size
     */
    public void setSize(final long size) {
        this.size = size;
    }

    /* (non-Javadoc)
     * @see org.alfresco.service.cmr.repository.ContentAccessor#getSize()
     */
    @Override
    public long getSize() {
        return size;
    }

    /**
     * Convenience access method
     *
     * @return
     */
    public DatabaseAdapter getDatabaseAdapter() {
        return databaseAdapter;
    }

}
