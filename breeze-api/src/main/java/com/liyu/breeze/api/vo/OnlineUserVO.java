package com.liyu.breeze.api.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 在线用户信息
 *
 * @author gleiyu
 */
@Data
public class OnlineUserVO {
    private String userName;
    private String email;
    private String ipAddress;
    private Date loginTime;
    private String token;
    private List<String> privileges;
    private List<String> roles;
    private Boolean remember;
    private Long expireTime;

    public Boolean getRemember() {
        return remember != null && remember;
    }

    public void setRemember(Boolean remember) {
        this.remember = remember;
    }
}
