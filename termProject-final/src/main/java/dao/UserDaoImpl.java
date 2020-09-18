package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import model.UserDto;
import model.UserDtoImpl;

public class UserDaoImpl implements UserDao{
	private UserDto user;
	private JdbcTemplate jdbcTemplate;

	public UserDto getUser() { return user; }
	public void setUser(UserDto user) { this.user = user; }
	public JdbcTemplate getJdbcTemplate() {return jdbcTemplate;}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { this.jdbcTemplate=jdbcTemplate; }
	
	public UserDto selectUserWithUserId(String userId) {
		List<UserDto> userList = jdbcTemplate.query("select * from USER where ID =?", 
				new RowMapper<UserDto>() {

					@Override
					public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						UserDto userDto = new UserDtoImpl(rs.getInt("no"),
								rs.getString("id"),
								rs.getString("PASSWORD"),
								rs.getString("NICKNAME"),
								rs.getTimestamp("REGISTERDATE"));
						return userDto;
					}
			
		}, userId);
		return userList.isEmpty()? null: userList.get(0);
	}
	public UserDto selectUserWithUserNo(int no) {
		List<UserDto> userList = jdbcTemplate.query("select * from USER where NO =?", 
				new RowMapper<UserDto>() {

					@Override
					public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						UserDto userDto = new UserDtoImpl(
								rs.getInt("no"),
								rs.getString("id"),
								rs.getString("PASSWORD"),
								rs.getString("NICKNAME"),
								rs.getTimestamp("REGISTERDATE"));
						return userDto;
					}
			
		}, no);
		return userList.isEmpty()? null: userList.get(0);
	}
	public UserDto insertUser(UserDto user) {
		this.user = user;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
				"insert into user(ID, PASSWORD, NICKNAME, REGISTERDATE) values(?, ?, ?, ?)",
				new String[] {"NO"});
				pstmt.setString(1, user.getId());
				pstmt.setString(2, user.getPassword());
				pstmt.setString(3, user.getNickname());
				long timeNow = Calendar.getInstance().getTimeInMillis();
				Timestamp ts = new Timestamp(timeNow);
				pstmt.setTimestamp(4, ts);
				return pstmt;
			}
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		user.setNo((int)keyValue.longValue());
		return user;
	}
	public boolean deleteUser(UserDto user) {
		int result = jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("delete from user where id=?");
				pstmt.setString(1, user.getId());
				return pstmt;
			}
		});
		if(result == 0)
			return false;
		else
			return true;
	}
	public int updateInfo(UserDto user) {
		return jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement("update user set password=?, nickname=? where id=?");
				pstmt.setString(1, user.getPassword());
				pstmt.setString(2, user.getNickname());
				pstmt.setString(3, user.getId());
				return pstmt;
			}
		});
	}
	
	public List<UserDto> selectUsers(){
		List<UserDto> userList = jdbcTemplate.query("select * from USER", 
				new RowMapper<UserDto>() {

					@Override
					public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						UserDto userDto = new UserDtoImpl(rs.getInt("no"),
								rs.getString("id"),
								rs.getString("PASSWORD"),
								rs.getString("NICKNAME"),
								rs.getTimestamp("REGISTERDATE"));
						return userDto;
					}
			
		});
		return userList.isEmpty()? null: userList;
	}
}

