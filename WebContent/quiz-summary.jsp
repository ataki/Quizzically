<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, com.backend.*, com.models.* " %>
<!DOCTYPE html>

<%
	int quizId = Integer.parseInt(request.getParameter("quizId"));
	Quiz quiz = Quiz.fetch(quizId);
	System.out.println(quizId);

	System.out.println(quiz);
%>

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
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.min.js"></script>
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
						
					</p>
				</div>
			</div>
		<hr />
		</div>
		<!-- End Heading -->
		
		<div id="myModal" class="reveal-modal">
			<h1>Are you sure you want to exit this quiz?</h1>
			<p>Exiting will cause you to lose all data, including progress, question, and more.</p>
			<!-- Don't know why but can't click properly on the link, so must use Javascript -->
			<a href="main.html" class="nice radius red button" data-reveal-id="myModal" data-animation="none" style="margin-top:30px" 
				onclick="self.location='main.html'">Exit</a>
			<a class="close-reveal-modal">&#215;</a>
		</div>
		
		
		<div class="row" style="min-width:1000px;">
			<div class="twelve columns">
				<h3><%=quiz.getName()%></h3>
				
				<div class="row">
					<div class="four columns" style="padding:20px; max-width:300px;">
					
						<!--
						////////////////////////////////////////////////////////////////////
						LINKS: 
						Sometimes clicking on buttons should logically take you to a 
						specific webpage to view a specific userid. 
						///////////////////////////////////////////////////////////////////
						
						Make sure that the servlet takes care of this by appending the proper
						id to whatever links necessitate them.
						 -->				
					
						<h5>By <a href="UserServlet/94834" style="margin-right:15px;"><%=quiz.getCreator_id() //TODO: Need to create a UserLite%></a>
							<a href="QuizServlet/23423" title="action=view" class="radius button">Take!</a>
						</h5>
						<br/>
						
						<h6><%=quiz.getDescription()%></h6>
						
					<ul class='star-rating'>
					  <li><a href='#' title='Rate this 1 star out of 5' class='one-star star-child'>1</a></li>
					  <li><a href='#' title='Rate this 2 stars out of 5' class='two-stars star-child'>2</a></li>
					  <li><a href='#' title='Rate this 3 stars out of 5'  class='three-stars star-child'>3</a></li>
					  <li><a href='#' title='Rate this 4 stars out of 5' class='four-stars star-child'>4</a></li>
					  <li><a href='#' title='Rate this 5 stars out of 5' class='five-stars star-child'>5</a></li>
					</ul>
						
					<br/>
					</div>
					<a class="columns gray box bluehover-highlight" style="min-width:175px; min-height:85px; width:175px; height:85px; overflow:hidden">2430<br/><br/><h6>Taken</h6></a>
					<a class="columns blue box hover-highlight" style="width:175px; height:85px; overflow:hidden">10<br/><br/><h6>Difficulty</h6></a>
					<a class="columns pink box pinkhover-highlight" style="width:175px; height:85px;  float:left; overflow:hidden">67%<br/><br/><h6>Highest Score</h6></a>
				</div>
				<br/>
				<blockquote>Tags: <a class="round tag" href="TagServlet/world">#world</a> 
								<a class="round tag" href="TagServlet/geography">#georgraphy</a> 
								<a class="round tag" href="TagServlet/news">#news</a> 
								<a class="round tag" href="TagServlet/colors" >#colors</a>
								<a class="round tag" href="TagServlet/interest">#interest</a> 
								<a class="round tag" href="TagServlet/birds" >#birds</a> 
				</blockquote>
				<hr/>
				<br/>
				<h5>Performance Comparison:</h5><br/>
				
				<!-- RESULTS TABS -->
				<dl class="contained tabs">
				  <dd><a href="#past" class="active">Your Past</a></dd>
				  <dd><a href="#top">Best Ever</a></dd>
				  <dd><a href="#last">Most Recent</a></dd>
				  <dd><a href="#lastDay">Top Last Day</a></dd>
				  <dd><a href="#stats">Statistics</a></dd>
				</dl>
				
				<!-- RESULTS TAB CONTAINER -->
				<ul class="nice tabs-content contained">
			
					<!-- YOUR OWN PAST RESULTS -->
					<li id="pastTab">
				  		<br/>
						<table width="800" id="user-performance-table" class="tablesorter">
									<thead>
										<tr>
											<th width="15%">Score</th>
											<th width="30%">Time Taken</th>
											<th width="15%">Date</th>
										</tr>
									</thead>
									<tbody>
										<tr class="hover-highlight">
												<td>231</td>
												<td>4344 seconds</td>
												<td>Feb 12 2012</td>
										</tr>
										<tr class="hover-highlight">
												<td>232</td>
												<td>4344 seconds</td>
												<td>Feb 12 2012</td>
										</tr>
										<tr class="hover-highlight">
												<td>234</td>
												<td>4344 seconds</td>
												<td>Feb 12 2012</td>
										</tr>
										
									</tbody>
						</table>
				  	</li>
				  	
				  	<!-- USERS' BEST RESULTS -->
				  	<li id="topTab">
						<br/>
							<table width="800"  id="top-performance-table" class="tablesorter">
										<thead>
											<tr>
												<th width="15%">Score</th>
												<th width="15%">User</td>
												<th width="30%">Time Taken</th>
											</tr>
										</thead>
										<tbody>
											<tr class="hover-highlight">
												<td>1000</td>
												<td><a href="UserServlet?">quantum3023</a></b></td>
												<td>3000 seconds</td>
											</tr>
										</tbody>
							</table>
				  	</li>
				  	
				  	<!-- MOST RECENT REGARDLESS OF RESULTS -->
					<li id="lastTab" >
				  		<br/>
						<table width="800" id="last-performance-table" class="tablesorter">
									<thead>
										<tr>
											<th width="15%">Score</th>
											<th width="30%">Time Taken</th>
											<th width="15%">Date</th>
										</tr>
									</thead>
									<tbody>
										<tr class="hover-highlight">
												<td>231</td>
												<td>4344 seconds</td>
												<td>Feb 12 2012</td>
											</tr>
										
									</tbody>
						</table>
				  	</li>
				  	
				  	<!-- TOP LAST DAY RESULTS -->
					<li id="lastDayTab" >
				  		<br/>
						<table width="800" id="top-last-day-performance-table" class="tablesorter">
									<thead>
										<tr>
											<th width="15%">Score</th>
											<th width="30%">Time Taken</th>
											<th width="15%">Date</th>
										</tr>
									</thead>
									<tbody>
										<tr class="hover-highlight">
												<td>222</td>
												<td>35223 seconds</td>
												<td>Mar 23 2012</td>
											</tr>
										
									</tbody>
						</table>
				  	</li>
				  	
				  	<!-- TOP LAST DAY RESULTS -->
					<li class="active" id="statsTab">
				  		<br/>
						<table width="800" class="tablesorter">
									<thead>
										<tr>
											<th width="15%">Score</th>
											<th width="30%">Time Taken</th>
											<th width="15%">Date</th>
										</tr>
									</thead>
									<tbody>
										<tr class="hover-highlight">
												<td>231</td>
												<td>4344 seconds</td>
												<td>Feb 12 2012</td>
											</tr>
										
									</tbody>
						</table>
				  	</li>
				  	
				</ul>
				
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
	<script src="javascripts/core.js"></script>
	<script src="javascripts/drag-n-drop.js"></script>
	<script src="javascripts/quiz.js"></script>
	<script src="javascripts/jquery.tablesorter.min.js"></script>
	
</body>
</html>
