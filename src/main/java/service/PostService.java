package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.PostDao;
import dao.UserDao;
import model.PostDto;
import model.PostDtoImpl;
import model.UserDto;

public interface PostService {
	void setPostDao(PostDao postDao);
	PostDao getPostDao();
	public void setUserDao(UserDao userDao);
	public UserDao getUserDao();
	public void setAttachmentService(AttachmentService attachmentService);
	public AttachmentService getAttachmentService();

	public int makePosting(PostDto post, UserDto user);
	
	
	public int getPostListPageWithUserIdCount(int page, String userId);
	public int getMyPostListPageWithUserIdCount(int page, int uNo);
	public int searchPostMineCount(String searchText, int page, String userId);
	public int searchPostCount(String searchText, int page, String userId);
	public List<PostDto> searchPost(String searchText, int page, String userId);
	public List<PostDto> searchPostMine(String searchText, int page, String userId);
//	public List<PostDto> searchPost(int scope,String searchText);
	public List<PostDto> displayPostList(UserDto user);
	public List<PostDto> getPostListPageWithUserId(int page, String userId);
	public List<PostDto> getMyPostListPageWithUserId(int page, int uNo);
	public PostDto getPostOnOpenDiary(int post);
	public PostDto getPostWithPno(int postNo);
	public List<PostDto> getMyAlarm(int uNo, int when);
	
	public int editPost(PostDto post);
	public int clearAtt(PostDto post);
	public int removePost(PostDto post);
}
