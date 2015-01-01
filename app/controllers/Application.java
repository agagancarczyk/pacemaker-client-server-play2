package controllers;

import java.util.List;

import play.data.Form;
import play.mvc.*;
import models.*;
import views.html.*;

public class Application extends Controller {
	
    private static final Form<User> userForm = Form.form(User.class);


    public static Result index() {
    	 List<User> users = User.findAll();
    	 return ok(index.render(users));
    }
    
    public static Result login()
    {
      return ok(login.render());
    }
    
    public static Result signup()
    {
      return ok(signup.render());
    }
    
    public static Result createUser()
    {
    	Form<User> boundForm = userForm.bindFromRequest();
        User user = boundForm.get();
        user.save();
        return redirect ("/");
    }
}