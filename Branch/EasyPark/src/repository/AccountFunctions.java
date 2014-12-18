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
import java.util.Locale;

import model.Person;
import oracle.jdbc.OracleTypes;

public class AccountFunctions {

	public void registerAccount(Connection con,Person id) throws IOException {
		boolean t;
		long d=0;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin REGISTEROWNER(?,?,?,?,?,?); end;");
			cs.clearParameters();
			//cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setString(1,id.get_firstname());
			cs.setString(2,id.get_lastname());
			cs.setString(3,id.get_email());
			cs.setString(4,id.get_password());
			cs.setString(5,id.get_city());
			//phone number
			cs.setString(6,id.get_email());
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
			d.set_firstname(rs.getString(2));
			d.set_lastname(rs.getString(3));
			d.set_city(rs.getString(4));
			d.set_email(rs.getString(5));
			d.set_personID(rs.getInt(1));
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return d;
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
			p.set_city(rs.getString(4));
			p.set_email(rs.getString(5));
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
}
