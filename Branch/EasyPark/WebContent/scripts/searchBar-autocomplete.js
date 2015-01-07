$(function(){
  var towns = [];
   // { value: 'Sarajevo', data: {longitude: 25, latitude: 24} },
  
  var request = $.ajax({
		url : "http://localhost:80/EasyPark/api/service/getsuggestions",
		type : "POST",
		contentType : 'application/json',
		dataType : "JSON",
		success : function(data) {

			console.log(data);
			//ubaciti u towns
		}
	});
	request.fail(function(jqXHR, textStatus) {
		clearInterval(interval);
	});
  
  // setup autocomplete function pulling from currencies[] array
  $('#autocomplete').autocomplete({
    lookup: towns,
    onSelect: function (suggestion) {    
    	//console.log(suggestion.data.longitude+' '+suggestion.data.latitude);
    }
  });
  
  

});