package client.checker;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.TreeSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import server.database.Database;

public class CheckerTest {
	
	private static Set<String> dictionary;
	private Set<String> suggestions;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dictionary = new TreeSet<String>();
		dictionary.add("BYU");
		dictionary.add("UCLA");
		dictionary.add("SUN");
		dictionary.add("ORANGE");
		dictionary.add("SAND");
		dictionary.add("CAT");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		return;
	}
	
	@Test
	public void testGetSuggestions() {
		Checker spellTest = new Checker();
		spellTest.setDictionary(dictionary);
		Set<String> actualSuggestions = spellTest.getSuggestions("UA");
		Set<String> supposedSuggestions = new TreeSet<String>();
		supposedSuggestions.add("UCLA");
		supposedSuggestions.add("SUN");
		supposedSuggestions.add("CAT");
		
		assertEquals(actualSuggestions, supposedSuggestions);
	}

}
