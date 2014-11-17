package ServicePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnector {
	private static Connection oc = null;
	protected OracleConnector() {};
	public static Connection getConnection() throws SQLException {
		if(oc == null) {
			oc = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "EasyParkTest",
					"FCChelsea1");
		}
		return oc;
	}

}
