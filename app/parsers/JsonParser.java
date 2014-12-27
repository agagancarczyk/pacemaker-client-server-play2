package parsers;

import models.Activity;
import models.User;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

public class JsonParser
{
  private static JSONSerializer  userSerializer     = new JSONSerializer();
  private static JSONSerializer  activitySerializer = new JSONSerializer();

  //User
  public static User renderUser(String json)
  {
    return new JSONDeserializer<User>().deserialize(json, User.class); 
  }

  public static String renderUser(Object obj)
  {
    return userSerializer.serialize(obj);
  }
  
  //Activity
  public static Activity renderActivity(String json)
  {
    return new JSONDeserializer<Activity>().deserialize(json, Activity.class); 
  }
  
  public static String renderActivity(Object obj)
  {
    return activitySerializer.serialize(obj);
  }
}