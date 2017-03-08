package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import play.mvc.Controller;
import play.mvc.Result;
import utils.URLUtils;

public class TestController extends Controller {
	public Result testMessage() {
		String googleUrl = "https://fcm.googleapis.com/fcm/send";
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("to", "AIzaSyALa2ASmkN_oQqH4E7bJU0dJWj3srk9SpI");
			URLUtils.postRequestToGcm(googleUrl, null, params);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ok("test");
	}
}
