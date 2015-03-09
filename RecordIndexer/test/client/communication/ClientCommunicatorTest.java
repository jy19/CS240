package client.communication;

import static org.junit.Assert.*;

import java.util.ArrayList;

import importer.DOMParser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import shared.communication.*;
import shared.model.*;

public class ClientCommunicatorTest {
	
	private static ClientCommunicator communicator;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//initialize communicator? -- probably with host/port
		communicator = new ClientCommunicator();
		communicator.getHostPort("localhost", "8080");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		DOMParser.main(null);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testValidateUser() {
		//test correct pw
		ValidateUserIn input = new ValidateUserIn("test1", "test1");
		ValidateUserOut output = communicator.validateUser(input);
		User user = new User("test1", "test1", "Test", "One", "test1@gmail.com", 0, 1, -1);
		ValidateUserOut result = new ValidateUserOut(true, user);
		assertTrue(output.isApproved());
		assertTrue(output.equals(result));
		//test incorrect pw 
		ValidateUserIn input2 = new ValidateUserIn("test1", "wrong");
		ValidateUserOut output2 = communicator.validateUser(input2);
		assertFalse(output2.isApproved());
	}
	
	@Test
	public void testGetProjects(){
		//test correct pw
		GetProjectsIn input = new GetProjectsIn("test1", "test1");
		GetProjectsOut output = communicator.getProject(input);
		Project p1 = new Project(1, "1890 Census", 8, 199, 60);
		Project p2 = new Project(2, "1900 Census", 10, 204, 62);
		Project p3 = new Project(3, "Draft Records", 7, 195, 65);
		ArrayList<Project> projects = new ArrayList<Project>();
		projects.add(p1);
		projects.add(p2);
		projects.add(p3);
		GetProjectsOut result = new GetProjectsOut(true, projects);
		assertTrue(output.equals(result));
		//test incorrect pw/fail
		GetProjectsIn input2 = new GetProjectsIn("bob123", "wrong");
		GetProjectsOut output2 = communicator.getProject(input2);
		assertNull(output2);
	}
	
	@Test
	public void testGetSampleImage(){
		//test correct pw
		SampleImageIn input = new SampleImageIn("test1", "test1", 1);
		SampleImageOut output = communicator.getImage(input);
		Image image = new Image(1, "images/1890_image0.png", 1, -1, -1);
		SampleImageOut result = new SampleImageOut(image);
		assertTrue(output.equals(result));
		//test incorrect pw/fail
		SampleImageIn input2 = new SampleImageIn("bob", "wrong", 2);
		SampleImageOut output2 = communicator.getImage(input2);
		assertNull(output2);
		
	}
	
	@Test
	public void testDownloadBatch(){
		//test correct pw
		DownloadBatchIn input = new DownloadBatchIn("test1", "test1", 1);
		DownloadBatchOut output = communicator.getBatch(input);
		Project project = new Project(1, "1890 Census", 8, 199, 60);
		Image image = new Image(1, "images/1890_image0.png", 1, -1, -1);
		//field array needed
		Field f1 = new Field(1, "Last Name", 60, 300, "fieldhelp/last_name.html", 
				"knowndata/1890_last_names.txt", 1);
		Field f2 = new Field(2, "First Name", 360, 280, "fieldhelp/first_name.html", 
				"knowndata/1890_first_names.txt", 1);
		Field f3 = new Field(3, "Gender", 640, 205, "fieldhelp/gender.html", 
				"knowndata/genders.txt" , 1);
		Field f4 = new Field(4, "Age", 845, 120, "fieldhelp/age.html", "", 1);
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.add(f1);
		fields.add(f2);
		fields.add(f3);
		fields.add(f4);
		DownloadBatchOut result = new DownloadBatchOut(project, image, fields);
		assertTrue(output.equals(result));
		//test incorrect pw/fail
		DownloadBatchIn input2 = new DownloadBatchIn("bob123", "wrong",1);
		DownloadBatchOut output2 = communicator.getBatch(input2);
		assertNull(output2);
		
	}
	
