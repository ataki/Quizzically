package com.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.models.*;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/CreateServlet")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		User user = (User) request.getSession().getAttribute("user");
		
		String quizName = request.getParameter("quiz-name");
		String quizType = request.getParameter("quiz-display");
		String quizDescription = request.getParameter("quiz-description");
		String quizCategory = request.getParameter("quiz-category");
		boolean immediateCorrection = request.getParameter("immediate-correction").equals("yes") ? true : false;
		boolean randomness = request.getParameter("order").equals("yes") ? false : true;
		// Push the quiz data into database (quiz name?), numQuestions? category? remove question?
		
		Quiz quiz = new Quiz();
		int quizId = -1;
		try {
			quizId = quiz.quizUpload(user.getUserName(), quizName, quizDescription, quizCategory, null, randomness);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (quizId != -1)
			parseQuestion(request, quizId);

		RequestDispatcher dispatch = request.getRequestDispatcher("quiz-summary.jsp?quizId=" + quizId);
		dispatch.forward(request, response);
	}

	private void parseQuestion(HttpServletRequest request, int quizId) {
		int numOfQuestions = Integer.parseInt(request.getParameter("num-questions"));
		for (int  i = 1; i <= numOfQuestions; i++) {
			String questionType = request.getParameter("question-type-" + i);
			List<String> texts = Arrays.asList(request.getParameterValues("question-" + i));
			List<String> answers = Arrays.asList(request.getParameterValues("answer-" + i));
			
			/*
			if (questionType.equals("question-response")) {
				
			}
			else if (questionType.equals("multi-answer-question")) {
				
			}
			else if (questionType.equals("picture-response")) {
				
			}
			else if (questionType.equals("multiple-choice-multiple-answer")) {
				
			}
			else if (questionType.equals("matching")) {
				
			}
			else if (questionType.equals("auto-generated")) {
				
			}
			else { //graded-questions
				
			}*/
		}
	}
}
