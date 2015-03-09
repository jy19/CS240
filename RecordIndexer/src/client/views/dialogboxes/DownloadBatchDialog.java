package client.views.dialogboxes;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import shared.communication.DownloadBatchIn;
import shared.communication.DownloadBatchOut;
import shared.communication.GetProjectsIn;
import shared.communication.GetProjectsOut;
import shared.communication.SampleImageIn;
import shared.communication.SampleImageOut;
import shared.model.Project;
import client.communication.ClientCommunicator;
import client.views.InfoManager;
import client.views.states.BatchState;

public class DownloadBatchDialog extends JDialog{
	private InfoManager info = InfoManager.getInfo();
	private BatchState batchState;
	private JComboBox<String> projects;
	private JButton sampleButton;
	private JButton downloadButton;
	private JButton cancelButton;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private int currProjectID;
	private ClientCommunicator communicator = ClientCommunicator.getCommunicator();
	
	public DownloadBatchDialog(){
		super(null, "Download Batch", Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		createComponents();
		currProjectID = projects.getSelectedIndex() + 1;
		this.add(topPanel);
		this.add(bottomPanel);
		this.pack();
		setLocationRelativeTo(null);
	}
	
	public void setBatchState(BatchState state){
		batchState = state;
	}
	
	/**
	 * populates the combo box with projects that are available
	 */
	
	private void populateProjects(){
		projects = new JComboBox<String>();
		
		GetProjectsIn input = new GetProjectsIn(info.getUsername(), info.getPassword());
		GetProjectsOut output = communicator.getProject(input);
		
		if(output != null){
			ArrayList<Project> allProjects = output.getProjects();
			for(Project project : allProjects){
				projects.addItem(project.getTitle());
			}
		}
		else{
			System.out.println("Get Projects -- this shouldn't be happening!?");
		}
	}
	
	private void createComponents(){
		topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JLabel projectLabel = new JLabel("Project: ");
		populateProjects();
		projects.addActionListener(projectsListener);
		sampleButton = new JButton("View Sample");
		sampleButton.addActionListener(sampleListener);
		
		topPanel.add(projectLabel);
		topPanel.add(projects);
		topPanel.add(sampleButton);
		
		bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(cancelListener);
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(downloadListener);
		
		bottomPanel.add(cancelButton);
		bottomPanel.add(downloadButton);
		
	}
	
	private void updateProject(){
		currProjectID = projects.getSelectedIndex() + 1;
		System.out.println("Project ID: " + currProjectID);
	}
	
	private ActionListener projectsListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			updateProject();
		}
		
	};
	
	private ActionListener sampleListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			SampleImageIn input = new SampleImageIn(info.getUsername(), info.getPassword(), currProjectID);
			SampleImageOut output = communicator.getImage(input);
			try {
				String imageURLtemp = communicator.getURLPrefix() + "//" + output.getImage().getFile();
				imageURLtemp = imageURLtemp.replace("\\", "/");
				URL imageURL = new URL(imageURLtemp);
				Image image =  ImageIO.read(imageURL);
				image = image.getScaledInstance(image.getWidth(null) / 2, image.getHeight(null) / 2, 0);
				ImageIcon sampleImage = new ImageIcon(image);
				JOptionPane.showMessageDialog(DownloadBatchDialog.this, "", "Sample image from " + projects.getSelectedItem(), 
						JOptionPane.OK_OPTION, sampleImage);
			} catch (IOException e1) {
				e1.printStackTrace();
				System.out.println("could not read image url");
			}
			
			
		}
		
	};
	
	private ActionListener downloadListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			DownloadBatchIn input = new DownloadBatchIn(info.getUsername(), info.getPassword(), currProjectID);
			DownloadBatchOut output = communicator.getBatch(input);
			
			batchState.setProject(output.getProject());
			batchState.setImageID(output.getImage().getId());
			batchState.setFields(output.getFieldsofProject());
			batchState.setBatchOutput(output);
			batchState.initDownloadBatch();
			
			DownloadBatchDialog.this.dispose();
			
		}
		
	};
	
	private ActionListener cancelListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			DownloadBatchDialog.this.dispose();
		}
		
	};
	
	
}
