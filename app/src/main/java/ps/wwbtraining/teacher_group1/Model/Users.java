package ps.wwbtraining.teacher_group1.Model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Users{

	@SerializedName("User")
	private User user;

	@SerializedName("statuse")
	private String statuse;

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setStatuse(String statuse){
		this.statuse = statuse;
	}

	public String getStatuse(){
		return statuse;
	}



	@Override
 	public String toString(){
		return 
			"Users{" + 
			"user = '" + user + '\'' + 
			",statuse = '" + statuse + '\'' + 
			"}";
		}
}