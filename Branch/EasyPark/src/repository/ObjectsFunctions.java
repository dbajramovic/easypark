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

import model.Complaints;
import model.Message;
import model.Person;
import model.Slika;
import model.Token;
import oracle.jdbc.OracleTypes;
public class ObjectsFunctions {
	public Slika giveMe(Connection con,int id) throws IOException {
		Slika p = new Slika();
		boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETPICTURE(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2,id);
			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			p.set_note(rs.getString(2));
			byte[] buffer = rs.getBytes(1);
			
			/*File image = new File("C:\\Users\\Bureksasutlijom\\Desktop\\java.jpg");
		      FileOutputStream fos = new FileOutputStream(image);
		      
		      InputStream is = rs.getBinaryStream(1);
		      while (is.read(buffer) > 0) {
		        fos.write(buffer);
		      }
		      fos.close();*/
		      p.set_slika(buffer);
		}
		return p;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	
	public List<Token> giveMeTokensForTable(Connection con,int howMuch) {
		List<Token> p = new ArrayList<Token>();
		Boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETTOKENSFORTABLE(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2,howMuch);
			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			Token pe=new Token();
			pe.set_tokenID(rs.getInt(1));
			pe.set_tokenBody(rs.getString(2));
			pe.set_date(rs.getString(3));
			pe.set_email(rs.getString(4));
			pe.set_sent(rs.getInt(5));
			pe.set_typeOfPerson(rs.getInt(6));
			p.add(pe);
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	
	public List<Message> giveMeMessagesForTable(Connection con,int howMuch) {
		List<Message> p = new ArrayList<Message>();
		Boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETMESSAGESFORTABLE(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2,howMuch);
			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			Message pe=new Message();
			pe.set_messageID(rs.getInt(1));
			pe.set_personID(rs.getInt(2));
			pe.set_parkingID(rs.getInt(3));
			pe.set_dateOdMes(rs.getString(4));
			pe.set_message(rs.getString(5));
			pe.set_isSolved(rs.getInt(6));
			p.add(pe);
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	public List<Complaints> giveMeComplaintsForTable(Connection con,int howMuch) {
		List<Complaints> p = new ArrayList<Complaints>();
		Boolean t;
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			CallableStatement cs = null;
			cs = con.prepareCall("begin ? := GETCOMPLAINTSFORTABLE(?); end;");
			cs.clearParameters();
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setInt(2,howMuch);
			t = cs.execute();
			rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			Complaints pe=new Complaints();
			pe.set_complaintsID(rs.getInt(1));
			pe.set_ownerID(rs.getInt(2));
			pe.set_accussedID(rs.getInt(3));
			pe.set_parkingID(rs.getInt(4));
			pe.set_numOfReserved(rs.getInt(5));
			pe.set_isSolved(rs.getInt(6));
			p.add(pe);
		}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
}
