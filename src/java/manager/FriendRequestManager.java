package manager;

import java.io.*;
import java.util.*;
import entity.*;

/**
* manages the Create, Retrieve, Update, Delete of Friend requests 
*/
public class FriendRequestManager{
	
	/**
	* @param FRIENDFILE location of the friend database file
	* @param friendReqList the temp memory of friend request list
	*/	
	private final File FRIENDFILE = new File("data/friendRequest.csv");
	private ArrayList<Friend> friendReqList;


	
	public FriendRequestManager(){
		friendReqList = new ArrayList<Friend>();
		load();
	}
	
	/**
	* Create new friend request
	* @param user user accepting request
	* @param friendRequester user requesting friendship
	*/
	public void createFriendRequest(String user, String friendRequester){
		
		load();
		Friend newFriend = new Friend(friendRequester, user);
		friendReqList.add(newFriend);
		save();
		
	}

	/**
	* Get user's receiving friend request
	* @return retrieve friend request based on username
	* @param username retrieve this user's friend requests
	*/
	public ArrayList<Friend> retrieveUserFriendRequest(String username){
		ArrayList<Friend> friendListToReturn = new ArrayList<Friend>();
		for (int i = 0; i < friendReqList.size(); i++){
			Friend current = friendReqList.get(i);
			if (current.getUserName().equals(username)){
				friendListToReturn.add(current);
			}
		}
		return friendListToReturn;
	}

	/**
	* Save into file
	*/
	public void save(){
		
		try{
			PrintStream writer = new PrintStream(new FileOutputStream(FRIENDFILE, false));

			writer.println("userName, friendRequester");
			for (int i = 0; i < friendReqList.size(); i++){
				Friend current = friendReqList.get(i);
				writer.println(current.getUserName() + "," + current.getFriendUserName());
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	* Delete a friend request
	* @param user delete this user's friend request
	* @param friendRequester requester's request to delete
	*/
	public void deleteFriendRequest(String user, String friendRequester){
		for (int i = 0; i < friendReqList.size(); i++){
			Friend current = friendReqList.get(i);
			if (current.getUserName().equals(user) && current.getFriendUserName().equals(friendRequester)){
				friendReqList.remove(i);
			}
		}
		save();
	}

	/**
	* Load into file
	*/
	public void load(){
		Scanner sc = null;
		try {
			sc = new Scanner(FRIENDFILE);
		} catch (Exception e){
			e.printStackTrace();
		}
		sc.nextLine(); //skip headers
		friendReqList.clear(); //clear arraylist before loading
		
		while (sc.hasNextLine()){
		
			Scanner scanLine = new Scanner(sc.nextLine());
			
			scanLine.useDelimiter(",");
			String retrUsername = scanLine.next();
			String retrFriendReqName = scanLine.next();

			friendReqList.add(new Friend(retrUsername, retrFriendReqName));
		}
	}
}