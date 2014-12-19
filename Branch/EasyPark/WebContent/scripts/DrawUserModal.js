var dugme = document.getElementById("Profil");

function getUser() {
	var userid = document.getElementById("userdiv").getAttribute('value');
	var JSONObject = {
		"userid" : userid
	};
	var jsonData = JSON.stringify(JSONObject);
	var request = $.ajax({
		url : "http://localhost:80/EasyPark/api/service/userdata",
		type : "POST",
		contentType : 'application/json',
		data : jsonData,
		dataType : "JSON",
		success : function(data) {
			drawProfile(data);
		}
	});
	request.fail(function(jqXHR, textStatus) {
		clearInterval(interval);
	});
}
window.addEventListener("load", getUser());
function drawProfile(infoData) {
	var ContentStringHeader = ' <div class="row left-panel">'+'<div class="col-xs-8" id="left">';
	var ContentStringMain =  '<h2>'+infoData._firstname + ' ' + infoData._lastname +'</h2><hr>'
					        + '<div class="panel panel-default">'
					        +'<div class="panel-heading"><a href="">Email</a></div>'
					        +'</div>'
					        +'<p>'+infoData._email+'</p>';
	var ContentStringFooter = '</div>'+ '</div>';
	document.getElementById("userModal").innerHTML = ContentStringHeader + ContentStringMain + ContentStringFooter;
};