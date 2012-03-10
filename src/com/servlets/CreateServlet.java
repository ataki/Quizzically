package com.servlets;

import java.io.IOException;
import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.models.*;
import com.models.questions.*;

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
		RequestDispatcher dispatch;
		List<BaseQuestion> questions = new ArrayList<BaseQuestion>(); 
		if (populateQuestions(request, singlePage, questions) == 0) {
			Quiz quiz = Quiz.insert(user.getId(), quizName, quizDescription, dummyCategory_id, randomness, immediate_feedback, singlePage, points, questions);
			dispatch = request.getRequestDispatcher("quiz-summary.jsp?quizId=" + quiz.getId());
		}
		else {
			dispatch = request.getRequestDispatcher("quiz-create.html");
		}
		dispatch.forward(request, response);
	}

	private int populateQuestions(HttpServletRequest request, boolean singlePage, List<BaseQuestion> questionList) {
		int numOfQuestions = Integer.parseInt(request.getParameter("num-questions"));
		for (int  i = 1; i <= numOfQuestions; i++) {
			String questionType = request.getParameter("question-type-" + i);
			List<String> questions = Arrays.asList(request.getParameterValues("question-" + i));
			List<String> answers = Arrays.asList(request.getParameterValues("answer-" + i));
			
			// Check to make sure answers and questions are both non-null values
			for (String question: questions)
				if (question == null) return i;
			for (String answer: answers)
				if (answer == null) return i;
			
			if (questionType.equals("question-response") || questionType.equals("fill-in-the-blank")) {
				questionList.add(new BaseQuestion(questions.get(0), answers.get(0)));			
			}
			else if (questionType.equals("multiple-choice")) {
				questionList.add(new MultipleChoiceQuestion(questions.get(0), answers.get(0), answers));
			}
			else if (questionType.equals("multi-answer-question")) {
				boolean randomness = request.getParameter("ordered" + i).equals("yes") ? false : true;
				questionList.add(new MultipleAnswersQuestion(questions.get(0), answers, randomness));
			}
			else if (questionType.equals("picture-response")) {
				String imageUrl = request.getParameter("image-" + i);
				if (imageUrl == null) return i;
				else questionList.add(new PictureResponseQuestion(questions.get(0), answers.get(0), imageUrl));
			}
			else if (questionType.equals("multiple-choice-multiple-answer")) {
				// Logic here to be done
				//questionList.add(new MultipleChoiceAndAnswersQuestion(questions.get(0), answers.get(0), answers));
			}
			else if (questionType.equals("matching")) {
				questionList.add(new MatchingQuestion(questions, answers));
			}
			else if (questionType.equals("auto-generated")) {
				
			}
			else { //graded-questions
				
			}
			// Set time only if the user specifies it
			if (!singlePage && request.getParameter("time-" + i) != null) {
				questionList.get(questionList.size() - 1).setTime(Integer.parseInt(request.getParameter("time-" + i)));
			}
		}
		return 0;
	}
}
