package com.guo.appium.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomUtil {
	/**
	 * 随机生成指定长度的字符串
	 * @param chars
	 * @param lengthOfString
	 * @return
	 */
	public static String getRndStrAndNumberByLen(int lengthOfString) {
		int i, count = 0;
		final String chars ="1,2,3,4,5,6,7,8,9,0,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		String[] charArr = chars.split(",");

		StringBuffer randomStr = new StringBuffer("");
		Random rnd = new Random();
		int strLen = charArr.length;

		while (count < lengthOfString) {
			i = rnd.nextInt(strLen);//strLen如果等于36，i值就在0-35之间
			//System.out.println(i);
			randomStr.append(charArr[i]);
			count++;
		}
		return randomStr.toString().toLowerCase();
	}
	public static String getRndStrByLen(int lengthOfString) {
		int i, count = 0;
		final String chars ="A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z";
		String[] charArr = chars.split(",");

		StringBuffer randomStr = new StringBuffer("");
		Random rnd = new Random();
		int strLen = charArr.length;

		while (count < lengthOfString) {
			i = rnd.nextInt(strLen);//strLen如果等于26，i值就在0-25之间
			//System.out.println(i);
			randomStr.append(charArr[i]);
			count++;
		}
		return randomStr.toString().toLowerCase();
	}
	public static String getRndStrZhByLen(int lengthOfString) {
		int i, count = 0;
		final String chars ="我,也,不,知,道,你,想,发,神,好,好,的,地,方,发,的,的,是,问,有,二,与";
		String[] charArr = chars.split(",");

		StringBuffer randomStr = new StringBuffer("");
		Random rnd = new Random();
		int strLen = charArr.length;

		while (count < lengthOfString) {
			i = rnd.nextInt(strLen);//strLen如果等于30，i值就在0-30之间
			//System.out.println(i);
			randomStr.append(charArr[i]);
			count++;
		}
		return randomStr.toString().toLowerCase();
	}
	/**
	 * 随机生成指定长度的数字，以字符串形式返回
	 * @param lengthOfNumber
	 * @return
	 */
	public static String getRndNumByLen(int lengthOfNumber) {
		int i, count = 0;
		//098
		StringBuffer randomStr = new StringBuffer("");
		Random rnd = new Random();

		while (count < lengthOfNumber) {
			i = Math.abs(rnd.nextInt(10));
			if (i == 0 && count == 0) {
				//意思是不生成开始为0的数字，比如098,01
			} else {
				randomStr.append(String.valueOf(i));
				count++;
			}
		}
		return randomStr.toString();
	}

	/**
	 * 生成指定范围内的数字，不包含参数本身
	 * @param extent
	 * @return
	 */
	public static int getExtentRandomNumber(int extent) {
		int number = (int) (Math.random() * extent);
		return number;
	}
	/**
	 * 生成指定范围内的浮点数
	 * @param min
	 * @param max
	 * @return
	 */
	private static float randomFloat(int min,int max){
        Random random = new Random();
        //10,100
        //0.0746273646*100=746.273646=746=7.46
//        int s = random.nextInt(max)%(max-min+1) + min;
        float x=min;//x=10
        while(x<=min){
        	double db = random.nextDouble() * max * 100;
        	x = ((int) db) / 100f;
        }
        return x;
	}
	/**
	 * 生成指定范围内的整数
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInt(int min,int max){ 
        Random random = new Random();
        //10,100 88%91=88+10=98
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
	}
//	/**
//	 * 从字符串里提取整数  dsd234dsf455h34h232h34545
//	 * @param str  目标字符串
//	 * @param index 提取第几组数字，从0开始
//	 * @return 返回一个整数，如果返回-1则表明你提取的组数不存在
//	 */
//	public static int getInt(String str,int index){
//		Pattern p = Pattern.compile("\\d{1,}");//这个1是指连续数字的最少个数
//        Matcher m = p.matcher(str);
//        List<Integer> result=new ArrayList<Integer>();
//        while (m.find()) {
//           // System.out.println(m.group());
//            result.add(Integer.valueOf(m.group()));
//        }
//        if(!result.isEmpty()&&index<result.size()){
//        	return result.get(index);
//        }else{
//        	System.out.println("你要找的第"+(index+1)+"组数字不存在");
//        	return -1;
//        }
//	}
	public static void main(String[] args) {
		Random r=new Random();
		System.out.println(getExtentRandomNumber(4));
		System.out.println(randomFloat(10,100));
//		System.out.println(getRndStrZhByLen(5)+getRndStrByLen(5));
//		System.out.println(r.nextDouble());
//		System.out.println(randomInt(1,10));
////		System.out.println(randomInt(5,10));
//		int s=getInt("appium -p 4490 -bp 2234",1);
//		System.out.println(s);
//		System.out.println("0"+getRndNumByLen(3));
//		System.out.println(getInt("appium -p 4490 -bp 2253 -U 127.0.0.1:62001>logs/127.0.0.1:62001.log",6));
	}

}
