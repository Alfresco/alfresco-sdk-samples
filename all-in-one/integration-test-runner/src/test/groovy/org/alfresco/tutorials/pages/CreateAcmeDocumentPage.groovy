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
package org.alfresco.tutorials.pages

import geb.Page

/**
 * Alfresco Share Create Content (Acme Document) Page Object
 *
 * @author marting.bergljung@alfresco.com
 */
class CreateAcmeDocumentPage extends Page {
    // The relative URL of the page;
    // used by the to() method to determine which URL to send the HTTP request to.
    static url = "page/create-content"

    // A closure that indicates whether the current page is this one or not -
    // called by the at() method; it should return a boolean, but you can also include assertions.
    static at = { title == "Alfresco » Create Content" }

    // A description of the page content, allowing for easy access to the parts declared here.
    // If it is not working for some content items then have a look at the Geb reports directory,
    // which contains the HTML that Geb is seeing... (i.e.
    // integration-test-runner/target/geb-reports/org/alfresco/tutorials/testSpecs/SurfModuleExtensionsTestSpec)
    static content = {
        createForm { $("form") }
    }
}
