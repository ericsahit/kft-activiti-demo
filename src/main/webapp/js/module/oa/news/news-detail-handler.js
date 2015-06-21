/**
 * 动态Form办理功能
 */
$(function() {

	$('.handle').click(handle);

});

/**
 * 打开办理对话框
 */
function handle() {
	var $ele = $(this);

	// 当前节点的英文名称
	var id = $(this).attr('nid');
	var title = $(this).attr('title');
	
   // alert(id);
	$('#handleTemplate').html('').dialog({
		modal: true,
		width: 500,
		//height: $.common.window.getClientHeight() / 2,
		height: 500,
		title: '[' + title + ']',
		open: function() {
			readFormFields.call(this, id);
		},
	});
}


/**
 * 读取表单字段
 */
function readFormFields(id) {
	var dialog = this;

	// 清空对话框内容
	//$(dialog).html("<form class='dynamic-form' method='post'><table class='dynamic-form-table'></table></form>");
	//var $form = $('.dynamic-form');

   alert("--->1");
	// 读取启动时的表单
   alert(ctx+"/oa/news/detail/"+id);
   $.get(ctx + '/oa/news/detail/' + id, function(datas) {
		//var trs = "";
		alert("--->2");
		alert("--->"+datas);
		
	});
}

/**
 * form对应的string/date/long/enum/boolean类型表单组件生成器
 * fp_的意思是form paremeter
 */
var formFieldCreator = {
	'string': function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			
			if (prop.id == "comment") {//备注字段需要显示成textarea
				result += "<td><textarea height='300' id='" + prop.id + "' name='fp_" + prop.id + "' class='" + className + "'>" + prop.value + "\r\n\r\n-------"+loginUser+"------\r\n</textarea>";
				//result += "<td><input type='textarea' height='400' id='" + prop.id + "' name='fp_" + prop.id + "' class='" + className + "' value='' />";
			} else {
				result += "<td><input type='text' id='" + prop.id + "' name='fp_" + prop.id + "' class='" + className + "' value='" + prop.value + "' />";
			}
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'date': function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><input type='text' id='" + prop.id + "' name='fp_" + prop.id + "' class='date " + className + "' value='" + prop.value + "'/>";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'enum': function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><select id='" + prop.id + "' name='fp_" + prop.id + "' class='" + className + "'>";
			$.each(datas[prop.id], function(k, v) {
				result += "<option value='" + k + "'>" + v + "</option>";
			});
			result += "</select>";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	},
	'users': function(prop, datas, className) {
		var result = "<td width='120'>" + prop.name + "：</td>";
		if (prop.writable === true) {
			result += "<td><input type='text' id='" + prop.id + "' name='fp_" + prop.id + "' class='" + className + "' value='" + prop.value + "' />";
		} else {
			result += "<td>" + prop.value;
		}
		return result;
	}
};

/**
 * 生成一个field的html代码
 */
function createFieldHtml(prop, className) {
	return formFieldCreator[prop.type.name](prop, className);
}