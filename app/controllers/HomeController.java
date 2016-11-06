package controllers;

import com.fasterxml.jackson.databind.node.ObjectNode;

import play.mvc.BodyParser;
import play.mvc.BodyParser.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	public static boolean status = false;
	
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
    	int test = 1;
        return ok(index.render("Your new application is ready."));
    }
    
    public Result changeStatus() {
    	if(status == false) {
    		status = true;
    	} else {
    		status = false;
    	}
    	
    	return ok();
    }
    
    public Result checkStatus() {
    	ObjectNode result = play.libs.Json.newObject();
    	result.put("status", status);
    	
    	return ok(result);
    }

}
