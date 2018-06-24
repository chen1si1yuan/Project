<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta charset="utf-8">
		<meta name="renderer" content="webkit">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/admin/public/css/main.css">
		
		<title>首页 - 数据管理平台 - 慧享校园</title>
	</head>

	<body>
		<div class="layui-layout layui-layout-admin">

			<!-- 头部导航 -->
			<div class="layui-header layui-bg-black">
				<div class="layui-logo">
					<i class="layui-icon layui-icon-app"></i> 销售数据管理平台
				</div>
				<!--<ul class="layui-nav layui-layout-left">
					<li class="layui-nav-item">
						<a href="">
							<i class="layui-icon layui-icon-console"></i> 首页
						</a>
					</li>
					<li class="layui-nav-item">
						<a href="">数据管理</a>
					</li>
					<li class="layui-nav-item">
						<a href="">用户查询</a>
					</li>
				</ul>-->
				<p class="layui-layout-left" style="line-height: 60px;"><i class="layui-icon layui-icon-notice"></i>&nbsp;<span id="">赵小雷</span>您好，欢迎登陆销售数据管理平台！</p>


				<ul class="layui-nav layui-layout-right">
					<li class="layui-nav-item">
						<a href="javascript:;">
							<img class="layui-nav-img" src="http://t.cn/RCzsdCq"> 赵小雷
						</a>
						<dl class="layui-nav-child layui-anim layui-anim-upbit">
							<dd><a href="#">基本资料</a></dd>
							<dd><a href="#">安全设置</a></dd>
							<dd><a href="./adminlogin.html">退出</a></dd>
						</dl>
					</li>
				</ul>
			</div>
			<!-- 侧边导航 -->
			<div class="layui-side layui-side-menu layui-bg-black">
				<div class="layui-side-scroll">
					<ul class="layui-nav layui-nav-tree layui-bg-black">
						<li class="layui-nav-item layui-nav-itemed">
							<a href="javascript:;">分销商管理</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="javascript:;" data-url="fenxiao" data-title="分销商查询" data-id="1" class="site-demo-active" data-type="tabAdd">
										<i class="layui-icon layui-icon-search"></i> 分销商查询</a>
								</dd>
								<dd>
									<a href="javascript:;" data-url="fenxiaoadd" data-title="分销商添加" data-id="2" class="site-demo-active" data-type = "tabAdd">
										<i class="layui-icon layui-icon-add-1"></i> 分销商添加
									</a>
								</dd>
							</dl>
						</li>
						<li class="layui-nav-item">
							<a href="javascript:;">销售数据管理</a>
							<dl class="layui-nav-child">
								<dd>
									<a href="">
										<i class="layui-icon layui-icon-search"></i> 数据查询
									</a>
								</dd>
								<dd>
									<a href="">
										<i class="layui-icon layui-icon-share"></i> 数据导出
									</a>
								</dd>
							</dl>
						</li>
						<li class="layui-nav-item">
							<a href="javascript:;">产品</a>
						</li>
						<li class="layui-nav-item">
							<a href="javascript:;" data-url="about" data-title="关于我们" data-id="3" class="site-demo-active" data-type="tabAdd">
										<i class="layui-icon layui-icon-tips"></i> 关于我们</a>
						</li>
					</ul>
				</div>
			</div>

			<!-- 内容主体 -->
			<div class="layui-body">
				<!--tab 切换-->
				<div class="layui-tab layui-tab-brief main-layout-tab" lay-filter="demo" lay-allowClose="true">
					<ul class="layui-tab-title">
						<li class="layui-this welcome">后台主页</li>
					</ul>
					<div class="layui-tab-content">
						<div class="layui-tab-item layui-show" style="background: #f5f5f5;">
							<!--1-->
							<iframe src='${pageContext.request.contextPath}/admin/welcome.jsp' frameborder="0" class="layadmin-iframe"></iframe>
							<!--1end-->
						</div>
					</div>
				</div>
			</div>

			<div class="layui-footer">
				<p style="text-align: center">Copyright © 2016-2018 慧创科技</p>
			</div>
		</div>
	<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
		<script src="${pageContext.request.contextPath}/admin/public/js/main.js"></script>
	
	</body>

</html>