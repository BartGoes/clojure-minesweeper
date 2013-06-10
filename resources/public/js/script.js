var path = "http://localhost:3000";
var method = "post"; // Set method to post by default if not specified.

function post_to_url(params) {
	// The rest of this code assumes you are not using a library.
	// It can be made less wordy if you use one.
	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", path);

	for(var key in params) {
		if(params.hasOwnProperty(key)) {
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type", "hidden");
			hiddenField.setAttribute("name", key);
			hiddenField.setAttribute("value", params[key]);

			form.appendChild(hiddenField);
		}
	}

	document.body.appendChild(form);
	form.submit();
}

function init() {
	 console.log('init');
	 $('.inputtable').bind('contextmenu', function(e) { return false; });
	 $('.inputtable').mousedown(function(event) {
	  switch (event.which) {
	  case 3:
	   var jso = new Object();
	   var modifiedbuttonname = this.name.replace('b','r');
	   jso[modifiedbuttonname] = " ";
	   console.log('Right mouse button pressed: ' + modifiedbuttonname);
	   post_to_url(jso);
	   break;
	  default: break;
	  }
	 });
	}