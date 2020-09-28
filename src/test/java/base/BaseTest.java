package base;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.AfterStep;
import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.ExecutionContext;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import util.WebDriverUtil;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class BaseTest {

    private Logger log = Logger.getLogger(BaseTest.class);
    private String currentScenarioName = "";
    public String browserName = "";


    @BeforeScenario
    public void setup(ExecutionContext context) {
        PropertyConfigurator.configure("src/test/resources/log4j.properties");
        currentScenarioName = context.getCurrentScenario().getName().toUpperCase();
        browserName = context.getCurrentScenario().getTags().toString();
        log.info(String.format("**************** %s Senaryosu Başladı ****************", currentScenarioName));
        startTheTest();
    }

    @AfterScenario
    public void tearDown() {
        try {
            WebDriverUtil.getInstance().getWebDriver().close();
            WebDriverUtil.getInstance().getWebDriver().quit();
            log.info(String.format("**************** %s Senaryosu Bitti ****************", currentScenarioName));
        } catch (Exception e) {
            log.error(String.format("Driver kapatılırken hata oluştu! '%s'", currentScenarioName));
        }
    }


    @AfterStep
    public void takeScreenShot(ExecutionContext context) {
        Date dateTime = new Date();
        if (context.getCurrentScenario().getIsFailing().booleanValue() == true)
            try {
                File scrFile = ((TakesScreenshot) WebDriverUtil.getInstance().getWebDriver()).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File("screenshots/" + currentScenarioName + dateTime +".png"));
            } catch (IOException e) {
                log.error(String.format("Ekran görüntüsü alma işlemi başarısız oldu!"));
            }

    }


    public void startTheTest() {
        WebDriverUtil
                .getInstance()
                .startTheTest(browserName);
    }
}
