package service;

import java.util.List;

import dao.AttachmentDao;
import model.AttachmentDto;
import model.PostDto;

public class AttachmentServiceImpl implements AttachmentService{
	private AttachmentDao attachmentDao;

	public AttachmentDao getAttachmentDao() {return attachmentDao;}
	public void setAttachmentDao(AttachmentDao attachmentDao) {this.attachmentDao = attachmentDao;}
	
	public int registerAttachment(AttachmentDto attachment, PostDto post) {
		attachment.setPost(post);
		boolean result = attachmentDao.insertAttachment(attachment)>0 ? true:false;
		return result? 1:0;
	}
	public List<AttachmentDto> getAttachmentListWithPostNo(int postNumber){
		return attachmentDao.selectAttachmentListWithPostNo(postNumber);
	}
	public AttachmentDto getAttachmentWithNo(int attachmentNo){
		return attachmentDao.selectAttachmentWithNo(attachmentNo);
	}
	public int clearPost(int pno) {
		return attachmentDao.deleteAttchment(pno);
	}
}

