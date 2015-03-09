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

import shared.model.Rvalue;

public class RvalueDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	private static Database db;
	private RvalueDAO valueTester;

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
		valueTester = db.getValueDAO();
	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		valueTester = null;
	}

	@Test
	public void testGetValue() {
		ArrayList<Rvalue> test = valueTester.getValue(2, "input!?");
		assertEquals(0, test.size());
	}
	
	public void testGetAll(){
		ArrayList<Rvalue> allValues = valueTester.getAll();
		assertEquals(0, allValues.size());
	}
	
	public void testAdd(){
		Rvalue v1 = new Rvalue(-1, 3, 5, 2, "value1");
		Rvalue v2 = new Rvalue(-1, 1, 2, 3, "value2!");
		
		valueTester.add(v1);
		valueTester.add(v2);
		
		ArrayList<Rvalue> allValues = valueTester.getAll();
		assertEquals(2, allValues.size());
		
		Rvalue result1 = allValues.get(0);
		Rvalue result2 = allValues.get(1);
		
		assertTrue(v1.equals(result1) && v2.equals(result2));
	}
	
	public void testUpdate(){
		Rvalue value = new Rvalue(-1, 2, 3, 1, "valueasdadsag");
		
		valueTester.add(value);
		value.setValue("meow");
		
		valueTester.update(value);
		
		ArrayList<Rvalue> updated = valueTester.getValue(3, "meow");
		
		assertTrue(value.equals(updated.get(0)));
		
	}

}
