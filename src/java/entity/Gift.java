package entity;

import java.util.*;

/**
Gift record created when a user sends a gift.
*/

public class Gift{
	/**
	* @param sender sender of the gift
	* @param receiver receiver of the gift
	* @param crop type of crop gifted
	* @param date date of the gifting
	*/ 
	private User sender;
	private User receiver;
	private Crop crop;
	private Date date;


	public Gift (User sender, User receiver, Crop crop, Date date){
		
		this.sender =sender;
		this.receiver=receiver;
		this.crop=crop;
		this.date=date;	
		
		
	}
	
	/**
	* Get gift sender
	* @return get gift sender 
	*/
	public User getSender(){
		return sender;
	}
	
	/**
	* Get gift receiver
	* @return get gift receiver
	*/
	
	public User getReceiver(){
		return receiver;
	}
	
	/**
	* Get crop gifted
	* @return get type of crop gifted
	*/
	public Crop getCrop(){
		return crop;
	}
	
	/**
	* Get gift date
	* @return get date of the gifting 
	*/
	public Date getDate(){
		return date;
	}
		

}