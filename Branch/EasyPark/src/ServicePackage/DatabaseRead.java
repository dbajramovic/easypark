package ServicePackage;
import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.OutputStreamWriter;
import java.sql.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class DatabaseRead
{
	private String str1="No";
	public String DajRezultat(){return str1;}

    public DatabaseRead(String user, String pass) throws SQLException
    {

    	Connection connection = null;
    	try
        {
            // Registrovanje MySQL JDBC drivera da bude poznat po DriverManager objektu
    		Class.forName("com.mysql.jdbc.Driver");
            
           // Pripremanje informacija za konekciju
            String url = "jdbc:mysql://localhost/TrackerDatabase?useEncoding=true&setCharset=UTF-32";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
            
            // Kreiranje statementa instance poziva za konekciju na bazu
            String newState="SELECT idAdmin FROM Admin WHERE Admin.Username = ? AND Admin.Password = ?";
            PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, user);
			query.setString(2, pass);
			ResultSet rs=query.executeQuery();
            while (rs.next()) 
            {
    			str1= Integer.toString(rs.getInt("idAdmin"));
            }   
        }catch( Exception e ) {return;}
    	finally
        {
            if (connection != null)
            {
            	 //zatvaranje konekcije s bazom
            	connection.close();
            }
        }
    }
    
	//uklanja slova šèæðdž
	public String BosnianEncode(String mystring){
		//mala slova
		mystring=mystring.replace("š", "ss");
		mystring=mystring.replace("dž", "dzzz");
		mystring=mystring.replace("ð", "djjj");
		mystring=mystring.replace("è", "ccc");
		mystring=mystring.replace("æ", "cc");
		//velika slova
		mystring=mystring.replace("Š", "SS");
		mystring=mystring.replace("Dž", "Dzzz");
		mystring=mystring.replace("Ð", "Djjj");
		mystring=mystring.replace("È", "Ccc");
		mystring=mystring.replace("Æ", "Cc");
		return mystring;
	}
	//vraæa slova šèædžð
	public String BosnianDecode(String mystring){
		//mala slova
		mystring=mystring.replace("ss","š");
		mystring=mystring.replace("dzzz","dž");
		mystring=mystring.replace("djjj","ð");
		mystring=mystring.replace("ccc","è");
		mystring=mystring.replace("cc","æ");
        //velika slova
		mystring=mystring.replace("Ss","Š");
		mystring=mystring.replace("Dzzz","Dž");
		mystring=mystring.replace("Djjj","Ð");
		mystring=mystring.replace("Ccc","È");
		mystring=mystring.replace("Cc","Æ");
		return mystring;
	}

}

