package com.qiya.middletier.service;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Created by qiyalm on 17/3/16.
 */
@Component
public class MailSendService {

    private static Logger log = LoggerFactory.getLogger(MailSendService.class);


    @Value("${leancloud.X-LC-Id:}")
    private String x_lc_id = null;

    @Value("${leancloud.X-LC-Key:}")
    private String x_lc_key = null;

    @Value("${spring.mail.username:}")
    private String username;

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private JavaMailSender javaMailSender;


    //发送文本邮件 subject:邮件主题; text:邮件内容
    public void sendTextMail(String subject,String text,String ... personList){
        threadPoolTaskExecutor.execute(() -> {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(username);
                message.setTo(personList);
                message.setSubject(subject);
                message.setText(text);
                log.info("发送文本邮件:{}", "邮件发送开始");
                javaMailSender.send(message);

            } catch (Exception e) {
                log.warn("发送文本邮件邮件失败=exception:{}", e);
            }
        });
    }

    //发送带附件的邮件 subject:邮件主题; text:邮件内容 ; file:附件
    public void sendFileMail(String subject, String text, File file,String[] personList){
        threadPoolTaskExecutor.execute(() -> {
            try {

                MimeMessage msg = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(msg,true);

                helper.setFrom(username);
                helper.setTo(personList);
                helper.setSubject(subject);
                helper.setText(text);

                //加入附件
                if(file!=null) helper.addAttachment(file.getName(), file);

                log.info("发送含附件邮件:{}", "邮件发送开始");

                javaMailSender.send(msg);

            } catch (Exception e) {
                log.warn("发送含附件邮件失败=exception:{}", e);
            }
        });
    }

}
