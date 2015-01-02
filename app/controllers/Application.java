package controllers;

import java.util.List;

import play.data.Form;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {
	
    private static final Form<User> userForm = Form.form(User.class);
    private static final Form<Activity> activityForm = Form.form(Activity.class);

    public static Result index() 
    {
    	return ok(index.render());
    }
    
    public static Result friends()
    {
    	List<User> users = User.findAll();
   	    return ok(friends.render(users));
    }
    
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
    
    public static Result reports()
    {
   	    return ok(reports.render());
    }
    
    public static Result showUser(Long id)
    {
      User user =  User.findById(id);
      return ok(showuser.render(user));
    }
}