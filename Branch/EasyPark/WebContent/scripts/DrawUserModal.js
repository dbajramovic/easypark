function getReservations(id) {
	var JSONObject = {
		"userid" : id
	};
	var jsonData = JSON.stringify(JSONObject);
	var request = $.ajax({
		url : "http://localhost:80/EasyPark/api/service/userreservations",
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		dataType : "JSON",
		success : function(data) {
			console.log(data);
			drawListOfReservations(data);
		}
	});
	request.fail(function(jqXHR, textStatus) {
		clearInterval(interval);
	});
}

function getUser() {
	var userid = document.getElementById("userdiv").getAttribute('value');
	var JSONObject = {
		"userid" : userid
	};
	var jsonData = JSON.stringify(JSONObject);
	var request = $.ajax({
		url : "http://localhost:80/EasyPark/api/service/userdata",
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		dataType : "JSON",
		success : function(data) {
			drawProfile(data);
		}
	});
	request.fail(function(jqXHR, textStatus) {
		clearInterval(interval);
	});
}
window.addEventListener("load", getUser());
function drawProfile(infoData) {
	var ContentStringHeader = ' <div class="row left-panel">'
			+ '<div class="col-xs-8" id="left">';
	var ContentStringMain = '<h2 align="center">'
			+ infoData._firstname
			+ ' '
			+ infoData._lastname
			+ '</h2><hr>'
			+ '<div class="panel panel-default">'
			+ '<div class="panel-heading"><a href="">Email</a></div>'
			+ '</div>'
			+ '<p align="center">'
			+ infoData._email
			+ '</p>'
			+ '<div class="panel panel-default">'
			+ '<div class="panel-heading"><a href="">Lista Rezervacija</a></div>'
			+ '</div>'
			+ '<p id="listofReservations" align="center"></p>';
	+'</div>'; 

	var ContentStringFooter = '</div>' + '</div>';
	document.getElementById("userModalregular").innerHTML = ContentStringHeader
			+ ContentStringMain + ContentStringFooter;
	getReservations(document.getElementById("userdiv").getAttribute('value'));
};
function drawListOfReservations(infoData) {
	var ContentStringMain = "";
	for ( var i = 0; i < infoData.length; i++) {
		ContentStringMain += 'Parking #'
		+infoData[i]._parkingID + ' Traje do: '+infoData[i]._dateofEnd + '<hr>' ;
	}
	document.getElementById("listofReservations").innerHTML = ContentStringMain;
}