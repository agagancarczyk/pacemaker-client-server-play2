package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Friendship;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.friends;
import views.html.showfriend;

public class Friendships extends Controller {

	public static Result friends(Long userId) {
		User user = Accounts.getLoggedInUser();

		String searchQuery = request().getQueryString("searchQuery");
		List<User> allUsers = User.findAll();
		allUsers.remove(user);
		List<User> users = new ArrayList<>();

		if (searchQuery == null)
			searchQuery = "";

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < allUsers.size(); i++) {
			sb.append(allUsers.get(i).firstname);
			sb.append(allUsers.get(i).lastname);
			if (sb.toString().contains(searchQuery)) {
				users.add(allUsers.get(i));
			}
			sb.setLength(0);
		}

		return ok(friends.render(user, users));
	}

	public static Result addFriend(Long userId, Long targetUserId) {
		User user = Accounts.getLoggedInUser();
		User targetUser = User.findById(targetUserId);
		boolean friendsAlready = false;
		for (int i = 0; i < user.friendships.size(); i++) {
			if (user.friendships.get(i).targetUser.id == targetUserId) {
				friendsAlready = true;
			}	
		}
		if (!friendsAlready){
			user.friendships.add(new Friendship(user,targetUser));
			user.save();
		}
		
		return friends(user.id);
	}

	public static Result removeFriend(Long userId, Long targetUserId) {
		User user = Accounts.getLoggedInUser();
		User targetUser = User.findById(targetUserId);
		for (int i = 0; i < user.friendships.size(); i++) {
			Friendship f = user.friendships.get(i);
			if (targetUser.id == f.targetUser.id) {
				user.friendships.remove(f);
				user.save();
				f.sourceUser = null;
				f.targetUser = null;
				f.delete();
			}
		}
		user.save();
		return friends(user.id);
	}
	
	public static Result showFriend(Long userId, Long friendId) {
		User user = Accounts.getLoggedInUser();
		User friend = User.findById(friendId);
		return ok(showfriend.render(friend));
	}

}
