<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<%@include file="/WEB-INF/views/common.jsp" %>
<link rel="stylesheet" href="${contextPath}/css/login.css">
</head>
<body>
  <div class="login-container">
    <div class="login-panel">
      <div class="login-title-container">
        <img alt="" src="${contextPath}/images/login_tilte_logo.jpg">
      </div>
      <div class="login-form-container">
        <form class="login-form" action="${contextPath}/login" method="post">
          <div class="login-input-container">
            <input type="text" name="mobilePhoneNumber" value="${mobilePhoneNumber}" placeholder="手机号" maxlength="11" autofocus="autofocus" autocomplete="mobile" />
            <button type="button" class="login-smscode-btn" disabled="disabled">获取验证码</button>
          </div>
          <div class="login-input-container">
            <input type="text" name="smsCode" placeholder="验证码" maxlength="6" autocomplete="off">
          </div>
          <div>
            <button type="button" class="login-submit-btn">登录</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  <script type="text/javascript">
    var errorMsg = "${errorMsg}";
  </script>
  <script type="text/javascript" src="${contextPath}/js/login.js"></script>
</body>
</html>