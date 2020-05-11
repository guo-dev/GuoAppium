package com.guo.appium.testng;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.guo.appium.testcases.TestBase;

import io.qameta.allure.Allure;

public class TestListener implements ITestListener {
   
    
    @Override
    public void onFinish(ITestContext testContext) {
        // super.onFinish(testContext);

        // List of test results which we will delete later
        ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
        // collect all id's from passed test
        Set<Integer> passedTestIds = new HashSet<Integer>();
        for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
           
            System.out.println(passedTest.getEndMillis());
            passedTestIds.add(getId(passedTest));
        }

        Set<Integer> failedTestIds = new HashSet<Integer>();
        for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
            
            // id = class + method + dataprovider
            int failedTestId = getId(failedTest);

            // if we saw this test as a failed test before we mark as to be
            // deleted
            // or delete this failed test if there is at least one passed
            // version
            if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)) {
                //testsToBeRemoved.add(failedTest);
                System.out.println("delete ===="+failedTest.getName());
            } else {
                failedTestIds.add(failedTestId);
            }
        }
        Set<Integer> skipedTestIds=new HashSet<Integer>();
        
        for (ITestResult skipedTest : testContext.getSkippedTests().getAllResults()) {
           
            // id = class + method + dataprovider
            int skipedTestId = getId(skipedTest);

            // if we saw this test as a failed test before we mark as to be
            // deleted
            // or delete this failed test if there is at least one passed
            // version
            if (failedTestIds.contains(skipedTestId) || passedTestIds.contains(skipedTestId)) {
                testsToBeRemoved.add(skipedTest);
                //System.out.println("delete ===="+skipedTest.getName());
            } else {
            	skipedTestIds.add(skipedTestId);
            }
        }
        
        
        

        // finally delete all tests that are marked
//        for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator
//                .hasNext();) {
//            ITestResult testResult = iterator.next();
//            if (testsToBeRemoved.contains(testResult)) {
//                logger.info("Remove repeat Fail Test: " + testResult.getName());
//                System.out.println("Remove repeat Fail Test: " + testResult.getName());
//                iterator.remove();
//            }
//        }
   //      finally delete all tests that are marked
        for (Iterator<ITestResult> iterator = testContext.getSkippedTests().getAllResults().iterator(); iterator
                .hasNext();) {
            ITestResult testResult = iterator.next();
            if (testsToBeRemoved.contains(testResult)) {
               
                System.out.println("Remove repeat Fail Test: " + testResult.getName());
                iterator.remove();
            }
        }

    }

    private int getId(ITestResult result) {
        int id = result.getTestClass().getName().hashCode();
        id = id + result.getMethod().getMethodName().hashCode();
        id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
        return id;
    }

    public void onTestStart(ITestResult result) {
    	Reporter.log(result.getName() + " Started");
    }

    public void onTestSuccess(ITestResult result) {
    	Reporter.log(result.getName() + " Success");
    	//写入数据库，写入改测试用例执行成功的结果
    	
    }

    public void onTestFailure(ITestResult result) {
		Reporter.log(result.getName() + " Failure");
//        Retry retry = (Retry) result.getMethod().getRetryAnalyzer();
//        retry.resetRetrycount();
        
        
		// takeScreenShot(tr);
		TestBase tb = (TestBase) result.getInstance();
        String filename=result.getName();
        try {
			tb.getDriver().takeScreen("report/snapshot/", filename);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //这里是extentreport的失败截图
		Reporter.log("<a href=snapshot/" + filename + ".png target=_blank><img src=snapshot/" + filename
				+ ".png style=width:30px;height:30px img/>失败截图</a>", true);
		try {
			//这里是allure测试报告的失败截图
			Allure.addAttachment("失败截图", new FileInputStream(new File("report/snapshot/"+filename+".png")));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			Long date = System.currentTimeMillis();
//			System.out.println(date);
//			Reporter.log("<a href=http://localhost:8080/" + result.getName() + tb.getUdid().split(":")[0]
//					+ Thread.currentThread().getId() + "_" + date + ".png" + " target=_blank>失败截图</a>", true);
//			Reporter.log("<img src=http://localhost:8080/" + result.getName() + tb.getUdid().split(":")[0]
//					+ Thread.currentThread().getId() + "_" + date + ".png" + " style=width:30px;height:30px img/>",
//					true);
//			tb.getDriver().takeScreen("D:/apache-tomcat-8.5.4-windows-x64/apache-tomcat-8.5.4/webapps/ROOT/",
//					result.getName() + tb.getUdid().split(":")[0] + Thread.currentThread().getId() + "_" + date);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    public void onTestSkipped(ITestResult result) {
    	Reporter.log(result.getName() + " Skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onStart(ITestContext context) {
    	
    }
}