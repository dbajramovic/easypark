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
	public void SetUserSession(JSONObject usr) throws JSONException
	{
		HttpSession custSession = request.getSession(true);
		custSession.setAttribute("username", usr.getString("_email"));	
		custSession.setAttribute("id", usr.getString("_personID"));
		custSession.setAttribute("firstName", usr.getString("_firstname"));
		custSession.setAttribute("lastName", usr.getString("_lastname"));
		custSession.setAttribute("address", usr.getString("_address"));
		custSession.setAttribute("companyName", usr.getString("_companyName"));
		custSession.setAttribute("phoneNumber", usr.getString("_phonenumber"));
		custSession.setAttribute("type", usr.getString("_type"));
	}
	//@Context private HttpServletRequest request2;
	@Path("/delete")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteSession(JSONObject usr) throws JSONException
	{
		HttpSession custSession = request.getSession(true);
		custSession.removeAttribute("username");	
		custSession.removeAttribute("id");
		custSession.removeAttribute("firstName");
		custSession.removeAttribute("lastName");
		custSession.removeAttribute("address");
		custSession.removeAttribute("companyName");
		custSession.removeAttribute("phoneNumber");
		custSession.removeAttribute("type");
	}
	
}


