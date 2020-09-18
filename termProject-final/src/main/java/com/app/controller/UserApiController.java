package com.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dao.UserDao;
import exception.IdPasswordNotMatchingException;
import exception.UserNotExistException;
import model.PostDto;
import model.UserDto;
import model.UserDtoImpl;
import service.UserService;

@RestController
public class UserApiController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/idPwdCheck", method= RequestMethod.POST)
		public boolean idPwdCheck(Model model,
			@RequestBody Map<String, String> requestParams) {
		String id = requestParams.get("account");
		String password = requestParams.get("password");
		UserDto user = new UserDtoImpl();
		System.out.println("id : "+id);
		System.out.println("pwd : "+password);
		user.setId(id);
		user.setPassword(password);
		try {
			userService.checkPasswordMatch(user);
			return true;		//password match
		}catch(IdPasswordNotMatchingException e) {
			return false;		//password not match
		}
	}
	@RequestMapping(value="/checkSession", method= RequestMethod.GET)
	public boolean checkSession(HttpServletRequest request
			, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = (String)session.getAttribute(session.getId());
		if(userId==null) {
			Cookie cookie = new Cookie("userId", null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			cookie = new Cookie("nickname",null);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		return true;
	}
	@RequestMapping(value="/signinCheck", method= RequestMethod.POST)
	public int signinCheck(HttpServletRequest  requestParams) {
		List<String> ll = Collections.list(requestParams.getParameterNames());
		for(String a : ll){
			System.out.println("name : "+a+", value : "+requestParams.getParameter(a));
		}
		try {
			userService.checkUserExist(requestParams.getParameter("account"));
			return 0;
		}catch(UserNotExistException e) {
			UserDto user = new UserDtoImpl();
			user.setId(requestParams.getParameter("account"));
			user.setPassword(requestParams.getParameter("password"));
			user.setNickname(requestParams.getParameter("nickname"));
			System.out.println(user);
			userService.signIn(user);
			return 1;
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
		
		// diaryService에서 오늘의 글 있는지 확인
	}

	@RequestMapping(value="/editInfo", method= RequestMethod.POST)
	public int editInfo(HttpServletRequest  requestParams, HttpServletResponse response) {
		List<String> ll = Collections.list(requestParams.getParameterNames());
		for(String a : ll){
			System.out.println("name : "+a+", value : "+requestParams.getParameter(a));
		}
		
		HttpSession session = requestParams.getSession();
		UserDto user = userService.getUserWithId((String)session.getAttribute(session.getId()));
		System.out.println(session.getId());
		System.out.println(requestParams.getParameter("oldPassword"));
		user.setPassword(requestParams.getParameter("oldPassword"));
		try {
			userService.checkPasswordMatch(user);
		}catch(IdPasswordNotMatchingException e) {
			return -1;	//패스워드 틀림
		}
		user.setPassword(requestParams.getParameter("password"));
		String nickname = requestParams.getParameter("nickname");

		if(nickname!=null && !nickname.isEmpty())
			user.setNickname(nickname);

		session.setAttribute(session.getId(), user.getId());
		session.setAttribute("nickname", nickname);
		session.setMaxInactiveInterval(60*60*24);
		
		Cookie cookie = new Cookie("userId", user.getId());
		cookie.setMaxAge(60*60*24);
		cookie.setPath("/");
		response.addCookie(cookie);
		cookie = new Cookie("nickname",user.getNickname());
		cookie.setMaxAge(60*60*24);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		return userService.changeUserInfo(user);
		
		// diaryService에서 오늘의 글 있는지 확인
	}
}
