package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.naming.NamingException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.DietDAO;
import model.User;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User loginUser = (session != null) ? (User)session.getAttribute("user") : null ;
		//未ログインの場合ログインページへ
		if(loginUser == null) {
			response.sendRedirect(request.getContextPath()+"/LoginServlet");
			return;
		}
		//log_idの取得
		String logIdStr = request.getParameter("log_id");
		if(logIdStr == null || logIdStr.isEmpty()) {
			response.sendRedirect(request.getContextPath()+"/ResultServlet");
		}
		
		try {
			int log_id = Integer.parseInt(logIdStr);
			DietDAO dao = new DietDAO();
			
			dao.deleteOne(log_id);
		}catch(NumberFormatException | SQLException | NamingException e){
			e.printStackTrace();
		}
		session.setAttribute("msg", "データを１件削除しました");
		response.sendRedirect(request.getContextPath()+"/ResultServlet");
	}

}
