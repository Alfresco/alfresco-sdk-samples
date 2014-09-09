/*
 * Copyright (C) 2005-2014 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.module.my_rm.model;

import org.alfresco.service.namespace.QName;

/**
 * Helper class containing my rm model qualified names.
 *
 * @author Tuna Aksoy
 */
public interface MyRMModel
{
    // Namespace details
    String MY_RM_URI = "http://www.alfresco.org/model/myRecordsManagement/1.0";
    String MY_RM_PREFIX = "myrm";

    // Custom Compliance Site
    QName TYPE_MY_RM_SITE = QName.createQName(MY_RM_URI, "site");

    // Custom Compliance File Plan
    QName TYPE_MY_RM_FILE_PLAN = QName.createQName(MY_RM_URI, "filePlan");

    // Custom Record
    QName ASPECT_MY_COMPLIANCE_RECORD_METADATA = QName.createQName(MY_RM_URI, "myComplianceRecordMetaData");
    QName PROP_MY_COMPLIANCE_ID = QName.createQName(MY_RM_URI, "myComplianceID");
}
