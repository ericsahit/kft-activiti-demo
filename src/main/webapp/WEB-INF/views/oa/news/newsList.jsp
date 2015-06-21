<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<title>新闻列表</title>
	<%@ include file="/common/meta.jsp" %>
    <%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/qtip/jquery.qtip.min.css" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/validate/jquery.validate.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/validate/messages_cn.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/blockui/jquery.blockUI.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/common.js" type="text/javascript"></script>
	<script src="${ctx }/js/module/activiti/workflow.js" type="text/javascript"></script>
	<script src="${ctx }/js/module/oa/news/news-detail-handler.js" type="text/javascript"></script>
	  <link href="${ctx }/js/common/plugins/datatables/css/jquery.dataTables.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${ctx }/js/common/plugins/datatables/js/jquery.dataTables.min.js"></script>
	<%-- <meta http-equiv="refresh" content="60"> --%>
	
    <script type="text/javascript">	
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
	 
<script type="text/javascript" > 
function p_del() { 
var msg = "您真的确定要删除吗？\n\n请确认！"; 
if (confirm(msg)==true){ 
return true; 
}else{ 
return false; 
} 
} 
</script> 
</head>

<body>
	<div style="height: 10px"></div>
	<table id="tbfinishlist" cellspacing="0">
	<thead>
		<tr>
			<th>新闻标题</th>
			<th>作者</th>
			<th>类型</th>
			<th>新闻ID</th>
			<th>创建时间</th>
			<th>删除</th>
			<th>编辑</th>
			
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${newsList}" var="news">
		 <tr>
		    <td><a class="handle" nid='${news.id}' title='${news.title}'} href="#" >${news.title}</a></td>
			<td>${news.author}</td>
			<td>${news.type }</td>
			<td>${news.id }</td>
			<td>${news.createTime }</td>
		    <td> <a href="${ctx}/oa/news/delete/${news.id}"  onclick="javascript:return p_del()">删除</a></td>
			<%-- <td><fmt:formatDate value="${hpi.endTime }" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></td> --%>
            <td> <a href="${ctx}/oa/news/edit/${news.id}">编辑</a></td>
		</tr>
		</c:forEach>
	<tbody>
	</table>
<%-- 	<tags:pagination page="${page}" paginationSize="${page.pageSize}"/> --%>
	<!-- 办理任务对话框 -->
	<div id="handleTemplate" class="template"></div>
	
</body>
</html>
