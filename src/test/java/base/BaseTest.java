package base;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.SelectOption;
import com.microsoft.playwright.options.WaitUntilState;
import extentlisteners.ExtentListeners;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class BaseTest {

    private Playwright playwright;
    private APIRequestContext apiRequestContext;
    public Browser browser;
    public BrowserContext browserContext;
    public Page page;
    private static Properties locators = new Properties();
    private static FileInputStream fis;
    private Logger log = LogManager.getLogger(this.getClass()); //generate applog.txt
    //private Logger log = Logger.getLogger(this.getClass());

    /*private static String sessionAuthToken;
    private static String refreshAuthToken;
    private static String sourceRef;*/

    private static ThreadLocal<Playwright> pw = new ThreadLocal<>();
    private static ThreadLocal<Browser> br = new ThreadLocal<>();
    private static ThreadLocal<Page> pg = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> brc = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return pw.get();
    }

    public static Browser getBrowserThread() {
        return br.get();
    }

    public static Page getPage() {
        return pg.get();
    }

    public static BrowserContext getBrowserContext() {
        return brc.get();
    }

    @BeforeSuite
    public void setUp() {

        PropertyConfigurator.configure("src/test/resources/properties/log4j.properties");
        log.info("Test Execution Started !!!");

        try {
            fis = new FileInputStream("src/test/resources/properties/locators.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            locators.load(fis);
            log.info("locators Properties file loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void click(String locatorKey) {

        try {
            getPage().locator(locators.getProperty(locatorKey)).click();
            log.info("Clicking on an Element: " + locatorKey);
            ExtentListeners.getExtent().info("Clicking on an Element: " + locatorKey);
        } catch (Throwable t) {
            log.error("Error while clicking on an Element " + t.getMessage());
            ExtentListeners.getExtent().fail("Error while clicking on an Element " + locatorKey + " error message is " + t.getMessage());
            Assert.fail(t.getMessage());
        }

    }

    public boolean isElementPresent(String locatorKey) {

        try {

            getPage().waitForSelector(locators.getProperty(locatorKey), new Page.WaitForSelectorOptions().setTimeout(2000)).isVisible();
            log.info("Finding an Element: " + locatorKey);
            ExtentListeners.getExtent().info("Finding on an Element: " + locatorKey);
            return true;

        } catch (Throwable t) {

            log.error("Error while Finding an Element " + locatorKey);
            ExtentListeners.getExtent().fail("Error while Finding an Element " + locatorKey);
            return false;

        }
    }

    public void type(String locatorKey, String value) {

        try {
            getPage().locator(locators.getProperty(locatorKey)).fill(value);
            log.info("Typing in an Element: " + locatorKey + " and entered the value as: " + value);
            ExtentListeners.getExtent().info("Typing in an Element: " + locatorKey + " and entered the value as: " + value);
        } catch (Throwable t) {
            log.error("Error while typing on an Element " + t.getMessage());
            ExtentListeners.getExtent().fail("Typing in an Element: " + locatorKey + " error message is " + t.getMessage());
            Assert.fail(t.getMessage());
        }

    }

    public void select(String locatorKey, String value) {

        try {
            getPage().selectOption(locators.getProperty(locatorKey), new SelectOption().setLabel(value));
            log.info("Selecting in an Element: " + locatorKey + " and selected the value as: " + value);
            ExtentListeners.getExtent().info("Typing in an Element: " + locatorKey + " and entered the value as: " + value);
        } catch (Throwable t) {
            log.error("Error while Selecting on an Element " + t.getMessage());
            ExtentListeners.getExtent().fail("Selecting in an Element: " + locatorKey + " error message is " + t.getMessage());
            Assert.fail(t.getMessage());
        }

    }

    public Browser getBrowser(String browserName) {

        playwright = Playwright.create();
        pw.set(playwright);

        switch (browserName) {
            case "chrome":
                log.info("Launching Chrome Browser");
                return getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setChannel("chrome").setHeadless(false));

            case "hearless":
                log.info("Launching Hearless Mode");
                return getPlaywright().chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));

            case "firefox":
                log.info("Launching Firefox Browser");
                return getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setChannel("firefox").setHeadless(false));

            case "webkit":
                log.info("Launching Webkit Browser");
                return getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(false));
            default:
                throw new IllegalArgumentException();
        }
    }

    public void navigate(Browser browser, String url) {

        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screensize.getWidth();
        double height = screensize.getHeight();

        this.browser = browser;
        br.set(browser);

        browserContext = this.browser.newContext(new Browser.NewContextOptions().setViewportSize((int) width, (int) height).setIgnoreHTTPSErrors(true));
        brc.set(browserContext);

        page = getBrowserContext().newPage();
        pg.set(page);

        getPage().navigate(url, new Page.NavigateOptions().setWaitUntil(WaitUntilState.LOAD).setTimeout(60000));
        log.info("Navigate to : " + url);

        getPage().onDialog(dialog -> {

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            dialog.accept();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /*
    public void apiLogin() {

        baseURI = locators.getProperty("baseUrl");
        basePath = locators.getProperty("apiEventLogin");

        Map<String, Object> login = new HashMap<String, Object>();
        login.put("USER_NAME", locators.getProperty("fbnUsernameValue"));
        login.put("PASSWORD", locators.getProperty("fbnPasswordValue"));

        Map<String, Object> loginDetails = new HashMap<String, Object>();
        loginDetails.put("DETAILS", login);

        JsonPath extractor =
                given()
                    .contentType(ContentType.JSON)
                    .config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails()))
                    .header("SOURCE_REF", "1234567890")
                    .body(loginDetails)
                .when()
                    .post()
                .then()
                    .extract()
                    .jsonPath()
                ;

        sessionAuthToken = extractor.get("SESSION_AUTH_TOKEN");
        refreshAuthToken = extractor.get("REFRESH_AUTH_TOKEN");
        sourceRef = extractor.get("SOURCE_REF");
    }*/

    @AfterMethod
    public void quit() {

        log.info("Closing... Playwright");
        if (getPage() != null) {
            getBrowserContext().close();
            getPage().close();
            //getPlaywright().close();
        }
    }

    @AfterSuite
    public void quitPlaywright() {

        if (getPage() != null) {
            getPlaywright().close();
        }
    }
}
