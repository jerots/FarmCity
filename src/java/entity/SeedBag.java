package entity;

/**
* the seedbags in a user's inventory. each seedbag contains seeds for a particular crop
*/

public class SeedBag {

	/**
	* @param owner the owner of the seedbag
	* @param crop the type of seed in the seedbag
	* @param quantity the number of seeds in the seedbag
	*/
	private User owner;
	private Crop crop;
	private int quantity;
	
	public SeedBag(User owner, Crop crop, int quantity){
		this.owner = owner;
		this.crop = crop;
		this.quantity = quantity;
	}

	/**
	* Get owner (User)
	* @return the owner of the seedbag
	*/	
	public User getOwner(){
		return owner;
	}
	
	/**
	* Get crop
	* @return the type of crop in the seedbag
	*/	
	public Crop getCrop(){
		return crop;
	}
	
	/**
	* Get quantity
	* @return the number of seeds in the seedbag
	*/
	public int getQuantity(){
		return quantity;
	}
	/**
	* Set new seedbag quantity
	* @param newQty the new quantity of seeds
	*/
	public void setQuantity(int newQty){
		this.quantity = newQty;
	}



}