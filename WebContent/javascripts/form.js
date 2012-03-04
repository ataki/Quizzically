/* Form Creation ---------------
* Team Mach One  
* CS 108 Stanford University
* Form is for when we're creating
* the quizzes
* ---------------------------- */
$(document).ready(function() {

	if(window.Mach != null) {
	
		/* Use JQuery's extend() to help
		* us add to the global MACH function 
		*/
		$.extend(true, window.Mach, {
			
			/* number of current questions */
			numQuestions:0,
			
			questionsArray:[],
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
			* add single checkboxes or textboxes */
			addInputText:"add-input-text",
			addInputRadio:"add-input-radio",
			addMatchPair:"add-match-text",

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
			
			/* 
			* InverseSemaphore for our timer field.
			* when any timer is initialized, we allow it to
			* up this semaphore until it is 0.
			* At this point, it's valid to submit so
			* we reenable the submit button.
			* Javascript is single-threaded so ok 
			* to have this global variable
			*/
			timerInverseSema:0
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
		// first count the number of questions
		$('.question-block').each(function() {
			Mach.numQuestions++;
			Mach.questionsArray.push($(this).parent());
		});	
		
		// attach close actionlistener to listener
		attachCloseQuestionListener();
		
		// adding more questions
		// prepend a question before the add button
		$("#more-questions").click(function() {
			var toAppend = $(generateQuestionBlock("question-type-box"));
			
			var N = Mach.numQuestions + 1;
			/*			
				make necessary changes as listed in the question-type-box
				note: 
				N is number of total questions + 1
						1. top-level div id: 		"question-N", 
						2. select id: 				"question-type-N"
						3. select name: 			"question-type-N"
						4. the question block id: 	"question-block-N".
						5. select class add: 		"question-block"
						6. h6: add to innerHTML     "N"
			*/
			//1
			$(toAppend).attr("id", "question-" + N);
			//6
			$(toAppend).children("h6 ").html("Question " + N + '<a style="font-size:16px;">&times;</a>');
			//5,2,3,
			$(toAppend).children("select").addClass("question-block").attr("id", "question-type-" + N).attr("name", "question-type-" + N);
			//4
			$(toAppend).children("#question-block-").attr("id", "question-block-"+N);
			//2
			$(toAppend).children("#question-id-").attr("id", "question-id-" + N);
			if($(toAppend).insertBefore(this))
				Mach.numQuestions++;
			// have to reattach change handler
			addQuestionBlockChangeFunctions();
			attachCloseQuestionListener();
			
			Mach.questionsArray.push($(toAppend));
			$("#num-questions").attr("value", Mach.numQuestions);
		});
		addQuestionBlockChangeFunctions();

		// on submit button, send whole quiz
		// over as JSON to the server. 
		// We catch the submit here to send over an unescaped
		// string
		$('form').submit(function() {
			var values = unescape($(this).serialize());
			$.post(Mach.createServer, { user: Mach.username, data:values});
		});
		
		if($('#quiz-name').val().length == 0) {
			generateWarningDisableSubmit($('#quiz-name'));
		}
		
		$('#quiz-name').change(function() {
			var value = $(this).val();
			if(value.length != 0) {
				reEnableInputAndSubmit(this);
			} else {
				if($(this).attr('class').search("warning") < 0)
					generateWarningDisableSubmit($('#quiz-name'));
			}
		});
		
		
	} // end of Mach Detect
});

function attachCloseQuestionListener() {
	/* Closing a question 
	 * remove the question, update all question numbers 
	 */
	$('.close-question').off("click").on("click", function() {
		if(Mach.numQuestions == 1) {
			return;
		} else { 
			Mach.numQuestions--;
			var i;
			var matchingID = $(this).parent().attr("id");
			for(i = 0; i < Mach.questionsArray.length;i++) {
				if(Mach.questionsArray[i].attr("id") == matchingID) {
					break;
				}
			}
			if(Mach.questionsArray[i] == undefined)
				return;
			
			/* removal process */
			$(Mach.questionsArray[i]).remove();
			Mach.questionsArray.splice(i, 1);
			
			/* reset all question numerics */
			for(i = 0; i < Mach.questionsArray.length ;i++) {
				var proper = 1 + i;
				var oldId = $(Mach.questionsArray[i]).attr("id").charAt($(Mach.questionsArray[i]).attr("id").length - 1);
				$(Mach.questionsArray[i]).attr("id", "question-" + proper)
				$(Mach.questionsArray[i]).children("h6").attr("class", "label").html("Question " + proper + '<a style="font-size:16px;">&times;</a>');
				$(Mach.questionsArray[i]).children("select").attr("id", "question-type-" + proper).attr("name", "question-type-" + proper);
				$(Mach.questionsArray[i]).children("#question-block-" + oldId).attr("id", "question-block-" + proper);
				
				$(Mach.questionsArray[i]).children("#question-block-" + proper).children().each(function() {
					if($(this).attr("name") != null) {
						$(this).attr("name", $(this).attr("name").slice(0, -1).concat(proper));
					}
					if($(this).attr("id") != null) {
						$(this).attr("name", $(this).attr("name").slice(0, -1).concat(proper));
					}
				});
			}
			reEnableInputAndSubmit(this);
			$("#num-questions").attr("value", Mach.numQuestions);
		}
	});
}

function generateWarningDisableSubmit(inputbox) {
	// don't let the user submit !
	if($(inputbox).attr("class") &&
		$(inputbox).attr("class").search("warning") < 0)
		var a = 5;
	else 
		$(inputbox).addClass('warning');
	Mach.timerInverseSema++;
	$('input[type=submit]').attr('disabled', 'disabled');
}

function reEnableInputAndSubmit(input) {
	if(Mach.timerInverseSema > 0) Mach.timerInverseSema--;
	$(input).removeClass('warning');
	// let the user submit
	if(Mach.timerInverseSema == 0) {
		$('input[type=submit]').removeAttr('disabled');
	}
}

/* Generates an HTML block based on
* existing hidden containers present
* in the HTML */
function generateQuestionBlock(type) {
	return $("#" + type).html();
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

/* reattaches form handler to our generic question-block box.
* Whenever a user clicks on an option in the select tags of question-block,
* attach handlers to allow user to add variable answer fields,
* to indicate how long to time the question, etc. 
*/
function addQuestionBlockChangeFunctions() {
		$('.question-block').unbind("change").change(function() {
			var type = $("#" + $(this).attr("id")).val();
			var containerID = $(this).attr("id");
			var num = containerID.charAt(containerID.length-1);
			
			var targetContainerID = "question-block-" + num;

			var text = generateQuestionBlock(type);
			$('#' + targetContainerID).empty();
			var toAppend = $(text);
			toAppend.each(function(index) {
				if($(this).attr("name") != null) {
					$(this).attr("name", $(this).attr("name") + num);
				}
				
			});
			toAppend.appendTo('#' + targetContainerID);
			
			// ALLOWING USER TO ADD TO VARIABLE FIELDS 
			// we have variable text box fields that allow a user to enter more
			// than one line of text.
			$('.' + Mach.addInputText).click(function() {
				var parentID = find_parent_who_has_id(this);
				var uniqueID = parentID.charAt(parentID.length-1);
				if(parentID.search("match") < 0) {
					$(generateTextBox("answer-" + uniqueID)).insertAfter(this);
				} 
				else { /* do nothing, error in html */}
			});
			
			// almost same as above, but for multi-choice-multi-answer,
			// distinguish between the wrong choice
			$('.' + 'n' + Mach.addInputText).click(function() {
				var parentID = find_parent_who_has_id(this);
				var uniqueID = parentID.charAt(parentID.length-1);
				if(parentID.search("match") < 0) {
					$(generateTextBox("nanswer-" + uniqueID)).insertAfter(this);
				} 
				else { /* do nothing, error in html */}
			});
			
			// special case of match questions
			$('.' + Mach.addMatchPair).click(function() {
				var parentID = find_parent_who_has_id(this);
				var uniqueID = parentID.charAt(parentID.length-1);
				if($(this).attr('class').search("match") < 0) {/* do nothing; error in html*/} 
				else { 
					Mach.matchCount++;
					$(generateMatchPair("match-question-" + Mach.matchCount, "match-answer-" + Mach.matchCount)).insertAfter(this);
				}
			});
			
			// timed option			
			// if checked, add a specifying time box to the question.
			$('.timed').unbind('change').change(function() {
				var parentID = find_parent_who_has_id(this);
				var uniqueID = parentID.charAt(parentID.length-1);
				if($("#time-" + uniqueID).length == 0) 
				{
					// timed box input error checking: must be a 
					// number or else we disable submit button
					var toAppend = $(generateQuestionBlock("time-limit")).change(function() {
						// check to see whether we have a 
						// number in our checkbox
						if( $(this).val().length === 0 )
        					generateWarningDisableSubmit(this);
        				else {
        					var result = parseInt($(this).val());
        					if(isNaN(result)) 
								generateWarningDisableSubmit(this);
							else 
        					if(typeof result == "number")
								reEnableInputAndSubmit(this);
        				}
					});
					
					if( $(toAppend).val().length === 0 ) {
						// don't let the user submit !
						generateWarningDisableSubmit(this);
					}
					$(toAppend).attr("name", "time-" + uniqueID).attr("id", "time-" + uniqueID).insertAfter(this);
					
				}
				else {
					// we've removed the checkbutton,
					// so changes are no longer valid
					reEnableInputAndSubmit(this);
					$("#time-" + uniqueID).remove();
				}
				
			});
			
		});
}