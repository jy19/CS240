package server.database;
import shared.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ImageDAO {

	private Database db;
	
	ImageDAO(Database db) {
		this.setDb(db);
	}
	/**
	 * use db's connection to get image that belongs to user
	 * @param UserID
	 * @return user's image
	 */
	public Image getUserImage(int UserID){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Image userImage = null;
		try {
			String sql = "SELECT * from images WHERE user_id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  UserID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				userImage = new Image(rs.getInt("id"), rs.getString("file"), rs.getInt("project_id"), rs.getInt("current_state"), rs.getInt("user_id"));
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get user's image, SQL exception");
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
		return userImage;
	}
	/**
	 * use db's connection to get a list of images in a given project
	 * @param projectID
	 * @return list of images of given project id
	 */
	public ArrayList<Image> getImagesbyProjectID(int projectID){
		ArrayList<Image> imagesbyProject = new ArrayList<Image>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from images WHERE project_id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1, projectID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Image image = new Image(rs.getInt("id"), rs.getString("file"), rs.getInt("project_id"), 
								rs.getInt("current_state"), rs.getInt("user_id"));
				imagesbyProject.add(image);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println("Could not get images by projectID, sql connection fail?");
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return imagesbyProject;
	}
	/**
	 * use db's connection to get an image
	 * @param imageID
	 * @return image
	 */
	public Image getImage(int imageID){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Image image = null;
		try {
			String sql = "SELECT * from images WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  imageID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				image = new Image(rs.getInt("id"), rs.getString("file"), rs.getInt("project_id"), 
						rs.getInt("current_state"), rs.getInt("user_id"));
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get image, SQL exception");
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
		return image;
	}
	/**
	 * use db's connection to query all images and return them
	 * @return list of images
	 */
	public ArrayList<Image> getAll() {
		ArrayList<Image> allImages = new ArrayList<Image>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from images";
			pstmt = db.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Image image = new Image(rs.getInt("id"), rs.getString("file"), rs.getInt("project_id"), rs.getInt("current_state"), rs.getInt("user_id"));
				allImages.add(image);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println("Could not get all, sql connection fail?");
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			}
			 catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return allImages;	
	}
	/**
	 * use db's connection to add a new image to the database
	 * @param image
	 */
	public int add(Image image) {
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		try{
			String sql =  "INSERT INTO images" +
					"(file, project_id, current_state, user_id)" +
					"VALUES(?,?,?,?)";
			connection = db.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  image.getFile());
			pstmt.setInt(2,  image.getProjectID());
			pstmt.setInt(3, image.getCurrentState());
			pstmt.setInt(4, image.getUserID());
			
			
			if(pstmt.executeUpdate() == 1){
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
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
	 * use db's connection to update image info in database
	 * @param image
	 */
	public void update(Image image) {
		PreparedStatement pstmt = null; 
		
		try{
			String sql = "UPDATE images " + 
							"SET file = ?, project_id = ?, current_state = ?, user_id = ?" +
							"WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setString(1,  image.getFile());
			pstmt.setInt(2,  image.getProjectID());
			pstmt.setInt(3, image.getCurrentState());
			pstmt.setInt(4,  image.getUserID());
			pstmt.setInt(5,  image.getId());
			if(pstmt.executeUpdate() == 1){
				//ok
				System.out.println("Image update successful");
			}
			else{
				//error
				System.out.println("Error when trying to update");
			}
		}
		catch(SQLException e){
			System.out.println("image update failed");
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
