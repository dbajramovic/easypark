package ServicePackage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
//import jdk.nashorn.internal.runtime.regexp.RegExp;




import Models.Parking;

@Path("/service")
public class TrackerService {

	private static final String api_version="1.0.3";
	private static String conn="";
	private String url;
	private String user;
	private String password;
	private Connection connection = null;
	
	@Path("/parkingsinrange")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Parking> giveMeParkingsInRange(JSONObject id) throws SQLException, JSONException{
		List<Parking> lp = null;
		GetParkingsAtLocation ga = new GetParkingsAtLocation();
		try {
			lp = ga.giveMe(OracleConnector.getConnection(), id.getInt("longtitude"), id.getInt("latitude"), id.getInt("range"));
		}
		catch(SQLException e) {
			e.printStackTrace();	
		}
		return lp;
	}
/*
	@Path("/getdataofadmin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Admin GiveMeDataAdmin(JSONObject id) throws SQLException{
		Admin te=new Admin();
		try{
			String mystring=(String)id.get("username");
			String myid=(String)id.get("id");
			mystring=BosnianEncode(mystring);
			CreateConnection();
			String newState="SELECT * FROM Admin WHERE Username = ? AND idAdmin = ?";
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, mystring);
			query.setString(2, myid);
			ResultSet rs=query.executeQuery();
         	while (rs.next()){
					te.setFirstName(BosnianDecode(new String(rs.getBytes("FirstName"),"UTF-32")));
					te.setLastName(BosnianDecode(new String(rs.getBytes("LastName"),"UTF-32")));
					te.setUsername(BosnianDecode(new String(rs.getBytes("Username"),"UTF-32")));
					te.setCompanyName(BosnianDecode(new String(rs.getBytes("CompanyName"),"UTF-32")));
         	}
			newState=new String("SELECT Count(*) FROM Device WHERE RegistratedBy = ?");
			query=connection.prepareStatement(newState);
			int i=Integer.parseInt((String)id.get("id"));
			query.setInt(1,i);
			rs=query.executeQuery();
			while (rs.next()){te.setNumberOfDevices(rs.getInt("Count(*)"));}
		}catch(Exception e){return te;}
		finally{
			CloseConnection();
		}
		return te;		
	}
	@Path("/position")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Temp> GiveMePosition(JSONObject id) throws SQLException{
		List<Temp>t= new ArrayList<Temp>();
		Temp te=new Temp();
		try{
			String mystring=(String)id.get("name");
			// da maknemo ð dž š è æ jer se ne moze pretrazivati po njima
			mystring=BosnianEncode(mystring);
			
			CreateConnection();
			String newState="SELECT * FROM Temp WHERE Temp.Name = ?";
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, mystring);
			ResultSet rs=query.executeQuery();
			te=new Temp();
         	t=new ArrayList<Temp>();
			if (rs==null){te.setMessage("Ne postoji ureðaj sa tim imenom");t.add(te); return t;}
         	while (rs.next()){
						te=new Temp();
		            	te.setIdTemp(rs.getInt("idTemp"));
		            	te.setIdDevice(rs.getInt("DeviceId"));
		            	te.setLat(rs.getFloat("Lat"));
		            	te.setLong(rs.getFloat("Long"));
		            	te.setName(BosnianDecode(new String(rs.getBytes("Name"),"UTF-32")));
		            	te.setOrdinalNumber(rs.getInt("OrdinalNumber"));
		            	te.setUserId(rs.getInt("UserId"));
		            	te.setTime(rs.getTime("Time").toString());
		            	te.setSpeed(rs.getDouble("Speed"));
		            	te.setDate(rs.getDate("Date").toString());
		            	te.setComment(BosnianDecode(new String(rs.getBytes("Comment"),"UTF-32")));
		            	t.add(te);
		            }

		}catch(Exception e){te.setMessage(e.getMessage());t.add(te);}
		finally{
			CloseConnection();
		}
		//
		return t;
		
	}	
	@Path("/positionadmin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<History> GiveMePositionAdmin(JSONObject id) throws SQLException{
		List<History>t= new ArrayList<History>();
		History te=new History();
		try{
			String mystring=(String)id.get("name");
			String choice=(String)id.get("choice");			
			mystring=BosnianEncode(mystring);
			CreateConnection();
			String newState="SELECT * FROM History WHERE History.Name = ?";
			switch(choice){
			case "1h":newState="SELECT * FROM History WHERE ((History.Name=?) AND (History.Time >= ( CURRENT_TIME - INTERVAL 1 HOUR )) AND (History.Date=CURRENT_DATE))";
			case "6h":newState="SELECT * FROM History WHERE ((History.Name=?) AND (History.Time >= ( CURRENT_TIME - INTERVAL 6 HOUR )) AND (History.Date=CURRENT_DATE))";
			case "1d":newState="SELECT * FROM History WHERE ((History.Name=?) AND (History.Date >= ( CURRENT_DATE - INTERVAL 1 DAY )))";
			case "2d":newState="SELECT * FROM History WHERE ((History.Name=?) AND (History.Date >= ( CURRENT_DATE - INTERVAL 2 DAY )))";
			case "30d":newState="SELECT * FROM History WHERE ((History.Name=?) AND (History.Date >= ( CURRENT_DATE - INTERVAL 30 DAY )))";
			}
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, mystring);
			ResultSet rs=query.executeQuery();
			Temp tek=new Temp();
			if (rs==null){return t;}
			int rowcount = 0;
			
			if (rs.last()) {
			  rowcount = rs.getRow();
			  rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
			}
			
			while (rs.next())
			{
						tek=new Temp();
						try{tek.setIdDevice(rs.getInt("DeviceId"));}catch(Exception e){}
		            	try{tek.setLat(rs.getFloat("Lat"));}catch(Exception e){}
		            	try{tek.setLong(rs.getFloat("Long"));}catch(Exception e){}
		            	try{tek.setName(BosnianDecode(new String(rs.getBytes("Name"),"UTF-32")));}catch(Exception e){}
		            	try{tek.setOrdinalNumber(rs.getInt("OrdinalNumber"));}catch(Exception e){}
		            	try{tek.setTime(rs.getTime("Time").toString());}catch(Exception e){}
		            	try{tek.setSpeed(rs.getDouble("Speed"));}catch(Exception e){}
		            	try{tek.setDate(rs.getDate("Date").toString());}catch(Exception e){}
		            	try{tek.setComment(BosnianDecode(new String(rs.getBytes("Comment"),"UTF-32")));}catch(Exception e){}
		            	
		            	int brojac=0;
		            	//pronaði kome pripada ovaj temp
		            	for (int j=0;j<t.size();j++)
		            	{
		            		if (brojac==0)
		            			{
		            			if (t.get(j).lista.get(0).getIdDevice()==tek.getIdDevice())
		            			{t.get(j).lista.add(tek);brojac++;}
		            			}
		            	}
		            	//ako nije nasao nijednoj kome pripada znaci da nije unesen nijednom dosad, i treba ga unijeti
		            	if (brojac==0) {History z=new History(); z.lista.add(tek); t.add(z);}
		            	brojac=0;        		
		     }
		}catch(Exception e){System.out.print(e.getMessage());}
		finally{
			CloseConnection();
		}
		return t;	
	}	
	@Path("/search")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> SearchDb(JSONObject id) throws SQLException{
		List<String>t= new ArrayList<String>();
		Temp te=new Temp();
		try{
			String mystring=(String)id.get("name");
			// da maknemo ð dž š è æ jer se ne moze pretrazivati po njima
			mystring=BosnianEncode(mystring);
			
			CreateConnection();
			mystring="%"+mystring+"%";
			String newState="SELECT IdDevice,Name FROM Device WHERE Name LIKE ?";
			
			
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, mystring);
			ResultSet rs=query.executeQuery();
         	t=new ArrayList<String>();
			if (rs==null){t.add("Ne postoji ureðaj sa tim imenom");return t;}
         	while (rs.next()){
					
		            	t.add(BosnianDecode(new String(rs.getBytes("Name"),"UTF-32")));
		            }

		}catch(Exception e){t.add(e.getMessage());}
		finally{
			CloseConnection();
		}
		//System.out.print(t.toString());
		return t;		
	}
	@Path("/searchadmin")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> SearchDbAdmin(JSONObject id) throws SQLException{
		List<String>t= new ArrayList<String>();
		Temp te=new Temp();
		try{
			String mystring=(String)id.get("name");
			String myid=(String)id.get("id");
			// da maknemo ð dž š è æ jer se ne moze pretrazivati po njima
			mystring=BosnianEncode(mystring);
			
			CreateConnection();
			mystring="%"+mystring+"%";
			myid="%"+myid+"%";
			String newState="SELECT Name FROM Device WHERE Name LIKE ? AND RegistratedBy LIKE ?";
			
			
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, mystring);
			query.setString(2, myid);
			ResultSet rs=query.executeQuery();
         	t=new ArrayList<String>();
			if (rs==null){t.add("Ne postoji ureðaj sa tim imenom");return t;}
         	while (rs.next()){
					
		            	t.add(BosnianDecode(new String(rs.getBytes("Name"),"UTF-32")));
		            }

		}catch(Exception e){t.add(e.getMessage());}
		finally{
			CloseConnection();
		}
		//System.out.print(t.toString());
		return t;		
	}


	@Path("/registerdevice")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void RegisterDevice(JSONObject id) throws SQLException{
		String s="Uspjesno";
		Device te=new Device();
		try{
			te.setName(BosnianEncode(id.get("name").toString()));
			te.setUsername(BosnianEncode(id.get("username").toString()));
			te.setRegistratedby((int)id.get("id"));
			te.setPassword(BosnianEncode(id.get("password").toString()));
			te.setSerialNumber(BosnianEncode(id.get("serialname").toString()));
			te.setType(BosnianEncode(id.get("type").toString()));
			
			CreateConnection();
			
			String newState="SELECT Count(*) FROM Device WHERE RegistratedBy= ?";
			PreparedStatement query=connection.prepareStatement(newState);
			query.setInt(1, te.getRegistratedby());
			ResultSet rs=query.executeQuery();
			int a=0;
			while (rs.next()){a=rs.getInt("Count(*)");}
			a++;
			te.setOrdinalNumber(a);
			newState=new String("INSERT INTO Device (RegistratedBy,Username,Password,SerialName,Name,OrdinalNumber,Type,RegistrationDate,RegistrationTime) VALUES (?,?,?,?,?,?,?,CURDATE(),CURTIME()) ");
			PreparedStatement querya=connection.prepareStatement(newState);
			querya.setInt(1, te.getRegistratedby());
			querya.setString(2, te.getUsername());
			querya.setString(3, te.getPassword());
			querya.setString(4, te.getSerialNumber());
			querya.setString(5, te.getName());
			querya.setInt(6, te.getOrdinalNumber());
			querya.setString(7, te.getType());
			int rse=querya.executeUpdate();
			
		}catch(Exception e){s=(e.getMessage());}
		finally{
			CloseConnection();
		}
		
	}
	
	@Path("/register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public void Register(JSONObject id) throws SQLException{
		String s="Uspjesno";
		Admin te=new Admin();
		try{
			// da maknemo ð dž š è æ jer se ne moze pretrazivati po njima
			te.setFirstName(BosnianEncode(id.get("firstname").toString()));
			te.setLastName(BosnianEncode(id.get("lastname").toString()));
			te.setAbout(BosnianEncode(id.get("about").toString()));
			te.setCompanyName(BosnianEncode(id.get("companyname").toString()));
			te.setPassword(BosnianEncode(id.get("password").toString()));
			te.setUsername(BosnianEncode(id.get("username").toString()));

			CreateConnection();
			String newState="INSERT INTO Admin (Username,FirstName,LastName,RegistrationDate,RegistrationTime,CompanyName,Password,About) VALUES (?,?,?,CURDATE(),CURTIME(),?,?,?) ";
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, te.getUsername());
			query.setString(2, te.getFirstName());
			query.setString(3, te.getLastName());
			query.setString(4, te.getCompanyName());
			query.setString(5, te.getPassword());
			query.setString(6, te.getAbout());
			int rs=query.executeUpdate();
		}catch(Exception e){s=(e.getMessage());}
		finally{
			CloseConnection();
		}
	}
	
	*/
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion(){
		return "<p>Version calling:</p>"+api_version;
	}
	/*
	
	public void CreateConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		url = "jdbc:mysql://localhost/TrackerDatabase?useEncoding=true&setCharset=UTF-32";
        user = "root";
        password = "";
        connection = DriverManager.getConnection(url, user, password);
	}
	public void CloseConnection() throws SQLException{
		if (connection != null)
        {
        	 //zatvaranje konekcije s bazom
        	connection.close();
        	connection=null;
        }
	}
	

@Path("/clean")
@GET
public void CleanTemp() throws SQLException{		
		String name=new String("Sutjeska-Vogosscca");
		try{
			CreateConnection();
			String newState="DELETE FROM Temp WHERE Temp.Name=?";
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, name);
			int rs=query.executeUpdate();
			newState="DELETE FROM History WHERE History.Name=?";
			query=connection.prepareStatement(newState);
			query.setString(1, name);
			rs=query.executeUpdate();

		}catch(Exception e){System.out.print(e.getMessage());}
		finally{
			CloseConnection();
		}
		
	}

@Path("/clean3")
@GET
public void CleanTempThree() throws SQLException{		
		
		String name1=new String("Sutjeska-Vogosscca");
		String name2=new String("Sutjeska-Ilijass");
		String name3=new String("Sutjeska-Nahorevo");
		try{
			CreateConnection();
			String newState="DELETE FROM Temp WHERE Temp.Name=?";
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, name1);
			int rs=query.executeUpdate();
			newState="DELETE FROM History WHERE History.Name=?";
			query=connection.prepareStatement(newState);
			query.setString(1, name1);
			rs=query.executeUpdate();
			
			newState="DELETE FROM Temp WHERE Temp.Name=?";
			query=connection.prepareStatement(newState);
			query.setString(1, name2);
			rs=query.executeUpdate();
			newState="DELETE FROM History WHERE History.Name=?";
			query=connection.prepareStatement(newState);
			query.setString(1, name2);
			rs=query.executeUpdate();
			
			newState="DELETE FROM Temp WHERE Temp.Name=?";
			query=connection.prepareStatement(newState);
			query.setString(1, name3);
			rs=query.executeUpdate();
			newState="DELETE FROM History WHERE History.Name=?";
			query=connection.prepareStatement(newState);
			query.setString(1, name3);
			rs=query.executeUpdate();

		}catch(Exception e){System.out.print(e.getMessage());}
		finally{
			CloseConnection();
		}
		
	}
@Path("/automaticsimulation")
@GET
public void Simulation() throws SQLException, InterruptedException{
		String name=new String("Sutjeska-Vogosscca");
		double latl= 43.85;
		double longl=18.33;
		for(int i=1;i<30;i++)
		{
		try{
			
			latl+=((double)i)/10000;
			longl+=((double)i)/10000;
			CreateConnection();
			String newState="INSERT INTO Temp (Temp.idTemp,Temp.DeviceId, Temp.UserId, Temp.Name, Temp.OrdinalNumber, Temp.Lat, Temp.Long, Temp.Time, Temp.Date, Temp.Comment, Temp.Speed) VALUES(74,4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE Temp.Lat=?, Temp.Long=?, Temp.Time=CURTIME(), Temp.Date=CURDATE(), Temp.Speed=?";
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, name);
			query.setDouble(2, latl);
			query.setDouble(3, longl);
			query.setDouble(4, i);
			query.setDouble(5, latl);
			query.setDouble(6, longl);
			query.setDouble(7, i);
			int rs=query.executeUpdate();
			//ubaciti upis u history
			newState="INSERT INTO History (History.DeviceId, History.UserId, History.Name, History.OrdinalNumber, History.Lat, History.Long, History.Time, History.Date, History.Comment, History.Speed) VALUES(4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE History.Lat=?, History.Long=?, History.Time=CURTIME(), History.Date=CURDATE(), History.Speed=?";
			query=connection.prepareStatement(newState);
			query.setString(1, name);
			query.setDouble(2, latl);
			query.setDouble(3, longl);
			query.setDouble(4, i);
			query.setDouble(5, latl);
			query.setDouble(6, longl);
			query.setDouble(7, i);
			 rs=query.executeUpdate();

		}catch(Exception e){System.out.print(e.getMessage());}
		finally{
			CloseConnection();
		}
		
		Thread.sleep(1000);
		}
		
}


@Path("/automaticsimulation3")
@GET
public void SimulationThree() throws SQLException, InterruptedException{
		String name1=new String("Sutjeska-Vogosscca");
		String name2=new String("Sutjeska-Ilijass");
		String name3=new String("Sutjeska-Nahorevo");
		
		double latl1= 43.85;
		double longl1=18.33;
		double latl2= 43.75;
		double longl2=18.43;
		double latl3= 43.65;
		double longl3=18.53;
		for(int i=1;i<30;i++)
		{
		try{
			
			latl1+=((double)i)/10000;
			longl1+=((double)i)/10000;
			latl2+=((double)i)/10000;
			longl2+=((double)i)/10000;
			latl3+=((double)i)/10000;
			longl3+=((double)i)/10000;
			
			CreateConnection();
			//Sutjeska-Vogosscca
			String newState="INSERT INTO Temp (Temp.idTemp,Temp.DeviceId, Temp.UserId, Temp.Name, Temp.OrdinalNumber, Temp.Lat, Temp.Long, Temp.Time, Temp.Date, Temp.Comment, Temp.Speed) VALUES(74,4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE Temp.Lat=?, Temp.Long=?, Temp.Time=CURTIME(), Temp.Date=CURDATE(), Temp.Speed=?";
			PreparedStatement query=connection.prepareStatement(newState);
			query.setString(1, name1);
			query.setDouble(2, latl1);
			query.setDouble(3, longl1);
			query.setDouble(4, i);
			query.setDouble(5, latl1);
			query.setDouble(6, longl1);
			query.setDouble(7, i);
			int rs=query.executeUpdate();
			//ubaciti upis u history
			newState="INSERT INTO History (History.DeviceId, History.UserId, History.Name, History.OrdinalNumber, History.Lat, History.Long, History.Time, History.Date, History.Comment, History.Speed) VALUES(4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE History.Lat=?, History.Long=?, History.Time=CURTIME(), History.Date=CURDATE(), History.Speed=?";
			query=connection.prepareStatement(newState);
			query.setString(1, name1);
			query.setDouble(2, latl1);
			query.setDouble(3, longl1);
			query.setDouble(4, i);
			query.setDouble(5, latl1);
			query.setDouble(6, longl1);
			query.setDouble(7, i);
			 rs=query.executeUpdate();
			 //Sutjeska-Ilijass
				newState="INSERT INTO Temp (Temp.idTemp,Temp.DeviceId, Temp.UserId, Temp.Name, Temp.OrdinalNumber, Temp.Lat, Temp.Long, Temp.Time, Temp.Date, Temp.Comment, Temp.Speed) VALUES(74,4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE Temp.Lat=?, Temp.Long=?, Temp.Time=CURTIME(), Temp.Date=CURDATE(), Temp.Speed=?";
				 query=connection.prepareStatement(newState);
				query.setString(1, name2);
				query.setDouble(2, latl2);
				query.setDouble(3, longl2);
				query.setDouble(4, i);
				query.setDouble(5, latl2);
				query.setDouble(6, longl2);
				query.setDouble(7, i);
				rs=query.executeUpdate();
				//ubaciti upis u history
				newState="INSERT INTO History (History.DeviceId, History.UserId, History.Name, History.OrdinalNumber, History.Lat, History.Long, History.Time, History.Date, History.Comment, History.Speed) VALUES(4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE History.Lat=?, History.Long=?, History.Time=CURTIME(), History.Date=CURDATE(), History.Speed=?";
				query=connection.prepareStatement(newState);
				query.setString(1, name2);
				query.setDouble(2, latl2);
				query.setDouble(3, longl2);
				query.setDouble(4, i);
				query.setDouble(5, latl2);
				query.setDouble(6, longl2);
				query.setDouble(7, i);
				 rs=query.executeUpdate();
				//Sutjeska-Nahorevo
					newState="INSERT INTO Temp (Temp.idTemp,Temp.DeviceId, Temp.UserId, Temp.Name, Temp.OrdinalNumber, Temp.Lat, Temp.Long, Temp.Time, Temp.Date, Temp.Comment, Temp.Speed) VALUES(74,4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE Temp.Lat=?, Temp.Long=?, Temp.Time=CURTIME(), Temp.Date=CURDATE(), Temp.Speed=?";
					 query=connection.prepareStatement(newState);
					query.setString(1, name3);
					query.setDouble(2, latl3);
					query.setDouble(3, longl3);
					query.setDouble(4, i);
					query.setDouble(5, latl3);
					query.setDouble(6, longl3);
					query.setDouble(7, i);
					rs=query.executeUpdate();
					//ubaciti upis u history
					newState="INSERT INTO History (History.DeviceId, History.UserId, History.Name, History.OrdinalNumber, History.Lat, History.Long, History.Time, History.Date, History.Comment, History.Speed) VALUES(4, 1,?,2,?,?, CURTIME(), CURDATE(),\"Ok\",?) ON DUPLICATE KEY UPDATE History.Lat=?, History.Long=?, History.Time=CURTIME(), History.Date=CURDATE(), History.Speed=?";
					query=connection.prepareStatement(newState);
					query.setString(1, name3);
					query.setDouble(2, latl3);
					query.setDouble(3, longl3);
					query.setDouble(4, i);
					query.setDouble(5, latl3);
					query.setDouble(6, longl3);
					query.setDouble(7, i);
					 rs=query.executeUpdate();
		}catch(Exception e){System.out.print(e.getMessage());}
		finally{
			CloseConnection();
		}
		
		Thread.sleep(1000);
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
*/
}


