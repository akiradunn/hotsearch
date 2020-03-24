package com.akiradunn.hotsearch.utils;

import com.akiradunn.hotsearch.config.MailConfig;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Properties;

public class MailUtils {

    public static void sendMail(String subject, String content, MailConfig mailConfig) throws Exception {

        String host = mailConfig.getMail_senderserver_host();       //邮箱服务器地址
        String port = mailConfig.getMail_senderserver_port();       //发送邮件的端口
        String auth = mailConfig.getMail_senderserverauth();        //是否需要进行身份验证,视调用的邮箱而定，比方说QQ邮箱就需要，否则就会发送失败
        String protocol = mailConfig.getMail_sender_protocol();                  //传输协议
        String mailFrom = mailConfig.getMail_sender_address();   //发件人邮箱
        String personalName = mailConfig.getMail_sender_personalName();         //发件人邮箱别名
        String username = mailConfig.getMail_sender_username();   //发件人邮箱用户名
        String password = mailConfig.getMail_sender_password();              //发件人邮箱密码
        String mailDebug = mailConfig.getMail_mailDebug();                //是否开启debug
        String contentType = null;                 //邮件正文类型
        String receiverAddress = mailConfig.getMail_receiver_address();  //收件人邮箱

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", auth == null ? "true" : auth);
        props.put("mail.transport.protocol", protocol == null ? "smtp" : protocol);
        props.put("mail.smtp.port", port == null ? "25" : port);
        props.put("mail.debug", mailDebug == null ? "false" : mailDebug);
        props.put("mail.smtp.timeout", "9000000");
        props.put("mail.smtp.connectiontimeout", "500000");
        Session mailSession = Session.getInstance(props);

        // 设置session,和邮件服务器进行通讯。
        MimeMessage message = new MimeMessage(mailSession);
        // 设置邮件主题
        message.setSubject(subject);
        // 设置邮件正文
        message.setContent(content, contentType == null ? "text/html;charset=UTF-8" : contentType);
        // 设置邮件发送日期
        message.setSentDate(new Date());
        InternetAddress address = new InternetAddress(mailFrom, personalName);
        // 设置邮件发送者的地址
        message.setFrom(address);
        // 设置邮件接收方的地址
        message.setRecipients(Message.RecipientType.TO, receiverAddress);

        Transport transport = null;
        transport = mailSession.getTransport();
        message.saveChanges();

        transport.connect(host, username, password);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
