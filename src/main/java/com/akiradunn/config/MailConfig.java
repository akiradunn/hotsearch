package com.akiradunn.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mail")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MailConfig {
    private String weibo_hotsearch_url;
    //#微博热搜关键字
    private String weibo_hotsearch_keyword;
    //#邮件标题
    private String mail_subject;
    //#邮件内容标题
    private String mail_content_head;
    //#邮箱服务器地址
    private String mail_senderserver_host;
    //#发送邮件的端口
    private String mail_senderserver_port;
    //#是否需要进行身份验证,视调用的邮箱而定，比方说QQ邮箱就需要，否则就会发送失败
    private String mail_senderserverauth;
    //#传输协议
    private String mail_sender_protocol;
    //#发件人邮箱
    private String mail_sender_address;
    //#发件人邮箱别名
    private String mail_sender_personalName;
    //#发件人邮箱用户名
    private String mail_sender_username;
    //#发件人邮箱客户端授权码
    private String mail_sender_password;
    //#是否开启debug
    private String mail_mailDebug;
    //#邮件正文类型
    private String mail_contentType;
    //#收件人邮箱
    private String mail_receiver_address;
}
