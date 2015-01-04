package controllers;

import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {
	
    public static Result index(Long userId) 
    {
    	User user = Accounts.getLoggedInUser(); 
    	return ok(index.render(user));
    }
   
   // public static Result showUser(Long id)
   // {
      //User user =  User.findById(id);
      //return ok(showuser.render(user));
   // }
    
    
    
}