package ServicePackage;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Models.Parking;
import oracle.jdbc.OracleTypes;
public class GetParkingsAtLocation {
	public List<Parking> giveMe(Connection con,int longtituda, int langtituda, int razdaljina) {
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		List<Parking> lista_parkinga = new ArrayList<Parking>();
		try {
		CallableStatement cs = null;
		cs = con.prepareCall("begin ? := GETPARKINGSATLOCATION(?,?,?); end;");
		cs.clearParameters();
		cs.registerOutParameter(1,OracleTypes.CURSOR);
		cs.setInt(2, longtituda);
		cs.setInt(3, langtituda);
		cs.setInt(4, razdaljina);
		cs.execute();
		rs = (ResultSet)cs.getObject(1);
		while(rs.next()) {
			Parking p = new Parking();
			p.set_parkingID(rs.getInt(1));
			p.set_longtitude(rs.getFloat(2));
			p.set_latitude(rs.getFloat(3));
			p.set_note(rs.getString(6));
			p.set_price(rs.getInt(5));
			p.set_pictureID(rs.getInt(2));
			p.set_totalnumber(rs.getInt(4));
			p.set_isthereCamera(new Boolean(rs.getInt(8) != 0));
			p.set_isthereGuard(new Boolean(rs.getInt(9) != 0));
			p.set_isthereLight(new Boolean(rs.getInt(13) != 0));
			p.set_isthereGoodEntrance(new Boolean(rs.getInt(12) != 0));
			p.set_isthereRoof(new Boolean(rs.getInt(10) != 0));
			p.set_isthereRoad(new Boolean(rs.getInt(11) != 0));
			lista_parkinga.add(p);
		}
		return lista_parkinga;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return lista_parkinga;
	}

}
