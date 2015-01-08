package controller;

import java.util.ArrayList;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.City;
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

@Path("/interface")
public class InterfaceService {

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
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion() {
		return "<p>Version calling:</p>" + api_version;
	}
	
	@Path("/parkingsinrange")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Parking> giveMeParkingsInRange(@QueryParam("longitude") double longitude, @QueryParam("latitude")double latitude, @QueryParam("range")int range) {
		List<Parking> lp = null;
		ParkingsFunctions ga = new ParkingsFunctions();
		CreateConnection();
		try {
			lp = ga.giveMe(connection, (float) longitude,(float) latitude, range);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lp;
	}
	
	@Path("/parkingreservationsforowner")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Reservation> ReservationsForOwner(@QueryParam("id") int id) throws SQLException {
		List<Reservation> l = null;
		ReservationFunctions lp = new ReservationFunctions();
		CreateConnection();
		try {
			l = lp.ReservationsForOwner(connection, id);
		} catch (Exception e) {
		} finally {
			CloseConnection();
		}
		return l;
	}
	
	@Path("/updateuserparking")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public void updateUserParking(@QueryParam("spaces") int spaces, @QueryParam("userid") int userid) {
		ParkingsFunctions ga = new ParkingsFunctions();
		CreateConnection();
		try {
			ga.updateUserParkings(connection, spaces,userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}