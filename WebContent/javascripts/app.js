/* -------------------------------... ... ...--------\\
	Team Mach : CS 108 Stanford Winter 2012
	
	Tools Used:
	jQuery, 
	jQuery tablesorter, 
	jQuery Reveal (Modal Plugin),
	Drag-n-Drop (self-created library), 
 -------------------------------... ... ...---------\\
 */

/* ---................................---------------
	TODOS:
		- Test uplaod of data to and from backend
		- Make sure POST request reach the server
			For posting ratings, message confirmations,
			deletions, and other communication between
			server and client
		- Write functions for uploading creation of 
			quizzes, messages,
		- Write functions for searching for friends
		 	and storing user data in a JSON format
	    - Test and debug tablesorter. Have everything
	    	wired up but need to test with real data in
	    	quiz-summary and quiz-results
   ---.......................,........---------------
*

/* NOTES ---------------------------------------------::
	> Make sure you include this file first before any other files 
	> Add library functions used by other docs here.
	> Make sure that for main.html to include main.js,
		and for admin.html to include admin.js,
		etc...
	
	---------------------------------------- */

// Global title reset
document.title = "Quizzically";

/* MACH globals -------------------------- 
 * Retrieves information so it's easier
 * for our functions to use. 
 * Useful info includes : 
 * 		- session username
 * 		- user information
 * 		- etc ... 
 * -------------------------------------- */
var _Mach = {

	/* Universal constants 
	* Should not be modified 
	*/
	user:readCookie("#username"),
	userInfo:readCookie("#userInformation"),
	securityKey:$('#securityKey').html()
	
}
// attach as window variable
window.Mach = _Mach;


/* Foundation v2.1.5 http://foundation.zurb.com */
$(document).ready(function () {
	
	/* INCLUDES -----------------------------
	* dynamically add javascript file to 
	* every page, site-wide
	*------------------------------------- */
	include('javascripts/jquery.gritter.min.js');
	// include('javascripts/jquery.tablesorter.min.js');
	
	/* TABS ----------------------
	Activate our tabs
	------------------------------- */
	function activateTab($tab) {
		var $activeTab = $tab.closest('dl').find('a.active'),
				contentLocation = $tab.attr("href") + 'Tab';
		//Make Tab Active
		$activeTab.removeClass('active');
		$tab.addClass('active');
    	//Show Tab Content
		$(contentLocation).closest('.tabs-content').children('li').hide();
		$(contentLocation).show();
	}
	$('dl.tabs').each(function () {
		//Get all tabs
		var tabs = $(this).children('dd').children('a');
		tabs.click(function (e) {
			activateTab($(this));
		});
	});
	if (window.location.hash) {
		activateTab($('a[href="' + window.location.hash + '"]'));
	}

	/* ALERT BOXES ------------ 
	--------------------------*/
	$(".alert-box").delegate("a.close", "click", function(event) {
    event.preventDefault();
	  $(this).closest(".alert-box").fadeOut(function(event){
	    $(this).remove();
	  });
	});

	/* PLACEHOLDER FOR FORMS ------------- */
	/* Remove this and jquery.placeholder.min.js if you don't need :) */

	$('input, textarea').placeholder();

	/* DROPDOWN NAV -------------
	Activates dropdown tabs
	-----------------------------*/
	var lockNavBar = false;
	$('.nav-bar a.flyout-toggle').live('click', function(e) {
		e.preventDefault();
		var flyout = $(this).siblings('.flyout');
		if (lockNavBar === false) {
			$('.nav-bar .flyout').not(flyout).slideUp(500);
			flyout.slideToggle(500, function(){
				lockNavBar = false;
			});
		}
		lockNavBar = true;
	});
	
  if (Modernizr.touch) {
    $('.nav-bar>li.has-flyout>a.main').css({
      'padding-right' : '75px'
    });
    $('.nav-bar>li.has-flyout>a.flyout-toggle').css({
      'border-left' : '1px dashed #eee'
    });
  } else {
    $('.nav-bar>li.has-flyout').hover(function() {
      $(this).children('.flyout').show();
    }, function() {
      $(this).children('.flyout').hide();
    })
  }
	
	
	/* ----------------------------------------------------
   	Team MachOne's Custom Implemenation Below this line
   		
   	Team Mach One: Stanford CS 108 Final Project
   	Jim Zheng, Samir Patel, Sydney Lin, Chris Yang	
   	
   	---------------------------------------------- */
	
   	/* MISC ------------------------------
   		Various functions to make
   		UI more reponsive
   	------------------------------------ */
   		
	// when a user closes an announcement, delete
	// it from that user's system
	$('.close').click(function(event) {
		var uniqueId = find_parent_who_has_id(this);
		$.post(Mach.annoucementServer, { user : Mach.user , number : uniqueId });
	});
	
	
});	// end of document.ready

/* Lib.js --------------------
   Just put the library in with app.js, and 
   make other classes reference this.
   Means 
   WE MUST INCLUDE APP.JS BEFORE ANY
   OTHER LIBRARY.
 ---------------------------------------- */

/* returns string of all digits from a String 
	containing chars and integers*/
function str_find_digits(haystack) {
	return haystack.replace(/\D/g,'');
}

/* from quirksmode.com : gets the cookie value */
function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(';');
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

function generateFixedStar_Str(num) {
	var num_str;
	switch(num) {
		case 1:
			num_str = "one";
			break;
		case 2:
			num_str = "two";
			break;
		case 3:
			num_str = "three";
			break;
		case 4:
			num_str = "four";
			break;
		case 5:
			num_str = "five";
			break;
		default:
			num_str = null;
			break;
	}
	return '<div class="star-rating-fixed' + num_str + '-stars"></div>';
}

/* finds the first parent who has an ID, and returns
 	that ID. If we go more than 5 generations above,
 	or we ever get to the 'document' elem, stop
 	and return NULL for unsuccessful search */
function find_parent_who_has_id(elem) {
	var cur = elem,
		parent = null,
		end = $('document');
		counter = 0;
		
	while(true) {
		counter++;
		parent = $(cur).parent();
		if(parent == end) return null;
		if(parent.attr('id') != null)
			return parent.attr('id');
		if(counter > 5) return null;
		cur = parent;
	}
}

/* finds the first parent who has an ID, and returns
 	that ID. If we go more than 5 generations above,
 	or we ever get to the 'document' elem, stop
 	and return NULL for unsuccessful search */
function find_parent_who_has_class(elem, className) {
	var cur = elem,
		parent = null,
		end = $('document');
		counter = 0;
		
	while(true) {
		counter++;
		parent = $(cur).parent();
		if(parent == end) return null;
		if(parent.attr('class') == className)
			return parent.attr('class');
		if(counter > 5) return null;
		cur = parent;
	}
}

/* generates a growl Notification right on the page!
* message disappears after x minutes
* @param headline: bold title
* @param message: the message you want to receive
* @param shouldRemain: should the message persist? 
*/
function generateGrowl(headline, message, shouldRemain, time) {
	var unique_id = $.gritter.add({
		// (string | mandatory) the heading of the notification
		title: headline,
		// (string | mandatory) the text inside the notification
		text: message,
		// (bool | optional) if you want it to fade out on its own or just sit there
		sticky: shouldRemain,
		// (int | optional) the time you want it to be alive for before fading out
		time: time,
	});
	return false;

}

/* a hacked around include function
* that imports other js files
*/
function include(path) {
	var script = document.createElement('script');
	script.src = path;
	script.type = 'text/javascript';
	document.getElementsByTagName('head')[0].appendChild(script);
}