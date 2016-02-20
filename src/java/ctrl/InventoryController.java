package ctrl;


import entity.*;
import java.sql.Connection;
import manager.*;
import java.util.*;
import sql.SQLManager;

/**
* Handles use cases: gifting, buy seeds
*/

public class InventoryController {

	/**
	* @param user the logged on user
	* @param cropMgr to CRUD crops
	* @param userMgr to CRUD users
	* @param seedMgr to CRUD seedbags
	*/
	private CropManager cropMgr;
	private UserManager userMgr;
        private SeedBagManager seedMgr;
        private RankManager rankMgr;
	
	
        public InventoryController(){
            Connection conn = SQLManager.connect();
            userMgr = new UserManager(conn);
            seedMgr = new SeedBagManager(conn);
            cropMgr = new CropManager();
            rankMgr = new RankManager();
            SQLManager.closeConnection(conn);
        }
        
        public InventoryController(Connection conn){
            userMgr = new UserManager(conn);
            seedMgr = new SeedBagManager();
            cropMgr = new CropManager();
        }
	
        public Rank getRank(User user){
            return rankMgr.getRank(user.getExp());
	}
        
        
	/**
	* Deduct seed from seedbag
	* @param crop the type of crop to use
	* @param user the logged on user
	*/
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
	* Add seed to seedbag
	* @param user the logged on user
	* @param crop the type of crop to add
	* @param qty the amount of seeds to add
	*/
	
	public void addSeed(User user, Crop crop, int qty){
            
		SeedBag current = seedMgr.getSeedBag(user, crop);
		if (current == null){
			seedMgr.createSeedBag(user,crop,qty);
		} else {
			int newQty = current.getQuantity() + qty;
			current.setQuantity(newQty);
			seedMgr.updateSeedBag(user,crop,current);
		}
	}


	/**
	* Deduct gold from user
	* @return return true if gold is deducted successfully, otherwise false
	* @param user the logged on user
	* @param cost the amount of gold to deduct
	*/
	public boolean deductGold(User user, int cost){
           
		if (user.getGold() < cost){
			return false;
		} else {
			user.setGold(user.getGold() - cost);
			userMgr.updateUser(user);
		}
		return true;
	
	}
	
	
	/**
	* Retrieve ArrayList of seedbags
	* @param user the logged on user
	* @return the list of all the logged on user's seedbags
	*/
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
	* Get all the crops
	* @return the list of all the crops available
	*/
	public ArrayList<Crop> getShopList(){
            
		return cropMgr.getAllCrop();
	}
		
}