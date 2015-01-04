function sendToList(data){
	console.log('promjena');
	var dataList = $('#search-datalist');
	//$(dataList).empty();
    data.forEach(function(item) {
      var option = document.createElement('option');
      option.value = data[item];
      dataList.appendChild(option);
      })
  
}
var list=['Sarajevo','Tuzla','Mostar'];

//$('#searchBar').on('change',function(){sendToList(list);});

function showHint(arg, choice)
{
	var JSONObject= {
			"term":arg,
			"choice": choice
			};
	var jsonData = JSON.stringify(JSONObject); 
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/service/searchsuggestions",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) { sendToList(data);}  
		});
	request.fail(function( jqXHR, textStatus ) {clearInterval(interval);});
}

