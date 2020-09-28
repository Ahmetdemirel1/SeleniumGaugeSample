package implementation;

import base.BasePage;
import com.thoughtworks.gauge.Step;
import element_helper.ElementHelper;
import element_helper.ElementInfo;
import element_helper.StoreHelper;
import operations.*;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.WebDriverUtil;

import java.util.List;


public class StepImplementation {

    private Logger log;
    private String logMessage = "";
    private ClickOperation clickOperation;
    private WaitOperation waitOperation;
    private ScrollOperation scrollOperation;
    private SendKeyOperation sendKeyOperation;
    private BasePage basePage;
    private AssertionOperation assertionOperation;
    private SelectOperation selectOperation;


    public StepImplementation(){
        log = Logger.getLogger(StepImplementation.class);
        clickOperation = new ClickOperation();
        waitOperation = new WaitOperation();
        scrollOperation = new ScrollOperation();
        sendKeyOperation = new SendKeyOperation();
        basePage = new BasePage();
        assertionOperation = new AssertionOperation();
        selectOperation = new SelectOperation();
    }



    public ElementInfo getElementInfo(String key){

        return StoreHelper.INSTANCE.findElementByInfo(key);
    }

    public By getBy (String key){

        return ElementHelper.getElementInfoToBy(getElementInfo(key));
    }


    @Step("<> ortamında çalıştır")
    public void navigateToUrl(String url){
        WebDriverUtil.getInstance().getWebDriver().navigate().to(url);
    }

    @Step("<key> elementine tıklanır ")
    public void clickElement(String key){

        By locator = getBy(key);
        clickOperation.click(locator);
        logMessage = String.format("'%s' elementine tıklandı.", key);
        log.info(logMessage);

    }

    @Step("<key> elementine JavaScript ile tıklanır ")
    public void clickElementWithJavaScript(String key){

        By locator = getBy(key);
        clickOperation.clickWithJavaScript(locator);
        logMessage = String.format("'%s' elementine JS ile tıklandı.", key);
        log.info(logMessage);

    }

    @Step("<key> alanına <value> yazılır")
    public void sendKey(String key, String value){

        By locator = getBy(key);
        sendKeyOperation.sendKey(locator,value);
        logMessage = String.format("'%s' alanına '%s' değeri yazıldı ", key, value);
        log.info(logMessage);
    }

    @Step("<key> alanına JavaScript ile <value> yazılır")
    public void sendKeyWithJavaScript(String key, String value){

        By locator = getBy(key);
        sendKeyOperation.sendKeyWithJavaScript(locator,value);
        logMessage = String.format("'%s' alanına '%s' değeri yazıldı ", key, value);
        log.info(logMessage);
    }

    @Step("<key> alanına random mail adresi yazılır")
    public void generateRandomlyMail(String key){

        String randomMailAddres = "testautomation" + basePage.getRandomNumberByDigitCount(10)+ "@gmail.com";
        sendKey(key,randomMailAddres);

    }

    @Step("<sec> saniye beklenir")
    public void wait(int sec){

        waitOperation.wait(sec);
        logMessage = String.format("'%s' saniye beklendi", sec);
        log.info(logMessage);
    }

    @Step("<key> alanı <expectedValue> değerini eşit mi kontrolü yapılır")
    public void checkElementValue(String key, String expectedValue){

        By locator = getBy(key);
        String actualValue = basePage.getText(locator);
        assertionOperation.checkEquals(expectedValue,actualValue);

    }

    @Step("<key> listesinden <value> objesi seçilir")
    public void selectValueFromList(String key, String value){

        By locator = getBy(key);
        basePage.selectFromList(locator,value);
        logMessage = String.format("'%s' listesinden '%s' değeri seçildi ", key, value);
        log.info(logMessage);
    }

    @Step("Sayfa üzerinde <key> elementi görünene kadar beklenir")
    public void waitUntilVisible(String key){
        By locator = getBy(key);
        waitOperation.waitPresence(locator);
        logMessage = String.format("'%s' elementi görünene kadar beklendi", key);
        log.info(logMessage);

    }

    @Step("<key> element görüntülenene kadar kaydır")
    public void scrollToElement(String key){
        By locator = getBy(key);
        scrollOperation.scrollToElement(locator);
        logMessage = String.format("'%s' elementi görünene kadar kaydırıldı", key);
        log.info(logMessage);
    }

    @Step("<key> listesinden <value> değeri seçilir")
    public void chooseValueOnTheSelectObject(String key, String value){
        By locator = getBy(key);
        selectOperation.chooseItemOnTheSelectElementByValue(locator,value);
        logMessage = String.format("'%s' select listesinden '%s' değeri seçildi", key, value);
        log.info(logMessage);
    }

    @Step("<key> alanı <expectedValue> değerini içeriyor mu kontrolü yapılır")
    public void checkElementContains(String key, String expectedValue){

        By locator = getBy(key);
        String actualValue = basePage.getText(locator);
        assertionOperation.containsText(actualValue,expectedValue);

    }

    @Step("<key> listesindeki elementler <expectedValue> değerini içeriyor mu kontrolü yapılır")
    public void checkElementContainsFromList(String key, String expectedValue){
        By locator = getBy(key);

        waitOperation.waitPresence(locator);
        waitOperation.waitUntilReadyForDocumentObjectModel();

        List<WebElement> elements = waitOperation.waitPresenceOffAllElements(locator, 3);
        for (WebElement element : elements) {
            String actualValue = element.getText();
            assertionOperation.containsText(actualValue,expectedValue);

        }
    }
}
