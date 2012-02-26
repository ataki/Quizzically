/* --------------------------
 * Admin interface GUI JS
 * Team Mach One 
 * Stanford CS 108 
 ------------------------*/

$(document).ready(function() {

	/* Admin Globals -----------
		Various globals; assume
		that we have window.MACH 
		defined.
	 --------------------------- */
	if(window.Mach != null) {
		$.extend(true, window.Mach, {
			optionsArray:[
						"remove-user",
						"promote-user",
						"demote-user",
						"send-message"
						]
		});
	
	} else return;
	
	adminInit();
	
});

/* 
* Initializes various UI aspects of admin interface
*/
function adminInit() {
	for(var i =0; i < Mach.optionsArray.length;i++) {
		// send a POST request to let the system know
		// what we want to do with that person.
		$("." + Mach.optionsArray[i]).click(function() {
			
			generateGrowl("Title", "Message", false, 10000);
			var opt = $(this).attr("class");
			
			var userid = $(this).parent().siblings(".options-user-id").html();
			// find our user id
			var spinnerPtr = $('<img class="spinner" src="images/spinner.gif" />');
			$(spinnerPtr).insertAfter( $(find_parent_who_has_class(this, "nav-bar")).toggle(false));
			
			var thisPtr = this;
			console.log(Mach.adminServer);
			$.ajax({
				url: Mach.adminServer,
				type: "POST",
				data: { type:"user", targetID:userid, option:opt },
				success: function(data) {
						$(spinnerPtr).remove();
						$(find_parent_who_has_class(thisPtr, "nav-bar")).toggle(true);
					},
				error: function(data) {
					$(spinnerPtr).remove();
					$(find_parent_who_has_class(thisPtr, "nav-bar")).toggle(true);
				}
			});
			
			//$.post(Mach.adminServer, { type:"user", user:userid, option:opt }, 
		});
	}
}