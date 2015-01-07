package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import oracle.jdbc.OracleTypes;
import model.Reservation;



public class ReservationFunctions {
	public List<Reservation> giveMe(Connection con,int userid, String d) {
		List<Reservation> ListaRezervacija = new ArrayList<Reservation>();
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
		CallableStatement cs = null;
		cs = con.prepareCall("begin ? := GETRESERVEDBYUSER(?,?); end;");
		cs.clearParameters();
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.setInt(2,userid);
		cs.setString(3, d);
		t = cs.execute();
		rs = (ResultSet)cs.getObject(1);
		while(rs.next()){
			Reservation r = new Reservation();
			r.set_reservationID(rs.getInt(1));
			r.set_parkingID(rs.getInt(2));
			r.set_dateofStart(rs.getDate(3));
			r.set_dateofEnd(rs.getDate(4));
			r.set_numberofreserved(rs.getInt(5));
			r.set_personID(rs.getInt(6));
			r.set_note(rs.getString(7));
			r.set_isdeleted(rs.getString(8));
			ListaRezervacija.add(r);
			}
			
		}
			catch(SQLException e) {
				e.printStackTrace();
			}
		return ListaRezervacija;
	}
	public List<Reservation> ReservationsForOwner(Connection con,int parkingid) {
		List<Reservation> ListaRezervacija = new ArrayList<Reservation>();
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
		CallableStatement cs = null;
		cs = con.prepareCall("begin ? := GETRESERVATIONFOROWNER(?); end;");
		cs.clearParameters();
		cs.registerOutParameter(1, OracleTypes.CURSOR);
		cs.setInt(2, parkingid);
		t = cs.execute();
		rs = (ResultSet)cs.getObject(1);
		while(rs.next()){
			Reservation r = new Reservation();
			r.set_reservationID(rs.getInt(1));
			r.set_parkingID(rs.getInt(2));
			r.set_dateofStart(rs.getDate(3));
			r.set_dateofEnd(rs.getDate(4));
			r.set_numberofreserved(rs.getInt(5));
			r.set_personID(rs.getInt(6));
			r.set_note(rs.getString(7));
			r.set_isdeleted(rs.getString(8));
			ListaRezervacija.add(r);
			}
			
		}
			catch(SQLException e) {
				e.printStackTrace();
			}
		return ListaRezervacija;
	}
	public void ReportUser(Connection con,int ownerid,int accusedid,int parkid, int numofR) {
		Locale.setDefault(Locale.US);
		try {
		CallableStatement cs = null;
		cs = con.prepareCall("begin INSERTCOMPLAINT(?,?,?,?); end;");
		cs.clearParameters();
		cs.setInt(1, ownerid);
		cs.setInt(2,accusedid);
		cs.setInt(3, parkid);
		cs.setInt(4, numofR);
		cs.execute();
		}
			catch(SQLException e) {
				e.printStackTrace();
			}
	}
}
