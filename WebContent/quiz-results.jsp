<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.util.*, com.backend.*, com.models.* " %>
<%
	/* Get results from our session 
	*/
	Quiz.Result res = (Quiz.Result) session.getAttribute("results");
	if(res == null) {
		response.sendRedirect("/404.html");	
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
				
			</div>
		<hr />
		</div>
		<!-- End Heading -->
		
		<div class="row">
			<div class="twelve columns">
				<h3>Results </h3>
				<br/>
				<h5><img src="images/check.png" width="25px" height="25px" style="padding-right:10px;" />Number Correct: 3</h5>
				<h5><img src="images/cross.png" width="25px" height="25px" style="padding-right:10px;" />Number Incorrect: 3</h5>
				<br/>
				<h5>Final Score: 3/6 (50%) </h5>
				<hr/>
				<br/>
				<h5>Performance Comparison:</h5><br/>
				
				<!-- RESULTS TABS -->
				<dl class="contained tabs">
				  <dd><a href="#past" class="active">Your Past</a></dd>
				  <dd><a href="#friend">Friends</a></dd>
				  <dd><a href="#top">Best Ever</a></dd>
				</dl>
				
				<!-- RESULTS TAB CONTAINER -->
				<ul class="nice tabs-content contained">
			
					<!-- YOUR OWN PAST RESULTS -->
					<li class="active" id="pastTab">
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
										
									</tbody>
						</table>
				  	</li>
				  	
				  	<!-- YOUR FRIENDS RESULTS -->
				  	<li id="friendTab" >
				  		<br/>
						<table width="800" id="friend-performance-table" class="tablesorter">
									<thead>
										<tr>
											<th width="15%">Score</th>
											<th width="15%">User</th>
											<th width="30%">Time Taken</th>
										</tr>
									</thead>
									<tbody>
										<tr class="hover-highlight">
												<td>233</td>
												<td><a href="UserServlet?">FreeBSD</a></td>
												<td>2424 seconds</td>
										</tr>
										
									</tbody>
						</table>
				  	</li>
				  	
				  	<!-- USERS' BEST RESULTS -->
				  	<li id="topTab" class="tablesorter">
						<br/>
							<table width="800" id="top-performance-table" >
										<thead>
											<tr>
												<th width="15%">Score</th>
												<th width="15%">User</th>
												<th width="30%">Time Taken</th>
											</tr>
										</thead>
										<tbody>
											<tr class="hover-highlight">
												<td>1000</td>
												<td><a href="UserServlet?">quantum3023</a></td>
												<td>3000 seconds</td>
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

</body>
</html>
