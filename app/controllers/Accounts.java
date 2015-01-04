package controllers;

import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.login;
import views.html.signup;

public class Accounts extends Controller {
	
    private static final Form<User> userForm = Form.form(User.class);
    
    public static Result logout() {
 	   session().clear();
 	   return redirect ("/");
 	}
     
    public static Result login()
    {
       return ok(login.render());
    }
     
    public static Result signup()
    {
       return ok(signup.render());
    }
     
    public static Result authenticate () {
    	Form<User> boundForm = userForm.bindFromRequest();
    	System.out.println(boundForm.toString());
        User formUser = boundForm.get();
        User user = User.findByEmail(formUser.email);
        System.out.println(formUser);
    	        
        if ((user != null) && (user.checkPassword(formUser.password))) {
            System.out.println("Authentication successful");
            session().put("logged_in_userid", user.id.toString());
            return ok(index.render(user));
		
    	} else {
			System.out.println("Authentication failed");
			return ok(login.render());
		}
	}
    
    public static Result createUser()
    {
    	Form<User> boundForm = userForm.bindFromRequest();
        User user = boundForm.get();
        user.save();
        return redirect ("/");
    }
    
    public static User getLoggedInUser() {
		User user = null;
		if (session().containsKey("logged_in_userid")){
			String userId = session().get("logged_in_userid");
			user = User.findById(Long.parseLong(userId));
		} else {
			login();
		}
		return user;
	}
}
