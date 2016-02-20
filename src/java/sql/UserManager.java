package sql;

import java.util.*;
import entity.*;
import java.sql.*;


/**
* manages the Create, Retrieve, Update, Delete of Users 
*/

public class UserManager {
	/**
	* @param USERFILE location of the user database file
	* @param userList the temp memory of users list
	*/
	private ArrayList<User> userList;
	
	public UserManager(){
		userList = new ArrayList<>();
		load();
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
	
	/**
	* Create a new user
	* @return create a new user
	* @param username the username of the user
	* @param fullName the full name of the user
	* @param password the password of the user
	*/
	public User createUser( String username, String fullName, String password){
		User user = new User(username,fullName, password);
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {

            stmt = conn.createStatement();
            String sql = "INSERT INTO USER VALUES ("
                    +"\"" + user.getUsername() + "\""
                    +"\"" + user.getFullName() + "\""
                    +"\"" + user.getPassword() + "\""
                    +user.getGold() + "\""
                    +user.getExp() +"\""
                    + ");"
                    ;
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
        User result = null;
        Connection conn = SQLManager.connect();
        try{
            Statement stmt = conn.createStatement();
            String sql = "";
            sql = "select * from user where username =" + username;
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()){
                //username = rs.getString("username");

                String fullname = rs.getString("fullName");
                String password = rs.getString("password");
                int gold = rs.getInt("gold") ;
                int exp = rs.getInt("exp");
                result = new User(username,fullname, password, gold, exp);
            }
        } catch (SQLException e){
             e.printStackTrace();
        }
        return result;
	}
	
	/**
	*Retrieve a user based on username and password (includes validation)
	* @return retrieve user based on username and password (password must be correct, else null)
	* @param username the username of the user
	* @param password the password of the user
	*/
	public User retrieveUser(String username, String password){ //for login
		return null;
	}

	/**
	* Update a user
	* @param username the username of the updated user
	* @param newUser the user object with the updated details
	*/
	public void updateUser(String username, User newUser){
	
	}
}