package operations;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class SelectOperation {

    private WaitOperation waitOperation;
    private Logger log = Logger.getLogger(SelectOperation.class);
    private String logMessage = "";

    public SelectOperation(){

        waitOperation = new WaitOperation();
    }


    public void chooseItemOnTheSelectElementByValue(By by, String value){
        try {
            waitOperation.waitPresence(by);
            waitOperation.waitUntilReadyForDocumentObjectModel();
            WebElement webElement = waitOperation.waitClickable(by);
            Select select = new Select(webElement);
            select.selectByVisibleText(value);
        }
        catch (StaleElementReferenceException | NullPointerException exception){
            logMessage = String.format("'%s' elementine tıklanırken sorun oluştu!", by);
            log.error(logMessage);
            log.error(exception);
            chooseItemOnTheSelectElementByValue(by, value);
        }
    }

}
