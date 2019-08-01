<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<link rel="stylesheet" href="${contextPath}/css/login.css">
</head>
<body>
  <div class="login-bg">
    <div class="content">
      <div class="title">
        <h2>${title}</h2>
      </div>
      <div class="form-box">
        <form id="loginForm" action="${contextPath}/login/sms" method="post">
          <div class="login-center">
            <span class="t-name">手机号：</span><input type="text" name="mobilePhoneNumber" id="tel_num" class="inp phone-inp" value="" placeholder="请输入您的手机号">
          </div>
          <div class="login-center">
            <span class="t-name">验证码：</span><input type="text" name="smsCode" id="code_num" class="inp code-inp" value="" placeholder="请输入您的验证码">
            <div class="get-code">
              <span class="code-btn">获取验证码</span>
            </div>
          </div>
          <div class="error-message">
            <c:if test="${not empty errorMessage}">
            ${errorMessage}
            </c:if>
          </div>
          <div class="login-button">登录</div>
        </form>
      </div>
    </div>
  </div>
  <script type="text/javascript">
	var ctx = "${contextPath}";
  </script>
  <script type="text/javascript" src="${contextPath}/plugins/jquery/jquery-3.4.1.min.js"></script>
  <script type="text/javascript" src="${contextPath}/js/login.js"></script>
</body>
</html>