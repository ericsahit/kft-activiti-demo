package me.kafeitu.demo.activiti.web.oa.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import me.kafeitu.demo.activiti.entity.oa.order.NewsInfo;
import me.kafeitu.demo.activiti.service.oa.leave.LeaveManager;
import me.kafeitu.demo.activiti.service.oa.order.NewsManager;
import me.kafeitu.demo.activiti.util.UserUtil;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * 请假控制器，包含保存、启动流程
 *
 * @author Wang Haihua
 */
@Controller
@RequestMapping(value = "/oa/news")
public class NewsController {
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
    
    @Autowired
    private NewsManager newsManager;
    
    @RequestMapping(value = {"create", ""})
    public String newsCreate() {
        return "/oa/news/newsCreate";
    }
    
    @RequestMapping(value = {"list", ""})
    public String newsList() {
        return "/oa/news/newsList";
    }
    
    /**
     * 存储新闻实体
     */
    @RequestMapping(value = "savenews", method = RequestMethod.POST)
    public String saveNews(
    	RedirectAttributes redirectAttributes,
        HttpServletRequest request) 
    {
    	
        User user = UserUtil.getUserFromSession(request.getSession());
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return "redirect:/login?timeout=true"; 
        }
    	
        String title = "";
    	String content = "";
    	String type = "";
    	
        // 从request中读取参数然后转换
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            String key = entry.getKey();
            
            if ("content".equals(key)) {
            	content = entry.getValue()[0];
            } else if ("type".equals(key)) {
            	type = entry.getValue()[0];
            } else if ("title".equals(key)) {
            	title = entry.getValue()[0];
            }

        }
        
        logger.debug("submit form parameter, title: " + title + "content: " + content);

        boolean hasError = false;
        String msg = "";
        
        try {
        	
        	NewsInfo news = new NewsInfo();
        	news.setTitle(title);
        	news.setAuthor(user.getFirstName() + user.getLastName());
        	news.setContent(content);
        	news.setType(type);
        	
			newsManager.saveNews(news);
			msg = "新闻创建成功!";
		} catch (Exception e) {
			hasError = true;
			logger.error("创建新闻失败: ", e);
		}
        
        if (hasError) {
        	redirectAttributes.addFlashAttribute("error", msg);
        } else {
        	redirectAttributes.addFlashAttribute("message", msg);
        }
        
        
    	return "redirect:/oa/news/newsCreate";
    }
    
    /**
     * 得到新闻列表 
     */
    @RequestMapping(value = "getlist")
    public List<NewsInfo> getNewsList(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/oa/news/newsList");
        List<NewsInfo> newsList = new ArrayList<NewsInfo>();
        User user = UserUtil.getUserFromSession(request.getSession());
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return newsList;
        }
        
        
        
        return newsList;
        
    }
    
    
    

}
