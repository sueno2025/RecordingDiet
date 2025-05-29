package controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.DietDAO;
import model.Log;
import model.User;

@WebServlet("/InputServlet")
public class InputServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//表示処理
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//キャッシュ無効化
		response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		
		//sessionが空の場合nullを返す
		HttpSession session = request.getSession(false);
		User loginUser = (session != null) ? (User) session.getAttribute("user") : null;
		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/LoginServlet");
			return;
		}
		String msg = (String) session.getAttribute("msg");
		if (msg != null) {
			request.setAttribute("msg", msg);
			session.removeAttribute("msg"); // 表示は一度だけにする
		}
		
		int user_id = loginUser.getUser_id();
		DietDAO dao = new DietDAO();
		List<Log> list = dao.findAll(user_id);
		session.setAttribute("user",loginUser);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/view/input.jsp").forward(request, response);
	}

	//追加処理
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.setCharacterEncoding("UTF-8");
		try {
			User loginUser = (session != null) ? (User) session.getAttribute("user") : null;
			if (loginUser == null) {
				response.sendRedirect(request.getContextPath() + "/LoginServlet");
				return;
			}
			int user_id = loginUser.getUser_id();
			double weight = Double.parseDouble(request.getParameter("weight"));
			String breakfast = emptyToNone(request.getParameter("breakfast"));
			String lunch = emptyToNone(request.getParameter("lunch"));
			String dinner = emptyToNone(request.getParameter("dinner"));
			String snack = emptyToNone(request.getParameter("snack"));
			String memo = emptyToNone(request.getParameter("memo"));
			java.util.Date logDate = new java.util.Date();
			//idはDB側でオートインクリメントされるので0でいい
			Log log = new Log(0, user_id, weight, breakfast, lunch, dinner, snack, logDate, memo);
			DietDAO dao = new DietDAO();
			dao.insertOne(log);
		} catch (NumberFormatException e) {
			//体重が不正な値だった場合（万が一）
			request.setAttribute("error", "体重は数字で入力してください");
		} catch (UnsupportedEncodingException e) {
			throw new ServletException(e);
		}
		//
		session.setAttribute("msg", "本日のデータ登録済み");
		response.sendRedirect(request.getContextPath() + "/ResultServlet");
	}

	//空文字判定
	private String emptyToNone(String s) {
		if (s == null || s.trim().isEmpty()) {
			 return "なし";
		} 
		
		return s.trim();
	}

}
