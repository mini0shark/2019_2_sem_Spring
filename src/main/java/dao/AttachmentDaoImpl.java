package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import model.AttachmentDto;
import model.AttachmentDtoImpl;
import model.PostDto;
import model.PostDtoImpl;
import model.UserDto;

public class AttachmentDaoImpl implements AttachmentDao{
	private JdbcTemplate jdbcTemplate;
	private PostDao postDao;
	public JdbcTemplate getJdbcTemplate() {return jdbcTemplate;}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {this.jdbcTemplate = jdbcTemplate;}
	public PostDao getPostDao() {return postDao;}
	public void setPostDao(PostDao postDao) {this.postDao = postDao;}
	

	public int insertAttachment(AttachmentDto attachment) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement pstmt = con.prepareStatement(
				"insert into file(path, p_no, savename, originalname) values(?, ?, ?, ?)",
				new String[] {"NO"});
				pstmt.setString(1, attachment.getPath());
				pstmt.setInt(2, attachment.getPost().getNo());
				pstmt.setString(3, attachment.getFileName());
				pstmt.setString(4, attachment.getOriginalName());
				return pstmt;
			}
		}, keyHolder);
		Number keyValue = keyHolder.getKey();
		attachment.setNo((int)keyValue.longValue());
		return attachment.getNo();
	}
	
	public List<AttachmentDto> selectAttachmentListWithPostNo(int postNumber){
		List<AttachmentDto> attachmentList =jdbcTemplate.query("select * from FILE where p_no=?",
				new RowMapper<AttachmentDto>(){

					@Override
					public AttachmentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						AttachmentDto attachment = new AttachmentDtoImpl();
						attachment.setNo(rs.getInt("no"));
						attachment.setPath(rs.getString("path"));
						attachment.setFileName(rs.getString("SAVENAME"));
						attachment.setOriginalName(rs.getString("ORIGINALNAME"));
						return attachment;
					}
			
		}, postNumber);
		return attachmentList.size() > 0 ? attachmentList: null;
	}
	public AttachmentDto selectAttachmentWithNo(int attachmentNo) {
		List<AttachmentDto> attachmentList = jdbcTemplate.query("select * from FILE where NO =?", 
				new RowMapper<AttachmentDto>() {
					@Override
					public AttachmentDto mapRow(ResultSet rs, int rowNum) throws SQLException {
						AttachmentDto attachment = new AttachmentDtoImpl();
						attachment.setNo(rs.getInt("NO"));
						attachment.setPath(rs.getString("path"));
						attachment.setFileName(rs.getString("savename"));
						attachment.setOriginalName(rs.getString("ORIGINALNAME"));
						return attachment;
					}
			
		}, attachmentNo);
		return attachmentList.isEmpty()? null: attachmentList.get(0);
	}
	public int deleteAttchment(int pno){
		return jdbcTemplate.update(new PreparedStatementCreator() {
					
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement pstmt = con.prepareStatement("delete from file where p_no=?");
						pstmt.setInt(1, pno);
						return pstmt;
					}
				});
	}
}
