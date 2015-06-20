package me.kafeitu.demo.activiti.entity.oa.order;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import me.kafeitu.demo.activiti.entity.IdEntity;

@Entity
@Table(name = "OA_NEWS")
public class NewsInfo extends IdEntity implements Serializable {
	private static final long serialVersionUID = 2L;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date createTime;
	
	private String title;
	
	private String author;
	
	private String type;
	
	private String content;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public String toString() {
		return String.valueOf(id)+title+author;
	}
			
}
