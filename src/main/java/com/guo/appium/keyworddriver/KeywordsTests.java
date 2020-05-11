package com.guo.appium.keyworddriver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.guo.appium.testcases.TestBase;

public class KeywordsTests extends TestBase {
	
	public static boolean testResult;

	@DataProvider
	public Object[][] getKeywordData() {
		// 在这里获取所有的测试用例名称
		ExcelUtil util = new ExcelUtil("configs/DONGKEYWORD.xlsx");
		Object[][] keywordData = util.getKeywordData("测试用例集合");
		return keywordData;
	}

	@Test(dataProvider = "getKeywordData")
	public void test(String casenum, String casename, String flag) throws Exception, Exception, InvocationTargetException {
		// 根据读取到的测试用例名称，去读对应的sheet工作表里所有的步骤并执行
//		System.out.println(casename);
		testResult=true;
		KeyWordsAction keyWordsAction = new KeyWordsAction(driver);
		Method[] method = keyWordsAction.getClass().getMethods();
		ExcelUtil util = new ExcelUtil("configs/DONGKEYWORD.xlsx");
		Object[][] caseData = util.getCaseData(casename);
		for (int i = 0; i < caseData.length; i++) {
			String keyword = caseData[i][0].toString();
			String locator = caseData[i][1].toString();
			String value = caseData[i][2].toString();
			for (Method m : method) {
				//System.out.println(m.getName());
				if(keyword.equalsIgnoreCase(m.getName())) {
					System.out.println("执行【"+keyword+"】操作");
					m.invoke(keyWordsAction, locator,value);//执行该方法
				}
			}
			if(!testResult) {
				util.setCellData(casename, i+1, 4, "测试步骤执行失败", testResult);
				util.setCellData("测试用例集合", Integer.valueOf(casenum), 3, "测试用例执行失败", testResult);
				Assert.fail(casename+"执行失败");
				break;
			}else {
				util.setCellData(casename, i+1, 4, "测试步骤执行成功", testResult);
			}
		}
		if(testResult) {
			util.setCellData("测试用例集合", Integer.valueOf(casenum), 3, "测试用例执行成功", testResult);
		}
	}

}
