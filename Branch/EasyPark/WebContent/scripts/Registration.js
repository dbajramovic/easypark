

function cleanFields(){	
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
function CheckToken(email, token){
	//code for checking token
	return true;
}
function tokenRequest(){
	var token = $('#Token').val();
	var email = $('#Email').val();
	var usertype = $('#UserType').val();
	var result=CheckToken(email,token);
	if (!result) {alert('Zahtjev za tokenom sa ovom e-mail adresom je već poslan!');}
	else{
		var JSONObject = 
		{
			"username":email, 
			"type":usertype,
		};
	var jsonData = JSON.stringify(JSONObject);
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/service/getToken",
			type: "POST",
			contentType: 'application/json',
			data: jsonData, 
		}).done(function() { 
			alert('Zahtjev za token je poslan! Uskoro provjerite dolazne poruke na unijetoj e-mail adresi!'); 
			})
	.fail(function() {
		alert("Greška sa konekcijom na server! Pokusajte ponovo."); 
		});
	}
}
function registration(){
	//Validation
	//...
	//
		var firstname=$('#FirstName').val();
		var lastname=$('#LastName').val();
		var companyname=$('#CompanyName').val();
		var password=$('#Password').val(); 
		var address = $('#Address').val();
		var usertype = $('#UserType').val();
		var accnumber = $('#AccNumber').val();
		var phone = $('#PhoneNumber').val();
		var token = $('#Token').val();
		var email = $('#Email').val();
		
		var result = CheckToken(email,token);

		if (result) {
		var JSONObject = 
			{
				"username":email, 
				"firstname":firstname,
				"lastname":lastname,
				"companyname":companyname,
				"password":password,
				"address":address,
				"type":usertype,
				"accountnumber":accnumber,
				"phonenumber":phone,
				"token": token,
			};
		var jsonData = JSON.stringify(JSONObject); 
		var request = $.ajax({
				url: "http://localhost:80/EasyPark/api/service/register",
				type: "POST",
				contentType: 'application/json',
				data: jsonData, 
			}).done(function() { 
				alert('Uspješno ste se registrovali! Probajte se upisati pomoću vašeg računa sad.');
				cleanFields(); 
				})
		.fail(function() {
			alert("Greška sa konekcijom na server! Pokusajte ponovo.");
			cleanFields(); 
			});
		}
		else {
			alert('Ne mozete koristit taj token za ovu e-mail adresu!');
		}
}

