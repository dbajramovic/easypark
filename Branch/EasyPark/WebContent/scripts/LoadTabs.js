function LoadPagination(a){
$(a).empty();
	YUI().use(
		 'aui-pagination',
		  function(Y) {
		    new Y.Pagination(
		      {
		    	boundingBox: a,
		    	circular: false,
		        containers: a,
		        total: 5,
			    page: 1
		      }
		    ).render();
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

function CreateButton(klase, unutrasnjiDio, td){
    var type='button';
    var element = document.createElement("button");
    element.type = type;
    element.name = type;
    element.className= klase;
    element.innerHTML=unutrasnjiDio;
    element.onclick = function() {alert("nista");};
    td.appendChild(element);
};

function SendDataToTable(data, naziv,table){
	$(table).empty();
	data.forEach(function(entry) {
	    var tr = document.createElement('tr');
	    for (var prop in entry) {
	        if(entry.hasOwnProperty(prop)
	        		&&entry[prop]!=null 
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
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-plus-sign"></span>', td);
		    CreateButton('btn btn-default', '<span class="glyphicon glyphicon-edit"></span>', td);
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-minus-sign"></span>', td);
	    }
	    if (naziv==='korisniciParkinga'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-ok-circle"></span>', td);
		    CreateButton('btn btn-default', '<span class="glyphicon glyphicon-edit"></span>', td);
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-ban-circle"></span>', td);
	    }
	    if (naziv==='vlasniciParkinga'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-ok"></span>', td);
		    CreateButton('btn btn-default', '<span class="glyphicon glyphicon-edit"></span>', td);
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-remove"></span>', td);
	    }
	    if (naziv==='tokeni'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-envelope"></span>', td);
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-remove"></span>', td);
	    }
	    if (naziv==='zahtjevi'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-ok"></span>', td);
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-remove"></span>', td);
	    }
	    if (naziv==='prijave'){
		    CreateButton('btn btn-success', '<span class="glyphicon glyphicon-envelope"></span>', td);
		    CreateButton('btn btn-danger', '<span class="glyphicon glyphicon-remove"></span>', td);
	    }
	    tr.appendChild(td);
        $(table).append(tr);
	});
};

function LoadData(url,naziv,table){
	var JSONObject= {
			"type":"admin",
			"numOfPage":0,//zasad
			"numOfResults":0 //zasad
	};
	var jsonData = JSON.stringify(JSONObject); 
	var request = $.ajax({
			url: "http://localhost:80/EasyPark/api/service/"+url,
			type: "POST",
			contentType: 'application/json',
			data: jsonData,
			dataType: "JSON",
			success: function(data) { 
				SendDataToTable(data, naziv, table);
				}  
		});
	request.fail(function( jqXHR, textStatus ) {alert('Problem sa konekcijom na bazu');});	
};

function LoadPrijave(){
	LoadData("getTopPrijave",'prijave','#prijaveTable');
	LoadPagination('#prijavePagination');
	LoadDatePicker('#prijaveDateFrom');
	LoadDatePicker('#prijaveDateTo');
}
function LoadZahtjevi(){
	LoadData("getTopRequests",'zahtjevi','#zahtjeviTable');
	LoadPagination('#zahtjeviPagination');
	LoadDatePicker('#zahtjeviDateFrom');
	LoadDatePicker('#zahtjeviDateTo');
}
function LoadTokeni(){
	LoadData("getTopTokens",'tokeni','#tokensTable');
	LoadPagination('#tokeniPagination');
	LoadDatePicker('#tokeniDateFrom');
	LoadDatePicker('#tokeniDateTo');
}
function LoadPremiumKorisnici(){
	LoadData("getTopPremium",'vlasniciParkinga','#premiumTable');
	LoadPagination('#premiumPagination');
	LoadDatePicker('#premiumDateFrom');
	LoadDatePicker('#premiumDateTo');
}
function LoadKorisniciParkinga(){
	LoadData("getTopKorisnici",'korisniciParkinga','#korisniciTable');
	LoadPagination('#korisniciPagination');
	LoadDatePicker('#korisniciDateFrom');
	LoadDatePicker('#korisniciDateTo');
}
function LoadParkings(){
	LoadData("getTopParkings",'parkinzi','#parkinziTable');
	LoadPagination('#parkinziPagination');
	LoadDatePicker('#parkinziDateFrom');
	LoadDatePicker('#parkinziDateTo');
}