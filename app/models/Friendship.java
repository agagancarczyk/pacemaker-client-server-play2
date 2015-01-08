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
	@Table(name="my_friendship")
	public class Friendship extends Model
	{
	  @Id
	  @GeneratedValue
	  public Long   id;
	  
	  @ManyToOne(cascade=CascadeType.ALL)
	  public User sourceUser;
	  
	  @ManyToOne(cascade=CascadeType.ALL)
	  public User targetUser;
	  
	  public Friendship()
	  {
	  }

	  public Friendship(User sourceUser, User targetUser)
	  {
	    this.sourceUser = sourceUser;
	    this.targetUser = targetUser;
	  }

	  public void update (Friendship friendship)
	  {
	    this.sourceUser = friendship.sourceUser;
	    this.targetUser = friendship.targetUser;
	  }

	  public String toString()
	  {
	    return Objects.toStringHelper(this)
	        .add("Id", id)
	        .add("SourceUser", sourceUser)
	        .add("TargetUser", targetUser).toString();
	  }

	  @Override
	  public boolean equals(final Object obj)
	  {
	    if (obj instanceof Friendship)
	    {
	      final Friendship other = (Friendship) obj;
	      return Objects.equal(sourceUser, other.sourceUser) 
	          && Objects.equal(targetUser, other.targetUser);
	    }
	    else
	    {
	      return false;
	    }
	  }

	  /*
	   * Method allows to find a friendship by id.
	   * 
	   * @param id 
	   *          Long
	   */
	  public static Friendship findById(Long id)
	  {
	    return find.where().eq("id", id).findUnique();
	  }
	  
	  /*
	   * Method allows to find all friendships in a list of friendships.
	   */
	  public static List<Friendship> findAll()
	  {
	    return find.all();
	  }

	  /*
	   * Method allows to delete all friendships.
	   */
	  public static void deleteAll()
	  {
	    for (Friendship friendship: Friendship.findAll())
	    {
	      friendship.delete();
	    }
	  } 

	  public static Model.Finder<String, Friendship> find = new Model.Finder<String, Friendship>(String.class, Friendship.class);
	}
