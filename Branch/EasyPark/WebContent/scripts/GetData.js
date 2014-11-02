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
	var JSONObject= {"name":arg};
	var jsonData = JSON.stringify(JSONObject); 
	var request = $.ajax({
			url: "http://localhost:8080/Tracker/api/service/search",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) { sendToScreen(data);}  
		});
	request.fail(function( jqXHR, textStatus ) {clearInterval(interval);});
}