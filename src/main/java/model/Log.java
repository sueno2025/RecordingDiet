package model;

import java.io.Serializable;
import java.util.Date;

public class Log implements Serializable {
	private int log_id;
	private int user_id;
	private double weight;
	private String breakfast;
	private String lunch;
	private String dinner;
	private String snack;  //間食
	private Date logDate; //日付はString
	private String memo;  //メモ
	
	
	public Log() {}
	
	public Log(int id,int user_id, double weight,String breakfast,String lunch,String dinner,String snack,Date logDate,String memo) {
		this.log_id = id;
		this.user_id = user_id;
		this.weight = weight;
		this.breakfast=breakfast;
		this.lunch = lunch;
		this.dinner = dinner;
		this.snack = snack;
		this.logDate = logDate;
		this.memo = memo;
//		Date now = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
//		this.logDate = sdf.format(now);
	}
	public int getLog_id() {
		return this.log_id;
	}
	public void setLog_id(int log_id) {
		this.log_id=log_id;
	}
	
	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date date) {
		this.logDate = date;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getBreakfast() {
		return breakfast;
	}

	public void setBreakfast(String breakfast) {
		this.breakfast = breakfast;
	}

	public String getLunch() {
		return lunch;
	}

	public void setLunch(String lunch) {
		this.lunch = lunch;
	}

	public String getDinner() {
		return dinner;
	}

	public void setDinner(String dinner) {
		this.dinner = dinner;
	}

	public String getSnack() {
		return snack;
	}

	public void setSnack(String snack) {
		this.snack = snack;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
