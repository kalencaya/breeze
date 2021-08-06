package com.liyu.breeze.api.controller;

import com.liyu.breeze.api.util.I18nUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gleiyu
 */
@RestController
@RequestMapping("/api")
public class CommonController {
    @GetMapping("i18n")
    public String i18nTest() {
        return I18nUtil.get("response.error.unsupported.connection");
    }
}
