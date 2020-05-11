package com.guo.appium.driver;

import com.guo.appium.utils.ProUtil;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.util.HashMap;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.guo.appium.server.Port;

public class CrazyCapabilities {
	/**
	 * 通过指定paltform来获取不同平台的caps参数
	 * 
	 * @param paltform
	 *            must is android or ios
	 * @return DesiredCapabilities
	 * @throws Exception
	 */
	public static DesiredCapabilities getCaps(String paltform, String udid) {
		if (MobilePlatform.ANDROID.equalsIgnoreCase(paltform)) {
			return getAndroidCaps(udid);
		} else if (MobilePlatform.IOS.equalsIgnoreCase(paltform)) {
			return getIosCaps(udid);
		} else {
			throw new RuntimeException("paltform is error, platform must is android or ios");
		}
	}

	/**
	 * 从配置文件获取ios的caps参数
	 * 
	 * @return DesiredCapabilities
	 * @throws Exception
	 */
	public static DesiredCapabilities getIosCaps(String udid) {
		ProUtil properties = new ProUtil(CrazyPath.iosCapsPath);
		HashMap<String, String> keyValues = properties.getAllKeyValue();
		DesiredCapabilities caps = new DesiredCapabilities(keyValues);
		caps.setCapability(MobileCapabilityType.UDID, udid);
		return caps;
	}

	/**
	 * 从配置文件获取android的caps参数
	 * 
	 * @return DesiredCapabilities
	 * @throws Exception
	 */
	public static DesiredCapabilities getAndroidCaps(String udid) {
		ProUtil properties = new ProUtil(CrazyPath.androidCapsPath);
		HashMap<String, String> keyValues = properties.getAllKeyValue();
		DesiredCapabilities caps = new DesiredCapabilities(keyValues);
		caps.setCapability(MobileCapabilityType.UDID, udid);
		caps.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Port.randomPort());
		return caps;
	}
}
