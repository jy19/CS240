package shared.communication;

import java.util.ArrayList;

import shared.model.Field;

public class GetFieldsOut {
	private int projectID;
	private ArrayList<Field> fields;
	
	public GetFieldsOut(int projectID, ArrayList<Field> fields) {
		super();
		this.projectID = projectID;
		this.fields = fields;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public ArrayList<Field> getFields() {
		return fields;
	}

	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}
	
	public String toString(){
		String result = "";
		for(Field field : fields){
			result += (field.getProjectID() + "\n" + field.getId() + "\n" + field.getTitle() + "\n");
		}
		return result;
	}
	
	public boolean equals(GetFieldsOut output){
		if(this == null || output == null){
			return (this == null && output == null);
		}
		if(this.toString().equals(output.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
}
