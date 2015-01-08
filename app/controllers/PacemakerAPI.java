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
		user.save();
		return ok(renderUser(user));
	}

	public static Result deleteUser(String token, Long id, Long userId) {
		User secureUsr = User.findById(id);
		if (token.equals(secureUsr.token)) {
			Result result = notFound();
			User user = User.findById(userId);
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
		User user = User.findById(id);
		if (user == null)
			return notFound();

		if (token.equals(user.token)) {
			User updatedUser = renderUser(request().body().asJson().toString());
			user.update(updatedUser);
			user.save();
			return ok(renderUser(user));
		} else {
			return forbidden();
		}
	}

	// Activities
	public static Result activities(String token, Long id, Long userId) {
		User secureUsr = User.findById(userId);
		if (secureUsr == null)
			return notFound();

		if (token.equals(secureUsr.token)) {
			User p = User.findById(userId);
			return ok(renderActivity(p.activities));
		} else {
			return forbidden();
		}
	}

	public static Result createActivity(String token, Long userId) {
		User secureUsr = User.findById(userId);
		if (token.equals(secureUsr.token)) {
			Activity activity = renderActivity(request().body().asJson().toString());
			secureUsr.activities.add(activity);
			secureUsr.save();
			return ok(renderActivity(activity));
		} else {
			return forbidden();
		}
	}

	public static Result activity(String token, Long id, Long userId, Long activityId) {
		User secureUsr = User.findById(id);
		if (secureUsr == null)
			return notFound();

		if (token.equals(secureUsr.token)) {
			User user = User.findById(userId);
			Activity activity = Activity.findById(activityId);

			if (activity == null) {
				return notFound();
			} else {
				return user.activities.contains(activity) ? ok(renderActivity(activity))
						: badRequest();
			}
		} else {
			return forbidden();
		}
	}

	public static Result deleteActivity(String token, Long userId, Long activityId) {
		User secureUsr = User.findById(userId);
		if (secureUsr == null) {
			return notFound();
		}

		if (token.equals(secureUsr.token)) {
			Activity activity = Activity.findById(activityId);
			if (activity == null) {
				return notFound();
			}

			if (secureUsr.activities.contains(activity)) {
				secureUsr.activities.remove(activity);
				activity.delete();
				secureUsr.save();
				return ok();
			} else {
				return badRequest();
			}
		} else {
			return forbidden();
		}
	}

	public static Result updateActivity(String token, Long userId, Long activityId) {
		User secureUsr = User.findById(userId);
		if (secureUsr == null)
			return notFound();

		if (token.equals(secureUsr.token)) {
			Activity activity = Activity.findById(activityId);
			if (activity == null) {
				return notFound();
			} else {
				if (secureUsr.activities.contains(activity)) {
					Activity updatedActivity = renderActivity(request().body().asJson().toString());
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
		} else {
			return forbidden();
		}
	}

	// Locations
	public static Result locations(String token, Long id, Long activityId) {
		User secureUsr = User.findById(id);
		if (secureUsr == null) {
			return notFound();
		}

		if (token.equals(secureUsr.token)) {
			Activity a = Activity.findById(activityId);
			if (a != null) {
				return ok(renderLocation(a.route));
			} else {
				return notFound();
			}
		} else {
			return forbidden();
		}
	}

	public static Result createLocation(String token, Long id, Long activityId) {
		User secureUsr = User.findById(id);
		if (secureUsr == null) {
			return notFound();
		}

		if (token.equals(secureUsr.token)) {
			Activity activity = Activity.findById(activityId);
			if (activity != null) {
				Location location = renderLocation(request().body().asJson().toString());
				activity.route.add(location);
				activity.save();
				return ok(renderLocation(location));
			} else {
				return notFound();
			}
		} else {
			return forbidden();
		}
	}

	public static Result location(String token, Long id, Long activityId, Long locationId) {
		User secureUsr = User.findById(id);
		if (secureUsr == null) {
			return notFound();
		}

		if (token.equals(secureUsr.token)) {
			Activity activity = Activity.findById(activityId);
			Location location = Location.findById(locationId);

			if (location == null || activity == null) {
				return notFound();
			} else {
				return activity.route.contains(location) ? ok(renderLocation(location))
						: badRequest();
			}
		} else {
			return forbidden();
		}
	}

	public static Result deleteLocation(String token, Long id, Long activityId, Long locationId) {
		User secureUsr = User.findById(id);
		if (secureUsr == null) {
			return notFound();
		}
		Activity activity = Activity.findById(activityId);
		Location location = Location.findById(locationId);
		if (location == null || activity == null) {
			return notFound();
		}

		if (token.equals(secureUsr.token) && secureUsr.activities.contains(activity)) {
			if (activity.route.contains(location)) {
				activity.route.remove(location);
				location.delete();
				activity.save();
				return ok();
			} else {
				return badRequest();
			}

		} else {
			return forbidden();
		}
	}

	public static Result updateLocation(String token, Long id, Long activityId, Long locationId) {
		User secureUsr = User.findById(id);
		if (secureUsr == null) {
			return notFound();
		}
		Activity activity = Activity.findById(activityId);
		Location location = Location.findById(locationId);

		if (activity == null || location == null) {
			return notFound();
		}
		if (token.equals(secureUsr.token) && secureUsr.activities.contains(activity)) {
			if (activity.route.contains(location)) {
				Location updatedLocation = renderLocation(request().body().asJson().toString());
				location.latitude = updatedLocation.latitude;
				location.longtitude = updatedLocation.longtitude;

				location.save();
				return ok(renderLocation(updatedLocation));
			} else {
				return badRequest();
			}
		} else {
			return forbidden();
		}
	}

	public static Result generateToken(String token, String email) {
		User user = User.findByEmail(email);
		user.generateAPItoken();
		return ok(user.token);
	}
	
	// Friendships
		public static Result friendships(String token, Long userId) {
			User secureUsr = User.findById(userId);
			if (secureUsr == null)
				return notFound();

			if (token.equals(secureUsr.token)) {
				return ok(renderFriendship(secureUsr.friendships));
			} else {
				return forbidden();
			}
		}

		public static Result createFriendship(String token, Long userId) {
			User secureUsr = User.findById(userId);
			if (token.equals(secureUsr.token)) {
				Friendship friendship = renderFriendship(request().body().asJson().toString());
				secureUsr.friendships.add(new Friendship(secureUsr, friendship.targetUser));
				secureUsr.save();
				return ok(renderFriendship(friendship));
			} else {
				return forbidden();
			}
		}

		public static Result friendship(String token, Long id, Long friendshipId) {
			User secureUsr = User.findById(id);
			if (secureUsr == null)
				return notFound();

			if (token.equals(secureUsr.token)) {
				Friendship friendship = Friendship.findById(friendshipId);

				if (friendship == null) {
					return notFound();
				} else {
					return secureUsr.friendships.contains(friendship) ? ok(renderActivity(friendship))
							: badRequest();
				}
			} else {
				return forbidden();
			}
		}

		public static Result deleteFriendship(String token, Long userId, Long friendshipId) {
			User secureUsr = User.findById(userId);
			if (secureUsr == null) {
				return notFound();
			}

			if (token.equals(secureUsr.token)) {
				Friendship friendship = Friendship.findById(friendshipId);
				if (friendship == null) {
					return notFound();
				}

				if (secureUsr.friendships.contains(friendship)) {
					secureUsr.friendships.remove(friendship);
					friendship.delete();
					secureUsr.save();
					return ok();
				} else {
					return badRequest();
				}
			} else {
				return forbidden();
			}
		}
		
}