package operations;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.WebDriverUtil;

import java.util.ArrayList;
import java.util.List;

public class WaitOperation {

    private WebDriver webDriver;

    private Logger log = Logger.getLogger(WaitOperation.class);
    private String logMessage = "";

    private int defaultTimeOutSecond = 30;
    private int defaultIntervalMilliSecond = 1000;


    public WaitOperation(){
        webDriver = WebDriverUtil.getInstance().getWebDriver();
        this.webDriver = WebDriverUtil.getInstance().getWebDriver();
    }

    public WebElement waitPresence(By by){
         return  waitPresence(by, defaultTimeOutSecond);
    }

    public WebElement waitPresence(By locator, int sec){
        WebElement webElement = null;
        try {
            waitUntilReadyForDocumentObjectModel();
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, sec, defaultIntervalMilliSecond);
            webElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }
        catch (Exception e){
            String errorMessage = String.format(" '%s' elementinin sayfa üzerinde var olması beklenirken hata oluştu! Hata kodu '%s'", locator, e.getMessage());
            log.error(errorMessage);
            Assert.fail(errorMessage);
        }
        return  webElement;
    }

    public void waitInvisible(By by){
         waitInvisible(by, defaultTimeOutSecond);
    }

    public void waitInvisible(By locator, int sec){
        try {
            waitUntilReadyForDocumentObjectModel();
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, sec, defaultIntervalMilliSecond);
            webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }
        catch (StaleElementReferenceException | NoSuchElementException e) {
            String logMessage = String.format(" '%s' elementinin sayfa üzerinde kaybolması beklenirken sorun oluştué", locator);
            log.error(logMessage);
            Assert.fail(logMessage);
            waitInvisible(locator, sec);
        }
        catch (Exception e){
            String errorMessage = String.format(" '%s' elementinin sayfa üzerinde kaybolması beklenirken hata oluştu! Hata kodu '%s'", locator, e.getMessage());
            log.error(logMessage);
            Assert.fail(logMessage);
        }
    }

    public WebElement waitClickable(By by){
        return  waitClickable(by, defaultTimeOutSecond);
    }

    public WebElement waitClickable(By locator, int sec){
        WebElement webElement = null;
        try {
            waitUntilReadyForDocumentObjectModel();
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, sec, defaultIntervalMilliSecond);
            webElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
        }
        catch (Exception e){
            String errorMessage = String.format(" '%s' elementinin sayfa üzerinde tıklanabilir olması beklenirken hata oluştu! Hata kodu '%s'", locator, e.getMessage());
            log.error(errorMessage);
        }
        return  webElement;
    }

    public List<WebElement> waitPresenceOffAllElements(By locator,int sec){
        List<WebElement> elementList = new ArrayList<>();
        try {
            waitUntilReadyForDocumentObjectModel();
            WebDriverWait webDriverWait = new WebDriverWait(webDriver, sec, defaultIntervalMilliSecond);
            elementList = webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        }
        catch (Exception e){
            String errorMessage = String.format(" '%s' elementleri sayfa üzerinde görünür olması beklenirken hata oluştu! Hata kodu '%s'", locator, e.getMessage());
            log.error(errorMessage);
        }
        return  elementList;
    }



    public void waitUntilReadyForDocumentObjectModel(){
        new WebDriverWait(webDriver, defaultTimeOutSecond).until((ExpectedCondition<Boolean>)
                wd -> ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public void wait(int sec){

        try {
            Thread.sleep(sec * (long)1000);
            String logMessage = String.format("'%s' saniye statik bekleme yapıldı", sec);
            log.warn(logMessage);
        }
        catch (Exception e){
            log.error("Statik bekleme yapılırken hata oluştu! Hata kodu: " + e.getMessage());
        }
    }
}
