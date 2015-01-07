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

import model.Complaints;
import model.Message;
import model.Parking;
import model.Person;
import model.Reservation;
import model.Slika;
import model.Token;

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

	@Path("/getTopParkings")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Parking> getTopParkings(JSONObject id) {
		List<Parking> lp = null;
		ParkingsFunctions ga = new ParkingsFunctions();
		CreateConnection();
		try {
			if (id.getString("type").equals("admin")){
				lp = ga.giveMeForTable(connection, 10);
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return lp;
	}
	@Path("/getTopKorisnici")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getTopUsers(JSONObject id) {
		List<Person> lp = null;
		AccountFunctions ga = new AccountFunctions();
		CreateConnection();
		try {
			if (id.getString("type").equals("admin")){
				lp = ga.giveMeUsersForTable(connection, 10);
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return lp;
	}
	@Path("/getTopPremium")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getTopPremium(JSONObject id) {
		List<Person> lp = null;
		AccountFunctions ga = new AccountFunctions();
		CreateConnection();
		try {
			if (id.getString("type").equals("admin")){
				lp = ga.giveMePremiumForTable(connection, 10);
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return lp;
	}
	@Path("/getTopTokens")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Token> getTopTokens(JSONObject id) {
		List<Token> lp = null;
		ObjectsFunctions ga = new ObjectsFunctions();
		CreateConnection();
		try {
			if (id.getString("type").equals("admin")){
				lp = ga.giveMeTokensForTable(connection, 10);
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return lp;
	}
	
	
	@Path("/getTopRequests")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getTopRequests(JSONObject id) {
		List<Message> lp = null;
		ObjectsFunctions ga = new ObjectsFunctions();
		CreateConnection();
		try {
			if (id.getString("type").equals("admin")){
				lp = ga.giveMeMessagesForTable(connection, 10);
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
		return lp;
	}
	
	@Path("/getTopPrijave")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Complaints> getTopPrijave(JSONObject id) {
		List<Complaints> lp = null;
		ObjectsFunctions ga = new ObjectsFunctions();
		CreateConnection();
		try {
			if (id.getString("type").equals("admin")){
				lp = ga.giveMeComplaintsForTable(connection, 10);
			}
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
	@Path("/reserveparking")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void reserveParking(JSONObject id) {
		ParkingsFunctions ga = new ParkingsFunctions();
		CreateConnection();

		try {
			ga.reserveParkings(connection, 1,id.getInt("userid"),id.getString("date"),id.getInt("parkingid"));
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
	@Path("/parkingreservationsforowner")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reservation> ReservationsForOwner(JSONObject id) throws SQLException {
		List<Reservation> l = null;
		ReservationFunctions lp = new ReservationFunctions();
		CreateConnection();
		try {
			l = lp.ReservationsForOwner(connection, id.getInt("parkingid"));
		} catch (Exception e) {
		} finally {
			CloseConnection();
		}
		return l;
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
	@Path("/reportuser")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void ReportUser(JSONObject id) throws SQLException {
		ReservationFunctions rf = new ReservationFunctions();
		CreateConnection();
		try {
			rf.ReportUser(connection, id.getInt("ownerid"), id.getInt("accusedid"),id.getInt("parkingid"), id.getInt("numofR"));

		} catch (Exception e) {
		} finally {
			CloseConnection();
		}
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

	

	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version calling:</p>" + api_version;
	}
	
}
