package shared.communication;

import java.util.ArrayList;

import shared.model.Image;
import shared.model.Rvalue;

public class SearchOut {
	private ArrayList<Image> images;
	private ArrayList<Rvalue> values;
	public SearchOut(ArrayList<Image> images, ArrayList<Rvalue> values) {
		super();
		this.images = images;
		this.values = values;
	}
	public ArrayList<Image> getImages() {
		return images;
	}
	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}
	public ArrayList<Rvalue> getValues() {
		return values;
	}
	public void setValues(ArrayList<Rvalue> values) {
		this.values = values;
	}
	public String toString(){
		String result = "";
		for(int i = 0; i < images.size(); i++){
			Image image = images.get(i);
			Rvalue value = values.get(i);
			String part = image.getId() + "\n" + image.getFile() + "\n" + value.getRowNum() + "\n" + value.getFieldID() + "\n";
			result += part;
		}
		return result;
	}
	public boolean equals(SearchOut output){
		if(this == null || output == null){
			return (this == null && output == null);
		}
		if(this.toString().equals(output.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
}
