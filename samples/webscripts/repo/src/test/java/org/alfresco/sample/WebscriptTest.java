package org.alfresco.sample;

import org.alfresco.repo.web.scripts.BaseWebScriptTest;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.TestWebScriptServer;
import java.io.IOException;

public class WebscriptTest extends BaseWebScriptTest {
    private static final Logger log = Logger.getLogger(WebscriptTest.class);
    private static final String ADMIN_USER_NAME = "admin";
    private static final String WEBSCRIPT_URL = "/example-webscripts/java-backed/webscript";

    //Optionally add .setArgs(Map<String,String>) to add request parameters
    private static final TestWebScriptServer.Request WEBSCRIPT_REQUEST = new TestWebScriptServer.GetRequest(WEBSCRIPT_URL);

    @Test
    public void testDemoWebscript() throws IOException {
        TestWebScriptServer.Response response = sendRequest(WEBSCRIPT_REQUEST, Status.STATUS_OK, ADMIN_USER_NAME);
        assertEquals("Hello world", response.getContentAsString());
    }
}