package com.guo.appium.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.guo.appium.driver.CrazyPath;


public class SendMail {   
    public void send(String title,String text) {
        try {
        	Properties p = new Properties(); //Properties p = System.getProperties();   
            p.put("mail.smtp.auth", "true");   
            p.put("mail.transport.protocol", "smtp");   
            p.put("mail.smtp.host", "smtp.163.com");   
            p.put("mail.smtp.port", "25");   
            //建立会话   
            Session session = Session.getInstance(p);   
            Message msg = new MimeMessage(session); //建立信息   
            ProUtil pu=new ProUtil(CrazyPath.globalPath);
            String sendUser=pu.getKey("senduser");
            String password=pu.getKey("password");
            msg.setFrom(new InternetAddress(sendUser)); //发件人   
            
            String toPersion = pu.getKey("tomail");//getMailList(to);
            InternetAddress[] iaToList = new InternetAddress().parse(toPersion);
            
            msg.setRecipients(Message.RecipientType.TO,iaToList); //收件人   
    
            msg.setSentDate(new Date()); // 发送日期   
            msg.setSubject(title); // 主题   
            msg.setText(text); //内容   
            // 邮件服务器进行验证   
            Transport tran = session.getTransport("smtp");   
            tran.connect("smtp.163.com",sendUser,password);    
            tran.sendMessage(msg, msg.getAllRecipients()); // 发送   
            System.out.println("邮件发送成功");   
    
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
    }   
    public static void main(String[] args) {
    	SendMail sm=new SendMail();
    	sm.send("邮件","这是邮件工具类");
	}
} 