package com.liyu.breeze.api.controller.admin;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import com.liyu.breeze.api.annotation.Logging;
import com.liyu.breeze.api.vo.ResponseVO;
import com.liyu.breeze.common.constant.Constants;
import com.liyu.breeze.service.admin.EmailService;
import com.liyu.breeze.service.admin.SystemConfigService;
import com.liyu.breeze.service.dto.SystemConfigDTO;
import com.liyu.breeze.service.vo.EmailConfigVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author gleiyu
 */
@RestController
@RequestMapping("/api/admin/config")
@Api(tags = "系统管理-系统配置")
public class SystemConfigController {

    @Autowired
    private SystemConfigService systemConfigService;

    @Autowired
    private EmailService emailService;

    @Logging
    @PutMapping(path = "email")
    @ApiOperation(value = "配置系统邮箱", notes = "配置系统邮箱")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseVO> configEmail(@Validated @RequestBody EmailConfigVO emailConfig) {
        String password = emailConfig.getPassword();
        emailConfig.setPassword(Base64.encode(password));
        this.systemConfigService.deleteByCode(Constants.CFG_EMAIL_CODE);
        SystemConfigDTO systemConfig = new SystemConfigDTO();
        systemConfig.setCfgCode(Constants.CFG_EMAIL_CODE);
        systemConfig.setCfgValue(JSONUtil.toJsonStr(emailConfig));
        this.systemConfigService.insert(systemConfig);
        this.emailService.configEmail(emailConfig);
        return new ResponseEntity<>(ResponseVO.sucess(), HttpStatus.OK);
    }

    @Logging
    @GetMapping(path = "email")
    @ApiOperation(value = "查询系统邮箱", notes = "查询系统邮箱")
    public ResponseEntity<EmailConfigVO> showEmail() {
        SystemConfigDTO systemConfig = this.systemConfigService.selectByCode(Constants.CFG_EMAIL_CODE);
        if (systemConfig != null) {
            EmailConfigVO config = JSONUtil.toBean(systemConfig.getCfgValue(), EmailConfigVO.class);
            return new ResponseEntity<>(config, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new EmailConfigVO(), HttpStatus.OK);
        }
    }
}
