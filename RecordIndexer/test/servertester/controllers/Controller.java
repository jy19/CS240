package servertester.controllers;

import java.util.*;

import client.communication.ClientCommunicator;
import servertester.views.*;
import shared.communication.*;
import shared.model.Field;
import shared.model.Image;

public class Controller implements IController {

	private IView _view;
	private ClientCommunicator communicator = new ClientCommunicator();
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		communicator.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		ValidateUserIn input = new ValidateUserIn(params[0], params[1]);
		getView().setRequest(input.toString());
		ValidateUserOut output = communicator.validateUser(input);
		if(output != null){
			//false password
			if(output.isApproved()){
				getView().setResponse(output.toString());
			}
			else{
				getView().setResponse("FALSE\n");
			}
		}
		else{
			getView().setResponse("FAILED\n");
		}
		
	}
	
	private void getProjects() {
		communicator.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		GetProjectsIn input = new GetProjectsIn(params[0], params[1]);
		getView().setRequest(input.toString());
		GetProjectsOut output = communicator.getProject(input);
		if(output != null){
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getSampleImage() {
		communicator.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		SampleImageIn input = new SampleImageIn(params[0], params[1], Integer.parseInt(params[2]));
		getView().setRequest(input.toString());
		SampleImageOut output = communicator.getImage(input);
		if(output != null){
			output.getImage().addUrl(getView().getHost(), getView().getPort());
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void downloadBatch() {
		communicator.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		DownloadBatchIn input = new DownloadBatchIn(params[0], params[1], Integer.parseInt(params[2]));
		getView().setRequest(input.toString());
		DownloadBatchOut output = communicator.getBatch(input);
		if(output != null){
			for(Field field : output.getFieldsofProject()){
				field.addUrl(getView().getHost(), getView().getPort());
			}
			output.getImage().addUrl(getView().getHost(), getView().getPort());
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void getFields() {
		communicator.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		GetFieldsIn input = null;
		try{
			input = new GetFieldsIn(params[0], params[1], Integer.parseInt(params[2]));
		}
		catch(Exception e){
			input = new GetFieldsIn(params[0], params[1], -1);
		}
		getView().setRequest(input.toString());
		GetFieldsOut output = communicator.getFields(input);
		if(output != null){
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void submitBatch() {
		communicator.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		String userInput = params[3].toLowerCase();
		ArrayList<String> allValues = new ArrayList<String>();
		String [] records = userInput.split(";");
		for(String record : records){
			String [] values = record.split(",");
			List<String> tempList = Arrays.asList(values);
			allValues.addAll(tempList);
		}
		SubmitBatchIn input = new SubmitBatchIn(params[0], params[1], Integer.parseInt(params[2]), allValues);
		getView().setRequest(input.toString());
		SubmitBatchOut output = communicator.submitBatch(input);
		if(output != null){
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}
	
	private void search() {
		communicator.getHostPort(getView().getHost(), getView().getPort());
		String [] params = getView().getParameterValues();
		String userInputSearch = params[3].toLowerCase();
		String [] parsedFields = params[2].split(",");
		String [] parsedSearch = userInputSearch.split(",");
		ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
		for(int i = 0; i < parsedFields.length; i++){
			fieldIDs.add(Integer.parseInt(parsedFields[i]));
		}
		ArrayList<String> searchValues = new ArrayList<String>();
		for(String value: parsedSearch){
			searchValues.add(value);
		}
		SearchIn input = new SearchIn(params[0], params[1], fieldIDs, searchValues);
		getView().setRequest(input.toString());
		SearchOut output = communicator.search(input);
		if(output != null){
			for(Image image : output.getImages()){
				image.addUrl(getView().getHost(), getView().getPort());
			}
			getView().setResponse(output.toString());
		}
		else{
			getView().setResponse("FAILED\n");
		}
	}

}

