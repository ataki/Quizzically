/** 
* Drag-N-Drop Object Library
* Copyright 2012 Jim Zheng
* 
* A simple framework-independent drag-drop library
* used to implement drag-drop functionality. DOM-element-agnostic.
* Self-contained; it has all the libraries it needs to run as a standalone, 
* Cleans up after itself on all modern browsers that allow it.
* 
* Usage: 
* js: var DOD = new drag_n_drop('draggable')
* html: Add <div class="...">...</div> around a set of DOM elements positioned in order 
* 	The elements in between are instantly draggable. To access elements inside the div,
* 	use the three callbacks presented by this library.
* 
* Callbacks:
*   var DOD = new drag_n_drop('mydivClass')
*   DOD.onDragStart = function() { ... }
*
*/

function drag_n_drop(wrapper_name) {

	/* make sure to add a check to checkSettings function for each setting here */
	this.settings = { 
		/**
		* settings
		* 
		* Visible/adjustable parameters.
		* @ divname : wrapper div around the content to be dragged. 
		* @ vertical/horizontal: in which dimensions do we want to allow dragging
		* @ free_move: allow the divs to move freely instead of in fixed intervals.
		* @ dragBackgroundColor: background color of transparent 
		* @ activeHoverClassName: name of class of element currently being hovered over
		*	after drag has started
		*/
		divname:wrapper_name, 
		vertical: 1,
		horizontal: 1,
		free_move: 0,
		dragBackgroundColor:"#98F5FF",
		activeHoverClassName:"active",
		
		/**
		* Callbacks
		*/
		
		/**
		* main callback for releasing the item. This item is a
		* child of draggable.
		*/
		dragResult:function(begin_elem, result_elem){/*replace me*/}
		,
		
		/**
		* after all drag variables have been initialized
		*/
		onDragStart:function(e){/*code before drag tracking begins*/}
		,
		
		/**
		* after we have finished resetting our drag functions;
		* called AFTER dragResult
		*/
		onDragFinish:function(e) {/*code after finshed dragging*/ }
		,
		
		/**
		* while drag is happening, detected every time onmousemove is called
		* WARNING: heavy computation here could up CPU usage by a lot
		*/
		onDragRun:function(e) {/*code while dragging*/ }
		,
		
		/**
		* keeps track of whether we've started dragging
		*/
		started:false
		,
	};
	
	/* Getters */
	this.getDragElement = function() {
		return _dragElement;
	}
	this.getMasterElement = function() {
		return _masterElement;
	}
	
	/**
	* Class variables :
	* Keep an underlying representation of the elements 
	*/
	var _start = { x:0, y:0 }
	var _elemPosition = { x:0, y:0 }
	var _oldCSS = { 
			_oldZ : "",
			_oldOpacity: "",
			_oldBackgroundColor:"",
			_oldColor: ""
	};
	var _bounds = { max_x:0, min_x:0, max_y:0, min_y:0 };
	var _interval = { x:0, y: 0 }
	var _dragElement; // DOM element being dragged; child of masterElem
	var _masterElement; // the master element currently being considered
	var _fakeElement; // visual of element; not the actual one dragged
	var _dragElem_arr = []; // array of all draggable DOM elements
	
	/* Lib: tries its best to extract an integer from a string */
	function ExtractNumber(value) {
		var n = parseInt(value);		
		return n == null || isNaN(n) ? 0 : n;
	}
	/* Lib: adds a simple event listener */
	function addEventSimple(obj,evt,fn) {
		if (obj.addEventListener)
			obj.addEventListener(evt,fn,false);
		else if (obj.attachEvent)
			obj.attachEvent('on'+evt,fn);
	}	
	/* Lib: removes a simple event listener */
	function removeEventSimple(obj,evt,fn) {
		if (obj.removeEventListener)
			obj.removeEventListener(evt,fn,false);
		else if (obj.detachEvent)
			obj.detachEvent('on'+evt,fn);
	}
	/* Lib: cross-browser, gets specified style in string format of DOMElement 'el' */
	function getStyle(el,styleProp) {
		if (el.currentStyle)
			var y = el.currentStyle[styleProp];
		else if (window.getComputedStyle)
			var y = document.defaultView.getComputedStyle(el,null).getPropertyValue(styleProp);
		return y;
	}
	/* Lib: finds the position:absolute coordinates. Returns new Object with .x and .y */
	function getAbsolutePos(elem) {
		var result = {x:0, y:0}
		if (elem.offsetParent) {
			do {
			result.x += elem.offsetLeft;
			result.y += elem.offsetTop;
			} while (elem = elem.offsetParent);
		}
		return result;
	}
	/**
	* lib: returns whether a node is a child ofof parent with given classNamee
	*/
	function isDirectChildOfClass(elem, className) {
		return (elem.parentNode.className.match(className))
   	}

	/**
	* lib: cross-browser, returns mouse position
	*/ 
	function mousePosition(e) {
		// check for mouse assignment
		var posx = 0;
		var posy = 0;
		if(e == null)
			e = window.event;
		if(!e) throw new Error("Cannot detect mouse position, returning")	
		
		if (e.pageX || e.pageY) {
			posx = e.pageX;
			posy = e.pageY;
		}
		else if (e.clientX || e.clientY) 	{
			posx = e.clientX + document.body.scrollLeft
				+ document.documentElement.scrollLeft;
			posy = e.clientY + document.body.scrollTop
				+ document.documentElement.scrollTop;
		}
		return {x:posx, y:posy};
	}
	
	/* if some child of a draggable elem is clicked, we go up and find the draggable parent */
	function getDraggableParent(elem) {
   		while(elem != undefined) {
   			if(isDirectChildOfClass(elem, $_drag_n_drop_settings.divname))
   				return elem;
			elem = elem.parentNode;
   		}
   		return undefined;
	}
	
	/**
	* gets the dom elem closest to (pX, pY) in the rendering
	* we iterate over all draggable items and check to see
	* if we have a match. 
	* Define a 'match' to be either the elem the cursor is 
	* completely over or the one with the most overlap with the 
	* current person.
	*
	*/
	function getDraggableElementByPosition(pX, pY) {
		for(var i = 0; i < _dragElements_arr.length; i++) {
			// CURRENTLY NOT NEEDED	
		}
	}
	
	function _dragMouseOver(e) {
		
		if($_drag_n_drop_settings.started == false) return;
		var elem = e.target != null ? e.target : e.srcElement;
		var dragElem = getDraggableParent(elem);
	
		if(dragElem.className.search("draggable-ignore") > 0) {
			return;
		}
		
		dragElem.isCursorOver = true;
		$(dragElem).toggleClass($_drag_n_drop_settings.activeHoverClassName, true);
		
	}	
	
	/**
	* _dragMouseOut:
	* fires when mouse focuses out of a draggable element.
	*/
	function _dragMouseOut(e) {
		if($_drag_n_drop_settings.started == false) return;
		var elem = e.target != null ? e.target : e.srcElement;
		var dragElem = getDraggableParent(elem);
		
		if(dragElem.className.search("draggable-ignore") > 0) {
			return;
		}
		dragElem.isCursorOver = false;
		$(dragElem).toggleClass($_drag_n_drop_settings.activeHoverClassName, false);
	}
	
	/**
	* _dragRun(e)
	*
	* move during a dragging process
	* cannot move past a certain bounds, and can only move in 
	* fixed intervals
	*/
	function _dragRun(e) {
		if($_drag_n_drop_settings.started == false) return;
		if(_fakeElement == null) return;
		if(_dragElement) {
			var mpos = mousePosition(e);
			var new_left = (_elemPosition.x + mpos.x - _start.x) 
			_fakeElement.style.left = new_left + 'px';  
			var new_top = (_elemPosition.y + mpos.y - _start.y) 
			_fakeElement.style.top =  new_top + 'px';  
		}
		$_drag_n_drop_settings.onDragRun(e);
		return false; // don't allow text to be highlighted still 
	}
	
	/**
	* resetElem(elem)
	* Resets callbacks and properties that may have been marred by
	* the user's custom function, which could prevent
	* these divs from being draggable again.
	*/
	function resetElem(elem) {
		if(elem == null) return;
		// add mouse event listeners
		if(elem.onmousedown == null) elem.onmousedown = _dragInit;
		if(elem.onmouseover == null) elem.onmouseover = _dragMouseOver;
		if(elem.onmouseout == null) elem.onmouseout = _dragMouseOut;
		elem.isCursorOver = false;
	}
	
	/**
	* _dragFin(e)
	*
	* when elem is released, we look at where it was released 
	* and do something with the element passed in.
	*/
	function _dragFin(e) {
		if($_drag_n_drop_settings.started == false) return;
		var mpos = mousePosition(e);
		// restore everything back
		if(_fakeElement != null) {
			parentElem = _fakeElement.parentNode;
			if(parentElem!=undefined) parentElem.removeChild(_fakeElement);
		}
		
		for(var i = 0; i < _masterElement.childNodes.length; i++) {
			$(_masterElement.childNodes[i]).toggleClass($_drag_n_drop_settings.activeHoverClassName, false);
			if(_masterElement.childNodes[i].isCursorOver && _masterElement.childNodes[i] != _fakeElement) {
				// only let the one closest elem match.
				$_drag_n_drop_settings.dragResult(_dragElement, _masterElement.childNodes[i]);
				break;
			}
		}
		
		/* we have to do this RESET because users could have modified/deleted our elems */
		for(var i = 0; i < _masterElement.childNodes.length; i++) {
			resetElem(_masterElement.childNodes[i]);
		}
		
		removeEventSimple(document,'mousemove', _dragRun);
		removeEventSimple(document,'mouseup', _dragFin);
		document.onselectstart = null; 
		$_drag_n_drop_settings.onDragFinish(e);
		_dragElement = null;
		
		// announce globally that we've finished
		$_drag_n_drop_settings.started = false;
	}
	/* initializes the fake element to be dragged instead of actual object */
	function _initFakeElem(realElem) {
		if($_drag_n_drop_settings.started == false) return;
		
		_fakeElement = realElem.cloneNode(true);
		// style the draggable elem according to the settings defined by the person
		_fakeElement.style.zIndex = "999";
		_fakeElement.style.position = "fixed";
		_fakeElement.style.opacity = "0.6";
		realElem.appendChild(_fakeElement);
		
		var elemPos = getAbsolutePos(realElem);
		_elemPosition.x = elemPos.x;
		_elemPosition.y = elemPos.y;
		_fakeElement.style.left = elemPos.x + ExtractNumber(getStyle(realElem, 'padding-left'));
		_fakeElement.style.top = elemPos.y + ExtractNumber(getStyle(realElem, 'padding-top'));
	}

	/** 
	* _dragInit(e)
	* starts a dragging process
	*/
	function _dragInit(e) {
		// as an edge case, we attach the _dragFin event to window
		// so we can detect when mouse leaves window and call it
		if(window.__dragFin == null) window.__dragFin = _dragFin;
		
		var mpos = mousePosition(e);
		// IE uses srcElement, others use target
   		var elem = e.target != null ? e.target : e.srcElement;
   		
   		if(elem.className.search("draggable-ignore") > 0) {
			return;
		}
   		// announce globally that we've started dragging
        $_drag_n_drop_settings.started = true;
        $_drag_n_drop_settings.last_dragged_item = elem;

   		elem = getDraggableParent(elem);
   		if(elem==undefined) return false;
   		
   		// setup our tracking system.
		_start.x = mpos.x;
		_start.y = mpos.y;
		_dragElement = elem;
	
		_initFakeElem(elem);
		
		addEventSimple(document,'mousemove', _dragRun);
		addEventSimple(document,'mouseup', _dragFin);

		// the following prevent text selection
		document.body.focus();
        _dragElement.ondragstart = function() { return false; }; // prevent IE from trying to drag an image
        document.onselectstart = function () { return false; }; // prevent text selection in IE
        
        $_drag_n_drop_settings.onDragStart(e);
        
        
        return false; // prevents text selection otherwise
	}
	
	/* cleanup */
	this.release = function(e) {
		  e = e || window.event;
		  // For IE and Firefox
		  if (e) {
			// delete all prototypes, objects, and allocated
			if(_start) delete _start
			if(_elemPosition) delete _elemPosition;
			if(_bounds) delete _bounds;
			if(_interval) delete _interval;
			if(_dragElement) delete _dragElement;
			if(_oldCSS) delete _oldCSS;
			if(window.$_drag_n_drop_settings) delete window.$_drag_n_drop_settings;
		  }
	}
	
	/* sets some default settings if none specified */
	this._trySetDefaultSettings = function() {
		if(this.settings.divname==undefined) this.settings = "drag";
	}
	
	/** 
	* check settings to make sure we've initialized our Object correctly 
	*/
	this._check_settings = function() {	
		this._trySetDefaultSettings();
		try {
			var settings_error_arr = []
			settings_error_arr.push(typeof this.settings.divname != 'string')
			settings_error_arr.push(typeof this.settings.vertical != 'number')
			settings_error_arr.push(typeof this.settings.horizontal != 'number')
			settings_error_arr.push(typeof this.settings.free_move != 'number')
			settings_error_arr.push(typeof this.settings.dragBackgroundColor != 'string')
			settings_error_arr.push(typeof this.settings.activeHoverClassName != 'string')
			settings_error_arr.push(typeof this.settings.onDragStart != 'function')
			settings_error_arr.push(typeof this.settings.onDragFinish != 'function')
			settings_error_arr.push(typeof this.settings.onDragRun != 'function')
			
		} catch (e) {
			throw new Error(e);
		}
		
		for(var i = 0; i < settings_error_arr.length; i++) {
			if(settings_error_arr[i]) { throw new TypeError(); }
		}
	}	
	/**
	* init helper functions and variables
	*/
	this.init = function() {
		/* check everything OK. Set settings to be accessible for callbacks. */
		this._check_settings();
		if(window.$_drag_n_drop_settings == null) 
			window.$_drag_n_drop_settings = this.settings;
		
		var draggable_arr = CLIB.getElementsByClassName(this.settings.divname);
		var draggable_elems = draggable_arr[0];
		_masterElement = draggable_elems; // remember the container 
		
		if(draggable_elems == null) 
			return;
			
		// extract values based on the starting node. 
		// we assume that the chldren node sizes are pretty much constant.
		_bounds.min_x = ExtractNumber(draggable_elems.style.left);
		_bounds.min_y = ExtractNumber(draggable_elems.style.top);
		_bounds.max_x = draggable_elems.offsetWidth + _bounds.min_x;
		_bounds.max_y = draggable_elems.offsetHeight + _bounds.min_y;

		var shouldAdvance = 1;
		/* prep our child nodes for manipulation using callbacks
		* also finds fixed. This might iadvertantly cause some white
		* space blocks to be set that are not a part of the child nodes;
		* blame the childNodes function. To remedy this problem, choose 
		* only divs that have the style property
		* 
		*/
		for(var i = 0; i < draggable_elems.childNodes.length; i++) {
			if(draggable_elems.childNodes[i].className == null)
				continue;
			if(draggable_elems.childNodes[i].className.search("ignore") == null)
				continue;
				
			if(draggable_elems.childNodes[i].offsetHeight != undefined
			     && draggable_elems.childNodes[i].offsetWidth != undefined
			     && shouldAdvance == 1) {
				_interval.x = draggable_elems.childNodes[i].offsetWidth;
				_interval.y = draggable_elems.childNodes[i].offsetHeight;
				shouldAdvance = 0;
			}
			
			if(draggable_elems.childNodes[i].style) {
				_dragElem_arr.push(draggable_elems.childNodes[i])
				// add mouse event listeners
				draggable_elems.childNodes[i].onmousedown = _dragInit;
				draggable_elems.childNodes[i].onmouseover = _dragMouseOver;
				draggable_elems.childNodes[i].onmouseout = _dragMouseOut;
				draggable_elems.childNodes[i].isCursorOver = false;
			}
		}
		window.onbeforeunload = this.release;
		
		/* In case we go out of bounds, don't do anything further
		*/
		CLIB.addEvent(window,"load",function(e) {
			CLIB.addEvent(document, "mouseout", function(e) {
				e = e ? e : window.event;
				var from = e.relatedTarget || e.toElement;
				if (!from || from.nodeName == "HTML") {
					// stop your drag event here
					_dragFin(e);
				}
			});
		});
	}
}

