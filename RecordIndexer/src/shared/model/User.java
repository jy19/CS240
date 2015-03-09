package shared.model;

public class User {
	private String username;
	
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	private int indexedrecords;
	private int imageID;
	private int userID;
	
	public User(String username, String password, String firstname,
			String lastname, String email, int indexedrecords,
			int imageID, int userID) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.indexedrecords = indexedrecords;
		this.imageID = imageID;
		this.userID = userID;
	}
	
	public void incrementRecords(int additional){
		indexedrecords += additional;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getIndexedrecords() {
		return indexedrecords;
	}
	public void setIndexedrecords(int indexedrecords) {
		this.indexedrecords = indexedrecords;
	}
	
	public int getImageID() {
		return imageID;
	}
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public String toString(){
		String result = this.getUsername() + " " + this.getPassword() + " " + this.getFirstname() + 
				" " + this.getLastname() + " " + this.getEmail() + " " + this.getIndexedrecords() + 
				" " + this.getImageID() + " " + this.getUserID();
		return result;
	}
	
	public boolean equals(User user){
		if((this == null && user != null)|| (user == null && this != null)){
			return false;
		}
		if(this.toString().equals(user.toString())){
			return true;
		}
		else{
			return false;
		}
	}
}
