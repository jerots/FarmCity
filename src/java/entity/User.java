package entity;

/**
* each user represents one person, it contains password, name, gold and experience.
*/

public class User {

	/**
	* @param username the username of the user
	* @param fullname the fullname of the user
	* @param password the password of the user
	* @param gold the gold the user has
	* @param exp the experience the user has
	*/
	private String username;
	private String fullName;
	private String password;
	private int gold;
	private int exp;
	
	public User (String username, String fullName, String password){
		this.username = username;
		this.fullName = fullName;
		this.password = password;
		gold = 1000;
		exp = 0;
	}
	
	public User (String username, String fullName, String password, int gold, int exp){
		this(username,fullName,password);
		this.gold = gold;
		this.exp = exp;
	}
	
	/**
	* Get username
	* @return the username
	*/
	public String getUsername(){
		return username;
	}
	
	/**
	* Get full name
	* @return the fullname
	*/
	public String getFullName(){
		return fullName;
	}

	/**
	* Get Password
	* @return the password
	*/	
	public String getPassword(){
		return password;
	}

	/**
	* Get gold
	* @return the gold the user has
	*/
	public int getGold(){
		return gold;
	}

	/**
	* Get experience
	* @return the experience the user has
	*/
	public int getExp(){
		return exp;
	}

	/**
	* Set new username
	* @param username the new username
	*/
	public void setUsername(String username){
		this.username = username;
	}

	/**
	* Set new full name
	* @param fullName new full name
	*/
	public void setFullName(String fullName){
		this.fullName = fullName;
	}

	/**
	* Set new password
	* @param password the new password
	*/
	public void setPassword(String password){
		this.password = password;
	}

	/**
	* Set gold quantity the user has
	* @param gold the new gold
	*/
	public void setGold(int gold){
		if (gold < 0){
		
			this.gold = 0;
			
		} else {
		
			this.gold = gold;
		}
		
	}

	/**
	* Set new experience for the user
	* @param exp the new exp
	*/
	public void setExp (int exp){
		this.exp = exp;
	}




}