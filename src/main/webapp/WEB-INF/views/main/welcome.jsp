<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="me.kafeitu.demo.activiti.util.PropertyFileUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
	<%@ include file="/common/global.jsp"%>
	<%@ include file="/common/meta.jsp"%>

	<%@ include file="/common/include-base-styles.jsp" %>
    <%@ include file="/common/include-jquery-ui-theme.jsp" %>
    <link href="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/jui/extends/portlet/jquery.portlet.min.css?v=1.1.2" type="text/css" rel="stylesheet" />
    <link href="${ctx }/js/common/plugins/qtip/jquery.qtip.css?v=1.1.2" type="text/css" rel="stylesheet" />
    <%@ include file="/common/include-custom-styles.jsp" %>
    <style type="text/css">
    	.template {display:none;}
    	.version {margin-left: 0.5em; margin-right: 0.5em;}
    	.trace {margin-right: 0.5em;}
        .center {
            width: 1200px;
            margin-left:auto;
            margin-right:auto;
        }
    </style>

    <script src="${ctx }/js/common/jquery-1.8.3.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/jquery-ui-${themeVersion }.min.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/portlet/jquery.portlet.pack.js?v=1.1.2" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/jui/extends/timepicker/jquery-ui-timepicker-addon.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/jui/extends/i18n/jquery-ui-date_time-picker-zh-CN.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/validate/jquery.validate.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/validate/messages_cn.js" type="text/javascript"></script>
    <script src="${ctx }/js/common/plugins/qtip/jquery.qtip.pack.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/html/jquery.outerhtml.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/plugins/blockui/jquery.blockUI.js" type="text/javascript"></script>
	<script src="${ctx }/js/common/common.js" type="text/javascript"></script>
	    <script type="text/javascript">
        var processType = '${empty processType ? param.processType : processType}';
    </script>
	<script src="${ctx }/js/module/activiti/workflow.js" type="text/javascript"></script>
    <script src="${ctx }/js/module/main/welcome-portlet.js" type="text/javascript"></script>
    <script src="${ctx }/js/module/main/todo-handler.js" type="text/javascript"></script>
    
