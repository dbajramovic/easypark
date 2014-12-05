package ServicePackage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;

import Models.Parking;
import Models.Slika;
import oracle.jdbc.OracleTypes;
import oracle.sql.BLOB;
 
public class OracleConnect {
 
	public static void main(String[] args) throws IOException {
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		Locale.setDefault(Locale.US);
		try {Class.forName("oracle.jdbc.driver.OracleDriver");} catch (ClassNotFoundException e) {
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("Oracle JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@80.65.65.66:1521:ETFLAB", "EasyPark","EasyParkIsNum1");
		}
		 catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			loadPicture l = new loadPicture();
			Slika p = l.giveMe(connection, 3078);
			System.out.println(p.get_slika());
		}
		else {
			System.out.println("Failed to make connection!");
		}
	}
}