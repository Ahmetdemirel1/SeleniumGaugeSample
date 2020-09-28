package util;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class WebDriverUtil {

    private static WebDriverUtil instance;

    private WebDriver webdriver;
    private Logger log = Logger.getLogger(WebDriverUtil.class);
    private String logMessage = "";


    private WebDriverUtil() { }


    private DesiredCapabilities capabilities;
    private ChromeOptions options;
    private FirefoxOptions firefoxOptions;

    private void chromeOptions(){
        options = new ChromeOptions();
        capabilities = DesiredCapabilities.chrome();
        options.addArguments("test-type");
        options.addArguments("disable-popup-blocking");
        options.addArguments("ignore-certificate-errors");
        options.addArguments("disable-translate");
        options.addArguments("--start-maximized");
        options.addArguments("disable-automatic-password-saving");
        options.addArguments("allow-silent-push");
        options.addArguments("disable-infobars");
        options.addArguments("--kiosk");
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        capabilities.setBrowserName("chrome");
        capabilities.setPlatform(Platform.getCurrent());

        System.setProperty("webdriver.chrome.verboseLogging", "false");
        selectPath(capabilities.getPlatform());
        System.setProperty("webdriver.chrome.silentOutput", "true");
        webdriver = new ChromeDriver(options);

    }

    private void FirefoxOptions(){
        firefoxOptions = new FirefoxOptions();
        capabilities = DesiredCapabilities.firefox();
        firefoxOptions.addArguments("test-type");
        firefoxOptions.setCapability("marionette", true);
        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
        capabilities.setBrowserName("firefox");
        capabilities.setPlatform(Platform.getCurrent());
        selectPath(capabilities.getPlatform());
        webdriver = new FirefoxDriver(firefoxOptions);
    }

    public static WebDriverUtil getInstance(){
        if(instance == null)
            instance = new WebDriverUtil();
        return instance;
    }

    public void startTheTest(String browser){
        log.error("error browser:" + browser);
        if (browser.contains("chrome")){
            chromeOptions();
            webDriverManageSettings();
        }
        else if( browser.contains("firefox")){
            FirefoxOptions();
            webDriverManageSettings();
        }
        else {
            logMessage = String.format("Browser seçiminden hata oluştu! lütfen 'chrome' yada 'firefox' seçimi yapınız!");
            log.error(logMessage);
        }



    }

    private void webDriverManageSettings(){
        webdriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        webdriver.manage().window().maximize();

    }

    protected void selectPath( Platform platform ) {
        String browser;
        if ("CHROME".equalsIgnoreCase(capabilities.getBrowserName())) {
            browser = "webdriver.chrome.driver";
            switch (platform) {
                case MAC:
                    System.setProperty(browser, "driver/chromedrivermac");
                    break;
                case WIN10:
                case WIN8:
                case WIN8_1:
                case WINDOWS:
                    System.setProperty(browser, "driver/chromedriverwin.exe");
                    break;
                case LINUX:
                    System.setProperty(browser, "driver/chromedriverlinux64");
                    break;
                default:
                    log.info("PLATFORM DOES NOT EXISTS");
                    break;
            }
        }
        else {
            browser = "webdriver.gecko.driver";
            switch (platform) {
                case MAC:
                    System.setProperty(browser, "driver/firefox/geckodriver");
                    break;
                case WIN10:
                case WIN8:
                case WIN8_1:
                case WINDOWS:
                    System.setProperty(browser, "driver/firefox/geckodriverwin64.exe");
                    break;
                case LINUX:
                    System.setProperty(browser, "driver/firefox/geckodriverlinux64");
                    break;
                default:
                    log.info("PLATFORM DOES NOT EXISTS");
                    break;
            }
        }

    }

    public WebDriver getWebDriver(){
        if(webdriver == null){
            String errorMessage = "Driver null durumda!";
            log.error(errorMessage);
            Assert.fail(errorMessage);
        }
        return webdriver;
    }

}
