var SpacesNum = 0;
var SpacesNumStart = 0;
var parkID = 0;
function updateParking(userid, spaces) {
	var JSONObject = {
		"spaces" : spaces,
		"userid" : userid
	};
	var jsonData = JSON.stringify(JSONObject);
	var request = $.ajax({
		url : "http://localhost:80/EasyPark/api/service/updateuserparking",
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
	}).done(function() {
		alert('Parking je azuriran!');
	}).fail(function() {
		alert("Greska sa konekcijom na server! Pokusajte ponovo.");
		cleanFields();
	});
}
function getParkings(userid) {
	var JSONObject = {
		"userid" : userid
	};
	var jsonData = JSON.stringify(JSONObject);
	var request = $.ajax({
		url : "http://localhost:80/EasyPark/api/service/parkingsofuser",
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		dataType : "JSON",
		success : function(data) {

			drawListOfParkings(data);
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
function drawProfile(infoData) {
	var ContentStringHeader = ' <div class="row left-panel">'
			+ '<div class="col-xs-8" id="left">';
	var ContentStringMain = '<h2 align="center">' + infoData._firstname + ' '
			+ infoData._lastname + '</h2><hr>'
			+ '<div class="panel panel-default">'
			+ '<div class="panel-heading"><a href="">Email</a></div>'
			+ '</div>' + '<p align="center">' + infoData._email + '</p>'
			+ '<div class="panel panel-default">'
			+ '<div class="panel-heading"><a href="">Lista Parkinga</a></div>'
			+ '</div>' + '<p id="listofUserParkings" align="center"></p>';
	+'</div>';

	var ContentStringFooter = '</div>' + '</div>';
	document.getElementById("userModalpremium").innerHTML = ContentStringHeader
			+ ContentStringMain + ContentStringFooter;
	getParkings(document.getElementById("userdiv").getAttribute('value'));
};
function sendUpdate(parkingID) {
	updateParking(parkingID, SpacesNum);
	location.reload();
}
function ChangeSpace(change) {
	if (change == 0) {
		SpacesNum--;

	}
	if (change == 1) {
		SpacesNum++;

	}
	if (SpacesNum < SpacesNumStart)
		document.getElementById("totalNum").setAttribute("style", "color:red");
	else if (SpacesNum > SpacesNumStart)
		document.getElementById("totalNum")
				.setAttribute("style", "color:green");
	else
		document.getElementById("totalNum")
				.setAttribute("style", "color:black");
	document.getElementById("totalNum").innerHTML = SpacesNum;

}
function drawListOfParkings(infoData) {
	for ( var i = 0; i < infoData.length; i++) {
		SpacesNum = SpacesNumStart = infoData[i]._totalnumber;
		parkID = infoData[i]._parkingID;
		var kamera = '<img src="http://s23.postimg.org/t59dwo3ob/Medal_Camera_None.png" width="55" height="85" title="Nema kamere">';
		var cesta = '<img src="http://s1.postimg.org/vpr8jybkf/Medal_Road_None.png" width="55" height="85" title="Nema ceste">';
		var krov = '<img src="http://s1.postimg.org/ocbukzrin/Medal_Roof_None.png" width="55" height="85" title="Nema krova">';
		var cuvar = '<img src="http://s1.postimg.org/shmr6wpan/Medal_Guard_None.png" width="55" height="85" title="Nema cuvara">';
		var svjetlo = '<img src="http://s1.postimg.org/ltq5kb5sf/Medal_Light_None.png" width="55" height="85" title="Nema svjetla">';
		if (infoData[i]._isthereCamera == true) {
			kamera = '<img src="http://s4.postimg.org/q249vw98t/Medal_Camera.png" width="55" height="85" title="Ima kamera">';
		}
		if (infoData[i]._isthereRoad == true) {
			cesta = '<img src="http://s4.postimg.org/qsx01obm5/Medal_Road.png" width="55" height="85" title="Ima cesta">';
		}
		if (infoData[i]._isthereRoof == true) {
			krov = '<img src="http://s4.postimg.org/s98ijtej1/Medal_Roof.png" width="55" height="85" title="Ima krov">';
		}
		if (infoData[i]._isthereGuard == true) {
			cuvar = '<img src="http://s4.postimg.org/i83oai1fx/Medal_Guard.png" width="55" height="85" title="Ima cuvar">';
		}
		if (infoData[i]._isthereLight == true) {
			svjetlo = '<img src="http://s4.postimg.org/ko5hocjil/Medal_Light.png" width="55" height="85" title="Ima svjetla">';
		}
		var ContentStringMain = "";
		ContentStringMain += '<b>Parking #'
				+ infoData[i]._parkingID
				+ '</b>'
				+ '<hr>'
				+ kamera
				+ cesta
				+ krov
				+ cuvar
				+ svjetlo
				+ '<hr>Slobodna mjesta: <b>'
				+ infoData[i]._freespots
				+ '</b> Zauzeta mjesta:<b>'
				+ infoData[i]._takenspots
				+ '</b><hr><button type="button" class="btn btn-default" aria-label="Minus">'
				+ '<span class="glyphicon glyphicon-minus-sign" aria-hidden="true" padding="2pt" onclick="ChangeSpace(0)"></span>'
				+ '</button>  Broj mjesta: <b id="totalNum" style="color:black">'
				+ SpacesNum
				+ '</b><button type="button" class="btn btn-default" aria-label="Plus" onclick="ChangeSpace(1)">'
				+ '<span class="glyphicon glyphicon-plus-sign" aria-hidden="true" padding="2pt"></span>'
				+ '</button><hr>'
				+ '<button type="button" class="btn btn-default" onclick="sendUpdate(parkID)">Potvrdi promjene</button>';
	}
	document.getElementById("listofUserParkings").innerHTML = ContentStringMain;
}
window.addEventListener("load", getUser());