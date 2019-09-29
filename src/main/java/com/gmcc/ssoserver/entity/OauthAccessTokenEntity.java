package com.gmcc.ssoserver.entity;

import com.gmcc.ssoserver.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.sql.Blob;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * <p>
 * Oauth access token
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-25
 */
@TableName("oauth_access_token")
public class OauthAccessTokenEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("token_id")
    private String tokenId;

    @TableField("token")
    private Blob token;

    @TableId(value = "authentication_id", type = IdType.INPUT)
    private String authenticationId;

    @TableField("user_name")
    private String userName;

    @TableField("client_id")
    private String clientId;

    @TableField("authentication")
    private Blob authentication;

    @TableField("refresh_token")
    private String refreshToken;


    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Blob getToken() {
        return token;
    }

    public void setToken(Blob token) {
        this.token = token;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Blob getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Blob authentication) {
        this.authentication = authentication;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public static final String TOKEN_ID = "token_id";

    public static final String TOKEN = "token";

    public static final String AUTHENTICATION_ID = "authentication_id";

    public static final String USER_NAME = "user_name";

    public static final String CLIENT_ID = "client_id";

    public static final String AUTHENTICATION = "authentication";

    public static final String REFRESH_TOKEN = "refresh_token";

    @Override
    public String toString() {
        return "OauthAccessTokenEntity{" +
        "tokenId=" + tokenId +
        ", token=" + token +
        ", authenticationId=" + authenticationId +
        ", userName=" + userName +
        ", clientId=" + clientId +
        ", authentication=" + authentication +
        ", refreshToken=" + refreshToken +
        "}";
    }
}
