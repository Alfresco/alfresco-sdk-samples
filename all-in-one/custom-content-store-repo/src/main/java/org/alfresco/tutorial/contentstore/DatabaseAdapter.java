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

import java.io.InputStream;
import java.sql.SQLException;

/**
 * A database adapter interface that should be implemented by
 * classes that wants to support storing and reading BLOBs
 * from different databases.
 *
 * @author martin.bergljung@alfresco.com
 */
public interface DatabaseAdapter {
    public InputStream getObject(String url) throws SQLException;
    public void storeObject(String url, InputStream is, long size) throws SQLException;
    public void deleteObject(String url) throws SQLException;
    public long getObjectSize(String url) throws SQLException;
    public boolean isObjectAvailable(String url) throws SQLException;
}
