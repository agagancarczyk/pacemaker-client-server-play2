package controllers;

import models.Activity;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.activities;
import views.html.postactivity;

public class Activities extends Controller {
	
    private static final Form<Activity> activityForm = Form.form(Activity.class);
    
    public static Result newActivity()
    {
  	   User user = Accounts.getLoggedInUser();
       return ok(postactivity.render(user));
    }
    
	public static Result activities(Long userId)
    {
	  User user = Accounts.getLoggedInUser(); 
	  return ok(activities.render(user));
	}
	    
	public static Result uploadActivity(Long userId)
	{
	  User user = Accounts.getLoggedInUser();
	    	    	
	  Form<Activity> boundForm = activityForm.bindFromRequest();
	  System.out.println(boundForm.toString());
	  Activity formActivity = boundForm.get();
	        
	  user.activities.add(formActivity); 
	        
	  user.save();
	  return ok(activities.render(user));
	}
	
//	public static Result showRecentActivities()
//	{
//		User user = Accounts.getLoggedInUser(); 
//		    
//	}
}
