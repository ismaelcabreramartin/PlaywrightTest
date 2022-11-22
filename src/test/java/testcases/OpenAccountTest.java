package testcases;

import base.BaseTest;
import extentlisteners.TestRetryAnalyzer;
import org.testng.annotations.Test;
import utilities.Constants;
import utilities.DataProviders;
import utilities.DataUtil;
import utilities.ExcelReader;

import java.util.Hashtable;

public class OpenAccountTest extends BaseTest {

    @Test(dataProviderClass = DataProviders.class, dataProvider = "bankManagerDP", retryAnalyzer = TestRetryAnalyzer.class)
    public void openAccountTest(Hashtable<String, String> data) {

        ExcelReader excel = new ExcelReader(Constants.SUITE2_XL_PATH);
        DataUtil.checkExecution("BankManagerSuite", "OpenAccountTest", data.get("Runmode"), excel);

        browser = getBrowser(data.get("browser"));
        navigate(browser, Constants.URL);

        click("bankManagerBtn_CSS");
        click("startOpenAccountBtn_CSS");
        select("customerSelect_CSS", data.get("customer"));
        select("currencySelect_CSS", data.get("currency"));
        click("OpenAccountBtn_CSS");

    }

}
