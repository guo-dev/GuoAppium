package com.guo.appium.driver;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.guo.appium.exception.DirectionException;
import com.guo.appium.exception.GestureOperationException;
import com.guo.appium.exception.PlatformException;
import com.guo.appium.utils.CommandLineExec;
import com.guo.appium.utils.Log;
import com.guo.appium.utils.ProUtil;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobilePlatform;
import io.appium.java_client.touch.LongPressOptions;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.touch.offset.PointOption;

public class CrazyMobileDriver {

	private Log logger = Log.getLogger(CrazyMobileDriver.class);
	public AppiumDriver driver;
	private String platform;
	private SwipeScreenOrElement swipe;

	/**
	 * 通过不同的platform来初始化不同的driver
	 * 
	 * @param platform
	 * @throws Exception
	 */
	public CrazyMobileDriver(String platform, String udid, String port) {
		this.platform = platform;

		InitDriver init = new InitDriver(udid);
		this.driver = init.getDriver(platform, port);
		this.swipe = new SwipeScreenOrElement(driver);
	}

	/**
	 * 获取app应用占用的屏幕宽度
	 * 
	 * @return 返回宽度
	 */
	public int appScreenWidth() {
		int width = driver.manage().window().getSize().getWidth();
		// System.out.println("获取app应用占用的屏幕宽度为->"+width);
		logger.info("Get the width of the app occupation screen->" + width);
		return width;
	}

	/**
	 * 获取app应用占用的屏幕高度
	 * 
	 * @return 返回高度
	 */
	public int appScreenHeight() {
		int height = driver.manage().window().getSize().getHeight();
		// System.out.println("获取app应用占用的屏幕宽度为->"+height);
		logger.info("Get the height of the app occupation screen ->" + height);
		return height;
	}
	/**
	 * 获取元素的结束点坐标对象
	 * @param element
	 * @return
	 */
	public Point getElementEndCor(MobileElement element){
		Point start=element.getLocation();
		int startX=start.getX();//起始点x
		int startY=start.getY();//起始点y
		
//		element.getCenter().getX();
//		element.getCenter().getY();
		Dimension size=element.getSize();
		int width=size.getWidth();
		int height=size.getHeight();
		
		
		int endX=startX+width;
		int endY=startY+height;
		
		Point end=new Point(endX, endY);
		return end;
	}

	/**
	 * 隐式等待
	 * 
	 * @param timeout 单位是秒
	 */
	public void implicitlyWait(int timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		//AppiumFieldDecorator.DEFAULT_WAITING_TIMEOUT.minus(Duration.ofSeconds(timeout));
	}

