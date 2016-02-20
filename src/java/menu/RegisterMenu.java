package menu;

import entity.*;
import ctrl.*;
import java.util.*;

/**
* The menu for user to register.
*/

public class RegisterMenu{
	
	private LoginController loginCtrl;
	
	
	/**
	* The method to begin menu display
	*/
	public void register(){
		
		loginCtrl = new LoginController();
                
		Scanner sc = new Scanner(System.in);
		
		System.out.println("== Farm City :: Registration ==");
		
		System.out.print("Enter your username > ");
		String username = sc.nextLine();
		boolean validName = validUsername(username);

		//Check if username contain only alphanumeric
		while (!validName){
			System.out.println();
			System.out.println("Username cannot be empty and should contain only alphanumeric characters!");
			System.out.println();
			System.out.print("Enter your username > ");
			username = sc.nextLine();
			validName = validUsername(username);
		}
		
		//
		System.out.print("Enter your full name > ");
		String fullName = sc.nextLine();
		System.out.print("Enter your password > ");
		String password = sc.nextLine();
		System.out.print("Confirm your password > ");
		String cfmPassword = sc.nextLine();
		boolean validPass = validPassword(password,cfmPassword);
		
		//Check if password same as confirmed password
		while (!validPass){
			System.out.println();
			System.out.println("Password not the same!");
			System.out.println();
			System.out.print("Enter your password > ");
			password = sc.nextLine();
			System.out.print("Confirm your password > ");
			cfmPassword = sc.nextLine();
			validPass = validPassword(password,cfmPassword);
		}
		
		//Register, user = null if username is already taken.
                
		User user = loginCtrl.register(username, fullName, password);
			
		//Success message
		if (user != null){
			System.out.println();
			System.out.println("Hi, " + user.getFullName() + "! Your account is successfully created!");
			System.out.println();
		} else {
			System.out.println();
			System.out.println("Username taken!");
			System.out.println();
		}
		
	}
	//checks if password is the same
	private boolean validPassword(String password, String cfmPassword){
		if(password.equals(cfmPassword)){
			return true;
		}
		return false;
	}
	
	/**
	* Check if a username is valid
	* @param username username to validate
	* @return true if valid, false otherwise
	*/
	//Checks if username contain only alphanumeric
	private boolean validUsername(String username){
		if (username.length() == 0){
			return false;
		}
		for (int i = 0; i < username.length(); i++){
			char usernameScan = username.toLowerCase().charAt(i);
			if (usernameScan < 'a' || usernameScan > 'z'){
				if(usernameScan < '0' || usernameScan > '9' ){
					return false;
				}
			}
		}	
		return true;
	}
}