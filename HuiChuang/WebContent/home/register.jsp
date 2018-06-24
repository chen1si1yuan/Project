<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <link rel="stylesheet"
	href="${pageContext.request.contextPath}/layui/css/layui.css">
    <link rel="stylesheet" href="${ pageContext.request.contextPath }/home/public/css/login.css">
    <title>注册 - 慧享校园</title>
</head>

<body>

    <div class="login">
        <div class="login-main">
            <div class="login-header">
                <img class="logo-img" src="${ pageContext.request.contextPath }/home/public/image/logo.png" alt="">
            </div>
            <div class="layui-form">
                <div class="layui-form-item">
                    <label class="layui-form-label layui-icon layui-icon-cellphone"></label>
                    <input type="text" id="phone" name="mobile" required lay-verify="phone" placeholder="请输入手机号码" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-row">
                    <div class="layui-col-xs7">
                        <label class="layui-form-label layui-icon layui-icon-vercode"></label>
                        <input type="text" name="code" required lay-verify="code" placeholder="请输入验证码" autocomplete="off" class="layui-input">
                    </div>
                    <div class="layui-col-xs5">
                        <div class="getcode">
                            <button type="button" class="layui-btn layui-btn-fluid layui-btn-radius layui-btn-primary" id="getcode">获取验证码</button>
                        </div>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label layui-icon layui-icon-password"></label>
                    <input type="password" name="pwd" required lay-verify="pwd" placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label layui-icon layui-icon-password"></label>
                    <input type="password" name="repwd" required lay-verify="pwd" placeholder="请确认密码" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-item Agreement">
                    <input type="checkbox" name="agreement" lay-skin="primary" title="同意用户协议" checked="">
                    <div class="layui-unselect layui-form-checkbox layui-form-checked" lay-skin="primary">
                        <span>用户注册协议</span>
                        <i class="layui-icon layui-icon-ok"></i>
                    </div>
                </div>
                <div class="layui-form-item">
                    <button class="layui-btn layui-btn-fluid layui-btn-radius layui-btn-normal" lay-submit lay-filter="register">注 册</button>
                </div>
                <div class="layui-form-item login-more">
                    <a class="layui-text register" href="${ pageContext.request.contextPath }/login">立即登录</a>
                    <a class="layui-text forget" href="${ pageContext.request.contextPath }/forget">忘记密码？</a>
                </div>
            </div>
        </div>
        <div class="layui-trans login-footer">
            <p>Copyright © 2016-2018
                <a href="#" target="_blank">慧创科技</a>
            </p>
        </div>
    </div>






    <script src="${ pageContext.request.contextPath }/layui/layui.js"></script>
    <script src="${ pageContext.request.contextPath }/home/public/js/register.js"></script>

</body>

</html>










