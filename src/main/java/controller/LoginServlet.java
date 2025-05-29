package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserDAO;
import model.User;

//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msg = (String)request.getSession().getAttribute("msg");
		if(msg != null) {
			request.setAttribute("msg",msg);
			request.getSession().removeAttribute("msg");
		}
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String user_name = request.getParameter("user_name");
		String rawPassword = request.getParameter("user_pass");
		
		try {
			//ハッシュ化してないパスワードを渡す、ハッシュ化はDAO内で
			User user = new UserDAO().authenticate(user_name,rawPassword);
			if(user != null) {
			//認証成功の場合
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath()+"/InputServlet");
				
			}else {
				request.setAttribute("error", "IDまたはパスワードが違います");
				request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request,response);
			}
		}catch(SQLException | NamingException e) {
			throw new ServletException(e);
		}
	}

}
