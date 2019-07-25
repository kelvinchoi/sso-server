package com.gmcc.ssoserver.entity;

import com.gmcc.ssoserver.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * <p>
 * Oauth code
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-25
 */
@TableName("oauth_code")
public class OauthCodeEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("code")
    private String code;

    @TableField("authentication")
    private Blob authentication;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Blob getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Blob authentication) {
        this.authentication = authentication;
    }

    public static final String CODE = "code";

    public static final String AUTHENTICATION = "authentication";

    @Override
    public String toString() {
        return "OauthCodeEntity{" +
        "code=" + code +
        ", authentication=" + authentication +
        "}";
    }
}
