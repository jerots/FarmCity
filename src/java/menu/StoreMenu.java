package menu;

import java.util.*;
import entity.*;
import ctrl.*;
import sql.SQLManager;

/**
* The menu for users to buy seeds.
*/

public class StoreMenu {

	private InventoryController invCtrl;
        
        public StoreMenu(InventoryController invCtrl){
            this.invCtrl = invCtrl;
            
        }

	/**
	* The method to begin menu display
	* @param user the logged on user
	*/
	public void display(User user){
		Scanner sc = new Scanner(System.in);
        
        System.out.println("== Farm City :: Store ==");
		System.out.println("Welcome, " + user.getFullName() + "!");
		System.out.println("Rank: " + invCtrl.getRank(user).getRankName() + "\tGold: " + user.getGold()); 
		System.out.println();
		System.out.println("Seeds Available:");
		
		ArrayList<Crop> cropList = invCtrl.getShopList();
		
		for (int i = 0; i < cropList.size(); i++){
			Crop current = cropList.get(i);
			System.out.println(i + 1 + ".\t" + current.getName() + " costs: " + current.getPrice() + " gold");
			System.out.println("\tHarvests in: " + current.getTime() + " mins");
			System.out.println("\tXP Gained: " + current.getExp());
		
		}
		System.out.println();
		
		System.out.print("[M]ain | Select choice > ");
		int choice = sc.nextInt();
		System.out.print("Enter quantity > ");
		int quantity = sc.nextInt();
		int userBalance = user.getGold();
		
		Crop chosenCrop = cropList.get(choice - 1);
		int cost = chosenCrop.getPrice() * quantity;
		
		if (invCtrl.deductGold(user, cost)){
			invCtrl.addSeed(user, chosenCrop, quantity);
			System.out.println(quantity + " bags of seeds purchased for " + cost + " gold.");
			
		} else {
			System.out.println("Not enough gold.");
		}
	}
}