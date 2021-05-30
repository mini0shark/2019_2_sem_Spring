package model;

import java.util.Date;

public interface UserDto {
	public int getNo();
	public void setNo(int no);
	public String getId();
	public void setId(String id);
	public String getPassword();
	public void setPassword(String password);
	public String getNickname();
	public void setNickname(String nickname);
	public Date getRegisterDate();
	public void setRegisterDate(Date registerDate);
}