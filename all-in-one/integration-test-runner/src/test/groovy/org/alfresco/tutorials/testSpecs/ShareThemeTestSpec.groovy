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
package org.alfresco.tutorials.testSpecs

import geb.spock.GebReportingSpec
import org.alfresco.tutorials.pages.AdminHomePage
import org.alfresco.tutorials.pages.AdminToolsPage
import org.alfresco.tutorials.pages.CreateAcmeDocumentPage
import org.alfresco.tutorials.pages.LoginPage
import org.alfresco.tutorials.pages.RepositoryPage
import spock.lang.Stepwise

/**
 * Tests for Share Extension Point: Share Themes
 *
 * We extend our test specification from GebReportingSpec. You can also extend from GebSpec but GebReportingSpec will
 * automatically create a screenshot if your test fails, which is more convenient.
 * Please note that you need Firefox to be installed if you want to run the tests.
 */
// Make sure we run tests in the order they are written in the class and re-use browser context for each test
@Stepwise
class ShareThemeTestSpec extends GebReportingSpec {

    def "Test valid login"() {
        given: "I'm at the login page"
        to LoginPage

        when: "I enter a valid username and password"
        loginForm.username = "admin"
        loginForm.password = "admin"
        submitButton.click()

        then: "I'm redirected to the User Dashboard page, which displays my username"
        at AdminHomePage
    }

    def "Test navigating to the Admin Tools page"() {
        given: "I'm at the Admin User Dashboard Page"
        to AdminHomePage

        when: "I click on the Admin Tools link in the top menu"
        adminToolsLink.click()

        then: "I'm redirected to the Admin Tools page"
        at AdminToolsPage
    }

    def "Test selecting the Tutorial Theme from the Drop down"() {
        given: "I'm at the Admin Tools page"
        to AdminToolsPage

        when: "I select the 'Tutorial Theme' in the 'Theme' drop down menu and click Apply button"
        themeDropDown = 'Tutorial Theme'
        applyButton.click()

        then: "I'm still on the Admin Tools Page"
        at AdminToolsPage
    }

    def "Test that the new Tutorial Theme is applied to both Aikau and YUI bits"() {
        given: "I'm at the Admin User Dashboard Page"
        to AdminHomePage

        expect: "The color for menu texts to be yellow (Aikau) and dashlet links to be purple (YUI)"
        headerDiv.css("color") == "rgba(255, 255, 0, 1)" // same as "#FFFF00" yellow
        headerSitesMenuDiv.css("color") == "rgba(255, 255, 0, 1)" // same as "#FFFF00" yellow
        myTasksDashletStartWorkflowLink.css("color") == "rgba(127, 0, 255, 1)" // same as "#7F00FF" purple
    }
}

