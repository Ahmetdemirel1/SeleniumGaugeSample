package operations;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import util.WebDriverUtil;

public class ScrollOperation {

    private WebDriver webDriver;
    private WaitOperation waitOperation;

    public ScrollOperation(){
        webDriver = WebDriverUtil.getInstance().getWebDriver();
        waitOperation = new WaitOperation();

    }

    public void scrollToElement(By by){

        WebElement webElement = waitOperation.waitPresence(by);
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].scrollIntoView();", webElement);
    }

}
