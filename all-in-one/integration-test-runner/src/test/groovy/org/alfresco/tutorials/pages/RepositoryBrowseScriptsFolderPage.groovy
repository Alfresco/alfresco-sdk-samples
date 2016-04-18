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

/**
 * Repository Browse Page - /Company Home/Data Dictionary/Scripts level
 *
 * @author marting.bergljung@alfresco.com
 */
class RepositoryBrowseScriptsFolderPage extends AbstractRepositoryBrowsePage {
    // The relative URL of the page;
    // used by the to() method to determine which URL to send the HTTP request to.
    static url = "page/repository#filter=path%7C%2FData%2520Dictionary%2FScripts%7C&page=1"

    // A closure that indicates whether the current page is this one or not -
    // called by the at() method; it should return a boolean, but you can also include assertions.
    static at = { title == "Alfresco » Repository Browser" }

    // A description of the page content, allowing for easy access to the parts declared here.
    // If it is not working for some content items then have a look at the Geb reports directory,
    // which contains the HTML that Geb is seeing... (i.e.
    // integration-test-runner/target/geb-reports/org/alfresco/tutorials/testSpecs/SurfModuleExtensionsTestSpec)
    static content = {
        /*
            <tbody tabindex="0" class="yui-dt-data">
                <tr class="yui-dt-rec yui-dt-first yui-dt-even" id="yui-rec677">
                    <td headers="yui-dt28-th-nodeRef " class="yui-dt28-col-nodeRef yui-dt-col-nodeRef yui-dt-first" style="width: 16px;">
                        <div class="yui-dt-liner" style="width: 16px;">
                            <label id="label_for_checkbox-yui-rec677" for="checkbox-yui-rec677" style="font-size: 0em;">Check alfresco docs.js.sample</label>
                            <input id="checkbox-yui-rec677" type="checkbox" name="fileChecked" value="workspace://SpacesStore/1d6c37f8-05c5-4014-9aad-76a5cc19cd6a">
                        </div>
                    </td>

        */
        checkAlfrescoDocsJsFileRow(wait:true) { $("tbody.yui-dt-data tr td div label", text: startsWith("Check alfresco docs.js")) }

    }
}