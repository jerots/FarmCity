package manager;

import java.io.*;
import java.util.*;
import java.text.*;
import entity.*;
import java.sql.Connection;

/**
* manages the Create, Retrieve, Update, Delete of Lands 
*/

public class LandManager {
	/**
	* @param LANDFILE location of the friend database file
	* @param landList the temp memory of friends list
	* @param userMgr the user manager for updating user
	* @param cropMgr the crop manager for retrieving crop info
	*/
	private final File LANDFILE = new File("data/land.csv");
	private ArrayList<Land> landList;
	private UserManager userMgr;
	private CropManager cropMgr;

	public LandManager(){
		landList = new ArrayList<>();
		userMgr = new UserManager();
		cropMgr = new CropManager();
		load();
	}
        
    public LandManager(Connection conn){
        landList = new ArrayList<>();
        userMgr = new UserManager(conn);
        cropMgr = new CropManager();
        load();
	}

	/**
	* Save into file
	*/
	public void save(){
		
		try{
			PrintStream writer = new PrintStream(new FileOutputStream(LANDFILE, false));
			DateFormat df = DateFormat.getDateTimeInstance();
			writer.println("Owner,Position,Crop,DatePlanted");
			for (int i = 0; i < landList.size(); i++){
				Land current = landList.get(i);
				
				writer.println(current.getOwner().getUsername() + "," + current.getPosition() + "," + current.getCrop().getName() + "," + df.format(current.getDate()));
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	* Load into file
	*/
	public void load(){
		
		Scanner sc = null;
		try {
			sc = new Scanner(LANDFILE);
		} catch (Exception e){
			e.printStackTrace();
		}
		sc.nextLine(); //skip headers
		landList.clear(); //clear arraylist before loading
		
		while (sc.hasNextLine()){
		
			Scanner scanLine = new Scanner(sc.nextLine());
			
			scanLine.useDelimiter(",");
			String retrUsername = scanLine.next();
			int position = Integer.parseInt(scanLine.next());
			String crop = scanLine.next();
			String date = scanLine.next() + "," + scanLine.next();
			try{
				DateFormat df = DateFormat.getDateTimeInstance();
				Date dateObj = df.parse(date);
                landList.add(new Land(userMgr.retrieveUser(retrUsername), position, cropMgr.getCrop(crop), dateObj));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	* Create a land
	* @return created land
	* @param owner the land owner
	* @param position the position of the land
	* @param crop the type of crop for the land
	*/
	public Land createLand( User owner, int position, String crop){
			
		Land newLand = new Land(owner, position, cropMgr.getCrop(crop));
		landList.add(newLand);
		save();
	
		return newLand;
	}
	
	/**
	* get land based on user and position
	* @return retrieve land based on owner and land position
	* @param owner the owner of the land
	* @param position the position of the land
	*/
	
	public Land retrieveLand(User owner, int position){
		for (int i = 1; i <= landList.size(); i++){
			Land current = landList.get(i-1);
			if (current.getOwner().getUsername().equals(owner.getUsername()) && current.getPosition() == position){
				return current;
			}
		}
		return null;
	}
    
	/**
	* delete land based on user and position
	* @param owner the owner of the land to be deleted
	* @param position the position of the land to be deleted
	*/
	
	public void deleteLand(User owner, int position){
		for (int i = 0; i < landList.size(); i++){
			Land current = landList.get(i);
			if (current.getOwner().getUsername().equals(owner.getUsername()) && current.getPosition() == position){
				landList.remove(i);
			}
		}
		save();
	}
}