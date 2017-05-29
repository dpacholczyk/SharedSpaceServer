package controllers;

import java.util.List;

import models.Marker;
import models.Session;
import models.SessionUser;
import models.Structure;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.creator;
import views.html.edit;

public class SessionController extends Controller {
	
	public Result creator() {
        return ok(views.html.creator.render());
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
			 int elementsCount = Integer.parseInt(form.get("ElementsCount"));
			 
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

			 if(elementsCount > 0) {
				 for(int i = 1; i <= elementsCount; i++) {
					 String patternNameKey = "PatternName_" + i;
					 String objectDefinitionKey = "ObjectDefinition_" + i;
					 String markerDefinitionKey = "MarkerDefinition_" + i;
					 String colorRedKey = "ColorRed_" + i;
					 String colorGreenKey = "ColorGreen_" + i;
					 String colorBlueKey = "ColorBlue_" + i;
					 String positionXKey = "PositionX_" + i;
					 String positionYKey = "PositionY_" + i;
					 
					 String patternName = form.get(patternNameKey);
					 String objectDefinition = form.get(objectDefinitionKey);
					 String markerDefinition = form.get(markerDefinitionKey);
					 float colorR = Float.parseFloat(form.get(colorRedKey));
					 float colorG = Float.parseFloat(form.get(colorGreenKey));
					 float colorB = Float.parseFloat(form.get(colorBlueKey));
					 int positionX = Integer.parseInt(form.get(positionXKey));
					 int positionY = Integer.parseInt(form.get(positionYKey));
					 
					 Structure structure = new Structure();
					 structure.definition = form.get(objectDefinitionKey);
					 structure.positionX = 0;
					 structure.positionY = 0;
					 structure.colorB = colorB;
					 structure.colorG = colorG;
					 structure.colorR = colorR;
					 structure.positionX = positionX;
					 structure.positionY = positionY;
					 
					 Marker marker = new Marker();
					 marker.fileName = patternName + ".patt";
					 marker.name = patternName;
					 marker.pattern = markerDefinition;
					 
					 structure.save();
					 marker.save();
					 
					 structure.marker = marker;
					 structure.update();
					 
					 marker.structure = structure;
					 marker.session = session;
					 marker.update();
				 }
			 }
			 
			 session.users.add(host);
			 session.update();
			 
		     return ok(views.html.create.render(session.id.intValue()));
		 }
	}
	
	public Result get(Long sessionId) {
		Session session = Session.find.byId(sessionId);
		
		if(session == null) {
			return badRequest("Session not found");
		} else {
			return ok(Json.toJson(session));
		}		
	}
	
	public Result getUsers(Long sessionId) {
		Session session = Session.find.byId(sessionId);
		
		if(session == null) {
			return badRequest("Session not found");
		}
		
		List<SessionUser> users = session.users;
		
		return ok(Json.toJson(users));
	}
	
	public Result join(String deviceId, Long sessionId) {
		User user = User.find.where().eq("device_id", deviceId).findUnique();		
		if(user == null) {
			return badRequest("User not found");
		}
		
		Session session = Session.find.byId(sessionId);		
		if(session == null) {
			return badRequest("Session not found");
		}
		
		SessionUser su = SessionUser.find.where().eq("user_id", user.id).findUnique();
		if(su == null) {
			su = new SessionUser();
			su.isHost = false;
			su.session = session;
			su.user = user;
			su.save();
		} else {
			su.session = session;
			su.update();
		}

		session.users.add(su);
		session.update();
		
		return ok();
	}
	
	public Result edit() {
		return ok(views.html.edit.render());
	}

	public Result editForm(long sessionId) {
		Session session = Session.find.byId(sessionId);

		return ok(views.html.editForm.render(session, session.getHost()));
	}

	public Result update() {
		return ok();
	}
}
