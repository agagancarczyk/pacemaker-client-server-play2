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
    
    /*
     * Method allows to render a new activity page.
     */
    public static Result newActivity()
    {
  	   User user = Accounts.getLoggedInUser();
       return ok(postactivity.render(user));
    }
    
    /*
     * Method allows render users most recent activity.
     * 
     * @param userId 
     *          Long
     */
	public static Result activities(Long userId)
    {
	  User user = Accounts.getLoggedInUser();
	  
	  return ok(activities.render(user, getLastActivity(user)));
	}
	 
	/*
	 * Method allows user to create/upload a new activity.
	 * 
	 * @param userId 
	 *          Long
	 */
	public static Result uploadActivity(Long userId)
	{
	  User user = Accounts.getLoggedInUser();
	    	    	
	  Form<Activity> boundForm = activityForm.bindFromRequest();
	  System.out.println(boundForm.toString());
	  Activity formActivity = boundForm.get();
	        
	  user.activities.add(formActivity); 
	        
	  user.save();
	  return ok(activities.render(user, getLastActivity(user)));
	}
	
	 /*
	  * Method allows show users activity.
	  * 
	  * @param userId 
	  *          Long
	  * @param activityId
	  *          Long
	  */
	public static Result showActivity(Long userId, Long activityId)
    {
	  User user = Accounts.getLoggedInUser();
	  Activity act = Activity.findById(activityId);
	  return ok(activities.render(user, act));
	}
	
	 /*
	  * Method allows to get last activity
	  * 
	  * @param user 
	  *          User
	  */
	private static Activity getLastActivity(User user)
	{
		Activity act = null;
		  if (user.activities.size()>0){
			  act = user.activities.get(user.activities.size()-1);
		  }
		return act;
	}
    
}
