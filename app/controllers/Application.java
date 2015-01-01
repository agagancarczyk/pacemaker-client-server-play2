package controllers;

import java.util.List;

import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	 List<User> users = User.findAll();
    	 return ok(index.render(users));
    }
}