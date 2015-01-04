package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.reports;

public class Reports extends Controller {
	
	public static Result reports(Long userId)
    {
		User user = Accounts.getLoggedInUser();
   	    return ok(reports.render(user));
    }
    
}
