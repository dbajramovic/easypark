
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
	$('.Vrijeme').html(jsonData.time);
	$('.Datum').html(jsonData.date);
	$('.Komentar').html(jsonData.comment);
	$('.RedniBroj').html(jsonData.ordinalNumber); 
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
	for(var g=0;g<jsonData.length;g++)
    {
    	for(var j=0;j<jsonData[g]['lista'].length;j++)
    	{	
    		setDefaultData(jsonData[g]['lista'][j],g);
    		setDefaultAction(jsonData[g]['lista'][j],g);
    	}
    }
}

function SendToService(a,b){
	var JSONObject= {"name":a, "choice":b};
	var jsonData = JSON.stringify(JSONObject); 
	var request = $.ajax({
			url: "http://localhost:8080/Tracker/api/service/positionadmin",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) {setData(data);}  
		});
	request.fail(function( jqXHR, textStatus ) {clearInterval(interval);});
}

function SendToCheck(){
	var a=document.getElementById('unosTeksta').value; 
	var rates = document.getElementsByName('izbor');
	var rate_value;
	for(var i = 0; i < rates.length; i++){
	    if(rates[i].checked){
	        rate_value = rates[i].value;
	    }
	}
	initialize();
SendToService(a,rate_value);
}

function sendToScreenAdmin(jsonData){
	$('.FirstName').html(jsonData.firstName);
		$('.LastName').html(jsonData.lastName);
		$('.CompanyName').html(jsonData.companyName);
		$('.NumberOfDevices').html(jsonData.numberOfDevices); 
}
function setDefaultDataOfUser(a){
	var id=document.getElementById('idUser').value;
	var JSONObject= {"username":a, "id":id};
	var jsonData = JSON.stringify(JSONObject); 
	
	var request = $.ajax({
			url: "http://localhost:8080/Tracker/api/service/getdataofadmin",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) { sendToScreenAdmin(data);}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});
}

function show_popup() {
	var id=document.getElementById('idUser').value;
	var myWindow = window.open("", "Unos novog uređaja", "toolbar=yes, scrollbars=yes, resizable=yes, top=100, left=500, width=430, height=440");
	myWindow.document.write("<!doctype html><html lang=''><head><meta charset='utf-8'><script src=\"http://code.jquery.com/jquery-latest.min.js\" type=\"text/javascript\"><\/script><title>Unos novog uređaja</title><link rel=\"stylesheet\" type=\"text/css\" href=\"css/DeviceStyle.css\" /><script src=\"scripts/SendDevice.js\" type=\"text/javascript\"><\/script></head><body><div class=\"okvir\"><form class=\"formaZaUnos\"><input type=\"text\" id=\"Username\" placeholder=\"Korisnicko ime\"></input><br><input type=\"text\" id=\"SerialName\" placeholder=\"Serijsko Ime\"></input><br><input type=\"text\" id=\"Name\" placeholder=\"Naziv(oprez)\"></input><br><input type=\"text\" id=\"Type\" placeholder=\"Tip\"></input><br><input type=\"password\" id=\"Password\" placeholder=\"Sifra\"></input><br><input type=\"button\" name=\"Register\" id=\"Register\" value=\"Registruj!\"onClick=\"Unesi("+id+")\"></input></form></div></body><html>");
	}
function sendToScreen(data){
	var dataList = document.getElementById('json-datalist');
	$(dataList).empty();
	var input = document.getElementById('unosTeksta');
	// Loop over the JSON array.
    data.forEach(function(item) {
      // Create a new <option> element.
      var option = document.createElement('option');
      // Set the value using the item in the JSON array.
      option.value = item;
      // Add the <option> element to the <datalist>.
      dataList.appendChild(option);
      })
  
}
function showHint(arg)
{
	var id=document.getElementById('idUser').value;
	var JSONObject= {"name":arg,"id":id};
	var jsonData = JSON.stringify(JSONObject); 
	var request = $.ajax({
			url: "http://localhost:8080/Tracker/api/service/searchadmin",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) { sendToScreen(data);}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});
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

