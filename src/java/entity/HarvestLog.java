package entity;

/**
* Harvest log contains all the details in each harvested land
*/


public class HarvestLog {

	/**
	* @param crop the crop harvested
	* @param quantity the quantity of crop harvested
	* @param exp the experience yield
	* @param gold the gold yield
	*/
	private Crop crop;
	private int quantity;
	private int exp;
	private int gold;
	
	public HarvestLog (Crop crop, int quantity, int exp, int gold){
		this.crop = crop;
		this.quantity = quantity;
		this.exp = exp;
		this.gold = gold;
	
	}
	
	/**
	* Get harvest's crop
	* @return the type of crop in this harvest
	*/
	public Crop getCrop(){
		return crop;
	}

	/**
	* Get harvest quantity
	* @return the quantity of crop in this harvest
	*/
	public int getQuantity(){
		return quantity;
	}
	/**
	* Get harvest total experience
	* @return the experience yield in this harvest
	*/
	public int getExp(){
		return exp;
	}

	/**
	* Get harvest total gold
	* @return the gold yield in this harvest
	*/
	public int getGold(){
		return gold;
	}
}