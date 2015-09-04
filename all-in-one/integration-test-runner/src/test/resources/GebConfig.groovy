/**
 * When using Geb, a GebConfig.groovy file is required to be on your classpath.
 * The best location is to put it in a recognizable location e.g. test/resources.
 * In this file you configure your WebDriver (e.g. Firefox, Chrome etc) and the profile settings for this driver e.g.
 * download folders, proxy settings etc.
 */
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile

driver = {
    FirefoxProfile profile = new FirefoxProfile()
    // Advanced Actions API relies on native events, such as moveToElement
    // Explicitly enable native events (this is mandatory on Linux system, since they  are not enabled by default)
    profile.setEnableNativeEvents(true);
    new FirefoxDriver(profile)
}

baseUrl = 'http://localhost:8080/share/'
reportsDir = 'target/geb-reports'