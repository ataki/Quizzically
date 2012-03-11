<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, com.backend.*, com.models.* " %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	/* special error checking to see if request and session are valid */
	if(request.getAttribute("special") == null) {	
		response.sendRedirect("/404.html");		
	}
	else if(!request.getAttribute("special").equals("29dd2f9f8d9312235caab2629e28ad45")) {
		response.sendRedirect("/404.html");
	}

	/* Get the managers */
	ServletContext context = request.getServletContext();
	UserManager userManager = (UserManager) context.getAttribute("userManager");
	User user = (User) request.getSession().getAttribute("user");

	if(user == null || user.getId() == User.INVALID_USER) {
	    response.sendRedirect("/login.html");
	}
	
 	AnnouncementManager announcementManager = (AnnouncementManager) context.getAttribute("announcementManager");
// 	QuizManager quizManager = (QuizManager) context.getAttribute("quizManager");
// 	QuestionManager questionManager = (QuestionManager) context.getAttribute("questionManager");
 	MessageManager messageManager = (MessageManager) context.getAttribute("messageManager");
 	ActivityManager activityManager = (ActivityManager) context.getAttribute("activityManager");
// 	AchievementManager achievementManager = (AchievementManager) context.getAttribute("achievementManager");
	
// 	/* Get Annoucements */
 	List<Announcement> announcementArray = announcementManager.getAllAnnouncement();
	
// 	/* Get Best performance for this user (highest scoring activities) */
 	List<Activity> activities = activityManager.getTopActivity();

// 	/* Get friends activities */
// 	List<Activity> friendActivities = activityManager.getRecentFriendActivity(user.getId(), userManager.getFriends(user.getId()));

// 	/* Get messages for this user */
// 	List<Message> messageArray = messageManager.getUserMessages(user.getId());
	List<Message> messageArray = null;

// 	/* get achievements */
// 	List<Achievement> achievements = achievementManager.getByUserId(user.getId());
 	List<Achievement> achievements = new ArrayList<Achievement>(); // temp until AchievementManager getByUserID works

// 	/* Get recently created quizzes for the whole site */
//	List<Quiz> recentQuizzes = quizManager.getRecent();
 	List<Quiz> recentQuizzes = new ArrayList<Quiz>(); // temp until QuizManager getRecent works
			
// 	/* Get popular quizzes for whole site */
//	List<Quiz> popularQuizzes = quizManager.getPopular();
 	List<Quiz> popularQuizzes = new ArrayList<Quiz>(); // tmp until QuizManager getPopular works
