package me.kafeitu.demo.activiti.service.oa.order;

import java.util.Date;
import java.util.List;

import me.kafeitu.demo.activiti.dao.NewsDao;
import me.kafeitu.demo.activiti.dao.NewsListByOrderDao;
import me.kafeitu.demo.activiti.entity.oa.order.NewsInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	
	private NewsListByOrderDao newsDao;
	
	@Autowired
	public void setNewsDao(NewsListByOrderDao newsDao) {
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
	
	public Iterable<NewsInfo> getNewsList() {
		Sort sort = new Sort(Direction.DESC, new String[] {"ID", "CREATE_TIME"});
		return newsDao.findAll(sort);
	}
	
	
	
	
}
