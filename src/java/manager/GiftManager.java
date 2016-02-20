package manager;

import java.io.*;
import java.util.*;
import java.text.*;
import entity.*;

/**
* manages the Create, Retrieve, Update, Delete of Gifts 
*/


public class GiftManager {
	/**
	* @param GIFTFILE location of the gift database file
	* @param giftList the temp memory of gifts list
	* @param userMgr the user manager for updating users
	* @param cropMgr the crop manager for retrieving crops
	*/	
	private final File GIFTFILE = new File("data/gift.csv");
	private ArrayList<Gift> giftList;
	private UserManager userMgr;
	private CropManager cropMgr;
	


	public GiftManager(){
		giftList = new ArrayList<>();
		userMgr = new UserManager();
		cropMgr = new CropManager();
		load();
	}


	/**
	* Save into file
	*/
	public void save(){
		
		try{
			PrintStream writer = new PrintStream(new FileOutputStream(GIFTFILE, false));
			DateFormat df = DateFormat.getDateTimeInstance();
			writer.println("Sender,Receiver,Crop,Date");
			for (int i = 0; i < giftList.size(); i++){
				Gift current = giftList.get(i);
				writer.println(current.getSender().getUsername() + "," + current.getReceiver().getUsername() + "," + current.getCrop().getName() + "," + df.format(current.getDate()));
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	* load into file
	*/
	public void load(){
		
		Scanner sc = null;
		try {
			sc = new Scanner(GIFTFILE);
		} catch (Exception e){
			e.printStackTrace();
		}
		sc.nextLine(); //skip headers
		giftList.clear(); //clear arraylist before loading
		
		while (sc.hasNextLine()){
		
			Scanner scanLine = new Scanner(sc.nextLine());
			
			scanLine.useDelimiter(",");
			String sender = scanLine.next();
			String receiver = scanLine.next();
			String crop = scanLine.next();
			String date = scanLine.next() + "," + scanLine.next();
			try{
				DateFormat df = DateFormat.getDateTimeInstance();
				Date dateObj = df.parse(date);
					
				giftList.add(new Gift(userMgr.retrieveUser(sender), userMgr.retrieveUser(receiver), cropMgr.getCrop(crop), dateObj));
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	
	/**
	* Create a new gift
	* @param sender the gift sender
	* @param receiver the gift receiver
	* @param crop the type of seed being given
	* @param date the date of gifting
	* @return the gift object
	*/
	public Gift createGift(User sender, User receiver, Crop crop, Date date){	
		Gift newGift = new Gift(sender, receiver, crop, date);
		giftList.add(newGift);
		save();
		return newGift;
	}
	
	/**
	* Retrieve all gifts by sender
	* @param sender person sending the gift
	* @return gift list 
	*/
		
	//retrieve gifts by user (NOT USED)
	public ArrayList<Gift> retrieveGifts(User sender){
		ArrayList<Gift> result = new ArrayList<>();
		for (int i = 0; i < giftList.size(); i++){
			Gift current = giftList.get(i);
			if (current.getSender().getUsername().equals(sender.getUsername())){
				result.add(current);
			}
		}
		return result;
	}
	
	/**
	* Retrieve gifts by users today (past 24 hours)
	* @param sender the gift sender
	* @return retrieve gift record past 24 hours
	*/
	public ArrayList<Gift> retrieveGifts24H(User sender){
		ArrayList<Gift> result = new ArrayList<>();
		Date currentDate = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		cal.add(Calendar.HOUR, 24); // adds 24 hour
		
		Date thresholdDate = cal.getTime();
		
		for (int i = 0; i < giftList.size(); i++){
			Gift current = giftList.get(i);
			Date giftDate = current.getDate();
			if (current.getSender().getUsername().equals(sender.getUsername()) && !giftDate.after(thresholdDate)){
				result.add(current);
			}
		}
		return result;
	}
	
	/**
	* Retrieve gifts by users today (after 0000h)
	* @return retrieve gift record today (after 0000h)
	* @param sender the gift sender
	*/
	public ArrayList<Gift> retrieveGiftsToday(User sender){
		ArrayList<Gift> result = new ArrayList<>();
        
		Calendar currentDate = Calendar.getInstance();
        
		for (int i = 0; i < giftList.size(); i++){
			Gift current = giftList.get(i);
			Date giftDate = current.getDate();
			
			Calendar giftCal = Calendar.getInstance();
			giftCal.setTime(giftDate);

			if (current.getSender().getUsername().equals(sender.getUsername())){ //if sender gifting found
				
				if (currentDate.get(Calendar.YEAR) == giftCal.get(Calendar.YEAR) &&
					currentDate.get(Calendar.MONTH) == giftCal.get(Calendar.MONTH) &&
					currentDate.get(Calendar.DAY_OF_MONTH) == giftCal.get(Calendar.DAY_OF_MONTH)){
					
					//gift was sent today
					result.add(current);
				}
			}
		}
		return result;
	}
}