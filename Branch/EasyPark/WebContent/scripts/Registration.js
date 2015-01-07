function cleanFields() {
	$('#FirstName').val("");
	$('#LastName').val("");
	$('#Email').val("");
	$('#CompanyName').val("");
	$('#Password').val("");
	$('#PasswordRepeat').val("");
	$('#PhoneNumber').val("");
	$('#Address').val("");
	$('#UserType').val(0);
	$('#AccNumber').val("");
	$('#Token').val("");
}
function CheckToken(email, token, usertype, action) {
	var res = false;
	var JSONObject = {
		"username" : email,
		"type" : usertype,
		"token" : token,
		"action" : action
	};
	var jsonData = JSON.stringify(JSONObject);
	var request = $.ajax({
		url : "http://localhost:80/EasyPark/api/service/checkToken",
		type : "POST",
		async : false,
		contentType : 'application/json',
		data : jsonData,
	}).done(function(data) {
		res = data;
	}).fail(function(data) {
		alert("Greška sa konekcijom na server! Pokusajte ponovo.");
		return false;
	});
	return res;
}
function tokenRequest() {
	var token = $('#Token').val();
	var email = $('#Email').val();
	var usertype = $('#UserType').val();
	var result = CheckToken(email, token, usertype, 'request');
	if (!result) {
		alert('Zahtjev za tokenom sa ovom e-mail adresom je već poslan!');
	} else {
		var JSONObject = {
			"username" : email,
			"type" : usertype
		};
		var jsonData = JSON.stringify(JSONObject);
		var request = $
				.ajax({
					url : "http://localhost:80/EasyPark/api/service/saveToken",
					type : "POST",
					contentType : 'application/json',
					data : jsonData,
				})
				.done(
						function() {
							alert('Zahtjev za token je poslan! Uskoro provjerite dolazne poruke na unijetoj e-mail adresi!');
						}).fail(function() {
					alert("Greška sa konekcijom na server! Pokusajte ponovo.");
				});
	}
}
function registration() {
	// Validation
	// ...
	//
	var firstname = $('#FirstName').val();
	var lastname = $('#LastName').val();
	var companyname = $('#CompanyName').val();
	var password = $('#Password').val();
	var address = $('#Address').val();
	var usertype = $('#UserType').val();
	var accnumber = $('#AccNumber').val();
	var phone = $('#PhoneNumber').val();
	var token = $('#Token').val();
	var email = $('#Email').val();

	var result = CheckToken(email, token, usertype, 'registration');
	if (result) {
		var JSONObject = {
			"username" : email,
			"firstname" : firstname,
			"lastname" : lastname,
			"companyname" : companyname,
			"password" : password,
			"address" : address,
			"type" : usertype,
			"accountnumber" : accnumber,
			"phonenumber" : phone,
			"token" : token,
		};
		var jsonData = JSON.stringify(JSONObject);
		var request = $
				.ajax({
					url : "http://localhost:80/EasyPark/api/service/register",
					type : "POST",
					contentType : 'application/json',
					data : jsonData,
				})
				.done(
						function() {
							alert('Uspješno ste se registrovali! Probajte se upisati pomoću vašeg računa sad.');
							cleanFields();
						}).fail(function() {
					alert("Greška sa konekcijom na server! Pokusajte ponovo.");
					cleanFields();
				});
	} else {
		alert('Ne mozete koristit taj token za ovu e-mail adresu!');
	}
}
	
	function parkingregistration() {
		// Validation
		// ...
		//
;
		var freespots = $('#FreespotsParking').val();
		var note = $('#NoteParking').val();
		var camera = $('#isthereCamera').val();
		var guard = $('#isthereGuard').val();
		var road = $('#isthereRoad').val();
		var light = $('#isthereLight').val();
		var roof = $('#isthereRoof').val();
		var telefon = $('#telefonParking').val();
		var long = document.getElementById("markerlong").getAttribute('value');
		var lat = document.getElementById("markerlat").getAttribute('value');
		var creatorid = document.getElementById("userdiv")
				.getAttribute('value');
		var goodE =  $('#isthereGoodEntrance').val();
		console.log(long,lat,creatorid);
		var JSONObject = {
			"freespots" : freespots,
			"note" : note,
			"camera" : camera,
			"guard" : guard,
			"road" : road,
			"light" : light,
			"roof" : roof,
			"long" : long,
			"lat" : lat,
			"creatorid" : creatorid,
			"telefon" : telefon,
			"goodentrance" : goodE
		};
		var jsonData = JSON.stringify(JSONObject);
		var request = $
				.ajax({
					url : "http://localhost:80/EasyPark/api/service/registerparking",
					type : "POST",
					contentType : 'application/json',
					data : jsonData,
				})
				.done(
						function() {
							alert('Uspješno unešen parking!');
							cleanFields();
						}).fail(function() {
					alert("Greška sa konekcijom na server! Pokusajte ponovo.");
					cleanFields();
				});
	}
