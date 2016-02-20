package entity;

/**
* Crop is a type of plant that can be planted and harvested
*/


public class Crop{
/**
* @param name the name of the crop
* @param cost the cost of the crop
* @param time the time required for the crop to mature, and the time for it to wilt after maturing
* @param exp the experience the user who harvests this crop can expect per unit
* @param minYield the minimum yield the user can expect to get from harvesting this crop
* @param maxYield the maximum yield the user can expect to get from harvesting this crop
* @param salePrice the cost of this crop
*/
	private String name;
	private int cost;
	private int time;
	private int exp;
	private int minYield;
	private int maxYield;
	private int salePrice;

	
	public Crop (String name, int cost, int time, int exp, int minYield, int maxYield, int salePrice){
		this.name = name;
		this.cost = cost;
		this.time = time;
		this.exp = exp;
		this.minYield = minYield;
		this.maxYield = maxYield;
		this.salePrice = salePrice;
		
	}
	
	/**
	* Get crop name
	*@return the name of the crop 
	*/
	public String getName(){
		return name;
	}
	
	
	/**
	* Get time for maturing and wilting
	* @return the time expected for crop to mature, and to wilt after maturing
	*/
	public int getTime(){
		return time;
	}
	
	/**
	* Get the experience for the crop
	*  @return the experience the crop provides per unit
	*/
	public int getExp(){
		
		return exp;
	}
	
	/**
	* Get the cost of the crop
	* @return the cost of the crop per unit
	*/
	public int getPrice(){
		return salePrice;
	}
	
	
	/**
	* Get the minimum yield
	* @return the minimum yield of this crop
	*/
	public int getMinYield(){
		return minYield;
	}
	
	/**
	* Get the maximum yield
	* @return the maximum yield of this crop
	*/
	public int getMaxYield(){
		return maxYield;
	}
	
}

