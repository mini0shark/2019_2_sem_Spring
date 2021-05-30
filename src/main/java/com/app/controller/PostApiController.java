package com.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import model.AttachmentDto;
import model.AttachmentDtoImpl;
import model.PostDto;
import model.PostDtoImpl;
import model.UserDto;
import service.AttachmentService;
import service.PostService;
import service.UserService;

@RestController
public class PostApiController {
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	@Autowired
	private AttachmentService attachmentService;
	@PostMapping(value="/registerDiary")
	public int registerDiary(HttpServletRequest  request
			, @RequestPart("attachment") MultipartFile file) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute(session.getId());
		if(userId==null)
			return 2;	// session만료
		// diaryService에서 오늘의 글 있는지 확인
		
//		System.out.println();
		AttachmentDto attachment = null;
		try {
			if(!file.isEmpty()) {
	            String originalName = file.getOriginalFilename(); 
	            String fileNameExtension = FilenameUtils.getExtension(originalName).toLowerCase(); 
	            File saveFile; 
	            String saveFileName; 
	            String fileUrl = "C:\\spring\\uploadFiles\\"+userId+"\\";
	            
	            do { 
	            	saveFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension; 
	            	saveFile = new File(fileUrl+ saveFileName); 
	            } while (saveFile.exists()); 
	            
	            saveFile.getParentFile().mkdirs(); 
	            file.transferTo(saveFile); 
	            
	            attachment= new AttachmentDtoImpl();
	            attachment.setFileName(saveFileName);
	            attachment.setOriginalName(originalName);
	            attachment.setPath(fileUrl);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		PostDto post = new PostDtoImpl();
		post.setTitle(request.getParameter("title"));
		try {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date alarmDate = transFormat.parse(request.getParameter("dDay"));
			post.setAlarmDate(alarmDate);
		} catch (ParseException e) {
			post.setAlarmDate(null);
		} catch (Exception e) {
			post.setAlarmDate(null);}
		try {
			post.setDisclosureLevel(Integer.parseInt(request.getParameter("discloureScope")));
		}catch(NumberFormatException e) {
			post.setDisclosureLevel(3);	// 비공개
		}
		post.setContent(request.getParameter("content"));
		post.setNo(postService.makePosting(post, userService.getUserWithId(userId)));
		if(attachment!=null) {
			return attachmentService.registerAttachment(attachment, post);
		} 
		return post.getNo()>0? 1:0;	// 성공 // 실패 
	}
	
	@GetMapping("getDiaryList")
	public Map<String, Object> getDiaryList(
			HttpServletRequest request,
			@RequestParam("page") int page) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute(session.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("count", postService.getPostListPageWithUserIdCount(page, userId));
		map.put("postList",postService.getPostListPageWithUserId(page, userId));
		return map; 
	}
	@GetMapping("getMyDiaryList")
	public Map<String, Object> getMyDiaryList(
			HttpServletRequest request,
			@RequestParam("page") int page) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute(session.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("count", postService.getMyPostListPageWithUserIdCount(page, userService.getUserWithId(userId).getNo()));
		map.put("postList",postService.getMyPostListPageWithUserId(page, userService.getUserWithId(userId).getNo()));
		return map; 
	}
	@GetMapping("searchPost")
	public Map<String, Object> searchPost(
			HttpServletRequest request,
			@RequestParam("searchText") String searchText,
			@RequestParam("page") int page) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute(session.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("count", postService.searchPostCount(searchText, page, userId));
		map.put("postList", postService.searchPost(searchText, page, userId));
		return map; 
	}

	@GetMapping("searchPostMine")
	public Map<String, Object> searchPostMine(
			HttpServletRequest request,
			@RequestParam("searchText") String searchText,
			@RequestParam("page") int page) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute(session.getId());
		Map<String, Object> map = new HashMap<>();
		map.put("count", postService.searchPostMineCount(searchText, page, userId));
		map.put("postList", postService.searchPostMine(searchText, page, userId));
		return map; 
	}
	@RequestMapping("/fileDownLoad/{no}")
	public void fileDownLoad(@PathVariable int no, HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		request.setCharacterEncoding("UTF-8");
		AttachmentDto attachment = attachmentService.getAttachmentWithNo(no);
		try {
			String fileUrl = attachment.getPath();
			String fileName = attachment.getFileName();
			String originalName = attachment.getOriginalName();
			InputStream is = null;
			OutputStream os = null;
			File f = null;
			boolean skip = false;
			String client = "";
			try {
				f = new File(fileUrl, fileName);
				is = new FileInputStream(f);
			}catch(FileNotFoundException fne) {
				skip = true;
			}
			client = request.getHeader("User-Agent");
			response.reset();
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Data");
			
			if(!skip) {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String(originalName.getBytes("UTF-8"), "ISO8859_1") + "\"");
                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
                response.setHeader("Content-Length", ""+f.length());
                os=response.getOutputStream();
                byte b[] = new byte[(int) f.length()];
                int leng = 0;
                while((leng=is.read(b))>0) {
                	os.write(b, 0, leng);
                }
			}else {
                response.setContentType("text/html;charset=UTF-8");
                System.out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	

	@PutMapping(value= {"/editDiary/{pno}/{fileExist}"})
	public int editDiary(HttpServletRequest  request, @PathVariable("pno")int pno
			,@PathVariable("fileExist")boolean fileExist
			, @RequestPart(value="attachment", required=false) MultipartFile file) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute(session.getId());
		PostDto  post = postService.getPostWithPno(pno);
		if(fileExist) {
			postService.clearAtt(post);
			post.clearAttachmentList();
		}
		if(userId==null)
			return -3;	// session만료
		// diaryService에서 오늘의 글 있는지 확인
		if(post==null)
			return -2; // 글이 없음
		if(!userId.equals(post.getUser().getId()))
			return -1;	// writer가 아님
		AttachmentDto attachment = null;
		try {
			if(file!=null && !file.isEmpty()) {
	            String originalName = file.getOriginalFilename(); 
	            String fileNameExtension = FilenameUtils.getExtension(originalName).toLowerCase(); 
	            File saveFile; 
	            String saveFileName; 
	            String fileUrl = "C:\\spring\\uploadFiles\\"+userId+"\\";
	            
	            do { 
	            	saveFileName = RandomStringUtils.randomAlphanumeric(32) + "." + fileNameExtension; 
	            	saveFile = new File(fileUrl+ saveFileName); 
	            } while (saveFile.exists()); 
	            
	            saveFile.getParentFile().mkdirs(); 
	            file.transferTo(saveFile); 
	            
	            attachment= new AttachmentDtoImpl();
	            attachment.setFileName(saveFileName);
	            attachment.setOriginalName(originalName);
	            attachment.setPath(fileUrl);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		post.setTitle(request.getParameter("title"));
		try {
			SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date alarmDate = transFormat.parse(request.getParameter("dDay"));
			post.setAlarmDate(alarmDate);
		} catch (ParseException e) {	
			post.setAlarmDate(null);
		} catch (Exception e) {
			post.setAlarmDate(null);}
		try {
			post.setDisclosureLevel(Integer.parseInt(request.getParameter("discloureScope")));
		}catch(NumberFormatException e) {
			post.setDisclosureLevel(3);	// 기본 비공개
		}
		post.setContent(request.getParameter("content"));
		if(attachment!=null) {
			return attachmentService.registerAttachment(attachment, post);
		};
		
		return postService.editPost(post);	// 성공 =1// 실패 = 0 
	}
	
	@DeleteMapping("/deletePost/{pno}")
	public int deletePost(HttpServletRequest  request, @PathVariable("pno")int pno) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute(session.getId());
		PostDto post = postService.getPostWithPno(pno);
		if(userId==null | userId.isEmpty())
			return -2;	//세션 만료
		if(post==null)
			return -1; 	//이미 지워짐
		if(!post.getUser().getId().equals(userId)) {
			return -3;
		}
		
		return postService.removePost(post);
		
	}
	
	@RequestMapping(value="/toDaysAlarmCheck", method= RequestMethod.GET)
	public boolean toDaysAlarmCheck(HttpServletRequest request) {
		boolean result=true;
		HttpSession session = request.getSession();		
		String userId = (String)session.getAttribute(session.getId());
		UserDto user = userService.getUserWithId(userId);
		List<PostDto> todayPost = postService.getMyAlarm(user.getNo(), 0);
		// diaryService에서 오늘의 글 있는지 확인
		return todayPost.size()>0?true:false;
	}
	

}
