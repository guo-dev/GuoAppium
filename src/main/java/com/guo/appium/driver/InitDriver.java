package com.guo.appium.driver;

import java.net.URL;

import com.guo.appium.exception.PlatformException;
import com.guo.appium.utils.Log;
import com.guo.appium.utils.ProUtil;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobilePlatform;

public class InitDriver {
	private Log logger = Log.getLogger(InitDriver.class);
	private String serverURL;
	ProUtil global;
	String udid;

	public InitDriver(String udid) {
		this.global = new ProUtil(CrazyPath.globalPath);
		this.udid = udid;
	}

	/**
	 * 
	 * @param platform
	 * @return
	 * @throws Exception
	 */
	public AppiumDriver getDriver(String platform, String port) {
		try {
			if (MobilePlatform.ANDROID.equalsIgnoreCase(platform)) {
				return initAndroidDriver(CrazyCapabilities.getAndroidCaps(udid), port);
			} else if (MobilePlatform.IOS.equalsIgnoreCase(platform)) {
				return initIosDriver(CrazyCapabilities.getIosCaps(udid), port);
			} else {
				logger.error("platform is error, platform must is android or ios");
				throw new PlatformException("platform is error, platform must is android or ios");
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}

	}

	/**
	 * 
	 * @param caps
	 * @return
	 * @throws Exception
	 */
	public AppiumDriver<AndroidElement> initAndroidDriver(DesiredCapabilities caps, String port) throws Exception {
		AppiumDriver driver = new AndroidDriver<AndroidElement>(
				new URL(global.getKey("androidserver") + ":" + port + "/wd/hub"), caps);
		return driver;
	}

	/**
	 * 
	 * @param caps
	 * @return
	 * @throws Exception
	 */
	public AppiumDriver<IOSElement> initIosDriver(DesiredCapabilities caps, String port) throws Exception {
		System.out.println(global.getKey("iosserver") + ":" + port + "/wd/hub");
		AppiumDriver driver = new IOSDriver<IOSElement>(new URL(global.getKey("iosserver") + ":" + port + "/wd/hub"),
				caps);
		return driver;
	}
}
