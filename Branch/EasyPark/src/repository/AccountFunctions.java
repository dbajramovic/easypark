package repository;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.Parking;
import model.Person;
import oracle.jdbc.OracleTypes;

public class AccountFunctions {

	public void registerAccount(Connection con,Person id) throws IOException {
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			// regular user koji ce traziti parkinge i rezervisati
			if (id.get_type()==1)cs = con.prepareCall("begin REGISTERREGULAR(?,?,?,?,?,?,?,?); end;");
			//vlasnici parkinga
			if (id.get_type()==2)cs = con.prepareCall("begin REGISTERPREMIUM(?,?,?,?,?,?,?,?); end;");
			//admin
			if (id.get_type()==3)cs = con.prepareCall("begin REGISTEROWNER(?,?,?,?,?,?,?,?); end;");
		
			cs.clearParameters();
			cs.setString(1,id.get_firstname());
			cs.setString(2,id.get_lastname());
			cs.setString(3,id.get_email());
			cs.setString(4,id.get_password());
			cs.setString(5,id.get_address());
			cs.setString(6,id.get_phonenumber());
			cs.setString(7,id.get_companyName());
			cs.setString(8,id.get_accountNumber());
			t = cs.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Person loginAccount(Connection con,String username, String password) throws IOException {
		boolean t;
		Person d=new Person();
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := LOGIN(?,?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setString(2,username);
			cs.setString(3,password);
			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			d.set_personID(rs.getInt(1));
			d.set_firstname(rs.getString(2));
			d.set_lastname(rs.getString(3));
			d.set_email(rs.getString(5));
			d.set_phonenumber(rs.getString(7));
			d.set_address(rs.getString(8));
			d.set_type(rs.getInt(9));
			d.set_companyName(rs.getString(11));
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return d;
	}

	public List<Person> giveMeUsersForTable(Connection con,int howMuch) {
		List<Person> p = new ArrayList<Person>();
		Boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETUSERSFORTABLE(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2,howMuch);
			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			Person pe=new Person();
			pe.set_personID(rs.getInt(1));
			pe.set_firstname(rs.getString(2));
			pe.set_lastname(rs.getString(3));
			pe.set_accountNumber(rs.getString(10));
			pe.set_phonenumber(rs.getString(7));
			pe.set_email(rs.getString(5));
			pe.set_address(rs.getString(8));
			p.add(pe);
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	public List<Person> giveMePremiumForTable(Connection con,int howMuch) {
		List<Person> p = new ArrayList<Person>();
		Boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETPREMIUMFORTABLE(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2,howMuch);
			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			Person pe=new Person();
			pe.set_personID(rs.getInt(1));
			pe.set_firstname(rs.getString(2));
			pe.set_lastname(rs.getString(3));
			pe.set_accountNumber(rs.getString(10));
			pe.set_phonenumber(rs.getString(7));
			pe.set_email(rs.getString(5));
			pe.set_address(rs.getString(8));
			pe.set_companyName(rs.getString(11));
			p.add(pe);
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	public Person UserDetails(Connection con,int userID) {
		Person p = new Person();
		Boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETPERSONBYID(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2,userID);
			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			p.set_firstname(rs.getString(2));
			p.set_lastname(rs.getString(3));
			p.set_address(rs.getString(4));
			p.set_email(rs.getString(4));
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	public void SaveTokenRequest(Connection con,String username, int type, String token) {
		Person p = new Person();
		Boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin SAVETOKENREQUEST(?,?,?,?); end;");
			cs.clearParameters();
			cs.setString(1,"");
			cs.setInt(2,type);
			cs.setString(3,username);
			cs.setInt(4,0);
			t = cs.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void UpdateTokenRequest(Connection con,long id, String token) {
		Boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin UPDATETOKENREQUEST(?,?,?); end;");
			cs.clearParameters();
			cs.setLong(1,id);
			cs.setString(2,token);
			cs.setInt(3,1);
			t = cs.execute();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Boolean CheckToken(Connection con,String username, int type, String token, String action) {
		Boolean t = true;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETTOKENREQ(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setString(2, username);
			t = cs.execute();
			rs = (ResultSet) cs.getObject(1);
			long id=0;
			String tokenS="sadjlkadjiwue1i2op2k3ečlksa9di3224kpp93ik93u24324jklsu83424csalčcsačlk";
			while (rs.next()) {
				id = rs.getLong(1);
				tokenS= rs.getString(2);
				//String datum  = rs.getDate(3).toString();
				//String emailS = rs.getString(4);
				//int sentS= rs.getInt(5);
			}
			if (action.equals("request")){
					if (id>0) {t=false;} 
					else {t=true;} 
				};
			if (action.equals("registration")){
					if (tokenS.equals(token)) {t=true;} 
					else {t=false;};  
				};
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}
}
