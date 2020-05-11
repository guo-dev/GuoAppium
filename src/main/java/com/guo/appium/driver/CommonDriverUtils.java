package com.guo.appium.driver;

import java.util.List;

import com.guo.appium.utils.Log;

import io.appium.java_client.MobileElement;

public class CommonDriverUtils {
	private static Log logger = Log.getLogger(CommonDriverUtils.class);

	/**
	 * 判断两个字符串集合list是否元素对应相等
	 * 
	 * @param strSrc
	 * @param strDes
	 * @return
	 */
	public static Boolean listStrEquals(List<String> strSrc, List<String> strDes) {
		Boolean flag = false;
		if ((!strSrc.isEmpty() && strSrc != null) && (!strDes.isEmpty() && strDes != null)) {
			if (strSrc.size() == strDes.size()) {
				for (int i = 0; i < strDes.size(); i++) {
					if (strSrc.get(i).equals(strDes.get(i))) {
						flag = true;
						continue;
					} else {
						logger.debug(strSrc.get(i) + "," + strDes.get(i) + " is not same" + " the " + i + " ");
						flag = false;
						break;
					}
				}
			} else {
				logger.debug("two list size not same");
			}
		} else {
			logger.debug("two list size is 0 or null");
		}
		return flag;
	}

	// 输入内容直到正确,15011123456 150 1112 3456
	public static void sendkeysUntilCorrect(MobileElement element, String str) {
		if (element != null) {
			boolean flag = true;
			element.sendKeys(str);
			while (flag) {
				if (str.equals(element.getText())) {
					flag = false;
				} else {
					element.sendKeys(str);
				}
			}
		} else {
			logger.debug("element is null,not sendkeys");
			throw new RuntimeException("element is null,not sendkeys");
		}
	}
}
