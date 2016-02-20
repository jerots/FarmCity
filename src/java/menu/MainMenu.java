package menu;

import java.util.*;
import java.text.*;
import entity.*;
import sql.SQLManager;

/**
* The menu after logging in. Includes friends, farm and inventory.
*/

public class MainMenu {
	private FarmMenu farmMenu;
	private InventoryMenu invMenu;
	private FriendMenu friendMenu;
    
	/**
	* The method to begin menu display
	* @param user the logged on user
	*/
	public void display(User user){

		Scanner sc = new Scanner (System.in);
		String choice = "0";
		while (!choice.equals("4")){
			outPut(user);
			
			choice = sc.nextLine();
			
			switch (choice){
                case "1":
                    friendMenu = new FriendMenu();
                    friendMenu.display(user);
                    break; 
                case "2":	
                    farmMenu = new FarmMenu();
                    farmMenu.display(user);
                    break;
                case "3":
                    invMenu = new InventoryMenu();
                    invMenu.display(user);
                    break;
                case "4":
                    SQLManager.updateCache();
                    System.out.println();
                    System.out.println("You have been logged out");
                    System.out.println();
                    break;
                default:
                    System.out.println();
                    System.out.println("Invalid input!");
                    System.out.println();
                    break;
			}
		}
	}
	
	private void outPut(User user){
		System.out.println();
		System.out.println("== Farm City :: Main Menu ==");
		System.out.println("Welcome, " + user.getFullName() + "!");
		System.out.println();
		System.out.println("1. My Friends");
		System.out.println("2. My Farm");
		System.out.println("3. My Inventory");
		System.out.println("4. Logout");
		System.out.print("Enter your choice > ");
	}
}