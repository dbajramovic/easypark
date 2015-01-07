<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="controller.SessionBox" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Easy Park</title>
<meta charset="UTF-8">
<!-- STYLES -->
	<link rel="stylesheet" type="text/css" href="css/Style.css" />
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
	<link rel="icon" type="image/png" href="http://www.queenshotelbrighton.com/portals/0/images/Parking%20Logo.jpg" />

<!-- INTERNET APIS -->
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyB0Nok-cKRP-OtaS8tPeuLEAgcN-FLKlIY"></script>
	<script src="http://code.highcharts.com/highcharts.js"></script>
	<script src="http://code.highcharts.com/modules/exporting.js"></script>
	<script src="http://cdn.alloyui.com/3.0.0/aui/aui-min.js"></script>
	<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="scripts/jquery.autocomplete.min.js"></script>
	<link href="http://cdn.alloyui.com/3.0.0/aui-css/css/bootstrap.min.css" rel="stylesheet"></link>

<!-- HTML templates -->
	<link rel="import" href="partialBlocks/RegisterBlock.html">
	<link rel="import" href="partialBlocks/LoginBlock.html">
	<link rel="import" href="partialBlocks/ParkingInfoBlock.html">
	<link rel="import" href="partialBlocks/UserInfoBlock.html">
	
<!-- SCRIPTS -->
	<script src="scripts/bootstrap.min.js"></script>
	<script src="scripts/LoadTemplates.js"></script>
	<script src="scripts/DrawingPoints.js"></script>
	<script src="scripts/Registration.js"></script>
	<script src="scripts/Login.js"></script>
	<script src="scripts/LoadTabs.js"></script>
	<!-- <script src="scripts/GetData.js"></script> -->


</head>

