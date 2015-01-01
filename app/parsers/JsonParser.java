package parsers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import play.Logger;
import models.Activity;
import models.Location;
import models.User;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import flexjson.*;

public class JsonParser
{
	private static JSONSerializer  userSerializer = new JSONSerializer().exclude("class");
	private static JSONSerializer  activitySerializer = new JSONSerializer().exclude("class");
    private static JSONSerializer  locationSerializer = new JSONSerializer().exclude("class");
	
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
		System.out.println(json);
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
	  
	//Location
    public static Location renderLocation(String json)
    {
	  Location location = new JSONDeserializer<Location>().deserialize(json,   Location.class);
      return location;
    }

    public static String renderLocation(Object obj)
    {
      return locationSerializer.serialize(obj);
    }
    
    public static  List<Location> renderLocations (String json)
    {
	  return new JSONDeserializer<ArrayList<Location>>().use("values", Location.class).deserialize(json);
	}  
}
