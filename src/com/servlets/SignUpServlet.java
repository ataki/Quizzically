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

/**
 * Servlet implementation class SignUpServlet
 */
@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
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

		String usr = (String) request.getParameter("Field0");
		String email = (String) request.getParameter("Field1");
		String pwd1 = (String) request.getParameter("Field2");
		String pwd2 = (String) request.getParameter("Field3");
		
		if(pwd1.equals(pwd2) == false) {
			RequestDispatcher rd = request.getRequestDispatcher("/signup.html");
			rd.forward(request, response);
		}

		User user = User.createUser(usr, email, pwd1);

		if(user == null || user.getId() == User.INVALID_USER) {
			RequestDispatcher rd = request.getRequestDispatcher("/signup.html");
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
