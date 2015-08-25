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
class DocLibTestSpec extends GebReportingSpec {
/*
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

    def "Test navigating to the Repository Browser page"() {
        given: "I'm at the Admin User Dashboard Page"
        to AdminHomePage

        when: "I click on the Repository link in the top menu"
        repositoryLink.click()

        then: "I'm redirected to the Repository Browser page"
        at RepositoryPage
    }

    def "Test navigating to the Data Dictionary folder on the Repository Browser page"() {
        given: "I'm at the Repostory Browser Page top folder level"
        to RepositoryPage

        when: "I click on the Data Dictionary folder link"
        dataDictionaryFolderLink.click()

        then: "I'm still at the Repository Browser page"
        at RepositoryPage
    }
*/
}

