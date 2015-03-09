package shared.communication;

import java.util.ArrayList;

import shared.model.Field;
import shared.model.Image;
import shared.model.Project;

public class DownloadBatchOut {
	
	private Project project;
	private Image image;
	private ArrayList<Field> fieldsofProject;
	
	public DownloadBatchOut(Project project, Image image, 
			ArrayList<Field> fieldsofProject) {
		super();
		this.project = project;
		this.image = image;
		this.fieldsofProject = fieldsofProject;
	}
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public ArrayList<Field> getFieldsofProject() {
		return fieldsofProject;
	}
	public void setFieldsofProject(ArrayList<Field> fieldsofProject) {
		this.fieldsofProject = fieldsofProject;
	}
	
	public String toString(){
		String result = image.getId() + "\n" + image.getProjectID() + "\n" + image.getFile() + "\n" + project.getFirstycoord() + 
				"\n" + project.getRecordheight() + "\n" + project.getRecordsperimage() + "\n" + fieldsofProject.size() + "\n";
		for(Field field: fieldsofProject){
			int count = 1;
			String part = field.getId() + "\n" + count + "\n" + field.getTitle() + "\n" + field.getHelphtml() + "\n" + 
					field.getXcoordinate() + "\n" + field.getWidth() + "\n";
			if(field.getKnowndata() != null && !field.getKnowndata().equals("")){
				part += field.getKnowndata() + "\n";
			}
			result += part;
			count++;
		}
		
		return result;
	}
	
	public boolean equals(DownloadBatchOut output){
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
