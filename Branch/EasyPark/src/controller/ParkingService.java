package controller;

import java.util.UUID;
import java.util.List;
import java.util.Locale;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Parking;
import model.Person;
import model.Reservation;
import model.Slika;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

//import jdk.nashorn.internal.runtime.regexp.RegExp;
import oracle.jdbc.driver.OracleDriver;
import oracle.jdbc.*;
import repository.AccountFunctions;
import repository.ParkingsFunctions;
import repository.ObjectsFunctions;
import repository.ReservationFunctions;

@Path("/service")
public class ParkingService {

	private static final String api_version = "1.0.3";
	private static String conn = "";
	private String url;
	private String user;
	private String password;
	private Connection connection = null;

	public void CreateConnection() {
		System.out.println("-------- Creating ------");
		Locale.setDefault(Locale.US);
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
		}
		System.out.println("Connection created!");
		try {
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@80.65.65.66:1521:ETFLAB", "EasyPark",
					"EasyParkIsNum1");
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}

	public void CloseConnection() {

	}

	@Path("/parkingsinrange")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Parking> giveMeParkingsInRange(JSONObject id) {
		List<Parking> lp = null;
		ParkingsFunctions ga = new ParkingsFunctions();
		CreateConnection();

		try {
			lp = ga.giveMe(connection, (float) id.getDouble("longitude"),
					(float) id.getDouble("latitude"), id.getInt("range"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lp;
	}

	@Path("/parkingsofuser")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Parking> giveMeParkingsByUser(JSONObject id) {
		List<Parking> lp = null;
		ParkingsFunctions ga = new ParkingsFunctions();
		CreateConnection();

		try {
			lp = ga.giveMeUserParkings(connection, id.getInt("userid"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lp;
	}

	@Path("/updateuserparking")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void updateUserParking(JSONObject id) {
		ParkingsFunctions ga = new ParkingsFunctions();
		CreateConnection();

		try {
			ga.updateUserParkings(connection, id.getInt("spaces"),
					id.getInt("userid"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Path("/searchsuggestions")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void searchSuggestions(JSONObject i) {
		ParkingsFunctions ga = new ParkingsFunctions();
		CreateConnection();

		try {
			List<String> l = ga.getSuggestions(connection, i.getString("term"),
					i.getInt("choice"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Path("/pic")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Slika giveMePicture(JSONObject id) {
		Slika p = new Slika();
		ObjectsFunctions lp = new ObjectsFunctions();
		CreateConnection();
		try {
			p = lp.giveMe(connection, id.getInt("pictureid"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	@Path("/register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Long Register(JSONObject id) throws SQLException {
		Long p = (long) 0;
		Person te = new Person();
		AccountFunctions lp = new AccountFunctions();
		CreateConnection();
		try {
			te.set_firstname(id.get("firstname").toString());
			te.set_lastname(id.get("lastname").toString());
			te.set_password(id.get("password").toString());
			te.set_phonenumber(id.get("phonenumber").toString());
			te.set_address(id.get("address").toString());
			te.set_email(id.get("username").toString());
			te.set_companyName(id.get("companyname").toString());
			te.set_type(id.getInt("type"));
			te.set_accountNumber(id.get("accountnumber").toString());
			te.set_token(id.getString("token"));
			lp.registerAccount(connection, te);
		} catch (Exception e) {
		} finally {
			CloseConnection();
		}
		return p;
	}

	@Path("/saveToken")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void SaveToken(JSONObject obj) throws SQLException {
		AccountFunctions lp = new AccountFunctions();
		CreateConnection();
		try {
			String username = obj.getString("username");
			int type = obj.getInt("type");
			// MailService lp = new MailService();
			// System.out.print(UUID.randomUUID());
			// System.out.print(UUID.fromString(username));
			// lp.SendToken(username, "megasadasdasdasdadsas");
			// String token = UUID.randomUUID().toString();
			lp.SaveTokenRequest(connection, username, type, "");

		} catch (Exception e) {
		} finally {
			CloseConnection();
		}

	}

	@Path("/checkToken")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Boolean CheckToken(JSONObject obj) throws SQLException {
		AccountFunctions lp = new AccountFunctions();
		CreateConnection();
		try {
			String username = obj.getString("username");
			int type = obj.getInt("type");
			String token = obj.getString("token");
			String action = obj.getString("action");
			Boolean result = lp.CheckToken(connection, username, type, token,
					action);
			return result;
		} catch (Exception e) {
			return false;
		} finally {
			CloseConnection();
		}
	}

	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Person Login(JSONObject id) throws SQLException {
		Person te = new Person();
		AccountFunctions lp = new AccountFunctions();
		CreateConnection();
		try {
			String user = id.getString("username");
			String pass = id.getString("password");
			te = lp.loginAccount(connection, user, pass);

		} catch (Exception e) {
		} finally {
			CloseConnection();
		}
		return te;
	}

	@Path("/userdata")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Person UserData(JSONObject id) throws SQLException {
		Person te = new Person();
		AccountFunctions lp = new AccountFunctions();
		CreateConnection();
		try {
			te = lp.UserDetails(connection, id.getInt("userid"));

		} catch (Exception e) {
		} finally {
			CloseConnection();
		}
		return te;
	}

	@Path("/userreservations")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reservation> UserReservations(JSONObject id)
			throws SQLException {
		List<Reservation> lr = null;
		ReservationFunctions rp = new ReservationFunctions();
		CreateConnection();
		try {
			lr = rp.giveMe(connection, id.getInt("userid"), id.getString("date"));

		} catch (Exception e) {
		} finally {
			CloseConnection();
		}
		return lr;
	}

	/*
	 * @Path("/getdataofadmin")
	 * 
	 * @POST
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Admin
	 * GiveMeDataAdmin(JSONObject id) throws SQLException{ Admin te=new Admin();
	 * try{ String mystring=(String)id.get("username"); String
	 * myid=(String)id.get("id"); mystring=BosnianEncode(mystring);
	 * CreateConnection(); String
	 * newState="SELECT * FROM Admin WHERE Username = ? AND idAdmin = ?";
	 * PreparedStatement query=connection.prepareStatement(newState);
	 * query.setString(1, mystring); query.setString(2, myid); ResultSet
	 * rs=query.executeQuery(); while (rs.next()){
	 * te.setFirstName(BosnianDecode(new
	 * String(rs.getBytes("FirstName"),"UTF-32")));
	 * te.setLastName(BosnianDecode(new
	 * String(rs.getBytes("LastName"),"UTF-32")));
	 * te.setUsername(BosnianDecode(new
	 * String(rs.getBytes("Username"),"UTF-32")));
	 * te.setCompanyName(BosnianDecode(new
	 * String(rs.getBytes("CompanyName"),"UTF-32"))); } newState=new
	 * String("SELECT Count(*) FROM Device WHERE RegistratedBy = ?");
	 * query=connection.prepareStatement(newState); int
	 * i=Integer.parseInt((String)id.get("id")); query.setInt(1,i);
	 * rs=query.executeQuery(); while
	 * (rs.next()){te.setNumberOfDevices(rs.getInt("Count(*)"));}
	 * }catch(Exception e){return te;} finally{ CloseConnection(); } return te;
	 * }
	 * 
	 * @Path("/position")
	 * 
	 * @POST
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<Temp>
	 * GiveMePosition(JSONObject id) throws SQLException{ List<Temp>t= new
	 * ArrayList<Temp>(); Temp te=new Temp(); try{ String
	 * mystring=(String)id.get("name"); // da maknemo ð dž š è æ jer se ne moze
	 * pretrazivati po njima mystring=BosnianEncode(mystring);
	 * 
	 * CreateConnection(); String
	 * newState="SELECT * FROM Temp WHERE Temp.Name = ?"; PreparedStatement
	 * query=connection.prepareStatement(newState); query.setString(1,
	 * mystring); ResultSet rs=query.executeQuery(); te=new Temp(); t=new
	 * ArrayList<Temp>(); if
	 * (rs==null){te.setMessage("Ne postoji ureðaj sa tim imenom");t.add(te);
	 * return t;} while (rs.next()){ te=new Temp();
	 * te.setIdTemp(rs.getInt("idTemp")); te.setIdDevice(rs.getInt("DeviceId"));
	 * te.setLat(rs.getFloat("Lat")); te.setLong(rs.getFloat("Long"));
	 * te.setName(BosnianDecode(new String(rs.getBytes("Name"),"UTF-32")));
	 * te.setOrdinalNumber(rs.getInt("OrdinalNumber"));
	 * te.setUserId(rs.getInt("UserId"));
	 * te.setTime(rs.getTime("Time").toString());
	 * te.setSpeed(rs.getDouble("Speed"));
	 * te.setDate(rs.getDate("Date").toString());
	 * te.setComment(BosnianDecode(new
	 * String(rs.getBytes("Comment"),"UTF-32"))); t.add(te); }
	 * 
	 * }catch(Exception e){te.setMessage(e.getMessage());t.add(te);} finally{
	 * CloseConnection(); } // return t;
	 * 
	 * }
	 * 
	 * @Path("/positionadmin")
	 * 
	 * @POST
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<History>
	 * GiveMePositionAdmin(JSONObject id) throws SQLException{ List<History>t=
	 * new ArrayList<History>(); History te=new History(); try{ String
	 * mystring=(String)id.get("name"); String choice=(String)id.get("choice");
	 * mystring=BosnianEncode(mystring); CreateConnection(); String
	 * newState="SELECT * FROM History WHERE History.Name = ?"; switch(choice){
	 * case "1h":newState=
	 * "SELECT * FROM History WHERE ((History.Name=?) AND (History.Time >= ( CURRENT_TIME - INTERVAL 1 HOUR )) AND (History.Date=CURRENT_DATE))"
	 * ; case "6h":newState=
	 * "SELECT * FROM History WHERE ((History.Name=?) AND (History.Time >= ( CURRENT_TIME - INTERVAL 6 HOUR )) AND (History.Date=CURRENT_DATE))"
	 * ; case "1d":newState=
	 * "SELECT * FROM History WHERE ((History.Name=?) AND (History.Date >= ( CURRENT_DATE - INTERVAL 1 DAY )))"
	 * ; case "2d":newState=
	 * "SELECT * FROM History WHERE ((History.Name=?) AND (History.Date >= ( CURRENT_DATE - INTERVAL 2 DAY )))"
	 * ; case "30d":newState=
	 * "SELECT * FROM History WHERE ((History.Name=?) AND (History.Date >= ( CURRENT_DATE - INTERVAL 30 DAY )))"
	 * ; } PreparedStatement query=connection.prepareStatement(newState);
	 * query.setString(1, mystring); ResultSet rs=query.executeQuery(); Temp
	 * tek=new Temp(); if (rs==null){return t;} int rowcount = 0;
	 * 
	 * if (rs.last()) { rowcount = rs.getRow(); rs.beforeFirst(); // not
	 * rs.first() because the rs.next() below will move on, missing the first
	 * element }
	 * 
	 * while (rs.next()) { tek=new Temp();
	 * try{tek.setIdDevice(rs.getInt("DeviceId"));}catch(Exception e){}
	 * try{tek.setLat(rs.getFloat("Lat"));}catch(Exception e){}
	 * try{tek.setLong(rs.getFloat("Long"));}catch(Exception e){}
	 * try{tek.setName(BosnianDecode(new
	 * String(rs.getBytes("Name"),"UTF-32")));}catch(Exception e){}
	 * try{tek.setOrdinalNumber(rs.getInt("OrdinalNumber"));}catch(Exception
	 * e){} try{tek.setTime(rs.getTime("Time").toString());}catch(Exception e){}
	 * try{tek.setSpeed(rs.getDouble("Speed"));}catch(Exception e){}
	 * try{tek.setDate(rs.getDate("Date").toString());}catch(Exception e){}
	 * try{tek.setComment(BosnianDecode(new
	 * String(rs.getBytes("Comment"),"UTF-32")));}catch(Exception e){}
	 * 
	 * int brojac=0; //pronaði kome pripada ovaj temp for (int
	 * j=0;j<t.size();j++) { if (brojac==0) { if
	 * (t.get(j).lista.get(0).getIdDevice()==tek.getIdDevice())
	 * {t.get(j).lista.add(tek);brojac++;} } } //ako nije nasao nijednoj kome
	 * pripada znaci da nije unesen nijednom dosad, i treba ga unijeti if
	 * (brojac==0) {History z=new History(); z.lista.add(tek); t.add(z);}
	 * brojac=0; } }catch(Exception e){System.out.print(e.getMessage());}
	 * finally{ CloseConnection(); } return t; }
	 * 
	 * @Path("/search")
	 * 
	 * @POST
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<String>
	 * SearchDb(JSONObject id) throws SQLException{ List<String>t= new
	 * ArrayList<String>(); Temp te=new Temp(); try{ String
	 * mystring=(String)id.get("name"); // da maknemo ð dž š è æ jer se ne moze
	 * pretrazivati po njima mystring=BosnianEncode(mystring);
	 * 
	 * CreateConnection(); mystring="%"+mystring+"%"; String
	 * newState="SELECT IdDevice,Name FROM Device WHERE Name LIKE ?";
	 * 
	 * 
	 * PreparedStatement query=connection.prepareStatement(newState);
	 * query.setString(1, mystring); ResultSet rs=query.executeQuery(); t=new
	 * ArrayList<String>(); if
	 * (rs==null){t.add("Ne postoji ureðaj sa tim imenom");return t;} while
	 * (rs.next()){
	 * 
	 * t.add(BosnianDecode(new String(rs.getBytes("Name"),"UTF-32"))); }
	 * 
	 * }catch(Exception e){t.add(e.getMessage());} finally{ CloseConnection(); }
	 * //System.out.print(t.toString()); return t; }
	 * 
	 * @Path("/searchadmin")
	 * 
	 * @POST
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<String>
	 * SearchDbAdmin(JSONObject id) throws SQLException{ List<String>t= new
	 * ArrayList<String>(); Temp te=new Temp(); try{ String
	 * mystring=(String)id.get("name"); String myid=(String)id.get("id"); // da
	 * maknemo ð dž š è æ jer se ne moze pretrazivati po njima
	 * mystring=BosnianEncode(mystring);
	 * 
	 * CreateConnection(); mystring="%"+mystring+"%"; myid="%"+myid+"%"; String
	 * newState
	 * ="SELECT Name FROM Device WHERE Name LIKE ? AND RegistratedBy LIKE ?";
	 * 
	 * 
	 * PreparedStatement query=connection.prepareStatement(newState);
	 * query.setString(1, mystring); query.setString(2, myid); ResultSet
	 * rs=query.executeQuery(); t=new ArrayList<String>(); if
	 * (rs==null){t.add("Ne postoji ureðaj sa tim imenom");return t;} while
	 * (rs.next()){
	 * 
	 * t.add(BosnianDecode(new String(rs.getBytes("Name"),"UTF-32"))); }
	 * 
	 * }catch(Exception e){t.add(e.getMessage());} finally{ CloseConnection(); }
	 * //System.out.print(t.toString()); return t; }
	 * 
	 * 
	 * @Path("/registerdevice")
	 * 
	 * @POST
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public void
	 * RegisterDevice(JSONObject id) throws SQLException{ String s="Uspjesno";
	 * Device te=new Device(); try{
	 * te.setName(BosnianEncode(id.get("name").toString()));
	 * te.setUsername(BosnianEncode(id.get("username").toString()));
	 * te.setRegistratedby((int)id.get("id"));
	 * te.setPassword(BosnianEncode(id.get("password").toString()));
	 * te.setSerialNumber(BosnianEncode(id.get("serialname").toString()));
	 * te.setType(BosnianEncode(id.get("type").toString()));
	 * 
	 * CreateConnection();
	 * 
	 * String newState="SELECT Count(*) FROM Device WHERE RegistratedBy= ?";
	 * PreparedStatement query=connection.prepareStatement(newState);
	 * query.setInt(1, te.getRegistratedby()); ResultSet
	 * rs=query.executeQuery(); int a=0; while
	 * (rs.next()){a=rs.getInt("Count(*)");} a++; te.setOrdinalNumber(a);
	 * newState=new String(
	 * "INSERT INTO Device (RegistratedBy,Username,Password,SerialName,Name,OrdinalNumber,Type,RegistrationDate,RegistrationTime) VALUES (?,?,?,?,?,?,?,CURDATE(),CURTIME()) "
	 * ); PreparedStatement querya=connection.prepareStatement(newState);
	 * querya.setInt(1, te.getRegistratedby()); querya.setString(2,
	 * te.getUsername()); querya.setString(3, te.getPassword());
	 * querya.setString(4, te.getSerialNumber()); querya.setString(5,
	 * te.getName()); querya.setInt(6, te.getOrdinalNumber());
	 * querya.setString(7, te.getType()); int rse=querya.executeUpdate();
	 * 
	 * }catch(Exception e){s=(e.getMessage());} finally{ CloseConnection(); }
	 * 
	 * }
	 */

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version calling:</p>" + api_version;
	}
	/*
	 * 
	 * public void CreateConnection() throws ClassNotFoundException,
	 * SQLException{ Class.forName("com.mysql.jdbc.Driver"); url =
	 * "jdbc:mysql://localhost/TrackerDatabase?useEncoding=true&setCharset=UTF-32"
	 * ; user = "root"; password = ""; connection =
	 * DriverManager.getConnection(url, user, password); } public void
	 * CloseConnection() throws SQLException{ if (connection != null) {
	 * //zatvaranje konekcije s bazom connection.close(); connection=null; } }
	 * 
	 * 
	 * @Path("/clean")
	 * 
	 * @GET public void CleanTemp() throws SQLException{ String name=new
	 * String("Sutjeska-Vogosscca"); try{ CreateConnection(); String
	 * newState="DELETE FROM Temp WHERE Temp.Name=?"; PreparedStatement
	 * query=connection.prepareStatement(newState); query.setString(1, name);
	 * int rs=query.executeUpdate();
	 * newState="DELETE FROM History WHERE History.Name=?";
	 * query=connection.prepareStatement(newState); query.setString(1, name);
	 * rs=query.executeUpdate();
	 * 
	 * }catch(Exception e){System.out.print(e.getMessage());} finally{
	 * CloseConnection(); }
	 * 
	 * }
	 * 
	 * @Path("/clean3")
	 * 
	 * @GET public void CleanTempThree() throws SQLException{
	 * 
	 * String name1=new String("Sutjeska-Vogosscca"); String name2=new
	 * String("Sutjeska-Ilijass"); String name3=new String("Sutjeska-Nahorevo");
	 * try{ CreateConnection(); String
	 * newState="DELETE FROM Temp WHERE Temp.Name=?"; PreparedStatement
	 * query=connection.prepareStatement(newState); query.setString(1, name1);
	 * int rs=query.executeUpdate();
	 * newState="DELETE FROM History WHERE History.Name=?";
	 * query=connection.prepareStatement(newState); query.setString(1, name1);
	 * rs=query.executeUpdate();
	 * 
	 * newState="DELETE FROM Temp WHERE Temp.Name=?";
	 * query=connection.prepareStatement(newState); query.setString(1, name2);
	 * rs=query.executeUpdate();
	 * newState="DELETE FROM History WHERE History.Name=?";
	 * query=connection.prepareStatement(newState); query.setString(1, name2);
	 * rs=query.executeUpdate();
	 * 
	 * newState="DELETE FROM Temp WHERE Temp.Name=?";
	 * query=connection.prepareStatement(newState); query.setString(1, name3);
	 * rs=query.executeUpdate();
	 * newState="DELETE FROM History WHERE History.Name=?";
	 * query=connection.prepareStatement(newState); query.setString(1, name3);
	 * rs=query.executeUpdate();
	 * 
	 * }catch(Exception e){System.out.print(e.getMessage());} finally{
	 * CloseConnection(); }
	 * 
	 * }
	 * 
	 * @Path("/automaticsimulation")
	 * 
	 * @GET public void Simulation() throws SQLException, InterruptedException{
	 * String name=new String("Sutjeska-Vogosscca"); double latl= 43.85; double
	 * longl=18.33; for(int i=1;i<30;i++) { try{
	 * 
	 * latl+=((double)i)/10000; longl+=((double)i)/10000; CreateConnection();
	 * String newState=
	 * "INSERT INTO Temp (Temp.idTemp,Temp.DeviceId, Temp.UserId, Temp.Name, Temp.OrdinalNumber, Temp.Lat, Temp.Long, Temp.Time, Temp.Date, Temp.Comment, Temp.Speed) VALUES(74,4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE Temp.Lat=?, Temp.Long=?, Temp.Time=CURTIME(), Temp.Date=CURDATE(), Temp.Speed=?"
	 * ; PreparedStatement query=connection.prepareStatement(newState);
	 * query.setString(1, name); query.setDouble(2, latl); query.setDouble(3,
	 * longl); query.setDouble(4, i); query.setDouble(5, latl);
	 * query.setDouble(6, longl); query.setDouble(7, i); int
	 * rs=query.executeUpdate(); //ubaciti upis u history newState=
	 * "INSERT INTO History (History.DeviceId, History.UserId, History.Name, History.OrdinalNumber, History.Lat, History.Long, History.Time, History.Date, History.Comment, History.Speed) VALUES(4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE History.Lat=?, History.Long=?, History.Time=CURTIME(), History.Date=CURDATE(), History.Speed=?"
	 * ; query=connection.prepareStatement(newState); query.setString(1, name);
	 * query.setDouble(2, latl); query.setDouble(3, longl); query.setDouble(4,
	 * i); query.setDouble(5, latl); query.setDouble(6, longl);
	 * query.setDouble(7, i); rs=query.executeUpdate();
	 * 
	 * }catch(Exception e){System.out.print(e.getMessage());} finally{
	 * CloseConnection(); }
	 * 
	 * Thread.sleep(1000); }
	 * 
	 * }
	 * 
	 * 
	 * @Path("/automaticsimulation3")
	 * 
	 * @GET public void SimulationThree() throws SQLException,
	 * InterruptedException{ String name1=new String("Sutjeska-Vogosscca");
	 * String name2=new String("Sutjeska-Ilijass"); String name3=new
	 * String("Sutjeska-Nahorevo");
	 * 
	 * double latl1= 43.85; double longl1=18.33; double latl2= 43.75; double
	 * longl2=18.43; double latl3= 43.65; double longl3=18.53; for(int
	 * i=1;i<30;i++) { try{
	 * 
	 * latl1+=((double)i)/10000; longl1+=((double)i)/10000;
	 * latl2+=((double)i)/10000; longl2+=((double)i)/10000;
	 * latl3+=((double)i)/10000; longl3+=((double)i)/10000;
	 * 
	 * CreateConnection(); //Sutjeska-Vogosscca String newState=
	 * "INSERT INTO Temp (Temp.idTemp,Temp.DeviceId, Temp.UserId, Temp.Name, Temp.OrdinalNumber, Temp.Lat, Temp.Long, Temp.Time, Temp.Date, Temp.Comment, Temp.Speed) VALUES(74,4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE Temp.Lat=?, Temp.Long=?, Temp.Time=CURTIME(), Temp.Date=CURDATE(), Temp.Speed=?"
	 * ; PreparedStatement query=connection.prepareStatement(newState);
	 * query.setString(1, name1); query.setDouble(2, latl1); query.setDouble(3,
	 * longl1); query.setDouble(4, i); query.setDouble(5, latl1);
	 * query.setDouble(6, longl1); query.setDouble(7, i); int
	 * rs=query.executeUpdate(); //ubaciti upis u history newState=
	 * "INSERT INTO History (History.DeviceId, History.UserId, History.Name, History.OrdinalNumber, History.Lat, History.Long, History.Time, History.Date, History.Comment, History.Speed) VALUES(4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE History.Lat=?, History.Long=?, History.Time=CURTIME(), History.Date=CURDATE(), History.Speed=?"
	 * ; query=connection.prepareStatement(newState); query.setString(1, name1);
	 * query.setDouble(2, latl1); query.setDouble(3, longl1); query.setDouble(4,
	 * i); query.setDouble(5, latl1); query.setDouble(6, longl1);
	 * query.setDouble(7, i); rs=query.executeUpdate(); //Sutjeska-Ilijass
	 * newState=
	 * "INSERT INTO Temp (Temp.idTemp,Temp.DeviceId, Temp.UserId, Temp.Name, Temp.OrdinalNumber, Temp.Lat, Temp.Long, Temp.Time, Temp.Date, Temp.Comment, Temp.Speed) VALUES(74,4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE Temp.Lat=?, Temp.Long=?, Temp.Time=CURTIME(), Temp.Date=CURDATE(), Temp.Speed=?"
	 * ; query=connection.prepareStatement(newState); query.setString(1, name2);
	 * query.setDouble(2, latl2); query.setDouble(3, longl2); query.setDouble(4,
	 * i); query.setDouble(5, latl2); query.setDouble(6, longl2);
	 * query.setDouble(7, i); rs=query.executeUpdate(); //ubaciti upis u history
	 * newState=
	 * "INSERT INTO History (History.DeviceId, History.UserId, History.Name, History.OrdinalNumber, History.Lat, History.Long, History.Time, History.Date, History.Comment, History.Speed) VALUES(4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE History.Lat=?, History.Long=?, History.Time=CURTIME(), History.Date=CURDATE(), History.Speed=?"
	 * ; query=connection.prepareStatement(newState); query.setString(1, name2);
	 * query.setDouble(2, latl2); query.setDouble(3, longl2); query.setDouble(4,
	 * i); query.setDouble(5, latl2); query.setDouble(6, longl2);
	 * query.setDouble(7, i); rs=query.executeUpdate(); //Sutjeska-Nahorevo
	 * newState=
	 * "INSERT INTO Temp (Temp.idTemp,Temp.DeviceId, Temp.UserId, Temp.Name, Temp.OrdinalNumber, Temp.Lat, Temp.Long, Temp.Time, Temp.Date, Temp.Comment, Temp.Speed) VALUES(74,4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE Temp.Lat=?, Temp.Long=?, Temp.Time=CURTIME(), Temp.Date=CURDATE(), Temp.Speed=?"
	 * ; query=connection.prepareStatement(newState); query.setString(1, name3);
	 * query.setDouble(2, latl3); query.setDouble(3, longl3); query.setDouble(4,
	 * i); query.setDouble(5, latl3); query.setDouble(6, longl3);
	 * query.setDouble(7, i); rs=query.executeUpdate(); //ubaciti upis u history
	 * newState=
	 * "INSERT INTO History (History.DeviceId, History.UserId, History.Name, History.OrdinalNumber, History.Lat, History.Long, History.Time, History.Date, History.Comment, History.Speed) VALUES(4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE History.Lat=?, History.Long=?, History.Time=CURTIME(), History.Date=CURDATE(), History.Speed=?"
	 * ; query=connection.prepareStatement(newState); query.setString(1, name3);
	 * query.setDouble(2, latl3); query.setDouble(3, longl3); query.setDouble(4,
	 * i); query.setDouble(5, latl3); query.setDouble(6, longl3);
	 * query.setDouble(7, i); rs=query.executeUpdate(); }catch(Exception
	 * e){System.out.print(e.getMessage());} finally{ CloseConnection(); }
	 * 
	 * Thread.sleep(1000); }
	 * 
	 * }
	 * 
	 * 
	 * //uklanja slova šèæðdž public String BosnianEncode(String mystring){
	 * //mala slova mystring=mystring.replace("š", "ss");
	 * mystring=mystring.replace("dž", "dzzz"); mystring=mystring.replace("ð",
	 * "djjj"); mystring=mystring.replace("è", "ccc");
	 * mystring=mystring.replace("æ", "cc"); //velika slova
	 * mystring=mystring.replace("Š", "SS"); mystring=mystring.replace("Dž",
	 * "Dzzz"); mystring=mystring.replace("Ð", "Djjj");
	 * mystring=mystring.replace("È", "Ccc"); mystring=mystring.replace("Æ",
	 * "Cc"); return mystring; } //vraæa slova šèædžð public String
	 * BosnianDecode(String mystring){ //mala slova
	 * mystring=mystring.replace("ss","š");
	 * mystring=mystring.replace("dzzz","dž");
	 * mystring=mystring.replace("djjj","ð");
	 * mystring=mystring.replace("ccc","è");
	 * mystring=mystring.replace("cc","æ"); //velika slova
	 * mystring=mystring.replace("Ss","Š");
	 * mystring=mystring.replace("Dzzz","Dž");
	 * mystring=mystring.replace("Djjj","Ð");
	 * mystring=mystring.replace("Ccc","È");
	 * mystring=mystring.replace("Cc","Æ"); return mystring; }
	 */
}
