package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.reports;

public class Reports extends Controller {
	
	public static Result reports()
    {
   	    return ok(reports.render());
    }
    
}
