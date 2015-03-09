package server;

import org.junit.* ;
import static org.junit.Assert.* ;

public class ServerUnitTests {

	public static void main(String[] args) {
		
		String[] testClasses = new String[] {
				"server.database.UserDAOTest",
				"server.database.ProjectDAOTest",
				"server.database.FieldDAOTest",
				"server.database.ImageDAOTest",
				"server.database.RvalueDAOTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
	
}

