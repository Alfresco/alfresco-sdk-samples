package org.alfresco.sample;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.extensions.webscripts.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Webscript extends DeclarativeWebScript
{

    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status,
                                              Cache cache)
    {
        Map<String, Object> model = new HashMap<String, Object>();

        model.put("greeting", "Hello world");

        return model;


    }
}