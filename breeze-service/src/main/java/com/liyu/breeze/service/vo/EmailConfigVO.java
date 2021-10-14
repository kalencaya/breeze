package com.liyu.breeze.service.vo;

import cn.hutool.core.util.StrUtil;
import com.liyu.breeze.common.constant.Constants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 系统邮箱配置类
 *
 * @author gleiyu
 */
@Data
public class EmailConfigVO {
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String host;
    @NotNull
    private Integer port;
    private String encoding;

    public String getEncoding() {
        return StrUtil.isEmpty(this.encoding) ? Constants.DEFAULT_CHARSET : this.encoding;
    }


}
