package com.app.config;

import java.util.ArrayList;
import java.util.Date;

import org.apache.tomcat.jdbc.pool.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import dao.AttachmentDao;
import dao.AttachmentDaoImpl;
import dao.PostDao;
import dao.PostDaoImpl;
import dao.UserDao;
import dao.UserDaoImpl;
import model.AttachmentDto;
import model.AttachmentDtoImpl;
import model.PostDto;
import model.PostDtoImpl;
import model.UserDto;
import model.UserDtoImpl;
import service.AttachmentService;
import service.AttachmentServiceImpl;
import service.PostService;
import service.PostServiceImpl;
import service.UserService;
import service.UserServiceImpl;

@Configuration
public class JavaConfig {

	@Bean
	public JdbcTemplate jdbcTemplate() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new DataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf-8&amp&serverTimezone=UTC");
		dataSource.setUsername("spring5");
		dataSource.setPassword("spring5");
		dataSource.setInitialSize(2);
		dataSource.setMaxActive(10);
		dataSource.setTestWhileIdle(true);
		dataSource.setMinEvictableIdleTimeMillis(60000*3);
		dataSource.setTimeBetweenEvictionRunsMillis(10*1000);
		return dataSource;
	}
	@Bean
	public UserService userService() {
		UserService userService = new UserServiceImpl();
		userService.setUser(user());
		userService.setUserDao(userDao());
		return userService;
	}
	@Bean
	public UserDto user() {
		UserDto user = new model.UserDtoImpl();
		user.setNo(0);
		user.setId("");
		user.setPassword("");
		user.setNickname("");
		user.setRegisterDate(new Date());
		return user;
	}
	@Bean
	public UserDao userDao() {
		UserDao userDao= new UserDaoImpl();
		userDao.setUser(user());
		userDao.setJdbcTemplate(jdbcTemplate());
		return userDao;
	}

	/////AttachMent

	@Bean
	public AttachmentDto attachment() {
		AttachmentDto attachment = new AttachmentDtoImpl();
		return attachment;
	}	
	@Bean AttachmentDao attachmentDao() {
		AttachmentDao attachmentDao = new AttachmentDaoImpl();
		attachmentDao.setJdbcTemplate(jdbcTemplate());
		return attachmentDao;
	}
	@Bean AttachmentService attachmentService() {
		AttachmentService attachmentService = new AttachmentServiceImpl();
		attachmentService.setAttachmentDao(attachmentDao());
		return attachmentService;
	}
	/////포스트

	@Bean
	public PostDto post() {
		PostDto post = new PostDtoImpl();
		post.setNo(0);
		post.setUser(user());
		post.setTitle("");
		post.setContent("");
		post.setAttachmentList(new ArrayList<AttachmentDto>());
		post.setCreateDate(new Date());
		post.setAlarmDate(null);
		post.setDisclosureLevel(0);
		return post;
	}
	@Bean
	public PostDao postDao() {
		PostDao postDao = new PostDaoImpl();
		postDao.setJdbcTemplate(jdbcTemplate());
		postDao.setUserDao(userDao());
		return postDao;
	}
	@Bean
	public PostService postService() {
		PostService postService = new PostServiceImpl();
		postService.setPostDao(postDao());
		postService.setUserDao(userDao());
		return postService;
	}
}
