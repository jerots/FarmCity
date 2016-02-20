package ctrl;

import java.util.*;
import entity.*;
import java.sql.Connection;
import manager.*;
import sql.SQLManager;

/**
* Handles use cases: plant, harvest, clear
*/
public class FarmController {

	/**
	* @param user user logged on
	* @param rank rank of logged on user
	* @param plots the number of plots the user can have based on user's rank
	* @param rankMgr used to read user's rank
	* @param landMgr used to CRUD user's lands
	* @param cropMgr used to read crop details
	* @param userMgr used to CRUD user details
	*/
	private RankManager rankMgr;
	private LandManager landMgr;
	private CropManager cropMgr;
	private UserManager userMgr;
        private SeedBagManager seedMgr;
	
	
	
	/**
	* Constructor
	*/	
	public FarmController(){
            Connection conn = SQLManager.connect();
		rankMgr = new RankManager();
		cropMgr = new CropManager();
		userMgr = new UserManager(conn);
		landMgr = new LandManager(conn);
                seedMgr = new SeedBagManager(conn);
            SQLManager.closeConnection(conn);
	}
        
	
	/**
	* Retrieve user's rank
	* @param user the logged on user
	* @return user's rank
	*/	
	public Rank getRank(User user){
            return rankMgr.getRank(user.getExp());
	}
	
	public ArrayList<SeedBag> loadAllSeedBag(User user){  //load all user's seedbags
            
		
		ArrayList<SeedBag> result = new ArrayList<>();
		ArrayList<Crop> cropList = cropMgr.getAllCrop();
		for (int i = 0; i < cropList.size(); i++){
			Crop currentCrop = cropList.get(i);
			if (seedMgr.getSeedBag(user, currentCrop) != null){
				SeedBag current = seedMgr.getSeedBag(user, currentCrop);
				result.add(current);
			}

		}
		return result;
	
	}
        
        
	/**
	* Plants the seed on the land
	* @return whether seed was planted successfully
	* @param user the logged on user
	* @param position position of the land
	* @param crop type of crop to plant
	*/	
	public boolean plantSeed(User user, int position , Crop crop){
        if (landMgr.retrieveLand(user, position) != null){
			return false;
		}
		landMgr.createLand(user, position, crop.getName());
		useSeed(user, crop);
		
		return true;
	}
        
        public void useSeed(User user, Crop crop){
            
		SeedBag current = seedMgr.getSeedBag(user, crop);
		int newQty = current.getQuantity() - 1;
		if (newQty <= 0){
			seedMgr.deleteSeedBag(user, crop);
		}
		current.setQuantity(newQty);
		seedMgr.updateSeedBag(user, crop, current);
	}


	/**
	* Load all of user's lands
	* @return arraylist of all lands in server
	* @param user the logged on user
	*/
	public ArrayList<Land> loadAllLand(User user){
		int plots = getRank(user).getPlots();
		ArrayList<Land> result = new ArrayList<>(plots);
		
		for (int i = 1; i <= plots; i++){
			Land current = landMgr.retrieveLand(user, i);
			result.add(current);
		}
		return result;
	
	}

	/**
	* Harvest all mature crops
	* @param user the logged on user
	* @return arraylist of harvest records
	*/

	public ArrayList<HarvestLog> harvest(User user){
		ArrayList<HarvestLog> result = new ArrayList<>();
		int plots = getRank(user).getPlots();
		for (int i = 1; i <= plots; i ++){
			Land current = landMgr.retrieveLand(user, i);
			if (current != null && current.isMature() && !current.isWilted()) {
				Crop crop = current.getCrop();
				int quantity = crop.getMinYield();
				
				Random random = new Random();
				quantity += random.nextInt(crop.getMaxYield() - crop.getMinYield());
				int exp = crop.getExp() * quantity;
				int gold = crop.getPrice() * quantity;
				HarvestLog log = new HarvestLog(crop, quantity, exp, gold);
				result.add(log);
				
				landMgr.deleteLand(user, i);
				user.setGold(user.getGold() + gold);
				user.setExp(user.getExp() + exp);
				userMgr.updateUser(user);
			}
		}
		return result;
		
	}
	
	
	/**
	* Clear specified wilted plot
	* @return whether clear was successful
	* @param user the logged on user
	* @param position of land to clear
	*/
	//clear specific
	public boolean clearWilted(User user, int position){
		Land currentLand = landMgr.retrieveLand(user, position);
		if (currentLand == null || !currentLand.isWilted() || user.getGold() < 5){
			return false;
		}
		landMgr.deleteLand(user, position);
		user.setGold(user.getGold() - 5);
		userMgr.updateUser(user);
		
		return true;
	}
	
	/**
	* Clear all wilted plots
	* @param user the logged on user
	* @return the number of crops cleared
	*/
	
	//clear all
	public int clearWilted(User user){
		int counter = 0;
		int plots = getRank(user).getPlots();
		for (int i = 1; i <= plots; i++){
			Land currentLand = landMgr.retrieveLand(user, i);
			if (currentLand != null && currentLand.isWilted()){
				landMgr.deleteLand(user, i);
				counter++;
			}
		}
		
		
		user.setGold(user.getGold() - 5);
		userMgr.updateUser(user);
		
		return counter;
	}
	
	
}