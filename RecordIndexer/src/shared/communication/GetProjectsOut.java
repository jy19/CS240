package shared.communication;

import java.util.ArrayList;

import shared.model.Project;

public class GetProjectsOut {
	private boolean approved;
	private ArrayList<Project> projects;
	
	public GetProjectsOut(boolean approved, ArrayList<Project> projects) {
		super();
		this.approved = approved;
		this.projects = projects;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}


	public ArrayList<Project> getProjects() {
		return projects;
	}

	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
	}
	
	public String toString(){
		String result = "";
		for(Project project: projects){
			result += (project.getID() + "\n" + project.getTitle() + "\n");
		}
		return result;
	}
	public boolean equals(GetProjectsOut output){
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
