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

	public Long registerAccount(Connection con,Person id) throws IOException {
		boolean t;
		long d=0;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := INSERTPERSONRETURNINGID(?,?,?,?,?,?,?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setString(2,id.get_firstname());
			cs.setString(3,id.get_lastname());
			cs.setString(4,id.get_city());
			cs.setString(5,id.get_username());
			cs.setString(6,id.get_password());
			cs.setString(7,id.get_email());
			cs.setString(8,"f");
			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			d=rs.getLong(0);
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return d;
	}
	
	public Person loginAccount(Connection con,String username, String password) throws IOException {
		boolean t;
		Person d=new Person();
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETPERSON(?,?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setString(2,username);
			cs.setString(3,password);

			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			d=(Person) rs.getObject(0);
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return d;
	}
}
