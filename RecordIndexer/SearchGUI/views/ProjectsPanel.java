package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.model.Project;

@SuppressWarnings("serial")
public class ProjectsPanel extends JPanel{
	private JLabel projectsLabel;
	private JComboBox<String> projectChoices;
	private ArrayList<Project> projects;
	
	public ProjectsPanel(){
		projectsLabel = new JLabel("Projects: ");
		projectChoices = new JComboBox<String>();
		projectChoices.setEditable(false);
		
		projectChoices.setPreferredSize(new Dimension(100, 50));
		projectChoices.setBackground(Color.WHITE);
		
//		this.setLayout(new BorderLayout());
//		this.add(projectsLabel, BorderLayout.WEST);
//		this.add(projectChoices, BorderLayout.CENTER);
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.add(projectsLabel);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(projectChoices);
	}
	
	
	public void populateChoices(ArrayList<Project> inputProjects){
		
		projects = inputProjects;
		for(Project project: inputProjects){
			projectChoices.addItem(project.getTitle());
		}
	}
	public JComboBox<String> getChoices(){
		return projectChoices;
	}
	public ArrayList<Project> getProjects(){
		return projects;
	}
	public int getProjectID(String projectTitle){
		int index = 0;
		for(int i = 0; i < projects.size(); i++){
			if(projectTitle.equals(projects.get(i).getTitle())){
				index = i;
				break;
			}
		}
		return index+1;
	}
}
