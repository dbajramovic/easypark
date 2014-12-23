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
			provjeriFeed(data._city);
		}
	});
	request.fail(function(jqXHR, textStatus) {
		clearInterval(interval);
	});
}

var prvi_put = true;
	window.setInterval(ProvjeriFeed,1000);

var vrijeme_provjere = new Date();


function ProvjeriFeed(grad) {
	var feedString = "";
	switch (grad) {
		case 1:
			feedString = 'http://www.klix.ba/rss/vijesti/bih';
		case 2:
			feedString = 'http://www.klix.ba/rss/vijesti/bih';
		default:
			feedString = 'http://www.klix.ba/rss/vijesti/bih';
	}
	var feed = new google.feeds.Feed(feedString);
        feed.setNumEntries(3);
	feed.load(function (data) { 
        var duzina = data.feed.entries.length;  
  	// Parse data depending on the specified response format, default is JSON. 
  	var GB = document.getElementById("left");
	if(prvi_put)
               {
		GB.innerHTML += '<h3 align="center">Vijesti iz BIH</h3><hr>';
		for (var i=0;i<duzina;i++) {
			GB.innerHTML += '<div class="panel-heading"><a href="'+data.feed.entries[i].link+'"><h4>'+data.feed.entries[i].title+"</h4>";
               	 	GB.innerHTML += data.feed.entries[i].content;
			}
		prvi_put=false;
		vrijeme_provjere = new Date(data.feed.entries[0].publishedDate);
		GB.innerHTML += '<hr><p><a href="http://etf.unsa.ba/">Fakultet</a> | <a href="https://github.com/dbajramovic/easypark">Source Code</a></p>'
	}
	else {       
        	duzina-=1;
        	for (var i=0;i<duzina+1;i++) {
                	var vrijeme_vijesti = new Date(data.feed.entries[i].publishedDate);
                	if (vrijeme_provjere<vrijeme_vijesti) {
				GB.innerHTML += "<h1>"+data.feed.entries[i].title+" by "+data.feed.entries[i].author+"</h1>";
               	 		GB.innerHTML += data.feed.entries[i].content;
				i++;
				duzina--;
				vrijeme_provjere=vrijeme_vijesti;
			}       
		}
	}
	});
}

