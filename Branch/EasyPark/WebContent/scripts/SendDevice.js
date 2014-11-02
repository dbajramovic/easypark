function Unesi(id){
   
	
	var username=document.getElementById('Username').value;
   var serialname=document.getElementById('SerialName').value;
   var name=document.getElementById('Name').value;
   var type=document.getElementById('Type').value;
   var password=document.getElementById('Password').value;
   
   	var JSONObject= {
   	"id":id,
	"username":username, 
	"serialname":serialname,
	"name":name,
	"type":type,
	"password":password
	};
   	if ((username.length<2)||(serialname.length<2)||(name.length<2)||(type.length<2)||(password.length<4)){
   		alert('Unijeli ste neko od polja sa manje od 2 znaka ili password sa manje od 4! Pokusajte opet.');
   		window.close();
   	}
   	else{
	var jsonData = JSON.stringify(JSONObject); 

	var request = $.ajax({
			url: "http://localhost:8080/Tracker/api/service/registerdevice",
			type: "POST",
			contentType: 'application/json',
			data: jsonData     	
	}).done(function() { alert('Uspjesno ste registrovali novi uredjaj');window.close(); })
    .fail(function() {alert("Greska! Pokusajte ponovo.");window.close(); });
   	}
   	}