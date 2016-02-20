package menu;


import java.util.*;

/**
* The menu before logging in. Includes registration and login.
*/


public class FarmCityMenu {
	
	private RegisterMenu regMenu;
	private LoginMenu loginMenu;
	
	
	/**
	* The method to begin menu display
	*/
	public void display (){
		Scanner sc = new Scanner(System.in);
		
		String choice = "0";
		
		//exit only if choice is 3
		while (!choice.equals("3")){
		
			regMenu = new RegisterMenu();
			loginMenu = new LoginMenu();
			
			welcomePage();
			choice = sc.nextLine();
				
			switch (choice){
				case "1": 
					regMenu.register();
					break; 
				case "2":	
					loginMenu.login();
					break;
				case "3":
					System.out.println("Good bye, hope to see you again!");
					break;
				default:
					System.out.println();
					System.out.println("Invalid input!");
					System.out.println();
					break;
			}
		}
	}

	//display welcome page
	private void welcomePage(){
		System.out.println("== Farm City :: Welcome ==");
		System.out.println("Hi, ");
		System.out.println("1. Register");
		System.out.println("2. Login");
		System.out.println("3. Exit");
		System.out.print("Enter your choice > ");
	}
}
