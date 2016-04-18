/**
 * When using Geb, a GebConfig.groovy file is required to be on your classpath.
 * The best location is to put it in a recognizable location e.g. test/resources.
 * In this file you configure your WebDriver (e.g. Firefox, Chrome etc) and the profile settings for this driver e.g.
 * download folders, proxy settings etc.
 */

import org.openqa.selenium.chrome.ChromeDriver
//import org.openqa.selenium.firefox.FirefoxDriver
//import org.openqa.selenium.firefox.FirefoxProfile

driver = {

    // For Chrome you need to first download the Chrome webdriver and put somewhere: https://sites.google.com/a/chromium.org/chromedriver/downloads

    // The ChromeDriver provides two modes of supporting native events called WebKit events and raw events.
    // In the WebKit events the ChromeDriver calls the WebKit functions which trigger Javascript events,
    // in the raw events mode operating systems events are used.
    // There is no need to turn on native events, such as with the FireFox driver
    System.setProperty('webdriver.chrome.driver', '/home/martin/drivers/chromedriver')
    new ChromeDriver()

    //FirefoxProfile profile = new FirefoxProfile()
    // Advanced Actions API relies on native events, such as moveToElement
    // Explicitly enable native events (this is mandatory on Linux system, since they  are not enabled by default)
    //profile.setEnableNativeEvents(true);
    //new FirefoxDriver(profile)
}

baseUrl = 'http://localhost:8080/share/'
reportsDir = 'target/geb-reports'