package views;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.*;

import shared.model.Field;
import shared.model.Image;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel{
	private JLabel imageLabel;
	private JList<String> imagesList;
	private JScrollPane scrollPane;
	
	public ImagePanel(){
		imageLabel = new JLabel("Images");
		imagesList = new JList<String>();
		
		scrollPane = new JScrollPane(imagesList);
		
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		this.setLayout(new BorderLayout());
		this.add(imageLabel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	public void populateImages(ArrayList<Image> images){
		
		String [] imagesArray = new String[images.size()];
		for(int i = 0; i < images.size(); i++){
			imagesArray[i] = images.get(i).getFile();
		}
		imagesList.removeAll();
		imagesList.setListData(imagesArray);
	}
	
	public JList<String> getImagesList(){
		return imagesList;
	}
}
