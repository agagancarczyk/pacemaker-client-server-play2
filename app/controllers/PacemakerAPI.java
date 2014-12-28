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
  public static Result activities (Long userId)
  {  
    User p = User.findById(userId);
    return ok(renderActivity(p.activities));
  }

  public static Result createActivity (Long userId)
  { 
    User    user      = User.findById(userId);
    Activity activity = renderActivity(request().body().asJson().toString());  

    user.activities.add(activity);
    user.save();

    return ok(renderActivity(activity));
  }

  public static Result activity (Long userId, Long activityId)
  {  
    User    user      = User.findById(userId);
    Activity activity = Activity.findById(activityId);

    if (activity == null)
    {
      return notFound();
    }
    else
    {
      return user.activities.contains(activity)? ok(renderActivity(activity)): badRequest();
    }
  }  

  public static Result deleteActivity (Long userId, Long activityId)
  {  
    User    user      = User.findById(userId);
    Activity activity = Activity.findById(activityId);
    if (activity == null)
    {
      return notFound();
    }
    else
    {
      if (user.activities.contains(activity))
      {
        user.activities.remove(activity);
        activity.delete();
        user.save();
        return ok();
      }
      else
      {
        return badRequest();
      }

    }
  }  

  public static Result updateActivity (Long userId, Long activityId)
  {
    User    user      = User.findById(userId);
    Activity activity = Activity.findById(activityId);
    if (activity == null)
    {
      return notFound();
    }
    else
    {
      if (user.activities.contains(activity))
      {
        Activity updatedActivity = renderActivity(request().body().asJson().toString());
        activity.distance = updatedActivity.distance;
        activity.location = updatedActivity.location;
        activity.type     = updatedActivity.type;

        activity.save();
        return ok(renderActivity(updatedActivity));
      }
      else
      {
        return badRequest();
      }
    }
  }   
}