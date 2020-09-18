package service;

import java.util.Scanner;

import dao.UserDao;
import model.UserDto;
import model.UserDtoImpl;
import exception.IdPasswordNotMatchingException;
import exception.UserNotExistException;

public class UserServiceImpl implements UserService{
	UserDao userDao;
	UserDto user;

//	User
	public void setUser(UserDto user) { this.user = user; }
	public UserDto getUser() { return user; }
	public void setUserDao(UserDao userDao) { this.userDao = userDao;}
	public UserDao getUserDao() { return userDao;}
	
	
	
	public void checkPasswordMatch(UserDto user) {
		userDao.setUser(this.user);
		UserDto targetUser = userDao.selectUserWithUserId(user.getId());
		if(targetUser==null || !targetUser.getPassword().equals(user.getPassword()))
			throw new IdPasswordNotMatchingException();
	}
	public void checkUserExist(String userId) {
		UserDto user = userDao.selectUserWithUserId(userId);
		if(user==null)
			throw new UserNotExistException();
	}
	public UserDto login(String userId, String password) {
		userDao.setUser(this.user);
		UserDto targetUser = userDao.selectUserWithUserId(userId);
		if(!targetUser.getPassword().equals(password))
			throw new IdPasswordNotMatchingException();
		else
			user=targetUser;
		return user;
	}
	public UserDto getUserWithId(String userId) {
		return userDao.selectUserWithUserId(userId);
	}

	public boolean withDrawal(UserDto user) {
		return userDao.deleteUser(user);
	}
	public UserDto signIn(UserDto user) {
		this.user=user;
		this.user = userDao.insertUser(this.user);
		return this.user;
	}
	public int changeUserInfo(UserDto user) {
		return userDao.updateInfo(user);
	}
}
