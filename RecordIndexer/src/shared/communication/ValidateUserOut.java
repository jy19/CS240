package shared.communication;

import shared.model.User;

public class ValidateUserOut {

	private boolean approved;
	private User user;
	
	public ValidateUserOut(boolean approved, User user) {
		super();
		this.approved = approved;
		this.user = user;
	}
	
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User user){
		this.user = user;
	}
	public String toString(){
		String result = "TRUE" + "\n" + user.getFirstname() + "\n" + user.getLastname() + "\n" + user.getIndexedrecords() + "\n";
		return result;
	}
	
	public boolean equals(ValidateUserOut output){
		if((this == null && output != null)|| (output == null && this != null)){
			return false;
		}
		if(this.toString().equals(output.toString())){
			return true;
		}
		else{
			return false;
		}
	}
}
