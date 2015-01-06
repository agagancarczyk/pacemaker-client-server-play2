package parsers;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import models.Friendship;
import play.Logger;
import models.Activity;
import models.Location;
import models.User;
import flexjson.*;

public class JsonParser
{
	private static JSONSerializer  userSerializer = new JSONSerializer().exclude("class");
	private static JSONSerializer  activitySerializer = new JSONSerializer().exclude("class");
    private static JSONSerializer  locationSerializer = new JSONSerializer().exclude("class");
    private static JSONSerializer  graphDataSerializer = new JSONSerializer().exclude("class");
    private static JSONSerializer  friendshipSerializer = new JSONSerializer().exclude("class");
	
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
    
    public static String renderGraphData(Object obj){
    	return graphDataSerializer.serialize(obj);
    }
    
  //Friendship
    public static Friendship renderFriendship(String json)
    {
	  Friendship friendship = new JSONDeserializer<Friendship>().deserialize(json,   Friendship.class);
      return friendship;
    }

	public static String renderFriendship(Object obj)
    {
      return friendshipSerializer.serialize(obj);
	}
		    
	public static  List<Friendship> renderFriendships (String json)
	{
	  return new JSONDeserializer<ArrayList<Friendship>>().use("values", Friendship.class).deserialize(json);
	}  
}
