package manager;

import entity.*;
import java.io.*;
import java.util.*;

/**
* manages the Create, Retrieve, Update, Delete of Crop 
*/


public class CropManager {
	/**
	* @param CROPFILE location of the crop file
	* @param cropList the temp memory of crop list
	*/
	private final File CROPFILE = new File("data/crop.csv");
	private ArrayList<Crop> cropList = new ArrayList<>();


	
	public CropManager(){
		load();
	}
	
	/**
	* @return Retrieve Crop based on crop name
	* @param cropName name of crop
	*/
	public Crop getCrop (String cropName){
		for (int i = 0; i < cropList.size(); i++){
			Crop current = cropList.get(i);
			if (current.getName().equals(cropName)){
				return current;
			}
		}
		
		return null;
	
	}
	
	/**
	* @return Retrieve all crops as arraylist
	*/
	public ArrayList<Crop> getAllCrop(){
		return cropList;
	}
	
	/**
	* load into file
	*/
	public void load(){
		Scanner sc = null;
		try {
			sc = new Scanner(CROPFILE);
			sc.nextLine(); //skip header
		} catch (Exception e){
			e.printStackTrace();
		}
		
		while (sc.hasNextLine()){
			//Name,Cost,Time,XP,MinYield,MaxYield,SalePrice
			Scanner scanLine = new Scanner(sc.nextLine());
			scanLine.useDelimiter(",");
			String cropName = scanLine.next();
			int cost = Integer.parseInt(scanLine.next());
			int time = Integer.parseInt(scanLine.next());
			int exp = Integer.parseInt(scanLine.next());
			int minYield = Integer.parseInt(scanLine.next());
			int maxYield = Integer.parseInt(scanLine.next());
			int salePrice = Integer.parseInt(scanLine.next());
			
			cropList.add(new Crop(cropName, cost, time, exp, minYield, maxYield, salePrice));
			
		}	
	}
}