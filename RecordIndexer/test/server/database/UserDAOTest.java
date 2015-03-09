package server.database;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import shared.model.User;

public class UserDAOTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Database.initialize();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	
	private static Database db;
	private UserDAO usertest;

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
		usertest = db.getUserDAO();
	}

	@After
	public void tearDown() throws Exception {
		// Roll back transaction so changes to database are undone
		db.endTransaction(false);
		db = null;
		usertest = null;
	}


	@Test
	public void testGetUser() {
		User test = usertest.getUser("bob");
		assertNull(test);
	}

	@Test
	public void testAdd() {
		User bob = new User("BobW", "password", "Bob", "White", 
				"bob@white.org", 0, -1, 50);
		User amy = new User("AmyB", "password2", "Amy", "Brown",
						"amy@brown.org", 2, -1, 100);
		
		usertest.add(bob);
		usertest.add(amy);
		
		User bobresult = usertest.getUser("BobW");
		User amyresult = usertest.getUser("AmyB");
		
		assertTrue(bob.equals(bobresult) && amy.equals(amyresult));
	}

	@Test
	public void testUpdate() {
		User test = new User("BobW", "password", "Bob", "White", 
				"bob@white.org", 0, 2, -1);
		
		usertest.add(test);
		test.setIndexedrecords(2);
		test.setImageID(5);
		usertest.update(test);
		
		User updated = usertest.getUser("BobW");
		
		assertTrue(test.equals(updated));
	}

}
