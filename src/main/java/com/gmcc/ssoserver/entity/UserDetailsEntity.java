package com.gmcc.ssoserver.entity;

import com.gmcc.ssoserver.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * <p>
 * User details
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-08-01
 */
@TableName("user_details")
public class UserDetailsEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "phone", type = IdType.INPUT)
    private String phone;

    @TableField("user_name")
    private String userName;

    @TableField("check_code")
    private String checkCode;

    @TableField("authority")
    private String authority;

    @TableField("last_sms_code_time")
    private LocalDateTime lastSmsCodeTime;

    @TableField("login_time")
    private LocalDateTime loginTime;


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public LocalDateTime getLastSmsCodeTime() {
        return lastSmsCodeTime;
    }

    public void setLastSmsCodeTime(LocalDateTime lastSmsCodeTime) {
        this.lastSmsCodeTime = lastSmsCodeTime;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public static final String PHONE = "phone";

    public static final String USER_NAME = "user_name";

    public static final String CHECK_CODE = "check_code";

    public static final String AUTHORITY = "authority";

    public static final String LAST_SMS_CODE_TIME = "last_sms_code_time";

    public static final String LOGIN_TIME = "login_time";

    @Override
    public String toString() {
        return "UserDetailsEntity{" +
        "phone=" + phone +
        ", userName=" + userName +
        ", checkCode=" + checkCode +
        ", authority=" + authority +
        ", lastSmsCodeTime=" + lastSmsCodeTime +
        ", loginTime=" + loginTime +
        "}";
    }
}
