package shared.model;

public class Field {
	private int id;
	private String title;
	private int xcoordinate;
	private int width;
	private String helphtml;
	private String knowndata;
	private int projectID;
	
	public Field(int id, String title, int xcoordinate, int width,
			String helphtml, String knowndata, int projectID) {
		super();
		this.id = id;
		this.title = title;
		this.xcoordinate = xcoordinate;
		this.width = width;
		this.helphtml = helphtml;
		this.knowndata = knowndata;
		this.projectID = projectID;
	}
	
	public void addUrl(String host, String port){
		String url = "http://" + host + ":" + port + "//";
		helphtml = url + helphtml;
		if(knowndata != null && !knowndata.equals("")){
			knowndata = url + knowndata;
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getXcoordinate() {
		return xcoordinate;
	}
	public void setXcoordinate(int xcoordinate) {
		this.xcoordinate = xcoordinate;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public String getHelphtml() {
		return helphtml;
	}
	public void setHelphtml(String helphtml) {
		this.helphtml = helphtml;
	}
	public String getKnowndata() {
		return knowndata;
	}
	public void setKnowndata(String knowndata) {
		this.knowndata = knowndata;
	}
	public int getProjectID() {
		return projectID;
	}
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	
	public String toString(){
		String result = this.getId() + " " + this.getTitle() + " " + this.getXcoordinate() + 
				" " + this.getWidth() + " " + this.getHelphtml() + " " + this.getKnowndata() + 
				" " + this.getProjectID();
		return result;
	}
	
	public boolean equals(Field field){
		if(this == null || field == null){
			return false;
		}
		if(this.toString().equals(field.toString())){
			return true;
		}
		else{
			return false;
		}
	}
}
