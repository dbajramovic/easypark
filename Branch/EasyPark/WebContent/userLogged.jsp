<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="Models.Admin,ServicePackage.DatabaseRead" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
<head> 
<link rel="stylesheet" type="text/css" href="css/LoginStyle.css" />
<title>Easy Park</title>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head> 
<body> 
<div id='cssmenu'>
<ul>
   <li><a href='index.html' onclick="alert('Uspjesno ste se odjavili! Pozdrav!');"><span>Track!</span></a></li>
   <li ><a href='Register.html' onclick="alert('Uspjesno ste se odjavili! Pozdrav!');"><span>Register</span></a></li>
   <li class='active'><a href='Login.html' onclick="alert('Uspjesno ste se odjavili! Pozdrav!');"><span>Log-off</span></a></li>

</ul>
</div>
 <% 
 //Admin currentUser = (Admin)session.getAttribute("currentSessionUser");
 try
 {
	 String un = request.getParameter("un");
	 String pass=request.getParameter("pw");
	 DatabaseRead db=new DatabaseRead(un,pass);
	 String odg=db.DajRezultat();
	
	 if (!(odg.equals("No"))) 
	 { 	
		
		 HttpSession ses = request.getSession(true); 
		ses.setAttribute("currentIdUser",odg); 
		ses.setAttribute("currentUsernameUser",un); 
%>
<input type="hidden" id="idUser" name="idUser" value=<%=odg%>>
<center style="color:white;"> Dobrodošao <%=un %>!</center>
<script src="http://maps.googleapis.com/maps/api/js?key=AIzaSyDY0kkJiTPVd2U7aTOAwhc9ySH6oHxOIYM&sensor=false"></script>
<script src="scripts/Login.js"></script>	
<script type="text/javascript">setDefaultDataOfUser('<%=un%>');</script>

<div class="okvir">
	<form>
		<input type="text" list="json-datalist" id="unosTeksta" placeholder="Unesite naziv vaseg uređaja, npr: Sutjeska-Vogošća" class="unosTeksta" name="unosTeksta" onkeyup="showHint(this.value)"></input>
		<datalist id="json-datalist"></datalist>
		<button type="button" name="pocetak" class="pocetak" onclick="SendToCheck()">Start</button>
		<div class="IzbornikTekst">Pretraga po vremenu:
		<input class="izbor" type="radio" name="izbor" value="Trenutno" checked>Trenutno
		<input class="izbor" type="radio" name="izbor" value="1h">Posljednjih 1h
		<input class="izbor" type="radio" name="izbor" value="6h">Posljednjih 6h
		<input class="izbor" type="radio" name="izbor" value="1d">Posljednjih 1 dan
		<input class="izbor" type="radio" name="izbor" value="2d">Posljednjih 2 dana
		<input class="izbor" type="radio" name="izbor" value="30d">Posljednjih 30 dana
		</div>
	</form>	
	
	<div id="googleMap" class="mapa"></div>
	<div class="tekstPodaciKorisnik">Podaci o korisniku	<hr>
	<div class="tekstIme">Ime</div>	
		<div type="text" class="FirstName"></div>
	<div class="tekstPrezime">Prezime</div>	
		<div type="text" class="LastName" ></div>
	<div class="tekstKompanija">Ime kompanije</div>	
		<div type="text" class="CompanyName"></div>
	<div class="tekstBrojU">Broj uređaja</div>	
		<div id="NumberOfDevices" class="NumberOfDevices"></div><input type="button" onClick="show_popup()" class="DodajNovi" value="Dodaj novi!"></input>
	</div>
	<div class="tekstPodaciTracking">Tracking<hr>
	<div class="tekstRedniB">Redni broj</div><div class="RedniBroj"></div>
	<div class="tekstVrijeme">Vrijeme</div><div class="Vrijeme"></div>
	<div class="tekstDatum">Datum</div><div class="Datum"></div>
	<div class="tekstKomentar">Komentar</div><div class="Komentar"></div>
	</div>
</div>

<%	} 
	 else{
	 %> <script type="text/javascript">alert('Unijeli ste pogrešan unos! Takav korisnik ne postoji u bazi.'); window.location = '/Tracker/Login.html';</script><%	
	 	}; 
 }
 catch(Exception e){out.println("Žao nam je, ne možete pristupiti ovoj stranici na takav način! :)");}
 %>
 </body> 
 </html> 