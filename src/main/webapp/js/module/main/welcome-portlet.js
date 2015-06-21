//待办事项中增加办理签收按钮
//对应后台处理在ActivitiController.java中

function formatDate(date, format) {   
    if (!date) return;   
    if (!format) format = "yyyy-MM-dd";   
    switch(typeof date) {   
        case "string":   
            date = new Date(date.replace(/-/, "/"));   
            break;   
        case "number":   
            date = new Date(date);   
            break;   
    }    
    if (!date instanceof Date) return;   
    var dict = {   
        "yyyy": date.getFullYear(),   
        "M": date.getMonth() + 1,   
        "d": date.getDate(),   
        "H": date.getHours(),   
        "m": date.getMinutes(),   
        "s": date.getSeconds(),   
        "MM": ("" + (date.getMonth() + 101)).substr(1),   
        "dd": ("" + (date.getDate() + 100)).substr(1),   
        "HH": ("" + (date.getHours() + 100)).substr(1),   
        "mm": ("" + (date.getMinutes() + 100)).substr(1),   
        "ss": ("" + (date.getSeconds() + 100)).substr(1)   
    };       
    return format.replace(/(yyyy|MM?|dd?|HH?|ss?|mm?)/g, function() {   
        return dict[arguments[0]];   
    });                   
}   


$(function() {
	$('#portlet-container').portlet({
		sortable: true,
		columns: [{
			width: 450,
			portlets: [{
				title: '待办任务',
				content: {
					style: {
						minHeight: 300
					},
					type: 'ajax',
					dataType: 'json',
					url: ctx + '/workflow/task/todo/list',
					formatter: function(o, pio, data) {
                        if (data.length == 0) {
                            return "无待办任务！";
                        }
						var ct = "<ol>";
						$.each(data, function() {
							ct += "<li>" + this.pdname + "->PID:" + this.pid + "-><span class='ui-state-highlight ui-corner-all'>" + this.name + "</span>";
							ct += "<span class='version' title='流程定义版本：" + this.pdversion + "'><b>V:</b>" + this.pdversion + "</span>";
							ct += "<a class='trace' href='#' pid='" + this.pid + "' title='点击查看流程图'>跟踪</a>";
							ct += "<a class='" 
								+ (this.status == 'claim' ? 'claim' : 'handle') + "' href='" 
								+ (this.status == 'claim' ? (ctx + '/form/dynamic/task/claimTodo/' + this.id) : '#') + "'  tid='" 
								+ this.id + "' tname='" 
								+ this.name + "' tkey='" 
								+ this.taskDefinitionKey + "' pid='" 
								+ this.pid + "'>" 
								+ (this.status == 'claim' ? '签收' : '办理') + "</a>";
							//ct += "<span class='status' title='任务状态'>" + (this.status == 'claim' ? '未签收' : '') + "</span>";
							ct += "</li>";
						});
						return ct + "</ol>";
					},
					afterShow: function() {
						$('.trace').click(graphTrace);
						$('.handle').click(handle);
					}
				}
			}, {
				title: '流程概念',
				content: {
					type: 'text',
					text: function() {
						return $('.workflow').html();
					}
				}
			}, {
                title: '任务概念',
                content: {
                    type: 'text',
                    text: function() {
                        return $('.task').html();
                    }
                }
            }]
		}, {
			width: 250,
			portlets: [{
				title: '系统功能',
				content: {
					type: 'text',
					text: function() {
						return $('.demos').html();
					}
				}
			}   ]
		}, {
			width: 500,
			portlets: [{
				title: '消息通知',
				content: {
					style: {
						minHeight: 300
					},
					type: 'ajax',
					dataType: 'json',
					url: ctx + '/oa/news/getlist',
					formatter: function(o, pio, data) {
                        if (data.length == 0) {
                            return "暂无消息！";
                        }
						var ct = "<ol>";
						$.each(data, function() {
							ct += "<li>";
							ct += "<a class='newsdetail' href='#' newsid='" + this.id + "' title='点击查看消息详情'>" + this.title + "</a>";
							ct += "<span class='version' title='作者'>" + this.author + "</span>";
							ct += "<span class='createtime' title='创建时间'>" + formatDate(this.createTime, "yyyy-MM-dd") + "</span>";
							//ct += "<span class='status' title='任务状态'>" + (this.status == 'claim' ? '未签收' : '') + "</span>";
							ct += "</li>";
						});
						return ct + "</ol>";
					},
					afterShow: function() {
						//$('.trace').click(graphTrace);
						$('.newsdetail').click(newsdetail);
					}
				}
			}, {
				title: '角色与用户',
				content: {
					type: 'text',
					text: function() {
						return $('.user').html();
					}
				}
			},  {
                title: 'APP客户端说明',
                content: {
                    type: 'text',
                    text: function() {
                        return $('.andriodapp').html();
                    }
                }
            }, {
				title: '技术支持',
				content: {
					type: 'text',
					text: function() {
						return $('.support').html();
					}
				}
			}
			]
		}]
	});
});



