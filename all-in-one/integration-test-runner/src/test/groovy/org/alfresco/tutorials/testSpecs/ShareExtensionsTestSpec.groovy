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
import org.alfresco.tutorials.pages.*
import spock.lang.Stepwise

/**
 * We extend our test specification from GebReportingSpec. You can also extend from GebSpec but GebReportingSpec will
 * automatically create a screenshot if your test fails, which is more convenient.
 * Please note that you need Firefox to be installed if you want to run the tests.
 */
// Make sure we run tests in the order they are written in the class and re-use browser context for each test
@Stepwise
class ShareExtensionsTestSpec extends GebReportingSpec {

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

    def "Test for new dashboard page content"() {
        when: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        then: "I should see the new content added by Surf Extension Module"
        newContentDiv.text() == "Hello World!"
    }

    def "Test for removed dashboard page content"() {
        when: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        then: "I should not see the My Sites Dashlet, which was removed by Surf Extension Module"
        !mySitesDiv
    }

    def "Test for customized footer copyright label"() {
        when: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        then: "There should be a customized Community copyright label in the footer"
        copyrightText == "This is free software. Copyright Alfresco forever"
    }

    def "Test for customized Web View dashlet"() {
        when: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        then: "There should be a customized Web View Dashlet displaying http://www.alfresco.com"
        webViewTitle == "Alfresco!"
    }

    def "Test for additional custom content in footer"() {
        when: "I'm at the Admin User Dashboard Page"
        at AdminHomePage

        then: "There should be some additional text before the out-of-the=box footer"
        additonalFooterText == "Additional Footer!"
    }

}

