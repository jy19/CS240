package shared.model;

public class Project {
	private int id;
	private String title;
	private int recordsperimage;
	private int firstycoord;
	private int recordheight;
	
	public Project(int id, String title, int recordsperimage,
			int firstycoord, int recordheight) {
		super();
		this.id = id;
		this.title = title;
		this.recordsperimage = recordsperimage;
		this.firstycoord = firstycoord;
		this.recordheight = recordheight;
	}
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getRecordsperimage() {
		return recordsperimage;
	}
	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
	}
	public int getFirstycoord() {
		return firstycoord;
	}
	public void setFirstycoord(int firstycoord) {
		this.firstycoord = firstycoord;
	}
	public int getRecordheight() {
		return recordheight;
	}
	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}
	public String toString(){
		String result = this.getID() + " " + this.getTitle() + " " + this.getRecordsperimage() + 
				" " + this.getFirstycoord() + " " + this.getRecordheight();
		return result;
	}
	
	public boolean equals(Project project){
		if((this == null && project != null) || (this != null && project == null)){
			return false;
		}
		if(this.toString().equals(project.toString())){
			return true;
		}
		else{
			return false;
		}
	}
	
}
