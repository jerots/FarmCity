/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sql;
import java.util.*;
import java.sql.*;

/**
 *
 * @author jeremyongts92
 */
public class SQLManager {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/farmcity";
    static final String USER = "adminkqnn2pM";
    static final String PASS = "lamA5vuSseqy";
    
    public static final ArrayList<String> caches = new ArrayList<>();
    
    
    public static Connection connect(){
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        }catch (Exception e){
            System.out.println("Database Connection failed.");
            e.printStackTrace();
        }
        return conn;
    }
    
    public static void closeConnection(ResultSet rs, Statement stmt, Connection conn){
       try {
            if (stmt != null){
                stmt.close();
            }
            if (rs != null){
                rs.close();
            }
            if (conn != null){
                conn.close();
            }
       } catch (SQLException e){
           e.printStackTrace();
       }
    }
    
    public static void closeConnection(Connection conn){
       try {
            if (conn != null){
                conn.close();
            }
       } catch (SQLException e){
           e.printStackTrace();
       }
    }
    
    public static void updateCache(){
        System.out.println("Saving...");
        Connection conn = connect();
        Statement stmt = null;
        
        try{
            stmt = conn.createStatement();
            for (String cache : caches){
                String sql = cache;
                stmt.addBatch(sql);
            }
            stmt.executeBatch();
            stmt.close();
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            closeConnection(conn);
        }
    }
}

/*
testing some conflict
*/