package controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.DietDAO;
import model.Log;
import model.User;

@WebServlet("/EditServlet")
public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User user = (session !=  null ) ? (User)session.getAttribute("user") : null;
		if(user == null) {
			response.sendRedirect(request.getContextPath()+"/LoginServlet");
			return;
		}
		
		try {
			int log_id = Integer.parseInt(request.getParameter("log_id"));
			DietDAO dao = new DietDAO();
			Log log = dao.findOne(log_id);
			
			//idが一致しない場合
			if(log == null || log.getUser_id() != user.getUser_id()) {
				response.sendRedirect(request.getContentType()+"/ResultServlet");
				return;
			}
			
			request.setAttribute("log", log);
			request.getRequestDispatcher("/WEB-INF/view/edit.jsp").forward(request, response);
			
		}catch(NumberFormatException e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/ResultServlet");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			int log_id = Integer.parseInt(request.getParameter("log_id"));
			double weight = Double.parseDouble(request.getParameter("weight"));
			String breakfast = emptyToNone(request.getParameter("breakfast"));
			String lunch = emptyToNone(request.getParameter("lunch"));
			String dinner = emptyToNone(request.getParameter("dinner"));
			String snack = emptyToNone(request.getParameter("snack"));
			String memo = emptyToNone(request.getParameter("memo"));
			
			Log log = new Log();
			log.setUser_id(((User)request.getSession().getAttribute("user")).getUser_id());
			log.setWeight(weight);
			log.setBreakfast(breakfast);
			log.setLunch(lunch);
			log.setDinner(dinner);
			log.setSnack(snack);
			log.setMemo(memo);
			log.setLogDate(null);
			log.setLog_id(log_id);
			
			new DietDAO().updateOne(log);
			
			request.getSession().setAttribute("msg","データを更新しました");
			response.sendRedirect(request.getContextPath()+"/ResultServlet");
		}catch(Exception e) {
			e.printStackTrace();
            request.setAttribute("error", "入力内容に誤りがあります");
            request.getRequestDispatcher("/WEB-INF/view/edit.jsp").forward(request, response);
			
		}
	}
	//空文字判定
		private String emptyToNone(String s) {
			if (s == null || s.trim().isEmpty()) {
				 return "なし";
			} 
			
			return s.trim();
		}

}
