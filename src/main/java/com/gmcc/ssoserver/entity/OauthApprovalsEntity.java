package com.gmcc.ssoserver.entity;

import com.gmcc.ssoserver.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * <p>
 * Oauth approvals, due to JdbcApprovalStore configuration, field names are camel case.
 * </p>
 *
 * @author Kelvin@Inspur
 * @since 2019-07-25
 */
@TableName("oauth_approvals")
public class OauthApprovalsEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("userId")
    private String userId;

    @TableField("clientId")
    private String clientId;

    @TableField("scope")
    private String scope;

    @TableField("status")
    private String status;

    @TableField("expiresAt")
    private LocalDateTime expiresAt;

    @TableField("lastModifiedAt")
    private LocalDateTime lastModifiedAt;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(LocalDateTime lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public static final String USERID = "userId";

    public static final String CLIENTID = "clientId";

    public static final String SCOPE = "scope";

    public static final String STATUS = "status";

    public static final String EXPIRESAT = "expiresAt";

    public static final String LASTMODIFIEDAT = "lastModifiedAt";

    @Override
    public String toString() {
        return "OauthApprovalsEntity{" +
        "userId=" + userId +
        ", clientId=" + clientId +
        ", scope=" + scope +
        ", status=" + status +
        ", expiresAt=" + expiresAt +
        ", lastModifiedAt=" + lastModifiedAt +
        "}";
    }
}
