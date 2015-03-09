package client.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.communication.DownloadBatchIn;
import shared.communication.DownloadBatchOut;
import shared.communication.GetFieldsIn;
import shared.communication.GetFieldsOut;
import shared.communication.GetProjectsIn;
import shared.communication.GetProjectsOut;
import shared.communication.SampleImageIn;
import shared.communication.SampleImageOut;
import shared.communication.SearchIn;
import shared.communication.SearchOut;
import shared.communication.SubmitBatchIn;
import shared.communication.SubmitBatchOut;
import shared.communication.ValidateUserIn;
import shared.communication.ValidateUserOut;

public class ClientCommunicator {
	
	private String urlPrefix = "";
	private static ClientCommunicator communicator = new ClientCommunicator();
	public ClientCommunicator(){
		
	}
	public ClientCommunicator(String host, String port){
		urlPrefix = "http://" + host + ":" + port;
	}
	public void getHostPort(String host, String port){
		urlPrefix = "http://" + host + ":" + port;
	}
	
	public static ClientCommunicator getCommunicator(){
		return communicator;
	}
	
	public String getURLPrefix(){
		return urlPrefix;
	}
	
	/**
	 * validates user input
	 * @param user input
	 * @return validation of user's input,
	 * 		if successful, also returns first/last name 
	 * 		and number of records indexed.
	 */
	public ValidateUserOut validateUser(ValidateUserIn params){
		return (ValidateUserOut) doPost("/validateuser", params);
	}
	/**
	 * validates user's input request for projects
	 * @param user input to see list of projects
	 * @return available projects
	 */
	public GetProjectsOut getProject(GetProjectsIn params){
		return (GetProjectsOut) doPost("/getprojects", params);
	}
	/**
	 * validates user's input request for image
	 * @param user input to get image, as well as which project
	 * @return validation and image url
	 */
	public SampleImageOut getImage(SampleImageIn params){
		return (SampleImageOut) doPost("/getsampleimage", params);
	}
	/**
	 * validates user's info and id, then returns possible batches
	 * @param user input: user info and project id
	 * @return batches and corresponding info
	 */
	public DownloadBatchOut getBatch(DownloadBatchIn params){
		return (DownloadBatchOut) doPost("/downloadbatch", params);
	}
	/**
	 * validate if user's input is correct
	 * @param user's input batch
	 * @return whether or not it's successful
	 */
	public SubmitBatchOut submitBatch(SubmitBatchIn params){
		return (SubmitBatchOut) doPost("/submitbatch", params);
	}
	/**
	 * validates user input and returns info
	 * @param user info, project id
	 * @return project id, field id, field title 
	 */
	public GetFieldsOut getFields(GetFieldsIn params){
		return (GetFieldsOut) doPost("/getfields", params);
	}
	/**
	 * validates user input, and returns the search results.  
	 * If found, returns the batch id, image url, record number, and field id.
	 * @param user info, fields to search, and search value
	 * @return search results
	 */
	public SearchOut search(SearchIn params){
		return (SearchOut) doPost("/search", params);
	}
	
	
	/**
	 * Make HTTP POST request to the specified URL, 
	 * passing in the specified postData object
	 * @param urlPath
	 * @param postData
	 */
	private Object doPost(String urlPath, Object postData) {
		Object response = null;
		try { 
			urlPath = urlPrefix + urlPath;
			URL url = new URL(urlPath); 
			HttpURLConnection connection = (HttpURLConnection)url.openConnection(); 
			 
			connection.setRequestMethod("POST"); 
			connection.setDoOutput(true); 
			 
			connection.connect(); 
			 
			OutputStream requestBody = connection.getOutputStream(); 
			XStream xmlStream = new XStream(new DomDriver());
			// Write request body to OutputStream ...
			xmlStream.toXML(postData, requestBody);
			  
			requestBody.close(); 
			 
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) { 
				 
				InputStream responseBody = connection.getInputStream(); 
				// Read response body from InputStream ...
				response = xmlStream.fromXML(responseBody);
				 
			} 
			else if(connection.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
				//wrong user pw
				return new ValidateUserOut(false, null);
			}
			else { 
				//failed
				return null;
			} 
		} 
		catch(IOException e){
			e.printStackTrace();
			System.out.println("Couldn't doPost!");
		}

		return response;
	}

}
