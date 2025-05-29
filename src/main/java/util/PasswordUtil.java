package util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
	public static String hashPasswordWithSalt(String password,String salt) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			//ハスワードとソルトを結合
			byte[] hashBytes = md.digest((password + salt).getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b : hashBytes) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("パスワードのハッシュ化に失敗しました",e);
		}
	}
	//ソルト生成(ランダムな英数字)
	public static String generateSalt() {
		byte[] saltBytes = new byte[16];
		new SecureRandom().nextBytes(saltBytes);
		return Base64.getEncoder().encodeToString(saltBytes);
	}
}
