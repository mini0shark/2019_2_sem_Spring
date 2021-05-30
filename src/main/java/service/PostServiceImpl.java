package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import ch.qos.logback.core.util.FileUtil;
import dao.AttachmentDao;
import dao.PostDao;
import dao.UserDao;
import model.AttachmentDto;
import model.PostDto;
import model.PostDtoImpl;
import model.UserDto;

public class PostServiceImpl implements PostService{
	@Autowired 
	PostDao postDao;
	@Autowired
	UserDao userDao;
	@Autowired
	AttachmentService attachmentService;
	public void setPostDao(PostDao postDao) { this.postDao=postDao; }
	public PostDao getPostDao() { return postDao; }
	public void setUserDao(UserDao userDao) { this.userDao=userDao;}
	public UserDao getUserDao() {return userDao;}
	public void setAttachmentService(AttachmentService attachmentService) {this.attachmentService=attachmentService;}
	public AttachmentService getAttachmentService() {return attachmentService;}
	
	
	
	//create
	public int makePosting(PostDto post, UserDto user) {
		return postDao.insertPost(post, user);
	}

	
	// retrieve
	public int getPostListPageWithUserIdCount(int page, String userId){
		UserDto user = userDao.selectUserWithUserId(userId);
		return postDao.selectMainPagePostListCount(page, user);
	}
	public int getMyPostListPageWithUserIdCount(int page, int uNo){
		return postDao.selectMyPostListPageCount(page, uNo);
	}
	public int searchPostMineCount(String searchText, int page, String userId){
		return postDao.selectPostWithSearchTextMineCount(searchText, page, userDao.selectUserWithUserId(userId).getNo());
	}
	public int searchPostCount(String searchText, int page, String userId){
		int uno = userId==null? 0 : userDao.selectUserWithUserId(userId).getNo();
		return postDao.selectPostWithSearchTextCount(searchText, page, uno);
	}
	public List<PostDto> getPostListPageWithUserId(int page, String userId){
		UserDto user = userDao.selectUserWithUserId(userId);
		return postDao.selectMainPagePostList(page, user);
	}
	public List<PostDto> getMyPostListPageWithUserId(int page, int uNo){
		return postDao.selectMyPostListPage(page, uNo);
	}
	public List<PostDto> searchPostMine(String searchText, int page, String userId){
		return postDao.selectPostWithSearchTextMine(searchText, page, userDao.selectUserWithUserId(userId).getNo());
	}
	public List<PostDto> searchPost(String searchText, int page, String userId){
		int uno = userId==null? 0 : userDao.selectUserWithUserId(userId).getNo();
		return postDao.selectPostWithSearchText(searchText, page, uno);
	}
	public List<PostDto> displayPostList(UserDto user) {
		if(user!=null) {
			return postDao.selectPostWithUserNo(user);
		}else {
			return postDao.selectPostsAll();
		}
	}
	public PostDto getPostOnOpenDiary(int postNo) {
		PostDto post = postDao.selectPostWithNo(postNo);
		post.setAttachmentList(new ArrayList<AttachmentDto>());
		List<AttachmentDto> attList = attachmentService.getAttachmentListWithPostNo(postNo);
		if(attList!=null)
			for(AttachmentDto attch : attList)
				post.addAttachment(attch);
		return post;
	}
	public PostDto getPostWithPno(int postNo) {
		return postDao.selectPostWithNo(postNo);		
	}
	public List<PostDto> getMyAlarm(int uNo, int when){
		return postDao.selectMyAlarm(uNo, when);
	}
//	public List<PostDto> searchPost(int scope,String searchText) {
//	return postDao.selectPostsWithSearchText(scope, searchText);
//}

	
	//update
	public int editPost(PostDto post) {
		return postDao.updatePost(post);
	}
	public int clearAtt(PostDto post) {
		return attachmentService.clearPost(post.getNo());
	}
	
	
	//delete
	public int removePost(PostDto post) {
		return postDao.deletePost(post);
	}

}
