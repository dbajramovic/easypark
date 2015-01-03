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
    	<li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">Mapa</a></li>
    	<li role="presentation"><a href="#parkinzi" aria-controls="profile" role="tab" data-toggle="tab">Parkinzi</a></li>
    	<li role="presentation"><a href="#korisniciParkinga" aria-controls="profile" role="tab" data-toggle="tab">Korisnici parkinga</a></li>
    	<li role="presentation"><a href="#premiumVlasnici" aria-controls="messages" role="tab" data-toggle="tab">Premium/vlasnici</a></li>
    	<li role="presentation"><a href="#tokeni" aria-controls="settings" role="tab" data-toggle="tab">Tokeni</a></li>
  		<li role="presentation"><a href="#zahtjeviBrisanje" aria-controls="settings" role="tab" data-toggle="tab">Zahtjevi za brisanje</a></li>
  		<li role="presentation"><a href="#prijave" aria-controls="settings" role="tab" data-toggle="tab">Prijave</a></li>
  		<li><a href="index.html" onclick="deleteSession()">Ispis <span class="glyphicon glyphicon-log-out"></span></a></li>
  		</ul>
    </div>
</div>


<!-- <div class="container-fluid" id="main"> 
	
	<div class="row left-panel"> 
	ovdje ovdje
	
	</div> 

</div> -->

<div class="tab-content">
    <div role="tabpanel" class="tab-pane active" id="home">
	<div id="googleMap" class="mapa-tab"></div>
	<div class="form-group" style="display:inline;">
          <div class="input-group">
						<div class="input-group-btn">
							<button type="button" class="btn btn-default maxSize"><span class="glyphicon glyphicon-search"></span></button>
						</div>
            <input type="text" class="form-control" placeholder="Koji grad?">
          </div>
        </div>
	</div>
    <div role="tabpanel" class="tab-pane fade" id="parkinzi">
    <br>
    <div class="container container-fluid">
    
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>Item ID</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th class="col-md-2">Opcije</th>
		        </tr>
    		</thead>
    		<tbody>
		  	<tr>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td >
			  		<button class="btn btn-success"><span class="glyphicon glyphicon-ok"></span></button>
			  		<button class="btn btn-default"><span class="glyphicon glyphicon-edit"></span></button> 
			  		<button class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span></button>
		  		</td>
		  		
		  	</tr>
		  	</tbody>
		</table>
    	</div>
    </div>
    
    </div>
    <div role="tabpanel" class="tab-pane fade" id="korisniciParkinga">
	<br>
    <div class="container container-fluid">
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>Item ID</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th class="col-md-2">Opcije</th>
		        </tr>
    		</thead>
    		<tbody>
		  	<tr>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td >
			  		<button class="btn btn-success"><span class="glyphicon glyphicon-ok"></span></button>
			  		<button class="btn btn-default"><span class="glyphicon glyphicon-edit"></span></button> 
			  		<button class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span></button>
		  		</td>
		  	</tr>
		  	</tbody>
		</table>
    	</div>
    </div>

	</div>
    <div role="tabpanel" class="tab-pane fade" id="premiumVlasnici">
   	<br>
    <div class="container container-fluid">
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>Item ID</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th class="col-md-2">Opcije</th>
		        </tr>
    		</thead>
    		<tbody>
		  	<tr>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td >
			  		<button class="btn btn-success"><span class="glyphicon glyphicon-ok"></span></button>
			  		<button class="btn btn-default"><span class="glyphicon glyphicon-edit"></span></button> 
			  		<button class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span></button>
		  		</td>
		  	</tr>
		  	</tbody>
		</table>
    	</div>
    </div>
	</div>
    <div role="tabpanel" class="tab-pane fade" id="tokeni">
	   <br>
    <div class="container container-fluid">
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>Item ID</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th class="col-md-2">Opcije</th>
		        </tr>
    		</thead>
    		<tbody>
		  	<tr>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td >
			  		<button class="btn btn-success"><span class="glyphicon glyphicon-ok"></span></button>
			  		<button class="btn btn-default"><span class="glyphicon glyphicon-edit"></span></button> 
			  		<button class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span></button>
		  		</td>
		  	</tr>
		  	</tbody>
		</table>
    	</div>
    </div>

	</div>
    <div role="tabpanel" class="tab-pane fade" id="zahtjeviBrisanje">
    <br>
    <div class="container container-fluid">
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>Item ID</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th class="col-md-2">Opcije</th>
		        </tr>
    		</thead>
    		<tbody>
		  	<tr>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td >
			  		<button class="btn btn-success"><span class="glyphicon glyphicon-ok"></span></button>
			  		<button class="btn btn-default"><span class="glyphicon glyphicon-edit"></span></button> 
			  		<button class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span></button>
		  		</td>
		  	</tr>
		  	</tbody>
		</table>
    	</div>
    </div>
    
    </div>
    <div role="tabpanel" class="tab-pane fade" id="prijave">
	   <br>
    <div class="container container-fluid">
    	<div class="table-responsive">
	    <table class="table table-bordered table-hover">
		  	 <thead>
		        <tr class="info">
		            <th>Item ID</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th>Item Name</th>
		            <th>Item Price</th>
		            <th class="col-md-2">Opcije</th>
		        </tr>
    		</thead>
    		<tbody>
		  	<tr>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td>...</td>
		  		<td >
			  		<button class="btn btn-success"><span class="glyphicon glyphicon-ok"></span></button>
			  		<button class="btn btn-default"><span class="glyphicon glyphicon-edit"></span></button> 
			  		<button class="btn btn-danger"><span class="glyphicon glyphicon-remove"></span></button>
		  		</td>
		  	</tr>
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