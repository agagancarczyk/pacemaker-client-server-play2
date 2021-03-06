package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.db.ebean.*;
import utils.TokenGenerator;
import models.Friendship;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
@Entity
@Table(name = "my_user")
public class User extends Model {
	@Id
	@GeneratedValue
	public Long id;
	public String firstname;
	public String lastname;
	public String email;
	public String password;
	public String nationality;
	public String token;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Activity> activities = new ArrayList<Activity>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "sourceUser")
	public List<Friendship> friendships = new ArrayList<Friendship>();

	public User() {
	}

	public User(String firstname, String lastname, String email, String password, String nationality) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.nationality = nationality;
	}

	 /*
     * Constructor with token parameter allows to test secure API.
     */
	public User(String firstname, String lastname, String email, String password,
			String nationality, String token) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.password = password;
		this.nationality = nationality;
		this.token = token;
	}

	public void update(User user) {
		this.firstname = user.firstname;
		this.lastname = user.lastname;
		this.email = user.email;
		this.password = user.password;
		this.nationality = user.nationality;
	}

	public String toString() {
		return Objects.toStringHelper(this).add("Id", id).add("Firstname", firstname)
				.add("Lastname", lastname).add("Email", email).add("Passwrod", password)
				.add("Nationality", nationality).add("Activities", activities).toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof User) {
			final User other = (User) obj;
			return Objects.equal(firstname, other.firstname)
					&& Objects.equal(lastname, other.lastname) && Objects.equal(email, other.email)
					&& Objects.equal(password, other.password)
					&& Objects.equal(nationality, other.nationality)
					&& Objects.equal(activities, other.activities);
		} else {
			return false;
		}
	}

    /*
	 * Method allows to find a user by email.
	 * 
	 * @param email 
	 *          String
	 */
	public static User findByEmail(String email) {
		return User.find.where().eq("email", email).findUnique();
	}

	/*
	 * Method allows to find a user by id.
	 * 
	 * @param id 
	 *          Long
	 */
	public static User findById(Long id) {
		return find.where().eq("id", id).findUnique();
	}

    /*
	 * Method allows to check user's password when logging into.
	 * 
	 * @param password 
	 *           String
	 */
	public boolean checkPassword(String password) {

		return this.password.equals(password);
	}

	/*
	 * Method allows to find all users in the users list.
	 */
	public static List<User> findAll() {
		List<User> users = find.all();
		users.remove(User.findByEmail("admin@tssg.org"));
		return users;
	}

	/*
	 * Method allows to delete all users.
	 */
	public static void deleteAll() {
		for (User user : User.findAll()) {
			// if (!("admin@tssg.org".equals(user.email)
			// && "gq21ghejfjgfiodr9teetbenrh".equals(user.token)))
			user.delete();
		}
	}

	/*
	 * Method allows to generate a token for a user.
	 */
	public void generateAPItoken() {
		this.token = TokenGenerator.nextToken();
		this.save();
	}

	public static Model.Finder<String, User> find = new Model.Finder<String, User>(String.class,
			User.class);
}
