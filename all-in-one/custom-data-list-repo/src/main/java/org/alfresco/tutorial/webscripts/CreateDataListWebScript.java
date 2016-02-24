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
package org.alfresco.tutorial.webscripts;

import org.alfresco.model.ContentModel;
import org.alfresco.model.DataListModel;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.service.cmr.repository.NodeRef;
import org.alfresco.service.namespace.QName;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.client.runtime.SessionFactoryImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.opencmis.commons.exceptions.CmisConnectionException;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptRequest;

import java.io.Serializable;
import java.util.*;

/**
 * A web script that creates a Data List instance with a few item rows.
 *
 * @author martin.bergljung@alfresco.com
 */
public class CreateDataListWebScript extends DeclarativeWebScript {
    private final static String NAMESPACE_URI = "http://www.acme.org/model/datalist/1.0";
    private final String DATA_LIST_SITE_CONTAINER = "dataLists";
    private final QName ACMEDL_PROJECT_LIST_ITEM_TYPE = QName.createQName(NAMESPACE_URI, "projectListItem");

    private ServiceRegistry serviceRegistry;

    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
    }

    @Override
    protected Map<String, Object> executeImpl(
            WebScriptRequest req, Status status, Cache cache) {
        Map<String, Object> model = new HashMap<String, Object>();

        // Name of the out-of-the-box Sample site (Web Site Design Project)
        String siteShortName = "swsdp";

        // Name of the data list we are about to create
        String dataListName = "projectListA";

        // Get or create the site data list container (assumes that the site exists)
        if (!serviceRegistry.getSiteService().hasContainer(siteShortName, DATA_LIST_SITE_CONTAINER)) {
            serviceRegistry.getSiteService().createContainer(siteShortName, DATA_LIST_SITE_CONTAINER,
                    ContentModel.TYPE_CONTAINER, null);
        }
        NodeRef dataListContainerNodeRef = serviceRegistry.getSiteService().getContainer(
                siteShortName, DATA_LIST_SITE_CONTAINER);

        // Check that the data list name is not already used
        if (serviceRegistry.getNodeService().getChildByName(
                dataListContainerNodeRef, ContentModel.ASSOC_CONTAINS, dataListName) == null) {
            Map<QName, Serializable> properties = new HashMap<QName, Serializable>();

            // Create the data list
            properties.put(ContentModel.PROP_NAME, dataListName);
            properties.put(ContentModel.PROP_TITLE, "Project List A");
            properties.put(ContentModel.PROP_DESCRIPTION, "A list of projects that has names starting with A");
            properties.put(DataListModel.PROP_DATALIST_ITEM_TYPE, "acmedl:" + ACMEDL_PROJECT_LIST_ITEM_TYPE.getLocalName());
            NodeRef datalistNodeRef = serviceRegistry.getNodeService().createNode(
                    dataListContainerNodeRef,
                    ContentModel.ASSOC_CONTAINS,
                    QName.createQName("cm:projectListA"),
                    DataListModel.TYPE_DATALIST,
                    properties).getChildRef();

            // Create a data list item
            properties.clear();
            properties.put(QName.createQName(NAMESPACE_URI, "projectName"), "Project A1");
            properties.put(QName.createQName(NAMESPACE_URI, "projectNumber"), "1");
            properties.put(QName.createQName(NAMESPACE_URI, "projectDescription"), "Project A1 handling important stuff");
            GregorianCalendar startDate = new GregorianCalendar(2016, 8, 5, 12, 0);
            properties.put(QName.createQName(NAMESPACE_URI, "projectStartDate"), startDate);
            properties.put(QName.createQName(NAMESPACE_URI, "projectActive"), true);
            NodeRef projectANodeRef = serviceRegistry.getNodeService().createNode(
                    datalistNodeRef, ContentModel.ASSOC_CONTAINS,
                    QName.createQName("cm:projectA1"),
                    ACMEDL_PROJECT_LIST_ITEM_TYPE,
                    properties).getChildRef();

            // Setup admin user as project member
            NodeRef adminUserNodeRef = serviceRegistry.getPersonService().getPerson("admin");
            serviceRegistry.getNodeService().createAssociation(projectANodeRef, adminUserNodeRef,
                    QName.createQName(NAMESPACE_URI, "projectMember"));

            model.put("msg", "Created Data List: Project List A");

            // Add data list item to an existing event data list via CMIS
            Session session = getSession("admin", "admin");
            populateEventDataList(session, "myEventDataListDesc");

            model.put("cmismsg", "Created Event Data List item: Summer Olympics 2016");
        } else {
            model.put("msg", "Did not create Data List: Project List A already exists");
        }

        return model;
    }

    /**
     * Get an Open CMIS session to use when talking to the Alfresco repo.
     *
     * @param username       the Alfresco username to connect with
     * @param pwd            the Alfresco password to connect with
     * @return an Open CMIS Session object
     */
    public Session getSession(String username, String pwd) {
        SessionFactory sessionFactory = SessionFactoryImpl.newInstance();
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(SessionParameter.USER, username);
        parameters.put(SessionParameter.PASSWORD, pwd);
        parameters.put(SessionParameter.ATOMPUB_URL,
                "http://localhost:8080/alfresco/api/-default-/cmis/versions/1.1/atom");
        parameters.put(SessionParameter.BINDING_TYPE, BindingType.ATOMPUB.value());
        parameters.put(SessionParameter.COMPRESSION, "true");
        parameters.put(SessionParameter.CACHE_TTL_OBJECTS, "0");

        // If there is only one repository exposed (e.g. Alfresco), these
        // lines will help detect it and its ID
        List<Repository> repositories = sessionFactory.getRepositories(parameters);
        Repository alfrescoRepository = null;
        if (repositories != null && repositories.size() > 0) {
            alfrescoRepository = repositories.get(0);
        } else {
            throw new CmisConnectionException(
                    "Could not connect to the Alfresco Server, no repository found!");
        }

        // Create a new session with the Alfresco repository
        Session session = alfrescoRepository.createSession();

        return session;
    }

    private static final String SECONDARY_OBJECT_TYPE_IDS_PROP_NAME = "cmis:secondaryObjectTypeIds";

    public boolean populateEventDataList(Session session, String eventDataListDesc) {
        // Get available DataLists in the out-of-the-box Sample site (Web Site Design Project)
        Folder dataLists = (Folder) session.getObjectByPath("/sites/swsdp/dataLists");

        // Iterate through the found data lists until we find the one we want to work with
        Iterator<CmisObject> it = dataLists.getChildren().iterator();
        Folder dataList = null;
        while (it.hasNext()) {
            CmisObject obj = it.next();
            if (obj.getDescription().compareToIgnoreCase(eventDataListDesc) == 0) {
                dataList = (Folder)obj;
            }
        }
        if (dataList == null) {
            // Could not find data list
            return false;
        }

        // Define the aspect that contains the titled and description properties, can be added as secondary type
        List<Object> aspects = new ArrayList<Object>();
        aspects.add("P:cm:titled");
        aspects.add("P:cm:attachable"); // Mandatory

        // Add an item to the "Event" data list
        Map<String, Object> properties = new HashMap<String, Object>();
        properties.put(PropertyIds.OBJECT_TYPE_ID, "D:dl:event");
        String eventName = "Summer Olympics";
        properties.put("cmis:name", eventName);
        properties.put(SECONDARY_OBJECT_TYPE_IDS_PROP_NAME, aspects);
        properties.put("cm:title", eventName);
        properties.put("cm:description", "The 2016 Summer Olympics, officially known as the Games of the XXXI Olympiad");
        properties.put("dl:eventNote", "Some notes");
        GregorianCalendar eventStartDate = new GregorianCalendar(2016,8,5,12,0);
        properties.put("dl:eventStartDate", eventStartDate);
        GregorianCalendar eventEndDate = new GregorianCalendar(2016,8,21,20,0);
        properties.put("dl:eventEndDate", eventEndDate);
        properties.put("dl:eventLocation", "Rio de Janeiro");
        properties.put("dl:eventRegistrations", "Some Regs");
        ObjectId newItemId = session.createItem(properties, dataList);

        return true;
    }
}