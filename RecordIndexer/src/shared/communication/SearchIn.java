package shared.communication;

import java.util.ArrayList;

public class SearchIn {
	private String username;
	private String password;
	private ArrayList<Integer> fieldIDs;
	private ArrayList<String> inputValues;
	public SearchIn(String username, String password,
			ArrayList<Integer> fieldIDs, ArrayList<String> inputValues) {
		super();
		this.username = username;
		this.password = password;
		this.fieldIDs = fieldIDs;
		this.inputValues = inputValues;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<Integer> getFieldIDs() {
		return fieldIDs;
	}
	public void setFieldIDs(ArrayList<Integer> fieldIDs) {
		this.fieldIDs = fieldIDs;
	}
	public ArrayList<String> getInputValues() {
		return inputValues;
	}
	public void setInputValues(ArrayList<String> inputValues) {
		this.inputValues = inputValues;
	}
	public String toString(){
		String fields = "";
		for(int id : fieldIDs){
			fields += id + "\n";
		}
		String values = "";
		for(String input : inputValues){
			values += input + "\n";
		}
		String result = username + "\n" + password + "\n" + fields + "\n" + values + "\n";
		return result;
	}
	
}
