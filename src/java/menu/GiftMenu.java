package menu;

import java.util.*;
import ctrl.*;
import entity.*;
/**
* The menu for gifts management.
*/

public class GiftMenu{
	
	private GiftController giftCtrl;
	
	/**
	* The method to begin menu display
	* @param user the logged on user
	*/
	public void display(User user){

		String choice = "0";
		
		while (!choice.equals("M")){	
			Scanner sc = new Scanner (System.in);
			giftCtrl = new GiftController();
            
			System.out.println("== Farm City :: Send a Gift ==");
			System.out.println("Welcome, "+user.getFullName()+"!");
			System.out.println("Rank: " +giftCtrl.getRank(user).getRankName()+"\tGold: "+user.getGold());
			System.out.println();
			System.out.println("Gifts Available:");
			System.out.println("1. 1 Bag of Papaya Seeds");
			System.out.println("2. 1 Bag of Pumpkin Seeds");
			System.out.println("3. 1 Bag of Sunflower Seeds");
			System.out.println("4. 1 Bag of Watermelon Seeds");

			System.out.print("[M]ain | Select choice > ");
			choice = sc.nextLine().toUpperCase();
			
			//Find crop
			Crop crop = null;
			if (!choice.equals("M")){
			
				while (crop == null){
					switch (choice){
                        case "1":
                             crop = giftCtrl.getCrop ("Papaya");
                             break;
                        case "2":
                             crop = giftCtrl.getCrop("Pumpkin");
                             break;
                        case "3":
                             crop = giftCtrl.getCrop("Sunflower");
                             break;
                        case "4": 
                             crop = giftCtrl.getCrop("Watermelon");
                             break;						
                        default:
                            System.out.println("Invalid choice! Enter seed choice again");
                            choice = sc.nextLine();
					}		 
				}
				//Find friend(s)
			
				System.out.print("Send to> ");
				String friendString = sc.nextLine();
	
				Scanner comma = new Scanner(friendString);
				comma.useDelimiter(",");
                
				//For each friend (comma) found...
				while(comma.hasNext()){
					String friendStr = comma.next().trim(); 
					User friendUser= giftCtrl.retrieveUser(friendStr);
                    
					//if friend cannot be found, print cannot find
					if(friendUser ==null){
						System.out.println(friendStr + " cannot be found! ");
					} else{
						if (giftCtrl.givingGift(user, crop,friendStr)){
							System.out.println("Gift sent to " + friendStr);
							System.out.println();
						} else{
							System.out.println("Gifting to " + friendStr + " failed!");
							System.out.println();
						}
					}
				}
			}
		}
	}
}