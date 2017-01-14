package controllers;

import java.util.List;

import models.Session;
import models.SessionUser;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.creator;

public class SessionController extends Controller {
	
	public Result creator() {
		
		return ok(creator.render());
	}
	
	public Result newSession() {
		return ok();
	}
	
	public Result test() {
		return ok("test");
	}
	
	public Result create() {
		 DynamicForm form = Form.form().bindFromRequest();

		 if (form.data().size() == 0) {
			 return badRequest("Expceting some data");
		 } else {
			 String deviceId = form.get("DeviceId");
			 String sessionName = form.get("SessionName");
			 
			 User user = User.findByDeviceId(deviceId);
			 if(user == null) {
				 System.out.println("USER NIE ISTNIEJE");
				 user = new User();
				 user.name = "auto";
				 user.deviceId = deviceId;
				 user.save();
			 } else {
				 System.out.println("ISTNIEJE: " + user.deviceId);
			 }
			 
			 
			 
			 Session session = new Session();
			 session.name = sessionName;
//			 session.users.add(host);
			 session.save();

			 SessionUser host = new SessionUser();
			 host.isHost = true;
			 host.user = user;
			 host.session = session;
			 host.save();
			 System.out.println("ISTNIEJE: " + host.id + " " + host.user.name + " " + host.user.deviceId);
			 
			 session.users.add(host);
			 session.update();
			 
		     return ok(session.id.toString());
		 }
	}
	
	public Result get(Long sessionId) {
		Session session = Session.find.byId(sessionId);
		
		return ok(Json.toJson(session));
	}
	
	public Result getUsers(Long sessionId) {
		Session session = Session.find.byId(sessionId);
		List<SessionUser> users = session.users;
		
		return ok(Json.toJson(users));
	}
	
	public Result join(Long userId, Long sessionId) {
		
		
		return ok();
	}
	
}
