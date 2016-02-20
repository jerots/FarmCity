package menu;

import ctrl.*;
import java.util.*;
import entity.*;

/**
* The user login menu to choose between friends, farm or inventory menu.
*/

public class LoginMenu{
	private MainMenu mainMenu;
	private LoginController loginCtrl;
	
	
	/**
	* the method to begin login menu
	*/
	public void login(){
		LoginController loginCtrl = new LoginController();
		Scanner sc = new Scanner(System.in);
		MainMenu mainMenu = new MainMenu();
		
		System.out.print("Enter your username > ");
		String username = sc.nextLine();
		System.out.print("Enter your password > ");
		String password = sc.nextLine();
		
		//return user if details are correct, otherwise null
		User user = loginCtrl.login(username, password);
		
		if (user != null){ //successful login
            mainMenu.display(user);
		} else { //unsuccessful login
			System.out.println();
			System.out.println("User not found or incorrect password!");
			System.out.println();
		}
	}
}