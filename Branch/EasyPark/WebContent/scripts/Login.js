function deleteSession(){
	var JSONObject= {"valid":"true"};
	var jsonData = JSON.stringify(JSONObject); 
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/session/delete",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) { window.open ('index.html','_self',false);}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});
}

//Opens userPage
function openUserScreen(data2){
	console.log(data2);
	//var JSONObject= {"username":data2._email,"password":data2._password,"id": data2._personID };
	//var jsonData = JSON.stringify(JSONObject); 
	
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/session/insert",
			type: "POST",
			contentType: 'application/json',
			data: JSON.stringify(data2), //jsonData,
			dataType: "JSON",
			success: function(data) {
				//console.log(data2);
				if (data2._type==2){window.open ('user.jsp','_self',false);}
				if (data2._type==3){window.open ('premium.jsp','_self',false);}
				if (data2._type==4){ window.open ('admin.jsp','_self',false);}
				}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});

}

//Check is it exists
function login(){
	var usr=document.getElementById('EmailLogin').value;
	var pss=document.getElementById('PasswordLogin').value;
	var JSONObject= {"username":usr, "password":pss};
	var jsonData = JSON.stringify(JSONObject); 
	
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/service/login",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) { 
				$("#userdiv").val(data._personID);
				if (data._personID==0){alert('Unijeli ste pogrešne podatke');} 
				else{
					openUserScreen(data);
					}
				}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});
}



