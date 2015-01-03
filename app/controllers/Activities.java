package controllers;

import models.Activity;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.activities;

public class Activities extends Controller {
	
    private static final Form<Activity> activityForm = Form.form(Activity.class);
	
	public static Result activities()
    {
	  return ok(activities.render());
	}
	    
	public static Result uploadActivity(Long userId, Long activityId)
	{
	  User user = User.findById(userId);
	    	    	
	  Form<Activity> boundForm = activityForm.bindFromRequest();
	  Activity activity = boundForm.get();
	        
	  user.activities.add(activity); 
	        
	  user.save();
	  return redirect ("user/activities");
	}    
}
