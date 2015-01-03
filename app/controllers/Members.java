package controllers;

import java.util.List;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.friends;

public class Members extends Controller {
	
	public static Result friends()
    {
    	List<User> users = User.findAll();
   	    return ok(friends.render(users));
    }

}
