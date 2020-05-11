package com.guo.appium.keyworddriver;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.guo.appium.testcases.TestBase;

import java.lang.reflect.Method;

public class TestSuiteByExcel extends TestBase{
    public  Method method[];
	public  String keyword;
	public  String locatorExpression;
	public  String value;
	public  KeyWordsAction keyWordsaction;
	public  int testStep;
	public  int testLastStep;
	public  String testCaseID;
	public  String testCaseRunFlag;
	public static  boolean testResult;

	@Test
	public void testTestSuite() throws Exception {

		// 声明一个关键词动作类的实例
		keyWordsaction = new KeyWordsAction(driver);
		// 使用 java 的反射机制获取 KeyWordsaction 类的所有方法对象
		method = keyWordsaction.getClass().getMethods();
		// 定义 excel 关键文件的路径
		String excelFilePath = Constants.Path_ExcelFile;
		// 设定读取 excel 
		ExcelUtil excel=new ExcelUtil(excelFilePath);
		// 读取“测试用例集合” sheet 中的测试用例总数
		int testCasesCount = excel.getRowCount(Constants.Sheet_TestSuite);
		// 使用 for 循环，执行所有标记为“y”的测试用例
		for (int testCaseNo = 1; testCaseNo <= testCasesCount; testCaseNo++) {
			// 读取“测试用例集合” sheet 中每行的测试用例序号
			testCaseID = excel.getCellData(Constants.Sheet_TestSuite,
			testCaseNo, Constants.Col_TestCaseID);
			System.out.println(testCaseID);
			// 读取“测试用例集合” sheet 中每行的是否运行列中的值
			testCaseRunFlag = excel.getCellData(Constants.Sheet_TestSuite,
			testCaseNo, Constants.Col_RunFlag);
			// 如果是否运行列中的值为“y”,则执行测试用例中的所有步骤
			if (testCaseRunFlag.equalsIgnoreCase("y")) {

				// 设定测试用例的当前结果为true，即表明测试执行成功
				testResult = true;
				// 在“DONG”sheet 中，获取当前要执行测试用例的第一个步骤所在行行号
				testStep = excel.getFirstRowContainsTestCaseID(
						Constants.Sheet_TestSteps, testCaseID);
				//在“DONG”sheet 中，获取当前要执行测试用例的最后一个步骤所在行行号
				testLastStep = excel.getTestCaseLastStepRow(
						Constants.Sheet_TestSteps, testCaseID, testStep);
				// 遍历测试用例中的所有测试步骤
				for (; testStep <=testLastStep; testStep++) {
			// 从“DONG” sheet 中读取关键字和操作值，并调用 execute_Actions 方法执行
					keyword = excel.getCellData(Constants.Sheet_TestSteps,
					testStep, Constants.Col_KeyWordAction);
					// 在日志文件中打印关键字信息
					System.out.println("从excel 文件读取到的关键字是：" + keyword);
					locatorExpression=excel.getCellData(Constants.Sheet_TestSteps,testStep,Constants.Col_LocatorExpression);

					value = excel.getCellData(Constants.Sheet_TestSteps,
					testStep, Constants.Col_ActionValue);
					// 在日志文件中打印操作值信息
					System.out.println("从 excel 文件中读取的操作值是：" + value);
					execute_Actions(excel);
					if (testResult == false) {
						/*
						 * 如果测试用例的任何一个测试步骤执行失败，则测试用例集合 sheet 
                             * 中的当前执行测试用例的执行结果设定为“测试执行失败”
						 */
						excel.setCellData(Constants.Sheet_TestSuite, testCaseNo,
						Constants.Col_TestSuiteTestResult, "测试执行失败",testResult);
						// 调用测试方法过程中，若出现异常，则将测试设定为失败状态，停止测试用例执行
						Assert.fail("执行出现异常，测试用例执行失败！");

						/*
						 * 当前测试用例出现执行失败的步骤，则整个测试用例设定为失败状态，
						 *  break语句跳出当前的 for 循环，继续执行测试集合中的下一个测试
                             * 用例
						 */
						break;
					}

				}

				if (testResult == true) {
					/*
					 * 如果测试用例的所有步骤执行成功，则会在测试用例集合 sheet 中的当						 * 前执行测试用例
					 * 的执行结果设定为“测试执行成功”
					 */
					excel.setCellData(Constants.Sheet_TestSuite, testCaseNo,
					Constants.Col_TestSuiteTestResult, "测试执行成功",testResult);
				}

			}
		}
	}

	private  void execute_Actions(ExcelUtil excel) {
		try {

			for (int i = 0; i < method.length; i++) {
				/*
				 * 使用反射的方式，找到关键字对应的测试方法，并使用 value （操作值） 作为测          
                  	 * 试方法的函数值进行调用
			  	 */

				if (method[i].getName().equals(keyword)) {
					method[i].invoke(keyWordsaction, locatorExpression,value);
					if (testResult == true) {
						/* 当前测试步骤执行成功，在 “知乎”sheet 中，会将当前执行的测
                             *  试步骤结果设定为“测试步骤执行成功”
					      */
						excel.setCellData(Constants.Sheet_TestSteps,
								testStep, Constants.Col_TestStepTestResult,"测试步骤执行成功",testResult);
						break;
					} else {
						/* 当前测试步骤执行失败，在 “知乎”sheet 中，会将当前执行的测
                           *  试步骤结果设定为“测试步骤执行失败”
						 */
						excel.setCellData(Constants.Sheet_TestSteps,
								testStep, Constants.Col_TestStepTestResult,"测试步骤执行失败",testResult);

						break;
					}
					
				}
			}
		} catch (Exception e) {

		}
	}
//
//	@BeforeClass
//	@Parameters({ "udid", "port" })
//	public void BeforeClass(String udid,String port) throws Exception {
//		//System.out.println("连接"+udid+"端口"+port);
//		//CaseBaseTest cb=new CaseBaseTest();
//		ProUtil p=new ProUtil(CrazyPath.globalPath);
//		String server=p.getPro("server");
//		//String port="4490";
//		String capsPath=CrazyPath.capsPath;
//		//String udid="127.0.0.1:62001";
//		String input="com.example.android.softkeyboard/.SoftKeyboard";
//		keyWordsaction.driver=new CrazyMobileDriver(server, port, capsPath, udid, input);
//		keyWordsaction.driver.implicitlyWait(10);
//		
//	}
//	@AfterClass
//	public void afterClass(){
//		keyWordsaction.quit();
//	}
}
