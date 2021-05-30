package model;

import java.util.Date;

public class UserDtoImpl implements UserDto{
	int no;
	String id;
	String password;
	String nickname;
	Date registerDate;
	public UserDtoImpl() {
		
	}
	public UserDtoImpl(int no, String id, String password, String nickname, Date registerDate) {
		super();
		this.no = no;
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.registerDate = registerDate;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	@Override
	public String toString() {
		return "UserDtoImpl [no=" + no + ", id=" + id + ", password=" + password + ", nickname=" + nickname
				+ ", registerDate=" + registerDate + "]";
	}
	
}
