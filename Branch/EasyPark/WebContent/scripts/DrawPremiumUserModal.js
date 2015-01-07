var SpacesNum = 0;
var SpacesNumStart = 0;
var parkID = 0;
var userID = document.getElementById("userdiv").getAttribute('value');
window.addEventListener("load", getParkings(userID));

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
			console.log(data)
			drawListOfParkings(data);
		}
	});
	request.fail(function(jqXHR, textStatus) {
		console.log("SASDSD")
		clearInterval(interval);
	});
}

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
	console.log(infoData);
	parkID = infoData[0]._parkingID;
	for ( var i = 0; i < infoData.length; i++) {
		SpacesNum = SpacesNumStart = infoData[i]._totalnumber;
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
		var ContentStringMain = " ";
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
function openParkingRegModal() {
	//$("#parkingregistrationModal").modal('show');
	//$("#parkingregistrationModal").appendTo("body");
}
document.getElementById('newparkingbtn').onclick = function() {
	openParkingRegModal();
};
document.getElementById('parkResList').onclick = function() {
	getParkingReservations(parkID);
};
function getParkingReservations(pID) {
	console.log(pID);
	parkID = pID;
	var JSONObject = {
		"parkingid" : pID
	};
	var jsonData = JSON.stringify(JSONObject);
	var request = $
			.ajax({
				url : "http://localhost:80/EasyPark/api/service/parkingreservationsforowner",
				type : "POST",
				contentType : 'application/json',
				data : jsonData,
				dataType : "JSON",
				success : function(data) {
					console.log(data,parkID);
					drawListOfReservations(data);
				}
			});
	request.fail(function(jqXHR, textStatus) {
		console.log("SASDSD")
		clearInterval(interval);
	});
}
function drawListOfReservations(infoData,pID) {
	document.getElementById("listofUserParkings").innerHTML = "";
	var ContentStringMain = "";
	for (i = 0; i < infoData.length; i++) {
		ContentStringMain += "<p>Korisnik #" + infoData[i]._personID
				+ " je zaduzio " + infoData[i]._numberofreserved
				+ " mjesta do " + infoData[i]._dateofEnd + '</p><button type="button" class="btn btn-default btn-sm" id="Prijavi'+i+'" onclick="ReportUser('+infoData[i]._personID+','+userID+','+parkID+','+infoData[i]._numberofreserved+')"><span class="glyphicon glyphicon-star" aria-hidden="true"></span> Prijavi</button><hr>';
	}
	document.getElementById("listofUserParkings").innerHTML = ContentStringMain;
}
function ReportUser(aID,uID,pID,nR) {
	var provjera = confirm('Da li ste sigurni da zelite prijaviti korisnika '+aID+'?');
	if(provjera == true) {
		var JSONObject = {
				"ownerid" : userID,
				"accusedid" : userID,
				"parkingid" : parkID,
				"numofR" : nR
			};
			var jsonData = JSON.stringify(JSONObject);
			var request = $
					.ajax({
						url : "http://localhost:80/EasyPark/api/service/reportuser",
						type : "POST",
						contentType : 'application/json',
						data : jsonData,
						dataType : "JSON",
						success : function(data) {
							alert("Korisnik prijavljen!");
							location.reload();
						}
					});
			request.fail(function(jqXHR, textStatus) {
				console.log("SASDSD")
				clearInterval(interval);
			});
	}
	else {
		
	}
}