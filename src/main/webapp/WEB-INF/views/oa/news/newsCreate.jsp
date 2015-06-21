<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>请假申请</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(function() {
    	$('#startTime,#endTime').datetimepicker({
            stepMinute: 5
        });
    });
    //$('#fp_comment').val("-------"+ loginUser + "-------\r\n");
    //document.getElementById('comment').text = "txt2";
    // &nbsp; CurentTime3() -------&#13;&#10;
    //$('#comment').val("dgdgdg");
    //$('#customer').val("dgdgdg");
    
    function CurentTime3()
	{ 
	    var now = new Date();
	   
	    var year = now.getFullYear();       //年
	    var month = now.getMonth() + 1;     //月
	    var day = now.getDate();            //日
	   
	    var hh = now.getHours();            //时
	    var mm = now.getMinutes();          //分
	   
	    var clock = year + "-";
	   
	    if(month < 10)
	        clock += "0";
	   
	    clock += month + "-";
	   
	    if(day < 10)
	        clock += "0";
	       
	    clock += day + " ";
	   
	    if(hh < 10)
	        clock += "0";
	       
	    clock += hh + ":";
	    if (mm < 10) clock += '0'; 
	    clock += mm; 
	    return(clock); 
	}
    
    </script>
</head>

<body>
	<div class="container showgrid">
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">${message}</div>
		<!-- 自动隐藏提示信息 -->
		<script type="text/javascript">
		setTimeout(function() {
			$('#message').hide('slow');
		}, 5000);
		</script>
	</c:if>
	<c:if test="${not empty error}">
		<div id="error" class="alert alert-error">${error}</div>
		<!-- 自动隐藏提示信息 -->
		<script type="text/javascript">
		setTimeout(function() {
			$('#error').hide('slow');
		}, 5000);
		
		</script>
	</c:if>
	<script type="text/javascript">
    $(function() {
    	$('#create').button({
    		icons: {
    			primary: 'ui-icon-plus'
    		}
    	}).click(function() {
				if (!$('#title').val()) {
					alert('请填写名称！');
					$('#title').focus();
					return;
				}
                setTimeout(function() {
                    location.reload();
                }, 1000);
				$('#inputForm').submit();
    	});
    });
    function setShowLength(obj, maxlength, id) 
    { 
        var rem = maxlength - obj.value.length; 
        var wid = id; 
        if (rem < 0){ 
         rem = 0; 
        } 
        document.getElementById(wid).innerHTML = "还可以输入" + rem + "字数"; 
    } 
    </script>
	<form:form id="inputForm" action="${ctx}/oa/news/savenews" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>创建新闻</small></legend>
			<table border="1" >
			<tr>
				<td >标&nbsp;&nbsp;题：<input type="text" id="title" name="title" maxlength="50" style="width:300px;height:25px;" class="required"  onkeyup="javascript:setShowLength(this, 50, 'wordnum');" ><span id="wordnum" style="color:red">还可以输入50字数</span></td>				 
			</tr>
			<tr>
				 <script id="container" name="content" type="text/plain"></script>
                 <!-- 配置文件 -->
                  <script type="text/javascript" src="${ctx }/ueditor/ueditor.config.js"></script>
                <!-- 编辑器源码文件 -->
                <script type="text/javascript" src="${ctx }/ueditor/ueditor.all.js"></script>
                 <!-- 实例化编辑器 -->
                 <script type="text/javascript">
                 var ue = UE.getEditor('container',{initialFrameHeight:350,initialFrameWidth:1000 });
                 //ue.initialFrameHeight=500;
                </script>
			</tr>
			<tr>
				<td>
					<button type="button" id="create">提交订单</button>
				</td>
			</tr>
			
		</table>
		</fieldset>
	</form:form>
	</div>
</body>
</html>
