<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, com.backend.MessageManager " %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	User username = (User) request.getSession().getAttribute("User");
	
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
					<Form>
						<input class="round" id="searchbar" type="search" size="50" name="searchbar" placeholder="Search for friends, quizzes, and more" />
					</Form> 
				</div>
				
				<div class="six columns">
					<p style="padding-top:80px; padding-left:180px;"><a href="/LogoutServlet" class="nice radius blue button" >Logout</a></p>
					<div style="padding-top:30px; margin-left:-50px;">
					
					</div>
				</div>
			</div>
		<hr />
		</div>
		<!-- End Heading -->
		
		<!-- TABS: SHOULD NOT HAVE TO CHANGE -->
		<div class="row">
			<div>
			<dl class="contained tabs">
			  <dd><a href="#nice1" class="active">Home</a></dd>
			  <dd><a href="#nice2">Popular</a></dd>
			  <dd><a href="#nice3">Create</a></dd>
			  <dd><a href="#nice4">Activity</a></dd>
			  <dd><a href="#nice5">Friends</a></dd>
			  <dd><a href="#nice6">Messages</a></dd>
			  <dd><a href="#nice7">Achievements</a></dd>
			</dl>
			</div>
			
			<!-- TAB CONTENT: MODIFY AS NEEDED --->
			<ul class="nice tabs-content contained">
			
			  <!-- ANNOUNCEMENTS -->
			  <li class="active" id="nice1Tab">
			  	<div class="row">
					<div class="twelve columns show-panel">
						<h3>Annoucements</h3>
						<div>
							<ul id="announcement-container" style="width:75%;">
							
								<li class="announcement" id="333333"><div class="alert-box success">Here is an announcement. <br/><a href="" class="close">&times;</a>
									Quiz changes are coming! We're happy to announce that we're bringing a social component to the whole site.
									In addition, we're going to revamp our messaging and challenge system so that you can better 
								</div></li>
								<li class="announcement" id="444444"><div class="alert-box">Here is a less important announcement<a href="" class="close">&times;</a></div></li>
								<li class="announcement" id="555555"><div class="alert-box success">Here is an announcement.<br/> <a href="" class="close">&times;</a></div></li>
								<li class="announcement" id="6666"><div class="alert-box error">Here is an important announcement. Your account may have been hacked into
									by an anonymous third party. Please verify your information by going to the authentication pary of our wsebsite now.<a href="" class="close">&times;</a></div></li>
								<li class="announcement" id="8888"><div class="alert-box success">Here is an announcement<a href="" class="close">&times;</a></div></li>
							</ul>
						</div>
					</div>
				</div>
			  </li>
			  <!-- END ANNOUNCEMENTS -->
			  
			  
			  <!-- POPULAR QUIZZES -->
			  <li id="nice2Tab">
			  			<h3>Popular</h3>
						<table width="800">
							<thead>
								<tr>
									<th width="15%"># Users Taken</th>
									<th width="30%">Name</th>
									<th width="15%">Questions</th>
									<th>Description</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>144</td>
									<td class="hover-highlight"><a href="QuizServlet?<%= username%>">Around the World</a></b></td>
									<td>34</td>
									<td>Can you name all the capitals in the city?</td>
								</tr>
								<tr>
									<td>144</td>
									<td class="hover-highlight"><a href="QuizServlet?<%= username%>">Around the World</a></b></td>
									<td>34</td>
									<td>Can you name all the capitals in the city?</td>
								</tr>
								<tr>
									<td>144</td>
									<td class="hover-highlight"><a href="QuizServlet?<%= username%>">Around the World</a></b></td>
									<td>34</td>
									<td>Can you name all the capitals in the city?</td>
								</tr>
								
							</tbody>
						</table>
			  
			  </li>
			  <!-- END POPULAR QUIZZES -->
			  
			  <!-- USER'S RECENTLY CREATED QUIZZES -->
			  <li id="nice3Tab">
			  
				 <div class="row">
					<h5><a href="quiz-create.html"><div class="small button">Create a brand new quiz</div></a>
					</h5>
					<br/>
						<div class="title">Recently Created</div>
							<br/>
							<table width="800">
								<thead>
									<tr>
										<th width="15%">Type</th>
										<th width="30%">From</th>
										<th width="15%">Message</th>
										<th widht="15%">Time</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>Challenge</td>
										<td class="hover-highlight"><a href="QuizServlet?<%=username%>">Around the World</a></b></td>
										<td></td>
										<td>Can you name all the capitals in the city?</td>
									</tr>
									<tr>
										<td>144</td>
										<td class="hover-highlight"><a href="QuizServlet?<%=username%>">Around the World</a></b></td>
										<td>34</td>
										<td>Can you name all the capitals in the city?</td>
									</tr>
									<tr>
										<td>144</td>
										<td class="hover-highlight"><a href="QuizServlet?<%=username%>">Around the World</a></b></td>
										<td>34</td>
										<td>Can you name all the capitals in the city?</td>
									</tr>
									
								</tbody>
							</table>
						
					</div>
			  </li>
			  <!-- END USER'S RECENTLY CREATED QUIZZES -->
			  
			  
			  <!-- USER'S RECENT ACTIVITY -->
			  <li id="nice4Tab">
				<div class="row">
					<div class="title">My Recent Activity</div>
							<br/>
							<table width="800">
								<thead>
									<tr>
										<th width="15%">Type</th>
										<th width="30%">From</th>
										<th width="15%">Message</th>
										<th widht="15%">Time</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>Challenge</td>
										<td class="hover-highlight"><a href="QuizServlet?<%=username%>">Around the World</a></b></td>
										<td></td>
										<td>Can you name all the capitals in the city?</td>
									</tr>
								</tbody>
							</table>
					</div>
				</li>
			  <!-- END USER'S RECENT ACTIVITY -->
			
			
			  <!-- FRIENDS' RECENT ACTIVITY -->
			  <li id="nice5Tab">
			  	<div class="row">
					<div class="twelve columns show-panel">
						<h3>Friends Activity</h3>
					
						<!-- Grid Example -->
						<div class="row">
							<div class="four columns show-panel">
								<div class="panel">
									400px
								</div>
							</div>
							<div class="four columns show-panel">
								<div class="panel"> 
									400px
								</div>
							</div>
							<div class="four columns show-panel">
								<div class="panel"> 
									400px
								</div>
							</div>
						</div>
					</div>
				</div>
			  </li>
			   <!-- END FRIENDS' RECENT ACTIVITY -->
			  
			   <!-- MESSAGES -->
			  <li id="nice6Tab">
			  
				<table width="800">
							<thead>
								<tr>
									<th width="15%">Type</th>
									<th width="30%">From</th>
									<th width="15%">Message</th>
									<th widht="15%">Time</th>
								</tr>
							</thead>
							<tbody>
								<tr class="hover-highlight">
									<td>Challenge</td>
									<td><a href="QuizServlet?<%= username%>">Around the World</a></b></td>
									<td></td>
									<td>Can you name all the capitals in the city?</td>
								</tr>
								<tr class="hover-highlight">
									<td>144</td>
									<td><a href="QuizServlet?<%= username%>">Around the World</a></b></td>
									<td>34</td>
									<td>Can you name all the capitals in the city?</td>
								</tr>
								<tr class="hover-highlight">
									<td>144</td>
									<td><a href="QuizServlet?<%= username%>">Around the World</a></b></td>
									<td>34</td>
									<td>Can you name all the capitals in the city?</td>
								</tr>
								
							</tbody>
				</table>
						
				</li>
			  <!-- FRIENDS' RECENT ACTIVITY -->
			
			  <!-- ACHIEVEMENTS -->
			  <li id="nice7Tab">
			  <div class="row">
					<div class="title">Recently Created</div>
							<br/>
							<table width="800">
								<thead>
									<tr>
										<th width="15%"></th>
										<th width="20%">Award</th>
										<th width="40%">Description</th>
										<th widht="15%">Date</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><img src="http://a.dryicons.com/images/icon_sets/luna_blue_icons/png/128x128/prize_winner.png" width="48" height="48" /></td>
										<td>Fastest Solver</td>
										<td>You are the fastest at solving a quiz of <b>Medium</b> difficulty</td>
										<td>Feb 3 2012</td>
									</tr>
									<tr>
										<td><img src="http://a.dryicons.com/images/icon_sets/luna_blue_icons/png/128x128/prize_winner.png" width="48" height="48" /></td>
										<td>Fastest Solver</td>
										<td>You are the fastest at solving a quiz of <b>Medium</b> difficulty</td>
										<td>Feb 3 2012</td>
									</tr>
									
								</tbody>
							</table>
						
					</div>
			  </li>
			 <!-- END ACHIEVEMENTS -->
			</ul>  <!-- END OF TAB CONTENT -->
		</div>

		<div id="footer" class="row">
			Stanford University Winter 2012. Site powered by Google App Engine, built using Zurb Foundations, and JQuery.

	</div>
	<!-- container -->




	<!-- Included JS Files -->
	<script src="javascripts/jquery.min.js"></script>
	<script src="javascripts/modernizr.foundation.js"></script>
	<script src="javascripts/foundation.js"></script>
	<script src="javascripts/app.js"></script>
	<script src="javascripts/main.js"></script>

</body>
</html>