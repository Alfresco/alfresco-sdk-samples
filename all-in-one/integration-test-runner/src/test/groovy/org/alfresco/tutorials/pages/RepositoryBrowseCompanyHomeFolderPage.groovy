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
 * Repository Browse Page - /Company Home level
 *
 * @author marting.bergljung@alfresco.com
 */
class RepositoryBrowseCompanyHomeFolderPage extends AbstractRepositoryBrowsePage {
    // The relative URL of the page;
    // used by the to() method to determine which URL to send the HTTP request to.
    static url = "page/repository"

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
                <tr class="yui-dt-rec yui-dt-first yui-dt-even" id="yui-rec51">
                    <td headers="yui-dt28-th-nodeRef " class="yui-dt28-col-nodeRef yui-dt-col-nodeRef yui-dt-first" style="width: 16px;">
                        <div class="yui-dt-liner" style="width: 16px;">
                            <label id="label_for_checkbox-yui-rec51" for="checkbox-yui-rec51" style="font-size: 0em;">Check Data Dictionary</label>
                            <input id="checkbox-yui-rec51" type="checkbox" name="fileChecked" value="workspace://SpacesStore/b45963fb-3797-47ec-be59-03bbda9a4926">
                        </div>
                    </td>
                    ...
                    */
        dataDictionaryFolderRow(wait:true) { $("tbody.yui-dt-data tr", 0) }



        /*
        <a rel="|path|/Data%20Dictionary|" class="filter-change" href="#">Data Dictionary</a>

        This is dynamic content so wait for the Data Dictionary folder link to appear...
         */
        dataDictionaryFolderLink(wait : true, to : RepositoryBrowseCompanyHomeFolderPage) { $("a", rel : contains("|path|/Data%20Dictionary|")) }

        /*
        <a title="Go to Google" class="simple-link" href="http://www.google.com" style="background-image:url(/share/res/components/documentlibrary/actions/google-16.png)"
        target="_blank" id="yui-gen511"><span id="yui-gen513">Go to Google</span></a>

        This is dynamic content so wait for the DocLib action to appear...
        */
        goToGoogleActionPopupMenuItem(wait : true) { $("a", title: "Go to Google") }

    }
}