package testcases;

import base.BaseTest;
import extentlisteners.TestRetryAnalyzer;
import utilities.Constants;
import utilities.DataProviders;
import utilities.DataUtil;
import utilities.ExcelReader;

import org.testng.annotations.Test;

import java.util.Hashtable;

public class AddCustomerTest extends BaseTest {

    @Test(dataProviderClass = DataProviders.class, dataProvider = "bankManagerDP", retryAnalyzer = TestRetryAnalyzer.class)
    public void addCustomerTest(Hashtable<String, String> data) {

        //Adding comments
        ExcelReader excel = new ExcelReader(Constants.SUITE2_XL_PATH);
        DataUtil.checkExecution("BankManagerSuite", "AddCustomerTest", data.get("Runmode"), excel);

        browser = getBrowser(data.get("browser"));
        navigate(browser, Constants.URL);

        click("bankManagerBtn_CSS");
        click("startAddCustomerBtn_CSS");
        type("firstNameCustomer_CSS", data.get("firstname"));
        type("lastNameCustomer_CSS", data.get("lastname"));
        type("postCodeCustomer_CSS", data.get("postcode"));
        click("AddCustomerBtn_CSS");

    }
}
