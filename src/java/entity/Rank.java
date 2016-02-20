package entity;

/**
* the rank for each player according to the experience. allocates the number of plots the user can have
*/


public class Rank {


	/**
	* @param exp the experience required for this rank
	* @param plots the number of plots this rank allocates
	*/
	private String rankName;
	private int exp;
	private int plots;
	
	public Rank (String rankName, int exp, int plots){
		this.rankName = rankName;
		this.exp = exp;
		this.plots = plots;
	}
	
	/**
	* Get experience
	* @return the experience of the current user
	*/	
	public int getExp(){
		return exp;
	}
	
	/**
	* Get plots for the rank
	* @return the number of land allocated for user
	*/
	public int getPlots(){
		return plots;
	}

	/**
	* Get name of rank
	* @return the rank name
	*/
	public String getRankName(){
		return rankName;
	}
	
}