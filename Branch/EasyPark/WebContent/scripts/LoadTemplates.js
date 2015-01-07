$(function() {
	//loading Register modal...
	var link = document.querySelector('link[href="partialBlocks/RegisterBlock.html"]');
	var template = link.import.querySelector('template');
	var clone = document.importNode(template.content, true);
	document.querySelector('#registrationTemplate').appendChild(clone);
	
	//loading Login modal
	var link2 = document.querySelector('link[href="partialBlocks/LoginBlock.html"]');
	var template2 = link2.import.querySelector('template');
	var clone2 = document.importNode(template2.content, true);
	document.querySelector('#loginTemplate').appendChild(clone2);
	
	//loading Parking modal
	var link3 = document.querySelector('link[href="partialBlocks/ParkingInfoBlock.html"]');
	var template3 = link3.import.querySelector('template');
	var clone3 = document.importNode(template3.content, true);
	document.querySelector('#parkingModal').appendChild(clone3);
	
	//loading User modal
	var link4 = document.querySelector('link[href="partialBlocks/UserInfoBlock.html"]');
	var template4 = link4.import.querySelector('template');
	var clone4 = document.importNode(template4.content, true);
	document.querySelector('#userModalregular').appendChild(clone4);
	
	var link5 = document.querySelector('link[href="partialBlocks/RegisterParkingBlock.html"]');
	var template5 = link5.import.querySelector('template');
	var clone5 = document.importNode(template5.content, true);
	document.querySelector('#parkingregistrationModal').appendChild(clone5);
});
