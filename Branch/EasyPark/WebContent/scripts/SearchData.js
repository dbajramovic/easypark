function sendToList(data){
	var dataList = document.getElementById('search-datalist');
	$(dataList).empty();
	//var input = document.getElementById('unosTeksta');

    data.forEach(function(item) {
      var option = document.createElement('option');
      option.value = item;
      dataList.appendChild(option);
      })
  
}

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

