package com.liyu.breeze.service.impl;

import com.liyu.breeze.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @author gleiyu
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;
    //todo 在service实例化后根据后台配置信息重新设置邮箱发送人 https://blog.csdn.net/xiaoxiaole0313/article/details/103659195

    /**
     * 异步发送邮件
     *
     * @param sendTo  收件人
     * @param subject 邮件主题
     * @param context 邮件内容
     */
    @Async
    @Override
    public void sendSimpleEmail(String[] sendTo, String subject, String context) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject(subject);
            message.setText(context);
            message.setTo(sendTo);
            message.setFrom(this.from);
            javaMailSender.send(message);
        } catch (MailException e) {
            log.error("email send error");
        }
    }

    /**
     * 异步发送HTML邮件
     *
     * @param sendTo  收件人
     * @param subject 邮件主题
     * @param html    HTML邮件内容
     */
    @Async
    @Override
    public void sendHtmlEmail(String[] sendTo, String subject, String html) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setTo(sendTo);
            mimeMessageHelper.setFrom(this.from);
            mimeMessageHelper.setText(html, true);
            javaMailSender.send(message);
        } catch (MessagingException | MailException e) {
            log.error("email send error");
        }
    }
}
