package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Session;
import models.SessionUser;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.URLUtils;

public class NotificationController extends Controller {
	
	private String title = null;
	private String body = null;
	private List<User> excluded = null;
	
	public Result saveToken(String deviceId, String token) {
		User user = User.find.where().eq("device_id", deviceId).findUnique();
		if(user == null) {
			user = new User();
			user.name = "auto";
			user.deviceId = deviceId;
			user.token = token;
			user.save();
		} else {
			if(user.token != token) {
				user.token = token;
				user.update();
			} else {
				// do nothing
			}
		}
		
		return ok();
	}
	
	public Result sendNotification() {
		this.send();
		
		return ok();
	}
	
	public Result receiveNotification() {
		DynamicForm form = Form.form().bindFromRequest();
		String message = form.get("message");
		System.out.println("Notyfikacja: " + message);
		
		this.title = "Sync";
		this.body = message;
		
		this.send();
		
		return ok(message);
	}
	
	private void send() {
		String googleUrl = "https://fcm.googleapis.com/fcm/send";
		Session session = Session.find.byId(new Long(1));
		for(SessionUser sUser : session.users) {

//			if(!this.excluded.contains(sUser.user)) {
				User user = sUser.user;
				try {
					String token = user.token;
					Map<String, String> params = new HashMap<String, String>();
					params.put("to", token);

					System.out.println("TOKEN: " + token);

					if(this.title != null) {
						params.put("title", this.title);
					}
					if(this.body != null) {
						params.put("body", this.body);
					}
					params.put("task_type", "ACTIVITY");
					params.put("structure", "1");
					params.put("session", "1");

					System.out.println("TITLE: " + this.title);
					System.out.println("BODY: " + this.body);

					URLUtils.postRequestToGcm(googleUrl, null, params);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}
		}
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return this.body;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setExcluded(List<User> excluded) {
		this.excluded = excluded;
	}

	public List<User> getExcluded() {
		return this.excluded;
	}
}
