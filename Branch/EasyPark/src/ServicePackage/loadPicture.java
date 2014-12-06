package ServicePackage;
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

import Models.Slika;
import oracle.jdbc.OracleTypes;
public class loadPicture {
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
		      System.out.print(buffer);
		}
		return p;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

}
