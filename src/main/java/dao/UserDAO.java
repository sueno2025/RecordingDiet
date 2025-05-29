package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import model.User;

public class UserDAO {
	public User authenticate(String user_name, String inputPass)
			throws SQLException, NamingException {
		String sql = "SELECT user_id,user_name,user_pass ,salt, height,gender FROM users WHERE user_name = ?";
		try (Connection con = new DbConnector().getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, user_name);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					String storedHash = rs.getString("user_pass");
					String salt = rs.getString("salt");
					
					//ソルト付きで再ハッシュ
					String inputHash = util.PasswordUtil.hashPasswordWithSalt(inputPass, salt);

					if (inputHash.equals(storedHash)) {
						//user_nameは引数をそのまま使用する
						int user_id = rs.getInt("user_id");
						double height = rs.getDouble("height");
						char gender = rs.getString("gender").charAt(0);
						return new User(user_id, user_name, height, gender);
					}
				}
			}
		}
		return null;
	}

	//新規登録(ソルト付き)
	public void register(String user_name, String hashedPass, String salt, double height, char gender)
			throws SQLException, NamingException {
		String sql = """
				INSERT INTO users(user_name,user_pass,salt,height,gender)
				VALUES(?,?,?,?,?)
				""";
		try (Connection con = new DbConnector().getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, user_name);
			ps.setString(2, hashedPass);
			ps.setString(3,salt);
			ps.setDouble(4, height);
			ps.setString(5, String.valueOf(gender));
			ps.executeUpdate();
		}
	}

}
