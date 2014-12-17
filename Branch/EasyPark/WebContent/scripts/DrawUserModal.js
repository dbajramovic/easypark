var dugme = document.getElementById("Profil");

function getUser(user) {
	var userid = document.getElementById("userdiv").value;
	var JSONObject = {
		"userid" : user
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
window.addEventListener("load", getUser(5859));
function drawProfile(infoData) {
	var ContentStringHeader = ' <div class="row left-panel">'+'<div class="col-xs-8" id="left">';
	var ContentStringMain =  '<h2>'+infoData._firstname + ' ' + infoData._lastname +'</h2>'
							+ '<div class="panel panel-default">'
					        +'<div class="panel-heading"><a href="">Grad</a></div>'
					        +'</div>'
					        +'<p><b>'+infoData._city+'</b></p>'
					        + '<div class="panel panel-default">'
					        +'<div class="panel-heading"><a href="">Email</a></div>'
					        +'</div>'
					        +'<p>'+infoData._email+'</p>';
	var ContentStringFooter = '</div>'+ '</div>';
	document.getElementById("userModal").innerHTML = ContentStringHeader + ContentStringMain + ContentStringFooter;
};