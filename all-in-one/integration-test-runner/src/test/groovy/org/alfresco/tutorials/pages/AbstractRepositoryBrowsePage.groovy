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
 * Base Page Object for Alfresco Share Repository Browse Pages.
 *
 * @author marting.bergljung@alfresco.com
 */
abstract class AbstractRepositoryBrowsePage extends Page {
    // A description of the page content, allowing for easy access to the parts declared here.
    // If it is not working for some content items then have a look at the Geb reports directory,
    // which contains the HTML that Geb is seeing... (i.e.
    // integration-test-runner/target/geb-reports/org/alfresco/tutorials/testSpecs/SurfModuleExtensionsTestSpec)
    static content = {
        /*
        <div class="create-content">
            <span class="yui-button yui-push-button yui-menu-button" id="template_x002e_documentlist_v2_x002e_repository_x0023_default-createContent-button">
                <span class="first-child">
                    <button type="button" aria-haspopup="true" tabindex="0"
                            id="template_x002e_documentlist_v2_x002e_repository_x0023_default-createContent-button-button">
                                    Create... ▾</button>
        */
        createMenuButton(wait: true) { $("div.create-content span span button", text: startsWith("Create...")) }

        /*
        <div class="bd">
            <ul class="first-of-type">
                <li id="yui-gen0" class="yuimenuitem first-of-type" groupindex="0" index="0">
                    <a rel="" href="/share/page/create-content?destination=%7BnodeRef%7D&amp;itemId=acme:document&amp;mimeType=text/plain" class="yuimenuitemlabel">

        Don't need to wait for this one as we wait for the Create... menu to be available...
        */
        createAcmeDocumentLink(to: CreateAcmeDocumentPage) { $("a", href : contains("itemId=acme:document")) }

        /*
        <div class="internal-show-more" id="onActionShowMore">
            <a href="#" class="show-more" alt="More..." aria-haspopup="true" id="yui-gen700">
                <span id="yui-gen699">More...</span>
            </a>
        </div>

        Only visible if hover over document list row
        */
        showMorePopupMenuItem(required: false) { $("div.internal-show-more a.show-more") }

        /*
        <a title="Go to Google" class="simple-link" href="http://www.google.com" style="background-image:url(/share/res/components/documentlibrary/actions/google-16.png)"
        target="_blank" id="yui-gen511"><span id="yui-gen513">Go to Google</span></a>


        Only visible if hover over document list row, and then clicking 'More...' menu item
        */
        goToGoogleActionPopupMenuItem(required: false) { $("a", title: "Go to Google") }

        /*
        <a title="Call Web Script" class="action-link" href="#" style="background-image:url(/share/res/components/documentlibrary/actions/callws-16.png)"
        id="yui-gen745"><span id="yui-gen746">Call Web Script</span></a>

        Only visible if hover over document list row, and then clicking 'More...' menu item
        */
        callWebScriptActionPopupMenuItem(required: false) { $("a", title: "Call Web Script") }

        /*
        <a title="Show Message" class="action-link" href="#" style="background-image:url(/share/res/components/documentlibrary/actions/showmsg-16.png)"
        id="yui-gen762"><span id="yui-gen763">Show Message</span></a>

        Only visible if hover over document list row, and then clicking 'More...' menu item
        */
        showMessageActionPopupMenuItem(required: false) { $("a", title: "Show Message") }

        /*
        <a title="Send as Email" class="action-link" href="#" style="background-image:url(/share/res/components/documentlibrary/actions/email-16.png)"
        id="yui-gen743"><span id="yui-gen744">Send as Email</span></a>

        Only visible if hover over document list row, and then clicking 'More...' menu item
        */
        sendAsEmailActionPopupMenuItem(required: false) { $("a", title: "Send as Email") }

        /**
         * Send-as-email form - Cancel button
         *
         <form action="/share/proxy/alfresco/api/action/send-as-email/formprocessor"
                            enctype="application/json" accept-charset="utf-8" method="post" id="alf-id186-form" forms-runtime="listening" onsubmit="return false;">

            <input type="hidden" value="workspace://SpacesStore/b92072cd-ce37-45d2-ae6b-072218a3fe6b" name="alf_destination" id="alf-id186-form-destination" />

            <div class="form-fields" id="alf-id186-form-fields">
                ...
            <div class="form-buttons" id="alf-id186-form-buttons">
                <span class="yui-button yui-submit-button" id="alf-id186-form-submit">
                    <span class="first-child">
                        <button type="button" tabindex="0" id="alf-id186-form-submit-button" name="-">OK</button></span></span> 
                <span class="yui-button yui-push-button" id="alf-id186-form-cancel">
                    <span class="first-child">
                        <button type="button" tabindex="0" id="alf-id186-form-cancel-button" name="-">Cancel</button></span></span>

            This form is not required to be on the page, but when we expect it to be there we also wait for it to appear...
         */
        sendAsEmailActionForm(wait: true, required: false) { $("form", action: contains("send-as-email")) }
    }

    /**
     * Fill in the Send-As-Email action form and then cancel out of it.
     *
     * @param toAddress the address you want to send the email to
     * @param subject the subject of the email
     * @param bodyText the body text of the email
     */
    boolean fillInAndCancelSendAsEmailForm(String toAddress, String subject, String bodyText) {
        sendAsEmailActionForm.prop_to = toAddress
        sendAsEmailActionForm.prop_subject = subject
        sendAsEmailActionForm.prop_body_text = bodyText
        sendAsEmailActionForm.find("button", text: "Cancel").click()
        return true;
    }

    /*
        Because Geb utilizes Groovy Truth to redefine how navigator are coerced to boolean values
        (empty navigators are falsey and non-empty are truthy) we can write exists methods such as:
    */
    boolean existSendAsEmailAction() { sendAsEmailActionPopupMenuItem }
    boolean existCallWebScriptAction() { callWebScriptActionPopupMenuItem}
    boolean existShowMessageAction() { showMessageActionPopupMenuItem }
}