
var dugme = document.getElementById("abc");
function getUser(user) {
	var JSONObject = {
		"user" : user
	};
	var jsonData = JSON.stringify(JSONObject);
	var request = $.ajax({
		url : "http://localhost:80/EasyPark/api/service/userdata",
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		dataType : "JSON",
		success : function(data) {
			getUserModal(data);
		}
	});
	request.fail(function(jqXHR, textStatus) {
		clearInterval(interval);
	});
}
dugme.addEventListener("click", getUserModal);
function getUserModal(data) {
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