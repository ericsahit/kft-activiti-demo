<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>已结束列表</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/datatables/css/jquery.dataTables.min.css" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>
    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctx }/js/common/plugins/datatables/js/jquery.dataTables.min.js"></script>
    
    <script type="text/javascript">
	
// 	$(document).ready(function() {
// 		$('#tbfinishlist').DataTable();
// 	} );
	
	$(document).ready(function() {
	    $('#tbfinishlist').dataTable( {
	    	//"bJQueryUI" : true,
	    	
            "oLanguage": { //国际化配置  
                "sProcessing" : "正在获取数据，请稍后...",    
                "sLengthMenu" : "显示 _MENU_ 条",    
                "sZeroRecords" : "没有您要搜索的内容",    
                "sInfo" : "从 _START_ 到  _END_ 条记录 总记录数为 _TOTAL_ 条",    
                "sInfoEmpty" : "记录数为0",    
                "sInfoFiltered" : "(全部记录数 _MAX_ 条)",    
                "sInfoPostFix" : "",    
                "sSearch" : "搜索",    
                "sUrl" : "",    
                "oPaginate": {    
                    "sFirst" : "第一页",    
                    "sPrevious" : "上一页",    
                    "sNext" : "下一页",    
                    "sLast" : "最后一页"    
                }
            }
	    } );
	} );
	
	</script>
</head>

<body>
	<div style="height: 10px"></div>
	<table id="tbfinishlist" cellspacing="0">
	<thead>
		<tr>
			<th>流程实例ID</th>
			<th>客户</th>
			<th>订单号</th>
			<th>创建人</th>
			<th>流程启动时间</th>
			<th>流程结束时间</th>
			<th>流程结束原因</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.result }" var="hpi">
		<tr title="${hpi.businessKey }">
			<td>${hpi.id }</td>
			<td>${hpi.startActivityId }</td>
			<td>${hpi.endActivityId }</td>
			<td>${hpi.startUserId }</td>
			<td><fmt:formatDate value="${hpi.startTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${hpi.endTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td>${empty hpi.deleteReason ? "正常结束" : hpi.deleteReason}</td>
<%-- 			<td style="visibility: hidden;">${hpi.businessKey }</td> --%>
		</tr>
		</c:forEach>
	<tbody>
	</table>
<%-- 	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/> --%>
	<!-- 办理任务对话框 -->
	<div id="handleTemplate" class="template"></div>

</body>
</html>
