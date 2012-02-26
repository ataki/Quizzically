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

----------------------------------------------
| Plugins Used (as permitted by instructor): |
----------------------------------------------
jQuery tablesorter - a plugin that sorts HTML tables according to an implicit ordering
jQuery foundations - provides easy "tabbing" functionality on admin/main pages.
jQuery growl - notification popup like Mac OSX growl.
simple-json - Java Library for parsing JSON.




