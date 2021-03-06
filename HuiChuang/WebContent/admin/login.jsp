<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Fram
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/admin/public/css/login.css">
    <title>登录 - 数据管理平台 - 慧享校园</title>
</head>

<body>

    <div class="login">
        <div class="login-main">
            <div class="login-header">
                <img class="logo-img" src="${pageContext.request.contextPath}/admin/public/image/logo.png" alt="">
                <h2>攀枝花慧创科技</h2>
                <p>2018年秋季联通销售数据管理平台</p>
            </div>
            <div class="login-content">
                <div class="layui-form">
                    <div class="layui-form-item">
                        <label class="layui-form-label layui-icon layui-icon-username"></label>
                        <input type="text" name="username" required lay-verify="phone" placeholder="请输入手机号码" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label layui-icon layui-icon-password"></label>
                        <input type="password" name="password" required lay-verify="password" placeholder="请输入密码" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <button class="layui-btn layui-btn-fluid layui-btn-radius layui-btn-normal" lay-submit lay-filter="login">登 录</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-trans login-footer">
            <p>Copyright © 2016-2018
                <a href="#" target="_blank">慧创科技</a>
            </p>
        </div>
    </div>
     <script src="${pageContext.request.contextPath}/layui/layui.js"></script>
    <script src="${pageContext.request.contextPath}/admin/public/js/login.js"></script>

</body>

</html>