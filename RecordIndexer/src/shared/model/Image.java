package shared.model;

public class Image {
	private int id;
	private String file;
	private int projectID;
	private int currentState;
	private int userID;
	
	public Image(int id, String file, int projectID, int currentState,
			int userID) {
		super();
		this.id = id;
		this.file = file;
		this.projectID = projectID;
		this.currentState = currentState;
		this.userID = userID;
	}
	
	public void addUrl(String host, String port){
		String url = "http://" + host + ":" + port + "//";
		file = url + file;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public int getCurrentState() {
		return currentState;
	}

	public void setCurrentState(int currentState) {
		this.currentState = currentState;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String toString(){
		String result = this.getId() + " " + this.getFile() + " " + this.getProjectID() + 
				" " + this.getCurrentState() + " " + this.getUserID();
		return result;
	}
	
	public boolean equals(Image image){
		if((this == null && image != null)|| (image == null && this != null)){
			return false;
		}
		if(this.toString().equals(image.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
}
