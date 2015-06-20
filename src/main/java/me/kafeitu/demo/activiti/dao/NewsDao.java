package me.kafeitu.demo.activiti.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import me.kafeitu.demo.activiti.entity.oa.order.NewsInfo;

@Component
public interface NewsDao extends CrudRepository<NewsInfo, Long> {
	
}
