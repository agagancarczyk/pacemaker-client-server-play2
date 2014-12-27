package models;

import java.util.List;

import javax.persistence.*;

import org.joda.time.DateTime;

import play.db.ebean.*;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name="my_activity")
public class Activity extends Model
{
  @Id
  @GeneratedValue
  public Long   id;
  public String type;
  public String location;
  public double distance;
  public DateTime date;
  public String duration; 
  
  public Activity()
  {
  }

  public Activity(String type, String location, double distance, DateTime date, String duration)
  {
    this.type = type;
    this.location  = location;
    this.distance     = distance;
    this.date  = date;
    this.duration = duration;
  }

  public void update (Activity activity)
  {
    this.type = activity.type;
    this.location  = activity.location;
    this.distance     = activity.distance;
    this.date  = activity.date;
    this.duration = activity.duration;
  }

  public String toString()
  {
    return Objects.toStringHelper(this)
        .add("Id", id)
        .add("Type", type)
        .add("Location", location)
        .add("Distance", distance)
        .add("Date", date)
        .add("Duration", duration).toString();
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Activity)
    {
      final Activity other = (Activity) obj;
      return Objects.equal(type, other.type) 
          && Objects.equal(location, other.location)
          && Objects.equal(distance, other.distance)
          && Objects.equal(date, other.date)
          && Objects.equal(duration, other.duration);
    }
    else
    {
      return false;
    }
  }

  public static Activity findById(Long id)
  {
    return find.where().eq("id", id).findUnique();
  }

  public static List<Activity> findAll()
  {
    return find.all();
  }

  public static void deleteAll()
  {
    for (Activity activity: Activity.findAll())
    {
      activity.delete();
    }
  } 

  public static Model.Finder<String, Activity> find = new Model.Finder<String, Activity>(String.class, Activity.class);
}
