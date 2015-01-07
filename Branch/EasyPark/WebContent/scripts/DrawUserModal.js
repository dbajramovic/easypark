
function getReservations() {
	var id = document.getElementById("userdiv").getAttribute('value');
	var date = new Date();
	var test = date.getFullYear()+'-'+("0" + (date.getMonth() + 1)).slice(-2)+'-'+("0" + date.getDate()).slice(-2);
	console.log(test);
	date = new Date(test);
	console.log(date);
	var JSONObject = {
		"userid" : id,
		"date" : test
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
window.addEventListener("load", getReservations());
function drawListOfReservations(infoData) {
	var ContentStringMain = "";
	for ( var i = 0; i < infoData.length; i++) {
		ContentStringMain += 'Parking #'
		+infoData[i]._parkingID + ' Traje do: '+infoData[i]._dateofEnd + '<hr>' ;
	}
	document.getElementById("listofReservations").innerHTML = ContentStringMain;
}