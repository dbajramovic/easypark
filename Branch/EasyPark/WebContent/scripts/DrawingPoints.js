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
var infowindow = new google.maps.InfoWindow({
    content: null,
    maxWidth: 400
});
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
			return marker
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
		return marker;
		}
}

function toggleBounce(yest,marker2) {
	if (yest==false) {marker2.setAnimation(null);} 
	else if (yest==true){marker2.setAnimation(google.maps.Animation.BOUNCE);}
};

function addInfoWindow(marker2, infoData){  
	var contentString = '<div id="content">'+
      '<div id="siteNotice">'+
      '</div>'+
      '<h1 id="firstHeading" class="firstHeading">Podaci o parkingu</h1>'+
      '<div id="bodyContent">'+
      '<p><b>Uslovi pod kojima biste ostavili auto</b>: vlasnik: <b>'+infoData._creatorID +'</b>'+ 
      ' video nadzor: <b>'+infoData._isthereCamera +'</b>'+
      ' dobar ulaz: <b>'+infoData._isthereGoodEntrance +'</b>'+
      ' zaštita: <b>'+infoData._isthereGuard +'</b>'+
      ' osvijetljeno: <b>'+infoData._isthereLight +'</b>'+
      ' cesta: <b>'+infoData._isthereRoad +'</b>'+
      ' krov: <b>'+infoData._isthereRoof +'</b>'+
      ' cijena: <b>'+infoData._price +'</b>'+
      ' broj mjesta: <b>'+infoData._totalnumber +'</b> </p>';
	  infowindow.content=contentString;
	  infowindow.open(map,marker2);  
};

function setHoverAction(marker2,jsonData){
	google.maps.event.addListener(marker2, 'mousemove', function() {
		addInfoWindow(marker2,jsonData);
	  });
	google.maps.event.addListener(marker2, 'mouseout', function() {
		infowindow.close();
	  });
}

function setDraggableAction(marker2){
	google.maps.event.addListener(marker2, 'dragend', function() {
		toggleBounce(true,marker2);
		map.setCenter(marker2.getPosition());
		var r= getParkings(marker2.getPosition().lat(),marker2.getPosition().lng(),10);
		toggleBounce(false,marker2);
	  });
}

function sendToScreen(data){
	data.forEach(function(item) {
    	var loc=new google.maps.LatLng(item._latitude, item._longitude);
    	var m = setMarker(loc,2,item._parkingID,false);
    	setHoverAction(m,item);
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

function getLocation() {
	 if (navigator.geolocation) {
	     navigator.geolocation.getCurrentPosition(function (position) {
	         initialLocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
	    	 map.setCenter(initialLocation);
	    	 var mark = setMarker(initialLocation,2,1,true);
	    	 setDraggableAction(mark);
	    	 var test = getParkings(position.coords.latitude,position.coords.longitude,10);
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

