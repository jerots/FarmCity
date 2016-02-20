package ctrl;

import manager.*;
import entity.*;
import java.util.*;

/**
* Handles use cases: register, login
*/

public class LoginController {

	/**
	* @param userMgr used to CRUD users
	*/
	private UserManager userMgr;
	
	
	/**
	* Register a new user
	* @return created user
	* @param username username to be registered
	* @param fullName full name of user to be registered
	* @param password password of user to be registered
	*/
	public User register(String username, String fullName, String password){
		userMgr = new UserManager();
		return userMgr.createUser(username,fullName, password);
		
	}
 
	/**
	* Login a user
	* @return logged on user
	* @param username username to login
	* @param password password of user to login
	*/ 
	public User login (String username, String password){
                userMgr = new UserManager();
		return userMgr.retrieveUser(username, password);
		
		
	}
	
 }