package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import models.Fcm;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import utils.URLUtils;

public class NotificationController extends Controller {
	
	private String title = null;
	private String body = null;
	
	public Result saveToken(String token) {
		Fcm fcm = Fcm.find.byId(new Long(1));
		if(fcm == null) {
			fcm = new Fcm(token);
			fcm.save();
		} else {
			fcm.token = token;
			fcm.update();
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
		Fcm fcm = Fcm.find.byId(new Long(1));
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("to", fcm.token);
			
			if(this.title != null) {
				params.put("title", this.title);
			}
			if(this.body != null) {
				params.put("body", this.body);
			}
			
			URLUtils.postRequestToGcm(googleUrl, null, params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
