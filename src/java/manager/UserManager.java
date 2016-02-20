package manager;

import java.io.*;
import java.util.*;
import entity.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import sql.SQLManager;


/**
* manages the Create, Retrieve, Update, Delete of Users 
*/

public class UserManager {
	/**
	* @param USERFILE location of the user database file
	* @param userList the temp memory of users list
	*/
	private final File USERFILE = new File("data/userFile.csv");
	private ArrayList<User> userList;
        private Connection conn;
	

		
	
	
	public UserManager(){
		userList = new ArrayList<>();
                load();
	}
	
    public UserManager(Connection conn){
        this.conn = conn;
        userList = new ArrayList<>();
        load(conn);
	}
        
        
        
	/**
	* Save into file
	*/
	public void save(){
		
		try{
			PrintStream writer = new PrintStream(new FileOutputStream(USERFILE, false));

			writer.println("Username,Full Name,Password,Gold,Exp");
			for (int i = 0; i < userList.size(); i++){
				User current = userList.get(i);
				writer.println(current.getUsername() + "," + current.getFullName() + "," + current.getPassword() + "," + current.getGold() + "," + current.getExp());
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
    public void load (){
        Connection conn = SQLManager.connect();
        Statement stmt = null;
        userList.clear();
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
            String sql = "select * from user";

            rs = stmt.executeQuery(sql);

            while (rs.next()){

                String username = rs.getString("username");
                String fullname = rs.getString("fullName");
                String password = rs.getString("password");
                int gold = rs.getInt("gold");
                int exp = rs.getInt("exp");

                userList.add(new User(username, fullname, password, gold, exp));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        SQLManager.closeConnection(rs, stmt, conn);
    }
        
    public void load (Connection conn){

        Statement stmt = null;
        userList.clear();
        ResultSet rs = null;
        try{
            stmt = conn.createStatement();
            String sql = "select * from user";

            rs = stmt.executeQuery(sql);

            while (rs.next()){

                String username = rs.getString("username");
                String fullname = rs.getString("fullName");
                String password = rs.getString("password");
                int gold = rs.getInt("gold");
                int exp = rs.getInt("exp");

                userList.add(new User(username, fullname, password, gold, exp));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }
        
	/**
	* Load into file
	*/
	/*public void load(){
		
		Scanner sc = null;
		try {
			sc = new Scanner(USERFILE);
		} catch (Exception e){
			e.printStackTrace();
		}
		sc.nextLine(); //skip headers
		userList.clear(); //clear arraylist before loading
		
		while (sc.hasNextLine()){
		
			Scanner scanLine = new Scanner(sc.nextLine());
			
			scanLine.useDelimiter(",");
			String retrUsername = scanLine.next();
			String retrName = scanLine.next();
			String retrPassword = scanLine.next();
			
			int gold = Integer.parseInt(scanLine.next());
			int exp = Integer.parseInt(scanLine.next());
			
			userList.add(new User(retrUsername, retrName, retrPassword, gold, exp));
		}
	}*/
	
	/**
	* Create a new user
	* @return create a new user
	* @param username the username of the user
	* @param fullName the full name of the user
	* @param password the password of the user
	*/
	/*public User createUser( String username, String fullName, String password){
		
		for (int i = 0; i < userList.size(); i++){
			User current = userList.get(i);
			if (current.getUsername().equals(username)){
				return null; //if username exists, createUser() fails
			}
		}
		
		User newUser = new User(username, fullName, password);
		userList.add(newUser);
		save();
	
		return newUser;
	}*/
        
    public User createUser( String username, String fullName, String password){
		User user = new User(username,fullName, password);
		
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = SQLManager.connect();
            stmt = conn.createStatement();
            String sql = "INSERT INTO user VALUES ("
                    +"\"" + user.getUsername() + "\","
                    +"\"" + user.getFullName() + "\","
                    +"\"" + user.getPassword() + "\","
                    +user.getGold() + ","
                    +user.getExp()
                    + ");"
                    ;
          //  System.out.println(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e){
            e.printStackTrace();
        }
		return user;
	}
	
	/**
	* Retrieve a user based on username
	* @return retrieve user based on username only
	* @param username the username of the user to be retrieved
	*/
	public User retrieveUser(String username){
            
		for (int i = 0; i < userList.size(); i++){
			User current = userList.get(i);
			if (current.getUsername().equals(username)){
				return current;
			}
		}
		return null;
	}
	
	/**
	*Retrieve a user based on username and password (includes validation)
	* @return retrieve user based on username and password (password must be correct, else null)
	* @param username the username of the user
	* @param password the password of the user
	*/
	public User retrieveUser(String username, String password){ //for login
		
		for (int i = 0; i < userList.size(); i++){
			User current = userList.get(i);
			if (current.getUsername().equals(username) && current.getPassword().equals(password)){
				return current;
			}
		}
		return null;
	}

	/**
	* Update a user
	* @param username the username of the updated user
	* @param newUser the user object with the updated details
	*/
	public void updateUser(String username, User newUser){
		for (int i = 0; i < userList.size(); i++){
			User current = userList.get(i);
			if (current.getUsername().equals(username)){
				userList.set(i, newUser);
			}
		}
		save();
                
	}
        
    public void updateUser(User newUser){
        String username = newUser.getUsername();
        String fullname = newUser.getFullName();
        String password = newUser.getPassword();
        int gold = newUser.getGold();
        int exp = newUser.getExp();

        String toCache = 
                "update user set "+
                "fullName=\"" + fullname + "\","+
                "password=\"" + password + "\","+
                "gold=" + gold + ","+
                "exp =" + exp + " " +
                "where username=\"" + username + "\""; 
        System.out.println(toCache);
        SQLManager.caches.add(toCache);
    }
}