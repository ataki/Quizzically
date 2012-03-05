package com.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.models.User;

//Author: Samir Patel

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		if(user != null) {
			RequestDispatcher rd = request.getRequestDispatcher("/main.jsp");
			request.setAttribute("special", "29dd2f9f8d9312235caab2629e28ad45");
			rd.forward(request, response);
		}

		String usr = (String) request.getParameter("Field0");
		String pwd = (String) request.getParameter("Field1");

		user = User.authenticateUser(usr, pwd);

		if(user == null || user.getId() == User.INVALID_USER) {
			RequestDispatcher rd = request.getRequestDispatcher("/login.html");
			rd.forward(request, response);
		}
		else {
			session.setAttribute("user", user);
			RequestDispatcher rd = request.getRequestDispatcher("/main.jsp");
			request.setAttribute("special", "29dd2f9f8d9312235caab2629e28ad45");
			rd.forward(request, response);
		}
	}

}
