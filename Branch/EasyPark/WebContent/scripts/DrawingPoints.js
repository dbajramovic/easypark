(function() {
	// VARIABLES
	var map;
	var initialLocation;
	var imageParking = 'css/parking.png';
	var marker;
	var i = 1;
	var a;
	var b;
	var interval;
	var searchName;
	var listOfDevices;
	var indexOfDevice = 0;
	var infowindow = new google.maps.InfoWindow({
		content : null,
		maxWidth : 400
	});
	var colors = [ 'aqua', 'black', 'blue', 'fuchsia', 'gray', 'green', 'lime',
			'maroon', 'navy', 'olive', 'orange', 'purple', 'red', 'silver',
			'teal', 'white', 'yellow' ];
	var styles = [ {
		stylers : [ {
			hue : "#00ffe6"
		}, {
			saturation : -20
		} ]
	}, {
		featureType : "road",
		elementType : "geometry",
		stylers : [ {
			lightness : 100
		}, {
			visibility : "simplified"
		} ]
	}, {
		featureType : "road",
		elementType : "labels",
		stylers : [ {
			visibility : "off"
		} ]
	} ];

	function setMarker(coordinates, color, id, defaultMarker) {
		if (defaultMarker) {
			var marker = new google.maps.Marker({
				position : coordinates,
				map : map,
				title : "Vasa lokacija!",
				animation : google.maps.Animation.DROP,
				draggable : true
			});
			return marker;
		} else {
			marker = new google.maps.Marker({
				position : coordinates,
				map : map,
				icon : {
					path : google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
					strokeColor : colors[color],
					scale : 3
				},
				title : id.toString(),
				animation : google.maps.Animation.DROP,
				icon : imageParking
			});
			return marker;
		}
	}

	function toggleBounce(yest, marker2) {
		if (yest == false) {
			marker2.setAnimation(null);
		} else if (yest == true) {
			marker2.setAnimation(google.maps.Animation.BOUNCE);
		}
	};

	function getPicture(picid) {
		
		var JSONObject = {
			"pictureid" : picid
		};
		var jsonData = JSON.stringify(JSONObject);
		var request = $.ajax({
			url : "http://localhost:80/EasyPark/api/service/pic",
			type : "POST",
			contentType : 'application/json',
			data : jsonData,
			dataType : "JSON",
			success : function(data2) {
				
				return data2;
			}
		});
		request.fail(function(jqXHR, textStatus) {
			console.log("dada");
			clearInterval(interval);
		});
	}
	
	function addInfoWindow(marker2, infoData) {
		var contentString = '<div id="content">'
				+ '<div id="siteNotice">'
				+ '</div>'
				+ '<h1 id="firstHeading" class="firstHeading">Podaci o parkingu</h1>'
				+ '<div id="bodyContent">'
				+ '<p><b>Uslovi pod kojima biste ostavili auto</b>: vlasnik: <b>'
				+ infoData._creatorID
				+ '</b>'
				+ ' video nadzor: <b>'
				+ infoData._isthereCamera
				+ '</b>'
				+ ' dobar ulaz: <b>'
				+ infoData._isthereGoodEntrance
				+ '</b>'
				+ ' zaštita: <b>'
				+ infoData._isthereGuard
				+ '</b>'
				+ ' osvijetljeno: <b>'
				+ infoData._isthereLight
				+ '</b>'
				+ ' cesta: <b>'
				+ infoData._isthereRoad
				+ '</b>'
				+ ' krov: <b>'
				+ infoData._isthereRoof
				+ '</b>'
				+ ' cijena: <b>'
				+ infoData._price
				+ '</b>'
				+ ' broj mjesta: <b>'
				+ infoData._totalnumber + '</b> </p>';
		infowindow.content = contentString;
		infowindow.open(map, marker2);
	};

	

	function getParkingModal(marker2, infoData) {
		var picData = getPicture(3078);
		console.log(picData);
		var kamera = '<img src="http://s23.postimg.org/t59dwo3ob/Medal_Camera_None.png" width="55" height="85" title="Nema kamere">';
		var cesta = '<img src="http://s1.postimg.org/vpr8jybkf/Medal_Road_None.png" width="55" height="85" title="Nema ceste">';
		var krov = '<img src="http://s1.postimg.org/ocbukzrin/Medal_Roof_None.png" width="55" height="85" title="Nema krova">';
		var cuvar = '<img src="http://s1.postimg.org/shmr6wpan/Medal_Guard_None.png" width="55" height="85" title="Nema cuvara">';
		var svjetlo = '<img src="http://s1.postimg.org/ltq5kb5sf/Medal_Light_None.png" width="55" height="85" title="Nema svjetla">';
		if (infoData._isthereCamera == true) {
			kamera = '<img src="http://s4.postimg.org/q249vw98t/Medal_Camera.png" width="55" height="85" title="Ima kamera">';
		}
		if (infoData._isthereRoad == true) {
			cesta = '<img src="http://s4.postimg.org/qsx01obm5/Medal_Road.png" width="55" height="85" title="Ima cesta">';
		}
		if (infoData._isthereRoof == true) {
			krov = '<img src="http://s4.postimg.org/s98ijtej1/Medal_Roof.png" width="55" height="85" title="Ima krov">';
		}
		if (infoData._isthereGuard == true) {
			cuvar = '<img src="http://s4.postimg.org/i83oai1fx/Medal_Guard.png" width="55" height="85" title="Ima cuvar">';
		}
		if (infoData._isthereLight == true) {
			svjetlo = '<img src="http://s4.postimg.org/ko5hocjil/Medal_Light.png" width="55" height="85" title="Ima svjetla">';
		}
		var ContentString = '<div class="modal-dialog" style="width:600px">'
				+ ' <div class="modal-content">'
				+ '<div class="modal-header">'
				+ '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'
				+ '<h4 class="modal-title">Parking #'
				+ infoData._parkingID
				+ '</h4>'
				+ '</div>'
				+ '<div class="modal-body">'
				+ '<p>Vlasnik:<b>'+ infoData._creator+'</b> <br>Broj telefona: <b>'+infoData._telefon
				+ '</b><div id="container1" style="width: 270px; height: 200px; margin: 0 auto; float:left" ></div>'
				+ '<div id="container2" style="width: 270px; height: 200px; margin: 0; float:left"></div>'
				+ '<div id="medalje"><hr><h4 class="modal-title" style="clear:both">Medalje</h4>'
				+ kamera
				+ ' '
				+ cesta
				+ ' '
				+ krov
				+ ' '
				+ cuvar
				+ ' '
				+ svjetlo
				+'<hr><b>Cijena:</b> '
				+infoData._price
				+ ' KM</p>'
				+ '</div></div>'
				+ '<div class="modal-footer">'
				+ '<button type="button" class="btn btn-default" data-dismiss="modal">Zatvori</button>'
				+ '<button type="button" class="btn btn-primary">NE RADI</button>'
				+ '</div>' + '</div>' + '</div>' + '</div>';
		document.getElementById("parkingModal").innerHTML = ContentString;
		;
		/*var slika = document.createElement('img');
		slika.src = 'data:image/jpg;base64,'+picData._slika;
		document.body.appendChild(slika);*/
		$("#parkingModal").modal('show');
	};
	
	function setHoverAction(marker2, jsonData) {
		google.maps.event
				.addListener(
						marker2,
						'click',
						function() {
							getParkingModal(marker2, jsonData);
							$(function() {
								$('#container1')
										.highcharts(
												{
													chart : {
														plotBackgroundColor : null,
														plotBorderWidth : 0,
														plotShadow : false
													},
													title : {
														text : 'Sigurnosna ocjena<br>parkinga',
														align : 'center',
														verticalAlign : 'middle',
														y : 50
													},
													tooltip : {
														pointFormat : '{series.name}: <b>{point.percentage:.1f}</b>'
													},
													plotOptions : {
														pie : {
															dataLabels : {
																enabled : false,
																distance : -50,
																style : {
																	fontWeight : 'bold',
																	color : 'white',
																	textShadow : '0px 1px 2px black'
																}
															},
															startAngle : -90,
															endAngle : 90,
															center : [ '50%',
																	'75%' ]
														}
													},
													series : [ {
														type : 'pie',
														name : 'Ocjena',
														innerSize : '50%',
														data : [
																[
																		'Sigurnosna Ocjena',
																		jsonData._secrating ],
																[
																		'',
																		10 - jsonData._secrating ],

														]
													} ]
												});

							});
							$(function() {
								$('#container2')
										.highcharts(
												{
													chart : {
														plotBackgroundColor : null,
														plotBorderWidth : 0,
														plotShadow : false
													},
													title : {
														text : 'Ukupna ocjena<br>parkinga',
														align : 'center',
														verticalAlign : 'middle',
														y : 50
													},
													tooltip : {
														pointFormat : '{series.name}: <b>{point.percentage:.1f}</b>'
													},
													plotOptions : {
														pie : {
															dataLabels : {
																enabled : false,
																distance : -50,
																style : {
																	fontWeight : 'bold',
																	color : 'white',
																	textShadow : '0px 1px 2px black'
																}
															},
															startAngle : -90,
															endAngle : 90,
															center : [ '50%',
																	'75%' ]
														}
													},
													series : [ {
														type : 'pie',
														name : 'Ocjena',
														innerSize : '50%',
														data : [
																[
																		'Ukupna ocjena',
																		jsonData._ovrrating ],
																[
																		'',
																		10 - jsonData._ovrrating ],

														]
													} ]
												});

							});
						 addInfoWindow(marker2,jsonData);
						});
		google.maps.event.addListener(marker2, 'mouseout', function() {
			infowindow.close();
		});
	}

	function setDraggableAction(marker2) {
		google.maps.event.addListener(marker2, 'dragend', function() {
			toggleBounce(true, marker2);
			map.setCenter(marker2.getPosition());
			var r = getParkings(marker2.getPosition().lat(), marker2
					.getPosition().lng(), 10);
			toggleBounce(false, marker2);
		});
	}

	function sendToScreen(data) {
		data.forEach(function(item) {
			var loc = new google.maps.LatLng(item._latitude, item._longitude);
			var m = setMarker(loc, 2, item._parkingID, false);
			setHoverAction(m, item);
		});
	}
	function getParkings(myLat, myLong, range) {
		var JSONObject = {
			"latitude" : myLat,
			"longitude" : myLong,
			"range" : range
		};
		var jsonData = JSON.stringify(JSONObject);
		var request = $.ajax({
			url : "http://localhost:80/EasyPark/api/service/parkingsinrange",
			type : "POST",
			contentType : 'application/json',
			data : jsonData,
			dataType : "JSON",
			success : function(data) {
				sendToScreen(data);
			}
		});
		request.fail(function(jqXHR, textStatus) {
			clearInterval(interval);
		});
	}
	
	
	//returns our location
	function getLocation() {
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(function(position) {
				initialLocation = new google.maps.LatLng(
						position.coords.latitude, position.coords.longitude);
				map.setCenter(initialLocation);
				var mark = setMarker(initialLocation, 2, 1, true);
				setDraggableAction(mark);
				var test = getParkings(position.coords.latitude,
						position.coords.longitude, 10);
			});
		} else {
			alert("Vaš pretrazivac ne odaje informaciju o vašoj geolokaciji!");
		}
	}

	//MAIN function for initialize
	function initialize() {
		var mapProp = {
			center : {
				lat : -34.397,
				lng : 150.644
			},
			zoom : 15,
			mapTypeId : google.maps.MapTypeId.ROADMAP
		};
		map = new google.maps.Map(document.getElementById("googleMap"), mapProp);
		map.setOptions({
			styles : styles
		});
		getLocation();
	}
	google.maps.event.addDomListener(window, 'load', initialize);
	google.maps.event.addListener(marker, 'click', function() {
		map.setZoom(8);
		map.setCenter(marker.getPosition());
	});
})();