</head>
<body style="margin-top: 1em;">
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">${message}</div>
		<!-- 自动隐藏提示信息 -->
		<script type="text/javascript">
		setTimeout(function() {
			$('#message').hide('slow');
		}, 5000);
		</script>
	</c:if>
	<div class="center">
        <div style="text-align: center;">
            <h3>欢迎访问工作流系统</h3>
        </div>
        <div id='portlet-container'></div>
    </div>
    <!-- 隐藏 -->
    <div class="workflow template">
        <ul>
            <li>
                <b>流程的概念</b>：工作流系统中的基本运行单位，可以是请假和销售流程等。
            </li>
            <li>
                <b>定义和部署</b>：本系统支持根据公司业务来新增流程，新增的流程通过设计工具设计被定义和部署后，方可以使用。
            </li>
            <li>
                <b>流程的启动</b>：流程可以在<b>流程列表</b>中启动。启动一个流程即开始工作流的一个实例，例如客服新接到订单后启动一个订单的工作流。
            </li>
            <li>
                <b>流程的查看</b>：可以在<b>运行中流程</b>和<b>已完成流程</b>中查看正在运行的流程和已完成的流程实例，运行中的流程可以查看到当前进行中的节点。
            </li>
        </ul>
    </div>
    <div class="task template">
    用户任务指的是流程运行过程中分配到相应角色的任务，当前用户可以在<b>任务列表</b>查看分配到自身角色的任务，通过<b>签收任务</b>来接收任务（因为同一角色的用户可能有多个）。
    完成任务后需要在<b>任务列表</b>中填写信息置任务完成。完成的任务会在工作流中自动进行下一环节的任务分配或任务结束。
    </div>
    <div class="project-info template">
        <ul>
            <li><a target="_blank" href='https://github.com/henryyan/kft-activiti-demo'>kft-activiti-demo</a>为Activiti初学者快速入门所设计。</li>
            <li>源代码托管Github：<a target="_blank" href='https://github.com/henryyan/kft-activiti-demo'>https://github.com/henryyan/kft-activiti-demo</a></li>
        </ul>
    </div>

    <div class="arch template">
        <ul>
            <li>
                Activiti版本：公共版本（${prop['activiti.version']}）
                <c:if test="${prop['activiti.version'] != prop['activiti.engine.version']}">&nbsp;引擎<strong>特定</strong>版本（${prop['activiti.engine.version']}）</c:if>
            </li>
            <li>Spring版本：${prop['spring.version']}</li>
            <li>Hibernate：${prop['hibernate.version']}</li>
            <li>使用<a href="http://maven.apache.org" target="_blank">Maven</a>管理依赖</li>
        </ul>
    </div>

    <div class="demos template">
        <ul>
        	<li>定制的订单流程</li>
        	<li>设计流程</li>
            <li>部署流程</li>
            <li>启动流程</li>
            <li>任务签收</li>
            <li>任务办理</li>
            <li>查询运行中流程</li>
            <li>查询历史流程</li>
            <li>个人待办任务汇总</li>
        </ul>
    </div>

    <div class="links template">
        <p>
            <b>Demo<span style="color: red">Wiki</span>：</b><a target="_blank" href="https://github.com/henryyan/kft-activiti-demo/wiki">https://github.com/henryyan/kft-activiti-demo/wiki</a>
        </p>
        <p>
            <b>Demo<span style="color: red">源码</span>：</b><a target="_blank" href="https://github.com/henryyan/kft-activiti-demo">https://github.com/henryyan/kft-activiti-demo</a>
        </p>
        <p>
            <b>Activiti<span style="color: red">资料</span>：</b><a target="_blank" href="http://www.kafeitu.me/categories.html#activiti-ref">http://www.kafeitu.me/categories.html#activiti-ref</a>
        </p>
        <p>
            <b>Activiti<span style="color: red">中文论坛</span>：</b><a target="_blank" href="http://www.activiti-cn.org">http://www.activiti-cn.org</a>
        </p>
    </div>

    <div class="aboutme template">
        <ul>
            <li>
                <b>作者：</b><a target="_blnak" href="http://www.kafeitu.me/?f=kad">咖啡兔</a>
            </li>
            <li>
                <b>QQ：</b>576525789
            </li>
            <li>
                <b>Weibo：</b><a target="_blank" href="http://weibo.com/kafeituzi">@kafeituzi</a>
            </li>
            <li>
                <b>QQ群：</b>
                <ul>
                    <li>Activiti中文群1(<span style="color:green;font-weight: bold">欢迎</span>)：236540304</li>
                    <li>Activiti中文群2(<span style="color:red">已满</span>)：23539326</li>
                    <li>Activiti中文群3(<span style="color:red">已满</span>)：139983080</li>
                    <li>Activiti中文群4(<span style="color:red">已满</span>)：327913744</li>
                </ul>
            </li>
        </ul>
    </div>

    <div class="rest template">
        <p>在web.xml中映射了两个两个Servlet</p>
        <dl>
            <dt>ExplorerRestletServlet</dt>
            <dd>
                <p>针对Activiti Modeler的Rest接口，映射路径：/service/*</p>
            </dd>
            <dt>RestletServlet</dt>
            <dd>
                <p>官方提供的完整Rest接口</p>
                <p>访问路径：http://localhost/yourappname/<用户手册提供的Rest地址></p>
                <p>映射路径：/rest/*</p>
                <p><a href="http://www.kafeitu.me/activiti/2013/01/12/kft-activiti-demo-rest.html">如何使用Activiti Rest模块</a></p>
            </dd>
        </dl>
    </div>
    
    <!-- 办理任务对话框 -->
	<div id="handleTemplate" class="template"></div>
</body>
</html>
