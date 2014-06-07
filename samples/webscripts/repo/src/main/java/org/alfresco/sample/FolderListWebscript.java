package org.alfresco.sample;

import org.alfresco.repo.model.Repository;
import org.alfresco.service.cmr.repository.NodeRef;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.alfresco.service.cmr.repository.NodeService;



public class FolderListWebscript extends DeclarativeWebScript
{

    protected Repository repository;
    protected NodeService nodeService;

    public void setRepository(Repository repository)
    {
        this.repository = repository;
    }

    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {

        Map<String, String> templateArgs =
                req.getServiceMatch().getTemplateVars();

        String storeType = templateArgs.get("store_type");
        String storeId   = templateArgs.get("store_id");
        String nodeId    = templateArgs.get("id");

        String nodePath = storeType + "/" + storeId + "/" + nodeId;


        NodeRef folder = repository.findNodeRef("node", nodePath.split("/"));


        // validate that folder has been found
        if (folder == null)
        {
            throw new WebScriptException(Status.STATUS_NOT_FOUND,
                    "Folder " + nodePath + " not found");
        }

        // construct model for response template to render
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("folder", folder);

        return model;


    }
}