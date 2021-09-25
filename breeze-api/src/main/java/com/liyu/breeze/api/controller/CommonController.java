package com.liyu.breeze.api.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.RandomUtil;
import com.liyu.breeze.api.annotation.AnonymousAccess;
import com.liyu.breeze.common.constant.Constants;
import com.liyu.breeze.service.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author gleiyu
 */
@Controller
@RequestMapping("/api")
@Api("公共模块接口")
public class CommonController {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 生成验证码
     *
     * @param req  request
     * @param resp response
     */
    @AnonymousAccess
    @ApiOperation(value = "查询验证码", notes = "查询验证码信息")
    @GetMapping(path = {"/authCode"})
    public ResponseEntity<Object> authCode(HttpServletRequest req, HttpServletResponse resp) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(150, 32, 5, RandomUtil.randomInt(6, 10));
        Font font = new Font("Stencil", Font.BOLD + Font.ITALIC, 20);
        lineCaptcha.setFont(font);
        lineCaptcha.setBackground(new Color(246, 250, 254));
        lineCaptcha.createCode();
        String uuid = Constants.AUTH_CODE_KEY + UUID.randomUUID().toString();
        redisUtil.set(uuid, lineCaptcha.getCode(), 10 * 60);
        Map<String, Object> map = new HashMap<>(2);
        map.put("uuid", uuid);
        map.put("img", lineCaptcha.getImageBase64Data());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
