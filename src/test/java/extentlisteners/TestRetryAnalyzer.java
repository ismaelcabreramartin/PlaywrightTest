package extentlisteners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class TestRetryAnalyzer implements IRetryAnalyzer {

    int initCount = 1;
    int maxRetryCount = 3;

    @Override
    public boolean retry(ITestResult result) {
        if(initCount < maxRetryCount) {

            initCount++;
            return true;
        }
        return false;
    }
}
