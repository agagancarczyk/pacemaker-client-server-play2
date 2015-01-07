package controllers;

import static parsers.JsonParser.*;
import play.mvc.*;

import java.util.*;

import ch.qos.logback.core.subst.Token;
import models.*;

public class PacemakerAPI extends Controller {

	// Users
	public static Result users(String token, Long userId) {
		User user = User.findById(userId);
		if (user.token != null && token.equals(user.token)) {
			List<User> users = User.findAll();
			return ok(renderUser(users));
		} else
			return forbidden();
	}

	public static Result user(String token, Long userId, Long id) {
		User secureUsr = User.findById(userId);
		if (token.equals(secureUsr.token)) {
			User user = User.findById(id);
			return user == null ? notFound() : ok(renderUser(user));
		} else
			return forbidden();
	}

	public static Result createUser() {
		User user = renderUser(request().body().asJson().toString());
		user.generateAPItoken();
		user.save();
		return ok(renderUser(user));
	}

	public static Result deleteUser(String token, Long id) {
        User secureUsr = User.findById(id);
        if (token.equals(secureUsr.token)) {
    		Result result = notFound();
        	User user = User.findById(id);
		if (user != null) {
			user.delete();
			result = ok();
		}
		return result;
        } else {
        	return forbidden();
        }
	}

	public static Result deleteAllUsers(String token, Long userId) {
		User user = User.findById(userId);
		if (token.equals(user.token)) {
			User.deleteAll();
			return ok();
		} else
			return forbidden();
	}

	public static Result updateUser(String token, Long id) {
		User secureUsr = User.findById(id);
		if (token.equals(secureUsr.token)) {
		Result result = notFound();
		User user = User.findById(id);
		if (user != null) {
			User updatedUser = renderUser(request().body().asJson().toString());
			user.update(updatedUser);
			user.save();
			result = ok(renderUser(user));
		}
		return result;
		} else {
			return forbidden();
		}
	}

	// Activities
	public static Result activities(String token, Long userId) {
		User secureUsr = User.findById(userId);
		if (token.equals(secureUsr.token)) {
		User p = User.findById(userId);
		return ok(renderActivity(p.activities));
		} else {
			return forbidden();
		}
	}

	public static Result createActivity(String token, Long userId) {
		User secureUsr = User.findById(userId);
		if (token.equals(secureUsr.token)){
		User user = User.findById(userId);
		Activity activity = renderActivity(request().body().asJson().toString());
		user.activities.add(activity);
		user.save();
		return ok(renderActivity(activity));
		}else{
			return forbidden();
		}
	}

	public static Result activity(String token, Long userId, Long activityId) {
		User secureUsr = User.findById(userId);
		if (token.equals(secureUsr.token)){
		  User user = User.findById(userId);
		  Activity activity = Activity.findById(activityId);

		  if (activity == null) {
			return notFound();
		  } else {
			return user.activities.contains(activity) ? ok(renderActivity(activity))
					: badRequest();
		  }
		}else{
			return forbidden();
		}
	}

	public static Result deleteActivity(String token, Long userId, Long activityId) {
		User secureUsr = User.findById(userId);
		if (token.equals(secureUsr.token)) {
		User user = User.findById(userId);
		Activity activity = Activity.findById(activityId);
		if (activity == null) {
			return notFound();
		} else {
			if (user.activities.contains(activity)) {
				user.activities.remove(activity);
				activity.delete();
				user.save();
				return ok();
			} else {
				return badRequest();
			}

		}
		}else{
			return forbidden();
		}
	}

	public static Result updateActivity(String token, Long userId, Long activityId) {
		User secureUsr = User.findById(userId);
		if (token.equals(secureUsr.token)) {
		User user = User.findById(userId);
		Activity activity = Activity.findById(activityId);
		if (activity == null) {
			return notFound();
		} else {
			if (user.activities.contains(activity)) {
				Activity updatedActivity = renderActivity(request().body()
						.asJson().toString());
				activity.distance = updatedActivity.distance;
				activity.location = updatedActivity.location;
				activity.category = updatedActivity.category;
				activity.duration = updatedActivity.duration;
				activity.date = updatedActivity.date;
				activity.averageSpeed = updatedActivity.averageSpeed;
				activity.caloriesBurned = updatedActivity.caloriesBurned;

				activity.save();
				return ok(renderActivity(updatedActivity));
			} else {
				return badRequest();
			}
		}
		}else{
			return forbidden();
		}
	}

	// Locations
	public static Result locations(String token, Long activityId) {	 
		User secureUsr = new User();
		if (token.equals(secureUsr.token)){
		  Activity a = Activity.findById(activityId);
		  return ok(renderLocation(a.route));
		}else{
			return forbidden();
		}
	}

	public static Result createLocation(String token, Long activityId) {
		User secureUsr = new User();
		if (token.equals(secureUsr.token)) {
		  Activity activity = Activity.findById(activityId);
		  Location location = renderLocation(request().body().asJson().toString());

		  activity.route.add(location);
		  activity.save();

		  return ok(renderLocation(location));
		}else{
			return forbidden();
		}
	}

	public static Result location(String token, Long activityId, Long locationId) {
		User secureUsr = new User();
		if (token.equals(secureUsr.token)){
		  Activity activity = Activity.findById(activityId);
		  Location location = Location.findById(locationId);

		  if (location == null) {
			return notFound();
		  } else {
			return activity.route.contains(location) ? ok(renderLocation(location))
					: badRequest();
		  }
		}else{
			return forbidden();
		}
	}

	public static Result deleteLocation(String token, Long activityId, Long locationId) {
		User secureUsr = new User();
		if (token.equals(secureUsr.token)){
		  Activity activity = Activity.findById(activityId);
		  Location location = Location.findById(locationId);
		  if (location == null) {
			return notFound();
		  } else {
			if (activity.route.contains(location)) {
				activity.route.remove(location);
				location.delete();
				activity.save();
				return ok();
			} else {
				return badRequest();
	      }
		}
		}else{
			return forbidden();
		}
	}

	public static Result updateLocation(String token, Long activityId, Long locationId) {
		User secureUsr = new User();
		if (token.equals(secureUsr.token)){
		Activity activity = Activity.findById(activityId);
		Location location = Location.findById(locationId);
		if (location == null) {
			return notFound();
		} else {
			if (activity.route.contains(location)) {
				Location updatedLocation = renderLocation(request().body()
						.asJson().toString());
				location.latitude = updatedLocation.latitude;
				location.longtitude = updatedLocation.longtitude;

				location.save();
				return ok(renderLocation(updatedLocation));
			} else {
				return badRequest();
			}
		}
	  }else{
		  return forbidden();
	  }
	}

	public static Result generateToken(String token, String email) {
		User user = User.findByEmail(email);
		user.generateAPItoken();
		return ok(user.token);
	}
}