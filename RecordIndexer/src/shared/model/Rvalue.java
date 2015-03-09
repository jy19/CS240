package shared.model;

public class Rvalue {
	private int id;
	private int rowNum;
	private int fieldID;
	private int imageID;
	private String value;
	
	public Rvalue(int id, int rowNum, int fieldID, int imageID, String value) {
		super();
		this.id = id;
		this.rowNum = rowNum;
		this.fieldID = fieldID;
		this.imageID = imageID;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public int getFieldID() {
		return fieldID;
	}

	public void setFieldID(int fieldID) {
		this.fieldID = fieldID;
	}

	public int getImageID() {
		return imageID;
	}

	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	public String toString(){
		String result = this.getId() + " " + this.getRowNum() + " " + this.getFieldID() + 
				" " + this.getImageID() + " " + this.getValue();
		return result;
	}
	
	public boolean equals(Rvalue rvalue){
		if((this == null && rvalue != null)|| (rvalue == null && this != null)){
			return false;
		}
		if(this.toString().equals(rvalue.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
}
