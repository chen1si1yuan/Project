layui.use(['form', 'jquery'], function() {
  var form = layui.form;
  var $ = layui.jquery;
  form.verify({
    password: [/^[\S]{6,}$/, '请输入密码']
  })
  //监听提交
  form.on('submit(login)', function(data) {
    console.log(data)
    console.log(data.field)
    var field = data.field
    $.ajax({
      url: '/HuiChuang/adminlogin',
      type: 'POST',
      dataType: 'JSON',
      data: field,
      success: function(res) {
        if (res.status == '1') {
          layer.msg(
            '登录成功',
            {
              icon: 1,
              time: 1000
            },
            function() {
              window.location.href = '/HuiChuang/admin'
            }
          )
        } else {
          layer.msg(res.msg, {
            icon: 5
          })
        }
      },
      error: function(err) {
        layer.msg('登录失败，请重试', {
          icon: 5
        })
      }
    })
    return false
  })
})
