function LoadPagination(a, pagesPerPage, results){
	var temp=Math.round(results/pagesPerPage);
	if (temp>5) {temp=5;}
$(a).empty();
	YUI().use(
		 'aui-pagination',
		  function(Y) {
		    new Y.Pagination(
		      {
		    	boundingBox: a,
		    	circular: false,
		        containers: a,
		        total: temp,
			    page: 1
		      }
		    ).render();
		   // Y.maxPageLinks=5;
		  }
);}
function LoadDatePicker(a){
	$(a).empty();
	YUI({ lang: 'en' }).use(
			  'aui-datepicker',
			  function(Y) {
			    var datepicker = new Y.DatePicker(
			      {
			        trigger: a,
			        mask: '%a, %b %d, %Y',
			        popover: {
			          toolbars: {
			            header: [[
			              {
			                icon:'icon-trash',
			                label: 'Obriši',
			                on: {
			                  click: function() {
			                    datepicker.clearSelection();
			                  }
			                }
			              },
			              {
			                icon:'icon-globe',
			                label: 'Danas',
			                on: {
			                  click: function() {
			                    datepicker.clearSelection();
			                    datepicker.selectDates(new Date());
			                  }
			                }
			              }
			            ]]
			          },
			          zIndex: 1
			        }
			      }
			    );
			  }
			);
}

function CreateButton(klase, unutrasnjiDio, td, funkcija){
    var type='button';
    var element = document.createElement("button");
    element.type = type;
    element.name = type;
    element.className= klase;
    element.innerHTML=unutrasnjiDio;
    element.onclick = funkcija;
    td.appendChild(element);
};

function SendDataToTable(data, naziv,table){
	$(table).empty();
	data.forEach(function(entry) {
	    var tr = document.createElement('tr');
	    for (var prop in entry) {
	        if(entry.hasOwnProperty(prop)
	        		&&entry[prop]!=null && prop!=='_results'
	        		&& entry[prop]!==0 
	        		|| naziv==='tokeni' 
	        		|| naziv==='zahtjevi' 
	        		|| naziv==='prijave'){
	        	var td = document.createElement('td');
		        td.innerHTML = entry[prop];
		        tr.appendChild(td);
	        }
	     }
	   
	    var td = document.createElement('td');
	    if (naziv==='parkinzi'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-plus-sign"></span>', td, function(){alert('nije implementirano');});
		    CreateButton('btn btn-default', '<span class="glyphicon glyphicon-edit"></span>', td, function(){alert('nije implementirano');});
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-minus-sign"></span>', td, function(){alert('nije implementirano');});
	    }
	    if (naziv==='korisniciParkinga'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-ok-circle"></span>', td, function(){alert('nije implementirano');});
		    CreateButton('btn btn-default', '<span class="glyphicon glyphicon-edit"></span>', td, function(){alert('nije implementirano');});
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-ban-circle"></span>', td, function(){alert('nije implementirano');});
	    }
	    if (naziv==='vlasniciParkinga'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-ok"></span>', td , function(){alert('nije implementirano');});
		    CreateButton('btn btn-default', '<span class="glyphicon glyphicon-edit"></span>', td ,function(){alert('nije implementirano');});
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-remove"></span>', td , function(){alert('nije implementirano');});
	    }
	    if (naziv==='tokeni'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-envelope"></span>', td , function(){SendToken(entry);});
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-remove"></span>', td , function(){alert('nije implementirano');});
	    }
	    if (naziv==='zahtjevi'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-ok"></span>', td ,function(){alert('nije implementirano');});
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-remove"></span>', td, function(){alert('nije implementirano');});
	    }
	    if (naziv==='prijave'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-envelope"></span>', td ,function(){SendComplaint(entry);});
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-remove"></span>', td ,function(){alert('nije implementirano');});
	    }
	    tr.appendChild(td);
        $(table).append(tr);
	});
	if  (typeof data[0]['_results']!=='undefined') {return data[0]['_results'];}
	else return 0;
};

function LoadData(url,naziv,table){
	var JSONObject= {
			"type":"admin",
			"numOfPage":0,//zasad
			"numOfResults":0 //zasad
	};
	var jsonData = JSON.stringify(JSONObject); 
	var results=0;
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/service/"+url,
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			async: false,
			success: function(data) { 
				results = SendDataToTable(data, naziv, table);
				return results;
				}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});	
};

function SendToken(a){
	var JSONObject= {
			"type":"admin",
			"id":a['_tokenID'],
			"email":a['_email']
	};
	var jsonData = JSON.stringify(JSONObject); 
	var results=0;
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/service/sendToken",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			async: false,
			success: function(data) { 
				alert('Mail sa tokenom je poslan');
				window.open ('admin.jsp','_self',false);
				}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});	
};
function SendComplaint(a){
	var JSONObject= {
			"type":"admin",
			"id":a['_tokenID']
	};
	var jsonData = JSON.stringify(JSONObject); 
	var results=0;
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/service/sendComplaint",
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			async: false,
			success: function(data) { 
				alert('Mail sa opomenom je poslan');
				window.open ('admin.html','_self',false);
				}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});	
};


function LoadPrijave(){
	brojRez=LoadData("getTopPrijave",'prijave','#prijaveTable');
	LoadPagination('#prijavePagination',10,10);
	LoadDatePicker('#prijaveDateFrom');
	LoadDatePicker('#prijaveDateTo');
}
function LoadZahtjevi(){
	brojRez=LoadData("getTopRequests",'zahtjevi','#zahtjeviTable');
	LoadPagination('#zahtjeviPagination',10,10);
	LoadDatePicker('#zahtjeviDateFrom');
	LoadDatePicker('#zahtjeviDateTo');
}
function LoadTokeni(){
	brojRez=LoadData("getTopTokens",'tokeni','#tokensTable');
	LoadPagination('#tokeniPagination',10,20);
	LoadDatePicker('#tokeniDateFrom');
	LoadDatePicker('#tokeniDateTo');
}
function LoadPremiumKorisnici(){
	brojRez=LoadData("getTopPremium",'vlasniciParkinga','#premiumTable');
	LoadPagination('#premiumPagination',10,10);
	LoadDatePicker('#premiumDateFrom');
	LoadDatePicker('#premiumDateTo');
}
function LoadKorisniciParkinga(){
	brojRez=LoadData("getTopKorisnici",'korisniciParkinga','#korisniciTable');
	LoadPagination('#korisniciPagination',10,10);
	LoadDatePicker('#korisniciDateFrom');
	LoadDatePicker('#korisniciDateTo');
}
function LoadParkings(){
	var brojRez=LoadData("getTopParkings",'parkinzi','#parkinziTable');
	LoadPagination('#parkinziPagination', 10, 1001);
	LoadDatePicker('#parkinziDateFrom');
	LoadDatePicker('#parkinziDateTo');
}