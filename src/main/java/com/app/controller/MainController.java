package com.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.resource.HttpResource;

import dao.AttachmentDao;
import model.PostDto;
import model.PostDtoImpl;
import model.UserDto;
import service.AttachmentService;
import service.PostService;
import service.UserService;

@Controller
public class MainController {
	@Autowired
	UserService userService;
	@Autowired
	PostService postService;
	@GetMapping("/")
	public String hello(Model model) {return "index";}
	@GetMapping("/login")
	public String login(Model model) {return "login";}
	@GetMapping("/signin")
	public String signin(Model model) {return "signin";}
	@GetMapping("/alertNoLogin")
	public String alertNoLogin(Model model) {return "/alertNoLogin";}
	@GetMapping("/writingBoard")
	public String writingBoard(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId= (String)session.getAttribute(session.getId());
		System.out.println("왜 안가지?");
		if(userId==null)
			return "/alertNoLogin";
		return "/writingBoard";
	}
	
	@RequestMapping("/editPost/{no}")
	public String editPost(Model model, @PathVariable int no, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId= (String)session.getAttribute(session.getId());
		PostDto post = postService.getPostOnOpenDiary(no);
		boolean isWriter = userId.equals(post.getUser().getId());
		model.addAttribute("isWriter", isWriter);
		if(!isWriter) {
			model.addAttribute("diary", new PostDtoImpl());
			return "/editPost";
		}
		model.addAttribute("diary", post);
		return "/editPost";
	}
	
	@PostMapping("/loginAction")
	public String loginAction(HttpServletRequest request
			, HttpServletResponse response
			, @RequestParam  Map<String, String> requestParams
			) {
		String accountId = requestParams.get("account");
		String password = requestParams.get("password");
		HttpSession session = request.getSession();
		session.setAttribute(session.getId(), accountId);
		session.setMaxInactiveInterval(60*60*24);
		
		UserDto user = userService.login(accountId, password);
		Cookie cookie = new Cookie("userId", user.getId());
		cookie.setMaxAge(60*60*24);
		cookie.setPath("/");
		response.addCookie(cookie);
		cookie = new Cookie("nickname",user.getNickname());
		cookie.setMaxAge(60*60*24);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return "redirect:/";
	}
	@GetMapping("/logout")		//로그아웃 시 세션값과 쿠키값 제거
	public String logout(HttpServletRequest request
			, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.setAttribute(session.getId(), null);
		session.setMaxInactiveInterval(60*60*24);
		
		Cookie cookie = new Cookie("userId", null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		cookie = new Cookie("nickname",null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		return "redirect:/";
	}
	@GetMapping("/mypage")
	public String mypage(Model model
			, @RequestParam(value="tab", required=false)String tab) {
		try {
			model.addAttribute("tab", Integer.parseInt(tab));
		}catch(NumberFormatException e) {
			model.addAttribute("tab", 1);
		}
		return "/mypage";
	}

	@GetMapping("/openDiary")
	public String openDiary(Model model, 
			@RequestParam(value="diaryNo", required=true)int diaryNo,
			HttpServletRequest request) {
		int postNo=diaryNo-1;
		HttpSession session = request.getSession();
		String userId= (String)session.getAttribute(session.getId());
		PostDto post = postService.getPostOnOpenDiary(postNo);
		boolean isWriter=false;
		if(userId!=null)
			isWriter=userId.equals(post.getUser().getId());
		boolean auth = true;
		if(post.getDisclosureLevel()<3 && userId==null)	//==> 3이면 무조건 true
			auth=false;
		else if(post.getDisclosureLevel()==1 &&!post.getUser().getId().equals(userId))
			auth=false;
		model.addAttribute("auth", auth);
		model.addAttribute("diary", post);
		model.addAttribute("isWriter", isWriter);
		return "/openDiary";
		}
	
}
