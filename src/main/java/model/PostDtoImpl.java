package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostDtoImpl implements PostDto{
	int no;
	String title;
	String content;
	UserDto user;
	List<AttachmentDto> attachmentList;
	int disclosureLevel;
	Date createDate;
	Date alarmDate;
	public PostDtoImpl() {
		attachmentList = new ArrayList<>();
	}
	public int getNo() { return no; }
	public void setNo(int no) { this.no=no; }
	public UserDto getUser() { return user; }
	public void setUser(UserDto user) { this.user = user; }
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	public int getDisclosureLevel() { return disclosureLevel; }
	public void setDisclosureLevel(int disclosureLevel) { this.disclosureLevel = disclosureLevel; }
	public Date getCreateDate() { return createDate; }
	public void setCreateDate(Date createDate) { this.createDate=createDate; }
	public Date getAlarmDate() { return alarmDate; }
	public void setAlarmDate(Date alarmDate) { this.alarmDate=alarmDate; }
	public List<AttachmentDto> getAttachmentList(){return attachmentList;}
	public void setAttachmentList(List<AttachmentDto> attachmentList){ this.attachmentList = attachmentList; }
	
	public void addAttachment(AttachmentDto attachmentPath) { attachmentList.add(attachmentPath);}
	public boolean removeAttachment(AttachmentDto attachmentPath) {
		for(int i = 0; i<attachmentList.size() ; i++) {
			if(attachmentList.get(i).getFileName().equals(attachmentPath.getFileName()))
			{
				attachmentList.remove(i);
			}
		}
		return true;
	}
	@Override
	public String toString() {
		return "PostDtoImpl [no=" + no + ", title=" + title + ", content=" + content + ", user=" + user
				+ ", attachmentList=" + attachmentList + ", disclosureLevel=" + disclosureLevel + ", createDate="
				+ createDate + ", alarmDate=" + alarmDate + "]";
	}
	public void clearAttachmentList() {
		attachmentList.clear();
	}
	
	
}
