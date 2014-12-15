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
	
	var usr=document.getElementById('EmailLogin').value;
	var pss=document.getElementById('PasswordLogin').value;
	var JSONObject= {"username":usr, "password":pss};
	var jsonData = JSON.stringify(JSONObject); 
	
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/session/insert",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) { if ((usr==='admin') && (pss==='admin')) window.open ('user.jsp','_self',false);}  
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
			success: function(data) { openUserScreen(data);}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});
}



