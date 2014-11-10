package ServicePackage;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import oracle.jdbc.OracleTypes;
public class GetParkingsAtLocation {
	public static ResultSet giveMe(Connection con,int longtituda, int langtituda, int razdaljina) {
		Locale.setDefault(Locale.US);
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "EasyParkTest",
					"FCChelsea1");
		CallableStatement cs = null;
		cs = con.prepareCall("begin ? := GETPARKINGSATLOCATION(?,?,?); end;");
		cs.clearParameters();
		cs.registerOutParameter(1,OracleTypes.CURSOR);
		cs.setInt(2, longtituda);
		cs.setInt(3, langtituda);
		cs.setInt(4, razdaljina);
		cs.execute();
		rs = (ResultSet)cs.getObject(1);
		return rs;
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

}
