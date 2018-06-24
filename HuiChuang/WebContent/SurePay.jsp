<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>${requestScope.message}</h1>
<%if(request.getAttribute("message")==null) {%>
<form action="${pageContext.request.contextPath}/aliyunpay">
订单号：<h3>${requestScope.out_trade_no}</h3>
购买号码：<h3>${requestScope.buyphone}</h3>
商品名称：<h3>${requestScope.subject}</h3>
商品金额：<h3>${requestScope.total_amount}</h3>
<input type="submit" value="submit" >
</form>
<%} %>
</body>
</html>