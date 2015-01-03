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
			cs.setFloat(3, longtituda);
			cs.setFloat(2, latituda);
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
	
	public List<String> getSuggestions(Connection con, String term, int choice) {
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		List<String> lista_prijedloga = new ArrayList<String>();
		// pronaci u bazi sve parkinge kod kojih se u nazivu nalazi term koji je u parametrima funkcije po choiceu.
		//ako je choice 1 onda neka pretrazuje u tabeli "gradovi", samo naziv mi treba
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ako je choice 2 onda u listu nek ubaci "0.50", "1.00" , "1.50", "2.00"
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ako je choice 3 onda u listu nek ubaci "0.50", "1.00" , "1.50", "2.00"
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ako je choice 4 onda u listu nek ubaci "Besplatno", "Sa naplaćivanjem" 
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista_prijedloga;
	}
	
	public List<Parking> getParkingsBySuggestion(Connection con, String term, int choice) {
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		List<Parking> lista_parkinga = new ArrayList<Parking>();
		//ako je choice 1 onda neka pretrazuje u tabeli "gradovi", po nazivu
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ako je choice 2 onda neka pretrazi sve parkinge kod kojih je cijena minimalno==term
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ako je choice 3 onda u listu neka pretrazi sve parkinge kod kojih je cijena maximalno==term
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//ako je choice 4 onda u listu neka pretrazi sve parkinge 
		try {
			// ako je term=="Besplatno" onda parkinge koji su besplatni
			//...
			// ako je term=="Sa naplaćivanjem" onda parkinge koji se placaju, cijena im je veca od 0
			//...
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista_parkinga;
	}
}
