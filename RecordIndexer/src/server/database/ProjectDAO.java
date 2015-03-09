package server.database;
import shared.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProjectDAO {

	private Database db;
	
	ProjectDAO(Database db) {
		this.setDb(db);
	}
	public Project getProject(int projectID){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Project project = null;
		try {
			String sql = "SELECT * from projects WHERE id = ?";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  projectID);
			rs = pstmt.executeQuery();
			while(rs.next()){
				project = new Project(rs.getInt("id"), rs.getString("title"), rs.getInt("recordsperimage"), 
								rs.getInt("firstycoord"), rs.getInt("recordheight"));
			}
		} catch (SQLException e) {
			System.out.println("Couldn't get project, SQL exception");
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
		return project;
	}
	/**
	 * use db's connection to query all projects and return them
	 * @return list of projects
	 */
	public ArrayList<Project> getAll() {
		ArrayList<Project> allProjects = new ArrayList<Project>();
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		try{
			String sql = "SELECT * from projects";
			pstmt = db.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Project project = new Project(rs.getInt("id"), rs.getString("title"), rs.getInt("recordsperimage"), 
						rs.getInt("firstycoord"), rs.getInt("recordheight"));
				allProjects.add(project);
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
		return allProjects;	
	}
	/**
	 * use db's connection to add a new project to the database
	 * @param project
	 */
	public int add(Project project) {
		int id = 0;
		PreparedStatement pstmt = null; 
		ResultSet rs = null;
		Connection connection = null;
		Statement stmt = null;
		try{
			String sql =  "INSERT INTO projects" +
					"(title, recordsperimage, firstycoord, recordheight)" +
					"VALUES(?,?,?,?)";
			connection = db.getConnection();
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,  project.getTitle());
			pstmt.setInt(2,  project.getRecordsperimage());
			pstmt.setInt(3, project.getFirstycoord());
			pstmt.setInt(4, project.getRecordheight());
			
			
			if(pstmt.executeUpdate() == 1){
				stmt = connection.createStatement();
				rs = stmt.executeQuery("SELECT last_insert_rowid()");
				rs.next();
				id = rs.getInt(1);
				project.setID(id);
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
	 * use db's connection to update project info in database
	 * @param project
	 */
	public void update(Project project) {
		PreparedStatement pstmt = null; 
		
		try{
			String sql = "UPDATE projects " + 
							"SET title = ?, recordsperimage = ?, firstycoord = ?, recordheight = ?" +
							"WHERE id = ?";
			
			pstmt = db.getConnection().prepareStatement(sql);
			
			pstmt.setString(1,  project.getTitle());
			pstmt.setInt(2,  project.getRecordsperimage());
			pstmt.setInt(3, project.getFirstycoord());
			pstmt.setInt(4,  project.getRecordheight());
			pstmt.setInt(5,  project.getID());
			
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
