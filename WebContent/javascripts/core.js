/**
* Core.js
* Copyright by Jim Zheng
* 
* Core Javascript Library 
* Very common javascript functions go here.
* 
*/

/*var Ajax = {
    getTransport: function () {
        return Try.these(function () {
            return new XMLHttpRequest()
        }, function () {
            return new ActiveXObject("Msxml2.XMLHTTP")
        }, function () {
            return new ActiveXObject("Microsoft.XMLHTTP")
        }) || false
    },
    activeRequestCount: 0
};*/

/* init small library prototypes */
		//if (!Array.prototype.map) { Array.prototype.map = function(fun /*, thisp*/) { var len = this.length; if (typeof fun != "function") throw new TypeError(); var res = new Array(len); var thisp = arguments[1]; for (var i = 0; i < len; i++) { if (i in this) res[i] = fun.call(thisp, this[i], i, this); } return res; };}	
		//Object.prototype.clone = function() { var newObj = (this instanceof Array) ? [] : {}; for (i in this) { if (i == 'clone') continue; if (this[i] && typeof this[i] == "object") { newObj[i] = this[i].clone(); } else newObj[i] = this[i] } return newObj; }
		

var CLIB = {
	
	addEvent: function(obj, evt, fn) {
		if (obj.addEventListener) {
			obj.addEventListener(evt, fn, false);
		}
		else if (obj.attachEvent) {
			obj.attachEvent("on" + evt, fn);
		}
	}
	, //end
	
	getElementsByClassName:function(className, tag, elm) {
		 /**
	     * Developed by Robert Nyman, http://www.robertnyman.com
		 * Code/licensing: http://code.google.com/p/getelementsbyclassname/
		 * 
		 */	
		if (document.getElementsByClassName) {
			getElementsByClassName = function (className, tag, elm) {
				elm = elm || document;
				var elements = elm.getElementsByClassName(className),
					nodeName = (tag)? new RegExp("\\b" + tag + "\\b", "i") : null,
					returnElements = [],
					current;
				for(var i=0, il=elements.length; i<il; i+=1){
					current = elements[i];
					if(!nodeName || nodeName.test(current.nodeName)) {
						returnElements.push(current);
					}
				}
				return returnElements;
			};
		}
		else if (document.evaluate) {
			getElementsByClassName = function (className, tag, elm) {
				tag = tag || "*";
				elm = elm || document;
				var classes = className.split(" "),
					classesToCheck = "",
					xhtmlNamespace = "http://www.w3.org/1999/xhtml",
					namespaceResolver = (document.documentElement.namespaceURI === xhtmlNamespace)? xhtmlNamespace : null,
					returnElements = [],
					elements,
					node;
				for(var j=0, jl=classes.length; j<jl; j+=1){
					classesToCheck += "[contains(concat(' ', @class, ' '), ' " + classes[j] + " ')]";
				}
				try	{
					elements = document.evaluate(".//" + tag + classesToCheck, elm, namespaceResolver, 0, null);
				}
				catch (e) {
					elements = document.evaluate(".//" + tag + classesToCheck, elm, null, 0, null);
				}
				while ((node = elements.iterateNext())) {
					returnElements.push(node);
				}
				return returnElements;
			};
		}
		else {
			getElementsByClassName = function (className, tag, elm) {
				tag = tag || "*";
				elm = elm || document;
				var classes = className.split(" "),
					classesToCheck = [],
					elements = (tag === "*" && elm.all)? elm.all : elm.getElementsByTagName(tag),
					current,
					returnElements = [],
					match;
				for(var k=0, kl=classes.length; k<kl; k+=1){
					classesToCheck.push(new RegExp("(^|\\s)" + classes[k] + "(\\s|$)"));
				}
				for(var l=0, ll=elements.length; l<ll; l+=1){
					current = elements[l];
					match = false;
					for(var m=0, ml=classesToCheck.length; m<ml; m+=1){
						match = classesToCheck[m].test(current.className);
						if (!match) {
							break;
						}
					}
					if (match) {
						returnElements.push(current);
					}
				}
				return returnElements;
			};
		}
		return getElementsByClassName(className, tag, elm);
	}//end
}

/* MISC --------------------
	
* -------------------- */
function semaphore(initialCount) {
	this.count = initialCount;
	
}