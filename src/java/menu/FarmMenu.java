package menu;

import java.util.*;
import java.text.*;
import ctrl.*;
import entity.*;
import sql.SQLManager;

/**
* The menu for farming purposes: farming, harvesting
*/

public class FarmMenu{

    private FarmController farmCtrl;
	
	/**
	* The method to begin menu display
	* @param user the logged on user
	*/
	public void display(User user){
		String choice = "0";
            farmCtrl = new FarmController();
		while (!choice.equals("M")){
            
            Scanner sc = new Scanner(System.in);
            ArrayList<Land> landList = farmCtrl.loadAllLand(user);
            NumberFormat percent = NumberFormat.getPercentInstance();
            //print menu
            System.out.println("== Farm City :: My Farm ==");
            System.out.println("Welcome, " + user.getFullName() + "!");
            System.out.println("Rank: " + farmCtrl.getRank(user).getRankName() + "\tGold: " + user.getGold()); 
            System.out.println();
            System.out.println("You have " + farmCtrl.getRank(user).getPlots() + " plots of land.");


            //print list of land
            for (int i = 1; i <= landList.size(); i ++){
                Land current = landList.get(i-1);
                String progressBar = "";

                if (current == null){ //if land is empty, print empty
                    System.out.println(i + ". <empty>");

                } else { //if land is not empty
                    if (current.isWilted()){  //if wilted, print wilted
                        progressBar = "  wilted  ";

                    } else if (current.isMature()){ //if mature, set full progress bar
                        progressBar = "##########";

                    } else { //if not mature, set progress bar
                        for (int j = 0; j < (current.getProgress() - 0.1) * 10; j++){
                            progressBar += "#";
                        }
                        for (int j = 1; j < 10 - (current.getProgress() - 0.1) * 10; j++){
                            progressBar += "-";
                        }
                    }
                    //prints progress bar
                    System.out.println(i + ". " + current.getCrop().getName() + "\t[" + progressBar + "] " + percent.format(current.getProgress()));
                }
            }

            //print menu
            System.out.print("[M]ain | [P]lant | C[L]ear | [H]arvest > "); 
            try{
                choice = sc.nextLine().toUpperCase();

            //PLANTING
                if (choice.charAt(0) == 'P'){ 

                    int plotId = Integer.parseInt(choice.substring(1)); //get plot ID

                    if (plotId > farmCtrl.getRank(user).getPlots()){ //if chosen plot exceeds size
                        System.out.println();
                        System.out.println("Invalid input! You don't have that many land");
                        System.out.println();

                    } else { //if chosen plot does not exceed size, initiate planting
                        plant(user, plotId);
                    }
            //CLEARING
                } else if (choice.charAt(0) == 'L'){

                    int plotId = Integer.parseInt(choice.substring(1)); //get plot ID

                    clear(user, plotId);
            //HARVESTING
                } else if (choice.equals("H")){
                    harvest(user);
            //MAIN MENU
                } else if (choice.equals("M")){
                                        SQLManager.updateCache();
                    System.out.println();
                    System.out.println("Back to main menu...");
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Invalid input!");
                    System.out.println();
                }
            } catch (Exception e){
                System.out.println();
                System.out.println("Invalid input!");
                System.out.println();
            }
		}
	}
	
	/**
	* menu for planting
	* @param user the logged on user
	* @param plotId plot id of land to plant at
	*/
	//PLANTING
	private void plant(User user, int plotId){
		System.out.println("Select the crop: ");
		ArrayList<SeedBag> seedList = farmCtrl.loadAllSeedBag(user);
		ArrayList<Land> landList = farmCtrl.loadAllLand(user);
		Scanner sc = new Scanner(System.in);
		
		if (seedList.size() == 0){
			System.out.println();
			System.out.println("You have no seeds!");
			System.out.println();
		}
		
		for (int i = 0; i < seedList.size(); i++){
			SeedBag current = seedList.get(i);
			System.out.println(i + 1 + ". " + current.getCrop().getName());
		}
		
		System.out.print("[M]ain | Select Choice > "); //choose seedbag
		String choice = sc.nextLine();
		
		SeedBag chosenSeed = seedList.get(Integer.parseInt(choice) - 1);
		Land chosenLand = landList.get(plotId - 1);
		
		if (farmCtrl.plantSeed(user, plotId , chosenSeed.getCrop())){
			System.out.println();
			System.out.println(chosenSeed.getCrop().getName() + " planted on plot " + plotId);
			System.out.println();
		} else {
			System.out.println();
			System.out.println("Land is occupied!");
			System.out.println();
		}
	
	}
	
	/**
	* menu for clearing ALL at once
	* @param user the logged on user
	*/
	//CLEAR ALL (NOT USED AS OF NOW)
	private void clear(User user){
		ArrayList<Land> landList = farmCtrl.loadAllLand(user);
		System.out.println();
		System.out.println(farmCtrl.clearWilted(user) + " plots cleared. 5 gold deducted.");
		System.out.println();
		
	
	}
	
	/**
	* Clears a plot based on user's choice
	* @param plotId plot id of land to clear
	*/
	private void clear(User user, int plotId){
		ArrayList<Land> landList = farmCtrl.loadAllLand(user);
		
		Land chosenLand = landList.get(plotId - 1);
		
		
		if (farmCtrl.clearWilted(user, plotId)){
			System.out.println();
			System.out.println("Plot " + plotId + " is cleared. 5 gold deducted.");
			System.out.println();
		} else {
			System.out.println();
			System.out.println("Invalid choice!");
			System.out.println();
		}
	
	}

	/**
	* menu for harvesting
	* @param user the logged on user
	*/
	//HARVESTING
	private void harvest(User user){
		System.out.println();
		ArrayList<HarvestLog> harvestList = farmCtrl.harvest(user);
		for (int i = 0; i < harvestList.size(); i++){
			HarvestLog harvest = harvestList.get(i);
			System.out.println();
			System.out.println("You have harvested " + harvest.getQuantity() + " units of " + harvest.getCrop().getName()
				+ " for " + harvest.getExp() + " XP and " + harvest.getGold() + " gold.");
			System.out.println();
		}
	}
}