/* Main.js ------------------------
 * CS 108 Stanford Final Project
 *
 * Core quiz application logic goes
 * here. Specifically, retrieval of
 * information from server to client
 * page, as well as enabling rating
 * functionality, searching for 
 * friends, quiz timer countdown, etc...
 * 
 * --------------------------------*/

$(document).ready(function() {
	if(window.Mach != null) {
	
		/* Use JQuery's extend() to help
		* us add to the global MACH function 
		*/
		$.extend(true, window.Mach, {
			/* URL's for various servers
			* These need to be responsive to GET
			* requests. Should return data in
			* JSON format.
			*/
			messageServer:"MessageServlet",
			quizServer:"QuizServlet",
			friendServer:"FriendServlet",
			ratingServer:"RatingServlet",
			announcementServer:"AnnouncementServlet",
			quizDataServer:"QuizDataServlet", /* not used*/
			
			/* Container class names */
			messageContainer:"message-container",
			popularQuizContainer:"popular-quiz-container",
			recentQuizContainer:"recent-quiz-container",
			announcementContainer:"announcement-container",
			recentActivityContainer:"recent-activity-container",
			friendsActivityContainer:"friends-activity-container",
			achievementsContainer:"achievements-container",
			
		});
	}
	/* If we don't detect windows.MACH, just abort*/
	else return;

	/* RETRIEVE DATA ----------------------
	Use JSON to retrieve data from the server.
	Perhaps will be needed at some point
	if we want to get fancy 
	-------------------------------------- */
	// RetrieveFromServer();
		
	/* FRIENDSHIP ------------------------- 
	Attaches a friending function to all buttons
	that require it
	---------------------------------------- */
	$('.friend-confirm-button').click(function(event) {
		var friendName = $(this).parent().attr('id');
		$.post(Mach.friendServer, { user: Mach.username, friend:friendName });
		var elemPtr = this;
		$(this).fadeTo('fast', 0, function() {
			$(elemPtr).remove();
		});
	});
   	
	/* STAR RATINGS --------------------- 
	Functionality: Ratings
	Retrieves rating attached to quiz, sends
	POST request to our rating server, and then
	replace the variable rating with a permanent rating.
	Rating-select callback: 
	Get the first id of the parent of the selected
	and treat this as the quiz-title identifier
	for the rating you want to receive
	------------------------------------- */
	
	$('.star-child').click(function() {
		var score = parseInt(str_find_digits($(this).parent().html())[0]);
		var parent_id = find_parent_who_has_id(this);
		$.post(Mach.ratingServer, { quiz:parent_id, rating:score });
		var elemParent = $(this).parent();
		$(this).replaceWith(generateFixedStar_Str);
	});
	
	
	/* COUNTDOWN ---------------------
	Functionality: Timed Questions
	Handles countdown if there is one for this page.
	Normally this clogs up the main thread, so please
	don't use it. Also not extremely accurate as there
	can be blocking I/O events.
	----------------------------------- */
	if($('#countdown') != null) {
		setInterval(function() {
			var curCount = parseInt($('#countdown').html());
			$('#countdown').html(curCount--);	
		}, 1000);
	}
	
});

/* pushes form data into quiz container based on certain
* parameters 
*/
function appendQuiz() {
	$('#' + Mach.createForm.name).empty();
	
}

/* Bunch of code that retrieves 
* data from server and appends it 
* to our containers. Don't need to
* actually use this function unless
* we're doing something fancy.
*/
function RetrieveFromServer() {
	/* MESSAGE DATA ------------------------
	(can be used as ex for getting other data from servlet)
	Parses some message data from the server, and appends
	this data to the message tab container.
	In real life, we would only include this security
	key in the js file that a logged-in user sees.
	Security key is unique for each user.
	---------------------------------------- */
	if(Mach.securityKey != null) {
		$.getJSON(Mach.messageServer, { user: Mach.username , securityKey: Mach.securityKey },
			function(data) {
			  var items = [];
			  $.each(data, function(key, val) {
				items.push('<td id="' + key + '">' + val + '</td>');
			  });
			
			  $('<tr/>', {
				'class': 'my-new-list',
				html: items.join('')
			  }).appendTo(Mach.messageContainer);
		});
	}
	
	/* QUIZ DATA ---------------------------
	Populates POPULAR/RECENT tables with the 
	necessary quiz data
	-------------------------------------- */
	if(Mach.securityKey != null) {
		$.getJSON(Mach.quizDataServer, { user:Mach.username, securityKey: Mach.security, type:"popular" },
			function(data) {
			  var items = [];
			  $.each(data, function(key, val) {
				items.push('<td id="' + key + '">' + val + '</td>');
			  });
			
			  $('<tr/>', {
				'class': 'popular-quizzes',
				html: items.join('')
			  }).appendTo(Mach.popularQuizContainer);
		});
		$.getJSON(Mach.quizDataServer, { user:Mach.username, securityKey: Mach.security, type:"recent"},
			function(data) {
			  var items = [];
			  $.each(data, function(key, val) {
				items.push('<td id="' + key + '">' + val + '</td>');
			  });
			
			  $('<tr/>', {
				'class': 'recent-quizzes',
				html: items.join('')
			  }).appendTo(Mach.recentQuizContainer);
		});
	}
	
	/* USER ACTIIVTY ----------------------
	Retrieves user activity and friends' activities
	----------------------------------------- */
	$.getJSON(Mach.quizDataServer, { user:Mach.username, securityKey: Mach.security, type:"all" },
			function(data) {
			  var items = [];
			  $.each(data, function(key, val) {
				items.push('<td id="' + key + '">' + val + '</td>');
			  });
			
			  $('<tr/>', {
				'class': 'my-new-list',
				html: items.join('')
			  }).appendTo(Mach.recentActivityContainer);
	});
	$.getJSON(Mach.quizDataServer, { user:Mach.username, securityKey: Mach.security, type:"all" },
			function(data) {
			  var items = [];
			  $.each(data, function(key, val) {
				items.push('<td id="' + key + '">' + val + '</td>');
			  });
			
			  $('<tr/>', {
				'class': 'my-new-list',
				html: items.join('')
			  }).appendTo(Mach.friendsActivityContainer);
	});
}