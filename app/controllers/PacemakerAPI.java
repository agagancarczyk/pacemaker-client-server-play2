package controllers;

import static parsers.JsonParser.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class PacemakerAPI extends Controller
{  

  //Users
  public static Result  users()
  {
    List<User> users = User.findAll();
    return ok(renderUser(users));
  }

  public static Result user(Long id)
  {
    User user = User.findById(id);  
    return user==null? notFound() : ok(renderUser(user)); 
  }

  public static Result createUser()
  {
    User user = renderUser(request().body().asJson().toString());
    user.save();
    return ok(renderUser(user));
  }

  public static Result deleteUser(Long id)
  {
    Result result = notFound();
    User user = User.findById(id);
    if (user != null)
    {
      user.delete();
      result = ok();
    }
    return result;
  }

  public static Result deleteAllUsers()
  {
    User.deleteAll();
    return ok();
  }

  public static Result updateUser(Long id)
  {
    Result result = notFound();
    User user = User.findById(id);
    if (user != null)
    {
      User updatedUser = renderUser(request().body().asJson().toString());
      user.update(updatedUser);
      user.save();
      result = ok(renderUser(user));
    }
    return result;
  }
  
  //Activities
  public static Result  activities()
  {
    List<Activity> activities = Activity.findAll();
    return ok(renderActivity(activities));
  }

  public static Result activity(Long id)
  {
    Activity activity = Activity.findById(id);  
    return activity==null? notFound() : ok(renderActivity(activity)); 
  }

  public static Result createActivity()
  {
    Activity activity = renderActivity(request().body().asJson().toString());
    activity.save();
    return ok(renderActivity(activity));
  }

  public static Result deleteActivity(Long id)
  {
    Result result = notFound();
    Activity activity = Activity.findById(id);
    if (activity != null)
    {
      activity.delete();
      result = ok();
    }
    return result;
  }

  public static Result deleteAllActivities()
  {
    Activity.deleteAll();
    return ok();
  }

  public static Result updateActivity(Long id)
  {
    Result result = notFound();
    Activity activity = Activity.findById(id);
    if (activity != null)
    {
      Activity updatedActivity = renderActivity(request().body().asJson().toString());
      activity.update(updatedActivity);
      activity.save();
      result = ok(renderUser(activity));
    }
    return result;
  }
}