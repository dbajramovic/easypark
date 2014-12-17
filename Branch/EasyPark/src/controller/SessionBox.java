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
		System.out.println(usr);
		HttpSession custSession = request.getSession(true);
		custSession.setAttribute("username", usr.getString("username"));	
		custSession.setAttribute("id", usr.getString("id"));
		custSession.setAttribute("password", usr.getString("password"));
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
		custSession.removeAttribute("password");
	}
	
}


