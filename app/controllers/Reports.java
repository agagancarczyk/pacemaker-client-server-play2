package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Activity;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import static parsers.JsonParser.*;
import views.html.reports;

public class Reports extends Controller {

	 /*
     * Method allows render users reports.
     * 
     * @param userId 
     *          Long
     * @param category
     *          String
     */
	public static Result reports(Long userId, String category) {
		User user = Accounts.getLoggedInUser();
		List<Activity> activity = user.activities;

		return ok(reports.render(user, activity, category));
	}

	 /*
     * Method allows to create a distance graph.
     * 
     * @param userId 
     *          Long
     * @param category
     *          String
     */
	public static Result distance(Long userId, String category) {
		User user = Accounts.getLoggedInUser();
		List<Activity> activities = user.activities;
		Object[][] str = new Object[activities.size()][];
		for (int i = 0; i < activities.size(); i++) {
			Activity act = activities.get(i);
			if (act.category.equalsIgnoreCase(category)
					|| category.equalsIgnoreCase("all")) {
				str[i] = new Object[] { act.date.toString(), act.distance };
			}
		}

		return ok(renderGraphData(removeNull(str)));
	}

	 /*
     * Method allows to create a duration graph.
     * 
     * @param userId 
     *          Long
     * @param category
     *          String
     */
	public static Result duration(Long userId, String category) {
		User user = Accounts.getLoggedInUser();
		List<Activity> activities = user.activities;
		Object[][] str = new Object[activities.size()][];
		for (int i = 0; i < activities.size(); i++) {
			Activity act = activities.get(i);
			double duration = Double.parseDouble(act.duration.substring(2,
					act.duration.length() - 1));
			duration /= 60;
			if (act.category.equalsIgnoreCase(category)
					|| category.equalsIgnoreCase("all")) {
				str[i] = new Object[] { act.date.toString(), duration };
			}
		}

		return ok(renderGraphData(removeNull(str)));
	}

	 /*
     * Method allows to create a calories burned graph.
     * 
     * @param userId 
     *          Long
     * @param category
     *          String
     */
	public static Result caloriesBurned(Long userId, String category) {
		User user = Accounts.getLoggedInUser();
		List<Activity> activities = user.activities;
		Object[][] str = new Object[activities.size()][];
		for (int i = 0; i < activities.size(); i++) {
			Activity act = activities.get(i);
			if (act.category.equalsIgnoreCase(category)
					|| category.equalsIgnoreCase("all")) {
				str[i] = new Object[] { act.id, act.caloriesBurned };
			}
		}
		return ok(renderGraphData(removeNull(str)));
	}

	 /*
     * Method removes null entries from the parent array.
     * @return array of arrays in which there is no null values.
     */
	private static Object[][] removeNull(Object[][] a) {
		System.out.println(a.toString());

		List<Object[]> arr = new ArrayList<>();
		for (Object[] o : a) {
			if (o != null)
				arr.add(o);
		}
		Object[][] cleanArr = new Object[arr.size()][1];
		for (int i = 0; i < cleanArr.length; i++) {
			cleanArr[i] = arr.get(i);
		}

		return cleanArr;
	}
}
