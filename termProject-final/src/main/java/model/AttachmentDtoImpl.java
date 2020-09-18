package model;

import dao.PostDao;

public class AttachmentDtoImpl implements AttachmentDto{
	private int no;
	private String path;
	private PostDto post;
	private String fileName;
	private String originalName;
	
	public int getNo() {return no;}
	public void setNo(int no) {this.no = no;}
	public String getPath() {return path;}
	public void setPath(String path) {this.path = path;}
	public PostDto getPost() {return post;}
	public void setPost(PostDto post) {this.post = post;}
	public String getFileName() {return fileName;}
	public void setFileName(String fileName) {this.fileName = fileName;}
	public String getOriginalName() {return originalName;}
	public void setOriginalName(String originalName) {this.originalName = originalName;}
	
}
