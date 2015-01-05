package controllers;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import models.Activity;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.reports;
import static parsers.JsonParser.*;


public class Reports extends Controller {

	public static Result reports(Long userId) {
		User user = Accounts.getLoggedInUser();

		return ok(reports.render(user));
	}

	public static Result distance(Long userId) {
		User user = Accounts.getLoggedInUser();
		List<Activity> activities = user.activities;
		Object[][] str = new Object[activities.size()][];
		for (int i = 0; i < activities.size(); i++) {
			Activity act = activities.get(i);
			str[i] = new Object[] { act.date.toString(),act.distance };
		}

		return ok(renderGraphData(str));
	}
	
	public static Result duration(Long userId) {
		User user = Accounts.getLoggedInUser();
		List<Activity> activities = user.activities;
		Object[][] str = new Object[activities.size()][];
		for (int i = 0; i < activities.size(); i++) {
			Activity act = activities.get(i);
			double duration = Double.parseDouble(act.duration.substring(2,act.duration.length()-1));
			duration /=60;
			str[i] = new Object[] { act.date.toString(),duration};
		}

		return ok(renderGraphData(str));
	}
}
