package operations;

import org.apache.log4j.Logger;
import org.junit.Assert;

public class AssertionOperation {

    private Logger log = Logger.getLogger(AssertionOperation.class);
    private String logMessage = "";

    public void  checkEquals(String expectedValue, String actualValue){

        if(!expectedValue.equals(actualValue)){
            logMessage = String.format(" '%s' ve '%s' değerler birbirine eşit değil! ", expectedValue, actualValue );
            log.error(logMessage);
            Assert.fail(logMessage);
        }
        logMessage = String.format(" '%s' ve '%s' değerleri bir birine eşit mi kontrol edildi.", expectedValue, actualValue);
        log.info(logMessage);
    }


    public void  containsText(String actualText, String expectedText){

        if(!actualText.contains(expectedText)){
            logMessage = String.format(" '%s' içinde '%s' değeri bulunamadı! ", actualText, expectedText );
            log.error(logMessage);
            Assert.fail(logMessage);
        }
        logMessage = String.format(" '%s' değeri '%s' değerini içeriyor mu kontrol edildi.", actualText, expectedText);
        log.info(logMessage);
    }

    public void  checkNotEquals(String expectedValue, String actualValue){

        if(expectedValue.equals(actualValue)){
            logMessage = String.format(" '%s' ve '%s' değerleri birbirine eşit! ", expectedValue, actualValue );
            log.error(logMessage);
            Assert.fail(logMessage);
        }
        logMessage = String.format(" '%s' ve '%s' değerleri bir birine eşit değil mi kontrol edildi.", expectedValue, actualValue);
        log.info(logMessage);
    }
}
