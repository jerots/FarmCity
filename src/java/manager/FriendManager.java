package manager;

import java.io.*;
import java.util.*;
import entity.*;


/**
* manages the Create, Retrieve, Update, Delete of Friends 
*/

public class FriendManager{
	/**
	* @param FRIENDFILE location of the friend database file
	* @param friendList the temp memory of friends list
	*/
	
	private final File FRIENDFILE = new File("data/friends.csv");
	private ArrayList<Friend> friendList;
	


	public FriendManager(){
		friendList = new ArrayList<Friend>();
		load();
	}

	/**
	* Create a new friend
	* @param user accepting friend
	* @param friendRequester friend who requesting
	*/
	public void createFriend(String user, String friendRequester){
		
		Friend newFriend = new Friend(user, friendRequester);
		friendList.add(newFriend);
		newFriend = new Friend(friendRequester,user);
		friendList.add(newFriend);
		save();
		
	}

	/**
	* Retrieve all all of user's friends
	* @return Retrieve friend based on username
	* @param username shows friend list of inserted user
	*/
	public ArrayList<Friend> retrieveUserFriend(String username){
		ArrayList<Friend> friendListToReturn = new ArrayList<Friend>();
		for (int i = 0; i < friendList.size(); i++){
			Friend current = friendList.get(i);
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

			writer.println("userName, friendUserName");
			for (int i = 0; i < friendList.size(); i++){
				Friend current = friendList.get(i);
				writer.println(current.getUserName() + "," + current.getFriendUserName());
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	* @param user friend of this user to be deleted
	* @param friendUserName the friend's name
	*/
	public void deleteFriend(String user, String friendUserName){
		for (int i = 0; i < friendList.size(); i++){
			Friend current = friendList.get(i);
			if (current.getUserName().equals(user) && current.getFriendUserName().equals(friendUserName)){
				friendList.remove(i);
			} else if(current.getUserName().equals(friendUserName) && current.getFriendUserName().equals(user)){
				friendList.remove(i);
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
		friendList.clear(); //clear arraylist before loading
		
		while (sc.hasNextLine()){
		
			Scanner scanLine = new Scanner(sc.nextLine());
			
			scanLine.useDelimiter(",");
			String retrUsername = scanLine.next();
			String retrFriendName = scanLine.next();

			friendList.add(new Friend(retrUsername, retrFriendName));
		}
	}
}