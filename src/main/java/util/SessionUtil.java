package util;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionUtil {
	public static boolean checkLogin(HttpServletRequest request,HttpServletResponse response)throws IOException{
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("user")==null) {
			response.sendRedirect("LoginServlet");
			return false;
		}
		return true;
	}
}