	@Test
	public void testGetFields(){
		//test correct pw w/projectID
		GetFieldsIn input = new GetFieldsIn("test1", "test1", 1);
		GetFieldsOut output = communicator.getFields(input);
		Field f1 = new Field(1, "Last Name", 60, 300, "fieldhelp/last_name.html", 
				"knowndata/1890_last_names.txt", 1);
		Field f2 = new Field(2, "First Name", 360, 280, "fieldhelp/first_name.html", 
				"knowndata/1890_first_names.txt", 1);
		Field f3 = new Field(3, "Gender", 640, 205, "fieldhelp/gender.html", 
				"knowndata/genders.txt" , 1);
		Field f4 = new Field(4, "Age", 845, 120, "fieldhelp/age.html", "", 1);
		ArrayList<Field> fields = new ArrayList<Field>();
		fields.add(f1);
		fields.add(f2);
		fields.add(f3);
		fields.add(f4);
		GetFieldsOut result = new GetFieldsOut(input.getProjectID(), fields);
		assertTrue(output.equals(result));
		//test correct pw without projectID
		GetFieldsIn input2 = new GetFieldsIn("test1", "test1", -1);
		GetFieldsOut output2 = communicator.getFields(input2);
		Field f5 = new Field(5, "Gender", 45, 205, "fieldhelp/gender.html", 
				"knowndata/genders.txt" , 2);
		Field f6 = new Field(6, "Age", 250, 120, "fieldhelp/age.html", "", 2);
		Field f7 = new Field(7, "Last Name", 370, 325, "fieldhelp/last_name.html", 
				"knowndata/1900_last_names.txt", 2);
		Field f8 = new Field(8, "First Name", 695, 325, "fieldhelp/first_name.html", 
				"knowndata/1900_first_names.txt", 2);
		Field f9 = new Field(9, "Ethnicity", 1020, 450, "fieldhelp/ethnicity.html",
				"knowndata/ethnicities.txt", 2);
		Field f10 = new Field(10, "Last Name", 75, 325, "fieldhelp/last_name.html", 
				"knowndata/draft_last_names.txt", 3);
		Field f11 = new Field(11, "First Name", 400, 325, "fieldhelp/first_name.html", 
				"knowndata/draft_first_names.txt", 3);
		Field f12 = new Field(12, "Age", 725, 120, "fieldhelp/age.html", "", 3);
		Field f13 = new Field(13, "Ethnicity", 845, 450, "fieldhelp/ethnicity.html",
				"knowndata/ethnicities.txt", 3);
		ArrayList<Field> allFields = new ArrayList<Field>();
		allFields.add(f1);
		allFields.add(f2);
		allFields.add(f3);
		allFields.add(f4);
		allFields.add(f5);
		allFields.add(f6);
		allFields.add(f7);
		allFields.add(f8);
		allFields.add(f9);
		allFields.add(f10);
		allFields.add(f11);
		allFields.add(f12);
		allFields.add(f13);
		GetFieldsOut result2 = new GetFieldsOut(-1, allFields);
		assertTrue(output2.equals(result2));
		//test incorrect pw/fail
		GetFieldsIn input3 = new GetFieldsIn("test1", "wrong", 5);
		GetFieldsOut output3 = communicator.getFields(input3);
		assertNull(output3);
		
	}
	
	@Test
	public void testSubmitBatch(){
		//test correct
		DownloadBatchIn setup = new DownloadBatchIn("test1", "test1", 1);
		DownloadBatchOut setupOut = communicator.getBatch(setup);
		ArrayList<String> testValues = new ArrayList<String>();
		testValues.add("Mooney"); testValues.add("Dick"); testValues.add("M");
		testValues.add("3"); testValues.add("Carney"); testValues.add("Maxwell");
		testValues.add("M"); testValues.add("50"); testValues.add("Carney");
		testValues.add("Candi"); testValues.add("F"); testValues.add("80");
		testValues.add("Ritter"); testValues.add("Karon"); testValues.add("F");
		testValues.add("83"); 
		for(int i = 0; i < 16; i++){
			testValues.add("");
		}
		SubmitBatchIn input = new SubmitBatchIn("test1", "test1", 1, testValues);
		SubmitBatchOut output = communicator.submitBatch(input);
		SubmitBatchOut result = new SubmitBatchOut(true);
		assertTrue(output.equals(result));
		//test wrong values
		testValues.add("dog");
		SubmitBatchIn input2 = new SubmitBatchIn("test1", "test1", 1, testValues);
		SubmitBatchOut output2 = communicator.submitBatch(input2);
		assertNull(output2);
		
	}
	
	@Test
	public void testSearch(){
		//test correct
		ArrayList<Integer> fields = new ArrayList<Integer>();
		ArrayList<String> inputs = new ArrayList<String>();
		fields.add(10); //fields.add(11); fields.add(12); fields.add(13);
		inputs.add("fox"); //inputs.add("russell"); inputs.add("19");
		//inputs.add("alaska native");
		SearchIn input = new SearchIn("test1", "test1", fields, inputs);
		SearchOut output = communicator.search(input);
		Image image = new Image(41, "images/draft_image0.png", 3, -1, -1);
		ArrayList<Image> images = new ArrayList<Image>();
		images.add(image);
		Rvalue v1 = new Rvalue(1, 1, 10, 41, "fox");
		Rvalue v2 = new Rvalue(2, 2, 11, 41, "russell");
		Rvalue v3 = new Rvalue(3, 3, 12, 41, "19");
		Rvalue v4 = new Rvalue(4, 4, 13, 41, "alaska native");
		ArrayList<Rvalue> results = new ArrayList<Rvalue>();
		results.add(v1); results.add(v2); results.add(v3); results.add(v4);
		SearchOut result = new SearchOut(images, results);
		assertTrue(output.equals(result));
		//test incorrect
		fields.add(50);
		SearchIn input2 = new SearchIn("test1", "test1", fields, inputs);
		SearchOut output2 = communicator.search(input2);
		assertNull(output2);
	}

}
