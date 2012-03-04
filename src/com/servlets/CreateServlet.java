package com.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String quizType = request.getParameter("quiz-display");
		boolean immediateCorrection = request.getParameter("immediate-correction").equals("yes") ? true : false;
		boolean inOrder = request.getParameter("order").equals("yes") ? true : false;
		// Push the quiz data into database (quiz name?), numQuestions? category? remove question?
		
		parseQuestion(request, response);

		RequestDispatcher dispatch = request.getRequestDispatcher("quiz-summary.html");
		dispatch.forward(request, response);
	}

	private void parseQuestion(HttpServletRequest request, HttpServletResponse response) {
		int numOfQuestions = 0;
		for (int  i = 1; i <= numOfQuestions; i++) {
			String questionType = request.getParameter("question-type-" + i);
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
				
			}
			
		}
	}
}
