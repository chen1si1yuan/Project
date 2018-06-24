<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
response.setHeader("X-Frame-Options", "SAMEORIGIN");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">


<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/layui/css/layui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/admin/public/css/main.css" />
<title>首页</title>
</head>

<body>
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md3">
				<div class="layui-card">
					<div class="index_title one_bg">
						<i class="layui-icon layui-icon-friends"></i>
						<p>一级分销商</p>
					</div>
					<div class="info">
						<h1 id='info1'>385</h1>
					</div>
				</div>
			</div>
			<div class="layui-col-md3">
				<div class="layui-card">
					<div class="index_title two_bg">
						<i class="layui-icon layui-icon-group"></i>
						<p>二级分销商</p>
					</div>
					<div class="info">
						<h1 id='info2'>1064</h1>
					</div>
				</div>
			</div>
			<div class="layui-col-md3">
				<div class="layui-card">
					<div class="index_title three_bg">
						<i class="layui-icon layui-icon-add-circle"></i>
						<p>今日新增用户</p>
					</div>
					<div class="info">
						<h1 id='info3'>1639</h1>
					</div>
				</div>
			</div>
			<div class="layui-col-md3">
				<div class="layui-card">
					<div class="index_title four_bg">
						<i class="layui-icon layui-icon-cellphone"></i>
						<p>已售号卡</p>
					</div>
					<div class="info">
						<h1 id='info4'>1876</h1>
					</div>
				</div>
			</div>
		</div>

		<!--图表组件放置-->
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">数据概览</div>
					<div class="layui-card-body">
						<div id="data1"
							style="width: 100%; height: 280px; margin-top: 20px;"></div>
					</div>
				</div>
			</div>
		</div>

	</div>

	<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin/public/lib/echarts.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin/public/lib/macarons.js"></script>
	<script
		src="${pageContext.request.contextPath}/admin/public/js/welcome.js"></script>
</body>

</html>