<body>

 <% HttpSession ses = request.getSession(true); 
 if ((session.getAttribute("id")==null) && (session.getAttribute("type")!="4")){%>
 
<h2>Niste registrovani!</h2>
<a href="index.html" >Vratite se na početnu stranicu</a>
 
 <% }
 else {%>
  	<!-- places for templates -->
  	<div id="registrationTemplate"></div>
  	<div id="loginTemplate"></div>
  	<div id="parkingModal" class="modal"></div>
  	<div id="userModal"></div>
  	<div id="userdiv" value="<%=session.getAttribute("id")%>"></div>
	<!-- end adding templates -->

<!-- begin main template -->
<div class="navbar navbar-custom navbar-fixed-top">
 <div class="navbar-header"><a class="navbar-brand" href="#">Easy Park</a>
      <a class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </a>
    </div>
    <div class="navbar-collapse collapse">
       
       <ul class="nav navbar-nav nav-tabs" role="tablist">
    	<li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab" >Mapa</a></li>
    	<li role="presentation"><a href="#parkinzi" aria-controls="profile" role="tab" data-toggle="tab" onclick="LoadParkings()">Parkinzi</a></li>
    	<li role="presentation"><a href="#korisniciParkinga" aria-controls="profile" role="tab" data-toggle="tab" onclick="LoadKorisniciParkinga()">Korisnici parkinga</a></li>
    	<li role="presentation"><a href="#premiumVlasnici" aria-controls="messages" role="tab" data-toggle="tab" onclick="LoadPremiumKorisnici()">Premium/vlasnici</a></li>
    	<li role="presentation"><a href="#tokeni" aria-controls="settings" role="tab" data-toggle="tab" onclick="LoadTokeni()">Tokeni</a></li>
  		<li role="presentation"><a href="#zahtjeviBrisanje" aria-controls="settings" role="tab" data-toggle="tab" onclick="LoadZahtjevi()">Zahtjevi za brisanje</a></li>
  		<li role="presentation"><a href="#prijave" aria-controls="settings" role="tab" data-toggle="tab" onclick="LoadPrijave()">Prijave</a></li>
  		<li><a href="index.html" onclick="deleteSession()">Ispis <span class="glyphicon glyphicon-log-out"></span></a></li>
  		</ul>
    </div>
</div>

<div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="home">
	<div id="googleMap" class="mapa-tab"></div>
	<div class="form-group" style="display:inline;">
          <div class="input-group">
						<div class="input-group-btn">
							<button type="button" class="btn btn-default maxSize"><span class="glyphicon glyphicon-search"></span></button>
						</div>
            <input id="autocomplete" type="text" class="form-control" placeholder="Koji grad?">
          </div>
        </div>
	</div>
	
    <div role="tabpanel" class="tab-pane fade" id="parkinzi">
    <br>
    <div class="container container-fluid">
	    <div id="parkinziPagination" class="pull-right"></div>
	    <div class="pull-left">
	    	Od datuma: <input id="parkinziDateFrom" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
	    	do datuma: <input id="parkinziDateTo" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
	    </div>		
	    <div class="table-responsive">
		    <table class="table table-bordered table-hover">
			  	 <thead>
			        <tr class="info">
			            <th>Parking ID</th>
			            <th>Vlasnik</th>
			            <th>Kapacitet</th>
			            <th>Cijena</th>
			            <th>Zabilješka</th>
			            <th>Telefon</th>
			            <th>Verificiran</th>
			            <th class="col-md-2">Opcije</th>
			        </tr>
	    		</thead>
	    		<tbody id="parkinziTable">
			  	</tbody>
			</table>
    		</div>
    </div>
    </div>
    
    <div role="tabpanel" class="tab-pane fade" id="korisniciParkinga">
	<br>
    <div class="container container-fluid">
    <div id="korisniciPagination" class="pull-right"></div>
    <div class="pull-left">
    	Od datuma: <input id="korisniciDateFrom" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    	do datuma: <input id="korisniciDateTo" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    </div>	
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>ID</th>
		            <th>Ime</th>
		            <th>Prezime</th>
		            <th>E-mail</th>
		            <th>Telefon</th>
		            <th>Žiro račun</th>
		            <th>Adresa</th>
		            <th class="col-md-2">Opcije</th>
		        </tr>
    		</thead>
    		<tbody id="korisniciTable">
		  	</tbody>
		</table>
    	</div>
    </div>
	</div>
    <div role="tabpanel" class="tab-pane fade" id="premiumVlasnici">
   	<br>
    <div class="container container-fluid">
    <div id="premiumPagination" class="pull-right"></div>
    <div class="pull-left">
    	Od datuma: <input id="premiumDateFrom" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    	do datuma: <input id="premiumDateTo" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    </div>	
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>ID</th>
		            <th>Ime</th>
		            <th>Prezime</th>
		            <th>E-mail</th>
		            <th>Telefon</th>
		            <th>Žiro račun</th>
		            <th>Adresa</th>
		            <th>Naziv kompanije</th>
		            <th class="col-md-2">Opcije</th>
		        </tr>
    		</thead>
    		<tbody id="premiumTable">
		  	</tbody>
		</table>
    	</div>
    </div>
	</div>
    <div role="tabpanel" class="tab-pane fade" id="tokeni">
	   <br>
    <div class="container container-fluid">
    <div id="tokeniPagination" class="pull-right"></div>
    <div class="pull-left">
    	Od datuma: <input id="tokeniDateFrom" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    	do datuma: <input id="tokeniDateTo" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    </div>	
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>Token ID</th>
		            <th>Token</th>
		            <th>Datum upita</th>
		            <th>E-mail</th>
		            <th>Poslan</th>
		             <th>Vrsta korisnika</th>
		            <th>Opcije</th>
		        </tr>
    		</thead>
    		<tbody id="tokensTable">
		  	</tbody>
		</table>
    	</div>
    </div>

	</div>
    <div role="tabpanel" class="tab-pane fade" id="zahtjeviBrisanje">
    <br>
    <div class="container container-fluid">
    <div id="zahtjeviPagination" class="pull-right"></div>
    <div class="pull-left">
    	Od datuma: <input id="zahtjeviDateFrom" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    	do datuma: <input id="zahtjeviDateTo" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    </div>	
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>ID</th>
		            <th>ID osobe</th>
		            <th>ID parkinga</th>
		            <th>Datum zahtjeva</th>
		            <th>Poruka</th>
		            <th>Riješeno?</th>
		            <th>Opcije</th>
		        </tr>
    		</thead>
    		<tbody id="zahtjeviTable">
		  	</tbody>
		</table>
    	</div>
    </div>
    
    </div>
    <div role="tabpanel" class="tab-pane fade" id="prijave">
	<br>
	<div class="container container-fluid">
		<div id="prijavePagination" class="pull-right"></div>
		<div class="pull-left">
    	Od datuma: <input id="prijaveDateFrom" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    	do datuma: <input id="prijaveDateTo" class="form-control" type="text" placeholder="Dan, Mjesec, Godina">
    	</div>	
    	<div class="table-responsive"> 
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>ID</th>
		            <th>Vlasnik parkinga</th>
		            <th>Korisnik parkinga</th>
		            <th>Parking ID</th>
		            <th>Rezervisanih mjesta</th>
		            <th>Riješeno</th>
		            <th>Opcije</th>
		        </tr>
    		</thead>
    		<tbody id="prijaveTable">
		  	</tbody>
		</table>
    	</div>
    </div>
	</div>
  </div>

<%}%>
<!-- end main template -->

</body>
</html>