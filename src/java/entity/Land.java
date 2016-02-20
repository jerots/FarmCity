package entity;

import java.util.*;
import java.text.*;


/**
* the land on each plot, including its position and type of crop and plant date
*/

public class Land {

	/**
	* @param owner the owner of the land
	* @param position the position of the land
	* @param crop the type of crop of the land
	* @param startDate the date planted
	*/
	private User owner;
	private int position;
	private Crop crop;
	Date startDate;
	
	
	/**
	* Create new land with current time
	* @param owner land owner
	* @param position land position
	* @param crop type of crop
	*/
	public Land (User owner, int position, Crop crop){
		this.owner = owner;
		this.position = position;
		this.crop = crop;
		startDate = new Date();
	
	}
	
	
	/**
	* Create new land with specified time
	* @param owner land owner
	* @param position land position
	* @param crop type of crop
	* @param date the date of planting
	*/
	public Land (User owner, int position, Crop crop, Date date){
		this.owner = owner;
		this.position = position;
		this.crop = crop;
		startDate = date;		
	}
	
	/**
	* Get planted date
	* @return the date planted
	*/
	public Date getPlantedDate(){
		return startDate;
	}
	
	/**
	* Get mature date
	* @return the date it matures
	*/
	public Date getMatureDate (){
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.MINUTE, crop.getTime()); // adds one hour
		return cal.getTime();
	}
	
	/**
	* Check whether land is mature
	* @return true if the land is mature, false otherwise
	*/
	public boolean isMature(){
		Date current = new Date();
		return current.after(getMatureDate());
	}
	
	/**
	* Get wilt date
	* @return the date when the land wilts
	*/
	public Date getWiltDate(){
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.MINUTE, crop.getTime() * 2);
		return cal.getTime();
	}
		
	/**
	* Check whether land has wilted
	* @return true if the land has wilted, false otherwise
	*/		
	public boolean isWilted(){
		Date current = new Date();
		return current.after(getWiltDate());
	}
	
	/**
	* Get owner
	* @return the land's owner
	*/
	public User getOwner(){
		return owner;
	}
	
	/**
	* Get land position
	* @return the land's position
	*/	
	public int getPosition(){
		return position;
	}
	/**
	* Get crop
	* @return the type of crop in this land
	*/		
	public Crop getCrop(){
		return crop;
	}
	
	/**
	* Get planted date
	* @return the date it was planted
	*/
	public Date getDate(){
		return startDate;
	}

	/**
	* Get maturity progress
	* @return the maturity progress, in percentage
	*/
	public double getProgress(){
	
		Date current = new Date();
		double totalTime = getMatureDate().getTime() - startDate.getTime();
		double passedTime = current.getTime() - startDate.getTime();
		if (passedTime/totalTime > 1){
			return 1.0;
		}
		return passedTime/totalTime;
	}
}