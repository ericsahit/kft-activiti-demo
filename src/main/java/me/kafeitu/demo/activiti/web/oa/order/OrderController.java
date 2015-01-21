package me.kafeitu.demo.activiti.web.oa.order;

import me.kafeitu.demo.activiti.entity.oa.Leave;
import me.kafeitu.demo.activiti.service.oa.leave.LeaveManager;
import me.kafeitu.demo.activiti.service.oa.leave.LeaveWorkflowService;
import me.kafeitu.demo.activiti.util.Page;
import me.kafeitu.demo.activiti.util.PageUtil;
import me.kafeitu.demo.activiti.util.UserUtil;
import me.kafeitu.demo.activiti.util.Variable;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ActivitiTaskAlreadyClaimedException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricDetailQuery;
import org.activiti.engine.history.HistoricFormProperty;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.form.StartFormDataImpl;
import org.activiti.engine.impl.form.TaskFormDataImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * 请假控制器，包含保存、启动流程
 *
 * @author HenryYan
 */
@Controller
@RequestMapping(value = "/oa/order")
public class OrderController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FormService formService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RuntimeService runtimeService;
    
    @RequestMapping(value = {"apply", ""})
    public String orderApply() {
        return "/oa/order/orderApply";
    }
    
    /**
     * 提交启动流程
     */
    @RequestMapping(value = "start", method = RequestMethod.POST)
    @SuppressWarnings("unchecked")
    public String submitStartFormAndStartProcessInstance(
                                                         @RequestParam(value = "processType", required = false) String processType,
                                                         RedirectAttributes redirectAttributes,
                                                         HttpServletRequest request) {
        Map<String, String> formProperties = new HashMap<String, String>();

        // 从request中读取参数然后转换
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            String key = entry.getKey();

            // fp_的意思是form paremeter
            if (StringUtils.defaultString(key).startsWith("fp_")) {
                formProperties.put(key.split("_")[1], entry.getValue()[0]);
            }
        }

        logger.debug("start form parameters: {}", formProperties);

        User user = UserUtil.getUserFromSession(request.getSession());
        // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
        if (user == null || StringUtils.isBlank(user.getId())) {
            return "redirect:/login?timeout=true";
        }
        boolean isError = false;
        String msg = "";
        try {
            identityService.setAuthenticatedUserId(user.getId());
            
            ProcessDefinitionQuery query1 = repositoryService.createProcessDefinitionQuery().processDefinitionKey("orderworkflow").active().orderByProcessDefinitionVersion().desc();
            List<ProcessDefinition> list = query1.list();
            String processDefinitionId = "";
            if (list.size() > 0) {
            	processDefinitionId = list.get(0).getId();
            } else {
            	isError = true;
            	msg = "启动失败，查找不到订单工作流实例，请联系管理员";
            }
            
            ProcessInstance processInstance = formService.submitStartFormData(processDefinitionId, formProperties);
            logger.debug("start a processinstance: {}", processInstance);
            msg = "启动成功，流程ID：" + processInstance.getId();
        } catch (ActivitiException e) {
            if (e.getMessage().indexOf("no processes deployed with key") != -1) {
                logger.warn("没有部署流程!", e);
                isError = true;
                msg = "没有部署流程，请在[工作流]->[流程管理]页面点击<重新部署流程>";
            } else {
                logger.error("启动请假流程失败：", e);
                isError = true;
                msg = "系统内部错误！";
            }
        } catch (Exception e) {
        	isError = true;
            logger.error("启动请假流程失败：", e);
        }
        finally {
            identityService.setAuthenticatedUserId(null);
        }
        
        if (isError) {
        	redirectAttributes.addFlashAttribute("error", msg);
        } else {
        	redirectAttributes.addFlashAttribute("message", msg);
        }

        return "redirect:/oa/order/apply";
        //return "redirect:/form/dynamic/process-list?processType=" + processType;
    }
    
    /**
     * task列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "task/list")
    public ModelAndView taskList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/oa/order/task-list");
        User user = UserUtil.getUserFromSession(request.getSession());
        // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return new ModelAndView("redirect:/login?timeout=true"); 
        }

        List<Task> tasks = new ArrayList<Task>();
        
    	tasks = taskService.createTaskQuery().processDefinitionKey("orderworkflow")
                .taskCandidateOrAssigned(user.getId()).active().orderByTaskId().desc().list();
        
        int newCount = 0;
        Object obj = request.getSession().getAttribute("lastTodolist");
        if (obj != null) {
        	List<Task> oldTasks = (List<Task>)obj;
        	for (Task newtask : tasks) {
        		boolean ifExist = false;
            	for (Task oldtask : oldTasks) {
            		if (newtask.getId().equals(oldtask.getId())) {
            			ifExist = true;
            			break;
            		}
            	}
            	
            	if (!ifExist) newCount++;
            	
        	}
        } else {
        	newCount = tasks.size();
        }
        
        for (Task task : tasks) {
        	getOrderForm(task);
        }
        
        //newCount = 10;//**** for test
        if (newCount > 0) {
        	request.getSession().setAttribute("lastTodolist", tasks);
        	mav.addObject("newCount", newCount);
        	mav.addObject("message", "您有"+newCount+"条新的任务！");
        }

        mav.addObject("tasks", tasks);
        return mav;
    }
    
    /**
     * 初始化启动流程，读取启动流程的表单字段来渲染start form
     */
    @RequestMapping(value = "get-form/start/{processDefinitionId}")
    @ResponseBody
    @SuppressWarnings("unchecked")
    public Map<String, Object> findStartForm(@PathVariable("processDefinitionId") String processDefinitionId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        StartFormDataImpl startFormData = (StartFormDataImpl) formService.getStartFormData(processDefinitionId);
        startFormData.setProcessDefinition(null);

    /*
     * 读取enum类型数据，用于下拉框
     */
        List<FormProperty> formProperties = startFormData.getFormProperties();
        for (FormProperty formProperty : formProperties) {
            Map<String, String> values = (Map<String, String>) formProperty.getType().getInformation("values");
            if (values != null) {
                for (Entry<String, String> enumEntry : values.entrySet()) {
                    logger.debug("enum, key: {}, value: {}", enumEntry.getKey(), enumEntry.getValue());
                }
                result.put("enum_" + formProperty.getId(), values);
            }
        }

        result.put("form", startFormData);

        return result;
    }

    /**
     * 读取Task的表单
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "get-form/task/{taskId}")
    @ResponseBody
    public Map<String, Object> findTaskForm(@PathVariable("taskId") String taskId) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        TaskFormDataImpl taskFormData = (TaskFormDataImpl) formService.getTaskFormData(taskId);

        // 设置task为null，否则输出json的时候会报错
        taskFormData.setTask(null);

        result.put("taskFormData", taskFormData);
    /*
     * 读取enum类型数据，用于下拉框
     */
        List<FormProperty> formProperties = taskFormData.getFormProperties();
        for (FormProperty formProperty : formProperties) {
            Map<String, String> values = (Map<String, String>) formProperty.getType().getInformation("values");
            if (values != null) {
                for (Entry<String, String> enumEntry : values.entrySet()) {
                    logger.debug("enum, key: {}, value: {}", enumEntry.getKey(), enumEntry.getValue());
                }
                result.put(formProperty.getId(), values);
            }
        }

        return result;
    }

    /**
     * 办理任务，提交task的并保存form
     */
    @RequestMapping(value = "task/complete/{taskId}")
    @SuppressWarnings("unchecked")
    public String completeTask(@PathVariable("taskId") String taskId, 
                               RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Map<String, String> formProperties = new HashMap<String, String>();

        // 从request中读取参数然后转换
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            String key = entry.getKey();

            // fp_的意思是form paremeter
            if (StringUtils.defaultString(key).startsWith("fp_")) {
                formProperties.put(key.split("_")[1], entry.getValue()[0]);
            }
        }

        logger.debug("start form parameters: {}", formProperties);

        User user = UserUtil.getUserFromSession(request.getSession());

        // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
        if (user == null || StringUtils.isBlank(user.getId())) {
            return "redirect:/login?timeout=true";
        }
        try {
            identityService.setAuthenticatedUserId(user.getId());
            formService.submitTaskFormData(taskId, formProperties);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        redirectAttributes.addFlashAttribute("message", "任务完成：taskId=" + taskId);
        return "redirect:/oa/order/task/list";
    }

    /**
     * 签收任务
     */
    @RequestMapping(value = "task/claim/{id}")
    public String claim(@PathVariable("id") String taskId, HttpSession session,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {
    	User user = UserUtil.getUserFromSession(request.getSession());
        // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return "redirect:/login?timeout=true"; 
        }
        String userId = user.getId();
        String msg = "任务签收成功";
        try {
        	taskService.claim(taskId, userId);
		} catch (ActivitiObjectNotFoundException e) {
			msg = "任务不存在！";
		} catch (ActivitiTaskAlreadyClaimedException e) {
			msg = "任务可能已被其他人签收！";
		}
        
        redirectAttributes.addFlashAttribute("message", msg);
        return "redirect:/oa/order/task/list";
    }

    /**
     * 运行中的流程实例
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "process-instance/running/list")
    public ModelAndView running(Model model, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/oa/order/running-list");
        Page<ProcessInstance> page = new Page<ProcessInstance>(PageUtil.PAGE_SIZE);
        int[] pageParams = PageUtil.init(page, request);
        
          ProcessInstanceQuery leaveDynamicQuery = runtimeService.createProcessInstanceQuery()
          .processDefinitionKey("orderworkflow").orderByProcessInstanceId().desc().active();
          List<ProcessInstance> list = leaveDynamicQuery.listPage(pageParams[0], pageParams[1]);
          
          for (ProcessInstance iinst : list) {
        	  ExecutionEntity inst = (ExecutionEntity)iinst;
          	if (inst != null) {
                Map<String, String> map = getOrderHistoryDetail(inst.getId());
              	inst.setDeleteReason(map.get("customer"));
              	inst.setTenantId(map.get("orderId"));
              	inst.setBusinessKey(map.get("comment"));
          	}
          }
          
          page.setResult(list);
          page.setTotalCount(leaveDynamicQuery.count());

        mav.addObject("page", page);
        return mav;
    }

    /**
     * 已结束的流程实例
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "process-instance/finished/list")
    public ModelAndView finished(Model model, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/oa/order/finished-list");
        Page<HistoricProcessInstance> page = new Page<HistoricProcessInstance>(PageUtil.PAGE_SIZE);
        int[] pageParams = PageUtil.init(page, request);
        
            HistoricProcessInstanceQuery leaveDynamicQuery = historyService.createHistoricProcessInstanceQuery()
                    .processDefinitionKey("orderworkflow").finished().orderByProcessInstanceEndTime().desc();
            List<HistoricProcessInstance> list = leaveDynamicQuery.listPage(pageParams[0], pageParams[1]);
            
            for (HistoricProcessInstance iinst : list) {
            	HistoricProcessInstanceEntity inst = (HistoricProcessInstanceEntity)iinst;
            	if (inst != null) {
                   	Map<String, String> map = getOrderHistoryDetail(inst.getId());
                	
                	inst.setStartActivityId(map.get("customer"));//
                	inst.setEndActivityId(map.get("orderId"));
                	inst.setBusinessKey(map.get("comment"));
                	
            	}
            }
            
            page.setResult(list);
            page.setTotalCount(leaveDynamicQuery.count());

        mav.addObject("page", page);
        return mav;
    }
    
    /**
     * 首页待办任务签收任务
     * ****没有检查是否登录超时
     */
    @RequestMapping(value = "task/claimTodo/{id}")
    public String claimTodo(@PathVariable("id") String taskId, HttpSession session,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {
    	
    	User user = UserUtil.getUserFromSession(request.getSession());
        // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return "redirect:/login?timeout=true"; 
        }
        String userId = user.getId();
        String msg = "任务签收成功";
        try {
        	taskService.claim(taskId, userId);
		} catch (ActivitiObjectNotFoundException e) {
			msg = "任务不存在！";
		} catch (ActivitiTaskAlreadyClaimedException e) {
			msg = "任务可能已被其他人签收！";
		}
    	
        redirectAttributes.addFlashAttribute("message", msg);
        return "redirect:/main/welcome";
    }
    
    /**
     * 办理任务，提交task的并保存form
     */
    @RequestMapping(value = "task/completeTodo/{taskId}")
    @SuppressWarnings("unchecked")
    public String completeTodoTask(@PathVariable("taskId") String taskId, @RequestParam(value = "processType", required = false) String processType,
                               RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Map<String, String> formProperties = new HashMap<String, String>();

        // 从request中读取参数然后转换
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            String key = entry.getKey();

            // fp_的意思是form paremeter
            if (StringUtils.defaultString(key).startsWith("fp_")) {
                formProperties.put(key.split("_")[1], entry.getValue()[0]);
            }
        }

        logger.debug("start form parameters: {}", formProperties);

        User user = UserUtil.getUserFromSession(request.getSession());

        // 用户未登录不能操作，实际应用使用权限框架实现，例如Spring Security、Shiro等
        if (user == null || StringUtils.isBlank(user.getId())) {
            return "redirect:/login?timeout=true";
        }
        try {
            identityService.setAuthenticatedUserId(user.getId());
            formService.submitTaskFormData(taskId, formProperties);
        } finally {
            identityService.setAuthenticatedUserId(null);
        }

        redirectAttributes.addFlashAttribute("message", "任务完成：taskId=" + taskId);
        return "redirect:/main/welcome";
    }
    
    private void getOrderForm(Task task) {
    	//Map<String, Object> result = new HashMap<String, Object>();
    	try {
    		TaskFormDataImpl taskFormData = (TaskFormDataImpl) formService.getTaskFormData(task.getId());
    		// 设置task为null，否则输出json的时候会报错
    		//taskFormData.setTask(null);
    		
    		List<FormProperty> propList = taskFormData.getFormProperties();
    		for (FormProperty prop : propList) {
    			if ("customer".equals(prop.getId())) {
    				//result.put("customer", prop.getValue());
    				task.setDescription(prop.getValue());//customer
    			} else if ("orderId".equals(prop.getId())) {
    				task.setOwner(prop.getValue());
    				//result.put("orderId", prop.getValue());
    			} else if ("comment".equals(prop.getId())) {
    				task.setCategory(prop.getValue());
    				//result.put("customer", prop.getValue());
    			}
    		}
			
		} catch (Exception e) {
		}
        
    	//return result;
    }
    
    private Map<String, String> getOrderHistoryDetail(String instId) {
    	
    	 Map<String, String> result = new HashMap<String, String>();
    	
    	String customer = "";
    	String orderId = "";
    	String lastComment = "";
    	int commentCount = 0;
    	
    	try {
        	HistoricDetailQuery query = historyService.createHistoricDetailQuery()
        			.formProperties().processInstanceId(instId);
        	List<HistoricDetail> list = query.list();
        	
        	for (HistoricDetail detail : list) {
        		HistoricFormProperty formDetail = (HistoricFormProperty)detail;
        		if (formDetail != null) {
        			if ("customer".equals(formDetail.getPropertyId())) {
        				result.put("customer", formDetail.getPropertyValue());
        				//inst.setStartActivityId(formDetail.getPropertyValue());//
        			} else if ("orderId".equals(formDetail.getPropertyId())) {
        				result.put("orderId", formDetail.getPropertyValue());
        				//inst.setEndActivityId(formDetail.getPropertyValue());
        			} else if ("comment".equals(formDetail.getPropertyId())) {
        				lastComment = formDetail.getPropertyValue();
        				//inst.setBusinessKey(formDetail.getPropertyValue());
        				commentCount++;
        			}
        		}
        	}
        	result.put("comment", lastComment);
        	
		} catch (Exception e) {
			
		}
    	return result;
    }

}
