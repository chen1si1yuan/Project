<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

</head>
<body>

<label id="recommend" ></label><label id="logstatu" ></label>
	<form action="${pageContext.request.contextPath }/aliyunpay" method="POST">
	请选择电话号码：<select name="buyphone" id="buyphone">
	</select>
		</form>
		<div id="ss" name="ss"></div>
		<button id="s" name="s" >pay</button>
	<script src="${pageContext.request.contextPath}/layui/layui.js"></script>
	<script type="text/javascript">
	//显示下拉菜单  显示所属这个流程的节点  
	//初始化  
	layui.use('jquery', function(){
		var $ = layui.jquery;
		
		window.onload=function(){  
			$.ajax({    
	            url: "recommendstatus",//调用方法    
	            type: "GET", 
	            dataType:"text",
	            success: function(data) {    
	            
	              $('#recommend').html(data+"<br>");//显示HTML    
	          }                 
	           }); 
			$.ajax({    
	            url: "logstatus",//调用方法    
	            type: "GET", 
	            dataType:"text",
	            success: function(data) {    
	            
	              $('#logstatu').html(data+"<br>");//显示HTML    
	          }                 
	           }); 
	        $.ajax({    
	            url: "showNodesByFlowNameOption",//调用方法    
	            type: "GET",    
	            data: {flowid:$("#flowid").val()},//参数    
	            dataType: "json",//类型    
	            success: function(data) {    
	              var tbHtml = "";//定义HTML    
	              tbHtml += "<option></option>";    
	              $.each(data, function(key, value) {//循环    
	                  tbHtml+="<option value="+value.phonenumber+">"+value.phonenumber+"</option>"//显示查询出来的结果内容    
	              });    
	                  
	              $('#buyphone').html(tbHtml);//显示HTML    
	          }                 
	           });  
	        
	     };    
	     $('#s').on('click', function() 
	    {
	    	 
	    	  $.ajax({    
	              url: "payphone",//调用方法    
	              type: "POST", 
	              dataType: 'json',
	              data:{buyphone:$("#buyphone").val()},
	              success: function(data) {    
	               if(data.status == "1")
	            	   {
	            	   
	            	   window.location.href='${pageContext.request.contextPath }/surePhone';
	            	   }
	               else
	            	   alert(data.status)
	            }                 
	             });  
	    	
	    })
	});
    
</script>
</body>

</html>