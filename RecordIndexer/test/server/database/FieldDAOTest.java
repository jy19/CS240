package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.model.Field;

public class FieldDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	private static Database db;
	private FieldDAO fieldTester;
	@Before
	public void setUp() throws Exception {
		db = new Database();
		File dbsetupFile = new File("ClearTable.txt");
		try {
			Scanner scanner = new Scanner(dbsetupFile);
			String sql = "";
			while(scanner.hasNextLine()){
				sql += scanner.nextLine() + "\n";
			}
			db.startTransaction();
			db.getConnection().createStatement().executeUpdate(sql);
			db.endTransaction(true);
		} catch (FileNotFoundException| SQLException e) {
			e.printStackTrace();
			System.out.println("no such file to set up test");
		}

		// Prepare database for test case	
		db = new Database();
		db.startTransaction();
		fieldTester = db.getFieldDAO();
	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		fieldTester = null;
	}

	@Test
	public void testGetField() {
		Field test = fieldTester.getField(1);
		assertNull(test);
	}
	
	public void testGetFieldsbyProjectID(){
		Field field1 = new Field(-1, "title", 50, 100, "help!", "knowndata", 2);
		Field field2 = new Field(-1, "titleeee", 60, 120, "helpagain", "known", 2);
		Field field3 = new Field(-1, "title!!!", 10, 20, "html", "known!", 5);
		Field field4 = new Field(-1, "title.", 20, 40, "halp", "knoww", 3);
		
		ArrayList<Field> fields = fieldTester.getFieldsbyProjectID(2);
		
		assertEquals(2, fields.size());
	}
	
	public void testGetAll(){
		ArrayList<Field> allFields = fieldTester.getAll();
		assertEquals(0, allFields.size());
	}
	
	public void testAdd(){
		Field field1 = new Field(-1, "title", 50, 100, "help!", "knowndata", 2);
		Field field2 = new Field(-1, "titleeee", 60, 120, "helpagain", "known", 2);
		
		fieldTester.add(field1);
		fieldTester.add(field2);
		
		ArrayList<Field> allFields = fieldTester.getAll();
		assertEquals(2, allFields.size());
		
		Field result1 = allFields.get(0);
		Field result2 = allFields.get(1);
		
		assertTrue(field1.equals(result1) && field2.equals(result2));
	}
	
	public void testUpdate(){
		Field field = new Field(-1, "title!!!", 10, 20, "html", "known", 5);
		
		fieldTester.add(field);
		field.setHelphtml("html2!");
		field.setKnowndata("different known");
		
		fieldTester.update(field);
		
		Field updated = fieldTester.getField(1);
		
		assertTrue(field.equals(updated));
	}

}
