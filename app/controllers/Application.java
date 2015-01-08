package controllers;

import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {
	
	 /*
	  * Method allows render users index page.
	  * 
	  * @param userId 
	  *          Long
	  */
    public static Result index(Long userId) 
    {
    	User user = Accounts.getLoggedInUser(); 
    	return ok(index.render(user));
    }
}