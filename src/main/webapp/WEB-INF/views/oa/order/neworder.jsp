<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>启动订单工作流</title>
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
			<legend><small>客户下单</small></legend>
			<table border="1">
			<tr>
				<td>品&nbsp;&nbsp;类：</td>
				<td><input type="text" id="custCarKind" name="custCarKind" /></td>
			</tr>
			<tr>
				<td>车&nbsp;&nbsp;型：</td>
				<td><input type="text" id="custCarModel" name="custCarModel" /></td>
			</tr>
			<tr>
				<td>数&nbsp;&nbsp;量：</td>
				<td><input type="text" id="custCarNumber" name="custCarNumber" /></td>
			</tr>
			<tr>
				<td>颜&nbsp;&nbsp;色：</td>
				<td><input type="text" id="custCarColor" name="custCarColor" /></td>
			</tr>
			<tr>
				<td>图&nbsp;&nbsp;片：</td>
				<td><input type="file" name="custFile" /></td>
			</tr>			
			<tr>
				<td>备&nbsp;&nbsp;注：</td>
				<td>
					<textarea name="custNotice"></textarea>
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
