package lv.qaguru.qa2.core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * Created by user on 2017.05.20..
 */
public class BaseFunctions {

    public WebDriver driver;
    private static final String CHROME_DRIVER_LOCATION = "C:/dev/browser/chromedriver.exe";
//    private static final String FIREFOX_DRIVER_LOCATION = "C:/dev/browser/geckodriver.exe";
    private static final Logger LOGGER = Logger.getLogger(BaseFunctions.class);

    public BaseFunctions() {
       // this.initDriver();
    }

    public void initDriver()    {
//        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_LOCATION);
//        this.driver = new FirefoxDriver();
        System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_LOCATION);
        this.driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    public void goToUrl(String url) {
        if(!url.contains("http://") && !url.contains("https://")) {
            url = "http://" + url;
        }
        LOGGER.info("goToUrl > User goes to: " + url);
        driver.get(url);
    }


    /** workaround for page content dynamic load : scroll down, then up */
    public void scrollPageToggle() {
        this.scrollDownToPageEnd();
        this.scrollUpToPageTop();
    }
    public void scrollDownToPageEnd() {
        ((JavascriptExecutor)driver).executeScript("console.log('QA > Selenium > scrollDownToPageEnd()'); window.scrollTo(0, document.body.scrollHeight);");
    }
    public void scrollUpToPageTop() {
        ((JavascriptExecutor)driver).executeScript("console.log('QA > Selenium > scrollUpToPageTop()'); window.scrollTo(0, 0);");
    }


    public void stopDriver() {
        /*
        webDriver.Close() - Close the browser window that the driver has focus of
        webDriver.Quit() - Calls Dispose()
        webDriver.Dispose() Closes all browser windows and safely ends the session
         */
        LOGGER.info("stopDriver > Stopping driver");
        //driver.close();
        driver.quit();
    }


    public void waitForElement(By element, long mills) {
        WebDriverWait wait = new WebDriverWait(driver, mills);
        wait.until(ExpectedConditions.visibilityOfElementLocated(element));
    }


    public boolean isPresentElement(By element) {
        List<WebElement> elements = driver.findElements(element);
        return !elements.isEmpty();
    }

}
