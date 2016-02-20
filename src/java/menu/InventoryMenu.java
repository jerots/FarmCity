package menu;

import java.util.*;
import entity.*;
import ctrl.*;
import sql.SQLManager;

/**
* The menu to manage inventory, send gifts and buy seeds
*/

public class InventoryMenu {

	private InventoryController invCtrl;
	private StoreMenu storeMenu;
	private GiftMenu giftMenu;
	
	/**
	* The method to begin menu display
	* @param user the logged on user
	*/
	public void display(User user){
		
		char choice = '0';
		invCtrl = new InventoryController();
		while (choice != 'M'){
			Scanner sc = new Scanner(System.in);
		
			System.out.println("== Farm City :: My Inventory ==");
			System.out.println("Welcome, " + user.getFullName() + "!");
			System.out.println("Rank: " + invCtrl.getRank(user).getRankName() + "\tGold: " + user.getGold()); 
			System.out.println();
			System.out.println("My Seeds:");
			ArrayList<SeedBag> seedList = invCtrl.loadAllSeedBag(user);
			if (seedList.size() == 0){
				System.out.println();
				System.out.println("You have no seeds");
				System.out.println();
			}
			for (int i = 0; i < seedList.size(); i++){
				SeedBag current = seedList.get(i);
				System.out.println(i + 1 + ". " + current.getQuantity() + " Bags of " + current.getCrop().getName());
			}
			//display seeds
			System.out.println();
		
			System.out.print("[M]ain | [B]uy | [G]ift |  Select choice > ");
		
			try{
				choice = sc.nextLine().toUpperCase().charAt(0);
			
				if (choice == 'B'){
                    storeMenu = new StoreMenu(invCtrl);
					storeMenu.display(user);
				
				} else if(choice == 'G'){
					giftMenu = new GiftMenu();
					giftMenu.display(user);
			
				} else if(choice == 'M'){
                                        SQLManager.updateCache();
					System.out.println();
					System.out.println("Back to main menu...");
					System.out.println();
				} else{
					System.out.println();
					System.out.println("Invalid input!");
					System.out.println();
				}
                
			} catch (Exception e){
				System.out.println("Invalid input!");
			}
		}
	}
}