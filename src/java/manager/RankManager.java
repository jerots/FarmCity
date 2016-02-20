package manager;


import entity.*;
import java.io.*;
import java.util.*;

/**
* manages the Retrieval of Ranks 
*/


public class RankManager {
	/**
	* @param RANKFILE location of the rank database file
	* @param rankList the temp memory of ranks list
	*/
	private final File RANKFILE = new File("data/rank.csv");
	private ArrayList<Rank> rankList = new ArrayList<>();
	

	public RankManager(){
		load();
	}

	/**
	* Get rank based on experience
	* @return retrieve rank based on experience
	* @param exp the experience to match the ranking
	*/
	public Rank getRank (int exp){
		for (int i = 1; i < rankList.size(); i++){
			Rank current = rankList.get(i);
			if (exp < current.getExp()){
				return rankList.get(i - 1);
			}
		}
		
		return rankList.get(rankList.size()-1);
	
	}

	/**
	* load into file
	*/
	public void load(){
		Scanner sc = null;
		try {
			sc = new Scanner(RANKFILE);
			sc.nextLine(); //skip header
		} catch (Exception e){
			e.printStackTrace();
		}
		
		while (sc.hasNextLine()){
			
			Scanner scanLine = new Scanner(sc.nextLine());
			scanLine.useDelimiter(",");
			String rankName = scanLine.next();
			int exp = Integer.parseInt(scanLine.next());
			int plots = Integer.parseInt(scanLine.next());
			
			rankList.add(new Rank(rankName, exp, plots));
			
		}	
	}
}