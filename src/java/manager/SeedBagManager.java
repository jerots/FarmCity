package manager;

import java.io.*;
import java.util.*;
import entity.*;
import java.sql.Connection;


/**
* manages the Create, Retrieve, Update, Delete of Seedbags 
*/

public class SeedBagManager {
	/**
	* @param SEEDBAGFILE location of the seedbag database file
	* @param seedList the temp memory of friends list
	* @param userMgr the user manager needed to update users
	* @param cropMgr the crop manager needed to retrieve crop info
	*/
	private final File SEEDBAGFILE = new File("data/seeds.csv");
	private ArrayList<SeedBag> seedList = new ArrayList<>();
	UserManager userMgr;
	CropManager cropMgr;

	public SeedBagManager(){
        cropMgr = new CropManager();
        userMgr = new UserManager();
		load();
	}
        
        public SeedBagManager(Connection conn){
             cropMgr = new CropManager();
             userMgr = new UserManager(conn);
		load();
	}
	

	/**
	* Create a new seedbag
	* @param owner the owner of the seedbag
	* @param crop the type of crop for the seedbag
	* @param quantity the number of seeds in the seedbag
	*/
	public void createSeedBag (User owner, Crop crop, int quantity){
		seedList.add(new SeedBag(owner, crop, quantity));
		save();
	}
		
	
	/**
	* Get a user's seedbag
	* @return retrieve seedbag based on owner and crop type
	* @param owner the owner of the seedbag
	* @param crop the type of crop for the seedbag to retrieve
	*/
	public SeedBag getSeedBag (User owner, Crop crop){
		for (int i = 0; i < seedList.size(); i++){
			SeedBag current = seedList.get(i);
                       // System.out.println("owner: "+owner.getUsername());
                       // System.out.println("current: "+current.getOwner().getUsername());

			if (current.getOwner().getUsername()
                .equals(owner.getUsername()) 
                && current.getCrop().getName()
                .equals(crop.getName())){
				return current;
			}
		}
		
		return null;
	
	}

	/**
	* Change contents of a seedbag
	* @param owner owner of the seedbag
	* @param crop type of seedbag
	* @param updatedBag the bag with new details
	*/
	public void updateSeedBag(User owner, Crop crop, SeedBag updatedBag){
		for (int i = 0; i < seedList.size(); i++){
			SeedBag current = seedList.get(i);
			if (current.getOwner().getUsername().equals(owner.getUsername()) && crop.getName().equals(current.getCrop().getName())){
				seedList.set(i, updatedBag);
			}
		}
		save();
	}

	/**
	* Delete a seedbag
	* @param owner the owner of the bag 
	* @param crop the type of the seedbag
	*/
	public void deleteSeedBag(User owner, Crop crop){
		for (int i = 0; i < seedList.size(); i++){
			SeedBag current = seedList.get(i);
			if (current.getOwner().getUsername().equals(owner.getUsername()) && crop.getName().equals(current.getCrop().getName())){
				seedList.remove(i);
			}
		}
		save();
	}

	/**
	* Load into file
	*/
	public void load(){
		Scanner sc = null;
		try {
			sc = new Scanner(SEEDBAGFILE);
			sc.nextLine(); //skip header
		} catch (Exception e){
			e.printStackTrace();
		}
		
		while (sc.hasNextLine()){
			Scanner scanLine = new Scanner(sc.nextLine());
			scanLine.useDelimiter(",");
			String ownerName = scanLine.next();
			String cropName = scanLine.next();
			int quantity = Integer.parseInt(scanLine.next());			
			seedList.add(new SeedBag(userMgr.retrieveUser(ownerName), cropMgr.getCrop(cropName), quantity));
			
		}	
	}
	
	/**
	* Save into file
	*/
	public void save(){
		
		try{
			PrintStream writer = new PrintStream(new FileOutputStream(SEEDBAGFILE, false));

			writer.println("Owner,Crop,Quantity");
			for (int i = 0; i < seedList.size(); i++){
				SeedBag current = seedList.get(i);
				writer.println(current.getOwner().getUsername() + "," + current.getCrop().getName() + "," + current.getQuantity());
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}