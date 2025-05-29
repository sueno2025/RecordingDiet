package model;

import java.io.Serializable;

public class User implements Serializable{

	private final int user_id;
	private final String user_name;
	public String user_pass;
	private double height;
	private char gender;
	public User(int user_id,String user_name,String user_pass,double height,char gender) {
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_pass = user_pass;
		this.height = height;
		this.gender = gender;
	}
	//パスワードを含めないコンストラクタ
	public User(int user_id,String user_name,double height ,char gender) {
		this(user_id,user_name,null,height,gender);
	}
	
	
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public int getUser_id() {
		return user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public String getUser_pass() {
		return user_pass;
	}

}
