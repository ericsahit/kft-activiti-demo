package me.kafeitu.demo.activiti.dao;

import me.kafeitu.demo.activiti.entity.oa.order.NewsInfo;

import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 得到排序后的列表
 * @author haihuawa
 *
 */
public interface NewsListByOrderDao extends PagingAndSortingRepository<NewsInfo, Long> {

}
