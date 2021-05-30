package model;

import java.util.Date;
import java.util.List;

public interface PostDto {
	public int getNo();
	public void setNo(int no);
	public UserDto getUser();
	public void setUser(UserDto user);
	public String getTitle();
	public void setTitle(String title);
	public String getContent();
	public void setContent(String content);
	public int getDisclosureLevel();
	public void setDisclosureLevel(int disclosureLevel);
	public Date getCreateDate();
	public void setCreateDate(Date createDate);
	public Date getAlarmDate();
	public void setAlarmDate(Date alarmDate);
	public void setAttachmentList(List<AttachmentDto> attachmentList);
	public List<AttachmentDto> getAttachmentList();
	public void addAttachment(AttachmentDto attachmentPath);
	public boolean removeAttachment(AttachmentDto attachmentPath);
	public void clearAttachmentList();
}
