package model;

import dao.PostDao;

public interface AttachmentDto {
	public int getNo();
	public void setNo(int no);
	public String getPath();
	public void setPath(String path);
	public PostDto getPost();
	public void setPost(PostDto post);
	public String getFileName();
	public void setFileName(String fileName);
	public String getOriginalName();
	public void setOriginalName(String originalName);

}
