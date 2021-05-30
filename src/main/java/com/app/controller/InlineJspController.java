package com.app.controller;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import model.PostDto;
import model.UserDto;
import service.PostService;
import service.UserService;


@Controller
public class InlineJspController {
	static final int PAST = -1;
	static final int TODAY = 0;
	static final int FUTURE = 1;
	@Autowired
	private PostService postService;
	@Autowired
	private UserService userService;
	@GetMapping("/footer")
	public String footer(Model model) {
		return "/inline/footer";
	}
	@GetMapping("/header")
	public String header(Model model,
			@CookieValue(value="loginId", required=false)Cookie loginId) {
		if(loginId!=null) {
			model.addAttribute("isLogin", true);
		}else {
			model.addAttribute("isLogin", false);
		}
		return "/inline/header";
	}
	@GetMapping("/myPageContent")
	public String myPageContent(Model model
			, @RequestParam(value="tab", required=false)String tab) {
		System.out.println("tab");
		try {
			model.addAttribute("tab", Integer.parseInt(tab));
		}catch(NumberFormatException e) {
			model.addAttribute("tab", 1);
		}
		return "/inline/myPageContent";
	}
	@GetMapping("/myAlarm")
	public String myAlarm(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute(session.getId());
		UserDto user = userService.getUserWithId(userId);
		List<PostDto> pastPost = postService.getMyAlarm(user.getNo(), PAST);
		model.addAttribute("past", pastPost);
		List<PostDto> TodayPost = postService.getMyAlarm(user.getNo(), TODAY);
		model.addAttribute("today", TodayPost);
		List<PostDto> FuturePost = postService.getMyAlarm(user.getNo(), FUTURE);
		model.addAttribute("futre", FuturePost);
		return "/inline/myAlarm";
	}

	@GetMapping("/diaryList")
	public String diaryList(Model model) {
		return "/inline/diaryList";
	}
	@GetMapping("/changeInfo")
	public String changeInfo(Model model) {
		return "/inline/changeInfo";
	}
	
}
