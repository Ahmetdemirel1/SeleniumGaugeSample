package operations;

import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import util.WebDriverUtil;

public class ClickOperation {

    private WebDriver webDriver;
    private WaitOperation waitOperation;
    private ScrollOperation scrollOperation;

    private Logger log = Logger.getLogger(ClickOperation.class);
    private String logMessage = "";


    public ClickOperation(){
        webDriver = WebDriverUtil.getInstance().getWebDriver();
        waitOperation = new WaitOperation();
        scrollOperation = new ScrollOperation();
    }


    public void click(By by){
        try {
            waitOperation.waitPresence(by);
            //scrollOperation.scrollToElement(by);
            WebElement webElement = waitOperation.waitClickable(by);
            webElement.click();
        }
        catch (StaleElementReferenceException | ElementClickInterceptedException exception){
            logMessage = String.format("'%s' elementine tıklanırken sorun oluştu!", by);
            log.error(logMessage);
            click(by);
        }
    }

    public void clickWithJavaScript(By by){
        try {
            waitOperation.waitPresence(by);
            scrollOperation.scrollToElement(by);
            WebElement webElement = waitOperation.waitClickable(by);
            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
            javascriptExecutor.executeScript("arguments[0].click();",webElement);
        }
        catch (StaleElementReferenceException | ElementClickInterceptedException exception){
            logMessage = String.format("'%s' elementine tıklanırken sorun oluştu!", by);
            log.error(logMessage);
            clickWithJavaScript(by);
        }
    }

    public void hover(By by){
        Actions actions = new Actions(webDriver);
        WebElement webElement = waitOperation.waitPresence(by);
        actions.moveToElement(webElement).build().perform();

    }
}
