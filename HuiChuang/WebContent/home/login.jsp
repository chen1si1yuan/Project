<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/layui/css/layui.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/home/public/css/login.css">
<title>登录 - 慧享校园</title>
</head>

<body>

	<div class="login">
		<div class="login-main">
			<div class="login-header">
				<img class="logo-img"
					src="${pageContext.request.contextPath}/home/public/image/logo.png"
					alt="">
			</div>
			<div class="login-content">
				<form class="layui-form"
					action="${pageContext.request.contextPath}/login" method="POST">
					<div class="layui-form-item">
						<label class="layui-form-label layui-icon layui-icon-username"></label>
						<input type="text" name="username" required lay-verify="phone"
							placeholder="请输入手机号码" autocomplete="off" class="layui-input" value="">
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label layui-icon layui-icon-password"></label>
						<input type="password" name="password" required
							lay-verify="password" placeholder="请输入密码" autocomplete="off"
							class="layui-input">
					</div>
					<div class="layui-form-item">
						<button
							class="layui-btn layui-btn-fluid layui-btn-radius layui-btn-normal"
							lay-submit lay-filter="login">登 录</button>
					</div>
				</form>
				<div class="layui-form-item login-more">
					<a class="layui-text register"
						href="${pageContext.request.contextPath}/register">注册账户</a>
					<a class="layui-text forget"
						href="${pageContext.request.contextPath}/forget">忘记密码？</a>
				</div>
			</div>
		</div>
		<div class="layui-trans login-footer">
			<p>
				Copyright © 2016-2018 <a href="#" target="_blank">慧创科技</a>
			</p>
		</div>
	</div>





		<script
		src="${pageContext.request.contextPath}/layui/layui.js"></script>
	<script>
		//Demo
		layui.use('form', function() {
			var form = layui.form;
			form.verify({
				password : [ /^[\S]{6,}$/, '密码最少6位，且不能出现空格' ]
			});
			${requestScope.error};
			//监听提交
			// form.on('submit(formDemo)', function (data) {
			//     layer.msg(JSON.stringify(data.field));
			//     return false;
			// });
		});
	</script>
</body>

</html>


</html>
