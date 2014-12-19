

window.setInterval(ProvjeriFeed,1000);

var vrijeme_provjere = new Date();
var prvi_put = true;

function ProvjeriFeed() {
	var feed = new google.feeds.Feed('http://www.klix.ba/rss/vijesti/bih');
        feed.setNumEntries(4);
	feed.load(function (data) { 
        var duzina = data.feed.entries.length;  
  	// Parse data depending on the specified response format, default is JSON. 
  	var GB = document.getElementById("GiantBomb");
	if(prvi_put)
               {
		for (var i=0;i<duzina;i++) {
			GB.innerHTML += "<h1>"+data.feed.entries[i].title+" by "+data.feed.entries[i].author+"</h1>";
               	 	GB.innerHTML += data.feed.entries[i].content;
			}
		prvi_put=false;
		vrijeme_provjere = new Date(data.feed.entries[0].publishedDate);     
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
        console.dir(data);
	});
}

