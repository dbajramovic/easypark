function cleanFields(){
	document.getElementById('Username').value="";
	document.getElementById('FirstName').value="";
	document.getElementById('LastName').value="";
	document.getElementById('CompanyName').value="";
	document.getElementById('About').value="";
	document.getElementById('Password').value=""; 

}
function registration(){
	//Validation
	//...
	//
		var firstname=document.getElementById('FirstName').value;
		var lastname=document.getElementById('LastName').value;
		var companyname=document.getElementById('CompanyName').value;
		var about=document.getElementById('About').value;
		var password=document.getElementById('Password').value;  
		var address=document.getElementById('Address').value;
		var email=document.getElementById('Email').value;
		var city =document.getElementById('City').value;
		var JSONObject = 
			{
				"username":email, 
				"firstname":firstname,
				"lastname":lastname,
				"companyname":companyname,
				"password":password,
				"about":about,
				"city":address,
				"email":email
			};
		
		var jsonData = JSON.stringify(JSONObject); 

		var request = $.ajax({
				url: "http://localhost:80/EasyPark/api/service/register",
				type: "POST",
				contentType: 'application/json',
				data: jsonData, 
			}).done(function() { alert('UspjeÅ¡no ste se registrovali! Probajte se upisati pomoÄ‡u vaÅ¡eg raÄ�una sad.');cleanFields(); })
		.fail(function() {alert("Greska sa konekcijom na server! Pokusajte ponovo.");cleanFields(); });
}
