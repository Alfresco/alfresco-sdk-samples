/**
 * Copyright (C) 2015 Alfresco Software Limited.
 *
 * This file is part of the Alfresco SDK Samples project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.alfresco.tutorials.testSpecs.context;


import org.alfresco.model.ContentModel
import org.alfresco.repo.security.authentication.AuthenticationUtil;
import org.alfresco.service.ServiceRegistry;
import org.alfresco.tutorial.demoamp.DemoComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import spock.lang.Specification
import spock.lang.Stepwise;

/**
 * A simple class demonstrating how to run out-of-container tests 
 * loading Alfresco application context.
 *
 * @author Martin Bergljung
 */
@ContextConfiguration(locations = [ "classpath:alfresco/application-context.xml", "classpath:alfresco/remote-api-context.xml", "classpath:alfresco/web-scripts-application-context.xml" ] )
@Stepwise
public class DemoComponentTestSpec extends Specification {
    private static final String ADMIN_USER_NAME = "admin";

    @Autowired
    DemoComponent demoComponent;

    @Autowired
    ServiceRegistry serviceRegistry;

    def setup() {
        AuthenticationUtil.setFullyAuthenticatedUser(ADMIN_USER_NAME);
    }

    def "Test bean wiring"() {
        expect:
        demoComponent != null
    }

    def "Test getting Company Home"() {
        given: "I get the Company Home node reference via the Demo Component"
        def companyHome = demoComponent.getCompanyHome()

        expect: "'Company Home' to be the name"
        def companyHomeName = (String) serviceRegistry.nodeService.getProperty(companyHome, ContentModel.PROP_NAME)
        "Company Home".equals(companyHomeName)
    }

    def "Test getting Company Home sub-nodes"() {
        given: "I get the Company Home node reference via the Demo Component"
        def companyHome = demoComponent.getCompanyHome();

        // There's 7 folders under Company home by default but a bootstrap repo AMP adds a file and a patch adds a folder
        expect: "There to be 9 nodes under Company Home"
        def childNodeCount = demoComponent.childNodesCount(companyHome);
        childNodeCount == 9
    }
}
