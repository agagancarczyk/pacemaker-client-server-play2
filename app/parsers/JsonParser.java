package parsers;

import java.util.ArrayList;
import java.util.List;

import models.Activity;
import models.User;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class JsonParser
{
	private static JSONSerializer  userSerializer = new JSONSerializer().exclude("class");
	private static JSONSerializer  activitySerializer = new JSONSerializer().exclude("class");

	//User
	public static User renderUser(String json)
	{
	  return new JSONDeserializer<User>().deserialize(json, User.class); 
	}

	public static String renderUser(Object obj)
	{
	  return userSerializer.serialize(obj);
	}

	public static List<User> renderUsers(String json)
	{
	  return new JSONDeserializer<ArrayList<User>>().use("values", User.class).deserialize(json);
	}   
	
	//Activity
	public static Activity renderActivity(String json)
	  {
	    Activity activity = new JSONDeserializer<Activity>().deserialize(json,   Activity.class);
	    return activity;
	  }

	  public static String renderActivity(Object obj)
	  {
	    return activitySerializer.serialize(obj);
	  }

	  public static  List<Activity> renderActivities (String json)
	  {
	    return new JSONDeserializer<ArrayList<Activity>>().use("values", Activity.class).deserialize(json);
	  }  
}