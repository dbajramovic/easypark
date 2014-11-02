//VARIABLES
var map;
var myLatlng;
var myLoc;
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


function setMarker(coordinates, color,id)
{
	marker = new google.maps.Marker({
	    position: coordinates,
	    map: map,
	    icon: {
	        path: google.maps.SymbolPath.FORWARD_CLOSED_ARROW,
	        strokeColor: colors[color],
	        scale: 3
	    },
	    title: id.toString()
	});
}

function setDefaultData(jsonData, indexOf){
	indexOfDevice=indexOf;
	$('#Time').html(jsonData.time);
	$('#Date').html(jsonData.date);
	$('#Comment').html(jsonData.comment);
	$('#Speed').html(jsonData.speed); 
	myLatlng=new google.maps.LatLng(jsonData.lat,jsonData.long);
	setMarker(myLatlng,indexOf,indexOf);
}
function setDefaultAction(jsonData,indexOf){
	google.maps.event.addListener(marker, 'click', function() {
	   
		indexOfDevice=marker.getTitle();
	   setDefaultData(jsonData,indexOf);
	  });

}
function setData(jsonData){
	listOfDevices=jsonData;
    if (jsonData==""){alert('Nema uređaja s tim imenom u bazi, ili nije uključeno praćenje trenutno! Pokušajte ponovo...');if (interval!=null) clearInterval(interval);}
    else {
    	for(var g=0;g<listOfDevices.length;g++){
    	setDefaultData(listOfDevices[g],g,g);
    	setDefaultAction(listOfDevices[g],g);
    	}
    } 
    $('#NumberOfDevices').html(jsonData.length); 
}
function getData()
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
}
function search(textField){
	if (interval!=null) clearInterval(interval);
	initialize();
	searchName=textField;
	interval=setInterval(getData, 1000);
}

function initialize()
	{
	myLoc = new google.maps.LatLng(43.8562586,18.413076300000057);
		var mapProp = 
				{
				center:myLoc,
				zoom:15,
				mapTypeId:google.maps.MapTypeId.ROADMAP
			  	};
			map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
			if (navigator.geolocation) 
			  {
			        navigator.geolocation.getCurrentPosition(function(position) {
			            myLoc = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
			            a=position.coords.latitude;
			            b=position.coords.longitude;
			            map.setCenter(myLoc);
			            marker = new google.maps.Marker({
			    		    position: myLoc,
			    		    map: map,
			    		    title: 'Vaša pozicija'
			    		});
			        });
			  } 
		  else 
			  {
			    alert("Geolocation is not supported by this browser. Default location is: Sarajevo");
			    myLoc = new google.maps.LatLng(43.8562586,18.413076300000057);
			    marker = new google.maps.Marker({
				    position: myLoc,
				    map: map,
				    title: 'Vaša pozicija'
				});
			  }	
	}
google.maps.event.addDomListener(window, 'load', initialize);