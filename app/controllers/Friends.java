package controllers;

import java.util.List;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.friends;

public class Friends extends Controller {
	
	public static Result friends(Long userId)
    {
		User user = Accounts.getLoggedInUser(); 
    	List<User> users = User.findAll();
   	    return ok(friends.render(user, users));
    }

}
