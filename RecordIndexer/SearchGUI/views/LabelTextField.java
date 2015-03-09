package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LabelTextField extends JPanel{
	
	private JTextField textField;
	
	public LabelTextField(String display, int fieldSize){
		JLabel label = new JLabel(display);
		
		textField = new JTextField(fieldSize);
		textField.setOpaque(true);
		textField.setBackground(Color.white);
		
		this.setLayout(new BorderLayout());
		this.add(label, BorderLayout.WEST);
		this.add(textField, BorderLayout.CENTER);
	}
	
	public String getText(){
		return textField.getText();
	}
	public void setPreferredSize(Dimension size){
		textField.setPreferredSize(size);
	}
}