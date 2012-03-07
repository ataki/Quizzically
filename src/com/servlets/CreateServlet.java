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
		String quizDescription = request.getParameter("quiz-description");
		String quizCategory = request.getParameter("quiz-category");
		boolean immediate_feedback = request.getParameter("immediate-correction").equals("yes") ? true : false;
		boolean randomness = request.getParameter("order").equals("yes") ? false : true;
		boolean singlePage = request.getParameter("quiz-display").equals("one") ? true : false;
		int points = Integer.parseInt(request.getParameter("num-questions"));
		// Push the quiz data into database (quiz name?), numQuestions? category? remove question?

		int dummyCategory_id = 0;
	    // TODO: Create quizCategory enum, tags
		Quiz quiz = Quiz.insert(user.getId(), quizName, quizDescription, dummyCategory_id, randomness, immediate_feedback, singlePage, points);
		parseQuestion(request, quiz.getId());
		RequestDispatcher dispatch = request.getRequestDispatcher("quiz-summary.jsp?quizId=" + quiz.getId());
		dispatch.forward(request, response);
	}

	private void parseQuestion(HttpServletRequest request, int quizId) {
		int numOfQuestions = Integer.parseInt(request.getParameter("num-questions"));
		for (int  i = 1; i <= numOfQuestions; i++) {
			String questionType = request.getParameter("question-type-" + i);
			List<String> texts = Arrays.asList(request.getParameterValues("question-" + i));
			List<String> answers = Arrays.asList(request.getParameterValues("answer-" + i));
			String imageUrl = request.getParameter("image-" + i);
			Question q = Question.insert(questionType, texts, answers, quizId, imageUrl);
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
