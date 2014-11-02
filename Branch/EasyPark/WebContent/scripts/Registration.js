function cleanFields(){
	document.getElementById('Username').value="";
	document.getElementById('FirstName').value="";
	document.getElementById('LastName').value="";
	document.getElementById('CompanyName').value="";
	document.getElementById('About').value="";
	document.getElementById('Password').value=""; 

}
function register(){
		var username=document.getElementById('Username').value;
		var firstname=document.getElementById('FirstName').value;
		var lastname=document.getElementById('LastName').value;
		var companyname=document.getElementById('CompanyName').value;
		var about=document.getElementById('About').value;
		var password=document.getElementById('Password').value;  
	   	var JSONObject= {
		"username":username, 
		"firstname":firstname,
		"lastname":lastname,
		"companyname":companyname,
		"password":password,
		"about":about
		};
		var jsonData = JSON.stringify(JSONObject); 
		var greska=0;
		var request = $.ajax({
				url: "http://localhost:8080/Tracker/api/service/register",
				type: "POST",
				contentType: 'application/json',
				data: jsonData, 
			}).done(function() { alert('Uspješno ste se registrovali! Probajte se upisati pomoću vašeg računa sad.'); greska=1;cleanFields(); })
		.fail(function() {alert("Greska! Pokusajte ponovo.");cleanFields(); });
}
function initialize()
	{
	myLoc = new google.maps.LatLng(43.8562586,18.413076300000057);
		var mapProp = 
				{
				center:myLoc,
				zoom:15,
				mapTypeId:google.maps.MapTypeId.ROADMAP
			  	};
			map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
			if (navigator.geolocation) 
			  {
			        navigator.geolocation.getCurrentPosition(function(position) {
			            myLoc = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			            map.setCenter(myLoc);
			            marker = new google.maps.Marker({
			    		    position: myLoc,
			    		    map: map,
			    		    title: 'Vaša pozicija'
			    		});
			        });
			  } 
		  else 
			  {
			    alert("Geolocation is not supported by this browser. Default location is: Sarajevo");
			    myLoc = new google.maps.LatLng(43.8562586,18.413076300000057);
			    marker = new google.maps.Marker({
				    position: myLoc,
				    map: map,
				    title: 'Vaša pozicija'
				});
			  }	
	}
google.maps.event.addDomListener(window, 'load', initialize);