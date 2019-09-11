<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<%@include file="/WEB-INF/views/common.jsp" %>
</head>
<body>
  <div style="height: 100vh; display: flex; flex-flow: row nowrap; justify-content: center; align-items: center;">
    <p>您所登录的系统验证失败，请联系管理员。点击<a href="${contextPath}/index">返回</a>服务列表页面。</p>
  </div>
</body>
</html>