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
 * Alfresco Share Home Page Object for Administrator
 *
 * @author marting.bergljung@alfresco.com
 */
class   AdminHomePage extends Page {
    // The relative URL of the page;
    // used by the to() method to determine which URL to send the HTTP request to.
    static url = "page/user/admin/dashboard"

    // A closure that indicates whether the current page is this one or not -
    // called by the at() method; it should return a boolean, but you can also include assertions.
    static at = { dashboardName == "Administrator Dashboard" }

    // A description of the page content, allowing for easy access to the parts declared here.
    static content = {
        /*
        <span data-dojo-attach-point="containerNode,textDirNode" id="HEADER_REPOSITORY_text"
	          class="alf-menu-bar-label-node">
            <a href="/share/page/repository" title="Repository"
                class="alfresco-navigation-_HtmlAnchorMixin" tabindex="-1">Repository
            </a>
        </span>
         */
        repositoryLink(to: RepositoryBrowseCompanyHomeFolderPage) { $("span#HEADER_REPOSITORY_text a") }

        /*
            <span data-dojo-attach-point="containerNode,textDirNode" id="HEADER_ADMIN_CONSOLE_text"
                  class="alf-menu-bar-label-node">
                <a href="/share/page/console/admin-console/application" title="Admin Tools"
                   class="alfresco-navigation-_HtmlAnchorMixin" tabindex="-1">Admin Tools
                </a>
         */
        adminToolsLink(to: AdminToolsPage) { $("span#HEADER_ADMIN_CONSOLE_text a") }

        /*
           <h1 class="alfresco-header-Title alfresco-debug-Info highlight" id="HEADER_TITLE" widgetid="HEADER_TITLE">
             <div class="alfresco-debug-WidgetInfo" id="uniqName_1_49" widgetid="uniqName_1_49">
                <img data-dojo-attach-event="ondijitclick:showInfo" alt="Information about widget HEADER_TITLE" class="image" src="/share/res/js/aikau/1.0.8.1/alfresco/debug/css/images/info-16.png" />
             </div>
             <a data-dojo-attach-point="textNode" class="text" href="#">Administrator Dashboard</a></h1>

             Wait for the header to appear...
             */
        dashboardName(wait: true) { $("h1#HEADER_TITLE span").text() }

        /*
        <div id="alf-ft">
            <div id="global_x002e_footer">
                <div id="global_x002e_footer_x0023_New_Content">
		            <div>
		                Hello World!
		            </div>
         */
        newContentDiv { $("div#alf-ft div div div") }

        /*
        <div class="title">My Sites</div>
         */
        mySitesDivExists { $("div.title").text() == "My Sites" }

        // <span class="copyright">
        // <a href="#" onclick="Alfresco.module.getAboutShareInstance().show(); return false;"><img src="/share/res/components/images/alfresco-share-logo.png" alt="Alfresco Share" border="0"/></a>
        //    <span>Supplied free of charge with
        copyrightText { $("span.copyright span").text() }

        /*
        <div class="dashlet webview webview-mode-default resizable yui-resize" id="yui-gen4">
          <div class="title">
             <span target="_blank" class="default-title" id="page_x002e_component-2-2_x002e_user_x007e_admin_x007e_dashboard_x0023_default-default-title">Web View</span>
             <span target="_blank" class="notsecure-title" id="page_x002e_component-2-2_x002e_user_x007e_admin_x007e_dashboard_x0023_default-notsecure-title">Web View</span>
             <a target="_blank" class="iframe-title" id="page_x002e_component-2-2_x002e_user_x007e_admin_x007e_dashboard_x0023_default-iframe-title" href="http://www.alfresco.com">Alfresco!</a>
          </div>
         */
        webViewTitle  { $("div.title a.iframe-title").text() }

        //  <div id="additional-footer">
        additonalFooterText { $("div#additional-footer").text() }

        // <div data-dojo-attach-point="parentNode" class="alfresco-layout-LeftAndRight widgets alfresco-header-Header alfresco-debug-Info highlight"
        //      id="SHARE_HEADER" widgetid="SHARE_HEADER">
        headerDiv { $("div#SHARE_HEADER") }

        /*
        <div tabindex="-1" role="menuitem" data-dojo-attach-point="focusNode" class="dijitReset dijitInline dijitMenuItemLabel alfresco-debug-Info highlight dijitMenuItem dijitPopupMenuItem"
            style="-moz-user-select: none;" id="HEADER_SITES_MENU" aria-label="Sites " widgetid="HEADER_SITES_MENU"
         */
        headerSitesMenuDiv { $("div#HEADER_SITES_MENU")}

        /*
        <div>
            ...
               <span class="align-right yui-button-align">
                  <span class="first-child">
                   <a class="theme-color-1" href="/share/page/start-workflow">
                      <img width="16" style="vertical-align: text-bottom" src="/share/res/components/images/workflow-16.png" />
                        Start Workflow
                   </a>

                   Note. myTasksDashletStartWorkflowLink { $("a", href : "/share/page/start-workflow") }
                        does not work for some reason...
         */
        myTasksDashletStartWorkflowLink { $("a", href : contains("/share/page/start-workflow")) }
    }
}