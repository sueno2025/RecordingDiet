package controller;

import java.io.IOException;
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

/**
 * Servlet implementation class ResultServlet
 */
@WebServlet("/ResultServlet")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		User loginUser = (session != null) ? (User) session.getAttribute("user") : null;

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/Login");
			return;
		}
		String msg =(String)session.getAttribute("msg");
		if(msg != null) {
			request.setAttribute("msg",msg);
			session.removeAttribute("msg");
		}
		DietDAO dao = new DietDAO();
		List<Log> logs = dao.findAll(loginUser.getUser_id());
		request.setAttribute("logs", logs);

		if (!logs.isEmpty()) {
			Log latestLog = logs.get(0);
			double heightM = loginUser.getHeight() / 100.0;
			double bmi = latestLog.getWeight() / (heightM * heightM);
			request.setAttribute("latestLog", latestLog);
			request.setAttribute("bmi", bmi);
		}

		//		Log latestLog = dao.findLatestByUserId(loginUser.getUser_id());
		//
		//		if (latestLog != null) {
		//			double height = loginUser.getHeight();
		//			Double heightM = height /100.0;
		//			double bmi = latestLog.getWeight() / ( heightM * heightM);
		//			request.setAttribute("latestLog",latestLog);
		//			request.setAttribute("bmi", bmi);
		//		}
		request.getRequestDispatcher("/WEB-INF/view/result.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
