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
	<form:form id="inputForm" action="${ctx}/oa/order/start" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>新订单</small></legend>
			<table border="1">
			<tr>
				<td>客&nbsp;&nbsp;户：</td>
				<td><input type="text" id="customer" name="fp_customer" class="required"><span style="color:red">*</span></td>
			</tr>
			<tr>
				<td>订&nbsp;单&nbsp;号：</td>
				<td><input type="text" id="orderId" name="fp_orderId" class="required"><span style="color:red">*</span></td>
			</tr>
			<tr>
				<td >发&nbsp;&nbsp;货：</td>
				<td><select id="needDriver" name="fp_needDriver" class="required">
					<option value="true">需要</option><option value="false">不需要</option></select><span style="color:red">*</span></td>
			</tr>
			<tr>
				<td>备&nbsp;&nbsp;注：</td>
				

				
				<td><textarea id="comment" name="fp_comment" class="">4</textarea>
				<input id="processType" name="processType" type="hidden" value="orderworkflow">
				
				
				<script type="text/javascript">
					//$('#customer').val("dgdgdg");
					$('#comment').text("-------" + loginUser + " " + CurentTime3() + "-------\r\n");
				</script>
				
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td>
					<button type="submmit">提交订单</button>
				</td>
			</tr>
			
		</table>
		</fieldset>
	</form:form>
	</div>
</body>
</html>
