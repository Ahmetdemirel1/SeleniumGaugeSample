package operations;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import util.WebDriverUtil;

public class SendKeyOperation {


    private Logger log = Logger.getLogger(SendKeyOperation.class);
    private String logMessage = "";

    private WaitOperation waitOperation;
    private ScrollOperation scrollOperation;
    private WebDriver webDriver;

    public SendKeyOperation(){
        webDriver = WebDriverUtil.getInstance().getWebDriver();
        waitOperation = new WaitOperation();
        scrollOperation = new ScrollOperation();
    }

    public void sendKey(By by, String value){

        try {
            waitOperation.waitPresence(by);
            scrollOperation.scrollToElement(by);
            WebElement webElement = waitOperation.waitClickable(by);
            webElement.sendKeys(value);
        }
        catch (StaleElementReferenceException | ElementNotInteractableException staleElementReferenceException){
            logMessage = String.format(" '%s' elementine sendKey yapılırken hata oluştu!", by);
            log.error(logMessage);
            sendKey(by, value);
        }
    }

    public void sendKeyWithJavaScript(By by, String value){

        try {
            waitOperation.waitPresence(by);
            scrollOperation.scrollToElement(by);
            WebElement webElement =  waitOperation.waitPresence(by);
            String injectScript = String.format("arguments[0].value='s';", value);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
            javascriptExecutor.executeScript(injectScript,webElement);

        }
        catch (StaleElementReferenceException | ElementNotInteractableException staleElementReferenceException){
            logMessage = String.format(" '%s' elementine sendKey yapılırken hata oluştu!", by);
            log.error(logMessage);
            sendKeyWithJavaScript(by, value);
        }
    }

    public void clear(By by){

        try {
            waitOperation.waitPresence(by);
            scrollOperation.scrollToElement(by);
            waitOperation.waitUntilReadyForDocumentObjectModel();
            WebElement webElement = waitOperation.waitClickable(by);
            webElement.clear();
        }
        catch (StaleElementReferenceException staleElementReferenceException){
            logMessage = String.format(" '%s' elementi temizlenirken sorun oluştu!", by);
            log.error(logMessage);
            clear(by);
        }
        catch (Exception e){
            String exceptionError = String.format("'%s' elementinin text değeri temizlenirken hata oluştu! Hata kodu: %'s' ", by, e);
            log.error(exceptionError);
            Assert.fail(exceptionError);
        }
    }

}
