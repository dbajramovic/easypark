$(function(){
  var towns = [];
   // { value: 'Sarajevo', data: {longitude: 25, latitude: 24} },
  
  var request = $.ajax({
		url : "http://localhost:80/EasyPark/api/service/getsuggestions",
		type : "POST",
		contentType : 'application/json',
		dataType : "JSON",
		success : function(data) {
			data.forEach(function(item){
				var temp= {
						value:item._name,
						data:{
							longitude: item._longitude,
							latitude: item._latitude
							}
				};
				towns.push(temp);
			});
		}
	});
  
  // setup autocomplete function pulling from currencies[] array
  $('#autocomplete').autocomplete({
    lookup: towns,
    onSelect: function (suggestion) {    
    	//console.log(suggestion.data.longitude+' '+suggestion.data.latitude);
    }
  });
  
  

});