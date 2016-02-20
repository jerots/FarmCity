package menu;

import java.util.*;
import java.text.*;
import entity.*;
import ctrl.*;
import sql.SQLManager;

/**
* The menu for adding and removing friends
*/

public class FriendMenu{
	private FriendController friendCtrl;
	private ArrayList<Friend> friendList;
	private ArrayList<Friend> friendRequestList;
	
	
	/**
	* The method to begin menu display
	* @param user the logged on user
	*/
	public void display(User user){
		
		char option = 'O';
            friendCtrl = new FriendController();
		while(option != 'M'){
			friendList = new ArrayList<Friend>();
			friendRequestList = new ArrayList<Friend>();
			Scanner sc = new Scanner(System.in);
		
			System.out.println("== Farm City :: My friends ==");
			System.out.println("Welcome, " + user.getFullName() + "!");
			System.out.println();
			System.out.println("My Friends:");
			
			int count = 1;
			
			//get arrayList<user> of all the friend 
			ArrayList<Friend> friendList = friendCtrl.getAllFriend(user);
			
			//display all the friend according to count
			for(int i = 0; i<friendList.size(); i++){
				Friend friend = friendList.get(i);
				System.out.println(count +". " + friend.getFriendUserName());
				count++;
			}
			System.out.println();
			
			
			//call out method from friendDisplayCtrl to display all the friend request
			System.out.println("My Requests");
			ArrayList<Friend> friendRequestList = friendCtrl.getAllFriendRequest(user);
			
			//display all the friend request according to count
			for(int i = 0; i<friendRequestList.size(); i++){
				Friend friend = friendRequestList.get(i);
				System.out.println(count +". " + friend.getFriendUserName());
				count++;
			}
			System.out.println();
			String input;
				
			System.out.print("[M]ain | [U]nfriend | re[Q]uest | [A]ccept | [R]eject > ");
			try{
				input = sc.nextLine().toUpperCase();
				char firstLetter = input.charAt(0);
				
				if(firstLetter == 'Q' && input.length()==1){
					handleOptionForRequest(user);
				} else if(firstLetter == 'M'){
					option = 'M';
                    SQLManager.updateCache();
					System.out.println();
					System.out.println("Returning to main menu...");
					System.out.println();
				} else if((firstLetter == 'U' || firstLetter == 'A' || firstLetter == 'R') && Integer.parseInt(input.substring(1))<=count-1){
					handleOptions(user,input.charAt(0),Integer.parseInt(input.substring(1)));
				} else{
					System.out.println();
					System.out.println("Invalid input");
					System.out.println();
				}
			} catch(Exception e){
				System.out.println();
				System.out.println("Invalid input");
				System.out.println();
			}
		}
	}
	
	/**	
	* The method to filter the options the user picks
	* @param optionInput the type of request. A for accept, R for reject, U for unfriend
	* @param number the identifying position of the friend in the friend list
	*/
	private void handleOptions(User user, char optionInput, int number){
		
		String friendUserName = null;;
		friendList = friendCtrl.getAllFriend(user);
		friendRequestList = friendCtrl.getAllFriendRequest(user);
		
		//from the number, get the username of the person
		if(optionInput == 'A' || optionInput == 'R' ){
			number = number - friendList.size();
			friendUserName = friendRequestList.get(number-1).getFriendUserName();
		}
		if(optionInput == 'U'){
		
			friendUserName = friendList.get(number-1).getFriendUserName();
		}
		switch(optionInput){
			case 'U':
				friendCtrl.deleteFriend(user.getUsername(), friendUserName);
				friendCtrl.deleteFriend(friendUserName,user.getUsername());
				System.out.println(friendUserName + " is no longer your friend.");
				System.out.println();
				break;
			case 'A':
				friendCtrl.acceptFriend(user.getUsername(), friendUserName);
				System.out.println(friendUserName + " is now your friend.");
				System.out.println();
				break;
			case 'R':
				friendCtrl.rejectFriend(user.getUsername(), friendUserName);
				System.out.println("You have rejected " + friendUserName + " as a friend.");
				System.out.println();
				break;
		}
	}
	
	/**
	* The menu to send out friend requests
	* @param user the logged on user
	*/
	private void handleOptionForRequest(User user){
		int check = 5;
		do{
			System.out.print("Enter the username > ");
			Scanner sc = new Scanner(System.in);
			String friendToRequest = sc.nextLine();
			check = friendCtrl.requestFriend(user.getUsername(), friendToRequest);
			//if return 0 means request sent out
			if(check == 0){
				System.out.println("A friend request is sent out to " + friendToRequest);
				System.out.println();
			}
			//if return 1 means no such user found
			else if(check == 1){
				System.out.println("No such user found");
				System.out.println();
			}
		} while(check != 0);
	}
}