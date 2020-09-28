package element_helper;

import org.openqa.selenium.By;

public class ElementHelper {


    public static By getElementInfoToBy(ElementInfo elementInfo) {
        By by = null;

        switch (elementInfo.getType()) {
            case "css":
                by = By.cssSelector(elementInfo.getValue());
                return by;

            case "id":
                by = By.id(elementInfo.getValue());
                return by;

            case "xpath":
                by = By.xpath(elementInfo.getValue());
                return by;

            case "className":
                by = By.className(elementInfo.getValue());
                return by;

            case "linkText":
                by = By.linkText(elementInfo.getValue());
                return by;

        }
        return by;
    }


}
