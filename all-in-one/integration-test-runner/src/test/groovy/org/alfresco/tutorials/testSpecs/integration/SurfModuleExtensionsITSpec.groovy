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
package org.alfresco.tutorials.testSpecs.integration

import geb.spock.GebReportingSpec
import org.alfresco.tutorials.pages.*
import spock.lang.Stepwise

/**
 * Tests for Share Extension Point: Surf Module Extensions.
 * <p/>
 * We extend our test specification from GebReportingSpec. You can also extend from GebSpec but GebReportingSpec will
 * automatically create a screenshot if your test fails, and scrape HTML, which is more convenient.
 * <p/>
 * Please note that you need Firefox to be installed if you want to run the tests.
 */
// Assume a workflow.
// Make sure we run each test method in the order they are written in the class and re-use browser context for each test.
// If one method fails the test will stop.
@Stepwise
class SurfModuleExtensionsITSpec extends GebReportingSpec {

    def "Test valid login"() {
        given: "I navigate to the login page"
        to LoginPage

        when: "I enter a valid Administrator username and password and click login"
        login("admin", "admin")

        then: "I'm redirected to the Admin User Dashboard page"
        at AdminHomePage
    }

    def "Test for new dashboard page content"() {
        given: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        expect: "to see the new content added by Surf Extension Module"
        newContentDiv.text() == "Hello World!"
    }

    def "Test for removed dashboard page content"() {
        given: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        expect: "not to see the My Sites Dashlet, which was removed by Surf Extension Module"
        !mySitesDivExists
    }

    def "Test for customized footer copyright label"() {
        given: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        expect: "there to be a customized Community copyright label in the footer"
        copyrightText == "This is free software. Copyright Alfresco forever"
    }

    def "Test for customized Web View dashlet"() {
        given: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        expect: "there to be a customized Web View Dashlet displaying http://www.alfresco.com"
        webViewTitle == "Alfresco!"
    }

    def "Test for additional custom content in footer"() {
        given: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        expect: "there to be some additional text before the out-of-the-box footer"
        additonalFooterText == "Additional Footer!"
    }

    def "Test navigating to the Repository Browser page"() {
        given: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        when: "I click on the Repository link in the top menu"
        repositoryLink.click()

        then: "I'm redirected to the Repository Browser page (Top Level)"
        at RepositoryBrowseCompanyHomeFolderPage
    }

    def "Test creating a new text document with acme:document type set"() {
        given: "I'm at the Repository Browser page"
        at RepositoryBrowseCompanyHomeFolderPage

        when: "I click on the 'Create an Acme Text Document' link in the 'Create...' drop down menu"
        createMenuButton.click()
        createAcmeDocumentLink.click()

        then: "I'm redirected to the Create Content Page, which has the custom property"
        at CreateAcmeDocumentPage
        createForm.prop_acme_documentId != null
    }

    def "Test that header/menu has custom background color"() {
        given: "I navigate to the Admin User Dashboard Page"
        to AdminHomePage

        expect: "The background color for heading/menu to be custom green"
        headerDiv.css("background-color") == "rgba(121, 146, 18, 1)" // same as "#799212"
    }
}

