package ServicePackage;

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
import oracle.jdbc.OracleTypes;
 
public class OracleConnect {
 
	public static void main(String[] args) {
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		Locale.setDefault(Locale.US);
 
		try {
 
			Class.forName("oracle.jdbc.driver.OracleDriver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
 
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
 
		Connection connection = null;
 
		try {
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:xe", "EasyParkTest",
					"FCChelsea1");
		}
		 catch (SQLException e) {
			
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 
		}
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
			GetParkingsAtLocation l = new GetParkingsAtLocation();
			List<Parking> lp = l.giveMe(connection, 30, 90, 100);
			for(int i = 0; i < lp.size();i++){
				System.out.println(lp.get(i).get_parkingID());
			}
		}
		else {
			System.out.println("Failed to make connection!");
		}
	}

 
}