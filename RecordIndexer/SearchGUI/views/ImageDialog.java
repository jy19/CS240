package views;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ImageDialog extends JDialog{
	public ImageDialog(Frame launchingWindow, BufferedImage image){
		super(launchingWindow,
	        "Selected Image", true); //launch this as a modal dialog.
	    JLabel imageDisplayer = new JLabel(new ImageIcon(image));
	    this.add(imageDisplayer, BorderLayout.CENTER);
	    this.pack();
	    this.setVisible(true);
	    setLocationRelativeTo(null);
	}
	
}
