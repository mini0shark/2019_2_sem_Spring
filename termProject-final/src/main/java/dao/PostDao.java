package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import model.PostDto;
import model.PostDtoImpl;
import model.UserDto;

public interface PostDao {
	public JdbcTemplate getJdbcTemplate();
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	public UserDao getUserDao();
	public void setUserDao(UserDao userDao);
	
	public int insertPost(PostDto post, UserDto user);
	
	public List<PostDto> selectPostWithUserNo(UserDto user);
	
	public List<PostDto> selectPostWithSearchTextMine(String searchText, int page, int uNo);
	public List<PostDto> selectPostWithSearchText(String searchText, int page, int nNo);
	public List<PostDto> selectPostsAll();
	public List<PostDto> selectMyPostListPage(int page, int uNo);
	public List<PostDto> selectMainPagePostList(int Page, UserDto user);
	public PostDto selectPostWithNo(int postNo);
	public List<PostDto> selectMyAlarm(int uNo, int when);
	
	public int updatePost(PostDto post);

	public int deletePost(PostDto post);
	
	

	public int selectPostWithSearchTextMineCount(String searchText, int page, int uNo);
	public int selectPostWithSearchTextCount(String searchText, int page, int nNo);
	public int selectMyPostListPageCount(int page, int uNo);
	public int selectMainPagePostListCount(int Page, UserDto user);

}
