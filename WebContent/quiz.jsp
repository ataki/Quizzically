<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, com.backend.*, com.models.* " %>
<%
	/* Initial Page Setup 
	* When taking the quiz, we find the current question.
	* Depending on the question type, we print out
	* a certain section of this JSP file that corresponds
	* to the question type. 
	*/
	int curQuestionNum = (Integer) session.getAttribute("curQuestion");
	Quiz quiz = (Quiz) session.getAttribute("quiz");
	Question question = quiz.nextQuestion(curQuestionNum);
	if(question == null) {
		// no more questions ! 
		// we've finished, so redirect to jsp
		String redirectURL = "/quiz-results.jsp";
	    response.sendRedirect(redirectURL);
	}
%>    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<!-- paulirish.com/2008/conditional-stylesheets-vs-css-hacks-answer-neither/ -->
<!--[if lt IE 7]> <html class="no-js lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]>    <html class="no-js lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]>    <html class="no-js lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en"> <!--<![endif]-->
<head>
	<meta charset="utf-8" />

	<!-- Set the viewport width to device width for mobile -->
	<meta name="viewport" content="width=device-width" />

	<title>Welcome to Foundation</title>
  
	<!-- Included CSS Files -->
	<link rel="stylesheet" href="stylesheets/foundation.css">
	<link rel="stylesheet" href="stylesheets/app.css">
	<link rel="stylesheet" href="stylesheets/reveal.css">
	<script src="javascripts/jquery.min.js" type="text/javascript"></script>
	<script src="javascripts/jquery.reveal.js" type="text/javascript"></script>

	<!--[if lt IE 9]>
		<link rel="stylesheet" href="stylesheets/ie.css">
	<![endif]-->


	<!-- IE Fix for HTML5 Tags -->
	<!--[if lt IE 9]>
		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->

</head>
<body>

	<!-- container -->
	<div class="container">
		
		<!-- Heading -->
		<div class="row" style="padding-top:30px">
			<div class="twelve columns">
				<div class="six columns">
					<div><img src="images/quizzically-logo-small.png" /></div>
					<p>Powered by Team Mach-One, cs108, Stanford University</p>
				</div>
				
				<div class="six columns">
					<p style="padding-top:80px; padding-left:180px;">
						<a href="/LogoutServlet" class="nice radius blue button" data-reveal-id="myModal" data-animation="none">Exit Quiz</a>
					</p>
				</div>
			</div>
		<hr />
		</div>
		<!-- End Heading -->
		
		<div id="myModal" class="reveal-modal">
			<h4>Are you sure you want to exit this quiz?</h4>
			<h6>Exiting will cause you to lose all data, including progress, question, and more.</h6>
			<form action="QuizServlet" method="POST">
				<input type="submit" class="nice radius red button" style="margin-top:30px" value="Exit" />	
			</form>
			<a class="close-reveal-modal">&#215;</a>
		</div>
		
		<div class="row">
		<!-- 
			Main questions container; distinguish between 
			types of questions
		 -->
			<div class="twelve columns">
			
			<% if (question.getType().equals(Question.Type.QuestionResponse)) { %>
				 <!-- Question Response -->
				<h6 class="undo-matching"><a>[-] Undo Matching</a></h6>
				<div class="twelve column">
					<form id="question">
					<!--  TODO  -->
					<br/><br/>
					<input type="submit" id="submit-button" class="blue button" value="Next&nbsp;&nbsp;&#187;" />
					</form>
				</div>
			<% } %>
			
			<% if (question.getType().equals(Question.Type.FillInTheBlank)) { %>
				 <!-- Fill-in-the-Blank -->
				<h6 class="undo-matching"><a>[-] Undo Matching</a></h6>
				<div class="twelve column">
					<form id="question">
					<!--  TODO  -->
					<br/><br/>
					<input type="submit" id="submit-button" class="blue button" value="Next&nbsp;&nbsp;&#187;" />
					</form>
				</div>
			<% } %>
			
			<% if (question.getType().equals(Question.Type.MultiChoice)) { %>
				 <!-- Multiple Choice -->
				<h6 class="undo-matching"><a>[-] Undo Matching</a></h6>
				<div class="twelve column">
					<form id="question">
					<!--  TODO  -->
					<br/><br/>
					<input type="submit" id="submit-button" class="blue button" value="Next&nbsp;&nbsp;&#187;" />
					</form>
				</div>
			<% } %>
			
			<% if (question.getType().equals(Question.Type.Matching)) { %>
				 
				<!-- Matching : Draggable Container Div -->
				 
				<h6 class="undo-matching"><a>[-] Undo Matching</a></h6>
				<div class="twelve column">
					<form id="question">
					<div class="draggable" id="matchquestions">
						<h5 class="draggable-ignore questionBegin">Match these items:</h5>
						<!--
							The matching questions. Drag these on top of 
							answers to form a "match"  
						 -->
						<% for(int i =0; i < question.getTexts().size(); i++) { %>
							<div class="question"><span class="name draggable-ignore"><%= i %></span><span class="info draggable-ignore"><%= question.getTexts().get(i) %></span></div>
						<% } %>
						
						<br/><br/>
						<h5 class="draggable-ignore answerBegin">... to these items:</h5>
						<!--
							The matching answers. Cannot be dragged; 
						 -->
						<% for(int i = 0; i < question.getAnswers().size(); i++) { %>
							<div class="answer"><span class="name draggable-ignore "><%=i %></span><span class="info draggable-ignore"><%= question.getAnswers().get(i) %></span></div>
						<% } %>
					
					</div>
					<br/><br/>
					<input type="submit" id="submit-button" class="blue button" value="Next&nbsp;&nbsp;&#187;" />
					</form>
				</div>
			<% } %>
			
			</div>
		</div>
		
		<div id="footer" class="row">
			Stanford University Winter 2012. Site powered by Google App Engine, built using Zurb Foundations, and JQuery.
		</div>
		
	</div>
	<!-- container -->

	<!-- Included JS Files -->
	<script src="javascripts/jquery.min.js"></script>
	<script src="javascripts/modernizr.foundation.js"></script>
	<script src="javascripts/foundation.js"></script>
	<script src="javascripts/app.js"></script>
	<script src="javascripts/main.js"></script>
	<script src="javascripts/quiz.js"></script>
	<script src="javascripts/drag-n-drop.js"></script>
	<script src="javascripts/core.js"></script>

</body>
</html>