//  */
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
					<Form action="ResultServlet" method="get">
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
		
	
		<div class="row">
			<div class="twelve columns">
			<!-- TABS: SHOULD NOT HAVE TO CHANGE -->
			<div class="two columns" style="height:100%; border: solid 0 #ddd; border-width: 0px 1px 0px 0px;">
				<dl class="nice vertical tabs">
				  <dd><a href="#nice0" class="active">Home</a></dd>
				  <dd><a href="#nice1">Announcements</a></dd>
				  <dd><a href="#nice2">Popular</a></dd>
				  <dd><a href="#nice3">Create</a></dd>
				  <dd><a href="#nice4">Activity</a></dd>
				  <dd><a href="#nice5">Friends</a></dd>
				  <dd><a href="#nice6">Messages</a></dd>
				  <dd><a href="#nice7">Achievements</a></dd>
				</dl>
			</div>
			
			<div class="ten columns content" style="">
			<!-- TAB CONTENT: MODIFY AS NEEDED --->
			<ul class="nice tabs-content contained">
			  <!-- Home -->
			  <li class="active" id="nice0Tab">
			  	<h5>Home</h5>
			  </li>
			  <!-- ANNOUNCEMENTS -->
			  <li class="active" id="nice1Tab">
			  	<div class="row">
					<div class="twelve columns show-panel">
						<h3>Announcement</h3>
						<div>
							<ul id="announcement-container" style="width:75%;">
								
								<%
								Announcement.Importance localAnnouncementImportance;
								String localAnnouncement;
								if(announcementArray.isEmpty() == false) {
									for (int i = 0; i < announcementArray.size(); i++) {
										Announcement announcement = announcementArray.get(i);
										localAnnouncement = announcement.getText();
										localAnnouncementImportance = announcement.getImportance();
										%>
										<li class="announcement">
											<div class="alert-box <%=localAnnouncementImportance%>"><%=localAnnouncement%></div>
										</li>
									<% 
										} 
								}
								else {
									localAnnouncement = "No announcements!";
									localAnnouncementImportance = Announcement.Importance.LOW;
								}
								%>
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
									<th width="15%">Rating</th>
									<th width="30%">Name</th>
									<th width="15%">Questions</th>
									<th>Description</th>
								</tr>
							</thead>
							<tbody>
								<% 
								if(popularQuizzes.isEmpty() == false) {
									for(Quiz q: popularQuizzes) { %>
								<tr>
									<td><%= q.getRating() %></td>
									<td class="hover-highlight">
										<a href="QuizServlet?<%= q.getId() %>">
											<%= q.getId() %>
										</a>
									</td>
									<td><%= q.getQuestions().size() %></td>
									<td>Can you name all the capitals in the city?</td>
								</tr>
								<% } } %>
							</tbody>
						</table>
			  
			  </li>
			  <!-- END POPULAR QUIZZES -->
			  
			  <!-- RECENTLY CREATED QUIZZES -->
			  <li id="nice3Tab">
			  
				 <div class="row">
					<h5>
						<a href="quiz-create.html">
							<div class="small button">
								Create a brand new quiz
							</div>
						</a>
					</h5>
					<br/>
						<div class="title">Recently Created</div>
							<br/>
							<table width="800">
								<thead>
									<tr>
										<th width="10%">Name</th>
										<th width="10%">Author</th>
										<th width="65%">Description</th>
										<th width="15%">Time</th>
									</tr>
								</thead>
								<tbody>
									<%
									if(recentQuizzes.isEmpty() != false) {
									for (Quiz q: recentQuizzes) { %>
									<tr>
										<td><%= q.getName() %></td>
										<td><%= q.getCreator_id() %></td>
										<td><%= q.getDescription() %></td>
										<td><%= q.getTimestamp() %></td>
									</tr>
									<% } } %>
								</tbody>
							</table>
						
					</div>
			  </li>
			  <!-- END RECENTLY CREATED QUIZZES -->
			  
			  
			  <!-- USER'S RECENT ACTIVITY -->
			  <li id="nice4Tab">
				<div class="row">
					<div class="title">My Recent Activity</div>
							<br/>
							<table width="800">
								<thead>
									<tr>
										<th width="15%">Quiz Name</th>
										<th width="30%">Score</th>
										<th width="15%">Time Elapsed</th>
										<th width="15%">Recorded</th>
									</tr>
								</thead>
								<tbody>
									<% for (Activity a: activities) { 
										Quiz quiz = Quiz.fetch(a.getQuiz_id());
									%>
									<tr>
										<td><%= quiz.getName() %></td>
										<td><%= a.getScore() %></td>
										<td><%= a.getTimeTaken() %></td>
										<td><%= a.getTimestamp() %></td>
									</tr>
									<% } %>
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
									<th width="15%">From</th>
									<th width="45%">Message</th>
									<th width="30%">Time</th>
								</tr>
							</thead>
							<tbody>
							<% 
								if(messageArray.isEmpty() == false) {
								for (int i = 0; i < messageArray.size(); i++) {
									Message message = messageArray.get(i);
							%>
								<tr>
									<td class="hover-highlight"><a href="user.jsp?id=<%=message.getFromUserId()%>"><%=user.getUserName() %></a></td>
									<td><%=message.getMessage()%></td>
									<td><%=message.getTimestamp()%></td>
								</tr>
							<% 
								} }
							%>
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
										<th width="15%">Date</th>
									</tr>
								</thead>
								<tbody>
									<% for (Achievement a: achievements) { %>
									<tr>
										<td><img src="<%= a.getUrl() %>" width="48" height="48" /></td>
										<td><%= a.getAward() %></td>
										<td><%= a.getDescription() %></td>
										<td><%= a.getTimestamp().toString() %></td>
									</tr>
									<% } %>
								</tbody>
							</table>
						
					</div>
			  </li>
			 <!-- END ACHIEVEMENTS -->
			</ul>  <!-- END OF TAB CONTENT -->
			</div> <!--  close row --> 
			</div>
		</div>

		<div id="footer" class="row">
			Stanford University Winter 2012. Site powered by Google App Engine, built using Zurb Foundations, and JQuery.
		</div>
		
	</div> <!-- close container -->




	<!-- Included JS Files -->
	<script src="javascripts/jquery.min.js"></script>
	<script src="javascripts/modernizr.foundation.js"></script>
	<script src="javascripts/foundation.js"></script>
	<script src="javascripts/app.js"></script>
	<script src="javascripts/main.js"></script>

</body>
</html>
