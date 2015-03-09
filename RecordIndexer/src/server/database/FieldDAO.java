package server.database;
import shared.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FieldDAO {

	private Database db;
	
	FieldDAO(Database db) {
		this.setDb(db);
	}
	
	public Field getField(int FieldID){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Field field = null;
		try {
			String sql = "Select * from fields WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  FieldID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				field = new Field(rs.getInt("id"), rs.getString("title"), rs.getInt("xcoordinate"), rs.getInt("width"), rs.getString("helphtml"), 
						rs.getString("knowndata"), rs.getInt("project_id"));
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get field, SQL exception");
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
		return field;
	}
	public ArrayList<Field> getFieldsbyProjectID(int ProjectID){
		ArrayList<Field> fieldsofProject = new ArrayList<Field>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Field field = null;
		try {
			String sql = "Select * from fields WHERE project_id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  ProjectID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				field = new Field(rs.getInt("id"), rs.getString("title"), rs.getInt("xcoordinate"), rs.getInt("width"), rs.getString("helphtml"), 
						rs.getString("knowndata"), rs.getInt("project_id"));
				fieldsofProject.add(field);
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get fields of project, SQL exception");
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
		return fieldsofProject;
	}
	/**
	 * use db's connection to query all fields and return them
	 * @return list of fields
	 */
	public ArrayList<Field> getAll() {
		ArrayList<Field> allFields = new ArrayList<Field>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from fields";
			pstmt = db.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Field field = new Field(rs.getInt("id"), rs.getString("title"), rs.getInt("xcoordinate"), rs.getInt("width"), 
						rs.getString("helphtml"), rs.getString("knowndata"), rs.getInt("project_id"));
				allFields.add(field);
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
		return allFields;		
	}
	/**
	 * use db's connection to add a new field to the database
	 * @param field
	 */
	public int add(Field field) {
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		try{
			String sql =  "INSERT INTO fields" +
					"(title, xcoordinate, width, helphtml, knowndata, project_id)" +
					"VALUES(?,?,?,?,?,?)";
			connection = db.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  field.getTitle());
			pstmt.setInt(2,  field.getXcoordinate());
			pstmt.setInt(3, field.getWidth());
			pstmt.setString(4, field.getHelphtml());
			pstmt.setString(5, field.getKnowndata());
			pstmt.setInt(6, field.getProjectID());
			
			
			if(pstmt.executeUpdate() == 1){
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				field.setId(id);
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
	 * use db's connection to update field info in database
	 * @param field
	 */
	public void update(Field field) {
		PreparedStatement pstmt = null; 
		
		try{
			String sql = "UPDATE fields" + 
							"SET title = ?, xcoordinate = ?, width = ?, helphtml = ?," +
							"knowndata = ?, project_id = ?" +
							"WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setString(1,  field.getTitle());
			pstmt.setInt(2,  field.getXcoordinate());
			pstmt.setInt(3, field.getWidth());
			pstmt.setString(4,  field.getHelphtml());
			pstmt.setString(5,  field.getKnowndata());
			pstmt.setInt(6,  field.getProjectID());
			pstmt.setInt(7,  field.getId());
			if(pstmt.executeUpdate() == 1){
				//ok
				System.out.println("Update successful");
			}
			else{
				//error
				System.out.println("Error when trying to update");
			}
		}
		catch(SQLException e){
			System.out.println("update failed");
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
