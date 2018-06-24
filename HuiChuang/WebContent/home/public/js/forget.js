layui.use(['jquery', 'form'], function() {
	var $ = layui.jquery;
	var form = layui.form;
	form.verify({
		pwd: [/^[\S]{6,}$/, '密码最少6位，且不能出现空格'],
		code: [/\S/, '验证码不能为空']
	});
	$('#getcode').on('click', function() {
		var mobile = $('#phone').val()
		if(mobile == '') {
			layer.msg('手机号码不能为空', {
				icon: 5
			})
		} else if(mobile.length != 11) {
			layer.msg('请正确输入手机号码', {
				icon: 5
			})
		} else {
			function invokeSettime(obj) {
				var countdown = 60
				settime(obj)

				function settime(obj) {
					if(countdown == 0) {
						$(obj).attr('disabled', false)
						$(obj).text('获取验证码')
						countdown = 60
						return
					} else {
						$(obj).attr('disabled', true)
						$(obj).addClass('layui-btn-disabled')
						$(obj).text(countdown + 'S后重试')
						countdown--
					}
					setTimeout(function() {
						settime(obj)
					}, 1000)
				}
			}
			$.ajax({
				url: '/HuiChuang/getcode',
				type: 'GET',
				dataType: 'JSON',
				data: {mobile:mobile},
				success: function(res) {
					if(res.status == '1') {
						layer.msg('发送成功')
						new invokeSettime('#getcode')
					} else {
						layer.msg('发送失败，请重试', {
							icon: 5
						})
					}
				},
				error: function(err) {
					layer.msg('发送失败，请重试', {
						icon: 5
					})
				}
			})
		}
	});
	//监听提交
	form.on('submit(forget)', function(data) {
		var field = data.field
		if(field.pwd !== field.repwd) {
			return layer.msg('两次输入的密码不一致')
		}
		$.ajax({
			url: '/HuiChuang/forget',
			type: 'POST',
			dataType: 'JSON',
			data: field,
			success: function(res) {
				if(res.status == '1') {
					layer.msg('修改成功', {
						icon: 1,
						time: 1000
					}, function() {
						window.location.href = '/HuiChuang/login'
					})
				} else {
					layer.msg(res.msg, {
						icon: 5
					})
				}
			},
			error: function(err) {
				layer.msg('修改失败，请重试', {
					icon: 5
				})
			}
		})
		return false
	});
})