package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.City;
import model.Parking;
import oracle.jdbc.OracleTypes;

public class ParkingsFunctions {
	public List<Parking> giveMe(Connection con, float longtituda,
			float latituda, int razdaljina) {
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		List<Parking> lista_parkinga = new ArrayList<Parking>();
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETPARKINGSATLOCATION(?,?,?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setFloat(2, latituda);
			cs.setFloat(3, longtituda);
			cs.setFloat(4, razdaljina);
			t = cs.execute();
			rs = (ResultSet) cs.getObject(1);
			while (rs.next()) {
				Parking p = new Parking();
				p.set_parkingID(rs.getInt(1));
				p.set_creator(rs.getString(17));
				p.set_longitude(rs.getFloat(3));
				p.set_latitude(rs.getFloat(4));
				p.set_note(rs.getString(7));
				p.set_price(rs.getFloat(6));
				p.set_pictureID(rs.getInt(2));
				p.set_totalnumber(rs.getInt(5));
				p.set_isthereCamera(new Boolean(rs.getInt(8) != 0));
				p.set_isthereGuard(new Boolean(rs.getInt(9) != 0));
				p.set_isthereLight(new Boolean(rs.getInt(13) != 0));
				p.set_isthereGoodEntrance(new Boolean(rs.getInt(12) != 0));
				p.set_isthereRoof(new Boolean(rs.getInt(10) != 0));
				p.set_isthereRoad(new Boolean(rs.getInt(11) != 0));
				p.set_secrating(rs.getFloat(15));
				p.set_ovrrating(rs.getFloat(16));
				p.set_telefon(rs.getString(14));
				p.set_freespots(rs.getInt(18));
				p.set_takenspots(rs.getInt(19));
				lista_parkinga.add(p);
			}
			return lista_parkinga;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista_parkinga;
	}
	
	public List<Parking> giveMeForTable(Connection con, int kolicina) {
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		List<Parking> lista_parkinga = new ArrayList<Parking>();
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETPARKINGSFORTABLE(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2, kolicina);
			t = cs.execute();
			rs = (ResultSet) cs.getObject(1);
			while (rs.next()) {
				Parking p = new Parking();
				p.set_parkingID(rs.getInt(1));
				p.set_creator(rs.getString(17));
				p.set_note(rs.getString(7));
				p.set_price(rs.getFloat(6));
				p.set_totalnumber(rs.getInt(5));
				p.set_telefon(rs.getString(14));
				p.set_isVerificated(rs.getString(20));
				p.set_results(rs.getInt(21));
				lista_parkinga.add(p);
			}
			return lista_parkinga;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista_parkinga;
	}
	public List<Parking> giveMeUserParkings(Connection con, int userid) {
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		List<Parking> lista_parkinga = new ArrayList<Parking>();
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETPARKINGBYOWNER(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2, userid);
			t = cs.execute();
			rs = (ResultSet) cs.getObject(1);
			while (rs.next()) {
				Parking p = new Parking();
				p.set_parkingID(rs.getInt(1));
				p.set_creator(rs.getString(17));
				p.set_longitude(rs.getFloat(3));
				p.set_latitude(rs.getFloat(4));
				p.set_note(rs.getString(7));
				p.set_price(rs.getFloat(6));
				p.set_pictureID(rs.getInt(2));
				p.set_totalnumber(rs.getInt(5));
				p.set_isthereCamera(new Boolean(rs.getInt(8) != 0));
				p.set_isthereGuard(new Boolean(rs.getInt(9) != 0));
				p.set_isthereLight(new Boolean(rs.getInt(13) != 0));
				p.set_isthereGoodEntrance(new Boolean(rs.getInt(12) != 0));
				p.set_isthereRoof(new Boolean(rs.getInt(10) != 0));
				p.set_isthereRoad(new Boolean(rs.getInt(11) != 0));
				p.set_secrating(rs.getFloat(15));
				p.set_ovrrating(rs.getFloat(16));
				p.set_telefon(rs.getString(14));
				p.set_freespots(rs.getInt(18));
				p.set_takenspots(rs.getInt(19));
				lista_parkinga.add(p);
				System.out.println(p);
			}
			return lista_parkinga;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista_parkinga;
	}

	public void updateUserParkings(Connection con, int spaces, int userid) {
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		List<Parking> lista_parkinga = new ArrayList<Parking>();
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin UPDATEUSERPARKING(?,?); end;");
			cs.clearParameters();
			cs.setInt(1, spaces);
			cs.setInt(2, userid);
			t = cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void reserveParkings(Connection con, int spaces, int userid,String date, int parkID) {
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		List<Parking> lista_parkinga = new ArrayList<Parking>();
		try {
			CallableStatement cs = null;
			System.out.print(date);
			cs = con.prepareCall("begin RESERVESPOTFORUSER(?,?,?,?); end;");
			cs.clearParameters();
			cs.setInt(1, spaces);
			cs.setInt(3, userid);
			cs.setString(2, date);
			cs.setInt(4,parkID);
			t = cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<City> getSuggestions(Connection con) {
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		List<City> lista_prijedloga = new ArrayList<City>();
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETCITIES(); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			t = cs.execute();
			rs = (ResultSet) cs.getObject(1);
			while (rs.next()) {
				City p = new City();
				p.set_longitude(rs.getFloat(4));
				p.set_latitude(rs.getFloat(3));
				p.set_name(rs.getString(2));
				lista_prijedloga.add(p);
			}
			return lista_prijedloga;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista_prijedloga;
	}
	public void registerParking(Connection con,Parking p, int creatorid) {
		boolean t;
		Locale.setDefault(Locale.US);
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ADDPARKING(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); end;");
			cs.clearParameters();
			cs.setInt(1, creatorid);
			cs.setInt(2, p.get_freespots());
			cs.setFloat(3, p.get_price());
			cs.setString(4,p.get_note());
			cs.setInt(5, 1);
			cs.setBoolean(6,p.get_isthereCamera());
			cs.setBoolean(7,p.get_isthereGuard());
			cs.setBoolean(8,p.get_isthereRoof());
			cs.setBoolean(9,p.get_isthereRoad());
			cs.setBoolean(10,p.get_isthereGoodEntrance());
			cs.setString(11,p.get_telefon());
			cs.setBoolean(12,p.get_isthereLight());
			cs.setInt(13, 0);
			cs.setInt(14, 0);
			cs.setString(15, "N");
			cs.setFloat(16,p.get_latitude());
			cs.setFloat(17,p.get_longitude());
			cs.setInt(18, p.get_city());
			cs.setInt(19, p.get_freespots());
			cs.setInt(20, 0);
			cs.setString(21,"Y");
			t = cs.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
}
