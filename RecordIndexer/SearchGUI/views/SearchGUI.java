package views;

import static servertester.views.Constants.TRIPLE_HSPACE;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import shared.communication.GetFieldsIn;
import shared.communication.GetFieldsOut;
import shared.communication.GetProjectsIn;
import shared.communication.GetProjectsOut;
import shared.communication.SearchIn;
import shared.communication.SearchOut;
import shared.communication.ValidateUserIn;
import shared.communication.ValidateUserOut;
import shared.model.Field;
import shared.model.Project;
import client.communication.ClientCommunicator;

@SuppressWarnings("serial")
public class SearchGUI extends JFrame{
	private FieldsPanel fields;
	private LoginPanel loginInfo;
	private HostportPanel hostport;
	private ImagePanel sampleImage;
	private ProjectsPanel projects;
	private ValuesPanel values;
	private JButton loginButton;
	private JButton searchButton;
	private boolean validated;
	private int currentProjectID;
	private ImageDialog imageDialog;
	private ClientCommunicator communicator = new ClientCommunicator();
	
	public SearchGUI(String title){
		super(title);
		
		createComponents();
	}
	
	
	
	private void createComponents(){
		addWindowListener(windowAdapter);
		
		hostport = new HostportPanel();
		loginInfo = new LoginPanel(communicator);
		loginButton = new JButton("Log In");
		loginButton.addActionListener(actionListener);

		JPanel topPanel = new JPanel();
//		topPanel.setLayout(new BorderLayout());
//		topPanel.add(hostport, BorderLayout.NORTH);
//		topPanel.add(loginInfo, BorderLayout.CENTER);
//		topPanel.add(loginButton, BorderLayout.EAST);
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		topPanel.add(Box.createHorizontalGlue());
		topPanel.add(hostport);
		hostport.setMaximumSize(new Dimension(1000, 30));
		topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		topPanel.add(loginInfo);
		topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		topPanel.add(loginButton);
		topPanel.add(Box.createHorizontalGlue());
		
		projects = new ProjectsPanel();
		//projects.getChoices().setSelectedIndex(0);
		projects.getChoices().addActionListener(projectsListener);
		
		JPanel middlePanel = new JPanel();
		middlePanel.setLayout(new BorderLayout());
		middlePanel.add(projects, BorderLayout.CENTER);
		middlePanel.setMaximumSize(new Dimension(1000, 20));
		
		values = new ValuesPanel();
		sampleImage = new ImagePanel();
		
		searchButton = new JButton("Search!");
		searchButton.addActionListener(searchListener);
		sampleImage.getImagesList().addListSelectionListener(selectImageListener);
		
		JPanel bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new BorderLayout());
		bottomRightPanel.add(values, BorderLayout.NORTH);
		bottomRightPanel.add(searchButton, BorderLayout.EAST);
		bottomRightPanel.add(sampleImage, BorderLayout.SOUTH);
		
		
		fields = new FieldsPanel();
		
//		JPanel bottomLeftPanel = new JPanel();
//		bottomLeftPanel.setLayout(new BorderLayout());
//		bottomLeftPanel.add(fields, BorderLayout.CENTER);
		
		JSplitPane splitBottomPanel = new JSplitPane();
		splitBottomPanel.setContinuousLayout(true);
		splitBottomPanel.setOneTouchExpandable(true);
		
		splitBottomPanel.setLeftComponent(fields);
		splitBottomPanel.setRightComponent(bottomRightPanel);
		
		
		JPanel basePanel = new JPanel();
		basePanel.setLayout(new BoxLayout(basePanel, BoxLayout.Y_AXIS));
		basePanel.add(topPanel);
		basePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		basePanel.add(middlePanel);
		basePanel.add(Box.createRigidArea(new Dimension(0, 10)));
		basePanel.add(splitBottomPanel);
		
		this.add(basePanel);
		
		
	}
	
	private void openImage(){
		
		try {
			URL imageURL = new URL("http://" + hostport.getHost() + ":" + hostport.getPort() + 
					"//" + sampleImage.getImagesList().getSelectedValue());
			BufferedImage image = ImageIO.read(imageURL);
			imageDialog = new ImageDialog(this, image);
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Malformed url/ioexception");
		}
	
	}
	
	private void updateProjectSelected() {
		
		String currProjectTitle = (String) projects.getChoices().getSelectedItem();
		currentProjectID = projects.getProjectID(currProjectTitle);
		
	}
	
	private ActionListener actionListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			
			communicator.getHostPort(hostport.getHost(), hostport.getPort());
			
			ValidateUserIn input = new ValidateUserIn(loginInfo.getUsername(), loginInfo.getPassword());
			ValidateUserOut output = communicator.validateUser(input);
			
			if(!output.isApproved()){
				validated = false;
			}
			else{
				validated = true;
				GetProjectsIn projectsIn = new GetProjectsIn(loginInfo.getUsername(), loginInfo.getPassword());
				GetProjectsOut projectsOut = communicator.getProject(projectsIn);
				projects.populateChoices(projectsOut.getProjects());
			}

		}
		
	};
	
	private ActionListener projectsListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			updateProjectSelected();
			
			communicator.getHostPort(hostport.getHost(), hostport.getPort());
			GetFieldsIn input = new GetFieldsIn(loginInfo.getUsername(), loginInfo.getPassword(), currentProjectID);
			GetFieldsOut output = communicator.getFields(input);
			fields.populateFields(output.getFields());
		}
		
	};
	
	
	private ActionListener searchListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent e) {
			communicator.getHostPort(hostport.getHost(), hostport.getPort());
			
			ArrayList<Integer> fieldIDs = new ArrayList<Integer>();
			List<Field> selectedFields = fields.getFieldsList().getSelectedValuesList();
			for(Field field : selectedFields){
				fieldIDs.add(field.getId());
			}
			SearchIn input = new SearchIn(loginInfo.getUsername(), loginInfo.getPassword(), 
								fieldIDs, values.getSearchValues());
			SearchOut output = communicator.search(input);
			if(output != null){
				sampleImage.populateImages(output.getImages());
			}
			
		}
		
	};
	
	private ListSelectionListener selectImageListener = new ListSelectionListener(){

		@Override
		public void valueChanged(ListSelectionEvent e) {
			openImage();
			
		}
	};
	
	private WindowAdapter windowAdapter = new WindowAdapter() {
	    	
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
	};
	public static void main(String[] args) {
    	
		
        EventQueue.invokeLater(new Runnable()
        {
           public void run()
           {
               SearchGUI frame = new SearchGUI("Search");
               frame.pack();
               frame.setSize(500, 500);
               frame.setVisible(true);
           }
        });
    }
	
}
