package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class HostportPanel extends JPanel{
	private LabelTextField hostPanel;
	private LabelTextField portPanel;
	
	public HostportPanel(){
		hostPanel = new LabelTextField("Host: ", 50);
		portPanel = new LabelTextField("Port: ", 50);
		
		hostPanel.setPreferredSize(new Dimension(100, 30));
		portPanel.setPreferredSize(new Dimension(100, 30));
		
//		this.setLayout(new BorderLayout());
//		this.add(hostPanel, BorderLayout.WEST);
//		this.add(portPanel, BorderLayout.EAST);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(Box.createHorizontalGlue());
		this.add(hostPanel);
		this.add(Box.createRigidArea(new Dimension(10, 0)));
		this.add(portPanel);
		this.add(Box.createHorizontalGlue());
		
	}
	
	public String getHost(){
		return hostPanel.getText();
	}
	public String getPort(){
		return portPanel.getText();
	}
	
	private ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			
		}  	
    };
}
