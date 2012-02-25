/* Form Creation ---------------
* Team Mach One  
* CS 108 Stanford University
* ---------------------------- */
$(document).ready(function() {

	if(window.Mach != null) {
	
		/* Use JQuery's extend() to help
		* us add to the global MACH function 
		*/
		$.extend(true, window.Mach, {
			/* create form names 
			*/
			
			/* controls submission process; default is
			* false because the user has not made selections
			* on the form type 
			*/
			canSubmit:false,
			
			/* form creation names */
			createContainer:"create-container",
			createForm: { name:"create-editor",
						  fields: [
							"quiz-display",
							"immediate-correction",
							"random-questions",
						  ],
						  ids: [
						  	"number-of-questions",
						  ]
						},
						
			/* classes that allow you to dynamically
			* add single checkboxes */
			addInputText:"add-input-text",
			addInputRadio:"add-input-radio",

			/* divs that we should populate with
			* an actual question form once we know
			* that we have a question type 
			*/
			questionBlock:"question-block",
			questionType:{
						// Below 10 are for textbox responses
						  1:"question-response",
						  2:"fill-in-the-blank",
						  3:"multi-answer-question",
						// Btwn 10-20 for radio boxes
						  11:"multiple-choice",
						  12:"multiple-choice-multiple-answer",
						// Btwn 20-30 for special types
						  21:"picture-response",
						  22:"matching",
						  23:"timed-question",
						  24:"auto-generated",
						  25:"graded-questions",
						  },
						  
			/*
			* count for matching questions; for each new
			* matching pair we increment this count. 
			* Allows us to identify matching textboxes 
			* by appending a specific uniqueID the matchCount.
			* Increment this every time we add another pair field
			* to a matching quesiton.
			*/
			matchCount:0,
		});
	}
	/* If we don't detect windows.MACH, just abort*/
	else return;
	
	
	/* FORM CREATION --------------------------
	Handles creation of forms among other things.
	Uses parameters from the first form to generate
	a list of new forms and questions. 
	------------------------------------ */
	if($('#' + Mach.createForm.name) != null) {
		
		// whenever user confirms a quiz type, 
		// append the appropriate form below
		// the type
		$('.question-block').change(function() {
			var type = $("#" + $(this).attr("id")).val();
			var containerID = $(this).attr("id").substring(1);
			var text = generateQuestionBlock(type);
			$('#' + containerID).empty();
			var num = containerID.charAt(containerID.length-1);
			var toAppend = $(text);
			toAppend.each(function(index) {
				if($(this).attr("name") != null) {
					$(this).attr("name", $(this).attr("name") + num);
				}
				
			});
			toAppend.appendTo('#' + containerID);
			
			// ALLOWING USER TO ADD TO VARIABLE FIELDS 
			// we have variable text box fields that allow a user to enter more
			// than one line of text.
			$('.' + Mach.addInputText).click(function() {
				var parentID = find_parent_who_has_id(this);
				var uniqueID = parentID.charAt(parentID.length-1);
				console.log(parentID);
				if(parentID.search("match") <= 0) {
					$(generateTextBox("answer" + uniqueID)).insertAfter(this);
				} 
				else { /* do nothing, error in html */}
			});
			
			$('.' + Mach.addMatchPair).click(function() {
				var parentID = find_parent_who_has_id(this);
				var uniqueID = parentID.charAt(parentID.length-1);
				if(parentID.search("match") <= 0) {/* do nothing; error in html*/} 
				else { 
					Mach.matchCount++;
					$(generateMatchPair("matchquestion" + Mach.matchCount, "matchanswer" + Mach.matchCount)).insertAfter(this);
				}
			});
		});
		
		// on submit button, send whole quiz
		// over as JSON to the server
		$('form').submit(function() {
			var values = $(this).serialize();
			// Get all the forms elements and 
			// their values in one step
			
			/* assoc_arr = {};
			for(var i=0;i< values.length;i++) {
				assoc_arr[values[i][name]] = values[i][value];
			}*/
			// convert the data from Object Array form to JSON
			
			$.post(Mach.createServer, { user: Mach.username, friend:friendName , data:values});
		});
	}
});

/* Generates an HTML block based on
* existing hidden containers present
* in the HTML */
function generateQuestionBlock(type) {
	return $("#" + Mach.questionType[type]).html();
}

function generateTextBox(name) {
	return '<input type="text" placeholder="New" size="50" name="' + name + '" /><br/>';
}

function generateMatchPair(box1Name, box2Name) {
	return '<br/>Elem&nbsp;<input type="text" placeholder="Your Question" size="25" name="' + box1Name + '"/>&nbsp;'
			+ 'Matching Elem&nbsp;<input type="text" placeholder="New" size="25" name="' + box2Name + '"/><br/>';
}	

function generateErrorMessage(divToAddTo, message) {
	return $('<span class="red label">' + message + '</span>').appendTo(divToAddTo);
}