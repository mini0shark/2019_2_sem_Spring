package dao;

import java.io.File;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


import model.PostDto;
import model.PostDtoImpl;
import model.UserDto;
import model.UserDtoImpl;

public class PostDaoImpl implements PostDao{
	static final int PAST = -1;
	static final int TODAY = 0;
	static final int FUTURE = 1;
	private JdbcTemplate jdbcTemplate;
	private UserDao userDao;

	public UserDao getUserDao() { return userDao; }
	public void setUserDao(UserDao userDao) { this.userDao = userDao; }
	public JdbcTemplate getJdbcTemplate() { return jdbcTemplate; }
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { this.jdbcTemplate=jdbcTemplate; }
	
	public int insertPost(PostDto post, UserDto user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
				"insert into POST(u_no, TITLE, CONTENT, LEVEL, CREATEDATE, ALARMDATE) values(?, ?, ?, ?, ?, ?)",
				new String[] {"NO"});
				pstmt.setInt(1, user.getNo());
				pstmt.setString(2, post.getTitle());
				pstmt.setString(3, post.getContent());
				pstmt.setInt(4, post.getDisclosureLevel());		// 공개범위 => 나중에 수정 가능하게
				long timeNow = Calendar.getInstance().getTimeInMillis();
				Timestamp ts = new Timestamp(timeNow);
				pstmt.setTimestamp(5, ts);
				try {
					pstmt.setDate(6, new Date(post.getAlarmDate().getTime()));					
				}catch(NullPointerException e) {
					pstmt.setNull(6, java.sql.Types.NULL);
				}
				
