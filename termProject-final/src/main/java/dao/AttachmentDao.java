package dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import model.AttachmentDto;

public interface AttachmentDao {
	public JdbcTemplate getJdbcTemplate() ;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	public PostDao getPostDao();
	public void setPostDao(PostDao postDao);
	
	public int insertAttachment(AttachmentDto attachment);
	
	public List<AttachmentDto> selectAttachmentListWithPostNo(int postNumber);
	public AttachmentDto selectAttachmentWithNo(int attachmentNo);
	public int deleteAttchment(int pno);
}
