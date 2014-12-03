$(function() {
	//loading Register modal...
	var link = document.querySelector('link[href="RegisterBlock.html"]');
	var template = link.import.querySelector('template');
	var clone = document.importNode(template.content, true);
	document.querySelector('#registrationTemplate').appendChild(clone);
	
	//loading Login modal
	var link2 = document.querySelector('link[href="LoginBlock.html"]');
	var template2 = link2.import.querySelector('template');
	var clone2 = document.importNode(template2.content, true);
	document.querySelector('#loginTemplate').appendChild(clone2);
	
	
});
