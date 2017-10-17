package controllers;

import models.Session;
import models.Structure;
import models.SyncHistory;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
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
			String deviceId = form.get("sender");
			String colorData = form.get("color");

			System.out.println("SYNC: " + activityType + " | " + structureId + " | " + sessionId + " | " + deviceId);

			Structure structure = Structure.find.byId(structureId);
			Session session = Session.find.byId(sessionId);
			User sender = User.findByDeviceId(deviceId);

			SyncHistory history = new SyncHistory();
			history.session = session;
			history.structure = structure;
			history.active = true;
			history.activityType = activityType;
			history.sender = sender;
//			history.save();

			List<User> exclude = new ArrayList<>();
			exclude.add(sender);

			NotificationController nc = new NotificationController();
			nc.setTitle("sync aktywności");
			nc.setBody("sync aktywności");
			nc.setExcluded(exclude);

			nc.addExtraParam("structure", structureId);
			nc.addExtraParam("session", sessionId);
			nc.addExtraParam("sender", deviceId);
			nc.addExtraParam("action_name", activityType);
			nc.addExtraParam("action", "ACTIVITY");
			nc.addExtraParam("color", colorData);

			nc.sendNotification(sessionId);
		}

		return ok();
	}
	
}
