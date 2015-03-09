package views;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ValuesPanel extends JPanel {
	private LabelTextField searchValues;
	
	public ValuesPanel(){
		searchValues = new LabelTextField("Search: ", 50);
		
		this.setLayout(new BorderLayout());
		this.add(searchValues, BorderLayout.CENTER);
	}
	
	public ArrayList<String> getSearchValues(){
		ArrayList<String> values = new ArrayList<String>();
		
		String inputValues = searchValues.getText();
		
		String[] arrayValues = inputValues.split(",");
		for(String val: arrayValues){
			values.add(val);
		}
		return values;
	}
}
