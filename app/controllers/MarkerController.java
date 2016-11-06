package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.Marker;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class MarkerController extends Controller {

	public Result markers() {
		List<Marker> markers = Marker.find.all();
		
		JsonNode markersJson = Json.toJson(markers);
    	
    	return ok(markersJson);
	}
	
}
