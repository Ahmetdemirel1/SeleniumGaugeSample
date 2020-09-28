package base;

import operations.WaitOperation;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import util.WebDriverUtil;

import java.util.NoSuchElementException;
import java.util.Random;

public class BasePage {

    private Logger log;
    private String logMessage = "";
    private WebDriver webDriver;
    private Random random;
    private WaitOperation waitOperation;



    public BasePage(){
        log = Logger.getLogger(BasePage.class);
        webDriver = WebDriverUtil.getInstance().getWebDriver();
        waitOperation = new WaitOperation();
        random = new Random();
    }


    public String getText(By by){

        String textValue = "";

        try {
            waitOperation.waitPresence(by);
            waitOperation.waitUntilReadyForDocumentObjectModel();
            textValue = waitOperation.waitPresence(by).getText().trim();
        }catch (StaleElementReferenceException | NoSuchElementException e){
            logMessage = String.format("'%s' elementinin text değeri alınırken hata oluştu ", by);
            log.error(logMessage);
            getText(by);
        }catch (Exception e){
            logMessage = String.format("'%s' elementinin text değeri alınırken hata oluştu! Hata kodu: '%s' ", by, e);
            log.error(logMessage);
            Assert.fail(logMessage);
        }

        return textValue;
    }

    public void selectFromList(By by, String value){

        waitOperation.waitPresence(by);
        waitOperation.waitUntilReadyForDocumentObjectModel();
        Select dropdown = new Select(waitOperation.waitPresence(by));
        dropdown.selectByValue(value);

    }


    public String getRandomNumberByDigitCount(int digitCount){

        StringBuilder randomNumber = new StringBuilder();
        int bound = 10;
        for(int i=0; i<digitCount; i++){
            int generateNumber = getRandomNumber(bound);
            if( i == 0 && generateNumber == 0){
                ++generateNumber;
            }
            randomNumber.append(generateNumber);
        }
        return randomNumber.toString();
    }

    public  int getRandomNumber(int bound){
        return random.nextInt(bound);
    }
}
