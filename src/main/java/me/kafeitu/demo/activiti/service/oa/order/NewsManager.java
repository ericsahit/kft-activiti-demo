package me.kafeitu.demo.activiti.service.oa.order;

import java.util.Date;

import me.kafeitu.demo.activiti.dao.NewsDao;
import me.kafeitu.demo.activiti.entity.oa.order.NewsInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 新闻实体管理
 * 
 * @author haihuawa
 *
 */
@Component
@Transactional(readOnly = true)
public class NewsManager {
	
	private NewsDao newsDao;
	
	@Autowired
	public void setNewsDao(NewsDao newsDao) {
		this.newsDao = newsDao;
	}
	
	@Transactional(readOnly = true)
	public void saveNews(NewsInfo entity) {
		if (entity.getId() == null) {
			entity.setCreateTime(new Date());
		}
		newsDao.save(entity);
	}
	
	@Transactional(readOnly = true)
	public void deleteNews(Long newsId) {
		newsDao.delete(newsId);
	}
	
	
	
	
}