				return pstmt;
			}
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		post.setNo((int)keyValue.longValue());
		return post.getNo();
	}
	public List<PostDto> selectPostsAll(){
		List<PostDto> postList =jdbcTemplate.query("select * from POST",
				new RowMapper<PostDto>(){

					@Override
					public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						PostDto post = new PostDtoImpl();
						post.setNo(rs.getInt("no"));
						post.setUser(userDao.selectUserWithUserNo(rs.getInt("u_no")));
						post.setTitle(rs.getString("title"));
						post.setContent(rs.getString("content"));
						post.setCreateDate(rs.getTimestamp("CREATEDATE"));
						post.setDisclosureLevel(rs.getInt("LEVEL"));
						return post;
					}
			
		}); 
		return postList; 
	}
	public List<PostDto> selectPostWithUserNo(UserDto user){
		List<PostDto> postList =jdbcTemplate.query("select * from POST where u_no=?",
				new RowMapper<PostDto>(){

					@Override
					public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						PostDto post = new PostDtoImpl();
						post.setNo(rs.getInt("no"));
						post.setUser(userDao.selectUserWithUserNo(rs.getInt("u_no")));
						post.setTitle(rs.getString("title"));
						post.setContent(rs.getString("content"));
						post.setCreateDate(rs.getTimestamp("CREATEDATE"));
						post.setDisclosureLevel(rs.getInt("LEVEL"));
						return post;
					}
			
		}, user.getNo()); 
		return postList; 
	}
	public List<PostDto> selectPostWithSearchTextMine(String searchText, int page, int uNo){
		String loginBlock="";
		if(uNo>0)
			loginBlock = " or LEVEL=2";
		String userQuery = "";
		if(uNo>0)
			userQuery+=" or (LEVEL=1 and U_NO="+uNo+")";
		String query = "select * from POST "
				+ "where (title LIKE '%"+searchText+"%' OR content LIKE '%"+searchText+"%') "
				+ "and u_no="+uNo
				+ " order by no DESC"
				+ " LIMIT "+(page-1)*10+", "+10;;
		if(!(query.length()>0))
				return null;
		System.out.println(query);
		List<PostDto> postList =jdbcTemplate.query(query,
				new RowMapper<PostDto>(){

			@Override
			public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				PostDto post = new PostDtoImpl();
				post.setNo(rs.getInt("no"));
				post.setUser(userDao.selectUserWithUserNo(rs.getInt("u_no")));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setCreateDate(rs.getTimestamp("CREATEDATE"));
				post.setDisclosureLevel(rs.getInt("LEVEL"));
				return post;
			}
	
		});
		return postList; 
	}
	public List<PostDto> selectPostWithSearchText(String searchText, int page, int uNo){
		String loginBlock="";
		if(uNo>0)
			loginBlock = " or LEVEL=2";
		String userQuery = "";
		if(uNo>0)
			userQuery+=" or (LEVEL=1 and U_NO="+uNo+")";
		String query = "select * from POST "
				+ "where (title LIKE '%"+searchText+"%' OR content LIKE '%"+searchText+"%') "
				+ "and (LEVEL=3"+loginBlock+userQuery+")"
				+ " order by no DESC"
				+ " LIMIT "+(page-1)*10+", "+10;;
		if(!(query.length()>0))
				return null;
		System.out.println(query);
		List<PostDto> postList =jdbcTemplate.query(query,
				new RowMapper<PostDto>(){

			@Override
			public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				PostDto post = new PostDtoImpl();
				post.setNo(rs.getInt("no"));
				post.setUser(userDao.selectUserWithUserNo(rs.getInt("u_no")));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setCreateDate(rs.getTimestamp("CREATEDATE"));
				post.setDisclosureLevel(rs.getInt("LEVEL"));
				return post;
			}
	
		});
		return postList; 
	}
	public List<PostDto> selectMyPostListPage(int page, int uNo){
		return jdbcTemplate.query("select * from POST where u_no=? order by no DESC LIMIT "+(page-1)*10+", "+10,
				new RowMapper<PostDto>(){

					@Override
					public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						PostDto post = new PostDtoImpl();
						post.setNo(rs.getInt("no"));
						post.setUser(userDao.selectUserWithUserNo(rs.getInt("u_no")));
						post.setTitle(rs.getString("title"));
						post.setContent(rs.getString("content"));
						post.setCreateDate(rs.getTimestamp("CREATEDATE"));
						post.setDisclosureLevel(rs.getInt("LEVEL"));
						return post;
					}
			
		}, uNo); 
	}

	public List<PostDto> selectMainPagePostList(int page, UserDto user){
		String loginBlock="";
		if(user!=null)
			loginBlock = " or LEVEL=2";
		String userQuery = "";
		if(user!=null)
			userQuery+=" or (LEVEL=1 and U_NO="+user.getNo()+")";
		String query = "select * from POST"
				+ " where LEVEL=3"+loginBlock+userQuery
				+ " order by no DESC"
				+ " LIMIT "+(page-1)*10+", "+10;
		List<PostDto> postList =jdbcTemplate.query(query,
				new RowMapper<PostDto>(){

			@Override
			public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				PostDto post = new PostDtoImpl();
				post.setNo(rs.getInt("no"));
				post.setUser(userDao.selectUserWithUserNo(rs.getInt("u_no")));
				post.setTitle(rs.getString("title"));
				post.setContent(rs.getString("content"));
				post.setCreateDate(rs.getTimestamp("CREATEDATE"));
				post.setDisclosureLevel(rs.getInt("LEVEL"));
				return post;
			}
	
		});
		return postList; 
	}
	public PostDto selectPostWithNo(int postNo) {
		List<PostDto> postList = jdbcTemplate.query("select * from POST where NO =?", 
				new RowMapper<PostDto>() {

					@Override
					public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						PostDto post = new PostDtoImpl();
						post.setNo(rs.getInt("NO"));
						post.setDisclosureLevel(rs.getInt("LEVEL"));
						post.setTitle(rs.getString("TITLE"));
						post.setUser(userDao.selectUserWithUserNo(rs.getInt("U_NO")));
						post.setContent(rs.getString("CONTENT"));
						post.setCreateDate(rs.getTimestamp("CREATEDATE"));
						post.setAlarmDate(rs.getDate("ALARMDATE"));
						return post;
					}
			
		}, postNo);
		return postList.isEmpty()? null: postList.get(0);
	}
	public List<PostDto> selectMyAlarm(int uNo, int when){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date now = new java.util.Date();

		String strNow = format.format(now);
		System.out.println(strNow);
		String midQuery="";
		switch(when){
		case PAST:
			midQuery="DATE(ALARMDATE) < '"+strNow+"'";			
			break;
		case TODAY:
			midQuery="DATE(ALARMDATE) = '"+strNow+"'";
			break;
		case FUTURE:
			midQuery="DATE(ALARMDATE) > '"+strNow+"'";
			break;
		}
		String q = "select * from POST where u_no=? and  "+midQuery+" and ALARMDATE is not null order by ALARMDATE DESC";
		System.out.println("query : "+q);
		
		System.out.println(now);
		List<PostDto> list =jdbcTemplate.query(q ,
				new RowMapper<PostDto>(){

				@Override
				public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					PostDto post = new PostDtoImpl();
					post.setNo(rs.getInt("no"));
					post.setTitle(rs.getString("title"));
					post.setAlarmDate(rs.getTimestamp("ALARMDATE"));
					return post;
				}
			
		}, uNo);  
		System.out.println(list);
		return list;
	}
	
	
	
	
	
	

	public int updatePost(PostDto post) { 
		return jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
				"UPDATE POST SET TITLE=?, CONTENT=?, LEVEL=?,  ALARMDATE=? WHERE no=?",
				new String[] {"NO"});
				pstmt.setString(1, post.getTitle());
				pstmt.setString(2, post.getContent());
				pstmt.setInt(3, post.getDisclosureLevel());		// 공개범위 => 나중에 수정 가능하게
				try {
					pstmt.setDate(4, new Date(post.getAlarmDate().getTime()));					
				}catch(NullPointerException e) {
					pstmt.setNull(4, java.sql.Types.NULL);
				}
				pstmt.setInt(5, post.getNo());
				
				return pstmt;
			}
		});
	}
	

	public int deletePost(PostDto post) {
		return jdbcTemplate.update("delete from POST where no=?", post.getNo());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public int selectPostWithSearchTextMineCount(String searchText, int page, int uNo){
		String loginBlock="";
		if(uNo>0)
			loginBlock = " or LEVEL=2";
		String userQuery = "";
		if(uNo>0)
			userQuery+=" or (LEVEL=1 and U_NO="+uNo+")";
		String query = "select count(*) from POST "
				+ "where (title LIKE '%"+searchText+"%' OR content LIKE '%"+searchText+"%') "
				+ "and u_no="+uNo;
		if(!(query.length()>0))
				return 0;
		return jdbcTemplate.queryForObject(query, int.class); 
	}
	public int selectPostWithSearchTextCount(String searchText, int page, int uNo){
		String loginBlock="";
		if(uNo>0)
			loginBlock = " or LEVEL=2";
		String userQuery = "";
		if(uNo>0)
			userQuery+=" or (LEVEL=1 and U_NO="+uNo+")";
		String query = "select COUNT(*) from POST "
				+ "where (title LIKE '%"+searchText+"%' OR content LIKE '%"+searchText+"%') "
				+ "and (LEVEL=3"+loginBlock+userQuery+")";
		if(!(query.length()>0))
				return 0;
		return jdbcTemplate.queryForObject(query, int.class);
	}
	public int selectMyPostListPageCount(int page, int uNo){	 
		String query = "select COUNT(*) from POST where u_no=?";
		return jdbcTemplate.queryForObject(query, int.class, uNo);
	}

	public int selectMainPagePostListCount(int page, UserDto user){
		String loginBlock="";
		if(user!=null)
			loginBlock = " or LEVEL=2";
		String userQuery = "";
		if(user!=null)
			userQuery+=" or (LEVEL=1 and U_NO="+user.getNo()+")";
		String query = "select COUNT(*) from POST"
				+ " where LEVEL=3"+loginBlock+userQuery;
		System.out.println(query);
		return jdbcTemplate.queryForObject(query, int.class); 
	}
}

