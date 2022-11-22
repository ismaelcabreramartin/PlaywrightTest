package testcases;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import org.testng.annotations.Test;

public class UserRegTest extends BaseTest {

    @Test(priority = 3)
    public void doLogin2() {

        Browser browser = getBrowser("webkit");
        navigate(browser, "https://www.google.com/");
        type("searchBox", "searchBoxValue");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test(priority = 4)
    public void doGmailLogin2() {

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
