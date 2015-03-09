package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import shared.model.Field;

@SuppressWarnings("serial")
public class FieldsPanel extends JPanel{
	private JLabel fieldsLabel;
	private JList<Field> fieldsList;
	private JScrollPane scrollpane;
	private ArrayList<Field> fields;
	
	public FieldsPanel(){
		fieldsLabel = new JLabel("Fields");
		fieldsList =  new JList<Field>();
		
		scrollpane = new JScrollPane(fieldsList);
		scrollpane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		this.setLayout(new BorderLayout());
		this.add(fieldsLabel, BorderLayout.NORTH);
		this.add(scrollpane, BorderLayout.CENTER);
		
	}
	
	public void populateFields(ArrayList<Field> inputFields){
		
		fields = inputFields;
		
		Field [] fieldsArray = new Field[inputFields.size()];
		fieldsArray = inputFields.toArray(fieldsArray);
		fieldsList.removeAll();
		fieldsList.setListData(fieldsArray);
	}
	
	public JList<Field> getFieldsList(){
		return fieldsList;
	}
	
	public ArrayList<Field> getFields(){
		return fields;
	}
}

