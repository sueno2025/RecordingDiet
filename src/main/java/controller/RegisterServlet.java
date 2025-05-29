package controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.UserDAO;
import util.PasswordUtil;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ログイン済みのユーザーがurlで飛んできた場合
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("user") != null) {
			response.sendRedirect(request.getContextPath()+"/InputServlet");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String user_name = request.getParameter("user_name").trim();
		String user_pass = request.getParameter("user_pass").trim();
		String confirm = request.getParameter("confirm").trim();
		String heightStr = request.getParameter("height");
		String genderStr = request.getParameter("gender");

		//入力の再表示用
		request.setAttribute("user_name", user_name);
		request.setAttribute("height", heightStr);
		request.setAttribute("gender", genderStr);

		//バリデーション
		if (user_name.isEmpty() || user_pass.isEmpty() || confirm.isEmpty() || heightStr.isEmpty()
				|| genderStr == null) {
			request.setAttribute("error", "すべての項目を入力してください");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
			return;

		}

		if (!user_pass.equals(confirm)) {
			request.setAttribute("error", "パスワードが一致しません");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
			return;
		}
		
		if(user_pass.length()<4) {
			request.setAttribute("error", "パスワードは４文字以上にしてください");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
			return;
		}
		
		double height;
		try {
			height= Double.parseDouble(heightStr);
			if(height < 50 || height > 250) {
				request.setAttribute("error"," 身長は50cm~250cmの範囲で入力してください");
				request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
		        return;
			}
		}catch(NumberFormatException e) {
			request.setAttribute("error", "身長の値が不正です");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
	        return;
		}
		
		char gender = genderStr.charAt(0);
		
		String salt = PasswordUtil.generateSalt();
		String hashPass = PasswordUtil.hashPasswordWithSalt(user_pass,salt);
		
		
		
		try {
			//ユーザー名の重複を確認、問題なければ登録(Mainに飛ぶか、アカウント作成完了画面を作る)
			new UserDAO().register(user_name,hashPass,salt, height, gender);
			request.getRequestDispatcher("WEB-INF/view/created_account.jsp").forward(request, response);
		} catch (SQLException e) {
			//重複した場合
			request.setAttribute("error", "そのユーザー名は既に使われています");
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("error", "予期せぬエラーが発生しました");
			e.printStackTrace();//ログを出す
			request.getRequestDispatcher("/WEB-INF/view/register.jsp").forward(request, response);
		}
	}

}
