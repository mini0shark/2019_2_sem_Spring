package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import model.UserDto;
import model.UserDtoImpl;

public interface UserDao {
	public UserDto getUser();
	public void setUser(UserDto user);
	public JdbcTemplate getJdbcTemplate();
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	
	public UserDto selectUserWithUserId(String userId);
	public UserDto selectUserWithUserNo(int no);
	public UserDto insertUser(UserDto user);
	public boolean deleteUser(UserDto user);
	public int updateInfo(UserDto user);
	
	public List<UserDto> selectUsers();
}
