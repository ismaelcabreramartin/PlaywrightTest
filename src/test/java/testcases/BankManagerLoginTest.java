package testcases;

import base.BaseTest;
import com.microsoft.playwright.Browser;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BankManagerLoginTest extends BaseTest {

    @Test
    public void loginAsBankmanager() {

        Browser browser = getBrowser("chrome");
        navigate(browser, "https://www.way2automation.com/angularjs-protractor/banking/#/login");

        click("bankManagerBtn_CSS");

        Assert.assertTrue(isElementPresent("startAddCustomerBtn_CSS"), "Bank Manager not logged in");

    }
}
