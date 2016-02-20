package entity;

/**
* A friend is a person who is a friend of another user
*/

public class Friend{

	private String userName;
	private String friendUserName;
	
	
	/**
	* Constructor
	* @param userName the username of current user
	* @param friendUserName the username of current user's friend
	*/
	public Friend(String userName, String friendUserName){
		this.userName = userName;
		this.friendUserName = friendUserName;
	}
	
	
	/**
	* Get friend's username
	* @return returns friend's username
	*/
	public String getFriendUserName(){
		return friendUserName;
	}
	
	/**
	* Get user's username
	* @return returns current user's name
	* @return userName returns 
	*/
	public String getUserName(){
		return userName;
	}
}