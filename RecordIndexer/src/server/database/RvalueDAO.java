package server.database;
import shared.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RvalueDAO {

	private Database db;
	
	RvalueDAO(Database db) {
		this.setDb(db);
	}
	/**
	 * use db's connection to get values in given field that matches given string
	 * @param fieldID
	 * @param inputValue
	 * @return a list of all values in the field that matches given string
	 */
	public ArrayList<Rvalue> getValue(int fieldID, String inputValue){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Rvalue> values = new ArrayList<Rvalue>();
		try {
			String sql = "SELECT * from rvalues WHERE field_id = ? AND rvalue = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  fieldID);
			pstmt.setString(2, inputValue);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Rvalue value = new Rvalue(rs.getInt("id"), rs.getInt("row_num"), rs.getInt("field_id"), 
						rs.getInt("image_id"), rs.getString("rvalue"));
				values.add(value);
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get value, SQL exception");
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
		return values;
	}
	/**
	 * use db's connection to query all record values in the table and return them
	 * @return list of record values
	 */
	public ArrayList<Rvalue> getAll() {
		ArrayList<Rvalue> allValues = new ArrayList<Rvalue>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from rvalues";
			pstmt = db.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Rvalue value = new Rvalue(rs.getInt("id"), rs.getInt("row_num"), rs.getInt("field_id"), 
						rs.getInt("image_id"), rs.getString("rvalue"));
				allValues.add(value);
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
				e.printStackTrace();
			}
			
		}
		return allValues;	
	}
	/**
	 * use db's connection to add a new record value to the database
	 * @param record value
	 */
	public int add(Rvalue value) {
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		try{
			String sql =  "INSERT INTO rvalues" +
					"(row_num, field_id, image_id, rvalue)" +
					"VALUES(?,?,?,?)";
			connection = db.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1,  value.getRowNum());
			pstmt.setInt(2,  value.getFieldID());
			pstmt.setInt(3, value.getImageID());
			pstmt.setString(4, value.getValue());
			
			
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
			e.printStackTrace();
		}
		finally{
			try{
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
				if(stmt != null) {stmt.close();}
			}
			 catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id;
	}
	/**
	 * use db's connection to update record value info in database
	 * @param record value
	 */
	public void update(Rvalue value) {
		PreparedStatement pstmt = null; 
		
		try{
			String sql = "UPDATE rvalues" + 
							"SET row_num = ?, field_id = ?, image_id = ?, rvalue = ?" +
							"WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  value.getRowNum());
			pstmt.setInt(2,  value.getFieldID());
			pstmt.setInt(3, value.getImageID());
			pstmt.setString(4,  value.getValue());
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
