package testcases;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class LoginTest extends BaseTest {

    @Test(priority = 1)
    public void doLogin() {

        Browser browser = getBrowser("chrome");
        navigate(browser, "https://www.google.com/");
        type("searchBox", "searchBoxValue");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test(priority = 2)
    public void doGmailLogin() {

        Browser browser = getBrowser("firefox");
        navigate(browser, "https://gmail.google.com/");

        type("emailGmailField", "emailGmailValue");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /*@Test
    public void openHomePageFbn() {

        Browser browser = getBrowser("chrome");
        navigate(browser, "https://fbn-dev-aqs2/home");

        // Login
        type("fbnUsername", "fbnUsernameValue");
        type("fbnPassword", "fbnPasswordValue");
        click("fbnSubmitBtn");

        browserContext.storageState(new BrowserContext.StorageStateOptions().setPath(Paths.get("auth.json")));
        System.out.println("SETUP storeState: " + browserContext.storageState());

    }*/
}
