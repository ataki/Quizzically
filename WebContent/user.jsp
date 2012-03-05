<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, com.backend.*, com.models.* " %>
    
<!DOCTYPE html>

<%
	int userId = Integer.parseInt(request.getParameter("id"));
	User user = (User)session.getAttribute("user");
	/*
	Past Performance
				  <dd><a href="#top">Best Ever</a></dd>
				  <dd><a href="#last">Most Recent</a></dd>
				  <dd><a href="#lastDay">Top Last Day</a></dd>
				  <dd><a href="#stats">Statistics</a></dd>
	*/
	/* Get user tags */
	List<Tag> tags = user.tagManager.fetchTagsForUser(user.getId());
	
	/* Get recent performance */
	
	/* Get best performances */
	
	/* Get past performance */
	
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
		
		
		<div class="row">
			<div class="twelve columns">
				<h3>
					<%= user.getUsername() %>
				</h3>
				
				<h6>
					<form id="friend" action="MessageServlet" method="post">
					<input type="hidden" name="friend" value="<%= user.getId() %>" />
					<input type="hidden" name="type" value="friend" />
					<input class="small green button" type="submit" value="Friend Request" />  
					
					<a href="mailto:<%= user.getEmail() %>" style="margin-right:30px; margin-left:10px; ">Email:<%= user.email  %></a>
					<a>Last Seen: 12 Feb 2012</a>
					</form>
				</h6>
				
				
				<div class="row">
					<div class="four columns" style="padding:20px;">
					
						<!--
						////////////////////////////////////////////////////////////////////
						LINKS: 
						Sometimes clicking on buttons should logically take you to a 
						specific webpage to view a specific userid. 
						///////////////////////////////////////////////////////////////////
						
						Make sure that the servlet takes care of this by appending the proper
						id to whatever links necessitate them.
						 -->				
						
						<h6><%= user.getDescription() %> </h6>
						<blockquote>Tags:
							<% for(Tag t : tags) { %> 
								<a class="round tag" href="ResultServlet/<%= t.tag %>">#<%= t.tag %></a> 
							<% } %> 
						</blockquote>
					</div>
					<a class="four columns gray box bluehover-highlight" style="width:175px; height:85px;"><%= user.getNumQuizzesTaken() %><br/><br/><h6>Quizzes Taken</h6></a>
					<a class="four columns blue box hover-highlight" style="width:175px; height:85px;">3.4<br/><br/><h6><%= u %></h6></a>
					<a class="four columns pink box pinkhover-highlight" style="width:175px; height:85px;  float:left;">100%<br/><br/><h6>Highest Score</h6></a>
				</div>
				<br/>
				
				<hr/>
				<br/>
				<h5>Performance Comparison:</h5><br/>
				
				<!-- RESULTS TABS -->
				<dl class="contained tabs">
				  <dd><a href="#past" class="active">Past Performance</a></dd>
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
