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
import org.springframework.web.bind.annotation.PathVariable;
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
    
//    @RequestMapping(value = {"list", ""})
//    public String newsList() {
//        return "/oa/news/newsList";
//    }
    
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
        
        return "redirect:/oa/news/list";
    }
    
    @RequestMapping(value = "detail/{id}")
    @ResponseBody
    public String getNewsDetail(@PathVariable("id") String newsId, 
    		HttpServletRequest request) {
        User user = UserUtil.getUserFromSession(request.getSession());
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return "请重新登录！";
        }
        
        logger.debug("Request for get news detail: {}", newsId);
        
        String content = "";
        try {
        	NewsInfo news = newsManager.getNews(Long.parseLong(newsId));
        	
        	if (news != null)
        		content = news.getContent();
        	else
        		content = "查询不存在";
		} catch (Exception e) {
			logger.error("获取新闻细节失败：", e);
	        return "获取新闻细节失败";
		}
        logger.debug("Get news detail: {}", content);
        return content;
    }
    
    /**
     * 得到新闻列表 
     */
    @RequestMapping(value = "getlist")
    @ResponseBody
    public List<NewsInfo> getNewsList(HttpServletRequest request) {
        //ModelAndView mav = new ModelAndView("/oa/news/list");
        List<NewsInfo> newsList = new ArrayList<NewsInfo>();
        User user = UserUtil.getUserFromSession(request.getSession());
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return newsList;
        }
        
        Iterable<NewsInfo> newsIterable = newsManager.getNewsList();
        
        if (newsIterable == null) {
        	return newsList;
        }
        
        if (newsIterable instanceof List) {
        	newsList = (List<NewsInfo>)newsIterable;
        } else {
        	for (NewsInfo news: newsIterable) {
        		newsList.add(news);
        	}
        }
        
        return newsList;
    }
    
    /**
     * 得到新闻列表页面
     */
    @RequestMapping(value = "list")
    public ModelAndView getNewsListPage(HttpServletRequest request) {
    	List<NewsInfo> newsList = new ArrayList<NewsInfo>();
        ModelAndView mav = new ModelAndView("/oa/news/newsList");
        
        User user = UserUtil.getUserFromSession(request.getSession());
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return new ModelAndView("redirect:/login?timeout=true"); 
        }
        
        Iterable<NewsInfo> newsIterable = newsManager.getNewsList();
        
        if (newsIterable == null) {
        	logger.debug("getlistpage: newsIterable is null, so return empty list.");
        	return mav;
        }
        
        if (newsIterable instanceof List) {
        	newsList = (List<NewsInfo>)newsIterable;
        	
        } else {
        	for (NewsInfo news: newsIterable) {
        		logger.debug("getlistpage： news detail: " + news);
        		newsList.add(news);
        	}
        }
        mav.addObject("newsList", newsList);
        
        return mav;
    }
    
    @RequestMapping(value = "delete/{id}")
    public String deleteNews(@PathVariable("id") String newsId, 
    		RedirectAttributes redirectAttributes, HttpServletRequest request) {

        User user = UserUtil.getUserFromSession(request.getSession());
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return "redirect:/login?timeout=true";
        }
        
        String msg = "";
        
        logger.debug("Request for delete news: {}", newsId);
        try {
        	NewsInfo news = newsManager.getNews(Long.parseLong(newsId));
        	if (news != null)
        		newsManager.deleteNewsByEntity(news);
        	else {
        		msg = "删除新闻失败，新闻查找不存在:newsId=" + newsId;
        		logger.error(msg);
        	}
        	//newsManager.deleteNews(Long.parseLong(newsId));
		} catch (Exception e) {
			msg = "新闻删除失败，删除异常：newsId=" + newsId;
			logger.error(msg, e);
		}
        
        if (msg.isEmpty()) {
        	redirectAttributes.addFlashAttribute("message", "新闻删除成功：newsId=" + newsId);
        } else {
        	redirectAttributes.addFlashAttribute("error", msg);
        }
        
        return "redirect:/oa/news/list";
    }
    
    
    /**
     * 更新新闻实体
     */
    @RequestMapping(value = "updatenews", method = RequestMethod.POST)
    public String updateNews(
    	RedirectAttributes redirectAttributes,
        HttpServletRequest request) 
    {
    	
        User user = UserUtil.getUserFromSession(request.getSession());
        if (user == null || StringUtils.isBlank(user.getId())) {
        	return "redirect:/login?timeout=true"; 
        }
    	
        long newsId = -1;
        String title = null;
    	String content = null;
    	String type = null;
    	
        // 从request中读取参数然后转换
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
        for (Entry<String, String[]> entry : entrySet) {
            String key = entry.getKey();
            
            if ("content".equals(key)) {
            	content = entry.getValue()[0];
            } else if ("newsId".equals(key)) {
            	newsId = Long.parseLong(entry.getValue()[0]);
            } else if ("title".equals(key)) {
            	title = entry.getValue()[0];
            }

        }
        
        logger.debug("Update news " + String.valueOf(newsId) + " parameter, title: " + title + "content: " + content);

        boolean hasError = false;
        String msg = "";
        
        try {
        	
        	NewsInfo news = newsManager.getNews(newsId);
        	news.setAuthor(user.getFirstName() + user.getLastName());
        	if (title != null) {
        		news.setTitle(title);
        	}
        	if (content != null) {
        		news.setContent(content);
        	}
        	if (type != null) {
        		news.setType(type);
        	}
        	
			newsManager.saveNews(news);
			msg = "新闻更新成功!";
		} catch (Exception e) {
			hasError = true;
			logger.error("新闻更新失败: ", e);
		}
        
        if (hasError) {
        	redirectAttributes.addFlashAttribute("error", msg);
        } else {
        	redirectAttributes.addFlashAttribute("message", msg);
        }
        
        
    	return "redirect:/oa/news/list";
    }
    

}
