<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<%@include file="/WEB-INF/views/common.jsp"%>
<link rel="stylesheet" href="${contextPath}/css/index.css">
</head>
<body>
  <div class="service-container">
    <c:if test="${systemServiceEntityList != null && !systemServiceEntityList.isEmpty()}">
      <c:forEach items="${systemServiceEntityList}" var="systemServiceEntity">
        <div class="service-panel">
          <div class="service-name">${systemServiceEntity.name}</div>
          <div class="service-hr">
            <hr />
          </div>
          <div class="service-desc">
            <p>${systemServiceEntity.description}</p>
          </div>
          <div class="service-link">
            <a href="${systemServiceEntity.url}">进入</a>
          </div>
        </div>
      </c:forEach>
    </c:if>
  </div>
  <script type="text/javascript">
    var message = "${message}";
  </script>
  <script type="text/javascript" src="${contextPath}/js/index.js"></script>
</body>
</html>