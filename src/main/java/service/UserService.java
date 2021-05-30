package service;

import java.util.Scanner;

import dao.UserDao;
import model.UserDto;
import model.UserDtoImpl;

public interface UserService {

	public void setUser(UserDto user);
	public UserDto getUser();
	public void setUserDao(UserDao userDao);
	public UserDao getUserDao();
	
	public UserDto login(String userId, String password);
	void checkPasswordMatch(UserDto user);
	public UserDto getUserWithId(String userId);
	public void checkUserExist(String userId);
	public UserDto signIn(UserDto user);
	public boolean withDrawal(UserDto user);
	public int changeUserInfo(UserDto user);
}
