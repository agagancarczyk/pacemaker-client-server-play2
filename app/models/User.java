package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.db.ebean.*;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name="my_user")
public class User extends Model
{
  @Id
  @GeneratedValue
  public Long   id;
  public String firstname;
  public String lastname;
  public String email;
  public String password;
  public String nationality; 
  
  @OneToMany(cascade=CascadeType.ALL)
  public List<Activity> activities = new ArrayList<Activity>();

  public User()
  {
  }

  public User(String firstname, String lastname, String email, String password, String nationality)
  {
    this.firstname = firstname;
    this.lastname  = lastname;
    this.email     = email;
    this.password  = password;
    this.nationality = nationality;
  }

  public void update (User user)
  {
    this.firstname = user.firstname;
    this.lastname  = user.lastname;
    this.email     = user.email;
    this.password  = user.password;
    this.nationality = user.nationality;
  }

  public String toString()
  {
    return Objects.toStringHelper(this)
        .add("Id", id)
        .add("Firstname", firstname)
        .add("Lastname", lastname)
        .add("Email", email)
        .add("Passwrod", password)
        .add("Nationality", nationality)
        .add("Activities", activities).toString();
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof User)
    {
      final User other = (User) obj;
      return Objects.equal(firstname, other.firstname) 
          && Objects.equal(lastname, other.lastname)
          && Objects.equal(email, other.email)
          && Objects.equal(password, other.password)
          && Objects.equal(nationality, other.nationality)
          && Objects.equal(activities, other.activities);
    }
    else
    {
      return false;
    }
  }

  public static User findByEmail(String email)
  {
    return  User.find.where().eq("email", email).findUnique();
  }

  public static User findById(Long id)
  {
    return find.where().eq("id", id).findUnique();
  }

  public static List<User> findAll()
  {
    return find.all();
  }

  public static void deleteAll()
  {
    for (User user: User.findAll())
    {
      user.delete();
    }
  } 

  public static Model.Finder<String, User> find = new Model.Finder<String, User>(String.class, User.class);
}
