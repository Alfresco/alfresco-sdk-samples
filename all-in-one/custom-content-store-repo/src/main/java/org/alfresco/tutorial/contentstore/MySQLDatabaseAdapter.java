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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.sql.*;

/**
 * MySQL file content BLOB access.
 * <p/>
 * Before using this adapter create the following table in your MySQL database:
 * <p/>
 * mysql> create database alfresco_content;
 * Query OK, 1 row affected (0.01 sec)
 * <p/>
 * mysql> grant all on alfresco_content.* to 'alfresco'@'localhost' identified by '1234' with grant option;
 * Query OK, 0 rows affected (0.00 sec)
 * <p/>
 * mysql> grant all on alfresco_content.* to 'alfresco'@'localhost.localdomain' identified by '1234' with grant option;
 * Query OK, 0 rows affected (0.00 sec)
 * mysql> exit
 * <p/>
 * $ mysql -u alfresco -p
 * Enter password:
 * Welcome to the MySQL monitor....
 * <p/>
 * mysql> use alfresco_content;
 * Database changed
 * mysql> create table alfresco_files ( url varchar(256) not null, file longblob ) ENGINE=InnoDB;
 * Query OK, 0 rows affected (0.01 sec)
 * <p/>
 * mysql> desc alfresco_files;
 * +-------+-------------+------+-----+---------+-------+
 * | Field | Type        | Null | Key | Default | Extra |
 * +-------+-------------+------+-----+---------+-------+
 * | url   | varchar(256)| NO   |     | NULL    |       |
 * | file  | longblob    | YES  |     | NULL    |       |
 * +-------+-------------+------+-----+---------+-------+
 * 2 rows in set (0.01 sec)
 * <p/>
 * LONGBLOB can contain up to 4GB
 * <p/>
 * Check the max allowed packet size = max upload size for file
 * mysql> show variables like 'max_allowed_packet';
 * +--------------------+----------+
 * | Variable_name      | Value    |
 * +--------------------+----------+
 * | max_allowed_packet | 16777216 |
 * +--------------------+----------+
 * 1 row in set (0.00 sec)
 *
 * @author martin.bergljung@alfresco.com
 */
public class MySQLDatabaseAdapter implements DatabaseAdapter {
    private static final Log LOG = LogFactory.getLog(DbContentReader.class);

    /**
     * MySQL Connection
     */
    private Connection dbConnection;

    public MySQLDatabaseAdapter() {
        initialize("alfresco_content", "alfresco", "1234");
    }

    /**
     * Construct MySQL DB Adapter with passed in database info.
     *
     * @param databaseName the name of the database to connect to
     * @param username     the database username to use when connecting
     * @param password     the user's password to use when connecting
     */
    public MySQLDatabaseAdapter(final String databaseName, final String username, final String password) {
        initialize(databaseName, username, password);
    }

    /**
     * Initialize the DB content store by loading the database driver and
     * connecting to the database.
     *
     * @param databaseName the name of the database to connect to
     * @param username     the database username to use when connecting
     * @param password     the user's password to use when connecting
     */
    private void initialize(final String databaseName, final String username, final String password) {
        String jdbcConnectionString = "jdbc:mysql://localhost:3306/" + databaseName;

        if (LOG.isInfoEnabled()) {
            LOG.info("Connecting to database: " + jdbcConnectionString + " ...");
        }

        try {
            // Load MySQL Driver and make connection
            Class.forName("com.mysql.jdbc.Driver");
            dbConnection = DriverManager.getConnection(jdbcConnectionString, username, password);

            // Set max upload size of file to 10 MB
            // Need SUPER privileges for this...
            //String querySetLimit = "SET GLOBAL max_allowed_packet=104857600;";
            //Statement stSetLimit = dbConnection.createStatement();
            //stSetLimit.execute(querySetLimit);

            if (LOG.isInfoEnabled()) {
                LOG.info("Initialization Complete and max file size set to 10MB");
            }
        } catch (ClassNotFoundException cnfe) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Initialization Error, MySQL driver class not found: " + cnfe.getMessage());
            }
        } catch (SQLException se) {
            if (LOG.isErrorEnabled()) {
                LOG.error("Initialization Error, MySQL connection failed: " + se.getMessage());
            }
        }
    }

    public InputStream getObject(String url) throws SQLException {
        PreparedStatement pre = null;

        try {
            // Get file content from Database as BLOB keyed on URL
            pre = dbConnection.prepareStatement("SELECT file FROM alfresco_files where url = ?");
            pre.setString(1, url);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                int blobColumnIndex = 1;
                final InputStream is = resultSet.getBinaryStream(blobColumnIndex);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Successfully obtained input stream [url=" + url + "]");
                }

                return is;
            } else {
                throw new ContentIOException("Did not find content BLOB [url=" + url + "]");
            }
        } finally {
            try {
                if (pre != null) {
                    pre.close();
                }
            } catch (SQLException se) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Failed to close prepared statement: " + se.getMessage());
                }
            }
        }
    }

    public void storeObject(String url, InputStream is, long size) throws SQLException {
        PreparedStatement pre = null;

        try {
            // Store file content in Database as BLOB keyed on URL
            pre = dbConnection.prepareStatement("INSERT INTO alfresco_files (url,file) values (?,?)");
            pre.setString(1, url);
            pre.setBinaryStream(2, is, (int) size);
            pre.executeUpdate();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Stored content in database BLOB [url=" + url + "][size=" + size + "]");
            }
        } finally {
            try {
                if (pre != null) {
                    pre.close();
                }
            } catch (SQLException se) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Failed to close prepared statement: " + se.getMessage());
                }
            }
        }
    }

    public void deleteObject(String url) throws SQLException {
        PreparedStatement pre = null;

        try {
            // Delete file content from Database, delete BLOB keyed on URL
            pre = dbConnection.prepareStatement("DELETE FROM alfresco_files where url = ?");
            pre.setString(1, url);
            pre.executeUpdate();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Successfully deleted database BLOB for file with [url=" + url +"]");
            }

        } finally {
            try {
                if (pre != null) {
                    pre.close();
                }
            } catch (SQLException se) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Failed to close prepared statement: " + se.getMessage());
                }
            }
        }
    }

    public long getObjectSize(String url) throws SQLException {
        PreparedStatement pre = null;
        long size = 0;

        try {
            // Get file content from Database as BLOB keyed on URL
            pre = dbConnection.prepareStatement("SELECT OCTET_LENGTH(file) FROM alfresco_files where url = ?");
            pre.setString(1, url);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                int sizeColumnIndex = 1;
                size = resultSet.getLong(sizeColumnIndex);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Successfully obtained file size [url=" + url + "][size=" + size + "]");
                }

                return size;
            } else {
                throw new ContentIOException("Did not succeed finding file size [url=" + url + "]");
            }
        } finally {
            try {
                if (pre != null) {
                    pre.close();
                }
            } catch (SQLException se) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Failed to close prepared statement: " + se.getMessage());
                }
            }
        }
    }

    public boolean isObjectAvailable(String url) throws SQLException {
        PreparedStatement pre = null;

        try {
            // Get file url to check if content exist
            pre = dbConnection.prepareStatement("SELECT url FROM alfresco_files where url = ?");
            pre.setString(1, url);
            ResultSet resultSet = pre.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } finally {
            try {
                if (pre != null) {
                    pre.close();
                }
            } catch (SQLException se) {
                if (LOG.isErrorEnabled()) {
                    LOG.error("Failed to close prepared statement: " + se.getMessage());
                }
            }
        }
    }
}
