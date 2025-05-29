package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import model.Log;

public class DietDAO {
	
	public List<Log> findAll(int user_id) {
		List<Log> list = new ArrayList<>();
		String sql = """
				SELECT
				id,user_id,weight,breakfast,lunch,dinner,snack,logDate,memo
				FROM diet_logs
				WHERE user_id = ?
				ORDER BY logDate DESC
				""";
		try (Connection db = new DbConnector().getConnection()) {
			PreparedStatement ps = db.prepareStatement(sql);
			ps.setInt(1, user_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				//
				double weight = rs.getDouble("weight");
				String breakfast = rs.getString("breakfast");
				String lunch = rs.getString("lunch");
				String dinner = rs.getString("dinner");
				String snack = rs.getString("snack");
				Date logDate = rs.getDate("logDate");
				String memo = rs.getString("memo");
				Log log = new Log(id,user_id,	 weight, breakfast, lunch, dinner, snack, logDate, memo);
				list.add(log);
			}
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		return list;
	}
	public Log findOne(int log_id) {
		String sql="""
				SELECT id,user_id,weight,breakfast,lunch,dinner,snack,logDate,memo
				FROM diet_logs
				WHERE id = ?
				""";
		try(Connection db = new DbConnector().getConnection();
				PreparedStatement ps = db.prepareStatement(sql)){
			ps.setInt(1, log_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int user_id = rs.getInt("user_id");
				double weight = rs.getDouble("weight");
				String breakfast = rs.getString("breakfast");
				String lunch = rs.getString("lunch");
				String dinner = rs.getString("dinner");
				String snack = rs.getString("snack");
				Date logDate = rs.getDate("logDate");
				String memo = rs.getString("memo");
				
				return new Log(log_id,user_id,weight,breakfast,lunch,dinner,snack,logDate,memo);
			}
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void insertOne(Log log) {
		String deleteSql = """
				DELETE FROM diet_logs
				WHERE 
				logDate = ?
				AND 
				user_id = ?
				""";
		String insertSql = """
				INSERT INTO
				diet_logs(user_id,weight,breakfast,lunch,dinner,snack,logDate,memo)
				VALUES(?,?,?,?,?,?,?,?);
				""";
		try (Connection db = new DbConnector().getConnection()) {
			//日付が重複しているものの削除
			try (PreparedStatement dps = db.prepareStatement(deleteSql)) {
				dps.setDate(1, new java.sql.Date(log.getLogDate().getTime()));
				dps.setInt(2,log.getUser_id());
				dps.executeUpdate();
			}
			//新規にLogを追加
			try (PreparedStatement ips = db.prepareStatement(insertSql)) {
				ips.setInt(1,log.getUser_id());
				ips.setDouble(2, log.getWeight());
				ips.setString(3, log.getBreakfast());
				ips.setString(4, log.getLunch());
				ips.setString(5, log.getDinner());
				ips.setString(6, log.getSnack());
				ips.setDate(7, new java.sql.Date(log.getLogDate().getTime()));
				ips.setString(8, log.getMemo());
				ips.executeUpdate();
			}
		} catch (SQLException | NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
	public Log findLatestByUserId(int userId) {
		try(Connection db = new DbConnector().getConnection()){
			String sql = """
					SELECT * FROM diet_logs 
					WHERE 
					user_id = ? 
					ORDER BY 
					log_date DESC
					LIMIT 1
					""";
			PreparedStatement ps = db.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("id");
				int user_id =rs.getInt("user_id");
				double weight = rs.getDouble("weight");
				String breakfast = rs.getString("breakfast");
				String lunch = rs.getString("lunch");
				String dinner = rs.getString("dinner");
				String snack = rs.getString("snack");
				Date logDate = rs.getTimestamp("log_date");
				String memo = rs.getString("memo");
				Log log = new Log(id,user_id,weight,breakfast,lunch,dinner,snack,logDate,memo);
				return log;
				
			}
		} catch (SQLException | NamingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}
	public void deleteOne(int log_id)throws SQLException,NamingException{
		String sql = "DELETE FROM diet_logs WHERE id = ?";
		try(Connection db = new DbConnector().getConnection();
				PreparedStatement ps = db.prepareStatement(sql)){
			ps.setInt(1, log_id);
			ps.executeUpdate();
			
		}
	}
	public void updateOne(Log log)throws SQLException,NamingException{
		String sql = """
				UPDATE diet_logs
				SET weight = ?,breakfast = ?, lunch = ?, dinner = ?, snack = ?, memo=?
				WHERE id = ?
				""";
		try(Connection db = new DbConnector().getConnection();
				PreparedStatement ps = db.prepareStatement(sql)){
			ps.setDouble(1, log.getWeight());
			ps.setString(2, log.getBreakfast());
			ps.setString(3, log.getLunch());
			ps.setString(4, log.getDinner());
			ps.setString(5, log.getSnack());
			ps.setString(6,log.getMemo());
			ps.setInt(7, log.getLog_id());
			
			ps.executeUpdate();
		}
	}
}