	public void implicitlyWaitDefault() {
		ProUtil p;
		try {
			p = new ProUtil(CrazyPath.globalPath);
			driver.manage().timeouts().implicitlyWait(Integer.valueOf(p.getKey("implicitlyWait")), TimeUnit.SECONDS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("implicitlyWaitDefault failure" + e.getMessage());
			throw new RuntimeException("implicitlyWaitDefault failure" + e.getMessage());
		}
	}

	public void implicitlyWaitZero() {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}
	
	public void resetApp(){
		driver.resetApp();
	}

	/**
	 * 判断元素是否存在
	 * 
	 * @param by
	 *            By.id("xxxx") By.xpath("xxx") NoSuchElementException
	 * @return 存在则返回true，不存在则返回false
	 */
	public boolean isElementExist(By by) {
		this.implicitlyWaitZero();
		try {
			driver.findElement(by).isDisplayed();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			logger.debug("element not found-->" + e.getMessage());
			return false;
		} finally {
			this.implicitlyWaitDefault();
		}
	}

	/**
	 * 在指定超时时间内元素是否存在，如存在则立即返回结果，如不存在则在超时后返回结果
	 * 
	 * @param by
	 *            定位对象
	 * @param timeout
	 *            超时时间
	 * @return 指定时间内任意时间该元素出现则停止等待返回true，指定时间内没出现则返回false
	 */
	public boolean isElementExist(By by, int timeout) {
		try {
			this.implicitlyWaitZero();
			new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(by));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			logger.debug("element not found-->" + e.getMessage());
			// this.implicitlyWaitDefault();
			return false;
		} finally {
			this.implicitlyWaitDefault();
		}
	}
	/**
	 * toast是否存在
	 * @param toast
	 * @return
	 */
	public boolean isToast(String toast){
		try {
			new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@text,"+toast+")]")));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	public boolean isToast(String toast,int timeout){
		try {
			new WebDriverWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text,"+toast+")]")));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	/**
	 * 判断元素不存在
	 * 
	 * @param by
	 *            By.id("xxxx") By.xpath("xxx") NoSuchElementException
	 * @return 存在则返回false，不存在则返回true
	 */
	public boolean isElementNotExist(By by) {
		return !this.isElementExist(by);
	}
	/**
	 * 在指定超时时间内元素不存在，如存在则立即返回结果，如不存在则在超时后返回结果
	 * 
	 * @param by
	 *            定位对象
	 * @param timeout
	 *            超时时间
	 * @return 指定时间内任意时间该元素出现则停止等待返回true，指定时间内没出现则返回false
	 */
	public boolean isElementNotExist(By by, int timeout) {
		return !this.isElementExist(by,timeout);
	}
	/**
	 * 启动某个应用
	 * @param appPackage
	 * @param appActivity
	 * @throws Exception 
	 */
	public void startActivity(String appPackage,String appActivity) throws Exception{
		if(platform.equalsIgnoreCase("android")){
			Activity activity=new Activity(appPackage, appActivity);
			((AndroidDriver) driver).startActivity(activity);
		}else{
			logger.debug("platform is "+platform+" not support startactivty");
			throw new Exception("platform is "+platform+" not support startactivty");
		}
	}
	public void startActivity(String appPackage,String appActivity,String appWaitActivity) throws Exception{
		if(platform.equalsIgnoreCase("android")){
			Activity activity=new Activity(appPackage, appActivity);
			activity.setAppWaitActivity(appWaitActivity);
			((AndroidDriver) driver).startActivity(activity);
		}else{
			logger.debug("platform is "+platform+" not support startactivty");
			throw new Exception("platform is "+platform+" not support startactivty");
		}
	}
	/**
	 * 获取服务端caps参数
	 * @return
	 */
	public Capabilities getCaps(){
		return driver.getCapabilities();
	}

	

	/**
	 * 查找元素,存在则返回该元素对象，不存在则报错
	 * 
	 * @param by
	 * @return
	 */
	public MobileElement findElement(By by) {
		//AndroidElement
		//IOSElement
		try {
			MobileElement element = (MobileElement) driver.findElement(by);
			logger.info("element found-->");
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("element not found-->" + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}


	/**
	 * 在指定超时时间内查找元素是否存在，如存在则立即返回MobileElement对象，如不存在则在超时后返回null
	 * 
	 * @param by
	 * @param timeout
	 *            单位是秒
	 * @return
	 */
	public MobileElement findElement(final By by, int timeout) {
		try {
			MobileElement element = new WebDriverWait(driver, timeout).until(new ExpectedCondition<MobileElement>() {
				@Override
				public MobileElement apply(WebDriver driver) {
					// TODO Auto-generated method stub
					return (MobileElement) driver.findElement(by);
				}
			});
			return element;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("element not found-->" + e.getMessage());
			return null;
		}
	}
	
	public List<MobileElement> findElements(By by) {
		try {
			List<MobileElement> elements = driver.findElements(by);
			logger.info("elements  found-->");
			return elements;
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("elements not found-->" + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 滑动屏幕方法，通过参数实现各方向滑动
	 * 
	 * @param direction
	 *            方向参数，值为"up"、"down"、"right"、"left"
	 * @param duration
	 *            滑动时间，单位毫秒
	 * @throws Exception
	 */
	public void swipe(String direction, int duration) {
		swipe.swipe(direction, duration);

	}
	
	public void swipe(int startx,int starty,int endx,int endy, int duration) {
		sleep(1500);
		Duration dur=Duration.ofMillis(duration);
		swipe.swipe(startx, starty, endx, endy, dur);

	}

	/**
	 * 在元素上滑动
	 * 
	 * @param element
	 *            元素对象
	 * @param derction
	 *            方向参数，值为"up"、"down"、"right"、"left"
	 * @param duration
	 * @throws DirectionException
	 */

	@SuppressWarnings("deprecation")
	public void swipeOnElement(MobileElement element, String derction, int duration) {
		String derc = derction.toLowerCase();
		switch (derc) {
		case "up":
			swipe.swipeOnElement(element,"UP", 10, 10, duration);
			break;
		case "down":
			swipe.swipeOnElement(element,"DOWN", 10, 10, duration);
			break;
		case "left":
			swipe.swipeOnElement(element,"LEFT", 10, 10, duration);
			break;
		case "right":
			swipe.swipeOnElement(element,"RIGHT", 10, 10, duration);
			break;
		default:
			logger.error("direction of swipe,direction must is up or down or left or right");
			throw new DirectionException("direction of swipe,direction must is up or down or left or right");
		}
	}

	public void swipeOnElement(By by, String derction, int duration) {
		MobileElement element = this.findElement(by);
		this.swipeOnElement(element, derction, duration);
	}

	/**
	 * 向某方向滑动直到某元素出现
	 * 
	 * @param by
	 *            查找对象
	 * @param direction
	 *            方向
	 * @param duration
	 *            每次滑动时间，单位毫秒
	 * @param findCount
	 *            查找次数
	 * @throws Exception
	 */
	public boolean swipeUntilElementAppear(By by, String direction, int duration, int findCount) throws Exception {
		this.implicitlyWaitZero();
		Boolean flag = false;
		while (findCount > 0) {
			try {
				this.findElement(by).isDisplayed();
				flag = true;
				break;
			} catch (Exception e) {
				// TODO: handle exception
				logger.debug("element not found with swipe " + findCount + " times " + e.getMessage());
				flag = false;
				this.swipe(direction, duration);
				findCount--;
			}
		}
		this.implicitlyWaitDefault();
		return flag;
	}

	public MobileElement swipeUntilElement(By by, String direction, int duration, int findCount) {
		// this.implicitlyWait(3);
		MobileElement element = null;
		boolean flag = false;
		while (findCount > 0) {
			try {
				element = this.findElement(by);
				flag = true;
			} catch (Exception e) {
				// TODO: handle exception
				logger.debug("element not found with swipe " + findCount + " times " + e.getMessage());
				flag = false;
				this.swipe(direction, duration);
				findCount--;
			}
		}
		return element;
	}

	/**
	 * 与服务端断开连接
	 */
	public void quit() {
		driver.quit();
	}

	/**
	 * touchaction的方式点击元素
	 * 
	 * @param element
	 * @throws GestureOperationException
	 */
	public void tap(MobileElement element) throws GestureOperationException {
		try {
			TouchAction ta = new TouchAction(driver);
			ta.tap(new TapOptions().withElement(ElementOption.element(element))).release().perform();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("tap error ->" + e.getMessage());
			throw new GestureOperationException("tap error ->" + e.getMessage());
		}
	}

	/**
	 * 点击某个坐标点
	 * 
	 * @param x
	 * @param y
	 * @throws GestureOperationException
	 */
	public void clickByCoordinate(int x, int y) {
		try {
			sleep(1000);
			TouchAction ta = new TouchAction(driver);
			ta.tap(new TapOptions().withPosition(new PointOption<>().withCoordinates(x, y))).release().perform();
			logger.info("clickByCoordinate Successed on " + x + "," + y);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("clickByCoordinate error ->" + e.getMessage());
			throw new GestureOperationException("clickByCoordinate error ->" + e.getMessage());
		}
	}

	/**
	 * 元素长按
	 * 
	 * @param by
	 * @throws GestureOperationException
	 */
	public void longPress(By by) {
		MobileElement element = this.findElement(by);
		this.longPress(element);
	}

	/**
	 * 元素长按
	 * 
	 * @param by
	 * @throws GestureOperationException
	 */
	public void longPress(MobileElement element) {
		try {
			TouchAction ta = new TouchAction(driver);
			ta.longPress(new LongPressOptions().withElement(ElementOption.element(element))).release().perform();
			logger.info("longPress Successed ");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("longPress error ->" + e.getMessage());
			throw new GestureOperationException("longPress error ->" + e.getMessage());
		}
	}

	/**
	 * 长按某元素，指定长按时间
	 * 
	 * @param element
	 * @param duration
	 * @throws GestureOperationException
	 */
	public void longPress(MobileElement element, int duration) {
		try {
			// AndroidElement element=(AndroidElement) super.findElement(by);
			TouchAction ta = new TouchAction(driver);
			Duration dur=Duration.ofMillis(duration);
			ta.longPress(new LongPressOptions().withElement(ElementOption.element(element)).withDuration(dur)).release().perform();
			logger.info("longPress Successed ");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("longPress error ->" + e.getMessage());
			throw new GestureOperationException("longPress error ->" + e.getMessage());
		}
	}

	/**
	 * 在某一个坐标点上长按
	 * 
	 * @param x
	 * @param y
	 * @throws GestureOperationException
	 */
	public void longPress(int x, int y) {
		try {
			TouchAction ta = new TouchAction(driver);
			ta.longPress(new LongPressOptions().withPosition(new PointOption<>().point(x, y))).release().perform();
			logger.info("longPress Successed on " + x + "," + y);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("longPress error ->" + e.getMessage());
			throw new GestureOperationException("longPress error ->" + e.getMessage());
		}
	}

	/**
	 * 长按坐标点，指定长按时间
	 * 
	 * @param x
	 * @param y
	 * @param duration
	 * @throws GestureOperationException
	 */
	public void longPress(int x, int y, int duration) {
		try {
			TouchAction ta = new TouchAction(driver);
			Duration dur=Duration.ofMillis(duration);
			ta.longPress(new LongPressOptions().withPosition(new PointOption<>().point(x, y)).withDuration(dur)).release().perform();
			logger.info("longPress Successed on " + x + "," + y);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("longPress error ->" + e.getMessage());
			throw new GestureOperationException("longPress error ->" + e.getMessage());
		}
	}

	public String getPageSource() {
		return driver.getPageSource();
	}


	/**
	 * 某方向滑动直到边界，如底部，顶部(在没有边界标识的时候使用)
	 * 
	 * @param direction
	 * @param strSrc
	 * @param strDes
	 * @return 实现滑动前后的字符串集合list的对比
	 * @throws Exception
	 */
	public void swipeUntilBoundary(String direction, By by) {
		boolean flag = false;
		while (!flag) {
			List<String> strSrc = new ArrayList<String>();
			List<String> strDes = new ArrayList<String>();
			// 滑动前定位元素并将元素的text添加到集合strSrc里
			List<MobileElement> elementOld = driver.findElements(by);
			for (MobileElement ae : elementOld) {
				strSrc.add(ae.getText());
			}
			// 这里可以增加对每个元素的操作
			this.swipe(direction, 500);
			this.sleep(1000);
			// 滑动后定位元素并将元素的text添加到集合strSrc里
			List<MobileElement> elementNew = driver.findElements(by);
			for (MobileElement ae : elementNew) {
				strDes.add(ae.getText());
			}
			flag = CommonDriverUtils.listStrEquals(strSrc, strDes);
		}
	}

	/**
	 * 等待，死等
	 * 
	 * @param milliSecond
	 */
	public void sleep(int milliSecond) {
		try {
			Thread.sleep(milliSecond);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e.getMessage());
		}
	}

	/**
	 * 设备返回键操作，仅支持android
	 * 
	 * @throws PlatformException
	 */
	public void pressBack() {
		if (MobilePlatform.ANDROID.equalsIgnoreCase(platform)) {
			this.sleep(500);
			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
		} else {
			logger.error("platform is " + platform + ",so not support pressBack method");
			throw new PlatformException("platform is " + platform + ",so not support pressBack method");
		}
	}

	/**
	 * 多次返回，仅支持android
	 * 
	 * @param number
	 * @throws RuntimeException
	 */
	public void pressBack(int number) {
		if (number > 0) {
			for (int i = 0; i < number; i++) {
				this.pressBack();
				logger.debug("excute the " + i + " time pressBack");
			}
		} else {
			logger.error("press back counts is error " + number);
			throw new RuntimeException("press back counts is error " + number);
		}
	}
	/**
	 * 清除文本框的内容，一个一个清除，仅支持android
	 * @param element
	 */
	public void clearOneByOne(MobileElement element){
		element.click();
		int length=element.getText().length();
		((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.MOVE_END));
		pressBackspace(length);
	}

	/**
	 * 设备home键操作，仅支持android
	 * 
	 * @throws PlatformException
	 */
	public void pressHome() {
		if (MobilePlatform.ANDROID.equalsIgnoreCase(platform)) {
			this.sleep(500);
			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.HOME));
		} else {
			logger.error("platform is " + platform + ",so not support pressBack method");
			throw new PlatformException("platform is " + platform + ",so not support pressHome method");
		}
	}

	/**
	 * 设备回车键操作，仅支持android
	 * 
	 * @throws PlatformException
	 */
	public void pressEnter() {
		if (MobilePlatform.ANDROID.equalsIgnoreCase(platform)) {
			this.sleep(500);
			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
		} else {
			logger.error("platform is " + platform + ",so not support pressBack method");
			throw new PlatformException("platform is " + platform + ",so not support pressEnter method");
		}
	}

	/**
	 * 手机键盘删除操作，仅支持android
	 * 
	 * @throws PlatformException
	 */
	public void pressBackspace() {
		if (MobilePlatform.ANDROID.equalsIgnoreCase(platform)) {
			this.sleep(200);
			((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.DEL));
		} else {
			logger.error("platform is " + platform + ",so not support pressBack method");
			throw new PlatformException("platform is " + platform + ",so not support pressBackspace method");
		}
	}

	/**
	 * 多次手机键盘删除操作，仅支持android
	 * 
	 * @throws PlatformException
	 */
	public void pressBackspace(int numbers) {
		if (numbers > 0) {
			for (int i = 0; i < numbers; i++) {
				this.pressBackspace();
				logger.debug("excute the " + i + " time pressBackspace");
			}
		} else {

			logger.debug("paramters must is > 0");
			throw new RuntimeException("pressBackspace counts is error " + numbers);
		}
	}

	/**
	 * 唤醒屏幕，仅支持android
	 * 
	 * @throws PlatformException
	 */
	public void wakeUp() {
		boolean flag;
		if (MobilePlatform.ANDROID.equalsIgnoreCase(platform)) {
			flag = ((AndroidDriver)driver).isDeviceLocked();
			if (flag) {
				((AndroidDriver)driver).unlockDevice();
				logger.info("unlockDevice complete");
			}
		} else {
			logger.error("platform is " + platform + ",so not support pressBack method");
			throw new PlatformException("platform is " + platform + ",so not support wakeUp method");
		}

	}

	// 
	/**
	 * 逐个输入数字，模拟的是键盘数字输入，15610112934，仅支持android
	 * @param text
	 */
	public void sendKeysOneByOne(String text) {
		if (MobilePlatform.ANDROID.equalsIgnoreCase(platform)) {
			char[] chr = text.toCharArray();// {1,5,6,1,x,x,x,x}
			for (int i = 0; i < chr.length; i++) {
				int c = Integer.valueOf(String.valueOf(chr[i]));
				AndroidKey number;
				switch (c) {
				case 0:
					// driver.pressKeyCode(AndroidKeyCode.KEYCODE_0);
					number = AndroidKey.DIGIT_0;
					break;
				case 1:
					number = AndroidKey.DIGIT_1;
					break;
				case 2:
					number = AndroidKey.DIGIT_2;
					break;
				case 3:
					number = AndroidKey.DIGIT_3;
					break;
				case 4:
					number = AndroidKey.DIGIT_4;
					break;
				case 5:
					number = AndroidKey.DIGIT_5;
					break;
				case 6:
					number = AndroidKey.DIGIT_6;
					break;
				case 7:
					number = AndroidKey.DIGIT_7;
					break;
				case 8:
					number = AndroidKey.DIGIT_8;
					break;
				case 9:
					number = AndroidKey.DIGIT_9;
					break;
				default:
					throw new RuntimeException("mobile number param is error-->" + c);
				}
				((AndroidDriver)driver).pressKey(new KeyEvent(number));// driver.pressKeyCode(16)=9;
			}
		} else {
			logger.error("platform is " + platform + ",so not support pressBack method");
			throw new PlatformException("platform is " + platform + ",so not support wakeUp method");
		}

	}
	/**
	 * 全屏截图，只需要传入存放路径和文件名称即可，不需要传文件后缀
	 * @param path
	 * @param fileName
	 * @throws Exception
	 */
	public void takeScreen(String path, String fileName) throws Exception {
		File srcFile = driver.getScreenshotAs(OutputType.FILE);
		
		FileUtils.copyFile(srcFile, new File(path + "/" + fileName + ".png"));
		logger.info("takeScreen on " + path + "/" + fileName + ".png");
	}
	/**
	 * 该方法已废弃，早期appium不支持单独元素截图时所使用的
	 * @param element
	 * @param path
	 * @param fileName
	 * @throws Exception
	 */
	@Deprecated
	public void takeScreenForElement111(MobileElement element, String path, String fileName) throws Exception {
		// 获得element的位置和大小
		Point location = element.getLocation();
		Dimension size = element.getSize();
		byte[] imageByte = driver.getScreenshotAs(OutputType.BYTES);
		// 创建全屏截图
		BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(imageByte));
		// 截取element所在位置的子图。
		BufferedImage croppedImage = originalImage.getSubimage(location.getX(), location.getY(), size.getWidth(),
				size.getHeight());
		ImageIO.write(croppedImage, "png", new File(path + fileName + ".png"));

	}
	

	/**
	 * 针对单独某个元素进行截图
	 * @param by
	 * @param path
	 * @param fileName
	 * @throws Exception
	 */
	public void takeScreenForElement(By by, String path, String fileName) throws Exception {
		MobileElement element = this.findElement(by);
		//this.takeScreenForElement(element, path, fileName);
		File srcFile = element.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(path + "/" + fileName + ".png"));
		logger.info("takeScreen on Element "  + "/" + fileName + ".png");
	}
	/**
	 * 针对单独某个元素进行截图
	 * @param by
	 * @param path
	 * @param fileName
	 * @throws Exception
	 */
	public void takeScreenForElement(MobileElement element, String path, String fileName) throws Exception {
		//this.takeScreenForElement(element, path, fileName);
		File srcFile = element.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcFile, new File(path + "/" + fileName + ".png"));
		logger.info("takeSreen on Element "  + "/" + fileName + ".png");
	}
	
	
	/**
	 * 针对有一些元素无法精确定位，但是能够定位到整体大框元素，而这个大框拆分成小框又是规律的，比如3*3,2*4,4*3等等
	 * 获取整体元素，将整体元素分为多行多列元素，取每一个子元素的中心坐标存入集合对象{1,2,3,4,5,6,7,8,9, ,0,x}
	 * @param element
	 * @param rows
	 * @param columns
	 * @return
	 */
	public List<Point> getElementByCoordinates(MobileElement element, int rows, int columns) {
		// int[] coordinate=new int[2];
		List<Point> elementResolve = new ArrayList<Point>();
		if (element != null) {
			int startx = element.getLocation().getX();// 起始点坐标x
			int starty = element.getLocation().getY();// 起始点坐标y
			int offsetx = element.getSize().getWidth();// 该元素的宽
			int offsety = element.getSize().getHeight();// 该元素的高
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < columns; j++) {
					int x = startx + (offsetx / 2 * columns) * (2 * j + 1);
					int y = starty + (offsety / (2 * rows) * (2 * i + 1));
					Point p = new Point(x, y);
					elementResolve.add(p);
				}
			}
		}
		return elementResolve;
	}
	/**
	 * 针对有规律元素拆分后，点击其中的某一个的坐标点，{1,2,3,4,5,6,7,8,9, ,0,x}
	 * @param element
	 * @param rows
	 * @param columns
	 * @param index
	 */
	public void clickElementByCoordinate(MobileElement element, int rows, int columns, int index) {
		if (element != null) {
			List<Point> elementResolve = getElementByCoordinates(element, rows, columns);
			if (!elementResolve.isEmpty() && elementResolve != null) {
				this.clickByCoordinate(elementResolve.get(index).x, elementResolve.get(index).y);
			} else {
				logger.error("the elementResolve is null or empty");
				throw new RuntimeException("the elementResolve is null or empty");
			}
		} else {
			logger.error("element is null");
			throw new RuntimeException("element is null");
		}
	}

	/**
	 * 密码从0到9，但是0的坐标点排布在第11个位置，所以索引是10,这是针对安全密码数字键盘的操作
	 * @param pwd
	 * @param password
	 */
	public void sendkeysPwd(List<Point> pwd, int[] password) {
		if (password.length > 0) {
			for (int i = 0; i < password.length; i++) {
				if (password[i] == 0) {
					this.clickByCoordinate(pwd.get(10).x, pwd.get(10).y);
				} else {
					this.clickByCoordinate(pwd.get(password[i] - 1).x, pwd.get(password[i] - 1).y);
				}
			}
		} else {
			logger.error("param 'password' is empty");
			throw new RuntimeException("param 'password' is empty");
		}
	}
	/**
	 * 安全键盘密码锁时使用，按照规律拆分后的坐标进行点击输入
	 * @param element
	 * @param rows
	 * @param columns
	 * @param password
	 */
	public void sendkeysPwd(MobileElement element, int rows, int columns, int[] password) {
		if (element != null) {
			List<Point> pwd = getElementByCoordinates(element, rows, columns);
			sendkeysPwd(pwd, password);
		} else {
			logger.error("element is null");
			throw new RuntimeException("element is null");
		}

	}
	/**
	 * 九宫格手势解锁,参数indexs是密码数字组成的数组，参数indexs={5,2,3,6,8,7},数字从1到9
	 * @param element
	 * @param indexs 
	 */
	public void wakeByGestures(MobileElement element, int[] indexs) {
		if (element != null) {
			List<Point> elementResolve = getElementByCoordinates(element, 3, 3);
			TouchAction ta = null;
			//Duration dur=Duration.ofMillis(duration);
			if (indexs.length > 0) {
				ta = new TouchAction(driver)
						.press(new PointOption<>().withCoordinates(elementResolve.get(indexs[0] - 1).x, elementResolve.get(indexs[0] - 1).y))
						.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)));
				for (int i = 1; i < indexs.length; i++) {
					ta.moveTo(new PointOption<>().withCoordinates(elementResolve.get(indexs[i] - 1).x,elementResolve.get(indexs[i] - 1).y)
							)
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)));
				}
				ta.release().perform();
			} else {
				logger.error("param 'password' is empty");
				throw new RuntimeException("param 'password' is empty");
			}
		} else {
			logger.error("element is null");
			throw new RuntimeException("element is null");
		}
	}
	/**
	 * 切换输入法，仅支持android，用在搜索时需要点击键盘上的搜索按钮时使用，可以在appium提供的输入法和手机已有的输入之间来回切换
	 * @param input
	 * @throws Exception
	 */
	public void switchInput(String input) throws Exception{
		if(MobilePlatform.ANDROID.equalsIgnoreCase(platform)){
			CommandLineExec.executor("adb shell ime set "+input);
		}else{
			logger.error("platform is " + platform + ",so not support switchInput");
			throw new PlatformException("platform is " + platform + ",so not support switchInput");
		}
	}
	/**
	 * 隐藏键盘，仅支持ios
	 */
	public void hideKeyboard() {
		if(MobilePlatform.IOS.equalsIgnoreCase(platform)) {
			((IOSDriver)driver).hideKeyboard();
		}else {
			logger.error("platform is " + platform + ",so not support hideKeyboard");
			throw new PlatformException("platform is " + platform + ",so not support hideKeyboard");
		}
		
	}
	/**
	 * 隐藏键盘仅支持ios
	 * @param keyName
	 */
	public void hideKeyboard(String keyName) {
		if(MobilePlatform.IOS.equalsIgnoreCase(platform)) {
			((IOSDriver)driver).hideKeyboard(keyName);;
		}else {
			logger.error("platform is " + platform + ",so not support hideKeyboard");
			throw new PlatformException("platform is " + platform + ",so not support hideKeyboard");
		}
	}
	/**
	 * 隐藏键盘，仅支持ios
	 * @param strategy
	 * @param keyName
	 */
	public void hideKeyboard(String strategy,String keyName) {
		if(MobilePlatform.IOS.equalsIgnoreCase(platform)) {
			((IOSDriver)driver).hideKeyboard(strategy, keyName);;
		}else {
			logger.error("platform is " + platform + ",so not support hideKeyboard");
			throw new PlatformException("platform is " + platform + ",so not support hideKeyboard");
		}
	}
	/**
	 * 这是重新启动ios app
	 * @param bundleId
	 */
	public void activateApp(String bundleId) {
		driver.activateApp(bundleId);
	}
	

}
