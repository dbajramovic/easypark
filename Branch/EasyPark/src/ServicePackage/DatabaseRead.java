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
    
	//uklanja slova ����d�
	public String BosnianEncode(String mystring){
		//mala slova
		mystring=mystring.replace("�", "ss");
		mystring=mystring.replace("d�", "dzzz");
		mystring=mystring.replace("�", "djjj");
		mystring=mystring.replace("�", "ccc");
		mystring=mystring.replace("�", "cc");
		//velika slova
		mystring=mystring.replace("�", "SS");
		mystring=mystring.replace("D�", "Dzzz");
		mystring=mystring.replace("�", "Djjj");
		mystring=mystring.replace("�", "Ccc");
		mystring=mystring.replace("�", "Cc");
		return mystring;
	}
	//vra�a slova ���d��
	public String BosnianDecode(String mystring){
		//mala slova
		mystring=mystring.replace("ss","�");
		mystring=mystring.replace("dzzz","d�");
		mystring=mystring.replace("djjj","�");
		mystring=mystring.replace("ccc","�");
		mystring=mystring.replace("cc","�");
        //velika slova
		mystring=mystring.replace("Ss","�");
		mystring=mystring.replace("Dzzz","D�");
		mystring=mystring.replace("Djjj","�");
		mystring=mystring.replace("Ccc","�");
		mystring=mystring.replace("Cc","�");
		return mystring;
	}

}

