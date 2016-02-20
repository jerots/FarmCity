package ctrl;

import java.util.*;
import manager.*;
import entity.*;
import java.sql.Connection;
import sql.SQLManager;
/**
* Handles use cases: gifting
*/

public class GiftController{
		
	/**
	* @param user the logged on user
	* @param userMGr to CRUD users
	* @param friendMgr to CRUD friends
	* @param giftMgr to CRUD gifts
	*/		
	private UserManager userMgr;
	private FriendManager friendMgr;
	private GiftManager giftMgr;
	private CropManager cropMgr;
	private RankManager rankMgr;
	
	/**
	* Constructor
	*/
	public GiftController(){
            Connection conn = SQLManager.connect();
		rankMgr = new RankManager();
		userMgr = new UserManager(conn);
		friendMgr = new FriendManager();
		giftMgr = new GiftManager();
		cropMgr = new CropManager();
            SQLManager.closeConnection(conn);
	}
	
	public Rank getRank(User user){
            return rankMgr.getRank(user.getExp());
	}
	
	
	/**
	* Gifts a gift to a friend
	* @return true if gift successful, otherwise false
	* @param user the logged on user
	* @param crop type of crop to give
	* @param friend friend to give
	*/
	public boolean givingGift(User user, Crop crop,String friend){
		//if sending to himself/herself, gifting fails
		if (user.getUsername().equals(friend)){ 
			return false;
		}
		
		//if not giving to friend, gifting fails
		ArrayList<Friend> friendList = friendMgr.retrieveUserFriend(user.getUsername());
		boolean givingToFriend = false;
		for (Friend current : friendList){
			if (current.getFriendUserName().equals(friend)){
			givingToFriend = true;
			}
		}
		
		if (!givingToFriend){ 
			return false;
		}
		
		//if user already gifted 5 times today, gifting fails
		Date currDate = new Date();
		ArrayList<Gift> giftsByUserToday = giftMgr.retrieveGiftsToday(user);
		if (giftsByUserToday.size() >= 5){
			return false;
		}
		
		
		//if user already gifted to this friend today, gifting fails
		for (int i = 0; i < giftsByUserToday.size(); i++){
			Gift current = giftsByUserToday.get(i);
			if (current.getReceiver().getUsername().equals(friend)){
				return false;
			}
		}
		
		
		User receiver = userMgr.retrieveUser(friend);
		
		giftMgr.createGift(user, receiver, crop, currDate);	

		InventoryController invcontroller = new InventoryController();
		invcontroller.addSeed(receiver, crop,1);
		
		return true;	
		
	}
	
	/**
	* Retrieve friend from userMgr
	* @param friendStr Friend's username
	* @return friend User
	*/
	public User retrieveUser(String friendStr){
		return userMgr.retrieveUser(friendStr);
	}
	
	
	/**
	* Get crop based on crop name
	* @param cropName name
	* @return Crop object
	*/
	public Crop getCrop(String cropName){
		return cropMgr.getCrop(cropName);
	}



}