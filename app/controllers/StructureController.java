package controllers;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import models.Structure;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class StructureController extends Controller {

	public Result structures() {
		List<Structure> structures = Structure.find.all();
		
		JsonNode structuresJson = Json.toJson(structures);
    	
    	return ok(structuresJson);
	}
	
	public Result structureForMarker(int markerId) {
		Structure structure = Structure.find.where().eq("marker_id", markerId).findUnique();
		
		JsonNode structureJson = Json.toJson(structure);
		
		return ok(structureJson);
	}
	
}
