package com.yuan.middleware.message.email;

import microsoft.exchange.webservices.data.core.ExchangeService;
import microsoft.exchange.webservices.data.core.enumeration.misc.ExchangeVersion;
import microsoft.exchange.webservices.data.core.enumeration.property.BodyType;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;
import microsoft.exchange.webservices.data.credential.ExchangeCredentials;
import microsoft.exchange.webservices.data.credential.WebCredentials;
import microsoft.exchange.webservices.data.property.complex.MessageBody;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Copyright(c) 2018 Sunyur.com, All Rights Reserved.
 * <P>https://www.jumtu.com/article/114777.html</P>
 *
 * @author: YuanJiaMin
 * @date: 2021/2/23 11:44 上午
 */
@Component
public class MailExchangeManager {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${youjia.exchange.mail.username}")
    private String username;
    @Value("${youjia.exchange.mail.password}")
    private String password;
    @Value("${youjia.exchange.mail.host}")
    private String host;


    /**
     * 使用Exchange协议发送
     *
     * @param to       收件人
     * @param subject  邮件主题
     * @param content  正文
     * @param filePath 附件
     * @throws Exception
     */
    public boolean sendMail(String to, String subject, String content, String filePath) {
        logger.info("exchange邮件发送 to:{}, subject:{}, content:{},filePath:{}", to, subject, content, filePath);
        boolean isOK;
        ExchangeService service = new ExchangeService(ExchangeVersion.Exchange2007_SP1);
        ExchangeCredentials credentials = new WebCredentials(username, password);
        service.setCredentials(credentials);
        try {
            service.setUrl(new URI(host));
            EmailMessage msg = new EmailMessage(service);
            msg.setSubject(subject);
            MessageBody body = MessageBody.getMessageBodyFromText(content);
            body.setBodyType(BodyType.HTML);
            msg.setBody(body);
            //收件人
            msg.getToRecipients().add(to);
            if (StringUtils.isNotEmpty(filePath)) {
                msg.getAttachments().addFileAttachment(filePath);
            }
            msg.send();
            isOK = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            isOK = false;
        }
        return isOK;
    }

    public static void main(String[] args) {
        Map<Object, Object> hashMap = new Hashtable<>();
        hashMap.put("a",null);
    }
}
