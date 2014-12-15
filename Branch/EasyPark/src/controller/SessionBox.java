package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/session")
public class SessionBox {
	
	@Context private HttpServletRequest request;
	@Path("/insert")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void SetUserSession(JSONObject usr)
	{
		HttpSession custSession = request.getSession(true);
		custSession.setAttribute("username", "GAZDA");
		custSession.setAttribute("id", "1245");
		custSession.setAttribute("about", "Neki tekst o registrovanom korisniku");
		custSession.setAttribute("email", "admin");
	}
	//@Context private HttpServletRequest request2;
	@Path("/delete")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteSession(JSONObject usr) throws JSONException
	{
		//System.out.println("Zdravo");
		//if (usr.getString("valid").toString()=="true")
		//{
			
		HttpSession custSession = request.getSession(true);
		custSession.removeAttribute("username");
		custSession.removeAttribute("id");
		custSession.removeAttribute("about");
		custSession.removeAttribute("email");
		//}
	}
	
}


