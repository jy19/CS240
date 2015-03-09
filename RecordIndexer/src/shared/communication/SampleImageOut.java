package shared.communication;

import shared.model.Image;

public class SampleImageOut {
	
	private Image image;
	
	public SampleImageOut(Image image) {
		super();
		this.image = image;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public String toString(){
		String result = image.getFile() + "\n";
		return result;
	}
	public boolean equals(SampleImageOut output){
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
