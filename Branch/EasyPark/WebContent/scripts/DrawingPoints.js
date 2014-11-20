(function() {
//VARIABLES
var map;
var initialLocation;
var imageParking = 'css/parking.png';
var marker;
var i=1;
var a;
var b;
var interval;
var searchName;
var listOfDevices;
var indexOfDevice=0;
var colors= ['aqua', 'black', 'blue', 'fuchsia', 'gray', 'green', 
             'lime', 'maroon', 'navy', 'olive', 'orange', 'purple', 'red', 
             'silver', 'teal', 'white', 'yellow'];
var styles = [
              {
                stylers: [
                  { hue: "#00ffe6" },
                  { saturation: -20 }
                ]
              },{
                featureType: "road",
                elementType: "geometry",
                stylers: [
                  { lightness: 100 },
                  { visibility: "simplified" }
                ]
              },{
                featureType: "road",
                elementType: "labels",
                stylers: [
                  { visibility: "off" }
                ]
              }
            ];
function createList(locatlong,locatlat){
	var desno=true;
	var lijevo=false;
	var l=[];
	for (var i=1;i<20;i++){
		if (desno){var a= locatlong + (i/1000); desno=!desno;}
		if (!desno){var d= locatlat-(i/1000);desno=!desno;}
		if (lijevo){var a= locatlong - (i/1000); lijevo=!lijevo;}
		if (!lijevo){var d= locatlat+(i/1000);lijevo=!lijevo;}
		var f=new google.maps.LatLng(a, d); 
		l.push(f);
	}
	return l;
}

function setMarker(coordinates, color,id, defaultMarker)
{
	if (defaultMarker)
		{
			var marker = new google.maps.Marker({
			    position: coordinates,
			    map: map,
			    title:"Vasa lokacija!",
			    animation: google.maps.Animation.DROP,
			    draggable:true
			});
		}
	else
		{
		marker = new google.maps.Marker({
		    position: coordinates,
		    map: map,
		    icon: {
		        path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
		        strokeColor: colors[color],
		        scale: 3
		    },
		    title: id.toString(),
		    animation: google.maps.Animation.DROP,
		    icon: imageParking
		});
		}
}

//function setDefaultData(jsonData, indexOf){
//	indexOfDevice=indexOf;
//	$('#Time').html(jsonData.time);
//	$('#Date').html(jsonData.date);
//	$('#Comment').html(jsonData.comment);
//	$('#Speed').html(jsonData.speed); 
//	myLatlng=new google.maps.LatLng(jsonData.lat,jsonData.long);
//	setMarker(myLatlng,indexOf,indexOf);
//}
//function setDefaultAction(jsonData,indexOf){
//	google.maps.event.addListener(marker, 'click', function() {
//	   
//		indexOfDevice=marker.getTitle();
//	   setDefaultData(jsonData,indexOf);
//	  });
//
//}
//function setData(jsonData){
//	listOfDevices=jsonData;
//    if (jsonData==""){alert('Nema uređaja s tim imenom u bazi, ili nije uključeno praćenje trenutno! Pokušajte ponovo...');if (interval!=null) clearInterval(interval);}
//    else {
//    	for(var g=0;g<listOfDevices.length;g++){
//    	setDefaultData(listOfDevices[g],g,g);
//    	setDefaultAction(listOfDevices[g],g);
//    	}
//    } 
//    $('#NumberOfDevices').html(jsonData.length); 
//}
/*function getData()
{
	var JSONObject= {"name":searchName};
	var jsonData = JSON.stringify(JSONObject); 
	var request = $.ajax({
			url: "http://localhost:8080/Tracker/api/service/position",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) {setData(data);}  
		});
	request.fail(function( jqXHR, textStatus ) {alert( "Request failed: " + textStatus ); clearInterval(interval);});
}*/
//function search(textField){
//	if (interval!=null) clearInterval(interval);
//	initialize();
//	searchName=textField;
//	interval=setInterval(getData, 1000);
//}

function sendToScreen(data){
    data.forEach(function(item) {
    	var loc=new google.maps.LatLng(data._latitude, data._longitude);
    	setMarker(loc,2,1,false);
        });
}
function getParkings(myLat,myLong,range)
{
	var JSONObject= {"latitude":myLat, "longitude":myLong, "range":range};
	var jsonData = JSON.stringify(JSONObject); 
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/service/parkingsinrange",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) { sendToScreen(data);}  
		});
	request.fail(function( jqXHR, textStatus ) {clearInterval(interval);}
	);
}

function sendToScreenT(data){
    data.forEach(function(item) {
    	setMarker(item,2,1,false);
        });
}



function getLocation() {
	 if (navigator.geolocation) {
	     navigator.geolocation.getCurrentPosition(function (position) {
	         initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
	    	 map.setCenter(initialLocation);
	    	 setMarker(initialLocation,2,1,true);
	    	 var test = createList(position.coords.latitude,position.coords.longitude);
	    	 sendToScreenT(test);
	    	 //getParkings(position.coords.longitude,position.coords.latitude,10);
	     });
	 }else{alert("Vaš pretrazivac ne odaje informaciju o vašoj geolokaciji!");}
}
       



function initialize(){
	var mapProp = {
				center:{ lat: -34.397, lng: 150.644},
				zoom:15,
				mapTypeId:google.maps.MapTypeId.ROADMAP
			  	};
	map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
	map.setOptions({styles: styles});
	getLocation();
	}

google.maps.event.addDomListener(window, 'load', initialize);
google.maps.event.addListener(marker, 'click', function() {
    map.setZoom(8);
    map.setCenter(marker.getPosition());
  });

})();

