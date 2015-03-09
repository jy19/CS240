package client;

import org.junit.*;
import static org.junit.Assert.*;

public class ClientUnitTests {

	public static void main(String[] args) {

		String[] testClasses = new String[] {
				"client.communication.ClientCommunicatorTest",
				"client.checker.CheckerTest"
		};

		org.junit.runner.JUnitCore.main(testClasses);
	}
}

