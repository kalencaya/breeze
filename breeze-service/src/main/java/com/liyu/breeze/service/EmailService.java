package com.magicdt.service;

/**
 * @author gleiyu
 */
public interface EmailService {
    /**
     * 发送简单邮件
     *
     * @param sendTo  收件人
     * @param subject 主题
     * @param context 正文
     */
    void sendSimpleEmail(String[] sendTo, String subject, String context);

    /**
     * 发送html邮件
     *
     * @param sendTo  收件人
     * @param subject 主题
     * @param html    正文
     */
    void sendHtmlEmail(String[] sendTo, String subject, String html);
}
