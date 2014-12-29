package models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name="my_location")
public class Location extends Model
{
  @Id
  @GeneratedValue
  public Long id;

  public float latitude;
  public float longtitude;
  
  @ManyToOne(cascade=CascadeType.ALL)
  public Activity activity;

  public Location()
  {
  }

  public Location (float latitude, float longitude)
  {
    this.latitude = latitude;
    this.longtitude = longitude;
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Location)
    {
      final Location other = (Location) obj;
      return Objects.equal(latitude, other.latitude) 
          && Objects.equal(longtitude, other.longtitude);
    }
    else
    {
      return false;
    }
  }

  public String toString()
  {
    return Objects.toStringHelper(this)
        .add("Latitude", latitude)
        .add("Longtitude", longtitude).toString();
  }
  
  public static Location findById(Long id)
  {
	 return find.where().eq("id", id).findUnique();
  }

  public static List<Location> findAll()
  {
    return find.all();
  }

  public static void deleteAll()
  {
    for (Location location: Location.findAll())
    {
      location.delete();
    }
  } 

  public static Model.Finder<String, Location> find = new Model.Finder<String, Location>(String.class, Location.class);
}
