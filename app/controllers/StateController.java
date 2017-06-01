package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Marker;
import models.Session;
import models.Structure;
import models.SyncHistory;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class StateController extends Controller {

	public Result sync() {
		DynamicForm form = Form.form().bindFromRequest();

		if (form.data().size() == 0) {
			return badRequest("Expceting some data");
		} else {
			String activityType = form.get("activity");
			Long structureId = new Long(form.get("structure"));
			Long sessionId = new Long(form.get("session"));

			Structure structure = Structure.find.byId(structureId);
			Session session = Session.find.byId(sessionId);

			SyncHistory history = new SyncHistory();
			history.session = session;
			history.structure = structure;
			history.active = true;
			history.save();

			NotificationController nc = new NotificationController();
			nc.setTitle("sync aktywności");
			nc.setBody("sync aktywności");
			nc.sendNotification();
		}

		return ok();
	}
	
}
