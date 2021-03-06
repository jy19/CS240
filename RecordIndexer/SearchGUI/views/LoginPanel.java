package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.communication.ClientCommunicator;
import shared.communication.ValidateUserIn;
import shared.communication.ValidateUserOut;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel{
	private LabelTextField userPanel;
	private LabelTextField passwordPanel;
	private ValidateUserOut validatedInfo;
	
	
	public LoginPanel(final ClientCommunicator communicator){
		userPanel = new LabelTextField("Username: ", 50);
		passwordPanel = new LabelTextField("Password: ", 50);
		
//		userPanel.setPreferredSize(new Dimension(100, 30));
//		passwordPanel.setPreferredSize(new Dimension(100, 30));
		
//		this.setLayout(new BorderLayout());
//		this.add(userPanel, BorderLayout.NORTH);
//		this.add(passwordPanel, BorderLayout.SOUTH);
		userPanel.setMaximumSize(new Dimension(800, 30));
		passwordPanel.setMaximumSize(new Dimension(800, 30));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(userPanel);
		this.add(passwordPanel);
	}
	
	public String getUsername(){
		return userPanel.getText();
	}
	public String getPassword(){
		return passwordPanel.getText();
	}
	public ValidateUserOut getLoginInfo(){
		return validatedInfo;
	}
}