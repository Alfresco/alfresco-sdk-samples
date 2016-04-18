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
import org.alfresco.tutorials.pages.AdminHomePage
import org.alfresco.tutorials.pages.LoginPage
import org.alfresco.tutorials.pages.RepositoryBrowseCompanyHomeFolderPage
import org.alfresco.tutorials.pages.RepositoryBrowseDataDictionaryFolderPage
import org.alfresco.tutorials.pages.RepositoryBrowseScriptsFolderPage
import spock.lang.Stepwise

/**
 * Tests for Share Extension Point: Document Library - Actions
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
class DocLibITSpec extends GebReportingSpec {
    def "Test valid login"() {
        given: "I navigate to the login page"
        def currentPage = to LoginPage

        when: "I enter a valid Administrator username and password and click login"
        currentPage.login("admin", "admin")

        then: "I'm redirected to the Admin User Dashboard page"
        at AdminHomePage
    }

    def "Test navigating to the Repository Browser page (Top Level)"() {
        given: "I'm at the Admin User Dashboard Page"
        def currentPage = at AdminHomePage

        when: "I click on the Repository link in the top menu"
        currentPage.repositoryLink.click()

        then: "I'm redirected to the Repository Browser page (Top Level)"
        at RepositoryBrowseCompanyHomeFolderPage
    }

    def "Test that the Go-to-Google DocLib action is available on a folder, but no other custom actions"() {
        given: "I'm at the Repository Browser Page top folder level"
        def currentPage = at RepositoryBrowseCompanyHomeFolderPage

        when: "I hover over folder row to expand the popup-menu and then click More..."
        interact { // An Actions instance is implicitly created, built into an Action
            moveToElement currentPage.dataDictionaryFolderRow
            perform()
            currentPage.showMorePopupMenuItem.click()
        }
        // Example of doing it via Actions API:
        //        def actions = new Actions(browser.driver)
        //      actions.moveToElement(currentPage.dataDictionaryFolderRow.firstElement()).perform()

        then: "I should then have the Go-to-Google DocLib action visible, but no other custom actions"
        currentPage.goToGoogleActionPopupMenuItem.displayed
        !currentPage.existSendAsEmailAction()
        !currentPage.existCallWebScriptAction()
        !currentPage.existShowMessageAction()
    }

    def "Test navigating to the Data Dictionary folder on the Repository Browser page (Top Level)"() {
        given: "I'm at the Repository Browser Page /Company Home folder level"
        def currentPage = at RepositoryBrowseCompanyHomeFolderPage

        when: "I click on the Data Dictionary folder link"
        currentPage.dataDictionaryFolderLink.click()

        then: "I'm redirected to the Repository Browser page for the Data Dictionary folder"
        at RepositoryBrowseDataDictionaryFolderPage
    }

    def "Test navigating to the Scripts folder on the Repository Browser page (Data Dictionary level)"() {
        given: "I'm at the Repository Browser Page /Company Home/Data Dictionary folder level"
        def currentPage = at RepositoryBrowseDataDictionaryFolderPage

        when: "I click on the Scripts folder link"
        currentPage.scriptsFolderLink.click()

        then: "I'm redirected to the Repository Browser page for the Data Dictionary/Scripts folder"
        at RepositoryBrowseScriptsFolderPage
    }

    def "Test that all the custom DocLib actions are available on a File"() {
        given: "I'm at the Repository Browser Page for the Data Dictionary/Scripts folder"
        def currentPage = at RepositoryBrowseScriptsFolderPage

        when: "I hover over a file row to expand the popup-menu and then click More..."
        interact { // An Actions instance is implicitly created, built into an Action
            moveToElement currentPage.checkAlfrescoDocsJsFileRow
            perform()
            currentPage.showMorePopupMenuItem.click()
        }

        then: "all the DocLib actions are visible"
        currentPage.goToGoogleActionPopupMenuItem.displayed
        currentPage.sendAsEmailActionPopupMenuItem.displayed
        currentPage.callWebScriptActionPopupMenuItem.displayed
        currentPage.showMessageActionPopupMenuItem.displayed
    }

    def "Test that the Send-as-Email custom DocLib action shows a form"() {
        given: "I'm at the Repository Browser Page for the Data Dictionary/Scripts folder"
        def currentPage = at RepositoryBrowseScriptsFolderPage

        when: "I hover over a file row to expand the popup-menu and then click More..."
        interact { // An Actions instance is implicitly created, built into an Action
            moveToElement currentPage.checkAlfrescoDocsJsFileRow
            perform()
            currentPage.showMorePopupMenuItem.click()
            currentPage.sendAsEmailActionPopupMenuItem.click()
        }

        then: "I should be able to cancel out of the Send-as-Email DocLib action form after filling it in"
        currentPage.fillInAndCancelSendAsEmailForm("someone@alfresco.com", "Email with attachments", "This is the body of the email")
    }

    def "Test that the custom Metadata template for Acme Document Type works"() {
        given: "I navigate to the Repository Browser Page top folder"
        def currentPage = to RepositoryBrowseCompanyHomeFolderPage

        expect: "to see a text file with property acme:documentId displayed in 2 ways"
        currentPage.acmeTextFileRow
        currentPage.acmeTextFileRowDocId_1
        currentPage.acmeTextFileRowDocId_2
    }
}

