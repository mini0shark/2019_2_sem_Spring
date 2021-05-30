package service;

import java.util.List;

import dao.AttachmentDao;
import model.AttachmentDto;
import model.PostDto;

public interface AttachmentService {
	public AttachmentDao getAttachmentDao();
	public void setAttachmentDao(AttachmentDao attachmentDao);
	
	public int registerAttachment(AttachmentDto attachment, PostDto post);
	
	public List<AttachmentDto> getAttachmentListWithPostNo(int postNumber);
	public AttachmentDto getAttachmentWithNo(int attachmentNo);
	public int clearPost(int pno);
}
