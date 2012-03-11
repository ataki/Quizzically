NOTE: THIS IS A BACKUP

user: jimzheng (Zheng, Jim)
user: wawa3070 (Lin, Sydney)
user: yangyh (Yang, Yuan-Hung)
user: samir42 (Patel, Samir)

CS 108 Project Documentation

------------------------
|		Team Mach One      |
------------------------

-------------
| Features: |
-------------
# Multiple Question Types:
	We implemented all of the different question types.
	For matching, we implemented our own drag-n-drop system in javascript
		and allowed users to match elements using this system.
	For multiple answers and questions, we keep a list of saved questions
		and their various answers.
	For auto-generated questions, we keep track of when a question has been
		graded and notify the user when they next log in.
	
# Rating System:
	We keep a list of ratings for the quiz as well as a 
		"safe" rating. If the quiz is voted too many times as
		inappropriate, the "safe" rating will drop to a low 
		percentage and the quiz will be made unavailable.

# Saved Quiz Progress:
	If the user stops taking a quiz and exits, we will save the progress
	data into a separate progress table. When the user next logs back
	in we can do an IF sql-statement to check whether the progress
	data exists, and if not, then we start a new quiz.

# Real-time Updates:
	If the user receives a new message or get an important
	announcement they will receive notification of it no matter
	when they're logging onto the site. We achieve this by having
	a function poll our servlets and see if there are new changes. 
	If there are, we use JQuery to update the application and
	notify the user.

# Recommendations List:
	Friends - We create a separate servlet to run in the backend every so often,
	getting recently updated rows of our friend system and making
	recommendations for friends.
	Quiz - We also implement recommendations at the quiz level, so that users
	can get a list of quizzes they may like based on their past experience
	with taking quizzes and their friends' taken quizzes.

# Extra Pages and Functionality:
	We added a couple of extra pages to make the website feel more complete.
	These include:
		Display of a user's page.
		Error-checking and spam-checking done in jQuery.

---------------------
| Essential Features |
---------------------
	Question Types:
		Implemented question-response, fill-in-the-blank, multiple choice, 
				(EXTENSIONS):
				picture-response, multi-answer questions, multiple choice/multiple answer,
				matching, auto-generated, timed and graded.

	Quiz Options:
		Implemented one/multiple pages, randomized order, immediate corrections,
		practice quizzes.

	Scoring:
		For each question, we keep track of the # of possible points a user can earn.
		We aggregate these and find the total, then add up the total points the user
		received and add them to the database.
	
		When we finish recording everything, we place the information about the quiz
		into an "Activity" object that tracks the user's scores, when the user finished
		the quiz, and how long it took.
		
		When we need to query for top scores, we do a select call to the database and
		pull the top scores associated with a quiz_id. 
	
	Friends:
		We maintain a two-way friendship model; when a friend is first created, we
		maintain a list of blocks that are 

	Mail:
		We support the three types of messages : challenge, note, and friend request.

	Administration:
		We keep an admin.jsp view that serves all functionalities required by the
		assignment. 

	Overall Web Look:
		We designed our code with CSS3 and took pains to make the UI seem responsive,
		utilizing jQuery and web conventions to do so.
		We borrowed various components from online tutorials to make our GUI
		more interactive, incorporating tabs, modal popups, notifications, labels,
		textfields, tablesorter, buttons, etc... 
	
	Error-checking and Form Creation:
		We do detailed error-checking for form creation in addition to page-level
		checking to make sure the user never gets redirected to a bad url unless
		a malicious user decides to try and manipuale the URL.
		
	Sessions:
		All data is stored in a session variable that's visible to all servlets.
		Thus, we support multiple users by default.

----------------------------------------------
| Plugins Used (as permitted by instructor): |
----------------------------------------------
jQuery tablesorter - open-source plugin that sorts HTML tables according to an implicit ordering
jQuery foundations - provides easy "tabbing" functionality on admin/main pages.
jQuery growl - notification popup like Mac OSX growl.
Gson - Google open source library for parsing JSON to and from Java objects.


------------------
| System Details |
------------------
Communication:
	We do client-to-server communcation mostly via POST requests to a server,
		which processes the request according to a common object format that both
		our Servlet and front-end "agrees" to identify. 
	Push requests from server to client are done primarily using GSON, Google's
	Java JSON parsing library. 


	

