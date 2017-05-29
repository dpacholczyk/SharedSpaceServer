package controllers;

import models.User;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class UserController extends Controller {
	
	public Result getUser(String deviceId) {
		User user = User.findByDeviceId(deviceId);
		
		if(user == null) {
			return badRequest("User not found");
		} else {
			return ok(Json.toJson(user));
		}
	}
	
}
