<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="controller.SessionBox"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Easy Park</title>
<meta charset="UTF-8">
<!-- STYLES -->
<link rel="stylesheet" type="text/css" href="css/Style.css" />
<link href="css/bootstrap.min.css" rel="stylesheet">
<!--[if lt IE 9]><script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->
<link rel="icon" type="image/png"
	href="http://www.queenshotelbrighton.com/portals/0/images/Parking%20Logo.jpg" />

<!-- INTERNET APIS -->
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script
	src="http://maps.googleapis.com/maps/api/js?key=AIzaSyB0Nok-cKRP-OtaS8tPeuLEAgcN-FLKlIY"></script>
<script src="http://code.highcharts.com/highcharts.js"></script>
<script src="http://code.highcharts.com/modules/exporting.js"></script>
<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="scripts/jquery.autocomplete.min.js"></script>

<!-- HTML templates -->
<link rel="import" href="partialBlocks/RegisterBlock.html">
<link rel="import" href="partialBlocks/LoginBlock.html">
<link rel="import" href="partialBlocks/ParkingInfoBlock.html">
<link rel="import" href="partialBlocks/UserInfoBlock.html">
<link rel="import" href="partialBlocks/RegisterParkingBlock.html">
<!-- SCRIPTS -->
<script src="scripts/bootstrap.min.js"></script>
<script src="scripts/LoadTemplates.js"></script>
<script src="scripts/DrawingPoints.js"></script> 
<script src="scripts/Registration.js"></script>
<script src="scripts/Login.js"></script>
<script src="scripts/SearchData.js"></script>
<!-- <script src="scripts/GetData.js"></script> -->


</head>

<body>

	<%
		HttpSession ses = request.getSession(true);
		if ((session.getAttribute("id") == null)
				&& (session.getAttribute("type") != "3")) {
	%>

	<h2>Niste registrovani!</h2>
	<a href="index.html">Vratite se na početnu stranicu</a>

	<%
		} else {
	%>
	<!-- places for templates -->
	<div id="registrationTemplate"></div>
	<div id="parkingregistrationModal"></div>
	<div id="loginTemplate"></div>
	<div id="parkingModal" class="modal"></div>
	<div id="markerlong" value=""></div>
	<div id="markerlat" value=""></div>
	<div id="userModalpremium">
		<div class="row left-panel">
			<!-- 		<ul class="nav navbar-nav nav-tabs" role="tablist">
    	<li role="presentation" class="active"><a href="#podaciPremium" aria-controls="home" role="tab" data-toggle="tab" >Opće</a></li>
    	<li role="presentation"><a href="#rezervacijePremium" aria-controls="profile" role="tab" data-toggle="tab" >Rezervacije</a></li>
  		</ul>  -->
			<div class="col-xs-8" id="left">
				<h2 align="center"><%=session.getAttribute("firstName")%>
					<%=session.getAttribute("lastName")%></h2>
				<hr>
				<div class="panel panel-default">
					<div class="panel-heading text-center">
						<div>
							E-mail:
							<%=session.getAttribute("username")%></div>
					</div>
					<div class="panel-heading text-center">
						<div>
							Broj telefona:
							<%=session.getAttribute("phoneNumber")%></div>
					</div>
					<div class="panel-heading text-center">
						<div>
							Adresa:
							<%=session.getAttribute("address")%></div>
					</div>
					<div class="panel-heading text-center">
						<div>
							Naziv kompanije:
							<%=session.getAttribute("companyName")%></div>
					</div>
					<br> <br>
					<div class="panel-heading text-center">
						<div>Podaci o parking-u:</div>
					</div>
				</div>
				<br>
				<div class="pull-right">
					<button class="btn btn-danger">
						<span class="glyphicon glyphicon-trash"></span>
					</button>
				</div>
				<p id="listofUserParkings" align="center"></p>
			</div>
		</div>
	</div>
	<div id="userModalregular"></div>
	<div id="userdiv" value="<%=session.getAttribute("id")%>"></div>
	<!-- end adding templates -->

	<!-- begin main template -->
	<div class="navbar navbar-custom navbar-fixed-top">
		<div class="navbar-header">
			<a class="navbar-brand" href="#">Easy Park</a> <a
				class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse"> <span class="icon-bar"></span> <span
				class="icon-bar"></span> <span class="icon-bar"></span>
			</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="" data-toggle="modal"
					data-target="#parkingregistrationModal2" id="newparkingbtn"> <span
						class="glyphicon glyphicon-plus"></span> parking
				</a></li>
				<li><a href="index.html" onclick="deleteSession()">Ispis</a></li>
				<li><a href="#" onclick="setListOfReservations()"
					id="parkResList">Lista Rezervacija</a></li>
			</ul>
			<form class="navbar-form">
				<div class="form-group" style="display: inline;">
					<div class="input-group">
						<div class="input-group-btn">
							<button type="button" class="btn btn-default maxSize">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</div>
						<input id="autocomplete" type="text" class="form-control"
							placeholder="Koji grad?">

					</div>
				</div>
			</form>
		</div>
	</div>

	<div id="googleMap" class="mapa"></div>
	<!-- <div class="container-fluid" id="main">
		<div class="row left-panel">
		<div role="tabpanel" class="tab-pane fade" id="podaciPremium">
					
		    </div>

			<div role="tabpanel" class="tab-pane fade" id="rezervacijePremium">
				    <br>
				    <div>
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
						      
						        </tr>
				    		</thead>
				    		<tbody id="zahtjeviTable">
						  	</tbody>
						</table>
				    	</div>
				  </div>
		</div>
	</div> -->
	<%
		}
	%>
	<!-- end main template -->
	<script src="scripts/DrawPremiumUserModal.js"></script>
</body>
</html>