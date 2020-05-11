package com.guo.appium.driver;

import java.time.Duration;

import com.guo.appium.exception.DirectionException;
import com.guo.appium.exception.SwipeException;
import com.guo.appium.utils.Log;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class SwipeScreenOrElement {
	private Log logger=Log.getLogger(SwipeScreenOrElement.class);
	private AppiumDriver driver;
	private int appWidth;
	private int appHeight;
	/**
	 * 构造方法，对三个变量进行初始化
	 * @param driver
	 */
	public SwipeScreenOrElement(AppiumDriver driver){
		this.driver=driver;
		this.appWidth=driver.manage().window().getSize().width;
		this.appHeight=driver.manage().window().getSize().height;
	}
	/**
	 * 向左滑动
	 * @param duration 滑动时间，单位是毫秒
	 * @throws SwipeException
	 */
	@SuppressWarnings("deprecation")
	private void swipeToLeft(int duration){
		int startx=appWidth*9/10;
		int endx=appWidth*1/10;
		int y=appHeight*1/2;
		try {
			Duration dur=Duration.ofMillis(duration);
			this.swipe(startx,y,endx,y,dur);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("swipeToLeft is failure -> "+e.getMessage());
			throw new SwipeException("swipeToLeft is failure -> "+e.getMessage());
		}
	}
	/**
	 * 向右滑动
	 * @param duration 滑动时间，单位毫秒
	 * @throws SwipeException 
	 */
	@SuppressWarnings("deprecation")
	private void swipeToRight(int duration){
		int startx=appWidth*1/10;
		int endx=appWidth*9/10;
		int y=appHeight*1/2;
		try {
			Duration dur=Duration.ofMillis(duration);
			this.swipe(startx,y,endx,y,dur);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("swipeToRight is failure -> "+e.getMessage());
			throw new SwipeException("swipeToRight is failure -> "+e.getMessage());
		}
	}
	/**
	 * 向上滑动
	 * @param duration 持续时间，单位毫秒
	 * @throws SwipeException 
	 */
	@SuppressWarnings("deprecation")
	private void swipeToUp(int duration){
		int starty=appHeight*9/10;
		int endy=appHeight*1/10;
		int x=appWidth*1/2;
		try {
			Duration dur=Duration.ofMillis(duration);
			this.swipe(x,starty,x,endy,dur);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("swipeToUp is failure -> "+e.getMessage());
			throw new SwipeException("swipeToUp is failure -> "+e.getMessage());
			
		}
	}
	/**
	 * 向下滑动
	 * @param duration 持续时间，单位毫秒
	 * @throws SwipeException 
	 */
	@SuppressWarnings("deprecation")
	private void swipeToDown(int duration){
		int starty=appHeight*1/10;
		int endy=appHeight*9/10;
		int x=appWidth*1/2;
		try {
			Duration dur=Duration.ofMillis(duration);
			this.swipe(x,starty,x,endy,dur);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("swipeToDown is failure -> "+e.getMessage());
			throw new SwipeException("swipeToDown is failure -> "+e.getMessage());
		}
	}
	/**
	 * 滑动方法，通过参数实现各方向滑动
	 * @param direction 方向参数，值为"up"、"down"、"left"、"right"
	 * @param duration 滑动时间，单位毫秒
	 * @throws SwipeException 
	 * @throws DirectionException
	 */
	public void swipe(String direction,int duration){
		String direc=direction.toLowerCase();
		switch(direc){
		case "up":
			this.swipeToUp(duration);
			break;
		case "down":
		    this.swipeToDown(duration);
		    break;
		case "left":
			this.swipeToLeft(duration);
			break;
		case "right":
			this.swipeToRight(duration);
			break;
		default:
			System.out.println("方向参数错误");
			logger.error("direction of swipe,direction must is up or down or left or right");
			throw new DirectionException("direction of swipe,direction must is up or down or left or right");
		}
	}
	
	public void swipe(int startx,int starty,int endx,int endy,Duration duration){
		TouchAction ta=new TouchAction(driver);
		
		ta.press(new PointOption<>().point(startx, starty)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(new PointOption<>().point(endx, endy)).release().perform();
	}
	public void swipeOnElement(MobileElement element,String direction){
		swipeOnElement(element, direction, 10, 10);
		
	}
	public void swipeOnElement(MobileElement element,String direction,int offsetfrom,int offsetend){
		swipeOnElement(element, direction, offsetfrom, offsetend,1000);
		
	}
	
	/**
	 * 我不想指定偏移量，我只想传持续时间、元素对象、滑动方向
	 */
	public void swipeOnElement(MobileElement element,String direction,int duration){
		
		swipeOnElement(element, direction, 10, 10, duration);
	}
	/**
	 * 基于元素上的滑动
	 * @param element
	 * @param direction
	 * @param offsetfrom
	 * @param offsetend
	 * @param duration
	 */
	public void swipeOnElement(MobileElement element,String direction,int offsetfrom,int offsetend,int duration){
		//获取元素起始点坐标
		Point p=element.getLocation();
		int startx=p.getX();
		int starty=p.getY();
		
		//获取元素的宽和高
		Dimension dim=element.getSize();
		int width=dim.getWidth();
		int height=dim.getHeight();
		
		//获取元素的中心点坐标
		Point centerp=element.getCenter();
		int centerx=centerp.getX();
		int centery=centerp.getY();
		
		//计算出元素的结束点坐标
		int endx=startx+width;
		int endy=starty+height;
		Duration dur=Duration.ofMillis(duration);
		switch (direction.toLowerCase()) {
		case "up":
			this.swipe(centerx, endy-offsetfrom, centerx, starty+offsetend, dur);
			break;
		case "down":
			this.swipe(centerx, starty+offsetfrom, centerx,endy-offsetend , dur);
			break;
		case "left":
			this.swipe(endx-offsetfrom, centery, startx+offsetend,centery, dur);
			break;
		case "right":
			this.swipe(startx+offsetfrom, centery,endx-offsetend,centery, dur);
			break;
		default:
			new RuntimeException("方向参数有误");
			break;
		}
		
	}	
}
