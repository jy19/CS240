package server.database;
import shared.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO {

	private Database db;
	
	UserDAO(Database db) {
		this.setDb(db);
	}
	/**
	 * use db's connection to get a user from the database
	 * @param userID
	 * @return
	 */
	public User getUser(String username){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			String sql = "Select * from users WHERE username = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setString(1,  username);
			rs = pstmt.executeQuery();
			while(rs.next()){
				user = new User(rs.getString("username"), rs.getString("password"), rs.getString("firstname"), rs.getString("lastname"), 
						rs.getString("email"), rs.getInt("indexrecords"), rs.getInt("image_id"), rs.getInt("id"));
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get user, SQL exception");
			e.printStackTrace();
		}  finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				System.out.println("Couldn't close prepared statement/resultset!?");
				e.printStackTrace();
			}
		}
		return user;
	}
	/**
	 * use db's connection to add a new user to the database
	 * @param user
	 */
	public int add(User user) {
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = db.getConnection();
		Statement stmt = null;
		//System.out.println("Connection: " + connection);
		try{
			String sql =  "INSERT INTO users" +
					"(username, password, firstname, lastname, email, indexRecords, image_id)" +
					"VALUES(?,?,?,?,?,?,?)";
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  user.getUsername());
			pstmt.setString(2,  user.getPassword());
			pstmt.setString(3, user.getFirstname());
			pstmt.setString(4, user.getLastname());
			pstmt.setString(5, user.getEmail());
			pstmt.setInt(6,  user.getIndexedrecords());
			pstmt.setInt(7,  user.getImageID());
			
			if(pstmt.executeUpdate() == 1){
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				user.setUserID(id);
			}
			else{
				System.out.println("Adding failed");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id;
	}
	/**
	 * use db's connection to update user info in database
	 * @param user
	 */
	public void update(User user) {
		//can only update which batch user is working on and how many records user has indexed.
		PreparedStatement pstmt = null; 
		
		try{
			String sql = "UPDATE users SET indexrecords = ?, image_id = ? WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  user.getIndexedrecords());
			pstmt.setInt(2,  user.getImageID());
			pstmt.setInt(3, user.getUserID());
			if(pstmt.executeUpdate() == 1){
				//ok
				System.out.println("User update successful");
			}
			else{
				//error
				System.out.println("Error when trying to update");
			}
		}
		catch(SQLException e){
			System.out.println("User update failed");
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
			} 
			catch(SQLException e){
				System.out.println("failed to close.");
				e.printStackTrace();
			}
		}
	}
	

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

}
