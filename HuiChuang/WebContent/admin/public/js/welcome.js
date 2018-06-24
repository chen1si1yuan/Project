layui.use(['jquery', 'layer'], function() {
	var $ = layui.jquery;
	var layer = layui.layer;
	var load = layer.load(2);
	$.ajax({
		url: '/HuiChuang/getData',
//		url: 'http://192.168.1.104:8080/HuiChuang/getData',
		type: 'GET',
		dataType: 'JSON',
		success: function(res) {
				layer.msg('数据获取成功', {
					icon: 1
				})
				layer.close(load)
				$('#info1').text(res.firstgrade)
				$('#info2').text(res.seccendgrage)
				$('#info3').text(res.todayregister)
				$('#info4').text(res.sellphone)
		},
		error: function(err) {
			layer.msg('数据获取失败，请重试', {
				icon: 5
			})
		}
	})
});

var myChart1 = echarts.init(document.getElementById('data1'), 'macarons');

var data1 = {
	title: {
		text: '今日用户流量增长趋势',
		x: 'center',
		textStyle: {
			fontSize: 18
		}
	},
	tooltip: {
		trigger: 'axis'
	},
	legend: {
		data: ['', '']
	},
	xAxis: [{
		type: 'category',
		boundaryGap: false,
		data: ['06:00', '06:30', '07:00', '07:30', '08:00', '08:30', '09:00', '09:30', '10:00', '11:30', '12:00', '12:30', '13:00', '13:30', '14:00', '14:30', '15:00', '15:30', '16:00', '16:30', '17:00', '17:30', '18:00', '18:30', '19:00', '19:30', '20:00', '20:30', '21:00', '21:30', '22:00', '22:30', '23:00', '23:30']
	}],
	yAxis: [{
		type: 'value'
	}],
	series: [{
		name: 'PV',
		type: 'line',
		smooth: true,
		itemStyle: {
			normal: {
				areaStyle: {
					type: 'default'
				}
			}
		},
		data: [111, 222, 333, 444, 555, 666, 3333, 33333, 55555, 66666, 33333, 3333, 6666, 11888, 26666, 38888, 56666, 42222, 39999, 28888, 17777, 9666, 6555, 5555, 3333, 2222, 3111, 6999, 5888, 2777, 1666, 999, 888, 777]
	}, {
		name: 'UV',
		type: 'line',
		smooth: true,
		itemStyle: {
			normal: {
				areaStyle: {
					type: 'default'
				}
			}
		},
		data: [11, 22, 33, 44, 55, 66, 333, 3333, 5555, 12666, 3333, 333, 666, 1188, 2666, 3888, 6666, 4222, 3999, 2888, 1777, 966, 655, 555, 333, 222, 311, 699, 588, 277, 166, 99, 88, 77]
	}]
};

myChart1.setOption(data1);