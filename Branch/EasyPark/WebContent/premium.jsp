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
 if (session.getAttribute("id")==null){%>
 
<h2>Niste registrovani!</h2>
<a href="index.html" >Vratite se na početnu stranicu</a>
 
 <% }
 else {%>
  	<!-- places for templates -->
  	<div id="registrationTemplate"></div>
  	<div id="loginTemplate"></div>
  	<div id="parkingModal" class="modal"></div>
  	<div id="userModalpremium"></div>
  	<div id="userModalregular"></div>
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
      <ul class="nav navbar-nav">
        <li><a href="" data-toggle="modal" data-target=".registrationModal"><span class="glyphicon glyphicon-plus"></span>parking</a></li>
        <li><a href="index.html" onclick="deleteSession()">Ispis</a></li>
      </ul>
      <form class="navbar-form">
        <div class="form-group" style="display:inline;">
          <div class="input-group">
 						<div class="input-group-btn">
							<button type="button"
								class="btn btn-default dropdown-toggle maxSize"
								data-toggle="dropdown">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</button>
						
							<ul class="dropdown-menu">
								<li>
									<a><input type="radio" class="radio" id="optionsRadios1" value="" checked> Grad</a>	
								</li>
								<li>								
									 <a><input type="radio" class="radio" id="optionsRadios2" value="option1">Minimalna Cijena</a>
								</li>
								<li>
									  <a><input type="radio" class="radio" id="optionsRadios3" value="option2" >Maximalna Cijena</a>									    
								</li>
								<li>
									<a><input type="radio"  class="radio" id="optionsRadios4" value="option3" >Plaćanje?</a>   
								</li> 
							</ul>
							<button type="button" class="btn btn-default maxSize"><span class="glyphicon glyphicon-search"></span></button>
						</div>
            <input type="text" class="form-control" placeholder="Koji grad?">
            <span class="input-group-addon"><span class="glyphicon glyphicon-search"></span> </span>
          </div>
        </div>
      </form>
    </div>
</div>

<div id="googleMap" class="mapa"></div>
<div class="container-fluid" id="main">
  <div class="row left-panel">
  </div>
</div>
<%}%>
<!-- end main template -->
	<script src="scripts/DrawPremiumUserModal.js"></script>
</body>
</html>