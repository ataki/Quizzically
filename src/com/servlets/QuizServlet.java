package com.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.models.Question;
import com.models.Quiz;
import com.models.User;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//error checking first
		String curQuestionStr = (String)request.getSession().getAttribute("curQuestion");
		if(curQuestionStr == null){}
			//redirrect to error checking

		
		//setup session info
		User user = (User)request.getSession().getAttribute("user");
		String quizIdStr = (String)request.getParameter("quiz_id");
		int quizId = Integer.parseInt(quizIdStr);
		int curQuestion = 0;
		int curScore = 0;
		int totalScore = 0;
		List<Question> questions = (List<Question>) Quiz.fetch(quizId).getQuestions();
		//pass back to session
		request.getSession().setAttribute("curQuestion", curQuestion);
		request.getSession().setAttribute("questions", questions);
		request.getSession().setAttribute("curScore", curScore);
		request.getSession().setAttribute("totalScore", totalScore);
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
