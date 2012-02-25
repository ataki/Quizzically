/* Quiz -----------------------------
* Team Mach One 
* CS 108 Stanford University
------------------------------------- */

$(document).ready(function() {

	/*
	* jQuery native swap function
	* to swap two things 
	*/
	jQuery.fn.swap = function(b) {
		b = jQuery(b)[0];
		var a = this[0],
			a2 = a.cloneNode(true),
			b2 = b.cloneNode(true),
			stack = this;
	
		a.parentNode.replaceChild(b2, a);
		b.parentNode.replaceChild(a2, b);
	
		stack[0] = a2;
		return this.pushStack( stack );
	};
	
	if(window.Mach != null) {
		/* Use JQuery's extend() to help
		* us add to the global MACH function 
		*/
		$.extend(true, window.Mach, {
			
			/* RESULTS AND SUMMARY ------------
			* allow table sorting 
			* ----------------------------- */
			tableSorterIDs:["user-performance-table",
							"friend-performance-table",
							"top-performance-table",
							"top-last-day-performance-table",
							"last-performance-table",]
							,
							
			/* SUBMIT BUTTON
			* if we need to make use of it and
			* write our own custom forms, 
			* put this ID onto your 
			* submit button and write
			* the function submit_callback
			*/
			submitButton:{
						  id:"submit-button",
						  callback:submitCallBack
						 },
		
			/* specific question types.
			* For now almost 1:1 reverse mapping
			* of questionTypes.
			*/
			questionClassNames: {
						// Below 10 are for textbox responses
						  "question-response":"type1",
						  "fill-in-the-blank":"type2",
						  "multi-answer-question":"type3",
						// Btwn 10-20 for radio boxes
						  "multiple-choice":"type11",
						  "multiple-choice-multiple-answer":"type12",
						// Btwn 20-30 for special types
						  "picture-response":"type21",
						  "matching":"type22",
						  "timed-question":"type23",
						  "auto-generated":"type24",
						  "graded-questions":"type25",
			},
			
			/*
			* className for undo matching
			*/
			undoButton:"undo-matching",
			
			/* the undo stack; useful for matching questions
			*/
			undoStack:{
				stackLen:0,
				stack:[],
				push:function(object) {
					this.stack.push(object);
					this.stackLen++;
				},
				pop:function() {
					if(this.stackLen == 0) return null;
					this.stackLen--;
					var a=this.stack.pop();
					return a;
				},
				empty:function() {
					return this.stackLen === 0;
				},
			},
			
			numAnswersToMatch:0,
		});
	}
	/* If we don't detect windows.MACH, just abort*/
	else return;
	
	/* MATCHING ------------------------
	* If we detect a matching question, 
	* initialize our drag-drop features
	 ----------------------------------- */
	if($('.' + Mach.questionClassNames["matching"]) != null) {
		initDragDrop();
		var arrToMatch = $('.' + dnd.settings.divname).children().filter('.answer');
		Mach.numAnswersToMatch = arrToMatch.length;
	}
	
	/* UNDO functionality ------------
	* In the case of a matching question, 
	* keep a stack of undo functions 
	* ------------------------------- */
	if($('.' + Mach.undoButton) != null) {
		$('.' + Mach.undoButton).click(function() {
			undoOneMatching();
		});
	}
	
	/* CUSTOM SUBMIT FORM------------ */
	if($('#' + Mach.submitButton.id) != null) {
		$('#' + Mach.submitButton.id).attr("disabled", true);
		$('#' + Mach.submitButton.id).click(function() {
			Mach.submitButton.callback(this);
		});
	}
	
	/* TABLE SORTING ------------------ 
	--------------------------------- */
	for(var i = 0; i < Mach.tableSorterIDs.length; i++) {
		if($('#' + Mach.tableSorterIDs[i]) != null) {
			$('#' + Mach.tableSorterIDs[i]).tablesorter(); 
		}
	}
});

/* submit button callback for matching
* forms
*/
function submitCallBack(elem) {
	if($('.' + Mach.questionClassNames["matching"]) != null) {
		// Read, jsonify data, and convert into an array.
		var ansArr = $('.' + dnd.settings.divname).children().filter('.answer').children().filter('.info');
		var dataObj = {};
		for(var i = 0; i < ansArr.length; i++) {
			dataObj["answer" + i] = $(ansArr[i]).html();
		}
		if(dataObj.length == 0) 
			return;
		
		dataObj["type"] = "matching";
		console.log(JSON.stringify(dataObj));
		$.post(Mach.quizServer, {data: JSON.stringify(dataObj)});
	}
}

function undoOneMatching() {
	
	if(Mach.undoStack.empty() == false) {
		var pairing = Mach.undoStack.pop();
		
		// try and get question section to append to 
		var firstNonTargetInList = $('.' + dnd.settings.divname).children().filter('.questionBegin')[0];
		
		// try and get answer section to append to 
		var firstTargetInList = $('.' + dnd.settings.divname).children().filter('.answerBegin')[0];
									
		console.log(firstNonTargetInList);							
		console.log(firstTargetInList);							
		if(firstTargetInList == null || firstNonTargetInList == null) {
			alert("Sorry, can't undo");
			return;
		}
		$(pairing["original"]).toggleClass("active-fixed", false).toggleClass("draggable-ignore", false).toggleClass("question", true).toggleClass("answer", false).insertAfter(firstNonTargetInList);
		var toReplace = $(pairing["result"]);
		toReplace.insertAfter(firstTargetInList);
		
		tryEnableSubmitButton();
	}
}

function tryEnableSubmitButton() {
	if($('#' + Mach.submitButton.id) != null) {
		// if we haven't finished matching up all answers,
		// send alert to the user
		var questionsLeft = $('.' + dnd.settings.divname).children().filter('.question');

		if(questionsLeft.length == 0) {
			$('#' + Mach.submitButton.id).attr("disabled",false);	
		}
		else {
			$('#' + Mach.submitButton.id).attr("disabled",true);	
		}
	}	
}

/* checks whether SRC is a question and DST is an answer */
function replaceHighlightDomElements(original /*SRC*/, result /*DST*/) {
	if($(result).attr('class') != null &&
	   	$(result).attr('class').search("answer") >= 0 &&
	   	$(original).attr('class').search("question") >= 0) 
	{	
		
		// toReplace is the replacement answer; make sure the user knows its highlighted
		var toReplace = 
			$(original).toggleClass("active-fixed", true).toggleClass("draggable-ignore", true).toggleClass("question", false).toggleClass("answer", true);
		$(result).replaceWith(toReplace);
		
		// push the adultered versions onto undo stack
		Mach.undoStack.push({"original":toReplace , "result":result});
		
		tryEnableSubmitButton();
	}
	/*if(original != result) 
		$(result).swap(original);*/
}

/* Initialize drag-n-drop 
* Initializes also with a callback function to replace the elements
*/
function initDragDrop() {
	// do stuff here
	window.dnd = new drag_n_drop('draggable');
	
	dnd.settings.dragResult = function(original/* elem we started @ */, result/* elem we landed on */) {
		replaceHighlightDomElements(original, result);
	}	
	dnd.init();
	
}