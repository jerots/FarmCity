package ctrl;

import java.util.*;
import manager.*;
import entity.*;
import java.sql.Connection;
import sql.SQLManager;

/**
* Handles use cases: friend, unfriend
*/

public class FriendController{

	/**
	* @param friendReqMgr to CRUD friend requests
	* @param friendMgr to CRUD friends
	* @param userMgr to CRUD users
	*/
	private FriendRequestManager friendReqMgr;
	private FriendManager friendMgr;
	private UserManager userMgr;
	
	
	/**
	* Constructor
	*/
	public FriendController(){
		Connection conn = SQLManager.connect();
		friendReqMgr = new FriendRequestManager();
		friendMgr = new FriendManager();
		userMgr = new UserManager(conn);
                SQLManager.closeConnection(conn);
	}
	
	/**
	* Get all the user's friends in an ArrayList
	* @return arraylist of all the user's friends
	* @param user User to find friends for
	*/
	public ArrayList<Friend> getAllFriend(User user){
		//call a method in friendMgr to retrieve all the current friends

		return friendMgr.retrieveUserFriend(user.getUsername());
	}
	
	/**
	* Get all the friend requests the user has
	* @return ArrayList user's friend requests
	* @param user to get friend requests for
	*/
	public ArrayList<Friend> getAllFriendRequest(User user){
		//call a method in friendreqMgr to retrieve all the current friends request
		return friendReqMgr.retrieveUserFriendRequest(user.getUsername());
	}

	/**
	* Delete friend
	* @param user logged on user
	* @param friendUserName friend's username (to be deleted)
	*/
	public void deleteFriend(String user, String friendUserName){
		friendMgr.deleteFriend(user, friendUserName);
	}
	
	/**
	* Accept friend
	* @param user logged on user
	* @param friendRequester friend to be accepted
	*/
	public void acceptFriend(String user, String friendRequester){
		friendMgr.createFriend(user, friendRequester);
		friendReqMgr.deleteFriendRequest(user, friendRequester);
	}


	/**
	* Reject Friend
	* @param user logged on user
	* @param friendRequester friend to be rejected
	*/
	public void rejectFriend(String user, String friendRequester){
		friendReqMgr.deleteFriendRequest(user, friendRequester);
	}
	
	/**
	* Request Friend
	* @return 1 if succesful, 0 if not
	* @param user logged on user
	* @param friendToRequest friend to request friendship with
	*/
	public int requestFriend(String user, String friendToRequest){
		User checkFriend = userMgr.retrieveUser(friendToRequest);
		if(checkFriend == null){
			return 1;
		}
		else{
			friendReqMgr.createFriendRequest(user, friendToRequest);
		}
		return 0;
	}
}